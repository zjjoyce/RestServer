# DataFoundry APIs


### 获取 bearer token

```
HEAD /oauth/authorize?client_id=openshift-challenging-client&response_type=token HTTP/1.1
```
Authorization: Basic RGVjb2RpbmdUaGlzQjY0U3RyaW5nOk1ha2VzTm9TZW5zZQo=

```bash
HTTP/1.1 302 Found
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Expires: Fri, 01 Jan 1990 00:00:00 GMT
Location: https://127.0.0.1/oauth/token/implicit#access_token=NLFk0H5o-5F9aRkvPki6IUacHt-MlWseQSb6R_yIcpo&expires_in=86400&scope=user%3Afull&token_type=Bearer
Pragma: no-cache
Set-Cookie: ssn=MTQ5NjgwNDYxOHxjZGV1XzRRX1NYWGI1TThqcnRPLUpuampKMTZCcHlyeWM1bi1SNEVHNV9oeU5mdWRXWi1mRTAwWW9GOEtSZXYwMXRUemJOMkR4NUV6SmFIN3BHN3dJSHRXUWYyWURyS3l5UmF5QVV5RWRDSkk1ZExJNEtucHdTWFFBR3JhMEhIcmNnPT18_Jg11GP_WYASAblZBXki6NHQTpR9se6sZuB-0n_DkQY=; Path=/; Expires=Wed, 07 Jun 2017 03:08:38 GMT; Max-Age=300; HttpOnly; Secure
Date: Wed, 07 Jun 2017 03:03:38 GMT
Content-Type: text/plain; charset=utf-8

```

* 以下API调用需要携带 bearer token，用于身份验证。API支持支持pretty参数，用来控制API输出格式，默认为true。

### 获取租户列表

```
GET /oapi/v1/projects
```
Authorization: Bearer NLFk0H5o-5F9aRkvPki6IUacHt-MlWseQSb6R_yIcpo

```
GET /oapi/v1/projects

HTTP/1.1 200 OK
Cache-Control: no-store
Content-Type: application/json
Date: Wed, 07 Jun 2017 03:44:12 GMT
Transfer-Encoding: chunked
```
```json
{
  "kind": "ProjectList",
  "apiVersion": "v1",
  "metadata": {
    "selfLink": "/oapi/v1/projects"
  },
  "items": [
    {
      "metadata": {
        "name": "default",
        "selfLink": "/oapi/v1/projectsdefault",
        "uid": "a4472b8a-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "2935",
        "creationTimestamp": "2017-05-27T07:25:21Z",
        "annotations": {
          "openshift.io/sa.initialized-roles": "true",
          "openshift.io/sa.scc.mcs": "s0:c1,c0",
          "openshift.io/sa.scc.supplemental-groups": "1000000000/10000",
          "openshift.io/sa.scc.uid-range": "1000000000/10000"
        }
      },
      "spec": {
        "finalizers": [
          "kubernetes",
          "openshift.io/origin"
        ]
      },
      "status": {
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "hell",
        "selfLink": "/oapi/v1/projectshell",
        "uid": "af0bd955-49b6-11e7-8d81-f0def1203974",
        "resourceVersion": "157344",
        "creationTimestamp": "2017-06-05T06:17:42Z",
        "annotations": {
          "openshift.io/description": "Description of this project",
          "openshift.io/display-name": "中信",
          "openshift.io/requester": "san",
          "openshift.io/sa.scc.mcs": "s0:c8,c2",
          "openshift.io/sa.scc.supplemental-groups": "1000060000/10000",
          "openshift.io/sa.scc.uid-range": "1000060000/10000"
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
    },
    {
      "metadata": {
        "name": "hello",
        "selfLink": "/oapi/v1/projectshello",
        "uid": "e7c93464-49b5-11e7-8d81-f0def1203974",
        "resourceVersion": "157232",
        "creationTimestamp": "2017-06-05T06:12:08Z",
        "annotations": {
          "openshift.io/description": "",
          "openshift.io/display-name": "",
          "openshift.io/requester": "san",
          "openshift.io/sa.scc.mcs": "s0:c7,c4",
          "openshift.io/sa.scc.supplemental-groups": "1000050000/10000",
          "openshift.io/sa.scc.uid-range": "1000050000/10000"
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
    },
    {
      "metadata": {
        "name": "kube-system",
        "selfLink": "/oapi/v1/projectskube-system",
        "uid": "a44e2b13-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "172",
        "creationTimestamp": "2017-05-27T07:25:21Z",
        "annotations": {
          "openshift.io/sa.scc.mcs": "s0:c3,c2",
          "openshift.io/sa.scc.supplemental-groups": "1000010000/10000",
          "openshift.io/sa.scc.uid-range": "1000010000/10000"
        }
      },
      "spec": {
        "finalizers": [
          "kubernetes"
        ]
      },
      "status": {
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "openshift",
        "selfLink": "/oapi/v1/projectsopenshift",
        "uid": "a821d02b-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "209",
        "creationTimestamp": "2017-05-27T07:25:27Z",
        "annotations": {
          "openshift.io/sa.scc.mcs": "s0:c6,c0",
          "openshift.io/sa.scc.supplemental-groups": "1000030000/10000",
          "openshift.io/sa.scc.uid-range": "1000030000/10000"
        }
      },
      "spec": {
        "finalizers": [
          "kubernetes",
          "openshift.io/origin"
        ]
      },
      "status": {
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "openshift-infra",
        "selfLink": "/oapi/v1/projectsopenshift-infra",
        "uid": "a6d0e4ad-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "2938",
        "creationTimestamp": "2017-05-27T07:25:25Z",
        "annotations": {
          "openshift.io/sa.initialized-roles": "true",
          "openshift.io/sa.scc.mcs": "s0:c5,c0",
          "openshift.io/sa.scc.supplemental-groups": "1000020000/10000",
          "openshift.io/sa.scc.uid-range": "1000020000/10000"
        }
      },
      "spec": {
        "finalizers": [
          "kubernetes",
          "openshift.io/origin"
        ]
      },
      "status": {
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "san",
        "selfLink": "/oapi/v1/projectssan",
        "uid": "da27de1c-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "484",
        "creationTimestamp": "2017-05-27T07:26:51Z",
        "annotations": {
          "openshift.io/description": "",
          "openshift.io/display-name": "",
          "openshift.io/requester": "san",
          "openshift.io/sa.scc.mcs": "s0:c6,c5",
          "openshift.io/sa.scc.supplemental-groups": "1000040000/10000",
          "openshift.io/sa.scc.uid-range": "1000040000/10000"
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
  ]
}
```

### 获取一个租户

```
GET /oapi/v1/projects/:name
```

