/*
注：permission字段不能为空，如果原表无数据，默认admin:index
无法创建关系：ERROR: relation "fk_sys_resource_parent_id" already exists
*/

/**DROP TABLE circle.sys_resource_test;*/

CREATE TABLE circle.sys_resource_test (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('sys_resource_id_seq'::regclass), -- 主键
  name CHARACTER VARYING(64) NOT NULL, -- 资源名称
  url CHARACTER VARYING(128), -- 资源url
  remark CHARACTER VARYING(255), -- 资源简要描述
  parent_id INTEGER, -- 父项id
  structure CHARACTER VARYING(16), -- 资源的层级结构
  sort_num INTEGER, -- 排序号
  subsys_code CHARACTER VARYING(32) NOT NULL, -- 所属子系统编号
  permission CHARACTER VARYING(32) NOT NULL, -- 权限
  resource_type INTEGER NOT NULL, -- 资源类型
  icon CHARACTER VARYING(255), -- 图标
  built_in BOOLEAN DEFAULT false,
  privilege BOOLEAN DEFAULT false, -- 是否系统内置
  status BOOLEAN DEFAULT false -- 菜单是否启用
);
CREATE INDEX fk_sys_resource_parent_id ON sys_resource_old USING BTREE (parent_id);
COMMENT ON TABLE circle.sys_resource_old IS '系统资源--Kevice';
COMMENT ON COLUMN circle.sys_resource_old.id IS '主键';
COMMENT ON COLUMN circle.sys_resource_old.name IS '资源名称';
COMMENT ON COLUMN circle.sys_resource_old.url IS '资源url';
COMMENT ON COLUMN circle.sys_resource_old.remark IS '资源简要描述';
COMMENT ON COLUMN circle.sys_resource_old.parent_id IS '父项id';
COMMENT ON COLUMN circle.sys_resource_old.structure IS '资源的层级结构';
COMMENT ON COLUMN circle.sys_resource_old.sort_num IS '排序号';
COMMENT ON COLUMN circle.sys_resource_old.subsys_code IS '所属子系统编号';
COMMENT ON COLUMN circle.sys_resource_old.permission IS '权限';
COMMENT ON COLUMN circle.sys_resource_old.resource_type IS '资源类型';
COMMENT ON COLUMN circle.sys_resource_old.icon IS '图标';
COMMENT ON COLUMN circle.sys_resource_old.privilege IS '是否系统内置';
COMMENT ON COLUMN circle.sys_resource_old.status IS '菜单是否启用';

/** parent start */
/* 游戏 */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (1,'游戏',NULL ,'游戏',NULL ,NULL ,1,'admin' ,'admin:match_control' ,1 ,'fa-server' ,TRUE ,FALSE ,TRUE );


/* 资金 */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (2,'资金',NULL ,'资金',NULL ,NULL ,2,'admin' ,'admin:index' ,1 ,'fa-server' ,TRUE ,FALSE ,TRUE );


/* 报表 */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (3,'报表',NULL ,'报表',NULL ,NULL ,3,'admin' ,'admin:report' ,1 ,'fa-pie-chart' ,TRUE ,FALSE ,TRUE );
/** parent end */


/**游戏 child 1 start */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (11,'游戏进行时',NULL ,'游戏进行时',1 ,NULL ,1,'admin' ,'admin:index' ,1 ,'fa-server' ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (12,'游戏管理',NULL ,'游戏管理',1 ,NULL ,2,'admin' ,'role:player' ,1 ,'fa-server' ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (13,'游戏活动设置',NULL ,'游戏活动设置',1 ,NULL ,3,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (14,'游戏公告',NULL ,'游戏公告',1 ,NULL ,4 ,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );
/**游戏 child 1 end */


/**游戏 child 2 start */
/**11-游戏进行时**/
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (111,'房间概况',NULL ,'房间概况',11 ,NULL ,1,'admin' ,'admin:gameRoom' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (112,'游戏参数',NULL ,'游戏参数',11 ,NULL ,2,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (113,'即时注单',NULL ,'即时注单',11 ,NULL ,3,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (114,'机器人',NULL ,'机器人',11 ,NULL ,4,'admin' ,'admin:index' ,1 ,'admin:index' ,TRUE ,FALSE ,TRUE );

/**12-游戏管理**/
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (121,'游戏列表',NULL ,'游戏列表',12 ,NULL ,1,'admin' ,'admin:game' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (122,'游戏玩法',NULL ,'游戏玩法',12 ,NULL ,2,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (123,'游戏房间',NULL ,'游戏房间',12 ,NULL ,3,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

/**13-活动设置**/
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (131,'活动管理',NULL ,'活动管理',13 ,NULL ,1,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (132,'当前活动',NULL ,'当前活动',13 ,NULL ,2,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (133,'参与统计',NULL ,'参与统计',13 ,NULL ,3,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

/**14-活动公告**/
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (141,'公告管理',NULL ,'公告管理',14 ,NULL ,1,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );
/**游戏 child 2 end */


/**资金 child 1 start */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (21,'存款账户',NULL ,'存款账户',1 ,NULL ,1,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );
/**资金 child 1 end */

/**资金 child 2 start */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (211,'公司入款',NULL ,'公司入款',21 ,NULL ,1,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );

INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (212,'在线支付',NULL ,'在线支付',21 ,NULL ,2,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );
/**资金 child 2 end */


/**报表 child 1 start */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (31,'报表查询',NULL ,'报表查询',3 ,NULL ,1,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );
/**报表 child 1 end */

/**报表 child 2 start */
INSERT INTO sys_resource (id, name, url, remark, parent_id, structure, sort_num, subsys_code, permission, resource_type, icon, built_in, privilege, status)
VALUES (311,'站长报表',NULL ,'站长报表',31 ,NULL ,1,'admin' ,'admin:index' ,1 ,NULL ,TRUE ,FALSE ,TRUE );
/**报表 child 2 end */