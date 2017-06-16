## OCManager Adapter REST APIs

__NOTE: All the rest request should set__ _Accept: application/json_ __and__ _Content-Type: application/json_

### Users APIs

1. 获取所有用户
```
GET /ocmanager/v1/api/user
```
__response:__
```
[
    {
        "description": "user1 description",
        "email": "user1@.com",
        "id": "085571dc-7a94-44aa-8963-99c328b5527a",
        "password": "password",
        "username": "user1"
    },
    ...
]
```

2. 获取单个用户
```
GET /ocmanager/v1/api/user/{id}
```
__response:__
```
{
    "description": "user1 description",
    "email": "user1@.com",
    "id": "085571dc-7a94-44aa-8963-99c328b5527a",
    "password": "password",
    "username": "user1"
}
```

3. 创建用户
```
POST /ocmanager/v1/api/user
```
__request body:__
```
{
    "username": "createUser1",
    "email": "createUser1@com",
    "description": "createUser1 description",
    "password": "createUser1 password"
}
```

__response:__
```
{
  "description": "createUser1 description",
  "email": "createUser1@com",
  "id": "8dc8f0dd-a6c0-434f-ad0a-095201caa8ef",
  "password": "createUser1 password",
  "username": "createUser1"
}
```



4. 更新用户
```
PUT /ocmanager/v1/api/user/{id}
```

__request body:__
```
{
    "username": "createUser2Update",
    "email": "createUser2@comUpdate",
    "id": "a02a11e8-c762-426f-8db9-3c204d87b2dc",
    "description": "createUser2 descriptionUpdate",
    "password": "createUser2 passwordUpdate"
}
```


__response:__
```
{
  "description": "createUser2 descriptionUpdate",
  "email": "createUser2@comUpdate",
  "id": "a02a11e8-c762-426f-8db9-3c204d87b2dc",
  "password": "createUser2 passwordUpdate",
  "username": "createUser2Update"
}
```

5. 删除用户
```
DELETE /ocmanager/v1/api/user/{id}
``` 
__response:__
```
{
  "message": "a02a11e8-c762-426f-8db9-3c204d87b2dc",
  "resCodel": 200,
  "status": "delete success"
}
```



### Services APIs
1. 获取所有服务
```
GET /ocmanager/v1/api/service
```
__response:__
```
[
  {
    "description": "hdfs description",
    "id": "100",
    "servicename": "hdfs"
  },
  ...
]
```

2. 获取单个服务
```
GET /ocmanager/v1/api/service/{id}
```
__response:__
```
{
  "description": "hdfs description",
  "id": "100",
  "servicename": "hdfs"
}
```



### Roles APIs
1. 获取所有服务角色
```
GET /ocmanager/v1/api/role
```
__response:__
```
[
  {
    "description": "admin description",
    "id": "1",
    "rolename": "admin"
  },
    ......
]
```


### Tenants APIs
1. 创建租户
```
POST /ocmanager/v1/api/tenant
```

__request body:__
```
{
    "name": "createTenant001",
    "description": "create tenant 001",
    "parentId": "f7f281ee-a544-4636-9341-2db50c491b96"
}
```


__response:__
```
{
  "kind": "Project",
  "apiVersion": "v1",
  "metadata": {
    "name": "09367148-c72a-413f-b1de-5a23b566d808",
    "selfLink": "/oapi/v1/projectrequests/09367148-c72a-413f-b1de-5a23b566d808",
    "uid": "d24eaab4-51d5-11e7-9b16-fa163efdbea8",
    "resourceVersion": "16511194",
    "creationTimestamp": "2017-06-15T14:20:45Z",
    "annotations": {
      "openshift.io/description": "create tenant 001",
      "openshift.io/display-name": "createTenant001",
      "openshift.io/requester": "system:serviceaccount:default:ocm",
      "openshift.io/sa.scc.mcs": "s0:c16,c10",
      "openshift.io/sa.scc.supplemental-groups": "1000260000/10000",
      "openshift.io/sa.scc.uid-range": "1000260000/10000"
    }
  },
  "spec": {
    "finalizers": [
      "openshift.io/origin",
      "kubernetes"
    ]
  },
  "status": {
    "phase": "Active"
  }
}
``` 



2. 获取所有租户
```
GET /ocmanager/v1/api/tenant
```
__response:__
```
[
  {
    "description": "child tenant",
    "id": "5a6c16a9-0c85-42da-aec3-8ac1f5532fe1",
    "name": "ChildTenant",
    "parentId": "f7f281ee-a544-4636-9341-2db50c491b96"
  },
  {
    "description": "root tenant",
    "id": "f7f281ee-a544-4636-9341-2db50c491b96",
    "name": "rootTenant"
  }
  ...
]
```


3. 获取单个租户
```
GET /ocmanager/v1/api/tenant/{id}
```
__response:__
```
{
  "description": "child tenant",
  "id": "5a6c16a9-0c85-42da-aec3-8ac1f5532fe1",
  "name": "ChildTenant",
  "parentId": "f7f281ee-a544-4636-9341-2db50c491b96"
}

```

