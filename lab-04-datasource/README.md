# Springboot数据库多数据源、读写分离、分库分表解决方案

# 数据库高性能

互联网业务兴起之后，海量用户加上海量数据的特点，单个数据库服务器已经难以满足业务需要，必须考虑数据库集群的方式来提升性能。高性能数据库集群的`第一种方式是“读写分离”`，`第二种方式是“数据库分片”`。

## 1、读写分离

**读写分离的基本实现：**

-  `主库负责处理事务性的增删改操作，从库负责处理查询操作`，能够有效的避免由数据更新导致的行锁，使得整个系统的查询性能得到极大的改善。
-  读写分离是`根据 SQL 语义的分析`，`将读操作和写操作分别路由至主库与从库`。
-  通过`一主多从`的配置方式，可以将查询请求均匀的分散到多个数据副本，能够进一步的提升系统处理能力。 
-  使用`多主多从`的方式，不但能够提升系统的吞吐量，还能够提升系统的可用性，可以达到在任何一个数据库宕机，甚至磁盘物理损坏的情况下仍然不影响系统的正常运行。

## 2、数据库分片

**读写分离的问题：**读写分离分散了数据库读写操作的压力，但没有分散存储压力，为了满足业务数据存储的需求，就需要`将存储分散到多台数据库服务器上`。

**数据分片：**将存放在单一数据库中的数据分散地存放至多个数据库或表中，以达到提升性能瓶颈以及可用性的效果。 数据分片的有效手段是对关系型数据库进行`分库和分表`。数据分片的拆分方式又分为`垂直分片和水平分片`。

### 2.1、垂直分片

**垂直分库：**`按照业务拆分的方式称为垂直分片，又称为纵向拆分`，它的核心理念是专库专用。 在拆分之前，一个数据库由多个数据表构成，每个表对应着不同的业务。拆分之后，按照业务将表进行归类，分布到不同的数据库中，从而将压力分散至不同的数据库。 

* 概念：以**表**为依据，按照业务归属不同，将不同的**表**拆分到不同的**库**中。
* 结果：
  * 每个**库**的**结构**都不一样；
  * 每个**库**的**数据**也不一样，没有交集；
  * 所有**库**的**并集**是全量数据；
* 场景：系统绝对并发量上来了，并且可以抽象出单独的业务模块。

**垂直分表：**垂直分表适合将表中某些不常用的列，或者是占了大量空间的列拆分出去。

* 概念：以**字段**为依据，按照字段的活跃性，将**表**中字段拆到不同的**表**（主表和扩展表）中。

* 结果：

  * 每个**表**的**结构**都不一样；

  * 每个**表**的**数据**也不一样，一般来说，每个表的**字段**至少有一列交集，一般是主键，用于关联数据；

  * 所有**表**的**并集**是全量数据；

* 场景：系统绝对并发量并没有上来，表的记录并不多，但是字段多，并且热点数据和非热点数据在一起，单行数据所需的存储空间较大。以至于数据库缓存的数据行减少，查询时会去读磁盘数据产生大量的随机读IO，产生IO瓶颈。

### 2.2、水平分片

**水平分库：**如果单表拆分为多表后，单台服务器依然无法满足性能要求，那就需要将多个表分散在不同的数据库服务器中。

* 概念：以**字段**为依据，按照一定策略（hash、range等），将一个**库**中的数据拆分到多个**库**中。

* 结果：

  * 每个**库**的**结构**都一样；

  * 每个**库**的**数据**都不一样，没有交集；

  * 所有**库**的**并集**是全量数据；

* 场景：系统绝对并发量上来了，分表难以根本上解决问题，并且还没有明显的业务归属来垂直分库。

**水平分表：**单表切分为多表后，新的表即使在同一个数据库服务器中，也可能带来可观的性能提升，如果性能能够满足业务要求，可以不拆分到多台数据库服务器，毕竟业务分库也会引入很多复杂性；

