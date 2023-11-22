# web课设后端
## 简介
### 框架MybatisPlus
****** 
 MyBatis-Plus (opens new window)（简称 MP）是一个 MyBatis (opens new window)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

***突出特点为***<br/>
1. 以Wrapper为“组先类”的数据库查询方法类(和sql语句相比各有优劣罢了)<br/>
2. 数据库名与实体类名、数据库列名与类属性以规范命名<br/>
3. 有很多使用插件(但本项目用的少)

 
### 数据库mysql
*****
> 我的版本信息
>> Ver 8.0.32 for Win64 on x86_64 (MySQL Community Server - GPL)

推荐使用 ***DataGrip或IntelliJ IDEA Ultimate***打开**mp.sql**文件
### 接口管理
使用ApiFox PC端(与we端同步)，***遥遥领先！！！***<br/>
**传送门**  <https://app.apifox.com/project/3552035> 

### JDK版本
> version 11.0.12

## getStart

### 打开项目
*****
请使用***IntelliJ IDEA Ultimate或IntelliJ IDEA Community***打开项目


### 数据库mysql
*****
假设你已经成功安装了任意版本的**mysql**，下面告诉你如何将数据库与项目“搭配”

- application.yaml文件
>路径: mp-demo/src/main/resources/

**你会在前6行看到如下代码**
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/mp?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
```
**只需** 将username与password变成你电脑上的mysql的用户名与密码<br/>
**端口**设置也在此处，但是需要配置额外依赖，在这里就是默认的***8080***端口


## 开发指北
### 项目结构(精简版)
```
mp-demo
  >.idea
  >src
    >main
      >java
      |    >com.itheima.mp
      |       >controller
      |       >domain
      |      |    >dto
      |      |    >info
      |      |    >po
      |      |    >vo
      |       >enums
      |       >mapper
      |       >payload
      |      |    >request
      |      |    >response
      |       >service
      |       >util
      |       MpDemoApplication.java
      >resources
         >mapper
         application.ymal
    >test     
  >target
  pom.xml    
```

### 配置类文件
**application.yaml**
*****
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/mp?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
```
- 数据库配置，url的意思就是本地的mysql服务，用的Asia/Shanghai上海的代理，driver-class-name，使用JDBC驱动
```yaml
  mail:
    #smtp服务主机  qq邮箱则为smtp.qq.com
    host: smtp.qq.com
    #服务协议
    protocol: smtp
    # 编码集
    default-encoding: UTF-8
    #发送邮件的账户
    username: 1704415077@qq.com
    #授权码
    password: "qvwucmvlflpfefbc"
    test-connection: true
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
```
- 发送邮件配置，用的是我个人的qq邮箱，不用改
```yaml
logging:
mybatis:
mybatis-plus:
```
- 这里是一些默认的配置，实现数据字典的json处理，主键自增等
```yaml
knife4j:
  enable: true
  openapi:
    title: 用户管理接口文档
    description: "用户管理接口文档"
    email: zhanghuyi@itcast.cn
    concat: CutePony
    url: https://www.itcast.cn
    version: v1.0.0
    group:
      default:
        group-name: default
        api-rule: package
        api-rule-resources:
          - com.itheima.mp.controller
```
- 方便的接口测试依赖
>将项目(MpDemoApplication)运行后,可以在<http://localhost:8080/doc.html#/home>中快速测试接口

**pom.xml**
******
配置依赖的的地方

### 最基本的CRUD实现逻辑
**service包、mapper包、po包间的联系**
*****
以***Student***为例：
- po包
```java
public class Student{}
```
PO(Persistent Object)持久化对象，与数据库表对应
- mapper包
```java
@Mapper
public interface StudentMapper extends BaseMapper<Student> {}
```
在这里面可自定义mysql的select语句实现某些方法

继承了BaseMapper，可使用其中已经定义好的**CRUD**方法，与StudentRepository类似

BaseMapper尖括号中就是定义好的持久化对象

- service.iservice包
```java
public interface IStudentService extends IService<Student> {}
```
继承 IService类，仅充当接口作用
- service.impl包
```java
public class StudentService extends ServiceImpl<StudentMapper, Student> implements IStudentService {}
```
继承ServiceImpl类以IStudentService接口(其实规范的命名为StudentServiceImpl)

**业务逻辑大多都在此处**
>以上就是一个数据库表所对应的配套的类，符合MybatisPlus规范，每增加一个表就需要增加相应的类

**domain.po详解**
*****
已经介绍了po为实体类，下面以Student.java为例
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "student")
public class Student {
    @TableId(type = IdType.AUTO, value = "student_id")
    @NotBlank
    private Integer studentId;

