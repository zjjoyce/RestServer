# this shell can deploy src_file to dest_file to all server with tag
# param including:src_file,dest_file,tag
# usage:deploy.sh /home/ocdc/test.keytab /home/ocdc/test/keytab dacp
# author:yujing2
set -x
src_file=$1
dest_file=$2
tag=$3

dir=`dirname $0`
current=`cd $dir;pwd`
servers=`cat $current/server.properties |grep -v "#"|grep "server"|awk -F ',' '{print $1}'`
code=0
for i in $servers
do
	scp $src_file $i:$dest_file
	let code=$code+$?
done
## if return code is 0 meaning success,else is failed
if[ $code -eq 0 ];then
    echo "success"
else
    echo "failed"
fi