* 概念：以**字段**为依据，按照一定策略（hash、range等），将一个**表**中的数据拆分到多个**表**中。

* 结果：

  * 每个**表**的**结构**都一样；

  * 每个**表**的**数据**都不一样，没有交集；

  * 所有**表**的**并集**是全量数据；

* 场景：系统绝对并发量并没有上来，只是单表的数据量太多，影响了SQL效率，加重了CPU负担，成为瓶颈。

## 3、实现方式

**编码层：**同一个项目中创建多个数据源，采用`if` `else`的方式，直接根据条件在代码中路由。Spring中有动态切换数据源的抽象类，具体参见`AbstractRoutingDataSource`。

**框架层：**通过实现一些拦截器（比如`Mybatis`的`Interceptor`接口），增加一些自定义解析来控制数据的流向，效果虽然较好，但会改变一些现有的编程经验。

**驱动层：**重新编写了一个`JDBC`的驱动，在内存中维护一个路由列表，然后将请求转发到真正的数据库连接中。ShardingSphere-JDBC、TDDL等，是在此层切入。

**代理层：**代理层的数据库中间件，将自己伪装成一个数据库，接受业务端的链接。然后负载业务端的请求，解析或者转发到真正的数据库中。ShardingSphere-Proxy、MyCat等，是在此层切入。

# ShardingSphere

Apache ShardingSphere 是一款分布式的数据库生态系统， 可以将任意数据库转换为分布式数据库，并通过数据分片、弹性伸缩、加密等能力对原有数据库进行增强。

## ShardingSphere-JDBC

ShardingSphere-JDBC 定位为轻量级 Java 框架，在 Java 的 JDBC 层提供的额外服务。它使用客户端直连数据库，以 jar 包形式提供服务，无需额外部署和依赖，可理解为增强版的 JDBC 驱动，完全兼容 JDBC 和各种 ORM 框架。

## ShardingSphere-Proxy

ShardingSphere-Proxy 定位为透明化的数据库代理端，通过实现数据库二进制协议，对异构语言提供支持。目前提供 MySQL 和 PostgreSQL 协议，透明化数据库操作，对 DBA 更加友好。

|            | ShardingSphere-JDBC | ShardingSphere-Proxy |
| :--------- | :------------------ | -------------------- |
| 数据库     | `任意`              | MySQL/PostgreSQL     |
| 连接消耗数 | `高`                | 低                   |
| 异构语言   | `仅 Java`           | 任意                 |
| 性能       | `损耗低`            | 损耗略高             |
| 无中心化   | `是`                | 否                   |
| 静态入口   | `无`                | 有                   |

ShardingSphere-JDBC，相比 ShardingSphere-Proxy 来说，是基于 client 模式，无需经过 proxy 一层的性能损耗，也不用考虑 proxy 的高可用。ShardingSphere-JDBC 对于 Java 项目来说，更推荐使用。目前主流一般都采用 client 模式的分库分表中间件。本文是在使用 Spring Boot 的情况下，使用ShardingSphere-JDBC分库分表的入门文章， ShardingSphere 提供的其它功能并未去编写，大家可以自己按照官方文档根据需求使用。

# 多数据源

多数据源：一个复杂的单体项目，因为没有拆分成不同的服务，需要连接**多个业务**的数据源。

## 实现方案

* **基于 Spring `AbstractRoutingDataSource` 做拓展**。
  * 通过继承 AbstractRoutingDataSource 抽象类，实现一个管理项目中多个 DataSource 的**动态** DynamicRoutingDataSource 实现类。Spring 在获取数据源时，可以通过 DynamicRoutingDataSource 返回**实际**的 DataSource 。
  * 本次使用 baomidou 提供的 `dynamic-datasource-spring-boot-starter`，代码参考datasource-baomidou-01

