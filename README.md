# SpringBoot+Vue—前后端分离博客项目

## 项目说明

​	该项目是基于SpringBoot和Vue开发的前后端分离博客项目，由3个模块组成：fox-admin(后台模块)，fox-blog前台模块，fox-framework(公共模块)。由于该项目是个学习用作品，拥有完整的前端代码编写，因此只需要把整个后端冲0做出来。对标的B站视频的BV号为BV1hq4y1F7zk。

​	后台管理基于诺依二次开发，含有侧边栏、历史标签、面包屑等；使用springsecurity进行登录验证;支持动态权限修改、动态菜单和路由；文章编辑使用Markdown编辑器；具备发布评论、回复评论、表情包等功能。

## **环境和相关技术说明：**

**相关技术：**SpringBoot、MybatisPlus、SpringSecurity、EasyExcel、Swagger2、redis、Echarts、Vue、ElementUI

**开发工具**

| 开发工具 | 说明               |
| -------- | ------------------ |
| IDEA     | Java 开发工具 IDE  |
| VSCode   | Vue 开发工具 IDE   |
| Navicat  | MySQL 远程连接工具 |

**开发环境**

| 开发环境     | 版本    |
| ------------ | ------- |
| JDK          | 8       |
| MySQL        | 8.0.29  |
| Redis        | 5.0.14  |
| Node         | 14.21.3 |
| Windows      | 10      |
| Vue          | 2.5.16  |
| Npm          | 6.14.18 |
| SpringBoot   | 2.5.0   |
| Mybatis-plus | 3.5.2   |

**部署环境**

| 开发环境 | 版本                  |
| -------- | --------------------- |
| JDK      | 8                     |
| MySQL    | 8.0.29                |
| Redis    | 5.0.14                |
| Nginx    | 1.18.0                |
| Docker   | 24.0.7                |
| 服务器   | 云服务器ECS（阿里云） |
| 操作系统 | CentOS  7.6 64位      |
| CPU&内存 | 2核(vCPU) 4 GiB       |

## 博客前台模块

### 文章列表—接口分析

​	需要查询浏览量最高的前10篇文章的信息。要求展示文章标题和浏览量。把能让用户自己点击跳转到具体的文章详情进行浏览

### 统一响应格式

①创建AppHttpCodeEnum类，封装code，msg

②创建ResponseResult类，作为统一响应格式的类

③在ArticleService中定义hotArticleList方法

④在ArticleController类增加了文章列表的统一响应格式的代码

### 后端解决跨域

​	通过实现接口并重写方法来解决跨域问题—新建WebConfig类

### 登录功能实现分析

**登录**

​	①自定义登录接口

​				调用ProviderManager的方法进行认证 如果认证通过生成jwt

​				把用户信息存入redis中

​	②自定义UserDetailsService

​				在这个实现类中去查询数据库

​	注意配置passwordEncoder为BCryptPasswordEncoder

**校验**

①实现校验功能，也就是使用jwt认证过滤器，对用户的登录进行校验，是否有登录状态

- 获取token，由于我们在把token存入Redis的时候加了前缀，所以获取的时候注意前缀

- 解析token获取其中的userid

- 从redis中获取用户信息

- 存入SecurityContextHolder

### 异常处理

**1.认证授权的异常处理**

​	实现AuthenticationEntryPoint(官方提供的认证失败处理器)类、AccessDeniedHandler(官方提供的授权失败处理器)类，然后配置给Security

**2.统一异常处理**

​	实际我们在开发过程中可能需要做很多的判断校验，如果出现了非法情况我们是期望响应对应的提示的。但是如果我们每次都自己手动去处理就会非常麻烦。我们可以选择直接抛出异常的方式，然后对异常进行统一处理。把异常中的信息封装成ResponseResult响应给前端

### 文件上传

**使用OSS上传图片（七牛云）**

​	因为如果把图片视频等文件上传到自己的应用的Web服务器的某个目录下，在读取图片的时候会占用比较多的资源。影响应用服务器的性能。所以我们一般使用OSS(Object Storage Service对象存储服务)存储图片或视频

**AK/SK认证**

AK: Access Key ID（访问密钥）,用于标示用户

