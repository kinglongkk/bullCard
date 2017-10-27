-- auto gen by lance 20170214 

-- 新增 player_warning_control、player_ai_control、player_ai_ratio_control 三张表
-- C_ABOUT_PLAYER_AND_AI

CREATE TABLE IF NOT EXISTS circle.player_warning_control (
  id SERIAL4, -- 主键
  status CHARACTER VARYING(2), -- 状态（10：正常生效 20：已失效）
  win_rate1 NUMERIC(20,2) NOT NULL, -- 胜率黄色预警
  win_rate2 NUMERIC(20,2) NOT NULL, -- 胜率红色预警
  win_gold_rate1 NUMERIC(20,2) NOT NULL, -- 赢得金币与本金的倍数比黄色预警
  win_gold_rate2 NUMERIC(20,2) NOT NULL, -- 赢得金币与本金的倍数比红色预警
  enchashment_sum1 NUMERIC(20,2) NOT NULL, -- 取现总额黄色预警
  enchashment_sum2 NUMERIC(20,2) NOT NULL, -- 取现总额红色预警
  payout_times1 NUMERIC(20,2) NOT NULL, -- 联系派彩次数黄色预警
  payout_times2 NUMERIC(20,2) NOT NULL, -- 联系派彩次数红色色预警
  enchashment_times1 NUMERIC(20,2) NOT NULL, -- 连续取现黄色预警
  enchashment_times2 NUMERIC(20,2) NOT NULL, -- 连续取现红色预警
  create_user CHARACTER VARYING(20), -- 记录追加人
  create_time TIMESTAMP(6), -- 记录追加时间
  update_user CHARACTER VARYING(20), -- 更新者
  update_time TIMESTAMP(6) -- 更新时间
);
COMMENT ON TABLE circle.player_warning_control IS '玩家实时监控控制表';
COMMENT ON COLUMN circle.player_warning_control.id IS '主键';
COMMENT ON COLUMN circle.player_warning_control.status IS '状态（10：正常生效 20：已失效）';
COMMENT ON COLUMN circle.player_warning_control.win_rate1 IS '胜率黄色预警';
COMMENT ON COLUMN circle.player_warning_control.win_rate2 IS '胜率红色预警';
COMMENT ON COLUMN circle.player_warning_control.win_gold_rate1 IS '赢得金币与本金的倍数比黄色预警';
COMMENT ON COLUMN circle.player_warning_control.win_gold_rate2 IS '赢得金币与本金的倍数比红色预警';
COMMENT ON COLUMN circle.player_warning_control.enchashment_sum1 IS '取现总额黄色预警';
COMMENT ON COLUMN circle.player_warning_control.enchashment_sum2 IS '取现总额红色预警';
COMMENT ON COLUMN circle.player_warning_control.payout_times1 IS '联系派彩次数黄色预警';
COMMENT ON COLUMN circle.player_warning_control.payout_times2 IS '联系派彩次数红色色预警';
COMMENT ON COLUMN circle.player_warning_control.enchashment_times1 IS '连续取现黄色预警';
COMMENT ON COLUMN circle.player_warning_control.enchashment_times2 IS '连续取现红色预警';
COMMENT ON COLUMN circle.player_warning_control.create_user IS '记录追加人';
COMMENT ON COLUMN circle.player_warning_control.create_time IS '记录追加时间';
COMMENT ON COLUMN circle.player_warning_control.update_user IS '更新者';
COMMENT ON COLUMN circle.player_warning_control.update_time IS '更新时间';