* **不同操作类，固定数据源**。
  * 以 MyBatis 举例子，假设有 `orders` 和 `users` 两个数据源。 那么可以创建两个 SqlSessionTemplate `ordersSqlSessionTemplate` 和 `usersSqlSessionTemplate` ，分别使用这两个数据源。配置不同的 Mapper 使用不同的 SqlSessionTemplate 。整个过程就变成，执行数据操作时，通过 Mapper 可以对应到其 SqlSessionTemplate ，使用 SqlSessionTemplate 获得**对应的实际的** DataSource 。再通过该 DataSource 获得 Connection 连接，最后发起数据库操作。
  * 代码参考datasource-mybatis

* **分库分表中间件**。
  * 分库分表的中间件，会解析编写的 SQL ，路由操作到对应的数据源。我们仅需配置好每个表对应的数据源，中间件就可以**透明**的实现多数据源、读写分离、分库分表。
  * 本次使用`Apache ShardingSphere-JDBC` ，代码参考datasource-sharding-jdbc-01

## 1、多数据源：datasource-baomidou-01

### 1.1 引入依赖

在 `pom.xml`文件中，引入相关依赖

### 1.2 Application

创建 `Application.java`类

### 1.3 应用配置文件

在 `resources`目录下，创建 `application.yaml` 配置文件

### 1.4 MyBatis 配置文件

在`resources` 目录下，创建 `mybatis-config.xml `配置文件

### 1.5 创建相关文件

创建对应表

创建 `UserDO.java `和` OrderDO.java `类

创建 ` OrderMapper.java `和 `UserMapper.java` 接口

创建 `OrderMapper.xml ` 和`UserMapper.xml`配置文件

创建 `OrderService.java`类

### 1.6 测试

创建 `UserMapperTest`和 `OrderMapperTest`测试类，测试数据库连接

创建 `OrderServiceTest`测试类

**场景一：`method01()`**

* 方法未使用 @Transactional 注解，不会开启事务。对于 OrderMapper 和 UserMapper 的查询操作，分别使用其接口上的 @DS 注解，找到对应的数据源，正常执行

**场景二：`method02()`**

* 和 method01() 方法相比，增加了 @Transactional 注解，声明要使用 Spring 事务。抛出异常：Table 'test_users.orders' doesn't exist。
 * Spring 事务的实现机制。方法添加了 `@Transactional` 注解，Spring 事务生效。Spring `TransactionInterceptor`会通过 AOP 拦截该方法，创建事务。创建事务，获得数据源。TransactionInterceptor 会使用 Spring `DataSourceTransactionManager` 创建事务，并将事务信息通过 ThreadLocal 绑定在当前线程。
   * 事务信息，包括事务对应的 Connection 连接。意味着，还没走到 OrderMapper 的查询操作，Connection 就已经被创建出来。因为事务信息会和当前线程绑定在一起，在 OrderMapper 在查询操作需要获得 Connection 时，就直接拿到当前线程绑定的 Connection ，而不是 OrderMapper 添加 `@DS` 注解所对应的 DataSource 所对应的 Connection 。
   * DataSourceTransactionManager 数据库事务管理器，创建时会传入其需要管理的 DataSource 数据源。在使用 `dynamic-datasource-spring-boot-starter` 时，它创建了一个 DynamicRoutingDataSource ，传入到 DataSourceTransactionManager 中。DynamicRoutingDataSource 负责管理我们配置的多个数据源，本例中就管理了 `orders`、`users` 两个数据源，并且默认使用 `users` 数据源。DynamicRoutingDataSource 需要基于 `@DS` 获得数据源名，从而获得对应的 DataSource ，因为我们在 Service 方法上，并没有添加 `@DS` 注解，所以返回默认数据源，也就是 `users` 。故此，就发生了 `Table 'test_users.orders' doesn't exist` 的异常。

**场景三：`method03()`**

* 场景三和场景二相同。

