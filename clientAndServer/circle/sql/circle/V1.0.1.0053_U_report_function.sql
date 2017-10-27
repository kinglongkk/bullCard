-- 代理盈利报表 存储过程 black
DROP FUNCTION player_data_statistics_report(begintime timestamp, endtime timestamp);
CREATE OR REPLACE FUNCTION circle.player_data_statistics_report(endTime TIMESTAMP)
  RETURNS BOOLEAN
AS
$BODY$
--总盈亏汇总
DECLARE
  paIsSuccess BOOLEAN := FALSE;
  paExecuteId BIGINT;
  paMaxExecuteId BIGINT;
BEGIN

  paExecuteId := (SELECT execute_id FROM profit_execute_id WHERE id = 5);
  paMaxExecuteId := (SELECT max(id) FROM player_data_statistics WHERE id > paExecuteId AND confirm_time < endTime);

  --判断paMaxExecuteId是否和paExecuteId，若是相等则不进行下面的操作
  IF paMaxExecuteId ISNULL THEN
    RETURN paIsSuccess;
  END IF;

  INSERT INTO
    player_data_statistics_report(date, win_amount, agent_username)
  SELECT
    bet_date AS betdate,
    SUM(win_amount) AS winamount,
    owner_username AS agentusername
  FROM
    player_data_statistics
  WHERE
    id > paExecuteId
  AND
    id <= paMaxExecuteId
  GROUP BY
    owner_username,
    bet_date;

  -- 修改代理盈利报表执行id
  UPDATE profit_execute_id SET execute_id = paMaxExecuteId WHERE id = 5;

  paIsSuccess := TRUE;
  RETURN paIsSuccess;
END
$BODY$
LANGUAGE plpgsql VOLATILE;