SK: Secret Access Key（安全密钥）,用于加密认证字符串和用来验证认证字符串的密钥，其中SK必须保密

**AK/SK的认证过程：**

客户端生成签名—>客户端发送请求时，携带AK然后通过SK方式加密—>服务端校验签名

![QQ图片20231209195045](C:\Users\Fox\OneDrive\桌面\picture\QQ图片20231209195045.png)



### AOP实现日志记录的分析

定义切面类，在切面类通过 '切点表达式' 或 '自定义注解'，来指定切点

切面类: 指定要增强哪个切点，里面写通知的方法，通知的方法里面写具体的增强代码

AOP中的通知方法有五种，如下

| 通知方法 | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| 前置通知 | 在一个方法执行之前的阶段，执行通知。可以在目标方法执行前做一些预处理操作 |
| 后置通知 | 在一个方法执行之后的阶段，执行通知。通常用于执行一些清理操作或日志记录 |
| 异常通知 | 在方法抛出异常退出时执行的通知。用于处理目标方法抛出的异常情况 |
| 最终通知 | 无论目标方法是否成功执行，最终通知总会被执行，常用于释放资源 |
| 环绕通知 | 环绕通知是AOP中最灵活的通知类型。能在目标方法前后完全控制连接点，决定是否执行目标方法并进行额外处理 |

### 前台模块—浏览次数实现

**思路分析**

在用户浏览博文时要实现对应博客浏览量的增加。我们只需要在每次用户浏览博客时更新对应的浏览数即可

但是如果直接操作博客表的浏览量的话，在并发量大的情况下会出现什么问题呢？如何去优化呢？如下四点

①在应用启动时把博客的浏览量存储到redis中 - 项目启动的预处理功能

②更新浏览量时去更新redis中的数据

③每隔3分钟把Redis中的浏览量更新到数据库中 - 定时任务功能

④读取文章浏览量时从redis读取

**启动预处理**

如果希望在SpringBoot应用启动时进行一些初始化操作可以选择使用CommandLineRunner接口来进行处理。

我们只需要实现CommandLineRunner接口，并且把对应的bean注入容器。把相关初始化的代码重新到需要重新的方法中。

这样就会在应用启动的时候执行对应的代码。

**定时任务**

定时任务的实现方式有很多，比如XXL-Job等。但是其实核心功能和概念都是类似的，很多情况下只是调用的API不同而已

这里就先用SpringBoot为我们提供的定时任务的API来实现一个简单的定时任务，先对定时任务里面的一些核心概念有个大致的了解

**cron表达式**

常用表达式例子

  （1）0/2 * * * * ?   表示每2秒 执行任务

  （1）0 0/2 * * * ?    表示每2分钟 执行任务

  （1）0 0 2 1 * ?   表示在每月的1日的凌晨2点调整任务

  （2）0 15 10 ? * MON-FRI   表示周一到周五每天上午10:15执行作业

  （3）0 15 10 ? 6L 2002-2006   表示2002-2006年的每个月的最后一个星期五上午10:15执行作

  （4）0 0 10,14,16 * * ?   每天上午10点，下午2点，4点 

  （5）0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时 

  （6）0 0 12 ? * WED    表示每个星期三中午12点 

  （7）0 0 12 * * ?   每天中午12点触发 

  （8）0 15 10 ? * *    每天上午10:15触发 

  （9）0 15 10 * * ?     每天上午10:15触发 

  （10）0 15 10 * * ?    每天上午10:15触发 

  （11）0 15 10 * * ? 2005    2005年的每天上午10:15触发 

  （12）0 * 14 * * ?     在每天下午2点到下午2:59期间的每1分钟触发 

  （13）0 0/5 14 * * ?    在每天下午2点到下午2:55期间的每5分钟触发 

  （14）0 0/5 14,18 * * ?     在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 

  （15）0 0-5 14 * * ?    在每天下午2点到下午2:05期间的每1分钟触发 

  （16）0 10,44 14 ? 3 WED    每年三月的星期三的下午2:10和2:44触发 

  （17）0 15 10 ? * MON-FRI    周一至周五的上午10:15触发 

  （18）0 15 10 15 * ?    每月15日上午10:15触发 

  （19）0 15 10 L * ?    每月最后一日的上午10:15触发 

  （20）0 15 10 ? * 6L    每月的最后一个星期五上午10:15触发 

  （21）0 15 10 ? * 6L 2002-2005   2002年至2005年的每月的最后一个星期五上午10:15触发 

  （22）0 15 10 ? * 6#3   每月的第三个星期五上午10:15触发


