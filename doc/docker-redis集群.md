第一步：
在/home下创建redis_cluster目录
在redis_cluster目录下创建 slave01~slave06 目录，每个目录包含redis700x.conf配置文件(本人使用的是7001-7006端口)
配置文件如下
port ${PORT}                                       ##节点端口
protected-mode no                                  ##开启集群模式
cluster-enabled yes                                ##cluster集群模式
cluster-config-file nodes.conf                     ##集群配置名
cluster-node-timeout 5000                          ##超时时间
cluster-announce-ip 192.168.XX.XX                  ##实际为各节点网卡分配ip  先用上网关ip代替
cluster-announce-port ${PORT}                      ##节点映射端口
cluster-announce-bus-port 1${PORT}                 ##节点总线端口
appendonly yes                                     ##持久化模式

第二步：创建自定义network

docker network create redis-net

第三步：

(docker pull redis:5.0.8)
docker run  -p 7001:7001 -p 17001:17001 --name redis-slave1 -d -v /home/redis_cluster/slave01/redis7001.conf:/usr/local/etc/redis/redis.conf --net redis-net --restart=always redis:5.0.8  (7001-7006)

第四步：查看容器分配ip

docker network inspect redis-net

第五步：修改配置文件并重启容器（7001-7006）

port 7001
protected-mode no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 172.18.0.2 #修改成容器分配的ip
cluster-announce-port 7001
cluster-announce-bus-port 17001
appendonly yes

第六步：进入其中一个节点创建集群

docker exec -it redis-slave1 bash
redis-cli --cluster create 172.18.0.2:7001 172.18.0.3:7002 172.18.0.4:7003 172.18.0.5:7004 172.18.0.6:7005 172.18.0.7:7006 --cluster-replicas 1

#具体显示
root@7cd530eaaf05:/data# redis-cli --cluster create 172.18.0.2:7001 172.18.0.3:7002 172.18.0.4:7003 172.18.0.5:7004 172.18.0.6:7005 172.18.0.7:7006 --cluster-replicas 1
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 172.18.0.6:7005 to 172.18.0.2:7001
Adding replica 172.18.0.7:7006 to 172.18.0.3:7002
Adding replica 172.18.0.5:7004 to 172.18.0.4:7003
M: 8480915e9f03dd99b761b7be22693bdeb5fe315d 172.18.0.2:7001
   slots:[0-5460] (5461 slots) master
M: 3526717c4b937fd950e888662ac0a10b4be3d134 172.18.0.3:7002
   slots:[5461-10922] (5462 slots) master
M: 56ec6d160e66e9d1af5494f2fbdbc9a4b996b6cb 172.18.0.4:7003
   slots:[10923-16383] (5461 slots) master
S: abe15c3d5b1abdffec4819c406723a145b542f20 172.18.0.5:7004
   replicates 56ec6d160e66e9d1af5494f2fbdbc9a4b996b6cb
S: 4b218fdae7a3ce1fb0c41521d4f3a05655527db8 172.18.0.6:7005
   replicates 8480915e9f03dd99b761b7be22693bdeb5fe315d
S: 123cb19f9fb73ddceb6d1eabfcc43a209ebefea7 172.18.0.7:7006
   replicates 3526717c4b937fd950e888662ac0a10b4be3d134
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
...
>>> Performing Cluster Check (using node 172.18.0.2:7001)
M: 8480915e9f03dd99b761b7be22693bdeb5fe315d 172.18.0.2:7001
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: 56ec6d160e66e9d1af5494f2fbdbc9a4b996b6cb 172.18.0.4:7003
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 4b218fdae7a3ce1fb0c41521d4f3a05655527db8 172.18.0.6:7005
   slots: (0 slots) slave
   replicates 8480915e9f03dd99b761b7be22693bdeb5fe315d
S: 123cb19f9fb73ddceb6d1eabfcc43a209ebefea7 172.18.0.7:7006
   slots: (0 slots) slave
   replicates 3526717c4b937fd950e888662ac0a10b4be3d134
M: 3526717c4b937fd950e888662ac0a10b4be3d134 172.18.0.3:7002
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: abe15c3d5b1abdffec4819c406723a145b542f20 172.18.0.5:7004
   slots: (0 slots) slave
   replicates 56ec6d160e66e9d1af5494f2fbdbc9a4b996b6cb
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
root@7cd530eaaf05:/data# redis-cli -c -p 7001  
127.0.0.1:7001> info replication
# Replication
role:master
connected_slaves:1
slave0:ip=172.18.0.6,port=7005,state=online,offset=28,lag=1
master_replid:c06645b1aa7ceb946a94794b95fb8bb9d45b2497
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:28
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:28

至此集群创建成功