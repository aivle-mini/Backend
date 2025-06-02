# AIVLE mini project
## 📘 도서 관리 시스템 구축
### ⚙️ 개발 환경
- `Java 17.0.12`
- `JDK 17.0.12`
- **Framework** : Springboot(3.5.0)
- **Database** : H2

# 📌 세부 구현 사항
## domin
### Book
```JAVA
@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length=255)
    private String title;
    @Column(nullable = false, length=255)
    private String content;
    @Column(nullable = false, length=4096)
    private String image_url;

    private Boolean is_deleted;
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
```
### User
```JAVA
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length=255)
    private String email;
    @Column(nullable = false, length=255)
    private String passwd;
    @Column(nullable = false, length=255)
    private String name;
    @CreationTimestamp
    private LocalDateTime created_at;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Book> book;
}
```
## dto
### BookDTO
```JAVA
public class BookDTO {
    // 신규 도서 생성
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String title;
        private String content;
        private String image_url;
        private Long userId;
    }
    // 조회(GET)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private User user;
        private String image_url;
        private String created_at;
        private String updated_at;
        private Boolean is_deleted;
    }
    // 도서 수정 (PUT)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Put {

        private String title;
        private String content;
        private String image_url;
    }
}
```
### UserDTO
```JAVA
public class UserDTO {

    @Getter
    @Setter
    public static class Register {
        private String email;
        private String passwd;
        private String name;
    }

    @Getter
    @Setter
    public static class Login {
        private String username;
        private String passwd;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String username;
        private String email;
    }
}
```
## controller
### BookController
```JAVA
    public class BookController {
    private final BookService bookService;

    //bckang
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO.Post dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    //bckang
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDTO.Put dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    // 전체 도서 조회 ymlee
    @GetMapping
    public List<BookDTO.Response> getBooks() {
        return bookService.findBooks();
    }

    // 특정 도서 조회 ymlee
    @GetMapping("/{bookId}")
    public BookDTO.Response getBook(@PathVariable("bookId") Long id) {
        return bookService.findBook(id);
    }

    // 삭제 ymlee
    @DeleteMapping("/{bookId}")
    public BookDTO.Response deleteBook(@PathVariable("bookId") Long id) {
        return bookService.deleteBook(id);
    }
}
```
### UserController
```JAVA
    public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDTO.Register dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    // 로그인 (세션에 사용자 정보 저장)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO.Login dto, HttpSession session) {
        UserDTO.Response user = userService.login(dto);
        session.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }

    // 로그아웃 (세션 종료)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("status", "success", "message", "로그아웃이 완료되었습니다."));
    }

    // 사용자 정보 조회 (세션 유효성 검증 포함)
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id, HttpSession session) {
        UserDTO.Response sessionUser = (UserDTO.Response) session.getAttribute("user");
        if (sessionUser == null || !sessionUser.getId().equals(id)) {
            return ResponseEntity.status(401).body(Map.of("status", "error", "message", "유효하지 않은 세션입니다."));
        }
        return ResponseEntity.ok(userService.getUser(id));
    }
}
```

## Service

### BookService

```JAVA
public interface BookService {
    // 모든 도서 조회 yml
    public List<BookDTO.Response> findBooks();
    // 특정 도서 조회 yml
    public BookDTO.Response findBook(Long id);
    // 도서 삭제 yml
    public BookDTO.Response deleteBook(Long id);
    // 데이터 유무 검증 yml
    public Book findVerifiredBook(Long id);
    //추가로직
    public Book createBook(BookDTO.Post dto);
    //수정로직
    public Book updateBook(Long id, BookDTO.Put dto);
}
```
### BookServiceImpl

```JAVA
@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookControlMapper mapper;
    private final BookResponseMapper responseMapper;

    // 모든 도서 조회 yml
    public List<BookDTO.Response> findBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println(books);
        System.out.println(responseMapper.booksToResponses(books));
        return responseMapper.booksToResponses(books);
    }

    // 특정 도서 조회 yml
    public BookDTO.Response findBook(Long id) {
        Book book = findVerifiredBook(id);
        return responseMapper.entityToResponse(book);
    }

    // 도서 삭제 yml
    public BookDTO.Response deleteBook(Long id) {
        Book book = findVerifiredBook(id);
        boolean deleted = Boolean.TRUE.equals(book.getIs_deleted());

        book.setIs_deleted(!deleted); // 토글
        return responseMapper.entityToResponse(book);
    }

    // 데이터 유무 검증 yml
    public Book findVerifiredBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> BaseException.type(BookErrorCode.NOT_FOUND_BOOK));
    }

    //추가로직
    public Book createBook(BookDTO.Post dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Book book = mapper.toEntity(dto);
        book.setUser(user);
        return bookRepository.save(book);
    }

    //수정로직
    public Book updateBook(Long id, BookDTO.Put dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        book.setTitle(dto.getTitle());
        book.setContent(dto.getContent());
        book.setImage_url(dto.getImage_url());
        return bookRepository.save(book);
    }
}

```
### UserService
```JAVA
public interface UserService {
    UserDTO.Response register(UserDTO.Register dto);
    UserDTO.Response login(UserDTO.Login dto);
    UserDTO.Response getUser(Long id);
}
```

### UserServiceImpl

```JAVA
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO.Response register(UserDTO.Register dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswd(dto.getPasswd()); // 실 서비스에서는 BCrypt 해싱 적용 필요
        user.setName(dto.getName());
        return toResponse(userRepository.save(user));
    }

    @Override
    public UserDTO.Response login(UserDTO.Login dto) {
        User user = userRepository.findByEmail(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        if (!user.getPasswd().equals(dto.getPasswd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return toResponse(user);
    }

    @Override
    public UserDTO.Response getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        return toResponse(user);
    }

    private UserDTO.Response toResponse(User user) {
        return new UserDTO.Response(user.getId(), user.getEmail(), user.getName());
    }
}

```
