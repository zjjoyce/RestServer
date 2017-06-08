# 多租户管理平台适配层接口需求-V2
**OCDP-XA-Ethan**
## 修改记录：
*V2-2017/06/08*
1.	修改 ID7  ID18用户赋权接口传入参数类型及数据格式，适配原service broker接口；
2.	修改ID7  ID18接口内容增加operator key映射操作类型（赋权/回收权限/扩容）；
3.	删除 ID14 租户中服务实例增加用户权限，与 租户中用户添加角色 合并为一个接口；
4.	删除 ID16 租户中服务实例删除用户权限，与 租户中用户删除角色 合并为一个接口；
5.	删除 ID15 租户中服务实例更改用户权限，更改操作可将用户角色删除再添加角色实现；

*V1-2017/06/06*
1.	新增 ID18 添加用户 接口；
2.	新增 ID19 删除用户 接口；

## 一、	功能需求列表：

 ID | Front Actions | Adapter Actions
---|---|---
1|	租户创建|	租户表创建租户记录
2|	租户删除|	前提：租户中所有用户及服务实例已解绑（删除租户记录）
3|	获取租户信息|	返回租户表基本信息及绑定服务实例信息
4|	租户中添加用户|	将用户绑定租户（插入租户用户关系表）
5|	租户中删除用户|	解绑租户用户，删除用户租户关系表记录
6|	获取租户中用户信息|	租户用户关系表，返回用户基本信息及角色
7|	租户中用户添加角色（用户授权）|	前提：用户与租户绑定。租户用户关系表中更新记录，添加角色，DF新增用户权限，ServiceBrocker update user privileges
8|	租户中用户删除角色（回收用户权限）|	租户用户关系表更新记录，DF回收用户权限，ServiceBrocker update revoke user privileges
9|	获取租户中用户角色信息|	返回该租户中用户的角色
10|	租户中创建服务实例|	实例表记录租户实例，DF新建服务实例， ServiceBrocker provision service_instance
11|	租户中删除服务实例|	实例表删除租户与实例绑定信息，DF回收服务实例，ServiceBrocker deprovision service_instance
12|	获取租户服务实例信息|	返回实例列表及各实例基本信息
13|	租户中更改服务实例配额|	只支持扩容不支持减容, 更新实例表记录，DF更新服务实例Quota，ServiceBrocker  update service_instance
14|	租户中服务实例增加用户权限|	DF update user privileges
15|	租户中服务实例更改用户权限|	更改 == 删除+新建
16|	租户中服务实例删除用户权限|	DF update user privileges
17|	获取所有可用服务列表|	DF返回可用服务列表及明细
18|	新增用户|	不绑定角色，不绑定租户
19|	删除用户|	前提:用户已分别解绑角色与租户,删除用户表记录

## 二、	DF接口需求详细
***（下列URL仅为参考，实际接口的URL以北京研发团队提供为准，请求body内的传参为适配层提供的参数，请评估是否能满足接口需求）***
### 1) 租户创建

```
POST tenant/
{
    "metadata":{
        "name":"tenant_name",
        "id":"tenant_id", //租户id
        "pid":"parent_id", //租户父节点id(子公司/项目id) 
        "description":"Im description."
    }
}
```

### 2) 租户删除

```
DELETE tenant/
{
    "metadata":{
        "name":"tenant_name",
        "id":"tenant_id", //租户id   //有无子节点
    }
}
```

### 3) 获取租户信息 

```
GET tenant/
{
    "metadata":{
        "id":"tenant_id", //租户id
    }
}
```

### 4) 租户中添加用户

```
POST tenant/user/
{
    "metadata":{
        "tenant_id":"tenant_id", // 租户id
        "user_id":" user _id" //用户id
    }
}
```

### 5) 租户中删除用户

```
Delete tenant/user/
{
    "metadata":{
        "tenant_id":"tenant_id", // 租户id
        "user_id":" user _id" //用户id
    }
}
```

### 6) 获取租户中用户信息

```
Get tenant/user/
{
    "metadata":{
        "tenant_id":"tenant_id", // 租户id
        "user_id":" user _id" //用户id
    }
}
```

返回用户基本信息，及用户角色。
### 7) 租户中用户添加角色（用户授权）

```
POST privileges/
{
    "operator":"assign/revoke/scale" //broker update接口执行动作类型，赋权/回收权限/扩容
    "metadata":{
        "tenant_id":"tenant_id", // 用户绑定的租户id
        "role_id":" role_id", //用户角色id
        "user":"{
             "user_id":"id",
            "user_name":"user_name"
        }
        "services":[
            {
                "service_id":"hbase_id", 
                "accesses":[
                    {"isAllowed": true,"type": "read"},
                    {"isAllowed": true,"type": "write"},
                    {"isAllowed": true,"type": "create"},
                    {"isAllowed": true,"type": "admin"}
                ],
                "ser_instances":[
                    ser_instanceID1, 
                    ser_instanceID1, 
                    …
                ]
            },
            {
                "service_id":"hive_id", 
                "accesses":[
                    {"isAllowed": true,"type": "select"},
                    {"isAllowed": true,"type": "update"},
                    {"isAllowed": true,"type": "create"},
                    {"isAllowed": true,"type": "drop"},
                    {"isAllowed": true,"type": "alter"},
                    {"isAllowed": true,"type": "index"},
                    {"isAllowed": true,"type": "lock"}
                ],
                "ser_instances":[
        			ser_instanceID1, 
        			ser_instanceID1, 
        			…
        		]
        	},
            {
                "service_id":"hdfs_id", 
                "accesses":[
                    {"isAllowed":true,"type":"read"},
                    {"isAllowed":true,"type":"write"},
                    {"isAllowed":true,"type":"execute"}
        		],
                "ser_instances":[
                    ser_instanceID1, 
                    ser_instanceID1, 
                    …
        	    ]
        	},
    	   …
        ]
    }
}
```

