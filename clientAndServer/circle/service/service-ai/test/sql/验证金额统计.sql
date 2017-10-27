SELECT a.ai_room_id, sum(wallet_balance-recharge_total),(gr.jackpot+gr.jackpot_overflow-c.jackpot_sum) as jp, gr.jackpot,gr.jackpot_overflow
from user_player u, ai_user a, game_room gr, game_room_config_bull100 c
where u.is_ai=false and u.id=a.user_id and a.ai_room_id=gr.id and a.ai_room_id=c.id
GROUP BY a.ai_room_id, jp, gr.jackpot,gr.jackpot_overflow ORDER BY a.ai_room_id;

SELECT sum(gr.jackpot+gr.jackpot_overflow-c1.jackpot_sum) from game_room gr,game_room_config_bull100 c1 where c1.id=gr.id;

SELECT sum(wallet_balance-recharge_total) from user_player u where u.is_ai=false;

SELECT a.ai_room_id,u.is_ai,sum(b.profit_amount)
from user_player u,bet b, ai_user a
where b.sys_user_id=a.user_id and u.id = b.sys_user_id
GROUP BY a.ai_room_id,u.is_ai ORDER BY a.ai_room_id;

/** 玩家余额输赢统计 */
SELECT count(b.id),sum(b.wallet_balance-b.recharge_total) from user_player b where is_ai=false;;
/** 玩家投注输赢统计 */
SELECT count(b.id), sum(b.single_amount), sum(b.profit_amount), sum(b.water_amount), sum(b.profit_amount + b.water_amount)
 from bet b,user_player a where a.is_ai=false and a.id=b.sys_user_id;
/** 房间池总盈亏 */
SELECT sum(a.jackpot-b.jackpot_sum + a.jackpot_overflow) from game_room a,game_room_config_bull100 b where a.id=b.id;
/** 各个房间池输赢统计 */
SELECT a.id,b.jackpot_sum,a.jackpot,a.jackpot_overflow,(a.jackpot-b.jackpot_sum + a.jackpot_overflow) from game_room a,game_room_config_bull100 b
where a.id=b.id ORDER BY a.id;
/** 各个房间的AI输赢统计 */
SELECT a.ai_room_id,count(b.id), sum(b.single_amount), sum(b.profit_amount), sum(b.water_amount), sum(b.profit_amount + b.water_amount)
from bet b,ai_user a where a.id%2=0 and a.user_id=b.sys_user_id GROUP BY a.ai_room_id ORDER BY a.ai_room_id;


/** 房间5,6玩家余额输赢统计 *//** 房间5,6玩家投注输赢统计 */
SELECT count(b.id),sum(b.wallet_balance-b.recharge_total) from user_player b, ai_user a
  where (a.ai_room_id =4) and a.user_id = b.id;
SELECT count(b.id), sum(b.single_amount), sum(b.profit_amount), sum(b.water_amount)
from bet b,ai_user a where (a.ai_room_id =4) and a.user_id=b.sys_user_id ;


