topic_name=$1
hosts="zx-dn-10
zx-dn-11
zx-dn-12
zx-dn-13
zx-dn-14"
#zx-bdi-01
#zx-bdi-02
#zx-bdi-03

quotaSum=0
huanSuanG=1048576
for i in $hosts
do
	quota=`ssh $i "du -sk /hdfs/data*/kafka-logs/${topic_name}"|awk -F '\t' '{sum+=$1} END {print sum}'`
	let quotaSum=quotaSum+quota
done
let quotaSum=$quotaSum/$huanSuanG
echo $quotaSum
