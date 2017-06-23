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


6. 通过用户ID获取某个用户再哪些租户下
```
GET /ocmanager/v1/api/user/id/{id}/tenants
```
__response:__
```
[
  {
    "roleId": "r1",
    "roleName": "r1",
    "tenantId": "t1",
    "tenantName": "t1",
    "userDescription": "u2 description",
    "userId": "u2",
    "userName": "u2"
  },
  ...
]
```



7. 通过用户名称获取某个用户再哪些租户下
```
GET /ocmanager/v1/api/user/name/{name}/tenants
```
__response:__
```
[
  {
    "roleId": "r1",
    "roleName": "r1",
    "tenantId": "t1",
    "tenantName": "t1",
    "userDescription": "u2 description",
    "userId": "u2",
    "userName": "u2"
  },
  ...
]
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

3. 添加Service broker（添加服务， 服务是注册在service broker 里面的，，因此会添加service broker 中注册的所有服务）
```
POST /ocmanager/v1/api/service/broker
```
__request body:__
```
{
  "kind":"ServiceBroker",
  "apiVersion":"v1",
  "metadata":
    {
      "name":"rds9"
    },
  "spec":
    {
      "url":"http://localhost:9900",
      "username":"test",
      "password":"test"
    }
}
```

__response:__
```
{
  "kind": "ServiceBroker",
  "apiVersion": "v1",
  "metadata": {
    "name": "rds9",
    "selfLink": "/oapi/v1/servicebrokers/rds9",
    "uid": "edf41739-564a-11e7-8f1f-fa163efdbea8",
    "resourceVersion": "17209399",
    "creationTimestamp": "2017-06-21T06:29:07Z"
  },
  "spec": {
    "url": "http://localhost:9900",
    "name": "",
    "username": "test",
    "password": "test"
  },
  "status": {
    "phase": "New"
  }
}
```

4. 删除Service broker（删除服务， 服务是注册在service broker 里面的，因此会删除service broker 中注册的所有服务）
```
POST /ocmanager/v1/api/service/broker/{name}
```

__response:__
```
{
  "kind": "ServiceBroker",
  "apiVersion": "v1",
  "metadata": {
    "name": "rds9",
    "selfLink": "/oapi/v1/servicebrokers/rds9",
    "uid": "edf41739-564a-11e7-8f1f-fa163efdbea8",
    "resourceVersion": "17209607",
    "creationTimestamp": "2017-06-21T06:29:07Z",
    "deletionTimestamp": "2017-06-21T06:31:36Z",
    "annotations": {
      "ServiceBroker/LastPing": "1498026648288080254",
      "ServiceBroker/NewRetryTimes": "1"
    }
  },
  "spec": {
    "url": "http://localhost:9900",
    "name": "",
    "username": "test",
    "password": "test"
  },
  "status": {
    "phase": "Deleting"
  }
}
```


5. 获取Data Foundry服务列表
```
GET /ocmanager/v1/api/service/df
```
__response:__
```
{
  "kind": "BackingServiceList",
  "apiVersion": "v1",
  "metadata": {
    "selfLink": "/oapi/v1/namespaces/openshift/backingservices",
    "resourceVersion": "17209829"
  },
  "items": [
    {
      "metadata": {
        "name": "Cassandra",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Cassandra",
        "uid": "38fa4221-cd9f-11e6-b10e-4e10dba0edae",
        "resourceVersion": "6202322",
        "creationTimestamp": "2016-12-29T08:17:22Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "Cassandra",
        "id": "3D7D7D07-D704-4B22-B492-EE5AE5301A55",
        "description": "A Sample Cassandra (v3.4) cluster on Openshift",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "cassandra",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "Cassandra",
          "documentationUrl": "https://wiki.apache.org/cassandra/GettingStarted",
          "imageUrl": "https://cassandra.apache.org/media/img/cassandra_logo.png",
          "longDescription": "Managed, highly available cassandra clusters in the cloud.",
          "providerDisplayName": "Asiainfo",
          "supportUrl": "https://cassandra.apache.org/"
        },
        "plans": [
          {
            "name": "standalone",
            "id": "7B7EC041-2090-4ACB-AE0F-E8BDF315A778",
            "description": "HA Cassandra on Openshift",
            "metadata": {
              "bullets": [
                "20 GB of Disk",
                "20 connections"
              ],
              "costs": null,
              "displayName": "Shared and Free",
              "customize": null
            },
            "free": true
          }
        ],
        "dashboard_client": null
      },
      "status": {
        "phase": "Inactive"
      }
    },
    {
      "metadata": {
        "name": "liuxu",
        "generateName": "liuxu",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/liuxu",
        "uid": "95468dec-28c4-11e7-9b96-fa163efdbea8",
        "resourceVersion": "7495189",
        "creationTimestamp": "2017-04-24T08:04:04Z",
        "labels": {
          "asiainfo.io/servicebroker": "liuxu"
        }
      },
      "spec": {
        "name": "Greenplum",
        "id": "98E2AFE3-7279-40CA-B04E-74276B3FF4B2",
        "description": "Greenplumæ¯Pivotalå¼æºçMPPæ°æ®åºã",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "Greenplum",
          "mpp",
          "database"
        ],
        "requires": null,
        "metadata": {
          "displayName": "Greenplum",
          "documentationUrl": "http://gpdb.docs.pivotal.io",
          "imageUrl": "pub/assets/Greenplum.png",
          "longDescription": "The First Open SourceMassively Parallel Data Warehouse",
          "providerDisplayName": "Asiainfo",
          "supportUrl": "http://greenplum.org"
        },
        "plans": [
          {
            "name": "Experimental",
            "id": "B48A3972-536F-47A6-B04F-A5344F4DC5E0",
            "description": "åç¬Greenplumå®ä¾",
            "metadata": {
              "bullets": [
                "20 GB of Disk",
                "20 connections"
              ],
              "costs": null,
              "displayName": "Shared and Free",
              "customize": null
            },
            "free": false
          }
        ],
        "dashboard_client": null
      },
      "status": {
        "phase": "Active"
      }
    },
    ...
  ]
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
    "id": "09367148-c72a-413f-b1de-5a23b566d808",
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
          "backingservice_plan_guid":"204F8288-F8D9-4806-8661-EB48D94504B3",
          "parameters":{"ETCDStorageQuota":"1024","ETCDQueueQuota":"10"}
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


