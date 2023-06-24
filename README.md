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
> 流程：
> 登录成功后，后端返回shortToken和longToken有效期一短、一长两个token，并在前端存储。
> 分别记为ts、tl，假如ts的有效期为5小时，tl的有效期为24小时。
> 前端向后端发起业务请求时，只携带ts。
> 情况1：从登录或刷新成功算起，业务请求时超过5小时，且未超过24小时，业务接口便会告知前端token已过期。
> 此时前端需缓存当前业务请求，并携带tl请求刷新接口，获取新的ts、tl对。然后再重新发起被缓存的请求（用户不感知刷新过程）。
> 情况2：从登录或刷新成功算起，业务请求时超过24小时，强制要求重新登录。
> 对于刷新token接口的请求，前端需要做好并发控制，即刷新动作必须加锁和防重。

4. 实现登录挤掉线功能（可配置）
