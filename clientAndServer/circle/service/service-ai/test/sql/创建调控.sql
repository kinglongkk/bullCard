

INSERT INTO circle.player_ai_control (id, room_id, room_name, batch_id, status, ai_qty, control_mode, bring_gold_min, bring_gold_max, interval_min_time, interval_max_time, leave_min_time, leave_max_time, rest_min_games, rest_max_games, room_max_qty, create_user, create_time, update_user, update_time, begin_control_time, control_cycle, bet_count, chip_rates, game_model) VALUES
  (1, 2, '1', '20170315001', '10', 0, '1', 1, 2, 100, 1500, 100, 1500, 3, 8, 200, 'admin', '2017-03-15 09:42:47.000000', 'admin', '2017-03-15 09:42:47.000000', null, null, 5, '1,1,1,1,1', 1);
INSERT INTO circle.player_ai_control (id, room_id, room_name, batch_id, status, ai_qty, control_mode, bring_gold_min, bring_gold_max, interval_min_time, interval_max_time, leave_min_time, leave_max_time, rest_min_games, rest_max_games, room_max_qty, create_user, create_time, update_user, update_time, begin_control_time, control_cycle, bet_count, chip_rates, game_model) VALUES
  (2, 4, '1', '20170315001', '10', 0, '1', 1, 2, 100, 1500, 100, 1500, 3, 8, 200, 'admin', '2017-03-15 09:42:47.000000', 'admin', '2017-03-15 09:42:47.000000', null, null, 5, '1,1,1,1,1', 1);
INSERT INTO circle.player_ai_control (id, room_id, room_name, batch_id, status, ai_qty, control_mode, bring_gold_min, bring_gold_max, interval_min_time, interval_max_time, leave_min_time, leave_max_time, rest_min_games, rest_max_games, room_max_qty, create_user, create_time, update_user, update_time, begin_control_time, control_cycle, bet_count, chip_rates, game_model) VALUES
  (3, 6, '1', '20170315001', '10', 0, '1', 1, 2, 100, 1500, 100, 1500, 3, 8, 200, 'admin', '2017-03-15 09:42:47.000000', 'admin', '2017-03-15 09:42:47.000000', null, null, 5, '1,1,1,1,1', 1);
INSERT INTO circle.player_ai_control (id, room_id, room_name, batch_id, status, ai_qty, control_mode, bring_gold_min, bring_gold_max, interval_min_time, interval_max_time, leave_min_time, leave_max_time, rest_min_games, rest_max_games, room_max_qty, create_user, create_time, update_user, update_time, begin_control_time, control_cycle, bet_count, chip_rates, game_model) VALUES
  (4, 8, '1', '20170315001', '10', 0, '1', 1, 2, 100, 1500, 100, 1500, 3, 8, 200, 'admin', '2017-03-15 09:42:47.000000', 'admin', '2017-03-15 09:42:47.000000', null, null, 5, '1,1,1,1', 3);
INSERT INTO circle.player_ai_control (id, room_id, room_name, batch_id, status, ai_qty, control_mode, bring_gold_min, bring_gold_max, interval_min_time, interval_max_time, leave_min_time, leave_max_time, rest_min_games, rest_max_games, room_max_qty, create_user, create_time, update_user, update_time, begin_control_time, control_cycle, bet_count, chip_rates, game_model) VALUES
  (5, 10, '1', '20170315001', '10', 0, '1', 1, 2, 100, 1500, 100, 1500, 3, 8, 200, 'admin', '2017-03-15 09:42:47.000000', 'admin', '2017-03-15 09:42:47.000000', null, null, 5, '1,1,1,1', 3);
INSERT INTO circle.player_ai_control (id, room_id, room_name, batch_id, status, ai_qty, control_mode, bring_gold_min, bring_gold_max, interval_min_time, interval_max_time, leave_min_time, leave_max_time, rest_min_games, rest_max_games, room_max_qty, create_user, create_time, update_user, update_time, begin_control_time, control_cycle, bet_count, chip_rates, game_model) VALUES
  (6, 12, '1', '20170315001', '10', 0, '1', 1, 2, 100, 1500, 100, 1500, 3, 8, 200, 'admin', '2017-03-15 09:42:47.000000', 'admin', '2017-03-15 09:42:47.000000', null, null, 5, '1,1,1,1', 3);

update player_ai_control set
bring_gold_min = (SELECT gg.min_limit_player_blance*100 from game_room gg where gg.id=player_ai_control.room_id),
bring_gold_max = (SELECT gg.min_limit_player_blance*400 from game_room gg where gg.id=player_ai_control.room_id);

