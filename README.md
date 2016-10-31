# message-consumer
### Table of Contents 
1. [About](https://github.com/GluuFederation/message-consumer#about)
2. [How it works](https://github.com/GluuFederation/message-consumer#how-it-works)
2. [Custom properties](https://github.com/GluuFederation/message-consumer#external-properties)
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
