#请设置您的用户名
set author=double

set sqlName=%1
#远程版本号
curl http://192.168.0.99:8002/circle.version > v.tmp
set /p version=<v.tmp
set prefix=V1.0.1.%version%
set sqlFile=%prefix%__%sqlName%.sql
set now=%date:~0,4%%date:~5,2%%date:~8,2%
echo -- auto gen by %author% %now% > %sqlFile%
del /f v.tmp
echo 'create sql file success'