* `self()` 代码替换成 `this` 之后，结果会正常执行。这又是为什么？

  调整后， `this` 不是代理对象， `#method031()` 和 `#method032()` 方法上的 `@Transactional` 直接没有作用，Spring 事务没有生效。所以，最终结果和场景一是相同的。

**场景四：`method04()`**

* 和 `method03()` 方法，`method041()` 和 `method042()` 方法，添加 `@DS` 注解，声明对应使用的 DataSource 。执行方法，正常结束，未抛出异常。

  * 执行 `method041()` 方法前，有 `@Transactional` 注解， Spring 事务机制触发。DynamicRoutingDataSource 根据 `@DS` 注解，获得对应的 `orders` 的 DataSource ，获得 Connection 。后续 OrderMapper 执行查询操作时，使用的是线程绑定的 Connection ，所以没有报错。实际上，此时 OrderMapper 上的 `@DS` 注解，也没有作用。

  * `method042()` ，也是同理。但是上面提了 Connection 会绑定在当前线程？那么，在 `method042()` 方法中，应该使用的是 `method041()` 的 `orders` 对应的 Connection 呀，应该报错才对。实际是在 Spring 事务机制中，在一个事务执行完成后，会将事务信息和当前线程解绑。在执行 `method042()` 方法前，又执行了一轮事务的逻辑。

* 总结 声明`@Transactional` 的 Service 方法上，可以通过 `@DS` 声明对应的数据源。

**场景五：`method05()`**

* 和 `method04()` 方法，差异在于，我们直接在 `method05()` 方法中，**此时处于一个事务中**，直接调用了 `method052()` 方法。执行方法，正常结束，未抛出异常。
* `method052()` 方法，添加的 `@Transactionl` 注解，事务传播级别是 `Propagation.REQUIRES_NEW` 。在执行 `#method052()` 方法之前，TransactionInterceptor 会将原事务**挂起**，暂时性的将原事务信息和当前线程解绑。
  - 所以执行 `#method052()` 方法前，又可以执行一轮事务的逻辑。
  - 在执行 `#method052()` 方法完成后，会将原事务**恢复**，重新将原事务信息和当前线程绑定。

* 使用默认事务传播级别是 `Propagation.REQUIRED`

## 2、多数据源：datasource-mybatis

### 2.1 引入依赖

在 `pom.xml`文件中，引入相关依赖

### 2.2 Application

创建 `Application.java`类，没有配置 `@MapperScan` 注解，手动配置`2.5配置类`

### 2.3 应用配置文件

在 `resources`目录下，创建 `application.yaml` 配置文件

### 2.4 MyBatis 配置文件

在`resources` 目录下，创建 `mybatis-config.xml `配置文件

### 2.5 MyBatis 配置类

`MyBatisOrdersConfig`配置类，配置使用 `orders` 数据源的 MyBatis 配置

`MyBatisUsersConfig`配置类，配置使用 `users` 数据源的 MyBatis 配置

### 2.6 创建相关文件

创建对应表

创建 `UserDO.java `和` OrderDO.java `类

创建 ` OrderMapper.java `和 `UserMapper.java` 接口

创建 `OrderMapper.xml ` 和`UserMapper.xml`配置文件

创建 `OrderService.java`类

### 2.7 测试

创建 `UserMapperTest`和 `OrderMapperTest`测试类，测试数据库连接

创建 `OrderServiceTest`测试类

**场景一：`method01()`**

- 方法未使用 `@Transactional` 注解，不会开启事务。
- 对于 OrderMapper 和 UserMapper 的查询操作，分别使用其接口对应的 SqlSessionTemplate ，找到对应的数据源，执行操作。

**场景二：`method02()`**

方法上增加了 `@Transactional` 注解，声明要使用 Spring 事务。

执行方法，抛出如下异常：NoUniqueBeanDefinitionException: No qualifying bean of type 'org.springframework.transaction.PlatformTransactionManager。

