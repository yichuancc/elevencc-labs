# 数据权限功能

## blade

```shell
# 组件类
blade-tool-master
 -- blade-core-datascope

# 后端项目
SpringBlade-boot

#前端项目
Saber-3.x

#开发手册
bladeX开发手册
 -- 5.4 
```

## lab-07-tool-data-scope

基于上述blade，仿写的数据权限功能

* 导入dm.sql脚本

* 运行项目，请求[127.0.0.1:8080/data-scope/list]()地址

* 查看控制台sql日志，，查看数据权限表的权限控制是否成功

  ```shell
  ==>  Preparing: select * from (SELECT id,menu_id,scope_name,scope_field,scope_class,scope_column,scope_type,scope_value,remark,create_time,create_user,create_name,update_time,update_user,update_name,del_flag FROM sys_data_scope) scope where scope.create_user in ('1')
  ==> Parameters: 
  <==    Columns: id, menu_id, scope_name, scope_field, scope_class, scope_column, scope_type, scope_value, remark, create_time, create_user, create_name, update_time, update_user, update_name, del_flag
  <==        Row: 1, , 测试, *, com.cc.lab07tooldatascope.module.mapper.SysDataScopeMapper.selectList, create_user, 2, , , null, 1, null, null, null, null, 0
  <==      Total: 1
  
  #结果匹配数据权限成功
  where scope.create_user in ('1')
  ```

* 业务使用说明

  * 维护需要控制的数据权限查询mapper方法
  * 授权角色对应的数据权限查询方法
  * 调整代码中登录用户信息
  * 根据业务需求，自定义数据权限查询策略

