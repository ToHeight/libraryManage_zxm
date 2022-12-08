`mystudent``libraryManage``Open`# 图书语言
INSERT INTO Constant(coding,VALUE) VALUES('LE001','英文')
,('LC002','中文')
,('LG003','德语')
,('LF004','法语')
,('LI005','意大利语')
,('LJ006','日语')
# 作者国家
INSERT INTO Constant(coding,VALUE) VALUES
 ('CE001','英国')
,('CC002','中华人民共和国')
,('CG003','德国')
,('CF004','法国')
,('CI005','意大利')
,('CA006','美国')
,('CJ007','日本')
# 图书状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('BS001','暂无')
,('BS002','可借阅')
,('BS003','不可借阅')
# 性别
INSERT INTO Constant(coding,VALUE) VALUES
 ('GM001','男')
,('GW002','女')
# 楼层状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('FS001','已开放')
,('FS002','未开放')
# 预定状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('RS001','未加入图书库')
,('RS002','已加入图书库')
,('RS003','未找到')
,('RS004','取消预订')
# 预约时间编号
INSERT INTO Constant(coding,VALUE) VALUES
 ('RT001','上午')
,('RT002','下午')
# 当前预约状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('AS001','正在预约')
,('AS002','预约已结束')
,('AS003','预约撤回')
,('AS004','预约失败')
,('AS005','预约成功')
# 帐号状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('ASU01','已激活')
,('ASU02','未激活')
# 借阅状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('BWS01','正在借阅中')
,('BWS05','归还未确认')
,('BWS02','已归还')
,('BWS03','已逾期')
,('BWS04','续租中')
# 活动状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('ACS01','报名中')
,('ACS02','活动进行中')
,('ACS03','活动已经结束')
# 报名状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('APS01','报名中')
,('APS02','报名成功')
,('APS03','报名失败')
,('APS04','活动进行中')
,('APS05','活动已经结束')
# 根据条件查找图书信息 图书名称、图片、作者、信息、种类、地址、状态、星级
SELECT DISTINCT book_name bookName,book_image bookImage,book_author bookAuthor,
       book_info bookInfo,book_address bookAddress,book_star bookStar,
       Constant.value bookStatus,Book_type.type_name bookType,
       Book_Constant_language.value bookLanguage,Book_Constant_country.value bookCountry,
       Book.borrow_number borrowNumber,Book.book_id bookId
FROM Book
INNER JOIN Constant ON Book.book_status=Constant.coding
INNER JOIN TYPE Book_type ON Book.type_id=Book_type.type_id
INNER JOIN Constant Book_Constant_language ON Book.book_language=Book_Constant_language.coding
INNER JOIN Constant Book_Constant_country ON Book.author_country=Book_Constant_country.coding
WHERE Book.book_delete=0

# 获取图书类别下拉列表
SELECT type_id id,type_name typeName
FROM TYPE

# 获取作者国家下拉列表
SELECT coding,VALUE
FROM Constant
WHERE coding LIKE 'C____'

# 获取图书语言下拉列表
SELECT coding,VALUE
FROM Constant
WHERE coding LIKE 'L____'


# 书架查询
SELECT DISTINCT book_name bookName,book_image bookImage,book_author bookAuthor,
       book_info bookInfo,book_address bookAddress,BookShelf.book_star bookStar,
       Constant.value bookStatus,Book_type.type_name bookType,
       Book_Constant_language.value bookLanguage,Book_Constant_country.value bookCountry,
       Book.borrow_number borrowNumber,Book.book_id bookId
FROM BookShelf
INNER JOIN Book ON BookShelf.book_id=Book.book_id 
INNER JOIN Constant ON Book.book_status=Constant.coding
INNER JOIN Constant Book_Constant_language ON Book.book_language=Book_Constant_language.coding
INNER JOIN Constant Book_Constant_country ON Book.author_country=Book_Constant_country.coding
INNER JOIN TYPE Book_type ON Book.type_id=Book_type.type_id
WHERE BookShelf.book_delete=0 AND BookShelf.user_id=''

