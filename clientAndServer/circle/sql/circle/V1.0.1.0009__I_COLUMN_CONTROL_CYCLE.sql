-- auto gen by lance 20170220 
-- player_ai_control表添加control_cycle字段
SELECT redo_sqls($$
  ALTER TABLE player_ai_control ADD COLUMN control_cycle NUMERIC(2,1);
$$);
COMMENT ON COLUMN player_ai_control.control_cycle IS '操作周期';