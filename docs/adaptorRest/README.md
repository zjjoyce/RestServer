## OCManager Adapter REST APIs

### Users APIs

1. 获取所有用户
```
GET /ocmanager/v1/api/user
```
__response:__
```
[
    {
        "id": "1",
        "username": "user1",
        "email": "xxx@123.com",
        "description": "xxxx"
        
    }
    ......
]
```

2. 获取单个用户
```
GET /ocmanager/v1/api/user/{id}
```
__response:__
```
{
    "id": "1",
    "username": "user1",
    "email": "xxx@123.com",
    "description": "xxxx"
    
}
```

3. 创建用户
```
POST /ocmanager/v1/api/user
```
__request body:__
```
{
    "id": "1",
    "username": "user1",
    "email": "xxx@123.com",
    "description": "xxxx"
    
}
```


__response:__
```
{
    "id": "1",
    "username": "user1",
    "email": "xxx@123.com",
    "description": "xxxx"
    
}
```



4. 更新用户
```
PUT /ocmanager/v1/api/user/{id}
```

__request body:__
```
{
    "id": "2",
    "username": "user2",
    "email": "xxx@123.com",
    "description": "xxxx"
    
}
```


__response:__
```
{
    "id": "2",
    "username": "user2",
    "email": "xxx@123.com",
    "description": "xxxx"
    
}
```

5. 删除用户
```
DELETE /ocmanager/v1/api/user/{id}
``` 
__response:__
```
{
  "status": "Success",
  "code": 200
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
        "id": "1",
        "servicename": "service1",
        "description": "xxxx"
        
    }
    ......
]
```

2. 获取单个服务
```
GET /ocmanager/v1/api/service/{id}
```
__response:__
```
{
        "id": "1",
        "servicename": "service1",
        "description": "xxxx"
    
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
        "id": "1",
        "rolename": "role1",
        "description": "xxxx"
        
    }
    ......
]
```


### Tenants APIs
1. 获取所有租户
```
GET /ocmanager/v1/api/tenant
```
__response:__
```
[
    {
        "id": "2",
        "name": "tenant1",
        "description": "xxxx",
        "parent_id": "1",
        "bindingUsers": 
            [
                {
                    "id": "1",
                    "username": "user1",
                    "email": "xxx@123.com",
                    "description": "xxxx",
                    "role": "role1"
                },
                ......
            ],
        "serviceInstances":
            [
                {
                    "id": "1",
                    "serviceTypeId": "2",
                    "quota": 
                        {
                            "hdfs_path":"/ocdp_cluster/my_path",
                            "hdfs_space":1000000000,
                            "hdfs_files_limit":10000
                        }
                    
                }
                ......
            ]
    }
    ......
]
```


2. 获取单个租户
```
GET /ocmanager/v1/api/tenant/{id}
```
__response:__
```
{
    "id": "2",
    "name": "tenant1",
    "description": "xxxx",
    "parent_id": "1",
    "bindingUsers": 
        [
            {
                "id": "1",
                "username": "user1",
                "email": "xxx@123.com",
                "description": "xxxx",
                "role": "role1"
            },
            ......
        ],
    "serviceInstances":
        [
            {
                "id": "1",
                "serviceTypeId": "2",
                "quota": 
                    {
                        "hdfs_path":"/ocdp_cluster/my_path",
                        "hdfs_space":1000000000,
                        "hdfs_files_limit":10000
                    }
                
            }
            ......
        ]
}

```



3. 创建租户
```
POST /ocmanager/v1/api/tenant
```

__request body:__
```
{
    "id": "2",
    "name": "tenant1",
    "description": "xxxx",
    "parent_id": "1"
}
```


__response:__
```
{
    "id": "2",
    "name": "tenant1",
    "description": "xxxx",
    "parent_id": "1",
    "bindingUsers": [],
    "serviceInstances": []
}
``` 


4. 更新租户

```
PUT /ocmanager/v1/api/tenant/{id}
```

__request body:__
```
{
    "id": "2",
    "name": "tenant1",
    "description": "xxxx",
    "parent_id": "1",
    "bindingUsers": 
        [
            {
                "id": "1",
                "username": "user1",
                "email": "xxx@123.com",
                "description": "xxxx",
                "role": "role1"
            },
            ......
        ],
    "serviceInstances":
        [
            {
                "id": "1",
                "serviceTypeId": "2",
                "quota": 
                    {
                        "hdfs_path":"/ocdp_cluster/my_path",
                        "hdfs_space":1000000000,
                        "hdfs_files_limit":10000
                    }
                
            }
            ......
        ]
}
```


__response:__
```
{
    "id": "2",
    "name": "tenant1",
    "description": "xxxx",
    "parent_id": "1",
    "bindingUsers": 
        [
            {
                "id": "1",
                "username": "user1",
                "email": "xxx@123.com",
                "description": "xxxx",
                "role": "role1"
            },
            ......
        ],
    "serviceInstances":
        [
            {
                "id": "1",
                "serviceTypeId": "2",
                "quota": 
                    {
                        "hdfs_path":"/ocdp_cluster/my_path",
                        "hdfs_space":1000000000,
                        "hdfs_files_limit":10000
                    }
                
            }
            ......
        ]
}
``` 



5. 删除用户
```
DELETE /ocmanager/v1/api/tenant/{id}
``` 
__response:__
```
{
  "status": "Success",
  "code": 200
}
```
