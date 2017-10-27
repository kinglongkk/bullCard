--玩家盈利榜 black

-- 删除原本的存储过程
DROP FUNCTION player_profit_order(DATE);

CREATE OR REPLACE FUNCTION circle.player_profit_order(endtime timestamp)
  RETURNS bool
AS
$BODY$
/**
 * Created by black on 2017/05/29
 * 玩家盈利榜
 */
DECLARE
  paIsSuccess BOOLEAN := FALSE;

  paExecuteId BIGINT;
  paMaxExecuteId BIGINT;
  paProfitAmount NUMERIC(20,2);
BEGIN

  paExecuteId := (SELECT execute_id FROM profit_execute_id WHERE id = 1);
  paMaxExecuteId := (SELECT max(id) FROM bet WHERE id > paExecuteId AND profit_amount > 0 AND confirm_time < endTime);

  --判断paMaxExecuteId是否和paExecuteId，若是相等则不进行下面的操作
  IF paMaxExecuteId ISNULL THEN
    RETURN paIsSuccess;
  END IF;
  -- 将投注过的玩家统计插入玩家盈利表
  INSERT INTO
    player_profit
    (
      user_id,
      profit_amount,
      game_id
    )
    SELECT
      sys_user_id,
      SUM(profit_amount) AS profit_amount,
      1
    FROM
      bet
    WHERE
      id > paExecuteId
      AND
      id <= paMaxExecuteId
      AND
      profit_amount > 0
    GROUP BY
      sys_user_id;

  -- 修改盈利榜的起始id
  UPDATE profit_execute_id SET execute_id = paMaxExecuteId WHERE id = 1;

  paIsSuccess := TRUE;
  RETURN paIsSuccess;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;