    private Integer userId;

    private String name;

    @EnumValue
    @TableField(typeHandler = EnumTypeHandler.class)
    private Major major;

    private Grade grade;

    private Double gpa;

    private Integer studentClass;

    @TableField(value = "rank_class")
    private Integer rankClass;

    @TableField(value = "rank_college")
    private Integer rankCollege;
}
```
- 注解
>@Data Lombok注解，用于生成所有所有属性的get、set方法
>
> @NoArgsConstructor @AllArgsConstructor 分别用于生成空参和带参数的构造方法
> 
> @TableName(value = "student") 标明对应的数据库表(若命名规范可以不加)
> 
> @TableId 标明主键名，必须有，因为studentMapper中的各种"ById"方法需要识别主键id，如果主键不命名为"id"，是识别不出来的<br/>
> @NotBlank 属性非空，也可以在数据库中直接设置<br/>
> @EnumValue @TableField(typeHandler = EnumTypeHandler.class) 枚举类型属性必备注解，后面枚举类型详细介绍

- 属性名与student表列名

studentId与数据库中student_id相对应<br/>
这是mybatisPlus的特性之一：属性名小驼峰，类名与标明均下划线命名<br/>
否则，很可能出现返回值为null的情况(被狠狠的折磨过)

- 属性类型<br/>
  - 主键，最好是Integer类型(Don't ask why)
  - 其他的最好就是对象类型 比如Integer,String,Double\
  - 枚举属性，例如Major，在数据库中是int类型

**do.vo包**
*****
以StudentVO为例
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    private Integer studentId;
    private User user;
    private Student student;
    private StudentBasic studentBasic;
    private StudentAdvanced studentAdvanced;
}
```
>VO(Value Object) 用于返回给前端的数据实体类

**enums与相关数据字典实现**
*****
***要完全按照下面的写法来***  别问为什么，问就是被折磨过
>枚举类型在项目中的作用主要就是数据字典的实现，很规范，突出语义特性
以UserType为例
```java
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType {
    ADMIN(1,"ADMIN"),
    STUDENT(2,"STUDENT"),
    TEACHER(3,"TEACHER");
    
    @EnumValue
    private Integer code;
    @JsonValue
    private String type;

    UserType(Integer code, String type){
        this.code=code;
        this.type=type;
    }

    @JsonCreator
    public static UserType getByCode(@JsonProperty("code") int code) {
        return Arrays.stream(UserType.values()).filter(item -> item.code == code).findFirst().get();
    }
}
```
新起的枚举类型实现，其实就是加了带参数的构造方法，而对象就是诸如ADMIN,STUDENT...<br/>
- @EnumValue<br/>
   用于标明该枚举类型定义的属性在数据库中的存储类型，在实体类定义中同样需要
```
@EnumValue
@TableField(typeHandler = EnumTypeHandler.class)
private Major major;
```
- @JsonValue<br/>
   用于标明返回给前端的类型
- getByCode方法，顾名思义，通过code属性返回UserType类型  

**mapper详解**
*****
又以StudentMapper为例
```java
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    //查询最大id
    @Select("SELECT MAX(student_id) FROM student")
    Integer findMaxStudentId();
    //寻找所给id位置，若没有，返回0
    @Select("SELECT COUNT(*) FROM student WHERE student_id = #{studentId}")
    int checkStudentId(Integer studentId);
}
```
- @Mapper 标明是一个Mapper
- @Select 标明是mysql的select语法的自定义语句

**impl详解**
*****
>值得庆幸的是，您在将以上说明阅读完毕后，已经足够理解相应的业务逻辑,
>下面请出我们的老朋友StudentService<br/>

```java
@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class StudentService extends ServiceImpl<StudentMapper, Student> implements IStudentService{}
```
- 类的头部，三个注解***缺一不可***
  - @Service标记为实现业务逻辑的类
  - @Configuration有这个注解才可以使用@Autowired注解实现接口等Bean的注入
  - @ComponentScan指定Spring在那个包下识别组件

```
@Autowired
private StudentMapper studentMapper;
```
- Service实现需要许多这样注入

