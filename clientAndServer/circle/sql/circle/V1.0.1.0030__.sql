-- auto gen by  20170401 


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