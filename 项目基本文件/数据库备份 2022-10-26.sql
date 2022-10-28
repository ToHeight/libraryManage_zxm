/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.51 : Database - libraryManage
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`libraryManage` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `libraryManage`;

/*Table structure for table `Action` */

DROP TABLE IF EXISTS `Action`;

CREATE TABLE `Action` (
  `action_id` int(11) NOT NULL COMMENT '权限id',
  `action_name` varchar(200) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `Action` */

/*Table structure for table `AppointmentsTime` */

DROP TABLE IF EXISTS `AppointmentsTime`;

CREATE TABLE `AppointmentsTime` (
  `time_id` int(11) NOT NULL COMMENT '时间id',
  `time_start` time DEFAULT NULL COMMENT '开始时间',
  `time_end` time DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`time_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `AppointmentsTime` */

/*Table structure for table `Book` */

DROP TABLE IF EXISTS `Book`;

CREATE TABLE `Book` (
  `book_id` varchar(50) NOT NULL COMMENT '图书id',
  `type_id` int(11) DEFAULT NULL COMMENT '图书种类id',
  `book_name` varchar(100) NOT NULL COMMENT '图书名称',
  `book_author` varchar(50) NOT NULL COMMENT '图书作者',
  `book_info` varchar(1000) DEFAULT NULL COMMENT '图书信息',
  `book_image` varchar(1000) DEFAULT NULL COMMENT '图书图片',
  `book_address` varchar(100) NOT NULL COMMENT '图书位置',
  `book_status` char(5) NOT NULL COMMENT '图书状态',
  `book_count` int(11) NOT NULL COMMENT '图书数量',
  `book_delete` int(11) DEFAULT NULL COMMENT '逻辑删除',
  `borrow_number` int(11) DEFAULT NULL COMMENT '借阅次数',
  `book_language` char(5) DEFAULT NULL COMMENT '图书语言',
  `author_country` char(5) DEFAULT NULL COMMENT '作者国家',
  `book_star` double DEFAULT NULL COMMENT '图书星级',
  PRIMARY KEY (`book_id`),
  KEY `FK_BOOK_REFERENCE_TYPE` (`type_id`),
  CONSTRAINT `FK_BOOK_REFERENCE_TYPE` FOREIGN KEY (`type_id`) REFERENCES `Type` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `Book` */

insert  into `Book`(`book_id`,`type_id`,`book_name`,`book_author`,`book_info`,`book_image`,`book_address`,`book_status`,`book_count`,`book_delete`,`borrow_number`,`book_language`,`author_country`,`book_star`) values ('1',4,'五号屠场','库尔特·冯内古特','《五号屠场》是一部独特的反战小说。作者在小说中塑造了一个对敌无害、对友无益的患精神分裂症的主人公毕利，通过这个傻乎乎的主人公的独特感受，一方面谴责德国法西斯的残暴，另一方面又抨击了盟国轰炸德累斯顿的野蛮行为，嘲笑人类发动战争是多么愚蠢。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/1.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697240462&Signature=kHiZ2HZsqawVsfz%2BmP/GFsGpEpY%3D','2A002-56-58','BS002',5,0,10,'LE001','CA006',4.5),('10',1,'厌女：日本的女性嫌恶','上野千鹤子, 王兰','《厌女：日本的女性嫌恶》不是一部纯理论性著作，而是运用女性主义理论针对日本当代的各种社会现象的实践性分析。她运用的理论资源中包括上海三联去年出版的赛吉维克著《男人之间：英国文学与男性同性社会性欲望》，但与那种纯理论性的晦涩论著不同，《厌女》用比较浅近的语言对现象进行了解读。在目前的中国，尚没有一部在理论上很有深度但针对的却是非常具体的现象的性别论著，《厌女》雅俗共赏的特点很可能让它成为知识分子热议的话题书，进而成为关心当代中国性别状况的媒体人、年轻读者追捧的畅销书','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/10.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697250322&Signature=WXvLrlICQZZ6zrh8D1mpY/RZh8o%3D','3A005-56-22','BS002',5,0,1,'LJ006','CJ007',4),('11',8,'夏天、烟火和我的尸体','乙一','竹笼眼、竹笼眼、竹笼眼，笼中的鸟儿 何时何时放天飞，凌晨夜 鹤与龟，摔一跤 背后的孩子、是~谁、沁人心脾的恐惧，令人哑然的结局。更附有精美的青马特刊，多达6篇的试读选段，让喜欢推理、惊悚故事的您大饱眼福！9岁那年，我死了。我被我的好朋友推下了树。睁开眼睛看着弥生和阿健为了藏匿我的尸体而屡次冒险，我的尸体也不免感到紧张。最后的藏匿地点是否安全呢？我的尸体是否真的不会被发现呢？','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/11.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697250586&Signature=XjBJ7QMUB8twJpPL25ytBCdk%2BSw%3D','5B012-01-55','BS002',3,0,3,'LJ006','CJ007',5),('12',3,'鲁迅全集','鲁迅','新版《鲁迅全集》由原来的16卷增至18卷，书信、日记各增加了一卷，共计创作10卷，书信4卷，日记3卷，索引1卷，总字数约700万字。与1981年版相比，此次《鲁迅全集》修订集中在三个方面：佚文佚信的增收；原著的文本校勘；注释的增补修改。 ★收文：书信、日记各增加了一卷 此次修订，增收新的佚文23篇，佚信20封，鲁迅致许广平的《两地书》原信68封，鲁迅与增田涉答问函件集编文字约10万字。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/12.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697250739&Signature=HPf2YrT2WUFgY4AYjlfJobqX%2BpM%3D','4B006-45-11','BS002',10,0,123,'LC002','CC002',5),('13',7,'克莱因壶','冈岛二人','什么时候你开始怀疑这个世界是假的？日本虚拟现实VR题材开山杰作，超前《盗梦空间》20年！传奇推理作家组合冈岛二人预言之书，以超强的技术预见力，30年前就已完美构想出虚拟现实游戏体验装置！这是一部拥有“噩梦特质”的小说，是一次鲜有的、堪比3D观影、脑洞大开的、深陷其中难以自拔的独特体验。——资深推理人天蝎小猪，上杉彰彦从未如此兴奋——他写的故事被伊普西隆研发公司买下，即将制作成颠覆时代的全新游戏《克莱因2》（Klein 2），并受邀与少女梨纱一同担任游戏测试员。上杉彻底陷入了由K2造就的完美虚拟世界，为它的逼真、超前赞叹不已。然而随着测试过程的深入，伊普西隆公司行事神秘得令他生疑，游戏中更不断听到有人警告他：“快逃！\"与此同时，一个自称是梨纱好友的女孩找到上杉，她说梨纱已失踪多日，音信全无。然而在寻人的过程中，两人都开始怀疑对方在撒谎，因为他们的记忆完全对不上…','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/13.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697250997&Signature=/ru9JH86ycbxpnSVyqtLB2wE6ZQ%3D','2A002-55-23','BS002',5,0,11,'LJ006','CJ007',5),('14',7,'香水','帕·聚斯金德','《香水:一个谋杀犯的故事(新版)》是有史以来最畅销的德文小说，它构思奇特，寓意深刻，1985年出版以来，始终高踞德国畅销书排行榜前列，已被译成30余种文字。根据小说改编拍摄的同名电影耗资5000万欧元，全球票房过亿，并获得“德国奥斯卡”——德国电影奖八项提名，且最终获得最佳摄影、美术、剪辑、音响在内的六项大奖。格雷诺耶出生在巴黎最臭的鱼市上。他天生没有体味，而嗅觉却异常灵敏。长大后他成了巴黎一香水大师的学徒，从而也渐渐产生了用香水征服世界的野心。一天，他发现一少女的气味令人着迷，无意中杀死了该少女，并嗅光了她的体香。之后，他先后杀死了26个少女，萃取了她们的体味，并蒸馏出神奇的香水。当他的罪行败露，被押赴刑场时，他释放了一瓶奇特的香水……','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/14.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697261835&Signature=e7feXrEzZSf0An2vDIg8TuK3sCo%3D','3A005-45-11','BS002',5,0,31,'LG003','CG003',5),('15',2,'平原上的摩西','双雪涛','作为写作者，我是地道的学徒。回看自己写过的东西，中短篇十几个，大多是过去两年所写，乏善可陈者多之，差强人意者几个，默然自傲者极少，有几个竟极其陌生，好像是他人所做，混到自己的文档里。长篇写了两个，都不真正长，十万字出头：一个类似于中短篇集锦，当时企望能承接《史记》的传统，勉力写人，现在再看，多少有混乱自恋之处；一个向村上春树致敬，想写个综合性的虚构品，于是矫揉造作处多，如同小儿舞着大刀，颠倒手脚。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/15.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697262041&Signature=hlY2tEtSpU5O8ha99VIPTpiw2wQ%3D','5B005-12-11','BS002',5,0,12,'LC002','CC002',4),('16',7,'生死疲劳','莫言','小说的叙述者，是土地改革时被枪毙的一个地主，他认为自己虽有财富，并无罪恶，因此在阴间里他为自己喊冤。在小说中他不断地经历着六道轮回，一世为驴、一世为牛、一世为猪、一世为狗、一世为猴、一世为人……每次转世为不同的动物，都未离开他的家族，离开这块土地。小说正是通过他的眼睛，准确说，是各种动物的眼睛来观察和体味农村的变革。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/16.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697262216&Signature=C04ni8MqM%2BwdSRLCbMfCOGHIjKM%3D','2A002-54-29','BS002',5,0,50,'LC002','CC002',4.8),('17',7,'哈利波特全集','J.K.罗琳','《哈利·波特》（Harry Potter）是英国作家J.K.罗琳（J. K. Rowling）于1997～2007年所著的魔幻文学系列小说，共7部。其中前六部以霍格沃茨魔法学校（Hogwarts School of Witchcraft and Wizardry）为主要舞台，描写的是主人公——年轻的巫师学生哈利·波特在霍格沃茨前后六年的学习生活和冒险故事；第七本描写的是哈利·波特在第二次魔法界大战中在外寻找魂器并消灭伏地魔的故事。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/17.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697263330&Signature=n2sF6pLo02LdeJ9SblqVFpS2fJk%3D','2A002-54-23','BS002',3,0,20,'LE001','CE001',4.6),('18',7,'云边有个小卖部','张嘉佳','本书讲述了云边镇少年刘十三的成长故事。\r\n少年刘十三自幼与开小卖部的外婆相依为命，努力读书为了离开小镇，追寻远方与梦想。在城市里四处碰壁受挫的刘十三回到了小镇，与少时玩伴程霜重逢。小镇生活平静却暗潮汹涌，一个孤儿，一场婚礼，一场意外，几乎打破了所有人的生活。\r\n\r\n为了完成一个几乎不可能完成的任务，刘十三拼尽全力，却不知道，生命中更重要的正在离自己而去。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com/librarySystem/book/18.jpg','2A002-54-30','BS002',10,0,6,'LC002','CC002',5),('19',1,'毛泽东选集（1-5卷）','毛泽东','《毛泽东选集》是毛泽东思想的重要载体，《毛泽东选集》是毛泽东思想的集中展现，是对20世纪中国影响最大的书籍之一。建国后两个版别的《毛泽东选集》，均由人民出版社出版。《毛泽东选集》在建国前即有大量出版。自1944年晋察冀日报社出版第一部《毛泽东选集》。建国后出版的《毛泽东选集》一至四卷，编入的是毛泽东同志在新民主主义革命时期的主要著作。第一版《毛泽东选集》一至四卷，分别于20世纪50年代初和60年代初出版。1991年7月1日，《毛泽东选集》一至四卷第二版正式出版发行。邓小平同志为新版《毛泽东选集》题写了书名。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/19.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697499772&Signature=njll/p1/PzJZXXOcj6zHqOxASjw%3D','1A001-01-01','BS002',10,0,100,'LC002','CC002',5),('2',7,'金瓶梅(全二册)','兰陵笑笑生','《金瓶梅》是我国第一部文人独立创作的长篇白话世情章回小说，以“禁书”、“奇书”闻名。本书借《水浒传》中武松杀嫂一段故事为引子，写潘金莲未被武松杀死，嫁给西门庆为妾，由此转入小说的主体，通过对西门庆及其家庭罪恶生活的描述，体现当时民间的生活面貌。以市井人物为主要角色，通过多侧面多层次的描写，将明代社会的黑暗腐朽暴露得淋漓尽致。在这一点上，《金瓶梅》远胜过《水浒传》。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/2.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697240619&Signature=7dXFQ4sm3ua3WYtVv5k4JbHiQkM%3D','2A002-55-03','BS003',5,0,50,'LC002','CC002',5),('20',6,'认知觉醒','周岭','这是一部可以穿透时间的个人成长方法论。通过“大脑构造、潜意识、元认知”等思维规律，你将真正看清自己；通过“深度学习、关联、反馈”事物规律，你将洞悉如何真正成事！如果对自己不了解，我们就会被人的原始天性束缚，这往往会让我们感到很痛苦。然而，如果了解大脑知识，我们就可以观察并指导自己，运用认知的力量去克服天性，从而获得长久而清晰的内在动力，让我们告别绝大多数人生痛苦。一个人的认知越清晰，行动就越坚定。本书解答了很多问题：为什么我们做事总是急于求成、避难趋易？所谓耐心，就是要“咬牙坚持、死磕到底”吗？如何彻底告别用“三分钟热情”和“打鸡血”的方式做事？如何保持极度专注？如何消除焦虑？如何提高学习能力？……一个人真正的耐心从来不是苦哈哈的毅力支撑，而是具有长远目光的结果。只要了解大脑规律，并知晓诸如“复利效应、舒适区边缘、学习权重比、学习平台期...','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/20.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697499971&Signature=RE6gxwlcpqoAxlhvy1qEsTHQ%2BT8%3D','2A002-02-23','BS002',5,0,2,'LC002','CC002',4.2),('21',6,'起床后的黄金1小时','Benjamin Spall,Michael Xander','64位成功人士不藏私分享 50%成功案例 + 50%獨立思維 = 100%專屬早晨習慣 因為 你 就是最佳解答 ★榮獲2018 亞馬遜商業類最佳書籍選書★ ★榮獲2018 5月《金融時報》商業類選書★ ★榮獲《商業內幕》商業類最佳書籍今夏選書★ 「想過什麼樣的人生，就過什麼樣的早晨！」 我們每天做的小事不但會影響自我成長，也會形塑我們的生活樣貌，決定我們成為什麼樣的人。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/21.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697500162&Signature=PtZCXQe9qzcc3EvBn2QkgLGirAs%3D','2A002-23-12','BS002',5,0,4,'LE001','CA006',4.3),('22',8,'杀死一只知更鸟','Harper Lee','成长总是个让人烦恼的命题。成长有时会很缓慢，如小溪般唱着叮咚的歌曲趟过，有时却如此突如其来，如暴雨般劈头盖脸……三个孩子因为小镇上的几桩冤案经历了猝不及防的成长——痛苦与迷惑，悲伤与愤怒，也有温情与感动。这是爱与真知的成长经典。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/22.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697500407&Signature=IjuoQp%2B/aqNU2x29AVhtRy0LLS8%3D','3A005-45-56','BS002',5,0,1,'LE001','CA006',4.6),('23',4,'silent reading','priest','Childhood, upbringing, family background, social relationships, trauma... We are constantly tracing and searching for the motives of the perpetrators, searching for the most subtle joys, sorrows and sorrows, not to put ourselves in sympathy or even forgive them, not to excuse the crime The reason is not to bow to the so-called complexity of human nature, not to reflect on social contradictions, nor to alienate ourselves into monsters—we are just looking for a justice for ourselves and those who still have expectations for this world. just to deal with.','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/23.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697500747&Signature=M1%2B4cSTu5Wc5/sfvrEfM5R3bawc%3D','3A005-12-12','BS002',5,0,2,'LE001','CA006',4.2),('24',3,'红玫瑰与白玫瑰','张爱玲','“也许每一个男子全都有过这样的两个女人，至少两个。娶了红玫瑰，久而久之，红的变了墙上的一抹蚊子血，白的还是\"床前明月光\"；娶了白玫瑰，白的便是衣服上沾的一粒饭黏子，红的却是心口上一颗朱砂痣”。因为《红玫瑰与白玫瑰》这句话成了脍炙人口的名言……本书收录了张爱玲1944年的中短篇小说作品。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/24.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697500884&Signature=H/BdHs7X43gkG7gtHf6yEEItxVY%3D','3A005-12-54','BS002',5,0,10,'LC002','CC002',5),('25',6,'恋练有词考研英语词汇识记与应用大全','俞敏洪','核心词汇由910组词群构成，每组词群包含一个主词及其形近词或同根词，同时还总结了相关词汇。这样归类可以快速提升同学们的记忆效率，找到每个单词的记忆源头，不再是孤立地记单词，而是形成串联式的记忆。此外，这样的编排也可以帮助同学们更好地辨析形近词和近义词的词义和用法，做到精准记忆。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/25.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697501028&Signature=wNbKGzUnJlCgttoRQNucQfoE1g4%3D','2B005-12-54','BS002',5,0,30,'LC002','CC002',3.2),('26',7,'解忧杂货店','东野圭吾','现代人内心流失的东西，这家杂货店能帮你找回——僻静的街道旁有一家杂货店，只要写下烦恼投进卷帘门的投信口，第二天就会在店后的牛奶箱里得到回答。因男友身患绝症，年轻女孩静子在爱情与梦想间徘徊；克郎为了音乐梦想离家漂泊，却在现实中寸步难行；少年浩介面临家庭巨变，挣扎在亲情与未来的迷茫中……他们将困惑写成信投进杂货店，随即奇妙的事情竟不断发生。生命中的一次偶然交会，将如何演绎出截然不同的人生？如今回顾写作过程，我发现自己始终在思考一个问题：站在人生的岔路口，人究竟应该怎么做？我希望读者能在掩卷时喃喃自语：我从未读过这样的小说。——东野圭吾','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/26.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697501213&Signature=oR9/m6DOvFufUKAxIC0fa0X1mnk%3D','5B002-15-12','BS002',4,0,21,'LJ006','CJ007',5),('27',1,'影响力','Robert Cialdini','自1986年出版以来，作为获得美国心理协会、美国心理学基金会年度大奖提名的西奥迪尼经典著作，本书已经成为史上最强大、最震摄人心、最诡谲的心理学畅销书，24年来，被翻译成26种文字广为传播，全球销量超过300万册。时至今日，本书仍位列亚马逊总排行榜Top400之中，并雄踞消费者行为学和应用心理学类畅销书榜首。也正是在这个畅销版本的基础上，西奥迪尼才衍生出后来为斯坦福大学等多所名校所采用的教材版本。 没有专家解读，没有每章导读，这是一本最原汁原味的《影响力》，醇厚甘甜，久而弥笃。阅读本书仿佛是与西奥迪尼面对面，让你可以细细聆听，在定格的经典中体味无孔不入的影响力。 本书由斩获国家级图书大奖”文津奖”的《牛奶可乐经济学》的译者担纲翻译，语言生动流畅，意趣盎然，西奥迪尼式的诡谲与雄辩惟妙惟肖，跃然纸上。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/27.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697501431&Signature=lwxpYw0QClHl1wwbjahUUGNzAtc%3D','1B002-15-23','BS002',5,0,2,'LE001','CA006',4.8),('28',9,'山茶文具店','小川糸','在镰仓，有一家帮人代笔的文具店，每代店主均由女性担任，只要有委托便会接受，即使是餐厅的菜单也会帮忙。不知不觉间，雨宫鸠子成为了第11代传人，而与外祖母之间的误会，以及开始独自一人活在世上的恐惧，使她充满迷茫。给死去宠物的吊唁信、宣布离婚的公告信、拒绝借钱的回绝信、写给挚友的分手信……一封封代笔信是客人们的写实生活，也是一节节人生的课堂。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/28.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697501803&Signature=O3M5m8r46SwK84HK8Jr7HAuIbZE%3D','2B005-22-13','BS002',5,0,1,'LJ006','CJ007',4.8),('29',1,'秋园','杨本芬','听八旬奶奶讲述她和妈妈的故事。1914年，世上有了“秋园”这个人。1918年，汉语有了“她”这个字。秋园，她来过，挣扎过，绝望过，幸福过。今天，她80岁的女儿，把普普通通的她，讲给世界听。“我写了一位普通中国女性一生的故事，写了我们一家人如何像水中的浮木般挣扎求生，写了中南腹地那些乡间人物的生生死死。我知道自己写出的故事如同一滴水，最终将汇入人类历史的长河。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/29.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697502871&Signature=HR9ynFJrxL/lcdMdeV2J81C4WzI%3D','3A005-45-12','BS002',5,0,2,'LC002','CC002',4.8),('3',7,'三体全集 （套装共3册）','刘慈欣','《三体》是刘慈欣创作的系列长篇科幻小说，由《三体》、《三体Ⅱ·黑暗森林》、《三体Ⅲ·死神永生》组成，第一部于2006年5月起在《科幻世界》杂志上连载，第二部于2008年5月首次出版，第三部则于2010年11月出版。作品讲述了地球人类文明和三体文明的信息交流、生死搏杀及两个文明在宇宙中的兴衰历程。其第一部经过刘宇昆翻译后获得了第73届雨果奖最佳长篇小说奖 ','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/3.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697240766&Signature=%2BNuariXRrXEwhWRJDALjjKLXQiU%3D','3E001-98-10','BS002',5,0,20,'LC002','CC002',4.8),('30',7,'局外人','阿尔贝•加缪','“今天妈妈死了。也许是昨天，我不清楚。”我是默尔索，一名普通的公司职员。我爱妈妈，但在葬礼上，我没有哭。第二天回到家，和女朋友玛丽寻欢，事后她问我爱不爱她，我说不爱。结婚前夕，一位刚认识的朋友邀请我去海边玩。途中，我连开五枪，杀死了一个陌生人。入狱后被叛了死刑，我选择不上诉。人们说我冷漠、自私、固执。这或许很荒诞，却是我的真实人生——我就是要活出真实的自己，哪怕成为你眼中的局外人！','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/30.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697503062&Signature=HE8hiwg57zTJqf5o7lOY2KcX85U%3D','2B005-15-33','BS002',5,0,2,'LF004','CF004',4.3),('31',1,'罗翔说刑法系列','罗翔','『刑法学讲义』+『刑罚的历史』+『刑法中的同意制度』套装。普法故事会，一起做法治之光）(套装共3册)','https://java-ljw.obs.cn-north-4.myhuaweicloud.com/librarySystem/book/31.jpg','3A006-56-13','BS002',5,0,2,'LC002','CC002',4.6),('4',9,'明朝那些事儿','当年明月','《明朝那些事儿》主要讲述的是从1344年到1644年这三百年间关于明朝的一些事情，以史料为基础，以年代和具体人物为主线，并加入了小说的笔法，对明朝十七帝和其他王公权贵和小人物的命运进行全景展示，尤其对官场政治、战争、帝王心术着墨最多，并加入对当时政治经济制度、人伦道德的演义。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/4.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697241013&Signature=7EtWH59N0EP4svnZYI6JvWPT0NQ%3D','4A005-25-34','BS002',5,0,5,'LC002','CC002',4.3),('5',8,'你当像鸟飞往你的山','塔拉·韦斯特弗','人们只看到我的与众不同：一个十七岁前从未踏入教室的大山女孩，却戴上一顶学历的高帽，熠熠生辉。只有我知道自己的真面目：我来自一个极少有人能想象的家庭。我的童年由垃圾场的废铜烂铁铸成，那里没有读书声，只有起重机的轰鸣。不上学，不就医，是父亲要我们坚持的忠诚与真理。父亲不允许我们拥有自己的声音，我们的意志是他眼中的恶魔。哈佛大学，剑桥大学，哲学硕士，历史博士……我知道，像我这样从垃圾堆里爬出来的无知女孩，能取得如今的成就，应当感激涕零才对。但我丝毫提不起热情。我曾怯懦、崩溃、自我怀疑，内心里有什么东西腐烂了，恶臭熏天。直到我逃离大山，打开另一个世界。那是教育给我的新世界，那是我生命的无限可能。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/5.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697241360&Signature=dzMjWYwYwd9gQ%2BL2GTxQ/d7abyQ%3D','1A005-25-45','BS002',5,0,5,'LE001','CA006',5),('6',7,'囚鸟','库尔特·冯内古特','我们都是受困于时代的“囚鸟”——既渴望逃离，又踟蹰不前。联邦最低限度安保措施成人改造所里，颓唐的小老头儿瓦尔特•斯代布克正在等待领他出狱的狱卒。在他过去的人生中，他曾是斯拉夫移民的儿子，哈佛大学毕业生，前共产党党员，前联邦政府官员，“水门事件”的涉案者……不久他还将获得一个新的身份，神秘的……','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/6.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697241718&Signature=K1Bvg%2BCyoVM0k81zR3UDovw0a6A%3D','1A005-23-44','BS002',5,0,1,'LE001','CA006',4.3),('7',7,'绝叫','叶真中显','某处简陋公寓，发现一具数月前死亡的女性尸体。事后死者被屋中十几只饥饿的猫啃噬，惨不忍睹。经警方初步判定，这个名叫铃木阳子的女人系典型的孤独死，可是负责此案的女警官奥贯绫乃（小西真奈美 饰）却有不同的看法、现场的蛛丝马迹显示，阳子之死另有隐情。镜头回溯，我们看到阳子（尾野真千子 饰）跌宕黯淡的一生，她从小不受母亲待见，长大后独自前往东京发展，为了多卖一份保险饱受凌辱。后来，遇人不淑的阳子邂逅利用游民骗取生活补助的神代武（安田显 饰），由此推开了通往地狱的大门。每走一步，阳子都是在绝望地和过去的自己决裂……','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/7.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697242110&Signature=mKF2iLV2sFKhdmWr3MzZWWZt82Y%3D','2A002-55-56','BS002',5,0,20,'LC002','CC002',4.1),('8',7,'围城','钱锺书','围城故事发生于1920到1940年代。主角方鸿渐是个从中国南方乡绅家庭走出的青年人，迫于家庭压力与同乡周家女子订亲。但在其上大学期间，周氏患病早亡。准岳父周先生被方所写的唁电感动，资助他出国求学。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/8.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697242861&Signature=mj9cJ18Zb2Edar4UG%2Bct1cDK/u0%3D','2A002-55-25','BS002',3,0,52,'LC002','CC002',5),('9',1,'自私的基因','理查德·道金斯','《自私的基因》是英国演化理论学者理查德·道金斯创作的科普读物，首次出版于1976年。作者在《自私的基因》中提出：人们生来是自私的。人类窥见了社会关系中基本的对称性和逻辑性，在人们有了更充分的理解之后，人们的政治见解当会重新获得活力，并对心理学的科学研究提供理论上的支柱。在这一过程中，人们也必将对自己受苦受难的许多根源有一个更深刻的理解。道金斯在《自私的基因》中的突破性贡献在于，把根据自然选择的社会学说的这一重要部分，用简明通俗的形式，妙趣横生的语言介绍给读者。','https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/book/9.jpg?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697243464&Signature=ZfO1OmVHDazn6S4pKCXDQrZ5Ufs%3D','3A005-56-12','BS002',4,0,3,'LE001','CE001',4.2),('test',1,'test','test','test',NULL,'test','BS002',1,0,17,'LC002','CE001',1);

/*Table structure for table `BookShelf` */

DROP TABLE IF EXISTS `BookShelf`;

CREATE TABLE `BookShelf` (
  `shelf_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '书架id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `book_id` varchar(50) NOT NULL COMMENT '图书id',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `book_delete` int(11) DEFAULT '0' COMMENT '书架逻辑删除',
  `book_star` int(11) DEFAULT '0' COMMENT '图书评价',
  PRIMARY KEY (`shelf_id`),
  KEY `FK_BOOKSHEL_REFERENCE_USER` (`user_id`),
  KEY `FK_BOOKSHEL_REFERENCE_BOOK` (`book_id`),
  CONSTRAINT `FK_BOOKSHEL_REFERENCE_BOOK` FOREIGN KEY (`book_id`) REFERENCES `Book` (`book_id`),
  CONSTRAINT `FK_BOOKSHEL_REFERENCE_USER` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `BookShelf` */

insert  into `BookShelf`(`shelf_id`,`user_id`,`book_id`,`add_time`,`book_delete`,`book_star`) values (7,1,'2','2022-10-23 14:48:49',0,0),(14,1,'8','2022-10-24 14:39:43',0,0),(17,2,'2','2022-10-26 08:26:55',0,0),(18,2,'8','2022-10-26 08:26:58',0,0);

/*Table structure for table `Borrow` */

DROP TABLE IF EXISTS `Borrow`;

CREATE TABLE `Borrow` (
  `borrow_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '借阅id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `book_id` varchar(50) NOT NULL COMMENT '图书id',
  `borrow_time` datetime NOT NULL COMMENT '借阅时间',
  `return_time` datetime NOT NULL COMMENT '归还时间',
  `borrow_tatus` char(5) NOT NULL DEFAULT 'BWS01' COMMENT '借阅状态',
  `borrow_delete` int(11) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`borrow_id`),
  KEY `FK_BORROW_REFERENCE_USER` (`user_id`),
  KEY `FK_BORROW_REFERENCE_BOOK` (`book_id`),
  CONSTRAINT `FK_BORROW_REFERENCE_BOOK` FOREIGN KEY (`book_id`) REFERENCES `Book` (`book_id`),
  CONSTRAINT `FK_BORROW_REFERENCE_USER` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `Borrow` */

insert  into `Borrow`(`borrow_id`,`user_id`,`book_id`,`borrow_time`,`return_time`,`borrow_tatus`,`borrow_delete`) values (8,1,'8','2022-10-23 14:41:58','2022-11-01 14:41:58','BWS02',1),(9,1,'9','2022-10-23 15:28:20','2022-11-22 15:28:20','BWS05',0),(10,1,'9','2022-10-24 15:58:37','2022-11-23 15:58:37','BWS01',0);

/*Table structure for table `Comments` */

DROP TABLE IF EXISTS `Comments`;

CREATE TABLE `Comments` (
  `comments_id` bigint(20) NOT NULL COMMENT '留言id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `book_id` varchar(50) DEFAULT NULL COMMENT '图书id',
  `comments_info` varchar(500) DEFAULT NULL COMMENT '留言内容',
  `delete_comments` int(11) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`comments_id`),
  KEY `FK_COMMENTS_REFERENCE_USER` (`user_id`),
  KEY `FK_COMMENTS_REFERENCE_BOOK` (`book_id`),
  CONSTRAINT `FK_COMMENTS_REFERENCE_BOOK` FOREIGN KEY (`book_id`) REFERENCES `Book` (`book_id`),
  CONSTRAINT `FK_COMMENTS_REFERENCE_USER` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `Comments` */

insert  into `Comments`(`comments_id`,`user_id`,`book_id`,`comments_info`,`delete_comments`) values (1584368878876798976,1,'8','测试33',0);

/*Table structure for table `Constant` */

DROP TABLE IF EXISTS `Constant`;

CREATE TABLE `Constant` (
  `coding` char(5) NOT NULL COMMENT '编码',
  `value` varchar(200) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`coding`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `Constant` */

insert  into `Constant`(`coding`,`value`) values ('ACS01','报名中'),('ACS02','活动进行中'),('ACS03','活动已经结束'),('APS01','报名中'),('APS02','报名成功'),('APS03','报名失败'),('APS04','活动进行中'),('APS05','活动已经结束'),('AS001','正在预约'),('AS002','预约已结束'),('AS003','预约撤回'),('AS004','预约失败'),('AS005','预约成功'),('ASU01','已激活'),('ASU02','未激活'),('BS001','暂无'),('BS002','可借阅'),('BS003','不可借阅'),('BWS01','正在借阅中'),('BWS02','已归还'),('BWS03','已逾期'),('BWS04','续租中'),('BWS05','归还未确认'),('CA006','美国'),('CC002','中华人民共和国'),('CE001','英国'),('CF004','法国'),('CG003','德国'),('CI005','意大利'),('CJ007','日本'),('FS001','已开放'),('FS002','未开放'),('GM001','男'),('GW002','女'),('LC002','中文'),('LE001','英文'),('LF004','法语'),('LG003','德语'),('LI005','意大利语'),('LJ006','日语'),('RS001','未加入图书库'),('RS002','已加入图书库'),('RS003','未找到'),('RT001','上午'),('RT002','下午');

/*Table structure for table `Manager` */

DROP TABLE IF EXISTS `Manager`;

CREATE TABLE `Manager` (
  `manager_id` int(11) NOT NULL COMMENT '管理员id',
  `manager_name` varchar(50) NOT NULL COMMENT '管理员姓名',
  `manager_age` int(11) NOT NULL COMMENT '管理员年龄',
  `manager_gender` char(5) NOT NULL COMMENT '管理员性别',
  `manager_telephone` char(11) DEFAULT NULL COMMENT '管理员电话',
  `manager_address` varchar(100) DEFAULT NULL COMMENT '管理员地址',
  `manager_delete` int(11) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`manager_id`),
  CONSTRAINT `FK_MANAGER_REFERENCE_MANAGERL` FOREIGN KEY (`manager_id`) REFERENCES `ManagerLogin` (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `Manager` */

/*Table structure for table `ManagerLogin` */

DROP TABLE IF EXISTS `ManagerLogin`;

CREATE TABLE `ManagerLogin` (
  `manager_id` int(11) NOT NULL COMMENT '管理员id',
  `manager_login` varchar(50) NOT NULL COMMENT '管理员登陆账号',
  `manager_passwd` varchar(50) NOT NULL COMMENT '管理员密码',
  `permission` int(11) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ManagerLogin` */

/*Table structure for table `Open` */

DROP TABLE IF EXISTS `Open`;

CREATE TABLE `Open` (
  `floor_id` int(11) NOT NULL COMMENT '楼层id',
  `floor_name` varchar(50) NOT NULL COMMENT '楼层名称',
  `floor_status` char(5) NOT NULL COMMENT '楼层状态',
  `people_count` int(11) NOT NULL COMMENT '预约人数',
  `time_id` int(11) NOT NULL COMMENT '时间id',
  PRIMARY KEY (`floor_id`),
  KEY `FK_OPEN_REFERENCE_APPOINTM` (`time_id`),
  CONSTRAINT `FK_OPEN_REFERENCE_APPOINTM` FOREIGN KEY (`time_id`) REFERENCES `AppointmentsTime` (`time_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `Open` */

/*Table structure for table `Scheduled` */

DROP TABLE IF EXISTS `Scheduled`;

CREATE TABLE `Scheduled` (
  `scheduled_id` bigint(20) NOT NULL COMMENT '预定id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `book_name` varchar(100) DEFAULT NULL COMMENT '图书名称',
  `scheduled_status` char(5) DEFAULT 'RS001' COMMENT '预定状态',
  PRIMARY KEY (`scheduled_id`),
  KEY `FK_SCHEDULE_REFERENCE_USER` (`user_id`),
  CONSTRAINT `FK_SCHEDULE_REFERENCE_USER` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `Scheduled` */

insert  into `Scheduled`(`scheduled_id`,`user_id`,`book_name`,`scheduled_status`) values (1584480561142112256,1,'张三的幸福一天','RS001');

/*Table structure for table `Type` */

DROP TABLE IF EXISTS `Type`;

CREATE TABLE `Type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书种类id',
  `type_name` varchar(100) NOT NULL COMMENT '种类名称',
  `type_info` varchar(500) DEFAULT NULL COMMENT '种类介绍',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `Type` */

insert  into `Type`(`type_id`,`type_name`,`type_info`) values (1,'社会科学','社科类书籍就是社会科学范围内的书籍。社科是社会科学的简称，是以社会现象为研究对象的科学。它的任务是研究与阐述各种社会现象及其发展规律。社会科学是关于社会事物的本质及其规律的科学。'),(2,'自然科学图书','自然科学的根本目的在于寻找隐藏在自然现象背后的规律，但是自然科学的工作尚不包括研究为什么会存在这些规律。自然科学认为超自然的、随意的和自相矛盾的现象是不存在的。自然科学的最重要的两个支柱是观察和逻辑推理。由对自然的观察和逻辑推理自然科学可以引导出大自然中的规律。假如观察的现象与规律的预言不同，那么要么是因为观察中有错误，要么是因为至此为止被认为是正确的规律是错误的。一个超自然因素是不存在的。'),(3,'中文图书','中文图书'),(4,'外文图书','外文图书'),(5,'普通图书','是一堆带有和主题相关内容的纸张，带有文字或图像。书通常由墨水、纸张、羊皮纸或者其他材料固定在书脊上组成。组成书的一张纸称为一张，一面称为一页。在图书馆信息学中，书被称为专著，以区别于杂志、学术期刊、报纸等连载期刊。所有的书面作品（包括图书）的主体是文学。在小说和一些类型（如传记）中，书可能还要分成卷。对书特别喜爱的人被称为爱书者或藏书家，更随意的称呼是书虫或者书呆子。'),(6,'工具书','工具书指一种在学习中和工作中可以作为工具使用的特定类型的书籍，专供查考资料，以解决工作或学习过程中所遇到的某些疑难问题。这些书籍经过汇集、编著或译述的资料，把相关领域的问题与其有关的知识资料按特定的编排方法汇集在一起，形成特定的编排和检索方式，可以让读者于短时间内查出答案，以供参考。'),(7,'小说','小说是文学的一种样式，一般描写人物故事，塑造多种多样的人物形象，但亦有例外。它是拥有不完整布局、发展及主题的文学作品。而对话是不是具有鲜明的个性，每个人物说的没有独特的语言风格，是衡量小说水准的一个重要标准。与其他文学样式相比，小说的容量较大，它可以细致的展现人物性格和命运，可以表现错综复杂的矛盾冲突，同时还可以描述人物所处的社会生活环境。'),(8,'儿童读物','读经教育是一种主要流行于大中华地区的教育主张。其支持者认为儿童自出生至13岁前是记忆力最好的年龄阶段，通过诵读古今中外最经典的篇章，使少年儿童接触代表人类最高智慧的经典文化，以达到潜移默化儿童性情，培养读书风气，启发儿童潜能，落实文化扎根，目标是使儿童成为有气质的文化人；但在实际操作中，读经教育出现了脱离实际等各种问题。'),(9,'非虚构作品','非虚构作品，即纪实作品，与虚构作品相对，是报告或是任何描述事实的作品总称，'),(10,'专业书','泛指有书籍别于天赋，必须耗费时间经由学习、训练或工作经验，才能获得的能力。通常也会将技术涵盖在“专业技能”之内。'),(11,'手册','手册是汇集某一或若干学科和专业领域的基本知识、数据、术语、概念、原理、数据、图表、公式等内容的工具书或参考资料。手册按其收录的内容可分为综合性手册和专业性手册。综合性手册概括的知识面比较广泛。专业性手册的内容只涉及某一领域的专门知识。');

/*Table structure for table `User` */

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) NOT NULL COMMENT '用户姓名',
  `user_idcard` char(18) NOT NULL COMMENT '用户身份证号',
  `user_gender` char(5) NOT NULL COMMENT '用户性别',
  `user_telephone` char(11) DEFAULT NULL COMMENT '用户电话',
  `user_address` varchar(100) DEFAULT NULL COMMENT '用户住址',
  `user_email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `user_image` varchar(200) DEFAULT NULL COMMENT '用户头像',
  `user_age` int(11) DEFAULT NULL COMMENT '用户年龄',
  `user_birth` date DEFAULT NULL COMMENT '用户生日',
  `leaseRenewalNumber` int(11) DEFAULT '1' COMMENT '续租次数',
  `borrowing_number` int(11) DEFAULT '5' COMMENT '借阅次数',
  `status` char(5) DEFAULT 'ASU02' COMMENT '账号状态',
  `user_delete` int(11) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `userLogin_user` FOREIGN KEY (`user_id`) REFERENCES `UserLogin` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `User` */

insert  into `User`(`user_id`,`user_name`,`user_idcard`,`user_gender`,`user_telephone`,`user_address`,`user_email`,`user_image`,`user_age`,`user_birth`,`leaseRenewalNumber`,`borrowing_number`,`status`,`user_delete`) values (1,'张晓萌','211382200503020015','GM001','15566335998','花果山','1362468712@qq.com',NULL,56,'1973-06-21',1,4,'ASU01',0),(2,'苏家楷','211342200305239842','GM001','13344556778','垃圾堆','1352465378@qq.coom',NULL,66,'2022-10-21',1,5,'ASU01',0);

/*Table structure for table `UserAppointments` */

DROP TABLE IF EXISTS `UserAppointments`;

CREATE TABLE `UserAppointments` (
  `appointment_id` int(11) NOT NULL COMMENT '预约id',
  `floor_id` int(11) NOT NULL COMMENT '楼层id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `appointments_id` char(5) NOT NULL COMMENT '预约时间编号',
  `appointments_status` char(5) NOT NULL COMMENT '当前预约状态',
  `appointments_createTime` datetime DEFAULT NULL COMMENT '创建预约时间',
  `seatName` varchar(50) DEFAULT NULL COMMENT '座位名称',
  PRIMARY KEY (`appointment_id`),
  KEY `FK_USERAPPO_REFERENCE_OPEN` (`floor_id`),
  CONSTRAINT `FK_USERAPPO_REFERENCE_OPEN` FOREIGN KEY (`floor_id`) REFERENCES `Open` (`floor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `UserAppointments` */

/*Table structure for table `UserLogin` */

DROP TABLE IF EXISTS `UserLogin`;

CREATE TABLE `UserLogin` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_login` varchar(50) NOT NULL COMMENT '登录账号',
  `user_passwd` varchar(300) NOT NULL COMMENT '登陆密码',
  `permission` int(11) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `UserLogin` */

insert  into `UserLogin`(`user_id`,`user_login`,`user_passwd`,`permission`) values (1,'user_1','$2a$10$5KBiHsHpkpk05NzaE4UbRuEDgjRLVig7C0muodtL2NW2TCce6ffj6',1),(2,'user_2','$2a$10$ZIV/AYoQzTiAAVr8vVT4iOTEP1v595RIhaPBVX76mrQSksoqVLbTK',1);

/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `activityId` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `activityName` varchar(200) DEFAULT NULL COMMENT '活动名称',
  `activityTypeId` int(11) DEFAULT NULL COMMENT '活动种类id',
  `activityInfo` varchar(3000) DEFAULT NULL COMMENT '活动信息',
  `activityOrganizer` varchar(200) DEFAULT NULL COMMENT '活动举办方',
  `activityDate` datetime DEFAULT NULL COMMENT '活动截至日期',
  `activityApplication` datetime DEFAULT NULL COMMENT '报名截止日期',
  `activityAge` int(11) DEFAULT NULL COMMENT '活动要求年龄',
  `activityStatus` char(5) DEFAULT NULL COMMENT '活动状态',
  `activityDelete` int(11) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`activityId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `activity` */

insert  into `activity`(`activityId`,`activityName`,`activityTypeId`,`activityInfo`,`activityOrganizer`,`activityDate`,`activityApplication`,`activityAge`,`activityStatus`,`activityDelete`) values (1,'亲子贴贴乐',1,'父母和孩子一起贴贴','{\"organizer\":\"腾讯\"}','2022-10-27 10:18:13','2022-10-25 10:18:19',12,'ACS01',0),(2,'test',1,'dw',NULL,'2022-10-26 17:23:52','2022-10-25 17:23:54',11,'ACS01',1);

/*Table structure for table `activityType` */

DROP TABLE IF EXISTS `activityType`;

CREATE TABLE `activityType` (
  `activityTypeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动种类Id',
  `activityTypeName` varchar(300) DEFAULT NULL COMMENT '种类名称',
  `activityTypeInfo` varchar(500) DEFAULT NULL COMMENT '种类介绍',
  PRIMARY KEY (`activityTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `activityType` */

insert  into `activityType`(`activityTypeId`,`activityTypeName`,`activityTypeInfo`) values (1,'文化活动','传播中国传统文化');

/*Table structure for table `application` */

DROP TABLE IF EXISTS `application`;

CREATE TABLE `application` (
  `applicationId` bigint(20) NOT NULL COMMENT '报名id',
  `userId` int(11) DEFAULT NULL COMMENT '用户Id',
  `applicationStatus` char(5) DEFAULT 'APS01' COMMENT '报名状态',
  `activityId` int(11) DEFAULT NULL COMMENT '活动id',
  PRIMARY KEY (`applicationId`),
  KEY `FK_APPLICAT_REFERENCE_ACTIVITY` (`activityId`),
  CONSTRAINT `FK_APPLICAT_REFERENCE_ACTIVITY` FOREIGN KEY (`activityId`) REFERENCES `activity` (`activityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `application` */

insert  into `application`(`applicationId`,`userId`,`applicationStatus`,`activityId`) values (1,1,'APS03',1),(2,2,'APS01',1);

/*Table structure for table `bookTag` */

DROP TABLE IF EXISTS `bookTag`;

CREATE TABLE `bookTag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_value` varchar(20) DEFAULT NULL COMMENT '标签值',
  `tag_delete` int(11) DEFAULT '0' COMMENT '逻辑删除',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `bookTag` */

insert  into `bookTag`(`tag_id`,`tag_value`,`tag_delete`,`user_id`) values (1,'爱情',0,NULL),(2,'测试数据',0,1),(3,'幻想',0,1),(4,'纯情\n',0,1),(7,'历史',0,1);

/*Table structure for table `bookTap` */

DROP TABLE IF EXISTS `bookTap`;

CREATE TABLE `bookTap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookId` varchar(50) DEFAULT NULL COMMENT '图书id',
  `tapId` int(11) DEFAULT NULL COMMENT '标签id',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `bookTap` */

insert  into `bookTap`(`id`,`bookId`,`tapId`,`userId`) values (1,'8',1,NULL),(2,'8',2,1),(4,'7',3,1),(5,'3',4,1),(10,'2',7,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
