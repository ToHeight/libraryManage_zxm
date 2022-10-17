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
,('AS002','未激活')
# 借阅状态
INSERT INTO Constant(coding,VALUE) VALUES
 ('BWS01','正在借阅中')
,('BWS02','已归还')
,('BWS03','已逾期')
,('BWS04','续租中')

