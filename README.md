# ape-frame

一个神秘的脚手架

轻量级快速开发框架，涵盖平时业务开发的常用场景，做到开箱即用。用户可根据自身情况选择组件来进行使用。采取组件化开发模式。

比如用户需要redis，则选择redis组件，需要websocket，则引入websocket组件，用户自身不需要再开发什么，只需要按照组件规则进行使用即可。

同时，有些经典的工具以及经典的设计模式代码，提供了大量实例参考，用户的业务场景一旦用到，就可以直接使用。

项目整体采用maven结构开发，封装了大量的模块，彼此解耦。满足用户日常开发需要。

Druid监控地址：http://localhost:8080/druid/login.html

Swagger文档地址：http://localhost:8080/swagger-ui.html

------

### 项目结构

- **ape-frame（parent模块）**
  - **ape-code-generator（MP自定义代码生成服务）**
  - **ape-common（通用模块服务）**
    - **ape-common-log（切面日志模块服务）**
    - **ape-common-mybatisPlus（mybatisPlus公共模块服务）**
    - **ape-common-web（请求、响应、异常工具模块服务）**
    - **ape-common-swagger（接口文档模块服务）**
    - **ape-common-redis（redis缓存模块服务）**
    - **ape-common-tool（公共工具类）**
    - **ape-common-job（xxl-job任务调度服务）**
    - **ape-common-test（单元测试服务）**
    - **ape-websocket（http通讯服务）**
  - **ape-user（用户模块服务）**
  - **ape-share（测试模块服务）**
  - **ape-design-mode（设计模式demo服务）**
  - **ape-sharding-jdbc（分库分表demo服务）**


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

3、ape-share 多线程生成mysql测试数据。

### day09

1、模板模式、工厂模式、抽象工厂模式、策略模式实战

2、工厂+策略模式实战

### day10

1、封装一个cacheUtil，函数式编程+泛型+guava本地缓存

2、websocket集成+鉴权

### day11

1、maven的profile实现环境隔离

2、springboot插件打包部署

3、httpclient优化

4、建造者、过滤器模式实战（过滤器内容有问题！）

### day12

1、集成springbootTest

2、服务启动预热（ApplicationListener）

3、controller入参时间格式转化

4、xxl-job集成springboot

### day13

1、获取接口请求时间，可修改请求参数、响应参数（注解aop式、过滤器式）

2、mp自定义代码生成、自定义模板。

3、sharding-jdbc分库分表实战。

4、自定义线程池。

5、future异步封装，可指定请求超时异常。

### day14

1、spring的event事件驱动（未实现）

2、Redis lua实现cas。

3、实现链路追踪。