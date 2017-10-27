-- auto gen by MK 20170311
-- 增加字段房间最大限入金额
SELECT redo_sqls($$
   ALTER TABLE circle.game_room ADD max_limit_player_blance BIGINT DEFAULT 1400000000 NOT NULL;
$$);
COMMENT ON COLUMN circle.game_room.max_limit_player_blance IS '房间最大限入金额';

CREATE OR REPLACE VIEW circle.v_room AS  SELECT r.id,
   r.game_id,
   r.game_model_id,
   r.name,
   r.status,
   r.description,
   r.max_limit_player_number,
   r.min_limit_player_blance,
   r.per_desk_seat_count,
   r.jackpot,
   r.jackpot_overflow,
   g.code AS game_code,
   g.name AS game_name,
   g.icon AS game_icon,
   g.status AS game_status,
   m.code AS game_model_code,
   m.name AS game_model_name,
   m.status AS game_model_status,
   m.icon AS game_model_icon,
   r.max_limit_player_blance
  FROM game g,
   game_model m,
   game_room r
  WHERE ((r.game_id = g.id) AND (r.game_model_id = m.id));