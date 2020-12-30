### 插件管理 manager 模块
####功能实现

1、组件介绍
	 Master 模块
	

	1.作为配置中心 
	2.作为资源分配中心
	3.注册监控中心
	4.通信模块
	5.插件反控

2. 依赖项目
	1)、jdk 1.8
	2)、组件及中间件：redis 3.0 apollo mysql
	3)、mysql数据库：XX
	4)、其他依赖项：无

	最低配置: 内存:2G+

4、部署步骤
    1) 全新安装请参照   安装步骤.txt
    2) 补丁升级参照     更新步骤.txt

5. 验证服务 在部署步骤说明里




####通信模块
<module>akka-rpc</module> 
####配置中心
<module>config</module>
####数据操作
<module>dao</module>
####插件自定义
<module>plugin-canal</module>
####对外服务
<module>server</module>

