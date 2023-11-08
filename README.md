# web课设后端
## 简介
### 框架MybatisPlus
****** 
 MyBatis-Plus (opens new window)（简称 MP）是一个 MyBatis (opens new window)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

突出特点为以Wrapper为“组先类”的数据库查询方法类

 
### 数据库mysql
*****
> 我的版本信息
>> Ver 8.0.32 for Win64 on x86_64 (MySQL Community Server - GPL)

推荐使用 ***DataGrip或IntelliJ IDEA Ultimate***打开**mp.sql**文件

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
**只需** 将username与password变成你电脑上的mysql的用户名与密码


## 开发指南
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

### 每个文件的作用
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

**pom.xml**
******
配置依赖的的地方

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
继承了BaseMapper，可使用其中已经定义好的CRUD方法，与StudentRepository类似

BaseMapper尖括号中就是定义好的持久化对象

- service.service包
```java
public interface IStudentService extends IService<Student> { }
```
继承 IService类
- service.impl包
```java
public class StudentService extends ServiceImpl<StudentMapper, Student> implements IStudentService {}
```
继承ServiceImpl类以IStudentService接口(其实规范的命名为StudentServiceImpl)
>以上就是一个数据库表所对应的配套的类，符合MybatisPlus规范，每增加一个表就需要增加相应的类


  





