*  `@Transactional` 注解上，未设置使用的事务管理器，系统会去选择一个事务管理器。但这里创建了 `ordersTransactionManager` 和 `usersTransactionManager` 两个事务管理器。此时，系统只好抛出 NoUniqueBeanDefinitionException 异常。

**场景三：`method03()`**

- `method031()` 和 `method032()` 方法上，声明的事务管理器。执行方法，正常结束，未抛出异常。

## 3、多数据源：datasource-sharding-jdbc-01

### 3.1 引入依赖

在 `pom.xml`文件中，引入相关依赖

### 3.2 Application

创建 `Application.java`类

### 3.3 应用配置文件

在 `resources`目录下，创建 `application.yaml` 配置文件

### 3.4 MyBatis 配置文件

在`resources` 目录下，创建 `mybatis-config.xml `配置文件

### 3.5 创建相关文件

创建对应表

创建 `UserDO.java `和` OrderDO.java `类

创建 ` OrderMapper.java `和 `UserMapper.java` 接口

创建 `OrderMapper.xml ` 和`UserMapper.xml`配置文件

创建 `OrderService.java`类

### 3.6 测试

创建 `UserMapperTest`和 `OrderMapperTest`测试类，测试数据库连接

创建 `OrderServiceTest`测试类

**场景一：`method01()`**

正常执行

**场景二：`method02()`**

正常执行，未抛出异常。

* **分库分表中间件返回的 Connection 返回的实际是动态的 DynamicRoutingConnection ，它管理了整个请求（逻辑）过程中，使用的所有的 Connection ，而最终执行 SQL 的时候，DynamicRoutingConnection 会解析 SQL ，获得表对应的真正的 Connection 执行 SQL 操作**。即使在和 Spring 事务结合的时候，会通过 ThreadLocal 的方式将 Connection 和当前线程进行绑定。此时这个 Connection 也是一个 动态的 DynamicRoutingConnection 连接。

**场景三：`method03()`**

正常执行

**场景四：`method04()`**

正常执行。

**场景五：`method05()`**

正常执行

# 读写分离

本质上，读写分离，仅仅是多数据源的一个场景，从节点是只提供读操作的数据源。实现了多数据源的功能，也就能够实现读写分离。

## 实现方案

* **基于 Spring `AbstractRoutingDataSource` 做拓展**。
  * 代码参考datasource-baomidou-02

* **不同操作类，固定数据源**。
  * 和多数据源类似的思路。只是将**从库**作为一个“**特殊**”的数据源，相比基于 Spring AbstractRoutingDataSource 做拓展来说，更加麻烦。且有多从情况下，不好处理，在选型时，方案一会**优**于方案二，被更普遍的采用

* **分库分表中间件**。
  * 代码参考datasource-sharding-jdbc-02

## 4、读写分离：datasource-baomidou-02

### 4.1 引入依赖

在 `pom.xml`文件中，引入相关依赖

### 4.2 Application

创建 `Application.java`类

### 4.3 应用配置文件

在 `resources`目录下，创建 `application.yaml` 配置文件

* 在`dynamic-datasource-spring-boot-starter`中，多个相同角色的数据源可以形成一个数据源组。判断标准是，数据源名以下划线`_`分隔后的首部即为组名。

  例如说，`slave_1`和`slave_2`形成了`slave`组。

  - 可以使用 `@DS("slave_1")` 或 `@DS("slave_2")` 注解，明确访问数据源组的指定数据源。
  - 也可以使用 `@DS("slave")` 注解，此时会负载均衡，选择分组中的某个数据源进行访问。目前，负载均衡默认采用轮询的方式。

### 4.4 MyBatis 配置文件

在`resources` 目录下，创建 `mybatis-config.xml `配置文件

### 4.5 创建相关文件

创建对应表

创建 ` OrderDO.java `类

创建 ` OrderMapper.java `接口

创建 `OrderMapper.xml `配置文件

