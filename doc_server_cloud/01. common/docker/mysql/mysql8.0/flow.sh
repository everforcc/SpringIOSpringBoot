### 实际还是手动操作,这边提供流程
#!/bin/bash
set -e

#查看mysql服务的状态，方便调试，这条语句可以删除
echo `service mysql status`

echo '1.启动mysql....'
#启动mysql
#service mysql start
#sleep 3
echo `service mysql status`

echo '2.创建用户....'
#导入数据
mysql < /mysql/privileges.sql
echo '3.创建用户完毕....'

#sleep 3
#echo `service mysql status`

#重新设置mysql密码
#echo '4.恢复数据....'
#mysql < /mysql/backup.sql
#echo '5.恢复数据完毕....'

#sleep 3
echo `service mysql status`
echo `mysql容器启动完毕,且数据导入成功`

tail -f /dev/null