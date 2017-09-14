# SvnAdmin 
frok from `https://git.oschina.net/hpboys/svnadmin.git`

## 说明：本系统用于配合silksvn服务器使用，不支持Visual SvnServer。

#### 致力于成为一个安全流畅，极简可靠的SVN管理工具
> 主要功能 
- SVN仓库创建，管理；
- SVN用户，用户组创建，管理；
- SVN资源权限授权；
- 用户权限查看，密码更改；
- SVN仓库支持多库模式；

> 获取老司机的带路：

<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=58dddb7a869c97060a2c96fb4eb658d4a50d2a108caaa1eef5dafbf94d1e09b3" rel="nofollow noreferrer noopener"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="SVN管理系统-交流群" title="SVN管理系统-交流群"></a>


> 一、使用源码开发部署步骤：
1. 下载项目源码；
1. 找到文件 test\resources\svnadmin_init.sql 进行执行初始化；
1. 默认root账户：root/root
1. 删除所有账户，进行登录，则可以重新初始化管理员账号；
1. SVN认证账户和登录账户默认一致；


> 二、使用部署包直接部署步骤：
1. 下载最新部署包（[点此下载](https://github.com/tanhaican/svnadmin/releases)）；
1. 找到文件 sql\svnadmin_init.sql 进行执行初始化；
1. 配置数据库连接信息，配置文件位置：WEB-INF/classes/jdbc.properties，修改数据库密码
1. 部署到tomcat等Web容器中即可；环境推荐JDK1.8 / Tomcat8
1. 系统默认root账户：root/root
1. 删除所有账户，进行登录，则可以重新初始化管理员账号；
1. SVN认证账户和登录账户默认一致；


> 三、SilkSvn启动模式：<br>
在cmd里面输入以下命令安装服务：<br>
 sc stop `silksvn` <br>
 sc delete `silksvn`  <br>
 sc create `silksvn` binPath= "\\"C:/Program Files/SlikSvn/bin/svnserve.exe\\" --service -r D:\\svn\\repository" DisplayName= "Silk SVN" depend= tcpip start= auto <br>
 上面的路径分别是SilkSvn的安装路径以及SVN仓库的根目录（该目录必须已经存在，否则服务启动失败）。
最后在windows服务里面启动silksvn服务
`PS：silksvn 默认的端口是：3690，注意开启防火墙`
