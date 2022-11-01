# 图书馆系统（目前还在开发中，已开发到在线预约座位）
## 功能介绍
图书馆系统功能包括：读者查看全部图书、借阅图书、在线预定图书馆座位、图书评论、图书评星等图书馆基本操作，管理员端口管理全部基本信息
#### 注意
本系统完全由本人（后端）和@Cheng-bei7（前端）开发，现发布github作为技术交流使用，严禁向公众出租、发售该系统。如若违规，本人依法追究民事责任。
## 软件架构
后端：后端使用技术为springCloud、Spring boot、springMVC、mybatis、redis、webSocket、RabbitMQ等
前端：使用vue、element plus、axios等
前后端分离项目
## 运行
1、下载前端和后端文件
2、前端运行前需要 npm i --legacy-peer-deps 命令
3、后端使用redis和mysql、RabbitMQ，修改application.yml中的参数文件，修改mysql、RabbitMQ 地址和密码

预约座位功能展示


https://user-images.githubusercontent.com/87918586/199138520-c9ae36d4-9132-4181-90d2-e99608cb6def.mp4

