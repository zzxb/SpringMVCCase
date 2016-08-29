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



## 修改日志
- 2016-8-28:
- [x] 创建演示项目,并对一些配置文件进行了注解。

## 参考资源
以下是在编写案例中收集的资源,对深入理解与运用有帮助

- [使用IntelliJ IDEA开发SpringMVC网站](http://my.oschina.net/gaussik/blog/385697)


------
建议使用IntelliJ Idea开发工具。

