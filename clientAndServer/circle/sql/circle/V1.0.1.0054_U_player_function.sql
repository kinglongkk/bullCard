-- 玩家数据详细 存储过程 black
CREATE OR REPLACE FUNCTION circle.player_data_statistics()
  RETURNS bool
AS
$BODY$
  --玩家数据详细
DECLARE
  -- 执行成功标识
  isSuccess BOOLEAN DEFAULT FALSE;
  paExecuteId BIGINT;
  paMaxExecuteId BIGINT;
BEGIN
  paExecuteId := (SELECT execute_id FROM profit_execute_id WHERE id = 4);
  paMaxExecuteId := (SELECT max(id) FROM bet WHERE id > paExecuteId);
  RAISE NOTICE '%,%',paExecuteId,paMaxExecuteId;
  --判断paMaxExecuteId是否和paExecuteId，若是相等则不进行下面的操作
  IF paMaxExecuteId ISNULL THEN
    RETURN isSuccess;
  END IF;
  INSERT INTO player_data_statistics(sys_user_id,owner_username, owner_nickname, game_id, game_name, game_model_id, game_model_name,room_id,room_name,bet_no,bet_date,single_amount,profit_amount,win_amount,status,result,bet_time,confirm_time,settle_time,effective_amount)
    (select
       s1.id,
       s2.username as ownerUsername,
       s2.nickname as ownerNickname,
       ga.id as gameId,
       ga.name as gameName,
       gm.id as gameModelId,
       gm.name as gameModelName,
       ga.id as roomId,
       ga.name as roomName,
       b.bet_no as betNo,
       b.bet_time,
       b.single_amount as singleAmount,
       b.profit_amount as profitAmount,
       -b.profit_amount as winAmount,
       b.status,
       b.result,
       b.bet_time as betTime,
       b.confirm_time as confirmTime,
       b.settle_time as settleTime,
       b.effective_amount as effectiveAmount
     from bet b
       left join sys_user s1 on b.sys_user_id = s1.id and s1.user_type='30'
       left join sys_user s2 on s1.owner_id= s2.id and s2.user_type='20',
       bet_detail bd
       left join match ma on ma.id=bd.match_id
       left join game_room gr on ma.game_room_id=gr.id
       left join game ga on gr.game_id=ga.id
       left join game_model gm on ma.game_model_id=gm.id
     where b.id>paExecuteId and b.id<=paMaxExecuteId and bd.bet_id>paExecuteId and bd.bet_id<=paMaxExecuteId and bd.bet_id=b.id
    );
  UPDATE profit_execute_id SET execute_id = paMaxExecuteId WHERE id = 4;
  --执行代理商下总盈亏汇总
  isSuccess = TRUE;
  RETURN isSuccess;
END
$BODY$
LANGUAGE plpgsql VOLATILE;