创建 `OrderService.java`类

### 4.6 测试

本地MySQL数据库模拟一主多从的环境，创建了 `test_orders_01`、`test_orders_02` 库，手动模拟作为 `test_orders` 的从库。

```sql
主库：[id = 1, user_id = 1]
从库 01：[id = 1, user_id = 2]
从库 02：[id = 1, user_id = 3]
```

**测试一：`OrderMapperTest`类testSelectById()**

* `slave` 分组负载均衡输出，从库读操作正常

**测试二：`OrderMapperTest`类testInsert()**

* 主库插入数据，主库写操作正常

**测试三：`OrderServiceTest`类testAdd()**

* `@Transactional` 声明的事务中，读操作也访问主库，声明了 `@DS(DBConstants.DATASOURCE_MASTER)` 。因此，后续的所有 OrderMapper 的操作，都访问的是订单库的 `MASTER` 数据源。

## 5、读写分离：datasource-sharding-jdbc-02

### 5.1 引入依赖

在 `pom.xml`文件中，引入相关依赖

### 5.2 Application

创建 `Application.java`类

### 5.3 应用配置文件

在 `resources`目录下，创建 `application.yaml` 配置文件

### 5.4 MyBatis 配置文件

在`resources` 目录下，创建 `mybatis-config.xml `配置文件

### 5.5 创建相关文件

创建对应表

创建 ` OrderDO.java `类

创建 ` OrderMapper.java `接口

创建 `OrderMapper.xml `配置文件

创建 `OrderService.java`类

### 5.6 测试

本地MySQL数据库模拟一主多从的环境，创建了 `test_orders_01`、`test_orders_02` 库，手动模拟作为 `test_orders` 的从库。

```sql
主库：[id = 1, user_id = 1]
从库 01：[id = 1, user_id = 2]
从库 02：[id = 1, user_id = 3]
```

**测试一：`OrderMapperTest`类testSelectById()**

* `slave` 分组负载均衡输出，从库读操作正常

**测试二：`OrderMapperTest`类testSelectById2()**

* 开启事务，读取的是主库。
  * 事务中的数据读写均用主库；

**测试三：`OrderMapperTest`类testSelectById3()**

* 基于 Hint 的强制主库路由，读取的是主库。

**测试四：`OrderMapperTest`类testInsert()**

* 主库插入数据，主库写操作正常

**测试五：`OrderServiceTest`类testAdd()**

* `@Transactional` 声明的事务中，读操作也访问主库。

# 分库分表

**分库分表中间件**

* 本次使用`Apache ShardingSphere`，代码参考datasource-sharding-jdbc-03

* 本地模拟`orders` 订单表，拆分到 **2** 个库，每个库 **4** 个订单表，一共 **8** 个表。
  * 偶数后缀的表，在 `test03_orders_0` 库下。
  * 奇数后缀的表，在 `test03_orders_1` 库下。

## 相关概念

**逻辑表：**相同结构的水平拆分数据库（表）的逻辑名称，是 SQL 中表的逻辑标识。例：订单数据根据主键尾数拆分为 10 张表，分别是 t_order_0 到 t_order_9，他们的逻辑表名为 t_order。

**真实表：**在水平拆分的数据库中真实存在的物理表。即上个示例中的 t_order_0 到 t_order_9。

**绑定表：**指分片规则一致的一组分片表。使用绑定表进行多表关联查询时，必须使用分片键进行关联，否则会出现笛卡尔积关联或跨库关联，从而影响查询效率。例如：t_order 表和 t_order_item 表，均按照 order_id分片，并且使用 order_id 进行关联，则此两张表互为绑定表关系。绑定表之间的多表关联查询不会出现笛卡尔积关联，关联查询效率将大大提升。

**广播表：**指所有的分片数据源中都存在的表，表结构及其数据在每个数据库中均完全一致。适用于数据量不大且需要与海量数据的表进行关联查询的场景，例如：字典表。