# 借阅图书查询
SELECT Borrow.borrow_id borrowId,Constant.value borrowStatus,Book.book_name bookName,Book.book_author bookAuthor,Book.book_image bookImage,User.leaseRenewalNumber,
	DATE_FORMAT(Borrow.return_time, '%Y年%m月%d日 %H时%i分') returnTime,DATE_FORMAT(Borrow.borrow_time, '%Y年%m月%d日 %H时%i分') borrowTime,User.leaseRenewalNumber,
	User.borrowing_number borrowingNumber,Comments.comments_info commentdInfo,Comments.comments_id commentsId
FROM Borrow
INNER JOIN Book ON Borrow.book_id=Book.book_id
INNER JOIN Constant ON Borrow.borrow_tatus=Constant.coding
INNER JOIN libraryManage.User ON Borrow.user_id=User.user_id
LEFT JOIN Comments ON Comments.book_id=Borrow.book_id AND Comments.delete_comments=0 AND Comments.user_id=1
WHERE Borrow.borrow_delete=0

# 过期图书
SELECT borrow_id
FROM Borrow 
WHERE borrow_time>return_time

# 查询当前数据
SELECT bookTap.id,bookTag.tag_value
FROM bookTap,bookTag
WHERE bookTap.bookId='8' AND bookTag.tag_delete=0 AND bookTap.tapId=bookTag.tag_id 
AND (bookTap.userId IS NULL OR bookTap.userId=bookTag.user_id=1)

SELECT Scheduled.scheduled_id,Scheduled.book_name,Constant.value
FROM Scheduled
INNER JOIN Constant ON Scheduled.scheduled_status=Constant.coding
WHERE Scheduled.user_id=1 

# 查询活动类别
SELECT activityTypeId coding,activityTypeName VALUE
FROM activityType

# 查询活动 活动名称、活动种类id、活动状态、举办方名称
SELECT  activity.activityId,activity.activityName,activityInfo,activityOrganizer,
	DATE_FORMAT(activityDate, '%Y年%m月%d日') activityDate,DATE_FORMAT(activityApplication, '%Y年%m月%d日 %H时%i分') activityApplication,activity.activityAge,
	Constant.value activityStatus,activityType.activityTypeName,appCon.value applicationStatus
FROM activity
INNER JOIN Constant ON Constant.coding=activity.activityStatus
INNER JOIN activityType ON activityType.activityTypeId=activity.activityTypeId
INNER JOIN application ON application.userId=1
INNER JOIN Constant appCon ON application.applicationStatus=appCon.coding
WHERE activity.activityDelete=0 AND activity.activityId IN (SELECT app.activityId FROM application app WHERE app.userId=1)
 AND activity.activityName LIKE CONCAT('%','亲','%') AND activity.activityId NOT IN (1)

# 更新活动列表
UPDATE activity SET activityStatus=(
	CASE WHEN activityDate<NOW() 
	THEN 'ACS03' 
	ELSE (
		CASE WHEN (NOW()>activityApplication AND NOW()<activityDate) 
		THEN 'ACS02' ELSE 'ACS01' END) END) 
WHERE activityStatus IN ('ACS01','ACS02') AND activityDelete=0

# 更新用户报名状态
UPDATE application
INNER JOIN activity ON application.activityId=activity.activityId
SET application.applicationStatus=(CASE WHEN (activity.activityDate<NOW() AND (application.applicationStatus='APS02' OR application.applicationStatus='APS04'))
	THEN 'APS05' 
	ELSE (
		CASE WHEN (activity.activityDate<NOW() AND application.applicationStatus='APS01')
	THEN 'APS03' 
	ELSE (
		CASE WHEN (NOW()>activityApplication AND NOW()<activityDate AND application.applicationStatus='APS02') 
		THEN 'APS04' ELSE 'APS01' END) END) END)
WHERE application.applicationStatus IN ('APS04','APS01','APS02')

# 查询用户活动是否够资格
SELECT COUNT(user.user_id)
FROM USER USER
INNER JOIN activity ON activity.activityId=1
WHERE user.user_id=1 AND user.user_age>activity.activityAge AND NOW()<activity.activityApplication

# 撤销报名
UPDATE application SET application.applicationStatus='APS03' WHERE applicationStatus IN ('APS01','APS02') AND applicationId='1' AND userId='1'

# 获取用户信息
SELECT user_name userName,user_image userImage
FROM USER
WHERE User.user_id=1

