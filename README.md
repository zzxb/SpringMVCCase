这是一个SpringMVC设计的教学案例，只为提供为不了解SpringMVC技术的人员提供学习参考。
- SpringMVC
- Spring Core
- Hibernate
- 阿里数据源
- MySQL5.6

## 名词解释

#### SpringMVC干什么用？

我的理解是：SpringMVC是替代Struts框架的Spring的一个模块。它比Struts框架更加简单,同时,也与Spring结合的非常简单。目前,大部分企业新的项目都是以SpringMVC为控制层设计的。<br/>

#### DispatcherServlet类的作用？

我的理解是：SpringMVC中核心控制器,其实,它就是一个标准的Servlet类<br/>

#### mvc-dispather-servlet.xml配置是干什么用？

这是SpringMVC中核心的配置文件,它的命名与web.xml中的DispatcherServlet类配置有关,一般的命名方式[servlet-name]-servlet.xml。<br/>


#### 图解SpringMVC运作流程？

详见：other目录下的图片


## 快速开始
1. 安装Maven
2. Maven编译
3. 在MySQL数据库创建SkDB数据库,再执行other/db/init.sql文件,创建相关表.
4. 将druid.jar,mysql数据库驱动两个包,放入tomcat中lib目录中。
5. 配置Tomcat的数据源,即context.xml文件,示例配置信息,可见other/db/数据源配置信息.txt
6. 重启tomcat
7. 快速部署
8. 测试

## 使用
地址:http://localhost:8080/SpringMVCCase/ <br/>

## 开发者

- zzxb


## 版本

V 1.0.1

## 核心说明

#### 关于创建一个基于SpringMVC框架的项目基本流程：

1.导入相关SpringMVC依赖的包(建议使用Maven的方式构建整个工程).

2.配置web.xml文件,将DispatcherServlet配置

```
  <servlet>
    <servlet-name>mvc-dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>mvc-dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

```

3.同时，配置过滤字符集的Filter,在web.xml中

```
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

```

4.创建一个根package,例如：me.zzxb.service/controller/domain/....

5.创建一个名为[servlet-name]-servlet.xml,例如：mvc-dispatcher-servlet.xml在WEB-INF目录下。

6.配置基本的配置信息,例如：

```xml
    <!--扫描所有具有注解的类-->
    <context:component-scan base-package="me.zzxb.*"/>
    <!--静态资源访问-->
    <mvc:default-servlet-handler/>
    <context:annotation-config />
    <!--开启注解模式-->
    <mvc:annotation-driven/>

    <bean id="jspViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```

7.创建控制层，模型层等模块，注意，控制层要加@Controller,模型层为@Service

#### 关于一些常用注解

1.@Controller,表示该类为控制层类

2.@Service,表示该类为模型层

3.@RequestMapping:是在控制层中的核心注解，比如：

```java

@RequestMapping(value = "/main",method = RequestMethod.GET)

```

value为请求地址，method为可接受请求类型(可省略),同时，Controller类也可以，使用@RequestMapping注解

4.@RequestParam:指定请求参数名

5.@ResponseBody:指定响应报文体为JSON类型

6.@PathVariable:制定路径类型的参数名

例如：

```java

    @RequestMapping(value = "/del/{uid}",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> delUsers(@PathVariable String uid)

```

#### 关于com.fasterxml.jackson.databind.JsonMappingException: could not initialize proxy - no Session错误的解决

这个错误出现的原因是在一对多情况下的查询,例如:一个Users对应多个Lxrs,那么当根据userid/uname获得其所有联系人时,会出现该错误。<br/>

那么解决方案是如下:

1.引入jackson-datatype-hibernate4第三方类库,Maven如下:<br/>

```xml
    <dependency>
      <groupId>com.fasterxml.jackson.datatype</groupId>
      <artifactId>jackson-datatype-hibernate4</artifactId>
      <version>2.7.4</version>
    </dependency>
```

2.创建一个自定义类,例如:MyConfigClass类<br/>

```java
public class MyConfigClass extends ObjectMapper {

    public MyConfigClass() {
        registerModule(new Hibernate4Module());
    }
}
```

3.在SpringMVC核心配置文件中(mvc-dispatcher-servlet.xml),配置如下信息:<br/>