4. 获取租户所有子租户
```
GET /ocmanager/v1/api/tenant/{id}/children
```
__response:__
```
[
  {
    "description": "child tenant",
    "id": "5a6c16a9-0c85-42da-aec3-8ac1f5532fe1",
    "name": "ChildTenant",
    "parentId": "f7f281ee-a544-4636-9341-2db50c491b96"
  }
  ...
]

```



5. 在租户下创建服务实例
```
POST /ocmanager/v1/api/tenant/{id}/service/instance
```

__request body:__
```
{
  "kind":"BackingServiceInstance",
  "apiVersion":"v1",
  "metadata":
    {
      "name":"ETCD-instance017"
    },
  "spec":
    {
      "provisioning":
        {
          "backingservice_name":"ETCD",
          "backingservice_plan_guid":"204F8288-F8D9-4806-8661-EB48D94504B3"
        }
    }
}
```


__response:__
```
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "ETCD-instance017",
    "namespace": "zhaoyim",
    "selfLink": "/oapi/v1/namespaces/zhaoyim/backingserviceinstances/ETCD-instance017",
    "uid": "e45783a5-5240-11e7-8905-fa163efdbea8",
    "resourceVersion": "16574723",
    "creationTimestamp": "2017-06-16T03:07:12Z"
  },
  "spec": {
    "provisioning": {
      "dashboard_url": "",
      "backingservice_name": "ETCD",
      "backingservice_spec_id": "",
      "backingservice_plan_guid": "204F8288-F8D9-4806-8661-EB48D94504B3",
      "backingservice_plan_name": "",
      "parameters": null,
      "credentials": null
    },
    "userprovidedservice": {
      "credentials": null
    },
    "binding": null,
    "bound": 0,
    "instance_id": "",
    "tags": null
  },
  "status": {
    "phase": "Provisioning",
    "action": "",
    "last_operation": null
  }
}
``` 


6. 获取租户下所有服务实例
```
GET /ocmanager/v1/api/tenant/{id}/service/instances
```
__response:__
```
[
  {
    "id": "e45783a5-5240-11e7-8905-fa163efdbea8",
    "instanceName": "ETCD-instance017",
    "serviceTypeId": "",
    "serviceTypeName": "ETCD",
    "tenantId": "zhaoyim"
  },
  ...
]
```


7. 删除租户下某个服务实例
```
DELETE /ocmanager/v1/api/tenant/{id}/service/instance/{instanceName}
```
__response:__
```
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "ETCD-instance017",
    "namespace": "zhaoyim",
    "selfLink": "/oapi/v1/namespaces/zhaoyim/backingserviceinstances/ETCD-instance017",
    "uid": "e45783a5-5240-11e7-8905-fa163efdbea8",
    "resourceVersion": "16575264",
    "creationTimestamp": "2017-06-16T03:07:12Z",
    "deletionTimestamp": "2017-06-16T03:12:13Z"
  },
  "spec": {
    "provisioning": {
      "dashboard_url": "",
      "backingservice_name": "ETCD",
      "backingservice_spec_id": "",
      "backingservice_plan_guid": "204F8288-F8D9-4806-8661-EB48D94504B3",
      "backingservice_plan_name": "",
      "parameters": null,
      "credentials": null
    },
    "userprovidedservice": {
      "credentials": null
    },
    "binding": null,
    "bound": 0,
    "instance_id": "",
    "tags": null
  },
  "status": {
    "phase": "Provisioning",
    "action": "_ToDelete",
    "last_operation": null
  }
}
```




8. 绑定租户，用户和角色
```
POST /ocmanager/v1/api/tenant/{id}/user/role/assignment
```

__request body:__
```
{
    "userId": "user1",
    "roleId": "role1"
}
```


__response:__
```
{
  "roleId": "role1",
  "tenantId": "tenant1",
  "userId": "user1"
}
``` 

9. 获取租户下所有用户以及用户角色
```
GET /ocmanager/v1/api/tenant/{id}/users
```
__response:__
```
[
  {
    "roleId": "r1Id",
    "roleName": "r1",
    "userDescription": "u2 description",
    "userId": "u2Id",
    "userName": "u2"
  },
  ...
]
```


10. 更新租户中用户的角色
```
PUT /ocmanager/v1/api/tenant/{id}/user/role/assignment
```

__request body:__
```
{
    "userId": "user1",
    "roleId": "role2"
}
```


__response:__
```
{
  "roleId": "role2",
  "tenantId": "tenant1",
  "userId": "user1"
}
``` 


11. 解除租户，用户和角色的绑定
```
DELETE /ocmanager/v1/api/tenant/{id}/user/{userId}/role/assignment
```
__response:__
```
{
  "message": "user1",
  "resCodel": 200,
  "status": "delete success"
}
``` 





