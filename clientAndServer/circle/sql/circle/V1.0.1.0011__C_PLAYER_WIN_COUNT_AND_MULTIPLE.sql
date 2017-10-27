-- auto gen by lance 20170223 


-- player_warning_win_count:用于存储过程预警设置今日派彩次数
-- player_warning_multiple:用于存储过程预警设置今日赢得金币与投注金额倍数
-- C_PLAYER_WIN_COUNT_AND_MULTIPLE

CREATE TABLE if NOT EXISTS circle.player_warning_win_count (
  id SERIAL4, -- 主键
  player_id INT4, -- 玩家ID
  date DATE, -- 日期
  username CHARACTER VARYING(30), -- 用户名
  nickname CHARACTER VARYING(30), -- 昵称
  register_date TIMESTAMP, -- 注册日期
  win_amount NUMERIC(20,2), -- 赢得金额
  bet_amount NUMERIC(20,2), -- 投注金额
  start_time TIMESTAMP, -- 开始时间
  end_time TIMESTAMP, -- 结束时间
  num INT4, -- 连赢次数
  match_id POINT, -- 赛事ID；
  status CHARACTER VARYING(3), -- 1:黄色2:红色；
  is_delete BOOL, -- 是否删除；
  create_time TIMESTAMP -- 创建时间；
);

COMMENT ON TABLE circle.player_warning_win_count IS '说明预警异常用户赢得连续派彩次数';
COMMENT ON COLUMN circle.player_warning_win_count.id IS '主键';
COMMENT ON COLUMN circle.player_warning_win_count.player_id IS '玩家ID';
COMMENT ON COLUMN circle.player_warning_win_count.date IS '日期';
COMMENT ON COLUMN circle.player_warning_win_count.username IS '用户名';
COMMENT ON COLUMN circle.player_warning_win_count.nickname IS '昵称';
COMMENT ON COLUMN circle.player_warning_win_count.register_date IS '注册日期';
COMMENT ON COLUMN circle.player_warning_win_count.win_amount IS '赢得金额';
COMMENT ON COLUMN circle.player_warning_win_count.bet_amount IS '投注金额';
COMMENT ON COLUMN circle.player_warning_win_count.start_time IS '开始时间';
COMMENT ON COLUMN circle.player_warning_win_count.end_time IS '结束时间';
COMMENT ON COLUMN circle.player_warning_win_count.num IS '连赢次数';
COMMENT ON COLUMN circle.player_warning_win_count.match_id IS '赛事ID；';
COMMENT ON COLUMN circle.player_warning_win_count.status IS '1:黄色2:红色；';
COMMENT ON COLUMN circle.player_warning_win_count.is_delete IS '是否删除；';
COMMENT ON COLUMN circle.player_warning_win_count.create_time IS '创建时间；';


CREATE TABLE if NOT EXISTS circle.player_warning_multiple (
  id SERIAL4, -- 主键
  player_id INT4, -- 玩家ID
  date DATE, -- 日期
  username CHARACTER VARYING(30), -- 用户名
  nickname CHARACTER VARYING(30), -- 昵称
  register_date TIMESTAMP, -- 注册日期
  win_amount NUMERIC(20,2), -- 今日赢得金额
  bet_amount NUMERIC(20,2), -- 今日投注金额
  status CHARACTER VARYING(3), -- 1:黄色2:红色；
  multiple NUMERIC(20,2), -- 赢得金额和投注金额倍数
  create_time TIMESTAMP -- 创建时间；
);

COMMENT ON TABLE circle.player_warning_multiple IS '说明预警异常用户赢得金额跟投注金额倍数';
COMMENT ON COLUMN circle.player_warning_multiple.id IS '主键';
COMMENT ON COLUMN circle.player_warning_multiple.player_id IS '角色名称';
COMMENT ON COLUMN circle.player_warning_multiple.date IS '状态';
COMMENT ON COLUMN circle.player_warning_multiple.username IS '创建用户id';
COMMENT ON COLUMN circle.player_warning_multiple.nickname IS '创建时间';
COMMENT ON COLUMN circle.player_warning_multiple.register_date IS '创建用户id';
COMMENT ON COLUMN circle.player_warning_multiple.win_amount IS '今日赢得金额';
COMMENT ON COLUMN circle.player_warning_multiple.bet_amount IS '今日投注金额';
COMMENT ON COLUMN circle.player_warning_multiple.status IS '1:黄色2:红色；';
COMMENT ON COLUMN circle.player_warning_multiple.multiple IS '赢得金额和投注金额倍数';
COMMENT ON COLUMN circle.player_warning_multiple.create_time IS '创建时间；';