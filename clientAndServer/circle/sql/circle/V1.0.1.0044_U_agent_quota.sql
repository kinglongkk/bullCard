--代理额度管理 black
-- 删除原本存在的储存过程
DROP FUNCTION player_profit_order(DATE);

CREATE OR REPLACE FUNCTION circle.agent_quota(endtime TIMESTAMP)
  RETURNS bool
AS
$BODY$
  /**
* Created by black on 2017/05/30
* 代理额度管理
*/
DECLARE
  paIsSuccess BOOLEAN := FALSE;
  paExecuteId BIGINT;
  paMaxExecuteId BIGINT;
BEGIN
  paExecuteId := (SELECT execute_id FROM profit_execute_id WHERE id = 2);
  paMaxExecuteId := (SELECT max(id) FROM bet WHERE id > paExecuteId AND confirm_time < endTime);
  --判断paMaxExecuteId是否和paExecuteId，若是相等则不进行下面的操作
  IF paMaxExecuteId ISNULL THEN
    RETURN paIsSuccess;
  END IF;
  -- 将代理在各个游戏的额度消耗插入代理旗下玩家消耗额度表
  INSERT INTO
    agent_quota_statistics
    (
      agent_id,
      game_id,
      quota
    )
    SELECT
      sysUser.owner_id,
      1,
      sum(agent.profit_amount) AS quota
    FROM
      (
        SELECT
          sys_user_id,
          sum(profit_amount) AS profit_amount
        FROM
          bet
        WHERE
          id > paExecuteId
          AND
          id <= paMaxExecuteId
        GROUP BY
          sys_user_id) AS
      agent,
      sys_user sysUser
    WHERE
      agent.sys_user_id = sysUser.id
    GROUP BY
      sysUser.owner_id;

  -- 修改盈利榜的起始id
  UPDATE profit_execute_id SET execute_id = paMaxExecuteId WHERE id = 2;

  paIsSuccess = TRUE;
  RETURN paIsSuccess;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;