--玩家盈亏表 black
CREATE SEQUENCE player_profit_and_loss_id_seq;
CREATE TABLE circle.player_profit_and_loss (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('player_profit_and_loss_id_seq'::regclass), -- 主键
  gather_time BIGINT NOT NULL, -- 采集时间
  player_id INTEGER NOT NULL, -- 玩家账号
  bet_frequency INTEGER NOT NULL, -- 投注次数
  effective_amount BIGINT NOT NULL, -- 有效投注额
  profit_amount BIGINT NOT NULL, -- 盈亏额
  water_amount BIGINT NOT NULL DEFAULT 0 -- 抽水额
);
CREATE UNIQUE INDEX player_profit_and_loss_id_uindex ON player_profit_and_loss USING BTREE (id);
COMMENT ON TABLE circle.player_profit_and_loss IS '玩家盈亏表';
COMMENT ON COLUMN circle.player_profit_and_loss.id IS '主键';
COMMENT ON COLUMN circle.player_profit_and_loss.gather_time IS '采集时间';
COMMENT ON COLUMN circle.player_profit_and_loss.player_id IS '玩家账号';
COMMENT ON COLUMN circle.player_profit_and_loss.bet_frequency IS '投注次数';
COMMENT ON COLUMN circle.player_profit_and_loss.effective_amount IS '有效投注额';
COMMENT ON COLUMN circle.player_profit_and_loss.profit_amount IS '盈亏额';
COMMENT ON COLUMN circle.player_profit_and_loss.water_amount IS '抽水额';

-- 执行表中插入初始化执行id
INSERT INTO profit_execute_id(id, execute_id, explanation) VALUES (3, 0, '玩家盈亏执行id');