# 更新用户表的信息
UPDATE UserAppointments
INNER JOIN libraryManage.Open ON UserAppointments.floor_id=Open.floor_id
 SET appointments_status=(CASE WHEN  Open.floor_status='FS002'
	THEN 'AS004' 
	ELSE (CASE WHEN 
		DATE_ADD(appointment_time,INTERVAL 2 HOUR)<NOW()
		THEN 'AS002' ELSE 'AS001' END
	) END) 
WHERE appointments_status IN ('AS001','AS005')

# 更新楼层状态
UPDATE libraryManage.Open
INNER JOIN AppointmentsTime ON Open.time_id=AppointmentsTime.time_id
SET Open.floor_status=(CASE WHEN (CURTIME()>AppointmentsTime.time_end || CURTIME()<AppointmentsTime.time_start) THEN 'FS002' ELSE 'FS001' END)

# 查找当前预约人员,根据用户选择的预约时间来获取预约座位
SELECT seatName
FROM UserAppointments
WHERE appointment_time<DATE_ADD('2022-10-30 08:00:00',INTERVAL 2 HOUR) AND appointments_status IN ('AS001','AS005') AND floor_id=1

# 获取楼层下拉列表
SELECT floor_id coding,floor_name VALUE,AppointmentsTime.time_start timeStart,AppointmentsTime.time_end timeEnd
FROM libraryManage.Open
INNER JOIN AppointmentsTime ON Open.time_id=AppointmentsTime.time_id
WHERE floor_status='FS001' AND people_count!=0

#获取用户信息
SELECT User.user_id id,User.user_name userName,conGen.value gender,user_address userAddress,user_email userEmail,user_age age
FROM libraryManage.User
INNER JOIN Constant conGen ON conGen.coding=User.user_gender 
WHERE User.user_id=1

# 添加新的数据
INSERT INTO UserAppointments(floor_id,user_id,appointment_time,appointments_createTime,seatName) 
SELECT  2,2,'2022-10-29 12:00:00',NOW(),'E-2'
FROM DUAL WHERE EXISTS 
(SELECT floor_id FROM libraryManage.Open WHERE floor_id=3 AND people_count!=0)

# 查询历史预约
SELECT Appointment_id appointmentId,Open.floor_name floorName,DATE_FORMAT(appointment_time, '%Y年%m月%d日 %H时%i分') appointmentTime,
	DATE_FORMAT(appointments_createTime, '%Y年%m月%d日 %H时%i分') appointmentsCreateTime,Constant.value appointmentsStatus
FROM UserAppointments
INNER JOIN libraryManage.Open ON Open.floor_id=UserAppointments.user_id
INNER JOIN Constant ON appointments_status=coding
WHERE UserAppointments.user_id=2

# 撤销预约
UPDATE UserAppointments SET appointments_status='AS003' WHERE appointment_id=1 AND appointments_status IN ('AS001','AS004') AND user_id=1

# 获取用户基本信息
SELECT user_id userId,user_name userName,user_idcard userIdcard,user_telephone telephone,user_address address,user_email email,user_image image,user_birth brithday,user_gender gender
FROM libraryManage.User
WHERE User.user_id=1

# 获取性别下拉框
SELECT Constant.coding,Constant.value
FROM Constant
WHERE Constant.coding LIKE 'GM___'

# 图书详情信息
SELECT  Book.book_id bookId,Book.book_name bookName,Book.book_author author,Book.book_address address,bookType.type_name typeName,country.value country,
	bookLanguage.value bookLanguage,Book.book_count bookCount,bookStatus.value bookStatus,book_star bookStar,Book.book_info bookInfo
FROM Book
INNER JOIN libraryManage.Type bookType ON bookType.type_id=Book.type_id
INNER JOIN Constant country ON country.coding=Book.author_country
INNER JOIN Constant bookLanguage ON bookLanguage.coding=Book.book_language
INNER JOIN Constant bookStatus ON bookStatus.coding=Book.book_status
WHERE Book.book_delete=0

# 图书标签查询
SELECT bookTag.tag_value tagName,bookTag.tag_id tagId
FROM bookTag
WHERE bookTag.tag_delete=0 AND bookTag.user_id IS NULL AND bookTag.tag_id IN (
	SELECT
		bookTap.tapId
	FROM 
		bookTap
	WHERE
		bookTap.bookId=8 AND bookTap.userId IS NULL
)

# 推荐图书信息
SELECT book_id bookId,book_name bookName,Type.type_name typeName,book_star bookStar,book_image bookImage
FROM 
	Book
