-- auto gen by double 20170314 
select redo_sqls($$
   alter table game_room_config_bull100 add column max_jackpot_a_match BIGINT NOT NULL DEFAULT 2000000;
$$);
COMMENT ON COLUMN game_room_config_bull100.max_jackpot_a_match IS '单场比赛最高金额,超出部分溢出';

UPDATE game_room_config_bull100 set max_jackpot_a_match =200000 where id = 1;
UPDATE game_room_config_bull100 set max_jackpot_a_match =750000 where id = 2;
UPDATE game_room_config_bull100 set max_jackpot_a_match =2500000 where id = 3;
UPDATE game_room_config_bull100 set max_jackpot_a_match =6250000 where id = 4;
UPDATE game_room_config_bull100 set max_jackpot_a_match =18750000 where id = 5;
UPDATE game_room_config_bull100 set max_jackpot_a_match =31250000 where id = 6;
UPDATE game_room_config_bull100 set max_jackpot_a_match =300000 where id = 7;
UPDATE game_room_config_bull100 set max_jackpot_a_match =420000 where id = 8;
UPDATE game_room_config_bull100 set max_jackpot_a_match =900000 where id = 10;
UPDATE game_room_config_bull100 set max_jackpot_a_match =2160000 where id = 10;
UPDATE game_room_config_bull100 set max_jackpot_a_match =7200000 where id = 11;
UPDATE game_room_config_bull100 set max_jackpot_a_match =21600000 where id = 12;

CREATE OR REPLACE VIEW circle.v_game_room AS
  SELECT gr.id,
    gr.game_id,
    gr.game_model_id,
    gr.name,
    gr.status,
    gr.description,
    gr.max_limit_player_number,
    gr.min_limit_player_blance,
    gr.per_desk_seat_count,
    gr.is_build_in,
    r.jackpot_sum,
    r.max_limit_game_lose,
    r.min_jackpot_limit,
    r.max_jackpot_limit,
    g.name AS gamename,
    gm.name AS modelname,
    r.bet_chip,
    r.dealer_blance,
    r.dealer_blance_tip,
    r.dealer_blance_quit,
    r.bet_times,
    r.max_jackpot_a_match
  FROM (((game_room gr
            LEFT JOIN game g ON ((gr.game_id = g.id)))
          LEFT JOIN game_model gm ON ((gm.id = gr.game_model_id)))
    LEFT JOIN game_room_config_bull100 r ON ((r.room_id = gr.id)));