```
GET /oapi/v1/projects/san
Authorization: Bearer NLFk0H5o-5F9aRkvPki6IUacHt-MlWseQSb6R_yIcpo

HTTP/1.1 200 OK
Cache-Control: no-store
Content-Type: application/json
Date: Wed, 07 Jun 2017 03:45:57 GMT
Content-Length: 702
```
```json


{
  "kind": "Project",
  "apiVersion": "v1",
  "metadata": {
    "name": "san",
    "selfLink": "/oapi/v1/projectssan",
    "uid": "da27de1c-42ad-11e7-95b5-f0def1203974",
    "resourceVersion": "484",
    "creationTimestamp": "2017-05-27T07:26:51Z",
    "annotations": {
      "openshift.io/description": "",
      "openshift.io/display-name": "",
      "openshift.io/requester": "san",
      "openshift.io/sa.scc.mcs": "s0:c6,c5",
      "openshift.io/sa.scc.supplemental-groups": "1000040000/10000",
      "openshift.io/sa.scc.uid-range": "1000040000/10000"
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


### 创建一个租户

```
POST /oapi/v1/projectrequests
```

```
POST /oapi/v1/projectrequests HTTP/1.1

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

{"apiVersion":"v1","kind":"ProjectRequest","metadata":{"name":"hell"},"displayName":"测试租户","description":"测试一下租户接口"}

