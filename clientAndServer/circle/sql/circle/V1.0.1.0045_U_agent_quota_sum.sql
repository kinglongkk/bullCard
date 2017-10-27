-- 代理额度消耗管理 black

CREATE OR REPLACE FUNCTION circle.agent_quota_sum()
  RETURNS bool
AS
$BODY$
  /**
* Created by black on 2016/12/16
* 代理额度消耗管理
*/
DECLARE
  isSuccess BOOLEAN = FALSE;

  firstCursor REFCURSOR;

  agentId INT;
  consumeQuota NUMERIC;
  agentNickname CHARACTER(32);
  totalQuota NUMERIC;
  currentQuota NUMERIC;
  warnType CHARACTER(10);
  quotaStatus CHARACTER(10);
  sumQuota NUMERIC;
  percentQuota INT;

BEGIN

  -- 打开游标
  OPEN firstCursor FOR
  SELECT
    statis.agent_id,
    statis.quota,
    sysUser.nickname
  FROM
    (
      SELECT
        agent_id,
        -1 * sum(quota) AS quota
      FROM
        agent_quota_statistics
      GROUP BY
        agent_id
    ) AS
    statis,
    sys_user sysUser
  WHERE
    statis.agent_id = sysUser.id;

  -- 从游标中获取数据
  FETCH NEXT FROM firstCursor INTO agentId, consumeQuota, agentNickname;

  -- 遍历游标数据
  WHILE FOUND LOOP

    -- 将数值插入代理额度消耗表
    INSERT INTO agent_quota_transaction(agent_id, quota, nickname) VALUES (agentId, consumeQuota, agentNickname);

    -- 取值
    SELECT
      quota,
      current_quota
    INTO
      totalQuota,
      currentQuota
    FROM
      user_agent
    WHERE
      agent_id = agentId;
    -- 赋值
    sumQuota = consumequota + currentQuota;
    percentQuota = sumQuota/totalQuota*100;
    -- 判断
    IF totalQuota < sumQuota THEN
      quotaStatus = '2';
      warnType = 'RED';
    END IF;
    IF totalQuota > sumQuota THEN
      quotaStatus = '1';
      IF percentQuota < 60 THEN
        warnType = 'GREEN';
      END IF;
      IF 60 <= percentQuota AND percentQuota < 70 THEN
        warnType = 'BLUE';
      END IF;
      IF 70 <= percentQuota AND percentQuota < 80 THEN
        warnType = 'YELLOW';
      END IF;
      IF percentQuota >= 80 THEN
        warnType = 'RED';
      END IF;
    END IF;

    -- 更新代理信息
    UPDATE
      user_agent
    SET
      current_quota = sumQuota,
      warn_type = warnType,
      quota_status = quotaStatus
    WHERE
      agent_id = agentId;

    -- 重新取值
    FETCH NEXT FROM firstCursor INTO agentId, consumeQuota, agentNickname;

  END LOOP;

  isSuccess = TRUE;
  RETURN isSuccess;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;