### 8) 租户中用户删除角色(回收用户权限)

```
DELETE privileges/
{
    "operator":"assign/revoke/scale" //broker update接口执行动作类型，赋权/回收权限/扩容
    "metadata":{
        "tenant_id":"tenant_id", // 用户绑定的租户id
        "role_id":" role_id", //用户角色id
        "user":"{
             "user_id":"id",
            "user_name":"user_name"
         }
        "services":[
             {
                "service_id":"hbase_id", 
                "accesses":[
                    {"isAllowed": true,"type": "read"},
                    {"isAllowed": true,"type": "write"},
                    {"isAllowed": true,"type": "create"},
                    {"isAllowed": true,"type": "admin"}
                		],
                "ser_instances":[
                    ser_instanceID1, 
                    ser_instanceID1, 
                    …
                ]
            },
            {
                "service_id":"hive_id", 
                "accesses":[
                    {"isAllowed": true,"type": "select"},
                    {"isAllowed": true,"type": "update"},
                    {"isAllowed": true,"type": "create"},
                    {"isAllowed": true,"type": "drop"},
                    {"isAllowed": true,"type": "alter"},
                    {"isAllowed": true,"type": "index"},
                    {"isAllowed": true,"type": "lock"}
                ],
        		"ser_instances":[
        			ser_instanceID1, 
        			ser_instanceID1, 
        			…
        		]
        	},
            {
                "service_id":"hdfs_id", 
                "accesses":[
                    {"isAllowed":true,"type":"read"},
                    {"isAllowed":true,"type":"write"},
                    {"isAllowed":true,"type":"execute"}
        		],
                "ser_instances":[
                    ser_instanceID1, 
                    ser_instanceID1, 
                    …
        		]
        	}
        	…
    	]
	}
}
```

### 9) 获取租户中用户角色信息

```
Get tenant/userrole/
{
    "metadata":{
        "tenant_id":"tenant_id", // 用户绑定的租户id
        "user_id":" user _id", //用户id
    }
}
```

### 10) 租户中创建服务实例（实例与租户绑定）

```
PUT tenant/ser_instance/
{
    "metadata":{
        "id":"ser_instance_id", //实例id
        "ser_type":"hdfs_id", // service_id 需要与broker中service_id统一
        "tenant_id":"tenant_id", //实例所属租户
        "resources":{  
            //不同实例quota定义不同,与原service_broker中原resource格式一致
            "path":{
                "isRecursive":true,
                "isExcludes":false,
                "values":[
                    "/user/like_fa8ad6dc"
        	    ]
        	}
    	}
    	"parameters":{
        	//不同实例quota定义不同,与原service_broker中原parameter格式一致
        	"nameSpaceQuota":"100000000000",
        	"storageSpaceQuota":"10000"
    	}
    }
}
```

该接口需要返回执行状态，成功与否。
### 11) 租户中删除服务实例（实例与租户解绑）

```
DELETE tenant/ser_instance/
{
    "metadata":{
        "ser_instance_id ":"ser_instance_id", //实例id
        "service_id":"service_id", //服务类型
        "tenant_id":"tenant_id" //实例所属租户
    }
}
```

### 12) 获取租户服务实例信息

```
GET tenant/ser_instance/
{
    "metadata":{
    	"tenant_id":"tenant_id", //租户id
    }
}
```

返回该租户下服务实例列表及各实例类型及quota信息
### 13) 租户中更改服务实例配额(只支持扩容，不支持减容)

```
POST tenant/ser_instance/
{
    "metadata":{
        "id":"ser_instance_id", //实例id
        "service_id":" hdfs_id", //服务类型
        "resources":{  
            //不同实例quota定义不同,与原service_broker中原resource格式一致
            "path":{
                "isRecursive":true,
                "isExcludes":false,
                "values":[
                    "/user/like_fa8ad6dc"
            	]
    	    }
    	}
    	"parameters":{
        	//不同实例quota定义不同,与原service_broker中原parameter格式一致
        	"nameSpaceQuota":"100000000000",
        	"storageSpaceQuota":"10000"
    	}
    }
}
```

### ~~14) 租户中服务实例增加用户权限~~
### ~~15) 租户中服务实例更改用户权限~~
### ~~16) 租户中服务实例删除用户权限~~
### 17) 获取所有可用服务列表

```
GET services/
{
        "metadata":{
    }
}
```

Response需要包含服务类型列表及各服务的基本信息.
### 18) 新增用户

```
POST user/
{
    "metadata":{
        "id":"user_id",
        "username":"user_name",
        "password":"user_passwd",
    }
}
```

### 19) 删除用户

```
DELETE user/
{
    "metadata":{
        "id":"user_id",
    }
}
```