HTTP/1.1 201
status: 201
cache-control: no-store
content-type: application/json
content-length: 754
date: Wed, 07 Jun 2017 07:42:32 GMT
```
```json
{
  "kind": "Project",
  "apiVersion": "v1",
  "metadata": {
    "name": "hell",
    "selfLink": "/oapi/v1/projectrequestshe1ll",
    "uid": "dda92906-4b54-11e7-8d81-f0def1203974",
    "resourceVersion": "193481",
    "creationTimestamp": "2017-06-07T07:42:32Z",
    "annotations": {
      "openshift.io/description": "测试一下租户接口",
      "openshift.io/display-name": "测试租户",
      "openshift.io/requester": "san",
      "openshift.io/sa.scc.mcs": "s0:c9,c4",
      "openshift.io/sa.scc.supplemental-groups": "1000080000/10000",
      "openshift.io/sa.scc.uid-range": "1000080000/10000"
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




### 删除一个租户

```
DELETE /oapi/v1/projects/:name
```

```
DELETE /oapi/v1/projects/san HTTP/1.1

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

```
```json
{
  "kind": "Status",
  "apiVersion": "v1",
  "metadata": {},
  "status": "Success",
  "code": 200
}
```

### 获取用户角色绑定

```
GET /oapi/v1/namespaces/:name/rolebindings
```

```
GET /oapi/v1/namespaces/san/rolebindings

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

```
```json
{
  "kind": "RoleBindingList",
  "apiVersion": "v1",
  "metadata": {
    "selfLink": "/oapi/v1/namespaces/san/rolebindings"
  },
  "items": [
    {
      "metadata": {
        "name": "admin",
        "namespace": "san",
        "selfLink": "/oapi/v1/namespaces/san/rolebindings/admin",
        "uid": "da44079a-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "494",
        "creationTimestamp": "2017-05-27T07:26:51Z"
      },
      "userNames": [
        "san"
      ],
      "groupNames": null,
      "subjects": [
        {
          "kind": "User",
          "name": "san"
        }
      ],
      "roleRef": {
        "name": "admin"
      }
    },
    {
      "metadata": {
        "name": "system:deployers",
        "namespace": "san",
        "selfLink": "/oapi/v1/namespaces/san/rolebindings/system%3Adeployers",
        "uid": "da3dd702-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "489",
        "creationTimestamp": "2017-05-27T07:26:51Z"
      },
      "userNames": [
        "system:serviceaccount:san:deployer"
      ],
      "groupNames": null,
      "subjects": [
        {
          "kind": "ServiceAccount",
          "namespace": "san",
          "name": "deployer"
        }
      ],
      "roleRef": {
        "name": "system:deployer"
      }
    },
    {
      "metadata": {
        "name": "system:image-builders",
        "namespace": "san",
        "selfLink": "/oapi/v1/namespaces/san/rolebindings/system%3Aimage-builders",
        "uid": "da3052c4-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "483",
        "creationTimestamp": "2017-05-27T07:26:51Z"
      },
      "userNames": [
        "system:serviceaccount:san:builder"
      ],
      "groupNames": null,
      "subjects": [
        {
          "kind": "ServiceAccount",
          "namespace": "san",
          "name": "builder"
        }
      ],
      "roleRef": {
        "name": "system:image-builder"
      }
    },
    {
      "metadata": {
        "name": "system:image-pullers",
        "namespace": "san",
        "selfLink": "/oapi/v1/namespaces/san/rolebindings/system%3Aimage-pullers",
        "uid": "da28de95-42ad-11e7-95b5-f0def1203974",
        "resourceVersion": "482",
        "creationTimestamp": "2017-05-27T07:26:51Z"
      },
      "userNames": null,
      "groupNames": [
        "system:serviceaccounts:san"
      ],
      "subjects": [
        {
          "kind": "SystemGroup",
          "name": "system:serviceaccounts:san"
        }
      ],
      "roleRef": {
        "name": "system:image-puller"
      }
    },
    {
      "metadata": {
        "name": "view",
        "namespace": "san",
        "selfLink": "/oapi/v1/namespaces/san/rolebindings/view",
        "uid": "e3c09112-46ac-11e7-8d81-f0def1203974",
        "resourceVersion": "89687",
        "creationTimestamp": "2017-06-01T09:30:02Z"
      },
      "userNames": [
        "dev"
      ],
      "groupNames": null,
      "subjects": [
        {
          "kind": "User",
          "name": "dev"
        }
      ],
      "roleRef": {
        "name": "view"
      }
    }
  ]
}
```


### 修改用户角色绑定

```
PUT /oapi/v1/namespaces/:name/rolebindings/:role
```

```
PUT /oapi/v1/namespaces/san/rolebindings/view

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

{"kind":"RoleBinding","apiVersion":"v1","metadata":{"name":"view","namespace":"san","selfLink":"/oapi/v1/namespaces/san/rolebindings/view","uid":"e3c09112-46ac-11e7-8d81-f0def1203974","resourceVersion":"89687","creationTimestamp":"2017-06-01T09:30:02Z"},"roleRef":{"name":"view"},"subjects":[{"kind":"User","name":"dev"},{"name":"test","kind":"User","namespace":"san"}],"userNames":null,"groupNames":null}
```
```json
{
  "kind": "RoleBinding",
  "apiVersion": "v1",
  "metadata": {
    "name": "view",
    "namespace": "san",
    "selfLink": "/oapi/v1/namespaces/san/rolebindings/view",
    "uid": "e3c09112-46ac-11e7-8d81-f0def1203974",
    "resourceVersion": "89972",
    "creationTimestamp": "2017-06-01T09:30:02Z"
  },
  "userNames": [
    "dev",
    "test"
  ],
  "groupNames": null,
  "subjects": [
    {
      "kind": "User",
      "name": "dev"
    },
    {
      "kind": "User",
      "namespace": "san",
      "name": "test"
    }
  ],
  "roleRef": {
    "name": "view"
  }
}
```

### 添加Broker

```
POST /oapi/v1/servicebrokers

```

```
POST /oapi/v1/servicebrokers

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

{"kind": "ServiceBroker","apiVersion": "v1","metadata":{"name":"rds"},"spec":{"url":"http://localhost:9900","username":"test","password":"test"}

```
```json


{
  "kind": "ServiceBroker",
  "apiVersion": "v1",
  "metadata": {
    "name": "rds",
    "selfLink": "/oapi/v1/servicebrokers/rds",
    "uid": "173c5c74-4b69-11e7-a220-fa163efdbea8",
    "resourceVersion": "15211241",
    "creationTimestamp": "2017-06-07T10:07:19Z"
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
### 删除broker

```
DELETE /oapi/v1/servicebrokers/:name
```

### 获取后端服务列表

```
GET /oapi/v1/namespaces/openshift/backingservices
```

```
GET /oapi/v1/namespaces/openshift/backingservices

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json
```
```json
{
  "kind": "BackingServiceList",
  "apiVersion": "v1",
  "metadata": {
    "selfLink": "/oapi/v1/namespaces/openshift/backingservices/",
    "resourceVersion": "18994861"
  },
  "items": [
    {
      "metadata": {
        "name": "DataCanvas",
        "generateName": "DataCanvas",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/DataCanvas",
        "uid": "0db6e814-d3e9-11e6-b6b0-00163e00009d",
        "resourceVersion": "13090767",
        "creationTimestamp": "2017-01-06T08:20:59Z",
        "labels": {
          "asiainfo.io/servicebroker": "DataCanvas"
        }
      },
      "spec": {
        "name": "DataCanvas",
        "id": "a1b82700-c76a-11e6-af08-3c970eb7826d",
        "description": "datacanvas for elastic hadoop cluster",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "BigData"
        ],
        "requires": null,
        "metadata": {
          "displayName": "CanvasD",
          "documentationUrl": "http://doc.datacanvas.io/",
          "imageUrl": "http://www.zetyun.com/images/content/clouds.png",
          "longDescription": "DataCanvas EDS（Elastic Database Stack） DataCanvas APS (Analytic Platform Service)",
          "providerDisplayName": "citic",
          "supportUrl": "http://www.zetyun.com/"
        },
        "plans": [
          {
            "name": "cluster-plan1",
            "id": "35325d21-c76b-11e6-a898-3c970eb7826d",
            "description": "3 clusters",
            "metadata": {
              "bullets": [
                "1 TB of Disk",
                "3 clusters"
              ],
              "costs": [
                {
                  "amount": {
                    "eur": 19,
                    "usd": 30
                  },
                  "unit": "MONTHLY"
                }
              ],
              "displayName": "3Cluster",
              "customize": null
            },
            "free": true
          }
        ],
        "dashboard_client": null
      },
      "status": {
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "ETCD",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/ETCD",
        "uid": "577909a2-d3eb-11e6-b6b0-00163e00009d",
        "resourceVersion": "18077521",
        "creationTimestamp": "2017-01-06T08:37:21Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "ETCD",
        "id": "5E397661-1385-464A-8DB7-9C4DF8CC0662",
        "description": "ETCD是一个高可用的键值存储系统,主要用于共享配置和服务发现。版本：v2.3.0",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "etcd",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "ETCD",
          "documentationUrl": "https://coreos.com/etcd/docs/latest",
          "imageUrl": "pub/assets/ETCD.png",
          "longDescription": "Managed, highly available etcd clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "https://coreos.com"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "256D56C0-B83D-11E6-B227-2714EF851DCA",
            "description": "单独持久化ETCD实例",
            "metadata": {
              "bullets": [
                "10 GB of Disk",
                "20 connections"
              ],
              "costs": null,
              "displayName": "Volumes Standalone",
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
    {
      "metadata": {
        "name": "Greenplum",
        "generateName": "sb-db",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Greenplum",
        "uid": "bb546a98-c78c-11e6-8ea6-00163e00009d",
        "resourceVersion": "16456149",
        "creationTimestamp": "2016-12-21T14:49:53Z",
        "labels": {
          "asiainfo.io/servicebroker": "sb-db"
        }
      },
      "spec": {
        "name": "Greenplum",
        "id": "98E2AFE3-7279-40CA-B04E-74276B3FF4B2",
        "description": "Greenplum是Pivotal开源的MPP数据库。版本：4.3.9",
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
          "providerDisplayName": "citic",
          "supportUrl": "http://greenplum.org"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "B48A3972-536F-47A6-B04F-A5344F4DC5E0",
            "description": "单独Greenplum实例",
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
    {
      "metadata": {
        "name": "HBase",
        "generateName": "ocdp",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/HBase",
        "uid": "f96792f5-2c02-11e7-bf66-00163e00009d",
        "resourceVersion": "16998110",
        "creationTimestamp": "2017-04-28T11:08:14Z",
        "labels": {
          "asiainfo.io/servicebroker": "ocdp"
        }
      },
      "spec": {
        "name": "HBase",
        "id": "d9845ade-9410-4c7f-8689-4e032c1a8450",
        "description": "HBase是Hadoop的面向列的分布式非关系型数据库。版本：v1.1.2",
        "bindable": false,
        "plan_updateable": false,
        "tags": [
          "hbase",
          "database"
        ],
        "requires": [],
        "metadata": {
          "displayName": "HBase",
          "documentationUrl": "http://hbase.apache.org/",
          "imageUrl": "pub/assets/HBase.png",
          "longDescription": "HBase是一个开源的，非关系型的，分布式数据库，类似于Google的BigTable。",
          "providerDisplayName": "citic",
          "supportUrl": "http://hbase.apache.org/book.html"
        },
        "plans": [
          {
            "name": "shared",
            "id": "f658e391-b7d6-4b72-9e4c-c754e4943ae1",
            "description": "共享HBase实例",
            "metadata": {
              "bullets": [
                "HBase Maximun Tables:10",
                "HBase Maximun Regions:10"
              ],
              "costs": [
                {
                  "amount": {
                    "usd": 0
                  },
                  "unit": "MONTHLY"
                }
              ],
              "displayName": "",
              "customize": {
                "maximumRegionsQuota": {
                  "default": 100,
                  "max": 1000,
                  "price": 10,
                  "step": 10,
                  "unit": "个",
                  "desc": "HBase命名空间允许的最大的region数目"
                },
                "maximumTablesQuota": {
                  "default": 10,
                  "max": 100,
                  "price": 10,
                  "step": 10,
                  "unit": "个",
                  "desc": "HBase命名空间允许的最大的表数目"
                }
              }
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
    {
      "metadata": {
        "name": "HDFS",
        "generateName": "ocdp",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/HDFS",
        "uid": "f96663e3-2c02-11e7-bf66-00163e00009d",
        "resourceVersion": "16998111",
        "creationTimestamp": "2017-04-28T11:08:14Z",
        "labels": {
          "asiainfo.io/servicebroker": "ocdp"
        }
      },
      "spec": {
        "name": "HDFS",
        "id": "ae67d4ba-5c4e-4937-a68b-5b47cfe356d8",
        "description": "HDFS是Hadoop的分布式文件系统。版本：v2.7.1",
        "bindable": false,
        "plan_updateable": false,
        "tags": [
          "hdfs",
          "storage"
        ],
        "requires": [],
        "metadata": {
          "displayName": "HDFS",
          "documentationUrl": "http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-hdfs/HdfsUserGuide.html",
          "imageUrl": "pub/assets/HDFS.png",
          "longDescription": "Hadoop分布式文件系统(HDFS)是一个的分布式的，可扩展的，轻量级的文件系统。",
          "providerDisplayName": "citic",
          "supportUrl": "http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-hdfs/HdfsUserGuide.html"
        },
        "plans": [
          {
            "name": "shared",
            "id": "72150b09-1025-4533-8bae-0e04ef68ac13",
            "description": "共享HDFS实例",
            "metadata": {
              "bullets": [
                "Name Space Quota:1000",
                "Storage Space Quota (GB):20"
              ],
              "costs": [
                {
                  "amount": {
                    "usd": 0
                  },
                  "unit": "MONTHLY"
                }
              ],
              "displayName": "",
              "customize": {
                "nameSpaceQuota": {
                  "default": 1000,
                  "max": 100000,
                  "price": 10,
                  "step": 10,
                  "desc": "HDFS目录允许创建的最大文件数目"
                },
                "storageSpaceQuota": {
                  "default": 1024,
                  "max": 102400,
                  "price": 10,
                  "step": 10,
                  "unit": "GB",
                  "desc": "HDFS目录的最大存储容量"
                }
              }
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
    {
      "metadata": {
        "name": "Hive",
        "generateName": "ocdp",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Hive",
        "uid": "f96895e8-2c02-11e7-bf66-00163e00009d",
        "resourceVersion": "16998112",
        "creationTimestamp": "2017-04-28T11:08:14Z",
        "labels": {
          "asiainfo.io/servicebroker": "ocdp"
        }
      },
      "spec": {
        "name": "Hive",
        "id": "2ef26018-003d-4b2b-b786-0481d4ee9fa3",
        "description": "Hive是Hadoop的数据仓库。版本：v1.2.1",
        "bindable": false,
        "plan_updateable": false,
        "tags": [
          "hive",
          "datawarehouse"
        ],
        "requires": [],
        "metadata": {
          "displayName": "Hive",
          "documentationUrl": "http://hive.apache.org/",
          "imageUrl": "pub/assets/Hive.png",
          "longDescription": "Hive是一个可以通过SQL去读写，管理存储在分布式存储系统上的大规模数据集的数据仓库解决方案。",
          "providerDisplayName": "citic",
          "supportUrl": "https://cwiki.apache.org/confluence/display/Hive/Home#Home-UserDocumentation"
        },
        "plans": [
          {
            "name": "shared",
            "id": "aa7e364f-fdbf-4187-b60a-218b6fa398ed",
            "description": "共享Hive实例",
            "metadata": {
              "bullets": [
                "Shared Hive Server (GB):20",
                "Yarn Queue Quota (GB):4"
              ],
              "costs": [
                {
                  "amount": {
                    "usd": 0
                  },
                  "unit": "MONTHLY"
                }
              ],
              "displayName": "",
              "customize": {
                "hiveStorageQuota": {
                  "default": 1024,
                  "max": 102400,
                  "price": 10,
                  "step": 10,
                  "unit": "GB",
                  "desc": "Hive数据库的最大存储容量"
                },
                "yarnQueueQuota": {
                  "default": 10,
                  "max": 100,
                  "price": 10,
                  "step": 10,
                  "unit": "GB",
                  "desc": "Yarn队列的最大容量"
                }
              }
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
    {
      "metadata": {
        "name": "Kafka",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Kafka",
        "uid": "cf911878-d3ec-11e6-b6b0-00163e00009d",
        "resourceVersion": "18077522",
        "creationTimestamp": "2017-01-06T08:47:52Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "Kafka",
        "id": "9972923D-0787-4271-839C-D000BD87E309",
        "description": "Kafka是一种高吞吐量的分布式发布订阅消息系统。版本：v0.9.0",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "kafka",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "Kafka",
          "documentationUrl": "http://kafka.apache.org/documentation.html",
          "imageUrl": "pub/assets/Kafka.png",
          "longDescription": "Managed, highly available kafka clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "http://kafka.apache.org"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "EDCB798A-C03F-11E6-8220-AB033AF07E38",
            "description": "单独持久化kafka实例",
            "metadata": {
              "bullets": [
                "20 GB of Disk",
                "20 connections"
              ],
              "costs": null,
              "displayName": "Volumes Standalone",
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
    {
      "metadata": {
        "name": "MapReduce",
        "generateName": "ocdp",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/MapReduce",
        "uid": "f969ab0c-2c02-11e7-bf66-00163e00009d",
        "resourceVersion": "16998113",
        "creationTimestamp": "2017-04-28T11:08:14Z",
        "labels": {
          "asiainfo.io/servicebroker": "ocdp"
        }
      },
      "spec": {
        "name": "MapReduce",
        "id": "ae0f2324-27a8-415b-9c7f-64ab6cd88d40",
        "description": "MapReduce是Hadoop的分布式计算框架。版本：v2.7.1",
        "bindable": false,
        "plan_updateable": false,
        "tags": [
          "MapReduce",
          "compute engine"
        ],
        "requires": [],
        "metadata": {
          "displayName": "MapReduce",
          "documentationUrl": "http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html ",
          "imageUrl": "pub/assets/MapReduce.png",
          "longDescription": "Hadoop MapReduce是一个可以快速编写能够在大规模集群(数千节点)上处理大规模数据(TB级)的可靠的，容错的应用的软件框架。",
          "providerDisplayName": "citic",
          "supportUrl": "http://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html"
        },
        "plans": [
          {
            "name": "shared",
            "id": "6524c793-0ea5-4456-9a60-ca70271decdc",
            "description": "共享MapReduce实例",
            "metadata": {
              "bullets": [
                "Yarn Queue Quota (GB):4",
                "Name Space Quota:1000",
                "Storage Space Quota (GB):20"
              ],
              "costs": [
                {
                  "amount": {
                    "usd": 0
                  },
                  "unit": "MONTHLY"
                }
              ],
              "displayName": "",
              "customize": {
                "nameSpaceQuota": {
                  "default": 1000,
                  "max": 100000,
                  "price": 10,
                  "step": 10,
                  "desc": "HDFS目录允许创建的最大文件数目"
                },
                "storageSpaceQuota": {
                  "default": 1024,
                  "max": 102400,
                  "price": 10,
                  "step": 10,
                  "unit": "GB",
                  "desc": "HDFS目录的最大存储容量"
                },
                "yarnQueueQuota": {
                  "default": 10,
                  "max": 100,
                  "price": 10,
                  "step": 10,
                  "unit": "GB",
                  "desc": "Yarn队列的最大容量"
                }
              }
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
    {
      "metadata": {
        "name": "MongoDB",
        "generateName": "sb-db",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/MongoDB",
        "uid": "bb5a8a4d-c78c-11e6-8ea6-00163e00009d",
        "resourceVersion": "16456150",
        "creationTimestamp": "2016-12-21T14:49:53Z",
        "labels": {
          "asiainfo.io/servicebroker": "sb-db"
        }
      },
      "spec": {
        "name": "MongoDB",
        "id": "A25DE423-484E-4252-B6FE-EA4F347BCE3D",
        "description": "MongoDB是一个基于分布式文件存储的数据库。版本：3.2.9",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "nosql",
          "MongoDB",
          "DataBase"
        ],
        "requires": null,
        "metadata": {
          "displayName": "MongoDB",
          "documentationUrl": "https://docs.mongodb.org/manual/",
          "imageUrl": "pub/assets/MongoDB.png",
          "longDescription": "MongoDB unleashes the power of software and data for innovators everywhere",
          "providerDisplayName": "citic",
          "supportUrl": "https://www.mongodb.org/"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "E28FB3AE-C237-484F-AC9D-FB0A80223F85",
            "description": "单独MongoDB实例",
            "metadata": {
              "bullets": [
                "20 GB of Disk",
                "20 connections"
              ],
              "costs": [
                {
                  "amount": {
                    "￥": 99
                  },
                  "unit": "MONTHLY"
                }
              ],
              "displayName": "small bucket",
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
    {
      "metadata": {
        "name": "Mysql",
        "generateName": "sb-db",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Mysql",
        "uid": "bb4e528f-c78c-11e6-8ea6-00163e00009d",
        "resourceVersion": "16456151",
        "creationTimestamp": "2016-12-21T14:49:53Z",
        "labels": {
          "asiainfo.io/servicebroker": "sb-db"
        }
      },
      "spec": {
        "name": "Mysql",
        "id": "01e4b605-0a8e-4d19-afbe-bdfb1e42d605",
        "description": "MySQL是WEB应用方面最好的关系数据库管理系统应用软件之一。版本：5.6.35",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "mysql",
          "database"
        ],
        "requires": null,
        "metadata": {
          "displayName": "Mysql",
          "documentationUrl": "http://docs.mysql.com",
          "imageUrl": "pub/assets/Mysql.png",
          "longDescription": "OpenSoure RDMBS Mysql",
          "providerDisplayName": "citic",
          "supportUrl": "http://www.mysql.com"
        },
        "plans": [
          {
            "name": "case sensitive",
            "id": "56660431-6032-43D0-A114-FFA3BF521B66",
            "description": "单独Mysql实例（区分大小写）",
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
          },
          {
            "name": "case insensitive",
            "id": "F6A1B0F6-878E-43CA-B2CA-1134239D0675",
            "description": "单独Mysql实例（不区分大小写）",
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
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "NiFi",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/NiFi",
        "uid": "577cce86-d3eb-11e6-b6b0-00163e00009d",
        "resourceVersion": "18077523",
        "creationTimestamp": "2017-01-06T08:37:21Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "NiFi",
        "id": "BCC10E77-98B6-4EF0-8AFB-E5FD789F712E",
        "description": "NiFi 是一个易于使用、功能强大而且可靠的数据处理和分发系统。版本：v0.6.1",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "nifi",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "NiFi",
          "documentationUrl": "https://nifi.apache.org/docs.html",
          "imageUrl": "pub/assets/NiFiDrop.png",
          "longDescription": "Managed, highly available nifi clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "https://nifi.apache.org"
        },
        "plans": [
          {
            "name": "no volumes",
            "id": "4435A93C-6CC9-45F0-AFB0-EA070361DD6A",
            "description": "单独nifi实例",
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
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "PostgreSQL",
        "generateName": "sb-db",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/PostgreSQL",
        "uid": "bb483f25-c78c-11e6-8ea6-00163e00009d",
        "resourceVersion": "16456152",
        "creationTimestamp": "2016-12-21T14:49:53Z",
        "labels": {
          "asiainfo.io/servicebroker": "sb-db"
        }
      },
      "spec": {
        "name": "PostgreSQL",
        "id": "cb2d4021-5fbc-45c2-92a9-9584583b7ce5",
        "description": "PostgreSQL对象关系型数据库。版本：9.2.18",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "postgresql",
          "database"
        ],
        "requires": null,
        "metadata": {
          "displayName": "Postgresql",
          "documentationUrl": "http://www.postgresql.org/docs/",
          "imageUrl": "pub/assets/PostgreSQL.png",
          "longDescription": "the world most advanced open source database",
          "providerDisplayName": "citic",
          "supportUrl": "http://www.postgresql.org/"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "bd9a94f2-5718-4dde-a773-61ff4ad9e843",
            "description": "单独PostgreSQL实例",
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
    {
      "metadata": {
        "name": "RabbitMQ",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/RabbitMQ",
        "uid": "5777de4e-d3eb-11e6-b6b0-00163e00009d",
        "resourceVersion": "18077524",
        "creationTimestamp": "2017-01-06T08:37:21Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "RabbitMQ",
        "id": "86272DCB-86D5-4039-9E05-894436B8E71D",
        "description": "RabbitMQ是流行的开源消息队列系统。版本v3.6.1",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "rabbitmq",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "RabbitMQ",
          "documentationUrl": "https://www.rabbitmq.com/documentation.html",
          "imageUrl": "pub/assets/RabbitMQ.png",
          "longDescription": "Managed, highly available rabbitmq clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "https://www.rabbitmq.com/"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "28749ee2-6f30-4967-8311-2bf34f9a5421",
            "description": "单独rabbitMQ实例",
            "metadata": {
              "bullets": [
                "10 GB of Disk",
                "20 connections"
              ],
              "costs": null,
              "displayName": "Volumes Standalone",
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
    {
      "metadata": {
        "name": "Redis",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Redis",
        "uid": "48027ac1-d3ee-11e6-b6b0-00163e00009d",
        "resourceVersion": "18077525",
        "creationTimestamp": "2017-01-06T08:58:24Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "Redis",
        "id": "A54BC117-25E3-4E41-B8F7-14FC314D04D3",
        "description": "Redis是一个可基于内存亦可持久化的日志型、Key-Value数据库。版本：v2.8",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "redis",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "Redis",
          "documentationUrl": "http://redis.io/documentation",
          "imageUrl": "pub/assets/Redis.png",
          "longDescription": "Managed, highly available redis clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "http://redis.io"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "f8554827-4f67-4082-84af-6d35475c1ea8",
            "description": "单独持久化Redis实例",
            "metadata": {
              "bullets": [
                "10 GB of Disk",
                "20 connections"
              ],
              "costs": null,
              "displayName": "Volumes Standalone",
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
    {
      "metadata": {
        "name": "Spark",
        "generateName": "ocdp",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Spark",
        "uid": "f96ae2f6-2c02-11e7-bf66-00163e00009d",
        "resourceVersion": "16998114",
        "creationTimestamp": "2017-04-28T11:08:14Z",
        "labels": {
          "asiainfo.io/servicebroker": "ocdp"
        }
      },
      "spec": {
        "name": "Spark",
        "id": "d3b9a485-f038-4605-9b9b-29792f5c61d1",
        "description": "Spark是一种的通用并行计算框架。版本：v1.6.0",
        "bindable": false,
        "plan_updateable": false,
        "tags": [
          "spark",
          "compute engine"
        ],
        "requires": [],
        "metadata": {
          "displayName": "Spark",
          "documentationUrl": "http://spark.apache.org/",
          "imageUrl": "pub/assets/Spark.png",
          "longDescription": "Apache Spark是一个快速的，通用性的集群计算系统。",
          "providerDisplayName": "citic",
          "supportUrl": "http://spark.apache.org/docs/1.6.0/"
        },
        "plans": [
          {
            "name": "shared",
            "id": "5c3d471d-f94a-4bb8-b340-f783f3c15ba1",
            "description": "共享Spark实例",
            "metadata": {
              "bullets": [
                "Yarn Queue Quota (GB):4",
                "Name Space Quota:1000",
                "Storage Space Quota (GB):20"
              ],
              "costs": [
                {
                  "amount": {
                    "usd": 0
                  },
                  "unit": "MONTHLY"
                }
              ],
              "displayName": "",
              "customize": {
                "nameSpaceQuota": {
                  "default": 1000,
                  "max": 100000,
                  "price": 10,
                  "step": 10,
                  "desc": "HDFS目录允许创建的最大文件数目"
                },
                "storageSpaceQuota": {
                  "default": 1024,
                  "max": 102400,
                  "price": 10,
                  "step": 10,
                  "unit": "GB",
                  "desc": "HDFS目录的最大存储容量"
                },
                "yarnQueueQuota": {
                  "default": 10,
                  "max": 100,
                  "price": 10,
                  "step": 10,
                  "unit": "GB",
                  "desc": "Yarn队列的最大容量"
                }
              }
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
    {
      "metadata": {
        "name": "Storm",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Storm",
        "uid": "a1ce5410-dbbd-11e6-a5db-00163e020112",
        "resourceVersion": "18077526",
        "creationTimestamp": "2017-01-16T07:30:19Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "Storm",
        "id": "6555DBC1-E6BC-4F0D-8948-E1B5DF6BD596",
        "description": "Storm为分布式实时计算框架。版本：v0.9.2",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "storm",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "Storm",
          "documentationUrl": "https://storm.apache.org/releases/1.0.1/index.html",
          "imageUrl": "pub/assets/Storm.png",
          "longDescription": "Managed, highly available storm clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "https://storm.apache.org/"
        },
        "plans": [
          {
            "name": "standalone",
            "id": "D0B82741-770A-463C-818F-6E99862367DF",
            "description": "单独Storm实例",
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
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "TableauSite",
        "generateName": "Tableau",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/TableauSite",
        "uid": "18e761ec-c767-11e6-9a4f-00163e0203d4",
        "resourceVersion": "13090769",
        "creationTimestamp": "2016-12-21T10:20:29Z",
        "labels": {
          "asiainfo.io/servicebroker": "Tableau"
        }
      },
      "spec": {
        "name": "TableauSite",
        "id": "37db65ec-135e-4502-8d3a-42096b42f894",
        "description": "A tableauSite instance",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "BI",
          "BigData",
          "Analysis"
        ],
        "requires": null,
        "metadata": {
          "displayName": "TableauSite",
          "documentationUrl": "http://www.tableau.com/products/server",
          "imageUrl": "http://admin:Asiainfo2016@10.247.32.42:8080/static/tableau.jpg",
          "longDescription": "TableauServer unleashes the power of software and data for innovators everywhere",
          "providerDisplayName": "tableau",
          "supportUrl": "http://www.tableau.com/"
        },
        "plans": [
          {
            "name": "Tableau-large",
            "id": "fd342c44-1063-43de-9653-94532da04e6a",
            "description": "A large TableauSite instance with a 20g storage and 20 users quota",
            "metadata": {
              "bullets": [
                "20 GB of Disk",
                "20 users"
              ],
              "costs": [
                {
                  "amount": {
                    "eur": 49,
                    "usd": 99
                  },
                  "unit": "MONTHLY"
                },
                {
                  "amount": {
                    "eur": 0.49,
                    "usd": 0.99
                  },
                  "unit": "1GB of messages over 20GB"
                }
              ],
              "displayName": "Tableau-large",
              "customize": null
            },
            "free": true
          },
          {
            "name": "Tableau-small",
            "id": "1b96a967-c941-45fe-9c7b-74e56bf71769",
            "description": "A small TableauSite instance with a 10g storage and 10 users quota",
            "metadata": {
              "bullets": [
                "10 GB of Disk",
                "10 users"
              ],
              "costs": [
                {
                  "amount": {
                    "eur": 49,
                    "usd": 99
                  },
                  "unit": "MONTHLY"
                },
                {
                  "amount": {
                    "eur": 0.49,
                    "usd": 0.99
                  },
                  "unit": "1GB of messages over 20GB"
                }
              ],
              "displayName": "Tableau-small",
              "customize": null
            },
            "free": true
          }
        ],
        "dashboard_client": null
      },
      "status": {
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "TensorFlow",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/TensorFlow",
        "uid": "75211177-c794-11e6-8ea6-00163e00009d",
        "resourceVersion": "18077527",
        "creationTimestamp": "2016-12-21T15:45:11Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "TensorFlow",
        "id": "2DD1363D-8768-4DAA-BDC3-FB29C10C4A8C",
        "description": "谷歌基于DistBelief研发的第二代AI学习系统。版本：v0.8.0",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "tensorflow",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "TensorFlow",
          "documentationUrl": "https://www.tensorflow.org/get_started",
          "imageUrl": "pub/assets/TensorFlow.png",
          "longDescription": "Managed, highly available tensorflow clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "https://www.tensorflow.org/"
        },
        "plans": [
          {
            "name": "no volumes",
            "id": "BE1CAAF2-CAB7-4B56-AEB4-2A3111225D50",
            "description": "单独tensorflow实例",
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
        "phase": "Active"
      }
    },
    {
      "metadata": {
        "name": "ZooKeeper",
        "generateName": "etcd",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/openshift/backingservices/ZooKeeper",
        "uid": "36f4b735-d40a-11e6-b6b0-00163e00009d",
        "resourceVersion": "18077528",
        "creationTimestamp": "2017-01-06T12:18:21Z",
        "labels": {
          "asiainfo.io/servicebroker": "etcd"
        }
      },
      "spec": {
        "name": "ZooKeeper",
        "id": "FA780A47-4AB2-4035-8638-287538B13416",
        "description": "ZooKeeper是一个开放源码的分布式应用程序协调服务。版本：v3.4.8",
        "bindable": true,
        "plan_updateable": false,
        "tags": [
          "zookeeper",
          "openshift"
        ],
        "requires": null,
        "metadata": {
          "displayName": "ZooKeeper",
          "documentationUrl": "https://zookeeper.apache.org/doc/trunk",
          "imageUrl": "pub/assets/ZooKeeper.png",
          "longDescription": "Managed, highly available zookeeper clusters in the cloud.",
          "providerDisplayName": "citic",
          "supportUrl": "https://zookeeper.apache.org/"
        },
        "plans": [
          {
            "name": "volumes",
            "id": "dffc3555-eb18-4c7b-ac56-bd326b19dcd0",
            "description": "单独持久化ZooKeeper实例",
            "metadata": {
              "bullets": [
                "10 GB of Disk",
                "20 connections"
              ],
              "costs": null,
              "displayName": "Volumes Standalone",
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
    }
  ]
}
```

### 获取单个服务信息

```
GET /oapi/v1/namespaces/openshift/backingservices/:name
```

```
GET /oapi/v1/namespaces/openshift/backingservices/Hive

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

```
```json

{
  "kind": "BackingService",
  "apiVersion": "v1",
  "metadata": {
    "name": "Hive",
    "generateName": "ocdp",
    "namespace": "openshift",
    "selfLink": "/oapi/v1/namespaces/openshift/backingservices/Hive",
    "uid": "f96895e8-2c02-11e7-bf66-00163e00009d",
    "resourceVersion": "16998112",
    "creationTimestamp": "2017-04-28T11:08:14Z",
    "labels": {
      "asiainfo.io/servicebroker": "ocdp"
    }
  },
  "spec": {
    "name": "Hive",
    "id": "2ef26018-003d-4b2b-b786-0481d4ee9fa3",
    "description": "Hive是Hadoop的数据仓库。版本：v1.2.1",
    "bindable": false,
    "plan_updateable": false,
    "tags": [
      "hive",
      "datawarehouse"
    ],
    "requires": [],
    "metadata": {
      "displayName": "Hive",
      "documentationUrl": "http://hive.apache.org/",
      "imageUrl": "pub/assets/Hive.png",
      "longDescription": "Hive是一个可以通过SQL去读写，管理存储在分布式存储系统上的大规模数据集的数据仓库解决方案。",
      "providerDisplayName": "citic",
      "supportUrl": "https://cwiki.apache.org/confluence/display/Hive/Home#Home-UserDocumentation"
    },
    "plans": [
      {
        "name": "shared",
        "id": "aa7e364f-fdbf-4187-b60a-218b6fa398ed",
        "description": "共享Hive实例",
        "metadata": {
          "bullets": [
            "Shared Hive Server (GB):20",
            "Yarn Queue Quota (GB):4"
          ],
          "costs": [
            {
              "amount": {
                "usd": 0
              },
              "unit": "MONTHLY"
            }
          ],
          "displayName": "",
          "customize": {
            "hiveStorageQuota": {
              "default": 1024,
              "max": 102400,
              "price": 10,
              "step": 10,
              "unit": "GB",
              "desc": "Hive数据库的最大存储容量"
            },
            "yarnQueueQuota": {
              "default": 10,
              "max": 100,
              "price": 10,
              "step": 10,
              "unit": "GB",
              "desc": "Yarn队列的最大容量"
            }
          }
        },
        "free": false
      }
    ],
    "dashboard_client": null
  },
  "status": {
    "phase": "Active"
  }
}
```

### 创建一个后端服务实例

```
POST /oapi/v1/namespaces/:name/backingserviceinstances
```

```
POST /oapi/v1/namespaces/san/backingserviceinstances

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

{"kind":"BackingServiceInstance","apiVersion":"v1","metadata":{"name":"hive-instance"},"spec":{"provisioning":{"backingservice_name":"Hive","backingservice_plan_guid":"aa7e364f-fdbf-4187-b60a-218b6fa398ed","parameters":{"hiveStorageQuota":"1024","yarnQueueQuota":"10"}}}}
```
```json
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "hive-instance",
    "namespace": "openshift",
    "selfLink": "/oapi/v1/namespaces/san/backingserviceinstances/hive-instance",
    "uid": "28a67756-4c24-11e7-a50b-00163e00009d",
    "resourceVersion": "19000233",
    "creationTimestamp": "2017-06-08T08:26:24Z"
  },
  "spec": {
    "provisioning": {
      "dashboard_url": "",
      "backingservice_name": "Hive",
      "backingservice_spec_id": "",
      "backingservice_plan_guid": "aa7e364f-fdbf-4187-b60a-218b6fa398ed",
      "backingservice_plan_name": "",
      "parameters": {
        "hiveStorageQuota": "1024",
        "yarnQueueQuota": "10"
      },
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

### 获取一个后端服务实例

```
GET /oapi/v1/namespaces/:name/backingserviceinstances/:instance_name
```

```
GET /oapi/v1/namespaces/san/backingserviceinstances/hive-instance

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

```
```json
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "hive-instance",
    "namespace": "openshift",
    "selfLink": "/oapi/v1/namespaces/openshift/backingserviceinstances/hive-instance",
    "uid": "28a67756-4c24-11e7-a50b-00163e00009d",
    "resourceVersion": "19000235",
    "creationTimestamp": "2017-06-08T08:26:24Z"
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
        "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
        "yarnQueueQuota": "10"
      },
      "credentials": {
        "Hive database": "28a8bbc54c2411e7915100163e020112",
        "host": "zx-dn-03",
        "password": "8127505b-c15c-4568-8025-fae4c0bb655f",
        "port": "10000",
        "uri": "jdbc:hive2://zx-dn-03:10000/28a8bbc54c2411e7915100163e020112;principal=hive/zx-dn-03@EXAMPLE.COM",
        "username": "openshift@EXAMPLE.COM"
      }
    },
    "userprovidedservice": {
      "credentials": null
    },
    "binding": null,
    "bound": 0,
    "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
    "tags": null
  },
  "status": {
    "phase": "Unbound",
    "action": "",
    "last_operation": null
  }
}
```

### 获取后端服务实例列表

```
GET /oapi/v1/namespaces/:name/backingserviceinstances
```

```
GET /oapi/v1/namespaces/san/backingserviceinstances

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json
```
```json
{
  "kind": "BackingServiceInstanceList",
  "apiVersion": "v1",
  "metadata": {
    "selfLink": "/oapi/v1/namespaces/san/backingserviceinstances/",
    "resourceVersion": "19000937"
  },
  "items": [
    {
      "metadata": {
        "name": "hive-instance",
        "namespace": "openshift",
        "selfLink": "/oapi/v1/namespaces/san/backingserviceinstances/hive-instance",
        "uid": "28a67756-4c24-11e7-a50b-00163e00009d",
        "resourceVersion": "19000235",
        "creationTimestamp": "2017-06-08T08:26:24Z"
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
            "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
            "yarnQueueQuota": "10"
          },
          "credentials": {
            "Hive database": "28a8bbc54c2411e7915100163e020112",
            "host": "zx-dn-03",
            "password": "8127505b-c15c-4568-8025-fae4c0bb655f",
            "port": "10000",
            "uri": "jdbc:hive2://zx-dn-03:10000/28a8bbc54c2411e7915100163e020112;principal=hive/zx-dn-03@EXAMPLE.COM",
            "username": "openshift@EXAMPLE.COM"
          }
        },
        "userprovidedservice": {
          "credentials": null
        },
        "binding": null,
        "bound": 0,
        "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
        "tags": null
      },
      "status": {
        "phase": "Unbound",
        "action": "",
        "last_operation": null
      }
    }
  ]
}
```


### 更新一个后端服务实例

```
PUT /oapi/v1/namespaces/:name/backingserviceinstances/:instance_name
```
*更新实例需要将status.patch设置成"Update"*

```
PUT /oapi/v1/namespaces/san/backingserviceinstances/hive-instance

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json

{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "hive-instance",
    "namespace": "openshift",
    "selfLink": "/oapi/v1/namespaces/openshift/backingserviceinstances/hive-instance",
    "uid": "28a67756-4c24-11e7-a50b-00163e00009d",
    "resourceVersion": "19001230",
    "creationTimestamp": "2017-06-08T08:26:24Z",
    "deletionTimestamp": "2017-06-08T08:34:01Z"
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
        "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
        "yarnQueueQuota": "10"
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
      "credentials": {
        "Hive database": "28a8bbc54c2411e7915100163e020112",
        "host": "zx-dn-03",
        "password": "8127505b-c15c-4568-8025-fae4c0bb655f",
        "port": "10000",
        "uri": "jdbc:hive2://zx-dn-03:10000/28a8bbc54c2411e7915100163e020112;principal=hive/zx-dn-03@EXAMPLE.COM",
        "username": "openshift@EXAMPLE.COM"
      }
    },
    "userprovidedservice": {
      "credentials": null
    },
    "binding": null,
    "bound": 0,
    "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
    "tags": null
  },
  "status": {
    "phase": "Unbound",
    "patch":"Update",
    "action": "_ToDelete",
    "last_operation": null
  }
}
```
```json
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "hive-instance",
    "namespace": "openshift",
    "selfLink": "/oapi/v1/namespaces/openshift/backingserviceinstances/hive-instance",
    "uid": "28a67756-4c24-11e7-a50b-00163e00009d",
    "resourceVersion": "19001230",
    "creationTimestamp": "2017-06-08T08:26:24Z",
    "deletionTimestamp": "2017-06-08T08:34:01Z"
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
        "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
        "yarnQueueQuota": "10"
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
      "credentials": {
        "Hive database": "28a8bbc54c2411e7915100163e020112",
        "host": "zx-dn-03",
        "password": "8127505b-c15c-4568-8025-fae4c0bb655f",
        "port": "10000",
        "uri": "jdbc:hive2://zx-dn-03:10000/28a8bbc54c2411e7915100163e020112;principal=hive/zx-dn-03@EXAMPLE.COM",
        "username": "openshift@EXAMPLE.COM"
      }
    },
    "userprovidedservice": {
      "credentials": null
    },
    "binding": null,
    "bound": 0,
    "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
    "tags": null
  },
  "status": {
    "phase": "Unbound",
    "patch":"Updating",
    "action": "_ToDelete",
    "last_operation": null
  }
}
```


### 删除一个后端服务实例

```
DELETE /oapi/v1/namespaces/:name/backingserviceinstances/:instance_name
```

```
DELETE /oapi/v1/namespaces/san/backingserviceinstances/hive-instance

Authorization: Bearer ZIu0vDcm7a0_zH00_o6mFh9iivNWbR0oRPK6S3NvsT0
Content-Type: application/json
```
```json
{
  "kind": "BackingServiceInstance",
  "apiVersion": "v1",
  "metadata": {
    "name": "hive-instance",
    "namespace": "openshift",
    "selfLink": "/oapi/v1/namespaces/openshift/backingserviceinstances/hive-instance",
    "uid": "28a67756-4c24-11e7-a50b-00163e00009d",
    "resourceVersion": "19001230",
    "creationTimestamp": "2017-06-08T08:26:24Z",
    "deletionTimestamp": "2017-06-08T08:34:01Z"
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
        "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
        "yarnQueueQuota": "10"
      },
      "credentials": {
        "Hive database": "28a8bbc54c2411e7915100163e020112",
        "host": "zx-dn-03",
        "password": "8127505b-c15c-4568-8025-fae4c0bb655f",
        "port": "10000",
        "uri": "jdbc:hive2://zx-dn-03:10000/28a8bbc54c2411e7915100163e020112;principal=hive/zx-dn-03@EXAMPLE.COM",
        "username": "openshift@EXAMPLE.COM"
      }
    },
    "userprovidedservice": {
      "credentials": null
    },
    "binding": null,
    "bound": 0,
    "instance_id": "28a8bbc5-4c24-11e7-9151-00163e020112",
    "tags": null
  },
  "status": {
    "phase": "Unbound",
    "action": "_ToDelete",
    "last_operation": null
  }
}
```