```
@ApiModelProperty("添加一个学生，对其中username，姓名，班级，年级，邮箱格式进行判断")
    public DataResponse insertStudent(DataRequest dataRequest){
        Map map=dataRequest.getData();
        Integer userId=getNewUserId();//新的id
        Integer studentId =getNewStudentId();

        User user=getUserFromMap(map,userId);
        Student student=getStudentFromMap(map,studentId,userId);
        StudentBasic studentBasic=getStudentBasicFromMap(map,studentId);

        DataResponse dataResponse=baseService.judgeStudentDataInsert(user,student,studentBasic);
        if(dataResponse.getCode()==1)return dataResponse;

        userMapper.insert(user);
        studentMapper.insert(student);
        studentBasicMapper.insert(studentBasic);
        return CommomMethod.getReturnMessageOK("成功添加了一名学生");
    }
```

- 重要的方法，用于新增一个学生
>  1. 获取新的studentId,userId;
>  2. 通过诸如getUserFromMap方法将map转化为实体类型
>  3. 通过定义好的baseService.judgeStudentData方法判断数据格式是否符合规定
>  4. userMapper.insert(user);通过insert方法将新的行插入数据库
>>其他的实体类对应的service也都采取了这种模式
      
(有些方法类的细节先不赘述)

```
 public DataResponse updateStudent(DataRequest dataRequest){
        //对学生是否存在的判断
        Integer studentId=dataRequest.getInteger("studentId");
        Map map=dataRequest.getData();
        if(studentId==null)return CommomMethod.getReturnMessageError("数据传输格式错误");
        if(studentMapper.checkStudentId(studentId)==0){
            return CommomMethod.getReturnMessageError("该学生不存在");
        }
        //将数据库中的相应行取出存为实体类
        Student student=studentMapper.selectById(studentId);
        StudentBasic studentBasic=studentBasicMapper.selectById(studentId);
        Integer userId=student.getUserId();
        User user=userMapper.selectById(userId);
        String usernameOld=user.getUsername();

        //将前端所给的需要更新的数据存为实体类
        Student studentSource=getStudentFromMap(map);
        User userSource=getUserFromMap(map);
        StudentBasic studentBasicSource=getStudentBasicFromMap(map,studentId);

        //核心方法copyNullProperties，对于不为null或blank的属性更新到实体类中
        UpdateUtil.copyNullProperties(studentSource,student);//目标为student
        UpdateUtil.copyNullProperties(userSource,user);
        UpdateUtil.copyNullProperties(studentBasicSource,studentBasic);

        //定义好的格式判断
        DataResponse dataResponse=baseService.judgeStudentDataUpdate(user,student,studentBasic,usernameOld);
        if(dataResponse.getCode()==1)return dataResponse;
        //存入
        userMapper.updateById(user);
        studentMapper.updateById(student);
        studentBasicMapper.updateById(studentBasic);
        return CommomMethod.getReturnMessageOK("成功修改了学生信息");
 }
```
- 目前来说实现最复杂的业务逻辑方法，更新一个学生的有关数据
>对于更新学生信息的效率思考<br/>
>>我们知道，从数据库中存取数据是最耗时的，而UpdateWrapper提供了对于指定属性的数据更新方法，但是一个一个属性进行存储虽然代码可能会
>>稍显简洁，但无疑使降低了运行的效率，因此我们选择对每个数据库表只进行一次存取
> 
> 重要的方法UpdateUtil.copyNullProperties(Object source,Object target)
```
    public DataResponse deleteStudent(DataRequest dataRequest)
    public DataResponse selectStudent(DataRequest dataRequest)
```
- 删除和查询学生的方法

### util包
>用于定义一些实用的util方法
>

**CommonMethod.java**
*****

**FormatMethod**
*****
```java
public class FormatMethod {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
```
- 定义一些格式判断的方法，例如邮箱格式判断

**ImageMethod**
*****

**UpdateUtil**
*****
```java
public class UpdateUtil {
    //所有为空值的属性都不copy
    public static void copyNullProperties(Object source, Object target);
    //获取属性中为空的字段
    private static String[] getNullField(Object target);
    public static boolean judgeValue(Object value);
}
```
>copyNullProperties方法用于copy对象，但要保证属性一一对应

### controller

以StudentController为例
```java
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping("/insertStudent")
    public DataResponse editStudent(@RequestBody DataRequest dataRequest) {
        return studentService.insertStudent(dataRequest);
    }
    //.....省略其他接口
}
```
@Autowired注解是必须要写的，不然会出现空指针异常

### payload.response

**DataResponse**
*****
```java
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataResponse {
    private Integer code;
    private Object data;
    private String msg;
}
```
>标准的DataResponse


## 数据库设计





## 代码细节
### List类型与Map类型
- ***list***
```
List<StudentVO> studentVOList = new ArrayList<StudentVO>();
```
***yes***
```
List<StudentVO> studentVOList = null;
```
***no***
>会出现空指针异常的问题












