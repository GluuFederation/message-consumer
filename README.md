# message-consumer
### Table of Contents 
1. [About](https://github.com/GluuFederation/message-consumer#about)
2. [How it works](https://github.com/GluuFederation/message-consumer#how-it-works)
2. [External properties](https://github.com/GluuFederation/message-consumer#external-properties)
3. [RESTful API](https://github.com/GluuFederation/message-consumer#restful-api)
4. [Database schema](https://github.com/GluuFederation/message-consumer#database-schema)

#About
The goal of this app to centralize all logs in one place and to provide a quick access to logging data by exposing RESTful API for searching with custom conditions. Roots of this project are drawn to the following [issue](https://github.com/GluuFederation/oxAuth/issues/307).

This version is uses [activemq](http://activemq.apache.org/) messaging server and [postgresql](https://www.postgresql.org/) database to store logging data.

#How it works

#External properties
 Besides others standard spring boot properties, the following could also be customized:
 * `message-consumer.oauth2-audit.destination` - defines activemq queue name for oauth2 audit logging
 * `message-consumer.oauth2-audit.days-after-logs-can-be-deleted` - defines how many days the oauth2 audit logging data should be kept
 * `message-consumer.oauth2-audit.cron-for-log-cleaner` - defines cron expression for oauth2 audit logging data cleaner
 * `message-consumer.oxauth-server.destination` - defines activemq queue name for oxauth server logs
 * `message-consumer.oxauth-server.days-after-logs-can-be-deleted` - defines how many days the oxauth server logging data should be kept
 * `message-consumer.oxauth-server.cron-for-log-cleaner` - defines cron expression for oxauth server logging data cleaner
 
#RESTful API
**Important notes**.

In order to perform search by date the following date format `yyyy-MM-dd HH:mm:ss.SSS` MUST be used, e.g:`/api/oauth2-audit-logs/search/query?fromDate=2016-10-03%2015:53:47.509`.

All string params are matched exactly e.g: `/api/oauth2-audit-logs/search/query?ip=10.0.2.2`, except `scope` and `formattedMessage` - they are searched using 'like' query, e.g: `scope like concat('%', :scope,'%')`

Default page size for all requests is 20, and max page size is 100. These properties could be configured in application-*.properties file.

* oauth2 audit logs
    * Get all logs: `/api/oauth2-audit-logs{?page,size,sort}`
    * Get single log:`/api/oauth2-audit-logs/{id}`
    * Search by custom fields: `/api/oauth2-audit-logs/search/query{?ip,clientId,action,username,scope,success,fromDate,toDate,page,size,sort}`
* oxauth server logs
    * Get all logs: `/api/oxauth-server-logs{?page,size,sort}`
    * Get single log:`/api/oxauth-server-logs/{id}`
    * Search by custom fields: `/api/oxauth-server-logs/search/query{?level,loggerName,formattedMessage,fromDate,toDate,page,size,sort}`
    
    
    
######Example: `curl http://localhost:8080/api/oauth2-audit-logs/search/query?ip=10.0.2.2&username=admin&scope=openid&size=1`
```
{
  "_embedded" : {
    "oauth2-audit-logs" : [ {
      "ip" : "10.0.2.2",
      "action" : "USER_AUTHORIZATION",
      "clientId" : "@!7A06.6C73.B7D4.3983!0001!CFEA.2908!0008!13E4.C749",
      "username" : "admin",
      "scope" : "openid profile email user_name",
      "success" : true,
      "timestamp" : "2016-10-03T12:53:47.509+0000",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/oauth2-audit-logs/3335"
        },
        "oAuth2AuditLoggingEvent" : {
          "href" : "http://localhost:8080/api/oauth2-audit-logs/3335"
        }
      }
    } ]
  },
  "_links" : {
    "first" : {
      "href" : "http://localhost:8080/api/oauth2-audit-logs/search/query?ip=10.0.2.2&username=admin&scope=openid&page=0&size=1"
    },
    "self" : {
      "href" : "http://localhost:8080/api/oauth2-audit-logs/search/query?ip=10.0.2.2&username=admin&scope=openid&size=1"
    },
    "next" : {
      "href" : "http://localhost:8080/api/oauth2-audit-logs/search/query?ip=10.0.2.2&username=admin&scope=openid&page=1&size=1"
    },
    "last" : {
      "href" : "http://localhost:8080/api/oauth2-audit-logs/search/query?ip=10.0.2.2&username=admin&scope=openid&page=1&size=1"
    }
  },
  "page" : {
    "size" : 1,
    "totalElements" : 2,
    "totalPages" : 2,
    "number" : 0
  }
}
```

#Database schema

##List of relations
Schema |                 Name                  | Type  | Owner
--------|:------------------------------------:|------:|-------
 public | oauth2_audit_logging_event            | table | gluu
 public | oxauth_server_logging_event           | table | gluu
 public | oxauth_server_logging_event_exception | table | gluu
 

##oauth2_audit_logging_event
  Column   |            Type             | Modifiers | Storage  | Stats target | Description
-----------|:---------------------------:|----------:|---------:|-------------:|-------------
 id        | bigint                      | not null  | plain    |              |
 action    | character varying(255)      |           | extended |              |
 client_id | character varying(255)      |           | extended |              |
 ip        | character varying(255)      |           | extended |              |
 scope     | character varying(255)      |           | extended |              |
 success   | boolean                     |           | plain    |              |
 timestamp | timestamp without time zone |           | plain    |              |
 username  | character varying(255)      |           | extended |              |
#####Indexes:
```
    "oauth2_audit_logging_event_pkey" PRIMARY KEY, btree (id)
    "oauth2_audit_logging_event_timestamp" btree ("timestamp")
```

##oxauth_server_logging_event
      Column       |            Type             | Modifiers | Storage  | Stats target | Description
-------------------|:---------------------------:|----------:|---------:|-------------:|-------------
 id                | bigint                      | not null  | plain    |              |
 formatted_message | text                        |           | extended |              |
 level             | character varying(255)      |           | extended |              |
 logger_name       | character varying(255)      |           | extended |              |
 timestamp         | timestamp without time zone |           | plain    |              |
#####Indexes:
```
    "oxauth_server_logging_event_pkey" PRIMARY KEY, btree (id)
    "oxauth_server_logging_event_timestamp" btree ("timestamp")
```
#####Referenced by:
```
    TABLE "oxauth_server_logging_event_exception" CONSTRAINT "fktp5p28uolrsx6vlj6annm7255" FOREIGN KEY (oxauth_server_logging_event_id) REFERENCES oxauth_server_logging_event(id)
```

##oxauth_server_logging_event_exception

             Column             |          Type          | Modifiers | Storage  | Stats target | Description
--------------------------------|:----------------------:|----------:|---------:|-------------:|-------------
 id                             | bigint                 | not null  | plain    |              |
 index                          | integer                |           | plain    |              |
 trace_line                     | character varying(255) |           | extended |              |
 oxauth_server_logging_event_id | bigint                 |           | plain    |              |
 
#####Indexes:
```
    "oxauth_server_logging_event_exception_pkey" PRIMARY KEY, btree (id)
```
#####Foreign-key constraints:
```
    "fktp5p28uolrsx6vlj6annm7255" FOREIGN KEY (oxauth_server_logging_event_id) REFERENCES oxauth_server_logging_event(id)
```
