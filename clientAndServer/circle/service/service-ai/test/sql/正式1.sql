TRUNCATE player_data_statistics;


update game_room_config_bull100 set max_jackpot_a_match=5;
update profit_execute_id set execute_id=0;
INSERT INTO "circle"."profit_execute_id" ("id", "execute_id", "explanation") VALUES ('4', '0', 'player_data_statistics执行id');
INSERT INTO "circle"."profit_execute_id" ("id", "execute_id", "explanation") VALUES ('5', '0', '代理盈利报表执行id');
INSERT INTO "circle"."profit_execute_id" ("id", "execute_id", "explanation") VALUES ('6', '0', '代理盈利执行id');

update user_player set wallet_balance=0,recharge_total=0 where id in(0,1);
UPDATE sys_user set nickname=username where id in(0,1);
UPDATE game_room set name='初级' where id%6=1;
UPDATE game_room set name='子爵' where id%6=2;
UPDATE game_room set name='中级' where id%6=3;
UPDATE game_room set name='伯爵' where id%6=4;
UPDATE game_room set name='高级' where id%6=5;
UPDATE game_room set name='尊爵' where id%6=0;


ALTER TABLE "circle"."player_data_statistics" ADD COLUMN "sys_user_id" int4;
ALTER TABLE "circle"."player_data_statistics" DROP COLUMN "username";
ALTER TABLE "circle"."player_data_statistics" DROP COLUMN "nickname";
DROP FUNCTION "circle"."player_data_statistics"("begintime" timestamp, "endtime" timestamp);
DROP FUNCTION "circle"."player_data_statistics_report"("begintime" timestamp, "endtime" timestamp);
CREATE OR REPLACE FUNCTION "circle"."player_data_statistics"()
  RETURNS "pg_catalog"."bool" AS $BODY$
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
  LANGUAGE 'plpgsql' VOLATILE COST 100;
CREATE FUNCTION "circle"."player_data_statistics_report"("endtime" timestamp)
  RETURNS "pg_catalog"."bool" AS $BODY$
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
  LANGUAGE 'plpgsql' VOLATILE COST 100;
DROP INDEX "circle"."IDX_BET_D_BET_ID";
CREATE INDEX "IDX_BET_D_BET_ID" ON "circle"."bet_detail" USING btree ("bet_id");
DROP INDEX "circle"."IDX_BET_USER_ID";
CREATE INDEX "IDX_BET_USER_ID" ON "circle"."bet" USING btree ("sys_user_id");
CREATE INDEX "IDX_STATIS_BET_DATE" ON "circle"."player_data_statistics" USING btree ("bet_date");
CREATE INDEX "IDX_STATIS_USER_ID" ON "circle"."player_data_statistics" USING btree ("sys_user_id");

ALTER TABLE "circle"."qrtz_blob_triggers" DROP CONSTRAINT "qrtz_blob_triggers_sched_name_fkey";
ALTER TABLE "circle"."qrtz_triggers" DROP CONSTRAINT "qrtz_triggers_sched_name_fkey";
DROP INDEX "circle"."idx_qrtz_ft_inst_job_req_rcvry";
DROP INDEX "circle"."idx_qrtz_ft_j_g";
DROP INDEX "circle"."idx_qrtz_ft_jg";
DROP INDEX "circle"."idx_qrtz_ft_t_g";
DROP INDEX "circle"."idx_qrtz_ft_tg";
DROP INDEX "circle"."idx_qrtz_ft_trig_inst_name";
DROP INDEX "circle"."idx_qrtz_j_grp";
DROP INDEX "circle"."idx_qrtz_j_req_recovery";
DROP INDEX "circle"."idx_qrtz_t_c";
DROP INDEX "circle"."idx_qrtz_t_g";
DROP INDEX "circle"."idx_qrtz_t_j";
DROP INDEX "circle"."idx_qrtz_t_jg";
DROP INDEX "circle"."idx_qrtz_t_n_g_state";
DROP INDEX "circle"."idx_qrtz_t_n_state";
DROP INDEX "circle"."idx_qrtz_t_next_fire_time";
DROP INDEX "circle"."idx_qrtz_t_nft_misfire";
DROP INDEX "circle"."idx_qrtz_t_nft_st";
DROP INDEX "circle"."idx_qrtz_t_nft_st_misfire";
DROP INDEX "circle"."idx_qrtz_t_nft_st_misfire_grp";
DROP INDEX "circle"."idx_qrtz_t_state";
DROP TRIGGER "RI_ConstraintTrigger_a_35760" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35761" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35765" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35766" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35770" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35771" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35775" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35776" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_a_35780" ON "circle"."qrtz_job_details";
DROP TRIGGER "RI_ConstraintTrigger_a_35781" ON "circle"."qrtz_job_details";
DROP TRIGGER "RI_ConstraintTrigger_c_35762" ON "circle"."qrtz_blob_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35763" ON "circle"."qrtz_blob_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35767" ON "circle"."qrtz_cron_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35768" ON "circle"."qrtz_cron_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35772" ON "circle"."qrtz_simple_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35773" ON "circle"."qrtz_simple_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35777" ON "circle"."qrtz_simprop_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35778" ON "circle"."qrtz_simprop_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35782" ON "circle"."qrtz_triggers";
DROP TRIGGER "RI_ConstraintTrigger_c_35783" ON "circle"."qrtz_triggers";
DROP TABLE "circle"."qrtz_blob_triggers";
DROP TABLE "circle"."qrtz_calendars";
DROP TABLE "circle"."qrtz_cron_triggers";
DROP TABLE "circle"."qrtz_fired_triggers";
DROP TABLE "circle"."qrtz_job_details";
DROP TABLE "circle"."qrtz_locks";
DROP TABLE "circle"."qrtz_paused_trigger_grps";
DROP TABLE "circle"."qrtz_scheduler_state";
DROP TABLE "circle"."qrtz_simple_triggers";
DROP TABLE "circle"."qrtz_simprop_triggers";
DROP TABLE "circle"."qrtz_triggers";
