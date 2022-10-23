# 图书语言
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
	DATE_FORMAT(Borrow.return_time, '%Y年%m月%d日 %H时%i分') returnTime,DATE_FORMAT(Borrow.borrow_time, '%Y年%m月%d日 %H时%i分') borrowTime,User.leaseRenewalNumber,User.borrowing_number borrowingNumber
FROM Borrow
INNER JOIN Book ON Borrow.book_id=Book.book_id
INNER JOIN Constant ON Borrow.borrow_tatus=Constant.coding
INNER JOIN USER ON Borrow.user_id=User.user_id
WHERE Borrow.borrow_delete=0 AND User.user_id='1'