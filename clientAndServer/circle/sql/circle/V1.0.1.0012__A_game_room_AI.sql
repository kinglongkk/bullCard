-- auto gen by MK 20170228
--新增2个字段：
-- 1.游戏变量奖池金额（区别于默认奖池金额的一个同类型字段）
-- 2.溢出奖池金额（溢出最高积累金额的积累金币值）
SELECT redo_sqls($$
  ALTER TABLE circle.game_room ADD jackpot BIGINT DEFAULT 1000000 NOT NULL;
  ALTER TABLE circle.game_room ADD jackpot_overflow INT DEFAULT 0 NOT NULL;
  ALTER TABLE circle.player_ai_control ADD bet_count INT DEFAULT 5 NOT NULL;
  ALTER TABLE circle.player_ai_control ADD chip_rates VARCHAR(100) DEFAULT '5,4,3,2,1' NOT NULL;
  ALTER TABLE circle.player_ai_control ADD game_model INT DEFAULT 1 NOT NULL;
  ALTER TABLE circle.player_ai_control ALTER COLUMN control_cycle TYPE INT USING control_cycle::INT;
  ALTER TABLE circle.player_ai_control RENAME COLUMN level_min_time TO leave_min_time;
  ALTER TABLE circle.player_ai_control RENAME COLUMN level_max_time TO leave_max_time;
$$);

COMMENT ON COLUMN circle.player_ai_control.bet_count IS '平均每场比赛投注次数';
COMMENT ON COLUMN circle.player_ai_control.chip_rates IS '投注筹码概率范围,逗号隔开';
COMMENT ON COLUMN circle.player_ai_control.game_model IS '游戏玩法';
COMMENT ON COLUMN circle.player_ai_control.control_cycle IS '控制周期 分钟数';
