## Authentication

## 支持授权的REST API

 * 添加用户
 * 更新用户
 * 删除用户
 *
 * 添加服务
 * 删除服务
 *
 * 给用户授权系统管理员
 * 给用户授权子公司管理员
 * 给用户授权项目管理员
 * 给用户授权团队成员

## 授权方法

所有授权的REST请求在Request Header里面加上当前用户的信息，包括当前用户的tenantId和username信息

```
{
  tenantId: "1",
  username: "zhaoyim"
}
```

如果当前用户没有相应的权限，系统会返回UNAUTHORIZED。
