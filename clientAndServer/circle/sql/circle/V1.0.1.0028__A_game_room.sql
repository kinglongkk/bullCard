-- auto gen by double 20170328
-- game_room COLUMN jackpot_overflow 改为bigInt
DROP view circle.v_room;

ALTER TABLE circle.game_room ALTER COLUMN jackpot_overflow TYPE BIGINT USING jackpot_overflow::BIGINT;

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