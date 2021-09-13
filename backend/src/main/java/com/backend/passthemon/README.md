### Spring Boot 框架使用说明

-----
#### package: constant
* 说明 : 类, 用来存常数, 如网址等. 用来存别名,如定义 NOT_FOUND 为 -1.
```JAVA
public class BookConstant {
  public static final String NOT_FOUND = -1;   
}
```

----
#### package: controller
* 说明 : 类, 控制层, 代码规范 :
```JAVA
@RestController
public class BookController {
    /* 仅允许引入Service */
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/Book")
    public String test () {
        return "This is a test book!";  // 字符串类型
    };
    @PostMapping("/editBookInfo")
    public ResponseEntity<Integer> editBookInfo(String bookString) {
        return new ResponseEntity<>(bookService.editBookInfo(book), HttpStatus.OK);
    };
}
```
  
----
#### package : entity
* 说明 : 类, 实体类, 与数据库表格创建密切相关, 如需修改需讨论, 且遵循已有规范.
```JAVA
@Entity
@Table(name="数据库表格")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Book {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "saleroom")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal saleroom;
    
    @Column(name = "image", columnDefinition = "longblob")
    private Byte[] image;
}
```
----
#### package : service
* 说明 : 接口, 服务层抽象, 被controller层调用, 作为serviceimpl的头文件, 仅用作接口.
```JAVA
public interface BookService {
    /*获取用户信息*/
    List<Book> getBooks ();
}
```

----
#### package : serviceimpl
* 说明 : 类, 服务层实体, 实现service中函数, 调用repository层.
```JAVA
@Service
public class BookServiceImpl implements BookService {
    /* 仅可定义Repository层 */
    @Autowired
    private BookRepository bookRepository;
    /*获取用户信息*/
    public List<Book> getBooks () {
        return bookRepository.getBooks();
    };
}
```

----
#### package : repository
* 说明 : 接口, 仓库层抽象, 被serviceimpl层调用, 作为repositoryimpl头文件.
```JAVA
public interface BookRepository {
   /*获取用户信息*/
   List<Book> getBooks ();
}
```

----
#### pacakge : repositoryimpl
* 说明 : 类, 仓库层实体, 实现repository中函数, 调用dao层.
```JAVA
@Repository
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    private BookDaoJpa bookDaoJpa;
    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    /*获取图书信息*/
    public List<Book> getBooks () {
        return bookDaoJpa.findByStateEquals(1);
    }
}
``` 

----
#### package : dao
* 说明 : 接口, dao层, 从jparepository拓展而来.
```JAVA
@Repository
public interface BookDaoJpa extends JpaRepository<Book, Long> {
    Book findById(Integer id);
    List<Book> findAll();
}
```

----
#### package : dto
* 说明 : 数据传输层, dto层

#### 测试
* 说明 : 测试时需要注释掉JwtInterceptor中函数内容, 因为其没有注解无法Autowire其他类.
* 说明 : 测试时将使用自带h2数据库, 和运行环境数据库不一样