INNER JOIN libraryManage.Type ON Type.type_id=Book.type_id
ORDER BY (
    CASE
     WHEN Book.type_id=(
SELECT bookType.type_id
FROM Book bookType
WHERE bookType.book_id=19    
     ) THEN 1 ELSE 4 END),book_star DESC;
     
# 首页推荐图书
SELECT book_id bookId,book_name bookName,Type.type_name typeName,book_star bookStar,book_image bookImage
FROM 
	Book
INNER JOIN libraryManage.Type ON Type.type_id=Book.type_id
ORDER BY (
    CASE
     WHEN Book.type_id IN (
	(SELECT  DISTINCT    
		BookBorrow.type_id
	FROM 
		Borrow
	INNER JOIN Book BookBorrow ON BookBorrow.book_id=Borrow.book_id
	WHERE 
		Borrow.user_id=8
	UNION 
	SELECT DISTINCT 
		BookBookShelf.type_id
	FROM 
		BookShelf
	INNER JOIN Book BookBookShelf ON BookBookShelf.book_id=BookShelf.book_id
	WHERE
		BookShelf.user_id=8)
     ) THEN 1 ELSE 4 END),book_star DESC;
     
     
# 查询图书评论
SELECT 
	User.user_name userName,User.User_image userImage,Comments.comments_info commentsInfo
FROM 
	Comments
INNER JOIN libraryManage.User ON User.user_id=Comments.user_id
WHERE
	Comments.book_id=8 AND delete_comments=0
	
# 查询书架是否存在相同图书
SELECT COUNT(*)
FROM BookShelf
WHERE user_id=1 AND book_id=8 AND book_delete=0

# 查询借阅图书个数
SELECT COUNT(*)
FROM Borrow
WHERE user_id=2 AND book_id=8 AND borrow_tatus NOT IN ('BWS02');

# 续租用户表更新
UPDATE libraryManage.User SET leaseRenewalNumber=leaseRenewalNumber-1 WHERE leaseRenewalNumber!=0 AND user_delete=0 AND STATUS = 'ASU01'

# 权限人员表
CREATE TABLE roleInfo(
	role_id INT PRIMARY KEY AUTO_INCREMENT,
	 NAME VARCHAR(50) NOT NULL
);
CREATE TABLE roleModule(
	role_id INT NOT NULL,
	module_id INT(4) NOT NULL
);
CREATE TABLE moduleInfo(
	module_id INT NOT NULL,
	NAME VARCHAR(50) NOT NULL
);

# 查询图书全部信息  根据图书名称、图书种类、图书作者、图书详情、状态、图书语言、作者国家查询
SELECT Book.book_id bookId,Type.type_name typeName,Book.book_author author,Book.book_info bookInfo,Book.book_image image,Book.book_address address,constantStatus.value bookStatus,
	Book.book_count bookCount,constanrLanguage.value bookLanguage,constanrCountry.value authorCountry
FROM Book
INNER JOIN libraryManage.Type ON Book.type_id=Type.type_id
INNER JOIN Constant constantStatus ON constantStatus.coding =Book.book_status
INNER JOIN Constant constanrLanguage ON constanrLanguage.coding=Book.book_language
INNER JOIN Constant constanrCountry ON constanrCountry.coding=Book.author_country
WHERE Book.book_delete=0 AND ( Book.book_name LIKE CONCAT('%','w','%') OR Book.book_author LIKE CONCAT('%','','%') OR Book.book_info LIKE CONCAT('%','w','%'))

# 查询权限
SELECT COUNT(*)
FROM libraryManage.Action
WHERE action_id IN (
	SELECT 
		module_id
	FROM
		roleModule
	WHERE
		role_id=2
) AND action_name='/librarySystemAdmin/dropdownListData/getBookType'

# 管理员信息查询
SELECT manager_id managerId,manager_name managerName,manager_age managerAge
FROM Manager
WHERE manager_id=1 AND manager_delete=0

# 更新图书信息
UPDATE Book SET type_id=1,book_name='test',book_author='test',book_info='test',book_image='test',book_address='test',book_status='test',book_language='test',author_country='test' WHERE Book.book_id='test';

# 添加图书
INSERT INTO Book(book_id,type_id,book_name,book_author,book_info,book_image,book_address,book_status,book_count,book_language,author_country,book_star) 
VALUES(1212,1,1,1,1,1,1,1,1,1,1,1)