**单表：**指所有的分片数据源中仅唯一存在的表。适用于数据量不大且无需分片的表。

## 6、分库分表：datasource-sharding-jdbc-03

### 6.1 引入依赖

在 `pom.xml`文件中，引入相关依赖

### 6.2 Application

创建 `Application.java`类

### 6.3 应用配置文件

在 `resources`目录下，创建 `application.yaml` 配置文件

### 6.4 MyBatis 配置文件

在`resources` 目录下，创建 `mybatis-config.xml `配置文件

### 6.5 创建相关文件

创建对应表

创建 ` OrderDO.java `，`OrderConfigDO.java`、`DictConfigDO`类

创建 ` OrderMapper.java `，` OrderConfigMapper.java `、`DictConfigMapper.java`接口

创建 `OrderMapper.xml `，`OrderConfigMapper.xml `、`DictConfigMapper.xml`配置文件

### 6.6 测试

**测试一：**`OrderConfigMapperTest`类**testSelectById()**

```shell
ShardingSphere-SQL                       : Logic SQL: SELECT    
        id, pay_timeout
        FROM order_config
        WHERE id = ?
        
ShardingSphere-SQL                       : Actual SQL: ds-orders-0 ::: SELECT
        id, pay_timeout
        FROM order_config
        WHERE id = ? ::: [1]
```

* Logic SQL ：逻辑 SQL 日志，代码里面编写的SQL
* Actual SQL ：物理 SQL 日志， Sharding-JDBC 向数据库真正发起的日志
  *  `ds-orders-0` ，表名该物理 SQL ，是路由到 `ds-orders-0` 数据源执行。
  *  查询的是 `order_config` 表。配置的 `order_config` 逻辑表，不使用分库分表，对应的数据节点仅有 `ds-orders-0.order_config` 。

**测试二：**`OrderMapperTest`类**testSelectById()**

```shell
ShardingSphere-SQL                       : Logic SQL: SELECT
             
        id, user_id
     
        FROM orders
        WHERE id = ?
        
ShardingSphere-SQL                       : Actual SQL: ds-orders-0 ::: SELECT
             
        id, user_id
     
        FROM orders_0
        WHERE id = ? UNION ALL SELECT
             
        id, user_id
     
        FROM orders_2
        WHERE id = ? UNION ALL SELECT
             
        id, user_id
     
        FROM orders_4
        WHERE id = ? UNION ALL SELECT
             
        id, user_id
     
        FROM orders_6
        WHERE id = ? ::: [1, 1, 1, 1]
        
ShardingSphere-SQL                       : Actual SQL: ds-orders-1 ::: SELECT
             
        id, user_id
     
        FROM orders_1
        WHERE id = ? UNION ALL SELECT
             
        id, user_id
     
        FROM orders_3
        WHERE id = ? UNION ALL SELECT
             
        id, user_id
     
        FROM orders_5
        WHERE id = ? UNION ALL SELECT
             
        id, user_id
     
        FROM orders_7
        WHERE id = ? ::: [1, 1, 1, 1]
```

* 一条 Logic SQL 操作，发起了 8 条 Actual SQL 操作。`id = ?` 作为查询条件， Sharding-JDBC 解析不到我们配置的 `user_id` 片键（分库分表字段），作为查询字段，所以只好 `全库表路由`，查询所有对应的数据节点，也就是配置的所有数据库的数据表。在获得所有查询结果后，通过`归并引擎`合并返回最终结果。

**测试三：**`OrderMapperTest`类**testSelectListByUserId()**

```shell
ShardingSphere-SQL                       : Logic SQL: SELECT
             
        id, user_id
     
        FROM orders
        WHERE user_id = ?
ShardingSphere-SQL                       : Actual SQL: ds-orders-1 ::: SELECT
             
        id, user_id
     
        FROM orders_1
        WHERE user_id = ? ::: [1]
```