创建MySQL容器：`docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=yourpassword -d mysql`

## **[mysql docker容器中导入数据库失败 Failed to open file ‘‘.sql‘‘, error: 2](https://www.cnblogs.com/yyhhblog/p/17805344.html)**

**mysql docker 容器中导入数据库失败**

```
docker run -d -e MYSQL_ROOT_PASSWORD=123456 --name mydata-mysql-1 -d -p 13307:3306 -v mysql_data1:/var/lib/mysql -v /usr/local/docker/mysql8.0.29/log:/var/log/ mysql:8.0.29创建Mysql容器**

docker exec -it xxxxxxxxx sh 进入mysql容器。
mysql -uroot -pxxxx 登陆
use database； 切换数据库
source /home/xxxx.sql 导入数据库文件
```

Error：
Failed to open file ‘‘xxx.sql’’, error: 2

查了网上资料，好多都是试试路径 把/变成 \试试 pass不行！
什么绝对路径 相对路径 pass 注意是linux！！！

```
首先
sudo docker ps //查看mysql容器id
sudo docker cp sql文件路径 mysql容器id:/ 注意 linux中后面要加上:/

再次进入mysql容器
docker exec -it 容器ID bash 
mysql -u root -p123456

切换数据库
use sg_blog;
 运行 source xxxx.sql；*注意这里sql文件不用加路径 *
成功！
```

**【containerd错误解决系列】failed to create task for container: failed to create shim task: OCI runtime create failed: runc create failed: unable to start container process: error during container init: error mounting "/app/mysql/conf/my.cnf" to rootfs at "/etc/my.cnf": mount /app/mysql/conf/my.cnf:/etc/my.cnf (via /proc/self/fd/6), flags: 0x5000: not a directory: unknown: Are you trying to mount a directory onto a file (or vice-versa)? Check if the specified host path exists and is the expected type**

```
 rpm -qa | grep libseccomp  //查看libseccomp版本

runc -version  

*sudo rpm -e libseccomp-2.3.3-3.el8.x86_64 --nodeps*  //卸载低版本libseccomp
```

安装高版本的libseccomp第一种方式：

```
 *yum provides libseccomp*  //安装高版本libseccomp

*yum install libseccomp-2.5.2-1.el8.x86_64*
```

第二钟方式：

```
在网站 [rpmfind.net](http://rpmfind.net/linux/rpm2html/search.php?query=libseccomp&submit=Search ...) 找到对应系统版本最新版本安装包""下载后传到服务器

http://rpmfind.net/linux/rpm2html/search.php?query=libseccomp&submit=Search%20...

 rpm -ivh libseccomp-2.5.2-1.el8.x86_64.rpm  //安装下载依赖包

rpm  -qa |grep libseccomp  //查看是否成功
```



### 解决原理

```
libseccomp需要高于`2.4版本`

containerd.io 要求安装版本为 2.4.0 的 libseccomp
```

# Mysql：Forcing close of thread xxx user: ‘root‘ 的解决方法

去看mysql的errorlog，看到如下的信息：

Forcing close of thread xxxxx user: ‘root’

百度之后

发现这算属MySQL的一个bug，不管连接是通过hosts还是ip的方式，MySQL都会对DNS做反查，IP到DNS，由于反查的接续速度过慢（不管是不是isp提供的dns服务器的问题或者其他原因），大量的查询就难以应付，线程不够用就使劲增加线程，但是却得不到释放，所以MySQL会“ 假死”。

解决的方案很简单，结束这个反查的过程，禁止任何解析。

linux 打开mysql的配置文件（my.cnf），在[mysqld]下面增加一行：

skip-name-resolve

window 在my.ini添加的内容:

skip-locking

skip-name-resolve

重启MySQL即可