# 查询书签
SELECT 
	bookTag.tag_id tagId,bookTag.tag_value tagValue,User.user_name userName
FROM 
	bookTag
LEFT JOIN libraryManage.User ON User.user_id=bookTag.user_id
WHERE
	bookTag.tag_delete=0 AND bookTag.tag_value LIKE CONCAT()

# 查询书签图书
SELECT 
	bookTap.tapId,bookTap.bookId,bookTap.tapId,Book.book_name bookName,Book.book_id bookId
FROM 
	bookTap
INNER JOIN Book ON Book.book_id=bookTap.bookId
WHERE
	bookTap.tapId=1 AND userId IS NOT NULL

#添加书签
INSERT INTO bookTap(bookId,tapId) VALUES (1,2)

# 查询图书留言
SELECT
	Comments.comments_id,Book.book_name bookName,Book.book_image bookImage,User.user_id userId,User.user_name userName,Comments.comments_info commentsInfo
FROM
	Comments
INNER JOIN Book ON Book.book_id=Comments.book_id
INNER JOIN libraryManage.User ON User.user_id=Comments.user_id
WHERE Comments.delete_comments=0 AND (Book.book_name LIKE CONCAT('%','','%') OR Comments.comments_info LIKE CONCAT('%','','%'))

# 查询留言下用户信息
SELECT
	SUBSTRING(Comments.comments_info,1,5) commentsInfo,User.user_name userName,User.user_email userEmail,Book.book_name bookName
FROM 
	Comments
INNER JOIN libraryManage.User ON User.user_id=Comments.user_id
INNER JOIN Book ON Book.book_id=Comments.book_id
WHERE Comments.comments_id='1584368878876798976'

# 借阅管理
SELECT Borrow.borrow_id borrowId,DATE_FORMAT(Borrow.borrow_time, '%Y年%m月%d日 %H时%i分') borrowTime,DATE_FORMAT(Borrow.return_time, '%Y年%m月%d日 %H时%i分') returnTime,
	User.user_name userName,Book.book_name bookName,Constant.value borrowStatus
FROM 
	Borrow
INNER JOIN libraryManage.User ON User.user_id=Borrow.user_id
INNER JOIN Book ON Book.book_id=Borrow.book_id
INNER JOIN Constant ON Constant.coding=borrow_tatus
WHERE
	borrow_delete=0
ORDER BY borrow_tatus DESC
# 预约图书管理
SELECT scheduled_id scheduledId,book_name,Constant.value scheduledStatus,User.user_name userName
FROM Scheduled
INNER JOIN Constant ON Constant.coding=scheduled_status
INNER JOIN libraryManage.User ON User.user_id=Scheduled.user_id

# 活动发布、取消、删除
SELECT 
	activity.activityId,activity.activityName,activity.activityInfo,activity.activityOrganizer,activity.activityDate,activityApplication,activityAge,activityType.activityTypeName,
	activity.activityTypeId,Constant.value activityStatus,activity.activityStatus activityStatusCoding
FROM
	activity
INNER JOIN activityType ON activityType.activityTypeId=activity.activityTypeId
INNER JOIN Constant ON Constant.coding=activity.activityStatus
WHERE
	activityDelete=0 AND 

# 报名管理
SELECT
	applicationId,application.userId,User.user_name userName,User.user_age userAge,application.applicationStatus applicationStatusCoding,
	Constant.value applicationStatus,activity.activityName,activity.activityInfo
FROM 
	application
INNER JOIN libraryManage.User ON User.user_id=application.userId 
INNER JOIN libraryManage.activity ON activity.activityId=application.activityId
INNER JOIN Constant ON Constant.coding=application.applicationStatus
	
# 楼层管理
SELECT 
	Open.floor_id floorId,Open.floor_name floorName,people_count peopleCount,AppointmentsTime.time_start timeStart,AppointmentsTime.time_end timeEnd,
	Open.time_id timeId
FROM 
	libraryManage.Open
INNER JOIN Constant ON Open.floor_status=Constant.coding
INNER JOIN AppointmentsTime ON Open.time_id=AppointmentsTime.time_id

#时间列表查询
SELECT 
	time_id timeId,time_start timeStart,time_end timeEnd
FROM 
	AppointmentsTime

