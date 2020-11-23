## project项目介绍

> 使用前请仔细阅读本文档

基于 SpringBoot 2.x 的 JavaEE 基础开发框架

本框架是基于SpringBoot开源技术，结合平时工作开发的经验，为了简化开发工作，提高开发效率，
自主研究集成的框架。

本框架细化了SpringBoot2.x在开发应用中的配置，并在本文档中介绍各种配置作用及修改所表示
的不同功能。


## project 框架的构成

本框架主要由SpringBoot2.x（Spring5，Spring MVC等技术）、数据库访问（Hibernate、Mybatis）、
安全框架（Spring Security）、页面模板（thymeleaf、freemarker）。

* Hibernate主要用来创建数据库实体，以实现自动建表的功能，包括简单的增删改查业务处理。
* Mybatis主要用于复杂的结果集查询，使用SQL注解与Provider方式处理较复杂的增删改查业务。
* Spring Security主要用于安全拦截与处理，自定义过滤器、验证处理器，包括同步与异步登录方式。
* 本框架使用Thymeleaf（类似JSP页面）作为前端页面的显示模板，其它框架需要用户自己修改配置，本框架暂不研究针对别的模板的集成。
* 本框架采用Java Config代码与注解的配置方式，摈弃xml配置方式，使用此框架的开发者须接受此约定。

框架中集成的相关技术如下所示：

* SpringMVC框架
* SpringIoc容器
* Hibernate数据库访问
* MyBatis plus数据库访问

## 从GITHUB上获取该项目

- 使用git命令获取项目：`git clone https://gitee.com/zxmusic/music.git`。
- 本框架使用gradle构建，关于gradle的安装，参考互联网。
- 使用gradle命令生成eclipse项目：`gradle eclipse`。需安装gradle插件。
- Intellij Idea可以直接打开工程。关闭Inceptions设置。
- 需要安装lombok工具插件（使注注解生成getter与setter方法），否则会报错。
- 框架使用内置H2数据库，构建好后可以直接运行，不需要任何其它操作，如果要使用其它数据库可修改c3p0.properties。
- 框架黙认超级用户superadmin/123456，可使用此用户名与密码登录访问权限控制页面。

## project 框架的目录使用约定
