CREATE DATABASE db_Card;
USE db_Card;

CREATE TABLE `User`( -- 用户表
	uid VARCHAR(32) PRIMARY KEY,-- 用户编号
	phonenumber VARCHAR(32), -- 手机号
	`name` VARCHAR(20),-- 姓名
	`password` PASSWORD-- 刚开始编码、单元测试的时候用Varchar，之后改成password类型
);
INSERT INTO `User` VALUES('1','18363999932','齐大佩','123');

CREATE TABLE Card(-- 名片
	cid VARCHAR(32) PRIMARY KEY,-- 名片编号
	phonenumber VARCHAR(32),
	`name` VARCHAR(20),-- 姓名，一个人有多个名字，比如真名、艺名、cn，他可能不是每次都想把真名告诉别人
	-- `desc` VARCHAR(100),-- 使用对象：比如普通朋友、商业朋友、陌生人，只能名片所有者查看		
	qq VARCHAR(15),-- qq号	
	weixin VARCHAR(15),-- 微信号	
	`time` DATETIME, -- 创建时间
	more VARCHAR(300), -- 更多
	shuxing VARCHAR(32), -- 属性
	miaoshu VARCHAR(100),-- 描述 
	cardid INT, -- 保存服务器的card主键
	uid VARCHAR(32),-- 所属的用户的编号，外键
	FOREIGN KEY (uid) REFERENCES `User`(uid)
);

-- drop table Card;
-- select * from Card;
CREATE TABLE have(-- 拥有表
	uid VARCHAR(32) ,
	cid VARCHAR(32) ,
	`time` DATETIME ,
	remark VARCHAR(20) ,-- 备注
	PRIMARY	KEY (uid,cid),-- 合起来作为主键
	FOREIGN KEY (uid) REFERENCES `User`(uid),
	FOREIGN KEY (cid) REFERENCES Card(cid)
);
-- drop table have;
-- select * from have;
/*
table user_base
(
 uid
 uname
 password  明文
)
*/