12. 删除租户
```
DELETE /ocmanager/v1/api/tenant/{id}
```
__response:__
```
{
  "kind": "Status",
  "apiVersion": "v1",
  "metadata": {},
  "status": "Success",
  "code": 200
}
```


13. 获取服务实例访问信息（只有服务实例状态是Active的时候才会有访问信息）
```
GET /ocmanager/v1/api/tenant/{tenantId}/service/instance/{serviceInstanceName}/access/info
```
__response:__
```
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "ETCD-instance014",
    "namespace": "zhaoyim",
    "selfLink": "/oapi/v1/namespaces/zhaoyim/backingserviceinstances/ETCD-instance014",
    "uid": "fae1f410-50ee-11e7-87b1-fa163efdbea8",
    "resourceVersion": "16363936",
    "creationTimestamp": "2017-06-14T10:48:19Z"
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

14. 更新租户单个服务实例
```
PUT /ocmanager/v1/api/tenant/{id}/service/instance/{instanceName}
```

__request body:__
```
{
    "kind": "BackingServiceInstance",
    "apiVersion": "v1",
    "metadata": {
        "name": "hive-instance",
        "namespace": "zhaoyim",
        "selfLink": "/oapi/v1/namespaces/zhaoyim/backingserviceinstances/hive-instance",
        "uid": "f1a993d6-57c8-11e7-9a0f-fa163efdbea8",
        "resourceVersion": "17457486",
        "creationTimestamp": "2017-06-23T04:03:41Z"
    },
    "spec": {
        "provisioning": {
            "dashboard_url": "",
            "backingservice_name": "Hive",
            "backingservice_spec_id": "2ef26018-003d-4b2b-b786-0481d4ee9fa3",
            "backingservice_plan_guid": "aa7e364f-fdbf-4187-b60a-218b6fa398ed",
            "backingservice_plan_name": "shared",
            "parameters": {
                "hiveStorageQuota": "1024",
                "instance_id": "14ea4d05-57c9-11e7-9a0f-fa163efdbea8",
                "tenant_name": "zhaoyim",
                "user_name": "u2",
                "yarnQueueQuota": "10"
            },
            "credentials": {
                "Hive database": "14ea4d0557c911e79a0ffa163efdbea8",
                "host": "zx-dn-03",
                "password": "e6510d82-2ed3-48f5-8668-b82d572aaac1",
                "port": "10000",
                "uri": "jdbc:hive2://zx-dn-03:10000/14ea4d0557c911e79a0ffa163efdbea8;principal=hive/zx-dn-03@EXAMPLE.COM",
                "username": "zhaoyim@EXAMPLE.COM"
            },
            "accesses": {
                "2ef26018-003d-4b2b-b786-0481d4ee9fa3": [
                    "select",
                    "update",
                    "create",
                    "drop",
                    "alter",
                    "index",
                    "lock"
                ],
                "ae0f2324-27a8-415b-9c7f-64ab6cd88d40": [
                    "submit-app",
                    "admin-queue"
                ],
                "ae67d4ba-5c4e-4937-a68b-5b47cfe356d8": [
                    "read",
                    "write",
                    "execute"
                ],
                "d3b9a485-f038-4605-9b9b-29792f5c61d1": [
                    "submit-app",
                    "admin-queue"
                ],
                "d9845ade-9410-4c7f-8689-4e032c1a8450": [
                    "read",
                    "write",
                    "create",
                    "admin"
                ]
            }
        },
        "userprovidedservice": {
            "credentials": null
        },
        "binding": null,
        "bound": 0,
        "instance_id": "14ea4d05-57c9-11e7-9a0f-fa163efdbea8",
        "tags": null
    },
    "status": {
        "phase": "Unbound",
        "action": "",
        "last_operation": null,
        "patch": "Updating"
    }
}
```


__response:__
```
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "hive-instance",
    "namespace": "zhaoyim",
    "selfLink": "/oapi/v1/namespaces/zhaoyim/backingserviceinstances/hive-instance",
    "uid": "f1a993d6-57c8-11e7-9a0f-fa163efdbea8",
    "resourceVersion": "17465724",
    "creationTimestamp": "2017-06-23T04:03:41Z"
  },
  "spec": {
    "provisioning": {
      "dashboard_url": "",
      "backingservice_name": "Hive",
      "backingservice_spec_id": "2ef26018-003d-4b2b-b786-0481d4ee9fa3",
      "backingservice_plan_guid": "aa7e364f-fdbf-4187-b60a-218b6fa398ed",
      "backingservice_plan_name": "shared",
      "parameters": {
        "hiveStorageQuota": "2048",
        "instance_id": "14ea4d05-57c9-11e7-9a0f-fa163efdbea8",
        "tenant_name": "zhaoyim",
        "user_name": "u2",
        "yarnQueueQuota": "20"
      },
      "credentials": {
        "Hive database": "14ea4d0557c911e79a0ffa163efdbea8",
        "host": "zx-dn-03",
        "password": "e6510d82-2ed3-48f5-8668-b82d572aaac1",
        "port": "10000",
        "uri": "jdbc:hive2://zx-dn-03:10000/14ea4d0557c911e79a0ffa163efdbea8;principal=hive/zx-dn-03@EXAMPLE.COM",
        "username": "zhaoyim@EXAMPLE.COM"
      }
    },
    "userprovidedservice": {
      "credentials": null
    },
    "binding": null,
    "bound": 0,
    "instance_id": "14ea4d05-57c9-11e7-9a0f-fa163efdbea8",
    "tags": null
  },
  "status": {
    "phase": "Unbound",
    "action": "",
    "last_operation": null
  }
}
``` 



### Single Sign on APIs
1. 获取当前用户(所要用户信息需要设置在request header中)
```
GET /ocmanager/v1/api/sso/user
```
__response:__
```
{
  "http_x_proxy_cas_email": "",
  "http_x_proxy_cas_loginname": "user1",
  "http_x_proxy_cas_mobile": "",
  "http_x_proxy_cas_userid": "",
  "http_x_proxy_cas_username": "user1"
}
```
