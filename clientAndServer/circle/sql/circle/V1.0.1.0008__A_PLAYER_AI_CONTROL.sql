-- auto gen by lance 20170216 
-- player_ai_control 表添加一个字段，删除两个字段
-- A_PLAYER_AI_CONTROL
  select redo_sqls($$
    ALTER TABLE player_ai_control ADD COLUMN begin_control_time TIMESTAMP(6) WITHOUT TIME ZONE;
    ALTER TABLE player_ai_control DROP COLUMN IF EXISTS in_time;
    ALTER TABLE player_ai_control DROP COLUMN IF EXISTS out_time;

    COMMENT ON COLUMN player_ai_control.begin_control_time IS '开始操作时间';

  $$);