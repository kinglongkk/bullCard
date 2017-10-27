@author Double
@date   160822
------------------------------------
工具目的:
    >>为了使开发人员在生成脚本文件时的版本号唯一
------------------------------------
执行方法(Linux)
------------------------------------
    >>使用idea terminal终端工具
        快捷键: alt+f12

    >>使用终端进入脚本目录:
        cd sql/circle

    >>执行命令
        ./genSqlFile.sh                 --->生成    V1.0.0.0001__.sql
            或
        ./genSqlFile.sh A_SYS_UESR      --->生成    V1.0.0.0002__A_SYS_USER.sql

    注:genSqlFile.sh命令行可以带一个参数,如上例中的"A_SYS_UESR"


------------------------------------
执行方法(Window)
------------------------------------
    >>使用idea terminal终端工具
        快捷键: alt+f12

    >>使用终端进入脚本目录:
        cd sql/circle

    >>执行命令
        ./genSqlFile.bat                 --->生成     V1.0.0.0001__.sql
            或
        ./genSqlFile.bat A_SYS_UESR      --->生成     V1.0.0.0002__A_SYS_USER.sql

    注:genSqlFile.bat命令行可以带一个参数,如上例中的"A_SYS_UESR"