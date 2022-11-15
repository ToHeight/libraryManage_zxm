# 图书馆系统（目前还在开发中）
## 功能介绍
图书馆系统功能包括：  
读者端：  
🎈、智能推荐图书✅  
🎆、读者在线预约座位✅  
🎇、读者借阅归还图书✅   
🧨、图书详情✅   
✨、图书评论、评星✅   
🎉、用户登录、注册、修改个人信息✅   
🎊、用户自定义图书标签✅   
🎃、用户报名活动参加活动✅   
🎄、书架展示和添加删除✅   
🎋、用户邮件登录、修改密码✅   
🎍、整体测试和页面优化懒加载✅   
后端具备定时任务自动判断是否到期进行修改，所以使用者无需考虑预约到期、活动到期、借阅到期的任务  
管理员端：  
🎎、管理员分角色登录✅  
🎏、图书管理✅  
🎐、图书标签管理✅  
🎑、图书留言管理✅  
🧧、活动管理✅  
🎀、编码管理✅  
🎁、预定管理✅  
🎗、借阅管理✅  

#### 注意
本系统完全由本人（后端）和@Cheng-bei7（前端）开发，现发布github作为技术交流使用，严禁向公众出租、发售该系统。如若违规，本人依法追究民事责任。
## 软件架构
后端：后端使用技术为springCloud、Spring boot、springMVC、mybatis、redis、webSocket、RabbitMQ等  
前端：使用vue、element plus、axios等  
前后端分离项目
## 运行
1、下载前端和后端文件  
2、前端运行前需要 npm i --legacy-peer-deps 命令  
3、后端使用redis和mysql、RabbitMQ、邮箱服务、华为对象存储服务等，需修改application.yml中的参数文件，修改mysql、RabbitMQ、电子邮件 地址和密码，修改obsHuaWei.properties中的华为对象存储Access Key ID和Secret Access Key。  

读者端界面展示

### 在线预约座位功能展示
![lv_0_20221110113739](https://user-images.githubusercontent.com/87918586/200995200-ac581973-5787-4646-b42e-b1e6bd564f3f.gif)

### 登录页面  
<img width="913" alt="登录" src="https://user-images.githubusercontent.com/87918586/200991646-6c11576b-9afe-4a2d-8482-53384d269069.png">

### 忘记密码  
![忘记密码](https://user-images.githubusercontent.com/87918586/200991760-5c81cc02-b739-4920-a977-49201f03129b.png)

### 修改密码  
<img width="896" alt="修改密码" src="https://user-images.githubusercontent.com/87918586/200991795-64169fab-dffa-4d98-b39f-1436f191c17b.png">

### 忘记密码邮件  
<img width="803" alt="邮件" src="https://user-images.githubusercontent.com/87918586/200991824-d465d33d-c697-46bc-af3f-ae5828b608e4.png">

### 图书大全  
<img width="916" alt="图书大全" src="https://user-images.githubusercontent.com/87918586/200991881-5fde37ba-ca94-49f8-b03c-dfa10955d612.png">

### 图书详情  
<img width="861" alt="图书详情" src="https://user-images.githubusercontent.com/87918586/200991914-d93fbb0a-e5dc-42b4-9e12-7568819bfaf3.png">

### 书架  
<img width="932" alt="书架" src="https://user-images.githubusercontent.com/87918586/200991939-5ffdb75a-878d-4c22-8b75-831a3da06148.png">

### 首页图书智能推荐  
<img width="920" alt="首页好书推荐" src="https://user-images.githubusercontent.com/87918586/200991971-16e085a7-c5ac-44fa-95bb-5416ff15dcfa.png">

### 个人信息修改  
<img width="914" alt="个人信息" src="https://user-images.githubusercontent.com/87918586/200991998-bf454579-353e-4dc5-98f7-36e97813dde3.png">

### 邮箱验证码  
![image](https://user-images.githubusercontent.com/87918586/200992122-b6c950ab-44cb-49a6-986b-d6473d103309.png)

### 借阅信息  
<img width="947" alt="借阅信息" src="https://user-images.githubusercontent.com/87918586/200992144-862733a1-bfa4-491f-85cb-6154827187ca.png">