* 一条 Logic SQL 操作，发起了 1 条 Actual SQL 操作，`user_id = ?` 作为查询条件， Sharding-JDBC 解析到我们配置的 `user_id` 片键（分库分表字段），作为查询字段，可以 `标准路由`，仅查询**一个**数据节点。
  * 分库：`user_id % 2 = 1 % 2 = 1` ，所以路由到 `ds-orders-1` 数据源。
  * 分表：`user_id % 8 = 1 % 8 = 1` ，所以路由到 `orders_1` 数据表。
  * 两者一结合，只查询 `ds-orders-1.orders_1` 数据节点。

**测试四：**`OrderMapperTest`类**testInsert()**

```shell
ShardingSphere-SQL                       : Logic SQL: INSERT INTO orders (
            user_id
        ) VALUES (
            ?
        )
ShardingSphere-SQL                       : Actual SQL: ds-orders-0 ::: INSERT INTO orders_2 (
            user_id
        , id) VALUES (?, ?) ::: [2, 843509747079446528]
```

* 不考虑`广播表`的情况下，插入语句必须带有片键（分库分表字段），否则 `执行引擎` 不知道插入到哪个数据库的哪个数据表中。

**测试五：**`DictConfigMapperTest`类**testSelectById()**

```shell
ShardingSphere-SQL                       : Logic SQL: SELECT
         
        id
        , dict_type
     
        FROM dict_config
        WHERE id = ?
ShardingSphere-SQL                       : Actual SQL: ds-orders-0 ::: SELECT
         
        id
        , dict_type
     
        FROM dict_config
        WHERE id = ? ::: [845960218868187136]
DictConfigDO(id=845960218868187136, dictType=23)
```

* Logic SQL逻辑SQL和Actual SQL 物理SQL都只有一条

**测试六：**`DictConfigMapperTest`类**testBroadcastInsert()**

```shell
ShardingSphere-SQL                       : Logic SQL: INSERT INTO dict_config (dict_type)
        VALUES (?)
ShardingSphere-SQL                       : Actual SQL: ds-orders-0 ::: INSERT INTO dict_config (dict_type, id)
        VALUES (?, ?) ::: [22, 845976858171277312]
ShardingSphere-SQL                       : Actual SQL: ds-orders-1 ::: INSERT INTO dict_config (dict_type, id)
        VALUES (?, ?) ::: [22, 845976858171277312]

```

* Actual SQL 物理SQL有两条记录

**测试7：**`DictConfigMapperTest`类**testBroadcastUpdate()**

```shell
ShardingSphere-SQL                       : Logic SQL: UPDATE dict_config
         SET dict_type = ? 
        WHERE id = ?
ShardingSphere-SQL                       : Actual SQL: ds-orders-0 ::: UPDATE dict_config
         SET dict_type = ? 
        WHERE id = ? ::: [11, 845960218868187136]
ShardingSphere-SQL                       : Actual SQL: ds-orders-1 ::: UPDATE dict_config
         SET dict_type = ? 
        WHERE id = ? ::: [11, 845960218868187136]
```

* Actual SQL 物理SQL有两条记录

* 广播表具有以下特性：

  * 插入、更新操作会实时在所有节点上执行，保持各个分片的数据一致性

  * 查询操作，只从一个节点获取

  * 可以跟任何一个表进行 JOIN 操作

# 总结

* 方案一【基于 SpringAbstractRoutingDataSource 做拓展】，大多数场景下，基本能够满足。
* 方案二【不同操作类，固定数据源】。实际开发中，基本排除了这种方案，此方案更加适合**不同类型**的数据源，例如说一个项目中，既有 MySQL 数据源，又有 MongoDB、Elasticsarch 等其它数据源。
* 方案三【分库分表中间件】是目前最完美解决方案，基本满足了所有的场景。无论是多数据源，还是分库分表，还是读写分离，都能适配。