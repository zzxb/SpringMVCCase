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

## 修改日志
- 2016-8-28:
- [x] 创建演示项目,并对一些配置文件进行了注解。

## 参考资源
以下是在编写案例中收集的资源,对深入理解与运用有帮助

- [使用IntelliJ IDEA开发SpringMVC网站](http://my.oschina.net/gaussik/blog/385697)


------
建议使用IntelliJ Idea开发工具。