INSERT INTO circle.player_ai_ratio_control (id, ai_player_control_id, player_proportion_min, player_proportion_max, ai_proportion_min, ai_proportion_max)
VALUES (1, 1, 0, 100, 45, 55),
(2, 2, 0, 100, 45, 55),
(3, 3, 0, 100, 45, 55),
(4, 4, 0, 100, 45, 55),
(5, 5, 0, 100, 45, 55),
(6, 6, 0, 100, 45, 55);

###########################调控修改
UPDATE player_ai_control set rest_min_games=6,rest_max_games=13;

SELECT id,a.max,a.dealer_blance_tip,a.dealer_blance_quit,a.jackpot_sum,a.min_jackpot_limit,a.max_jackpot_limit,a.max_jackpot_a_match,a.max_limit_game_lose from game_room_config_bull100 a ORDER BY id asc;
UPDATE game_room_config_bull100 set dealer_blance = jackpot_sum/4,dealer_blance_tip = 60, dealer_blance_quit =40;
update game_room set min_limit_player_blance = 10000 where id=4;

update user_player set is_ai=false;
update user_player set is_ai=true where id in(SELECT b.user_id from ai_user b where b.id%2=1);

update game_room_config_bull100 set max_jackpot_limit=6000000<<(id-7),max_jackpot_a_match=300000<<(id-7),jackpot_sum=3000000<<(id-7),
max_limit_game_lose=300000<<(id-7), min_jackpot_limit=600000<<(id-7) where id>=7;
update game_room_config_bull100 set max_jackpot_limit=4000000<<(id-1),max_jackpot_a_match=400000<<(id-1),jackpot_sum=2000000<<(id-1),
max_limit_game_lose=400000<<(id-1), min_jackpot_limit=200000<<(id-1) where id<=6;

circle=> SELECT id,a.jackpot_sum,a.min_jackpot_limit,a.max_jackpot_limit,a.max_jackpot_a_match,a.max_limit_game_lose from game_room_config_bull100 a ORDER BY id asc;
 id | jackpot_sum | min_jackpot_limit | max_jackpot_limit | max_jackpot_a_match | max_limit_game_lose
----+-------------+-------------------+-------------------+---------------------+---------------------
  1 |     2000000 |            200000 |           4000000 |              400000 |              400000
  2 |     4000000 |            400000 |           8000000 |              800000 |              800000
  3 |     8000000 |            800000 |          16000000 |             1600000 |             1600000
  4 |    16000000 |           1600000 |          32000000 |             3200000 |             3200000
  5 |    32000000 |           3200000 |          64000000 |             6400000 |             6400000
  6 |    64000000 |           6400000 |         128000000 |            12800000 |            12800000
  7 |     3000000 |            600000 |           6000000 |              300000 |              300000
  8 |     6000000 |           1200000 |          12000000 |              600000 |              600000
  9 |    12000000 |           2400000 |          24000000 |             1200000 |             1200000
 10 |    24000000 |           4800000 |          48000000 |             2400000 |             2400000
 11 |    48000000 |           9600000 |          96000000 |             4800000 |             4800000
 12 |    96000000 |          19200000 |         192000000 |             9600000 |             9600000

2017170420=>

 id | jackpot_sum |  jackpot  | jackpot_overflow |  ?column?
----+-------------+-----------+------------------+------------
  1 |     2000000 |    933600 |         38517800 |   37451400
  2 |     4000000 |   5785600 |         83385600 |   85171200
  3 |     8000000 |  14103000 |        172507000 |  178610000
  4 |    16000000 |  28381000 |         78009000 |   90390000
  5 |    32000000 |   3420000 |        727694000 |  699114000
  6 |    64000000 | 102255000 |       2383420000 | 2421675000
  7 |     3000000 |   5390000 |         29330000 |   31720000
  8 |     6000000 |  11094600 |        117684200 |  122778800
  9 |    12000000 |  22266000 |        223285000 |  233551000
 10 |    24000000 |  48000000 |        476357000 |  500357000
 11 |    48000000 |  94594000 |       1366318000 | 1412912000
 12 |    96000000 | 192000000 |       2932575000 | 3028575000

SELECT b.wallet_balance-b.recharge_total, a.ai_room_id from user_player b,ai_user a where
 a.user_id=b.id and b.wallet_balance-b.recharge_total>0
ORDER by b.wallet_balance-b.recharge_total desc limit 10;