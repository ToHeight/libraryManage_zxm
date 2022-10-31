`Open`# 图书语言
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
WHERE UserAppointments.user_id=1

# 撤销预约
UPDATE UserAppointments SET appointments_status='AS003' WHERE appointment_id=1 AND appointments_status IN ('AS001','AS004') AND user_id=1