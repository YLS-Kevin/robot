/*
Navicat MySQL Data Transfer

Source Server         : 机器人测试
Source Server Version : 50718
Source Host           : gz-cdb-ewafz4tc.sql.tencentcdb.com:63144
Source Database       : robot2Test_db6

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-08-09 09:49:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `account_name` varchar(128) DEFAULT NULL COMMENT '账户昵称',
  `super_user_id` varchar(32) NOT NULL COMMENT '超级用户id',
  `vip_id` varchar(32) NOT NULL COMMENT '会员等级id',
  `vip_expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '会员过期时间，免费会员：永不过期',
  `atype` char(1) NOT NULL DEFAULT '1' COMMENT '类型。1-个人，2-企业，3-单位',
  `isauth` char(1) NOT NULL DEFAULT '2' COMMENT '是否认证。1-是，2-否',
  `authtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '认证时间',
  `state` char(1) NOT NULL DEFAULT '1' COMMENT '状态。1-正常。2-禁用。',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='一个公司、单位、个人一个账户\r\n有一个通用账户，它的对话库是可以给其它账户使用的。账户号为：1';

-- ----------------------------
-- Table structure for CLIENT_WORDDEF
-- ----------------------------
DROP TABLE IF EXISTS `CLIENT_WORDDEF`;
CREATE TABLE `CLIENT_WORDDEF` (
  `adid` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_c` varchar(32) NOT NULL COMMENT '终端id。UUID',
  `id_a` varchar(32) DEFAULT NULL COMMENT '通用问题id。UUID。通用问题id 与 终端问题id有一个不为空',
  `id_ca` varchar(32) DEFAULT NULL COMMENT '终端问题id。UUID。通用问题id 与 终端问题id有一个不为空',
  `dword1def` varchar(32) DEFAULT NULL COMMENT '动态词1默认值',
  `dword2def` varchar(32) DEFAULT NULL COMMENT '动态词2默认值',
  `dword3def` varchar(32) DEFAULT NULL COMMENT '动态词3默认值',
  `dword4def` varchar(32) DEFAULT NULL COMMENT '动态词4默认值',
  `dword5def` varchar(32) DEFAULT NULL COMMENT '动态词5默认值',
  PRIMARY KEY (`adid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='终端实例与问题默认动态词对照表';

-- ----------------------------
-- Table structure for DIALOG
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG`;
CREATE TABLE `DIALOG` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `id_dt` varchar(32) NOT NULL COMMENT '类别id。UUID',
  `cid_m_id_dt` varchar(32) DEFAULT NULL COMMENT '该值应该是通用情景模块的所对应的对话库id.  如果该值不为空,说明是机器人模块下自定义对话',
  `mul_dialog_type` decimal(2,0) DEFAULT NULL COMMENT '4-多轮对话主题入口，5-多轮对话中',
  `atype` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '对话类别。1-未知（暂不用），2-固定应答，3-接口应答。',
  `id_ap` varchar(32) DEFAULT NULL COMMENT '主题id。多轮对话时才有。单轮对话时，该字段为空。',
  `team_id` varchar(32) NOT NULL COMMENT '组别id',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_contain_kw` decimal(2,0) DEFAULT '1' COMMENT '是否包含关键词. 0:不包含,1:包含, 默认1',
  `del_flag` char(1) NOT NULL COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`id`),
  KEY `Idx_DIALOG_1` (`id_ac`),
  KEY `Idx_DIALOG_2` (`id_dt`),
  KEY `Idx_DIALOG_3` (`id_ap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for DIALOG_ANSWER_EXP
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_ANSWER_EXP`;
CREATE TABLE `DIALOG_ANSWER_EXP` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `skid` decimal(2,0) NOT NULL DEFAULT '3' COMMENT '种类。1-全局默认，2-账户默认，3-终端',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `cid` varchar(32) NOT NULL COMMENT '机器人id。UUID',
  `stype` decimal(2,0) NOT NULL COMMENT '类型。1-无答案时，2-接口异常时，3-系统出错时',
  `answer` varchar(1024) NOT NULL COMMENT '机器应答内容',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小爱还要好好学习\r\n我还小不懂啦\r\n。。。\r\n\r\n\r\n种类。1-全局默';

-- ----------------------------
-- Table structure for DIALOG_ANSWER_SCRIPT
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_ANSWER_SCRIPT`;
CREATE TABLE `DIALOG_ANSWER_SCRIPT` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `id_d` varchar(32) NOT NULL COMMENT '人机对话id',
  `atype` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '对话类别。1-多轮对话，2-固定应答，3-接口应答。',
  `stype` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '脚本类型。 1-无，2-接口中返回，3-自定义返回。 当为接口应答时，该字段才有意义',
  `repara` varchar(20) DEFAULT NULL COMMENT '返回参数。接口中返回的其中一个参数名。当为接口应答时，该字段才有意义',
  `sin` decimal(2,0) DEFAULT NULL COMMENT '包含或等于。 1-包含，2-不包含，3-等于，4-不等于。当为接口应答时，该字段才有意义',
  `sinword` varchar(1024) DEFAULT NULL COMMENT '包含关键词。多个用 | 分开。当为接口应答时，该字段才有意义',
  `scripts` varchar(2048) DEFAULT NULL COMMENT '返回脚本',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='\r\n当 对话类别 为 接口应答 时，一个对话可能有多条记录';

-- ----------------------------
-- Table structure for dialog_cache_event
-- ----------------------------
DROP TABLE IF EXISTS `dialog_cache_event`;
CREATE TABLE `dialog_cache_event` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `action` varchar(32) NOT NULL COMMENT '增加,修改,删除  add, update  delete',
  `secretKey` varchar(32) DEFAULT NULL COMMENT '秘钥',
  `cid` varchar(32) DEFAULT NULL COMMENT '机器人id',
  `update_before_data` text COMMENT '所操作的数据,更新前Json格式',
  `update_data` text COMMENT '所要操作的数据JSON格式',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2509 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for DIALOG_MAN
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_MAN`;
CREATE TABLE `DIALOG_MAN` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `id_d` varchar(32) NOT NULL COMMENT '人机对话id',
  `aptype` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '匹配类型。1-模糊匹配，2-关键词匹配',
  `aword` varchar(32) DEFAULT NULL COMMENT '人说的话',
  `awordnum` decimal(2,0) DEFAULT '1' COMMENT '关键词个数。最多5个。指一定得有才能形成这个问题的关键词',
  `aword1type` decimal(2,0) DEFAULT '1' COMMENT '关键词1类型。1-固定，2-变化。',
  `aword1` varchar(32) DEFAULT NULL COMMENT '关键词1。如果为变化型，写入时用[地名]。不用默认了，因为默认可定义近义问题解决',
  `aword1near` varchar(512) DEFAULT NULL COMMENT '关键词1同义词。多个用 | 分开',
  `aword1dyna` varchar(32) DEFAULT NULL COMMENT '关键词1动态类型',
  `aword1para` varchar(20) DEFAULT NULL COMMENT '关键词1url接口参数。当对话的类型为：接口时需用到',
  `aword2type` decimal(2,0) DEFAULT '1' COMMENT '关键词2类型。1-固定，2-变化。',
  `aword2` varchar(32) DEFAULT NULL COMMENT '关键词2',
  `aword2near` varchar(512) DEFAULT NULL COMMENT '关键词2同义词。多个用 | 分开',
  `aword2dyna` varchar(32) DEFAULT NULL COMMENT '关键词2动态类型',
  `aword2para` varchar(20) DEFAULT NULL COMMENT '关键词2url接口参数。当对话的类型为：接口时需用到',
  `aword3type` decimal(2,0) DEFAULT '1' COMMENT '关键词3类型。1-固定，2-变化。',
  `aword3` varchar(32) DEFAULT NULL COMMENT '关键词3',
  `aword3near` varchar(512) DEFAULT NULL COMMENT '关键词3同义词。多个用 | 分开',
  `aword3dyna` varchar(32) DEFAULT NULL COMMENT '关键词3动态类型',
  `aword3para` varchar(20) DEFAULT NULL COMMENT '关键词3url接口参数。当对话的类型为：接口时需用到',
  `aword4type` decimal(2,0) DEFAULT '1' COMMENT '关键词4类型。1-固定，2-变化。',
  `aword4` varchar(32) DEFAULT NULL COMMENT '关键词4',
  `aword4near` varchar(512) DEFAULT NULL COMMENT '关键词4同义词。多个用 | 分开',
  `aword4dyna` varchar(32) DEFAULT NULL COMMENT '关键词4动态类型',
  `aword4para` varchar(20) DEFAULT NULL COMMENT '关键词4url接口参数。当对话的类型为：接口时需用到',
  `aword5type` decimal(2,0) DEFAULT '1' COMMENT '关键词5类型。1-固定，2-变化。',
  `aword5` varchar(32) DEFAULT NULL COMMENT '关键词5',
  `aword5near` varchar(512) DEFAULT NULL COMMENT '关键词5同义词。多个用 | 分开',
  `aword5dyna` varchar(32) DEFAULT NULL COMMENT '关键词5动态类型',
  `aword5para` varchar(20) DEFAULT NULL COMMENT '关键词5url接口参数。当对话的类型为：接口时需用到',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for DIALOG_MORE
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_MORE`;
CREATE TABLE `DIALOG_MORE` (
  `id` varchar(32) NOT NULL COMMENT '主题id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id',
  `id_dt` varchar(32) NOT NULL COMMENT '对话库id',
  `tname` varchar(255) NOT NULL COMMENT '主题名。如：问天气',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for DIALOG_MORE1
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_MORE1`;
CREATE TABLE `DIALOG_MORE1` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_d` varchar(32) NOT NULL COMMENT '人机对话id。UUID',
  `id_dt` varchar(32) NOT NULL COMMENT '对话库id',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `iword1type` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '选项词类型。1-固定，2-变化。',
  `iword1` varchar(32) NOT NULL COMMENT '选项词。如果为变化型，写入时用[地名]。不用默认了，因为默认可定义近义问题解决',
  `iword1near` varchar(512) DEFAULT NULL COMMENT '选项词同义词。多个用 | 分开',
  `iword1dyna` varchar(32) DEFAULT NULL COMMENT '选项词1动态类型',
  `nid_d` varchar(32) NOT NULL COMMENT '下轮对话id。该选项词对应的下轮对话的id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='当账户人机对话表中对话类别为 1-多轮对话 时，该表存放多轮对话的选项与对应的第二轮对话id';

-- ----------------------------
-- Table structure for dialog_order
-- ----------------------------
DROP TABLE IF EXISTS `dialog_order`;
CREATE TABLE `dialog_order` (
  `order_id` varchar(32) NOT NULL COMMENT '账单id,主键',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `order_num` varchar(32) NOT NULL COMMENT '订单编号',
  `order_time` datetime NOT NULL COMMENT '生成订单时间',
  `order_type` char(1) NOT NULL COMMENT '订单状态. 1:下单成功,2:订单已支付,3:订单已送达',
  `pay_type` char(1) DEFAULT NULL COMMENT '支付方式.1:微信支付,2:支付宝支付,3:银联支付',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `vip_id` varchar(32) NOT NULL COMMENT '所购买的商品',
  `order_amount` decimal(4,2) NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `actural_pay_amount` decimal(4,2) NOT NULL DEFAULT '0.00' COMMENT '实付金额',
  `is_invoice` char(1) NOT NULL DEFAULT '0' COMMENT '是否开发票. 0不开发票,1:开发票',
  `invoice_type` char(1) NOT NULL DEFAULT '0' COMMENT '发票类型. 0:增值税普通发票.1:增值税专用发票',
  `invoice_tityle` varchar(64) DEFAULT NULL COMMENT '发票抬头',
  `invoice_receive_type` char(1) NOT NULL DEFAULT '0' COMMENT '发票接收方式. 0:邮寄方式.1:上门自取',
  `invoice_receive_person` varchar(32) DEFAULT NULL COMMENT '发票接收人',
  `invoice_receive_phone` varchar(32) DEFAULT NULL COMMENT '发票接收人联系电话',
  `invoice_receive_area` varchar(128) DEFAULT NULL COMMENT '发票接收人地址',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dialog_resource
-- ----------------------------
DROP TABLE IF EXISTS `dialog_resource`;
CREATE TABLE `dialog_resource` (
  `resource_id` varchar(32) NOT NULL COMMENT '资源id主键',
  `resource_name` varchar(32) NOT NULL COMMENT '资源名称',
  `parent_id` varchar(32) NOT NULL COMMENT '父id',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(1024) DEFAULT NULL COMMENT '跳转href',
  `icon` varchar(256) DEFAULT NULL COMMENT '图标',
  `is_show` char(1) NOT NULL COMMENT '是否展示',
  `permission` varchar(1024) DEFAULT NULL COMMENT '权限标识',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for DIALOG_ROBOT_INTER
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_ROBOT_INTER`;
CREATE TABLE `DIALOG_ROBOT_INTER` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `id_d` varchar(32) NOT NULL COMMENT '人机对话id',
  `id_di` varchar(32) NOT NULL COMMENT '数据接口id',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='一个问题对应一个接口。';

-- ----------------------------
-- Table structure for DIALOG_ROBOT_MODEL
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_ROBOT_MODEL`;
CREATE TABLE `DIALOG_ROBOT_MODEL` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `sextype` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '男声女声。1-通用，2-男声，3-女声',
  `asmname` varchar(50) NOT NULL COMMENT '模式名',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `orderby` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='男，普通模式，行政模式，温柔模式，搞笑模式等\r\n女，普通模式，行政模式，温柔模式，搞笑模式等  ';

-- ----------------------------
-- Table structure for DIALOG_ROBOT_STATIC
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_ROBOT_STATIC`;
CREATE TABLE `DIALOG_ROBOT_STATIC` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `id_d` varchar(32) NOT NULL COMMENT '人机对话id',
  `answer` varchar(1024) NOT NULL COMMENT '机器应答内容',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='一个最终问题可以对应多个固定应答。多个答案随机应答';

-- ----------------------------
-- Table structure for DIALOG_TYPES
-- ----------------------------
DROP TABLE IF EXISTS `DIALOG_TYPES`;
CREATE TABLE `DIALOG_TYPES` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `cid_m` varchar(32) DEFAULT NULL COMMENT '机器人模块id',
  `atname` varchar(100) NOT NULL COMMENT '类别名',
  `be_quoted` int(11) DEFAULT NULL COMMENT '被引用的数量',
  `is_share` char(1) NOT NULL COMMENT '是否共享.0-默认不共享, 1-共享',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对话的库。如：聊天，查询，计算，询问，我要做，帮我做决定，帮我做等';

-- ----------------------------
-- Table structure for dialog_user
-- ----------------------------
DROP TABLE IF EXISTS `dialog_user`;
CREATE TABLE `dialog_user` (
  `user_id` char(32) NOT NULL COMMENT '主键用户id',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `telphone` char(11) DEFAULT NULL COMMENT '手机',
  `is_safety_phone` char(1) NOT NULL DEFAULT '0' COMMENT '是否是安全手机. 0:否, 1:是',
  `pwd` varchar(100) NOT NULL COMMENT '登录密码MD5加密',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `is_safety_email` char(1) NOT NULL DEFAULT '0' COMMENT '是否是安全邮箱. 0:否, 1:是',
  `register_type` char(1) NOT NULL COMMENT '1:qq,2:微信,3新浪微博,4:手机短信',
  `qq4_user_id` varchar(64) DEFAULT NULL COMMENT 'qq唯一标识用户id',
  `wx4_user_id` varchar(64) DEFAULT NULL COMMENT 'wx唯一标识用户id',
  `xlwb4_user_id` varchar(64) DEFAULT NULL COMMENT '新浪微博唯一标识用户id',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dialog_vip
-- ----------------------------
DROP TABLE IF EXISTS `dialog_vip`;
CREATE TABLE `dialog_vip` (
  `vip_id` varchar(32) NOT NULL COMMENT 'vip主键',
  `vip_level_name` varchar(32) NOT NULL COMMENT 'vip名称',
  `vip_valid_day` int(11) NOT NULL COMMENT 'vip有效天数',
  `vip_price` decimal(4,0) NOT NULL COMMENT 'vip价格',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`vip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dialog_vip_auth
-- ----------------------------
DROP TABLE IF EXISTS `dialog_vip_auth`;
CREATE TABLE `dialog_vip_auth` (
  `vip_id` varchar(32) NOT NULL COMMENT '会员id',
  `resource_id` varchar(32) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`vip_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for DWORDGROUP
-- ----------------------------
DROP TABLE IF EXISTS `DWORDGROUP`;
CREATE TABLE `DWORDGROUP` (
  `id` varchar(32) NOT NULL COMMENT '动态词组id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `group_name` varchar(50) NOT NULL COMMENT '动态词组名。英文',
  `group_cn_name` varchar(50) DEFAULT NULL COMMENT '动态词组中文名 ',
  `is_share` char(1) NOT NULL DEFAULT '0' COMMENT '是否共享.0-默认不共享, 1-共享',
  `is_default` char(1) DEFAULT '2' COMMENT '是否是默认添加  1:是, 2否',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for DWORDS
-- ----------------------------
DROP TABLE IF EXISTS `DWORDS`;
CREATE TABLE `DWORDS` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `id_dwg` varchar(32) NOT NULL COMMENT '动态词组id。UUID',
  `dwname` varchar(50) NOT NULL COMMENT '动态词名',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for INTER_DATA
-- ----------------------------
DROP TABLE IF EXISTS `INTER_DATA`;
CREATE TABLE `INTER_DATA` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `url` varchar(1024) NOT NULL COMMENT 'url',
  `urltest` varchar(1024) DEFAULT NULL COMMENT 'urltest。测试url',
  `explains` varchar(255) NOT NULL COMMENT '接口说明',
  `icall` varchar(10) NOT NULL DEFAULT 'post' COMMENT '接口调用方式。get，post',
  `param_name` varchar(512) DEFAULT NULL COMMENT '接口参数名',
  `param_value` varchar(512) DEFAULT NULL COMMENT '接口参数值',
  `state` char(255) NOT NULL DEFAULT '0' COMMENT '是否启用, 1启用,0禁用',
  `del_flag` char(255) NOT NULL DEFAULT '0' COMMENT '删除标记  0表示启用,1表示禁用',
  `paracode` varchar(20) DEFAULT 'UTF-8' COMMENT 'url参数编码。UTF-8(默认)',
  `needlogin` decimal(2,0) DEFAULT '2' COMMENT '接口需要登录。1-需要，2-不需要。',
  `loginkey` varchar(256) DEFAULT NULL COMMENT '登录key',
  `loginsecret` varchar(256) DEFAULT NULL COMMENT '登录密钥',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_front_call` decimal(2,0) DEFAULT '0' COMMENT '是否是前端调用, 1:是, 0:否.   默认:0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户接口管理';

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ROBOT_CLIENT_USE_LOG
-- ----------------------------
DROP TABLE IF EXISTS `ROBOT_CLIENT_USE_LOG`;
CREATE TABLE `ROBOT_CLIENT_USE_LOG` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_cu` varchar(32) NOT NULL COMMENT '机器人终端id',
  `cip` varchar(32) DEFAULT NULL COMMENT '终端IP。',
  `lon` decimal(16,10) DEFAULT NULL COMMENT '经度',
  `lat` decimal(16,10) DEFAULT NULL COMMENT '纬度',
  `scity` varchar(32) DEFAULT NULL COMMENT '城市',
  `saddr` varchar(256) DEFAULT NULL COMMENT '地址',
  `vdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
  `mansay` varchar(2048) DEFAULT NULL COMMENT '人说',
  `robotsay` varchar(10000) DEFAULT NULL COMMENT '机器说',
  `participle` varchar(512) DEFAULT NULL COMMENT '分词结果',
  `isfind` char(1) DEFAULT NULL COMMENT '调用接口结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for robot_vocation
-- ----------------------------
DROP TABLE IF EXISTS `robot_vocation`;
CREATE TABLE `robot_vocation` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行业表';

-- ----------------------------
-- Table structure for robots
-- ----------------------------
DROP TABLE IF EXISTS `robots`;
CREATE TABLE `robots` (
  `id` varchar(32) NOT NULL COMMENT '机器人id。UUID',
  `id_ac` varchar(32) DEFAULT NULL COMMENT '账户id。UUID',
  `cname` varchar(100) NOT NULL COMMENT '机器人名',
  `nname` varchar(100) DEFAULT NULL COMMENT '机器人昵称。',
  `intrade` varchar(50) DEFAULT NULL COMMENT '所属行业',
  `iconurl` varchar(256) DEFAULT NULL COMMENT '图标url',
  `access_way` varchar(50) DEFAULT NULL COMMENT '接入方式。如：WEBAPI，ANDROID，IOS',
  `appid` varchar(32) NOT NULL COMMENT 'APPID',
  `secret` varchar(128) DEFAULT NULL COMMENT '秘钥',
  `state` char(1) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(256) DEFAULT NULL COMMENT '功能描述',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL COMMENT '删除标记。0-正常，1-已删除',
  PRIMARY KEY (`id`),
  KEY `Idx_robots_1` (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存放机器人实例。如：江苏气象局机器人';

-- ----------------------------
-- Table structure for ROBOTS_AND_DIALOG_TYPES
-- ----------------------------
DROP TABLE IF EXISTS `ROBOTS_AND_DIALOG_TYPES`;
CREATE TABLE `ROBOTS_AND_DIALOG_TYPES` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `cid` varchar(32) NOT NULL COMMENT '机器人id。UUID',
  `cid_m` varchar(32) DEFAULT NULL COMMENT '机器人模块id。UUID。 通用模块时，该字段为空',
  `ctype` decimal(2,0) NOT NULL DEFAULT '2' COMMENT '类型。1-通用模块，2-自定义模块',
  `id_dt` varchar(1600) NOT NULL COMMENT '人机对话库id。UUID',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='某个机器人实例是应用哪几类的问题呢？ 如：江苏气象局机器人     用这两种类型的问题     应用通用类+天气类';

-- ----------------------------
-- Table structure for robots_module
-- ----------------------------
DROP TABLE IF EXISTS `robots_module`;
CREATE TABLE `robots_module` (
  `cid_m` varchar(32) NOT NULL COMMENT '机器人模块id。UUID',
  `cid` varchar(32) NOT NULL COMMENT '机器人id。UUID',
  `iscommon` decimal(2,0) NOT NULL DEFAULT '2' COMMENT '通用模块。1-是。2-否。',
  `mname` varchar(128) NOT NULL COMMENT '模块名',
  `dokey` varchar(256) NOT NULL COMMENT '触发关键词。说该关键词时，进入或退出该模块。该模块关键词，多个用空格分开。如：打开主页，回到主页，我要去主页',
  `state` char(1) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `del_flag` char(1) NOT NULL COMMENT '删除标记。0-正常，1-已删除',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(256) DEFAULT NULL COMMENT '功能描述',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`cid_m`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='一个机器人至少有一个默认（通用）模块';

-- ----------------------------
-- Table structure for ROTOT_CLIENT
-- ----------------------------
DROP TABLE IF EXISTS `ROTOT_CLIENT`;
CREATE TABLE `ROTOT_CLIENT` (
  `id` varchar(32) NOT NULL COMMENT '终端用户id。UUID',
  `cid` varchar(32) NOT NULL COMMENT '机器人id。UUID',
  `cuserid` varchar(64) NOT NULL COMMENT '客户端用户号。客户端自己的唯一号',
  `cname` varchar(128) DEFAULT NULL COMMENT '客户端名。 做为扩展用，以后可以在页面修改',
  `usenum` int(11) NOT NULL DEFAULT '0' COMMENT '使用次数。当天使用次数',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Idx_CLIENT_USER_1` (`cid`,`cuserid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存放机器人终端信息。一般是软件机器人有多个终端。如：深圳天气app每个使用者算一个终端。\r\n每个机器人至少有一';

-- ----------------------------
-- Table structure for sms_verify
-- ----------------------------
DROP TABLE IF EXISTS `sms_verify`;
CREATE TABLE `sms_verify` (
  `telphone` varchar(11) NOT NULL COMMENT '手机号码',
  `code` varchar(4) NOT NULL COMMENT '验证码',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`telphone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信验证';

-- ----------------------------
-- Table structure for WORDS
-- ----------------------------
DROP TABLE IF EXISTS `WORDS`;
CREATE TABLE `WORDS` (
  `id` varchar(32) NOT NULL COMMENT 'id。UUID',
  `id_ac` varchar(32) NOT NULL COMMENT '账户id。UUID',
  `id_dwg` varchar(2048) DEFAULT NULL,
  `wname` varchar(100) NOT NULL COMMENT '词名',
  `synonym` varchar(2048) DEFAULT NULL,
  `wx` varchar(6) NOT NULL COMMENT '词性。现代汉语的词可以分为12类。实词：名词、动词、形容词、数词、量词和代词。虚词：副词、介词、连词、助词、叹词、拟声词。',
  `wx2` varchar(6) DEFAULT NULL COMMENT '词性2。现代汉语的词可以分为12类。实词：名词、动词、形容词、数词、量词和代词。虚词：副词、介词、连词、助词、叹词、拟声词。',
  `wften` int(11) DEFAULT '0' COMMENT '词频',
  `wften2` int(11) DEFAULT '0' COMMENT '词频2',
  `autoin` decimal(2,0) NOT NULL DEFAULT '2' COMMENT '自动插入。1-是，2-否（默认）。',
  `state` decimal(2,0) NOT NULL DEFAULT '1' COMMENT '状态。1-启用。2-禁用。',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
