#创建AI用户配置表
INSERT INTO ai_user (user_id, ai_room_id)
SELECT id,1 from sys_user where username LIKE 'mkk%' LIMIT 200;

INSERT INTO ai_user (user_id, ai_room_id)
SELECT id,3 from sys_user where username LIKE 'mkk8__' or username LIKE 'mkk7__' LIMIT 200;

#查询AI用户余额及盈亏表
SELECT a.id,b.wallet_balance-b.recharge_total,b.wallet_balance,b.recharge_total
from ai_user a,user_player b where a.user_id=b.id and  b.wallet_balance-b.recharge_total>0 ORDER BY a.id asc;

SELECT count(b.id),sum(b.wallet_balance-b.recharge_total)
from ai_user a,user_player b where a.user_id=b.id;
SELECT count(b.id),sum(b.wallet_balance-b.recharge_total)
from user_player b;

UPDATE player_ai_control set rest_min_games=6,rest_max_games=13;

update player_warning_control set win_rate1=5, win_rate2= 7;

#正式版数据更改
DELETE from game_room_config_bull100 where id in(4,5,6,10,11,12);
update game_room_config_bull100 set id=id*2-1 where id = 3;
update game_room_config_bull100 set id=id*2-1 where id = 2;
update game_room_config_bull100 set id=(id-6)*2+5 where id = 9;
update game_room_config_bull100 set id=(id-6)*2+5 where id = 8;
UPDATE game_room_config_bull100 set room_id=id;

INSERT INTO "circle"."game_room_config_bull100" ("id", "room_id", "bet_chip", "dealer_blance", "dealer_blance_tip", "dealer_blance_quit", "bet_times", "jackpot_sum", "max_limit_game_lose", "min_jackpot_limit", "max_jackpot_limit", "max_jackpot_a_match") VALUES ('2', '2', '100,500,1000,2000,5000', '500000', '60', '40', '10', '2000000', '400000', '200000', '4000000', '400000');
INSERT INTO "circle"."game_room_config_bull100" ("id", "room_id", "bet_chip", "dealer_blance", "dealer_blance_tip", "dealer_blance_quit", "bet_times", "jackpot_sum", "max_limit_game_lose", "min_jackpot_limit", "max_jackpot_limit", "max_jackpot_a_match") VALUES ('4', '4', '200,1000,2000,5000,10000', '1000000', '60', '40', '10', '4000000', '800000', '400000', '8000000', '800000');
INSERT INTO "circle"."game_room_config_bull100" ("id", "room_id", "bet_chip", "dealer_blance", "dealer_blance_tip", "dealer_blance_quit", "bet_times", "jackpot_sum", "max_limit_game_lose", "min_jackpot_limit", "max_jackpot_limit", "max_jackpot_a_match") VALUES ('6', '6', '500,2000,5000,10000,20000', '2000000', '60', '40', '10', '8000000', '1600000', '800000', '16000000', '1600000');
INSERT INTO "circle"."game_room_config_bull100" ("id", "room_id", "bet_chip", "dealer_blance", "dealer_blance_tip", "dealer_blance_quit", "bet_times", "jackpot_sum", "max_limit_game_lose", "min_jackpot_limit", "max_jackpot_limit", "max_jackpot_a_match") VALUES ('8', '8', '100,500,1000,2000', '750000', '60', '40', '10', '3000000', '300000', '600000', '6000000', '300000');
INSERT INTO "circle"."game_room_config_bull100" ("id", "room_id", "bet_chip", "dealer_blance", "dealer_blance_tip", "dealer_blance_quit", "bet_times", "jackpot_sum", "max_limit_game_lose", "min_jackpot_limit", "max_jackpot_limit", "max_jackpot_a_match") VALUES ('10', '10', '200,1000,2000,5000', '1500000', '60', '40', '10', '6000000', '600000', '1200000', '12000000', '600000');
INSERT INTO "circle"."game_room_config_bull100" ("id", "room_id", "bet_chip", "dealer_blance", "dealer_blance_tip", "dealer_blance_quit", "bet_times", "jackpot_sum", "max_limit_game_lose", "min_jackpot_limit", "max_jackpot_limit", "max_jackpot_a_match") VALUES ('12', '12', '500,2000,5000,10000', '3000000', '60', '40', '10', '12000000', '1200000', '2400000', '24000000', '1200000');

UPDATE game_room_config_bull100 set jackpot_sum=min_jackpot_limit;

update game_room set min_limit_player_blance=1000 WHERE id in(2,8);
update game_room set min_limit_player_blance=2000 WHERE id in(3,4,9,10);
update game_room set min_limit_player_blance=5000 WHERE id in(5,6,11,12);


update player_ai_ratio_control set ai_proportion_min=96,ai_proportion_max=99;
update user_player set is_ai=false;
update user_player set is_ai=true where id in(SELECT b.user_id from ai_user b where b.id%20=0);

#清理数据
update circle.game_room set jackpot = (SELECT b.jackpot_sum from game_room_config_bull100 b where b.id =game_room.id),
 jackpot_overflow=0;
TRUNCATE match;
TRUNCATE match_result;
TRUNCATE bet;
TRUNCATE bet_detail;
TRUNCATE player_transaction;
TRUNCATE player_data_statistics;
TRUNCATE player_data_statistics_report;
TRUNCATE player_warning_win_count;
TRUNCATE player_profit;
UPDATE user_player set recharge_total =0, wallet_balance = 0;

update user_player set wallet_balance=50000000,recharge_total=50000000 where id in(SELECT id from sys_user where nickname in('mk01','mk02','mk03','mk04','mk05','mk06','mk07','mk08','mk09'));
UPDATE profit_execute_id set execute_id =0;