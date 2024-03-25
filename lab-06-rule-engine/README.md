# [LiteFlow](https://liteflow.cc/)

## Springboot场景安装运行

> 参考项目：**lab-06-liteflow**

### 依赖

LiteFlow提供了liteflow-spring-boot-starter依赖包，提供自动装配功能

```xml
<dependency>
    <groupId>com.yomahub</groupId>
    <artifactId>liteflow-spring-boot-starter</artifactId>
    <version>2.11.4.2</version>
</dependency>
```

### 配置

#### 组件定义

在依赖了以上jar包后，你需要定义并实现一些组件，确保SpringBoot会扫描到这些组件并注册进上下文。

```java
@Component("a")
public class ACmp extends NodeComponent {

	@Override
	public void process() {
		//do your business
	}
}
```

以此类推再分别定义b，c组件

#### 配置文件

然后，在你的SpringBoot的application.properties或者application.yml里添加配置(这里以properties为例，yaml也是一样的)

```properties
liteflow.rule-source=config/flow.el.xml
```

#### 规则文件的定义

同时，你得在resources下的`config/flow.el.xml`中定义规则：SpringBoot在启动时会自动装载规则文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<flow>
    <chain name="chain1">
        THEN(a, b, c);
    </chain>
</flow>
```

### 运行

#### 服务调用

```java
@Component
public class YourClass{
    
    @Resource
    private FlowExecutor flowExecutor;
    
    public void testConfig(){
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
    }
}
```

## SQL数据库配置源

> 参考项目：**lab-06-liteflow-sql**

### 依赖

如果使用数据库作为规则配置源，你需要添加以下额外插件依赖

```xml
<dependency>
    <groupId>com.yomahub</groupId>
    <artifactId>liteflow-rule-sql</artifactId>
    <version>2.11.4.2</version>
</dependency>
```

### 配置

依赖了插件包之后，你无需再配置`liteflow.ruleSource`路径。只需要配置插件的额外参数即可：

```yml
liteflow:
  rule-source-ext-data-map:
    url: jdbc:mysql://localhost:3306/poseidon
    driverClassName: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
    applicationName: demo
    #是否开启SQL日志
    sqlLogEnabled: true
    #是否开启SQL数据轮询自动刷新机制 默认不开启
    pollingEnabled: true
    pollingIntervalSeconds: 60
    pollingStartSeconds: 60
    #以下是chain表的配置，这个一定得有
    chainTableName: chain
    chainApplicationNameField: application_name
    chainNameField: chain_name
    elDataField: el_data
    chainEnableField: enable
    #以下是script表的配置，如果你没使用到脚本，下面可以不配置
    scriptTableName: script
    scriptApplicationNameField: application_name
    scriptIdField: script_id
    scriptNameField: script_name
    scriptDataField: script_data
    scriptTypeField: script_type
    scriptLanguageField: script_language
    scriptEnableField: enable
```

### 配置说明

LiteFlow并不约束你的表名和表结构，你只需要把表名和相关的字段名配置在参数里即可。对于配置项说明如下：

| 配置项                     | 说明                                                         |
| -------------------------- | ------------------------------------------------------------ |
| url                        | jdbc的连接url                                                |
| driverClassName            | 驱动器类名                                                   |
| username                   | 数据库用户名                                                 |
| password                   | 数据库密码                                                   |
| applicationName            | 你的应用名称                                                 |
| sqlLogEnabled              | 是否开启SQL日志 默认开启                                     |
| pollingEnabled             | 是否开启SQL数据轮询自动刷新机制 默认不开启                   |
| pollingIntervalSeconds     | SQL数据轮询时间间隔(s) 默认为60s                             |
| pollingStartSeconds        | 规则配置后首次轮询的起始时间(s) 默认为60s                    |
| chainTableName             | 编排规则表的表名                                             |
| chainApplicationNameField  | 编排规则表中应用名称存储字段名                               |
| chainNameField             | 规则名称存储的字段名                                         |
| elDataField                | EL表达式的字段(只存EL)                                       |
| chainEnableField           | 此chain是否生效，此字段最好在mysql中定义成`tinyInt`类型      |
| scriptTableName            | 你的脚本存储表的表名                                         |
| scriptApplicationNameField | 脚本表中应用名称存储字段名                                   |
| scriptIdField              | 脚本组件的Id的字段名                                         |
| scriptNameField            | 脚本组件名称的字段名                                         |
| scriptDataField            | 脚本数据的字段名                                             |
| scriptTypeField            | 脚本类型的字段名(类型参照[定义脚本组件](https://liteflow.cc/pages/81d53c/)) |
| scriptLanguageField        | 脚本语言类型（groovy \| qlexpress \| js \| python \| lua \| aviator \| java） |
| scriptEnableField          | 此脚本是否生效，此字段最好在mysql中定义成`tinyInt`类型       |

在数据库中，你至少需要一张表来存储编排规则，这是必须的。如果你使用到了脚本，那么需要第二张表来存储脚本。在规则表中，一行数据就是一个规则。在脚本表中，一行数据就是一个脚本组件。

规则表：liteflow_chain

| id   | application_name | chain_name | chain_desc | el_data               | create_time         |
| ---- | ---------------- | ---------- | ---------- | --------------------- | ------------------- |
| 1    | demo             | chain1     | 测试流程1  | THEN(a, b, c, s1,s2); | 2022-09-19 19:31:00 |

脚本表：liteflow_script

| id   | application_name | script_id | script_name | script_data                                                  | script_type | script_language | create_time         |
| ---- | ---------------- | --------- | ----------- | ------------------------------------------------------------ | ----------- | --------------- | ------------------- |
| 1    | demo             | s1        | 脚本s1      | import cn.hutool.core.date.DateUtil def date = DateUtil.parse("2022-10-17 13:31:43") println(date) defaultContext.setData("demoDate", date) class Student { int studentID String studentName } Student student = new Student() student.studentID = 100301 student.studentName = "张三" defaultContext.setData("student",student) def a=3 def b=2 defaultContext.setData("s1",a*b) | script      | groovy          | 2022-09-19 19:31:00 |
| 2    | demo             | s2        | 脚本s2      | defaultContext.setData("s2","hello")                         | script      | groovy          | 2022-09-19 19:31:00 |

### 使用项目中的dataSource来进行连接

LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接。如果你项目中已有链接配置，比如：

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/poseidon
spring.datasource.username=root
spring.datasource.password=123456
```

那么你在`rule-source-ext-data-map`中无需再配置以下几项：

```yaml
url: jdbc:mysql://localhost:3306/poseidon
driverClassName: com.mysql.cj.jdbc.Driver
username: root
password: 123456
```

> 需要注意的是，如果你的系统中声明了多个数据源，那么LiteFlow会自动判断该选用哪个数据源。
>
> 如果你的系统中使用了动态数据源，那么请确保默认数据源是含有LiteFlow链路数据的表数据的。