CREATE TABLE IF NOT EXISTS circle.player_ai_control (
  id SERIAL4, -- 主键
  room_id INTEGER NOT NULL, -- 房间ID
  room_name CHARACTER VARYING(20), -- 房间名
  batch_id CHARACTER VARYING(20), -- 批次标志
  status CHARACTER VARYING(2) NOT NULL, -- 状态（10：生效 20：已失效 30：未生效）
  ai_qty INTEGER NOT NULL, -- AI数目
  control_mode CHARACTER VARYING(2) NOT NULL, -- 调控方式（1：进入；2：退出）
  in_time TIMESTAMP(6) WITHOUT TIME ZONE, -- 进入时间
  out_time TIMESTAMP(6) WITHOUT TIME ZONE, -- 退出时间
  bring_gold_min INTEGER NOT NULL, -- 最少携带金币数
  bring_gold_max INTEGER NOT NULL, -- 最多携带金币数
  interval_min_time INTEGER NOT NULL, -- 进入最小时间间隔
  interval_max_time INTEGER NOT NULL, -- 进入最大时间间隔
  level_min_time INTEGER NOT NULL, -- 离开最小时间间隔
  level_max_time INTEGER NOT NULL, -- 离开最大时间间隔
  rest_min_games INTEGER NOT NULL, -- 最少游戏休息局数
  rest_max_games INTEGER NOT NULL, -- 最大游戏休息局数
  room_max_qty INTEGER NOT NULL, -- 游戏人数最高值
  create_user CHARACTER VARYING(20), -- 创建人
  create_time TIMESTAMP(6), -- 创建时间
  update_user CHARACTER VARYING(20), -- 更新者
  update_time TIMESTAMP(6) -- 更新时间
);
COMMENT ON TABLE circle.player_ai_control IS 'Ai玩家设置';
COMMENT ON COLUMN circle.player_ai_control.id IS '主键';
COMMENT ON COLUMN circle.player_ai_control.room_id IS '房间ID';
COMMENT ON COLUMN circle.player_ai_control.room_name IS '房间名';
COMMENT ON COLUMN circle.player_ai_control.batch_id IS '批次标志';
COMMENT ON COLUMN circle.player_ai_control.status IS '状态（10：生效 20：已失效 30：未生效）';
COMMENT ON COLUMN circle.player_ai_control.ai_qty IS 'AI数目';
COMMENT ON COLUMN circle.player_ai_control.control_mode IS '调控方式（1：进入；2：退出）';
COMMENT ON COLUMN circle.player_ai_control.bring_gold_min IS '最少携带金币数';
COMMENT ON COLUMN circle.player_ai_control.bring_gold_max IS '最多携带金币数';
COMMENT ON COLUMN circle.player_ai_control.interval_min_time IS '进入最小时间间隔';
COMMENT ON COLUMN circle.player_ai_control.interval_max_time IS '进入最大时间间隔';
COMMENT ON COLUMN circle.player_ai_control.rest_min_games IS '最少游戏休息局数';
COMMENT ON COLUMN circle.player_ai_control.rest_max_games IS '最大游戏休息局数';
COMMENT ON COLUMN circle.player_ai_control.room_max_qty IS '游戏人数最高值';
COMMENT ON COLUMN circle.player_ai_control.create_user IS '创建人';
COMMENT ON COLUMN circle.player_ai_control.create_time IS '创建时间';
COMMENT ON COLUMN circle.player_ai_control.update_user IS '更新者';
COMMENT ON COLUMN circle.player_ai_control.update_time IS '更新时间';


CREATE TABLE IF NOT EXISTS circle.player_ai_ratio_control (
  id SERIAL4, -- 主键
  ai_player_control_id INTEGER NOT NULL, -- Ai设置ID
  player_proportion_min INTEGER NOT NULL, -- 玩家最低占比
  player_proportion_max INTEGER NOT NULL, -- 玩家最高占比
  ai_proportion_min INTEGER NOT NULL, -- AI最低占比
  ai_proportion_max INTEGER NOT NULL -- AI最高占比
);
COMMENT ON TABLE circle.player_ai_ratio_control IS 'Ai与玩家配比设置';
COMMENT ON COLUMN circle.player_ai_ratio_control.id IS '主键';
COMMENT ON COLUMN circle.player_ai_ratio_control.ai_player_control_id IS 'Ai设置ID';
COMMENT ON COLUMN circle.player_ai_ratio_control.player_proportion_min IS '玩家最低占比';
COMMENT ON COLUMN circle.player_ai_ratio_control.player_proportion_max IS '玩家最高占比';
COMMENT ON COLUMN circle.player_ai_ratio_control.ai_proportion_min IS 'AI最低占比';
COMMENT ON COLUMN circle.player_ai_ratio_control.ai_proportion_max IS 'AI最高占比';
