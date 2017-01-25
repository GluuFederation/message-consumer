# message-consumer
### Table of Contents 
1. [About](https://github.com/GluuFederation/message-consumer#about)
2. [How it works](https://github.com/GluuFederation/message-consumer#how-it-works)
3. [Message format](https://github.com/GluuFederation/message-consumer#message-format)
4. [Configure oxauth-server logging](https://github.com/GluuFederation/message-consumer#configure-oxauth-server-logging)
5. [External properties](https://github.com/GluuFederation/message-consumer#external-properties)
6. [RESTful API](https://github.com/GluuFederation/message-consumer#restful-api)
7. [Install and run activeMQ](https://github.com/GluuFederation/message-consumer#install-and-run-activemq)
8. [Database schema](https://github.com/GluuFederation/message-consumer#database-schema)
9. [MySQL](https://github.com/GluuFederation/message-consumer#mysql)
10. [PostgreSQL](https://github.com/GluuFederation/message-consumer#postgresql)
11. [Building and running](https://github.com/GluuFederation/message-consumer#building-and-running)

#About
The goal of this app to centralize all logs in one place and to provide a quick access to logging data by exposing RESTful API for searching with custom conditions. Roots of this project are drawn to the following [issue](https://github.com/GluuFederation/oxAuth/issues/307).

This version is uses [activemq](http://activemq.apache.org/) messaging server and [postgresql](https://www.postgresql.org/) or [mysql](https://www.mysql.com/) (up to your choice) database to store logging data.

#How it works
At first the application tries to connect to activemq using the following url: `failover:(tcp://localhost:61616)?timeout=5000` (could be configured from application properties ).

If connection to message broker succeeded, then the application starts two asynchronous receivers, which reads messages from: `oauth2.audit.logging` and `oxauth.server.logging` (could be modified in `application-{profile}.properties`) queues and stores them in database. It also exposes a discoverable REST API that helps *clients* to read and search through logging messages.

At the same time the application starting scheduled tasks that must delete old messages from database. The cron expression and the number of days that messages must be stored, could be configured from application properties.

#Message format
Messages from `oauth2.audit.logging` queue are expected to be json strings with the following properties:
```
{
	"ip" : "",
	"action" : "",
	"timestamp" : 1480935174312,
	"macAddress" : "",
	"clientId" : "",
	"username" : "",
	"scope" : "",
	"success" : true
}
```
Messages from `oxauth.server.logging` queue are expected to be objects: `org.apache.log4j.spi.LoggingEvent`. To send them [JMSQueueAppender](https://gist.github.com/worm333/fd60ed5535878c423c228ccb7617748e) could be used.

#Configure oxauth-server logging
To configure [oxauth-server](https://github.com/GluuFederation/oxAuth/tree/master/Server) to send logging messages via JMS, just add JMSAppender into [log4j2.xml](https://github.com/GluuFederation/oxAuth/blob/master/Server/src/main/resources/log4j2.xml) e.g:

```
	<JMS name="jmsQueue"
		destinationBindingName="dynamicQueues/oxauth.server.logging"
		factoryName="org.apache.activemq.jndi.ActiveMQInitialContextFactory"
		factoryBindingName="ConnectionFactory"
		providerURL="tcp://localhost:61616"
		userName="admin"
		password="admin">
	</JMS>

```
and `<AppenderRef ref="jmsQueue"/>` to the `root` tag in the [log4j2.xml](https://github.com/GluuFederation/oxAuth/blob/master/Server/src/main/resources/log42j.xml) file.


More about [JMSAppender](https://github.com/apache/logging-log4j2/blob/master/log4j-core/src/main/java/org/apache/logging/log4j/core/appender/mom/JmsAppender.java) you can read [here](https://logging.apache.org/log4j/2.x/manual/appenders.html#JMSAppender).

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
    
    
    
#####Example:
`curl http://localhost:8080/api/oauth2-audit-logs/search/query?ip=10.0.2.2&username=admin&scope=openid&size=1`
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
 mac_address| character varying(255)     |           | extended |              |
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
 trace_line                     | text                   |           | extended |              |
 oxauth_server_logging_event_id | bigint                 |           | plain    |              |
 
#####Indexes:
```
    "oxauth_server_logging_event_exception_pkey" PRIMARY KEY, btree (id)
```
#####Foreign-key constraints:
```
    "fktp5p28uolrsx6vlj6annm7255" FOREIGN KEY (oxauth_server_logging_event_id) REFERENCES oxauth_server_logging_event(id)
```

#MySQL
To create schema for MySQL use [mysql-schema.sql](https://github.com/GluuFederation/message-consumer/blob/master/schema/mysql_schema.sql).

```
source ${path_to_file}/mysql_schema.sql
```

#PostgreSQL
To create schema for PostgreSQL use [postgresql_schema.sql](https://github.com/GluuFederation/message-consumer/blob/master/schema/postgresql_schema.sql).

Edit `postgresql_schema.sql` to change databse Owner and postgresql user. Here is example how to create `gluu` user and create database schema:

```
CREATE USER gluu WITH password 'root';
\i ${path_to_file}/postgresql_schema.sql
```
Note: update `spring.datasource.username` and `spring.datasource.password` in `application-prod-postgresql.properties` after creating new postgresql user.

#Install and run activeMQ
1. Download the activemq zipped tarball file to the Unix machine, using either a browser or a tool, i.e., wget, scp, ftp, etc. (see [Download](http://activemq.apache.org/download.html) -> "The latest stable release")
2. Extract archive, e.g: `tar -xvzf apache-activemq-x.x.x-bin.tar.gz`
3. Edit `apache-activemq-x.x.x-bin.tar.gz/bin/env` to specify the location of your java installation using JAVA_HOME
4. Run activeMQ, e.g: `apache-activemq-x.x.x-bin.tar.gz/bin/activemq start`
5. Optional. Navigate to activeMQ console `http://localhost:8161/`.

#Building and running
[message-consumer](https://github.com/GluuFederation/message-consumer) supports two production profiles: `prod-mysql` and `prod-postgresql`.
Before running this app make sure that MySQL/PostgreSQL is running and schema is created and activeMQ is installed and running. Also check the configuration from `application-{profile}.properties`, make sure that connection properties to activeMQ and database are correct.

Here is an example how to build and run project for MySQL:

```
git clone https://github.com/GluuFederation/message-consumer.git
cd message-consumer/
sudo mvn -Pprod-mysql clean package
java -jar target/message-consumer-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod-mysql
```

Here is an example how to build and run project for PostgreSQL:

```
git clone https://github.com/GluuFederation/message-consumer.git
cd message-consumer/
sudo mvn -Pprod-postgresql clean package
java -jar target/message-consumer-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod-postgresql
```
