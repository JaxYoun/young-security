# young-security
## 一、新SpringSecurityDemo项目(含新旧两种配置方式)
1. 纯后端接口项目，所以在Security配置项中禁用了Session管理、CSRF防御功能
2. 实现了以数据库用户登录认证
3. 以JWT作为认证信息传输token载体
4. 实现了注解式接口权限控制（权限未从数据库获取）
5. 自定义统一的认证、权限异常处理器
6. 分别从SpringBoot和SpringSecurity两个层面处理了跨域问题
7. 接口地址：
```shell
#1.登录
http://localhost/authentication/login/yang/111111

#2.登出
http://localhost/authentication/logout

#3.业务测试
http://localhost/user/hello

#4.添加用户
localhost/user/add/yang/111111
```
## 二、TodoList:
1. 明确续期时机
2. 细化认证异常（方便token刷新）
3. JWT双token，实现自动token刷新
4. 实现登录挤掉线功能（可配置）