```xml
    <mvc:annotation-driven>
        <mvc:message-converters>
            <!-- Use the HibernateAware mapper instead of the default -->
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="me.zzxb.tools.MyConfigClass" />
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

#### 关于引入Hibernate4版本之后,SpringMVC操作非查询操作时,需要提供Spring AOP等事物操作。<br/>

1.引入相关依赖.Maven<br/>

```xml
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>4.1.6.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
      <version>4.1.6.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>4.1.6.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>4.1.6.RELEASE</version>
    </dependency>
```
2.在applicationContext.xml文件中,配置事物处理。<br/>

```xml

    <bean id="transactionManager" class="org.springframework.orm.hibernate4.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"/>
    </bean>
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="save*" propagation="REQUIRED" />
            <tx:method name="add*" propagation="REQUIRED" />
            <tx:method name="update*" propagation="REQUIRED" />
            <tx:method name="del*" propagation="REQUIRED" />
            <tx:method name="find*" propagation="REQUIRED" read-only="true" />
            <tx:method name="get*" propagation="REQUIRED" read-only="true" />
            <tx:method name="page*" propagation="REQUIRED" read-only="true" />
            <tx:method name="createHQLQuery" propagation="REQUIRED" />
            <tx:method name="createSQLQuery" propagation="REQUIRED" />
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <!--* me.zzxb.service.*.*(..)的意思是:me.zzxb.service包下的所有类的所有方法-->
        <aop:pointcut id="txPointcut" expression="execution(* me.zzxb.service.*.*(..))" />
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>
    <tx:annotation-driven transaction-manager="transactionManager"/>

```

3.在mvc-dispatcher-servlet.xml文件中引入applicationContext.xml<br/>

```xml
    <import resource="classpath:applicationContext.xml"/>
```

#### 关于控制层(controller)返回JSON类型(@ResponseBody)

返回JSON类型需要引入jackson相关包的引入,Maven

```xml
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-databind</artifactId>
          <version>${jacksonForJSON.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
        <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-core</artifactId>
          <version>${jacksonForJSON.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations -->
        <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-annotations</artifactId>
          <version>${jacksonForJSON.version}</version>
        </dependency>
```


一般，返回的类型为:Map<String,Object>，SpringMVC会自动将其转换为JSON格式。

建议：返回的消息体中至少包含两部分：状态部分以及数据体(state/data)

#### 关于JNDI的配置

1.首先，在Tomcat中的lib目录中添加数据库驱动包和连接池包

2.配置conf/context.xml文件

```

<Resource
        name="jdbc/skmysql"
        factory="com.alibaba.druid.pool.DruidDataSourceFactory"
        auth="Container"
        type="javax.sql.DataSource"
        driverClassName="com.mysql.jdbc.Driver"
        url="jdbc:mysql://127.0.0.1:3306/skdb?useUnicode=true&amp;characterEncoding=utf-8"
        username="root"
        password="qaz123"
        initialSize="5"
        maxActive="200"
        maxWait="60000"
        minIdle="1"
        timeBetweenEvictionRunsMillis="60000"
        minEvictableIdleTimeMillis="300000"
        testWhileIdle="true"
        testOnBorrow="false"
        testOnReturn="false"
        poolPreparedStatements="true"
        maxOpenPreparedStatements="20"
        filters="stat"/>

```

3.配置需要引入JNDI的项目中的web.xml

```
  <resource-ref>
    <description>MySQL DB Connection</description>
    <res-ref-name>jdbc/skmysq</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
  </resource-ref>
```

注意：res-ref-name标签值要与context文件name值一致。

比如：

```
<Resource
        name="jdbc/skmysql"
```

```
  <resource-ref>
    <res-ref-name>jdbc/skmysq</res-ref-name>
```

#### 关于Restful中标准请求（GET,POST,PUT,DELETE）

1.GET用于处理查询

2.POST用于新增

3.PUT用于更新

4.DELETE用于删除

请求消息体的格式：/users/delete/{参数值}


## 修改日志
- 2016-8-28:
- [x] 创建演示项目,并对一些配置文件进行了注解。
- 2016-9-13:
- [x] 新增了对SpringMVC基本流程步骤的说明
- 2016-9-18:
- [x] 新增了Restful标准请求(GET,POST,PUT,DELETE)的处理
- 2016-10-26:
- [x] 新增了对HttpSession，控制层重定向以及JSTL的支持

## 参考资源
以下是在编写案例中收集的资源,对深入理解与运用有帮助

- [使用IntelliJ IDEA开发SpringMVC网站](http://my.oschina.net/gaussik/blog/385697)


------
建议使用IntelliJ Idea开发工具。