# 预定管理
SELECT
	UserAppointments.appointment_id appointmentId,Open.floor_name floorName,User.user_name userName,Constant.value appointmentsStatus,
	UserAppointments.appointments_status appointmentsStatusCoding,appointment_time appointmentTime,appointments_createTime appointmentsCreateTime
FROM
	UserAppointments
INNER JOIN libraryManage.Open ON Open.floor_id=UserAppointments.floor_id
INNER JOIN libraryManage.User ON User.user_id=UserAppointments.user_id
INNER JOIN Constant ON Constant.coding=UserAppointments.appointments_status`Action`
WHERE
	
# 权限管理
SELECT 
	role_id roleId,NAME 
FROM
	roleInfo
WHERE

SELECT
	moduleInfo.module_id moduleId,moduleInfo.name
FROM 
	moduleInfo
WHERE
	moduleInfo.module_id IN (
	SELECT
		roleModule.module_id
	FROM
		roleModule
	WHERE
		roleModule.role_id=1
	)
	
INSERT INTO roleModule VALUES(1,9)
SELECT module_id coding,NAME VALUE
FROM moduleInfo
# 用户管理
SELECT
	User.user_id userId,User.user_name userName,User.user_idcard userIdcard,User.user_gender userGenderCoding,User.user_telephone userTelephone,User.user_address userAddress,
	User.user_email userEmail,constantStatus.value userStatus,constantGender.value userGender,User.user_image userImage
FROM 
	libraryManage.User
INNER JOIN Constant constantStatus ON constantStatus.coding = User.status 
INNER JOIN Constant constantGender ON constantGender.coding = User.user_gender
WHERE
	User.user_delete=0

# 管理员管理
SELECT
	Manager.manager_id managerId,manager_name managerName,manager_age age,manager_telephone managerTelephone,
	manager_address managerAddress,manager_gender managerGenderCoding,Constant.value managerGender,ManagerLogin.permission permissionCoding,
	roleInfo.name
FROM 
	Manager
INNER JOIN Constant ON Constant.coding=manager_gender
INNER JOIN ManagerLogin ON Manager.manager_id=ManagerLogin.manager_id
INNER JOIN roleInfo ON ManagerLogin.permission=roleInfo.role_id
WHERE
	Manager.manager_delete=0
	
	
SELECT 
	roleModule.role_id roleId,roleModule.module_id moduleId,moduleInfo.name
FROM
	roleModule
INNER JOIN moduleInfo ON moduleInfo.module_id=roleModule.module_id
WHERE
	role_id=1
	
CREATE TABLE `lostBook` (
  `id` VARCHAR(100) NOT NULL,
  `borrowId` INT(11) DEFAULT NULL,
  `lostStatus` CHAR(5) DEFAULT NULL,
  `payId` VARCHAR(100) DEFAULT NULL,
  `payDate` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
)

SELECT 
	COUNT(1)
FROM
	Borrow
WHERE
	Borrow.borrow_id=8 AND Borrow.user_id=1
	
# 添加丢失图书
INSERT INTO lostBook VALUES(1,8,'PB002',NULL,NULL);

# 查询全部丢失图书
SELECT 
	lostBook.id lostBookId,Book.book_name bookName,Book.book_image bookImage,Constant.value lostBookStatus,Borrow.borrow_time borrowTime,lostBook.payId,lostBook.payDate
FROM 
	lostBook
INNER JOIN Constant ON Constant.coding=lostBook.lostStatus
INNER JOIN Borrow ON Borrow.borrow_id=lostBook.borrowId
INNER JOIN Book ON Book.book_id=Borrow.book_id
WHERE
	lostBook.borrowId IN (
		SELECT
			Borrow.borrow_id
		FROM
			Borrow
		WHERE
			Borrow.user_id=1
	)
	
	
# 定时逾期到达三个星期任务
SELECT 
	Borrow.borrow_id
FROM 
	Borrow
WHERE 
	Borrow.borrow_tatus='BWS03' AND DATEDIFF(return_time,NOW())>21
	
#添加借阅信息
INSERT INTO Borrow VALUES

# 查询是否有未支付记录
SELECT 
	COUNT(1)
FROM
	lostBook
WHERE
	lostStatus='PB002' AND 
	lostBook.borrowId IN (
		SELECT
			Borrow.borrow_id
		FROM
			Borrow
		WHERE
			Borrow.user_Id=1
	)
