# ape-frame

一个神秘的脚手架

Druid监控地址：http://localhost:8080/druid/login.html

Swagger文档地址：http://localhost:8080/swagger-ui.html

------

### 项目结构

- **ape-frame（parent模块）**
  - **ape-common（通用模块服务）**
    - **ape-common-log（切面日志模块服务）**
    - **ape-common-mybatisPlus（mybatisPlus公共模块服务）**
    - **ape-common-web（请求、响应、异常工具模块服务）**
    - **ape-common-swagger（接口文档模块服务）**
    - **ape-common-redis（redis缓存模块服务）**
    - **ape-common-tool（公共工具类）**
  - **ape-user（用户模块服务）**
  - **ape-share（测试模块服务）**

------

### day01

1、环境准备 git（已经安装）jdk1.8（已经安装） maven（已经安装） postman（已经安装） 

2、建立git仓库 

3、集成springboot、springmvc 

4、linux docker安装mysql

### day02

1、服务模块拆解 

2、集成mybatis plus 

3、集成druid监控 

### day03

1、mybatis优化器的自动装载 

2、mybatis的实体优化 

3、全局异常捕获 

4、mybatisplus提供的拦截器集成

### day04

1、使用easycode插件，自动生成crud接口

2、 集成mapstruct映射器，通过set/get提高执行效率，减少实体之间转换的耦合度

### day05

1、整合Redis缓存，解决序列化的问题。

2、通过ApplicationContext，在项目启动时获取对应的bean对象，实现自动预热

### day06

1、封装一个分布式锁

2、注解缓存的方式

3、快捷注释设置

### day07

1、日志log4j集成，异步日志集成

- 创建log4j-spring.xml文件
- 配置文件加上 logging-config: classpath:log4j-spring.xml
- 在启动器上加上 `System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");`

2、安装Maven-Helper插件，解决依赖冲突。

3、aop切面日志，打印controller、service层的入参日志。

### day08

1、freemaker实现自定义word格式文件导出。

2、word文档需要转xml格式内容，可以通过spice.doc依赖实现doc对xml的转换。

3、ape-share 多线程生成mysql测试数据
