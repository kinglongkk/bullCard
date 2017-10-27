--玩家盈亏表 存储过程 black

-- 删除原本的储存过程
DROP FUNCTION player_profit_and_loss(DATE);

CREATE OR REPLACE FUNCTION circle.player_profit_and_loss(endtime timestamp)
  RETURNS bool
AS
$BODY$
  /**
* Created by black on 2017/05/31
* 玩家盈亏统计
*/
DECLARE
  paIsSuccess BOOLEAN := FALSE;
  paExecuteId BIGINT;
  paMaxExecuteId BIGINT;
BEGIN
  paExecuteId := (SELECT execute_id FROM profit_execute_id WHERE id = 3);
  paMaxExecuteId := (SELECT max(id) FROM bet WHERE id > paExecuteId AND confirm_time < endTime);
  --判断paMaxExecuteId是否和paExecuteId，若是相等则不进行下面的操作
  IF paMaxExecuteId ISNULL THEN
    RETURN paIsSuccess;
  END IF;
  -- 将数据插入玩家盈亏统计表
  INSERT INTO
    player_profit_and_loss
    (player_id,
     gather_time,
     bet_frequency,
     effective_amount,
     profit_amount,
     water_amount)
    SELECT
      sys_user_id,
      cast(to_char(current_timestamp, 'YYYYMMDD') AS INTEGER) AS gather_time,
      count(bet_no) AS bet_frequency,
      sum(effective_amount) AS effective_amount,
      sum(profit_amount) AS profit_amount,
      sum(water_amount) AS water_amount
    FROM
      bet
    WHERE
      id > paExecuteId
      AND
      id <= paMaxExecuteId
    GROUP BY
      sys_user_id;
  -- 修改盈亏的执行id
  UPDATE profit_execute_id SET execute_id = paMaxExecuteId WHERE id = 3;

  paIsSuccess = TRUE;
  RETURN paIsSuccess;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;