

# HOWTO install Gluu Cluster MGR in a XENIAL VM (ubuntu 16.04)

First add Gluu repo:
```
# echo "deb https://repo.gluu.org/ubuntu/ xenial-devel main" > /etc/apt/sources.list.d/gluu-repo.list
```

Accept the repo gpg key:

```
# curl https://repo.gluu.org/ubuntu/gluu-apt.key | apt-key add -
```

Install the meta package:
```
# apt-get update
# apt-get install gluu-cluster-mgr
```
