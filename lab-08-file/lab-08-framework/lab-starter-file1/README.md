文件存储模块，提供文件上传、下载、删除等操作。

文件存储相关配置，示例：

```yaml
#文件存储配置
file-server:
  defaultType: disk # 默认存储方式
  disk: # 磁盘存储方式
    enabled: true  
    base-path: /tmp/nas #本地文件上传路径
  minio: # MinIO存储方式
    enabled: false
    endpoint: http://127.0.0.1:9000
    access-key: admin
    secret-key: admin123456
    bucket-name: zhyw
  ftp: # FTP存储方式
    enabled: false
    host: 127.0.0.1
    port: 21
    password: anonymous
    username: anonymous
    # 相对路径，基于FTP服务器配置的根目录
    base-path: zhyw
    http-path: ftp://127.0.0.1
    timeout: 10000    
```



