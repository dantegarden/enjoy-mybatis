# enjoy-mybatis
此项目是个人用来研究mybatis-3源码时随手写的。对核心配置和功能都做了注释和示例

可以配合本人仓库中的带注释的mybatis源码来使用

[mybatis-3](https://github.com/dantegarden/mybatis-3)

### 项目结构说明
+ mybatis-quickstart： mybatis3的使用（单表查询、关联查询、xml和注解方法、代码生成器、插件开发、缓存）
    + 该项目为maven工程，在test包下提供了示例
- mybatis-spring：spring集成mybatis，关于mybatis-spring的原理，未来将在博客说明。
- base-jdbc：纯jdbc示例。mybatis底层还是jdbc，这里示范了jdbc的标准流程
- my-orm：学习mybatis的思路，从头开始手写一个orm框架
    + 自定义配置文件，并解析配置文件
    + 内置数据源使用DruidDataSource
    + 使用自定义的SqlSessionFactory来进行初始化
    + 使用自定义的Executor来操作数据库
    + 自定义参数绑定方法，实现自动映射
    + 直接使用了mybatis的reflection包