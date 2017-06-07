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


#################################

### 创建一个租户

```
POST /oapi/v1/projectrequests
```

```
curl example
```
```json
resp body
```