-- auto gen by solo 20170320
-- start--添加字段，服务端被动生成公告的条件，为最高可输金额的百分比
SELECT redo_sqls($$
  ALTER TABLE circle.game_room ADD send_msg_condition DOUBLE PRECISION DEFAULT 1.0 NOT NULL;
$$);
COMMENT ON COLUMN game_room.send_msg_condition IS '产生消息满足大于最高可输金额的比例条件';
-- end-----------------------------------------------------------------------------------------------------

-- start--数据字典添加新数据
INSERT INTO "circle".sys_dict(module,dict_type,dict_code,order_num,remark,parent_code,active) VALUES ('game','sendMsg','1',1,'','',TRUE );
-- end-----------------------------------------------------------------------------------------------------

-- start--添加字段，服务端被动生成公告的条件，为最高可输金额的百分比
  SELECT redo_sqls($$
    ALTER TABLE circle.game_announcement ADD msg_type  VARCHAR(20);
    ALTER TABLE circle.game_announcement ADD gain_gold_num BIGINT;
    ALTER  TABLE circle.game_announcement ADD is_send INT;
  $$);
   COMMENT ON COLUMN game_announcement.msg_type IS '产生消息的类型,WINGAME:赢得比赛产生的公告';
   COMMENT ON COLUMN game_announcement.gain_gold_num IS '获得金币数量';
   COMMENT ON COLUMN game_announcement.is_send IS '是否已经发送过 0:未发送，1：已发送';
-- end-----------------------------------------------------------------------------------------------------


