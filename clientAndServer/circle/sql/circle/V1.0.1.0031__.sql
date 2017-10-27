-- auto gen by  20170401 
delete from ai_user;
DELETE from sys_user where username like 'mkk%';
delete from user_player where id not IN (SELECT id from sys_user);

CREATE OR REPLACE FUNCTION createUser (
  IN vStart INT,IN vCount INT,
  OUT vOutValue INT
) AS
$$
DECLARE
  ii INT;
  jj INT;
  maxId INT;
BEGIN
  vOutValue := 0;
  maxId:= (SELECT max(id) from sys_user)+1;
  FOR jj in 1..12 LOOP
  FOR ii in 1..vCount LOOP
    vOutValue := vOutValue+1;
    INSERT INTO "circle"."sys_user" ("id", "username", "password", "dept_id", "status", "create_user", "create_time", "update_user", "update_time", "default_locale", "default_timezone", "subsys_code", "user_type", "built_in", "site_id", "owner_id", "freeze_type", "freeze_start_time", "freeze_end_time", "login_time", "login_ip", "last_active_time", "use_line", "last_login_time", "last_login_ip", "total_online_time", "register_ip", "nickname", "real_name", "birthday", "sex", "constellation", "country", "nation", "avatar_url", "permission_pwd", "idcard", "default_currency", "register_site", "region", "city", "memo", "password_level", "login_ip_dict_code", "last_login_ip_dict_code", "register_ip_dict_code", "login_error_times", "freeze_title", "freeze_content", "freeze_code", "last_logout_time", "freeze_user", "disabled_user", "disabled_time", "freeze_time", "account_freeze_remark", "secpwd_freeze_start_time", "secpwd_freeze_end_time", "secpwd_error_times", "session_key")
    VALUES (maxId, 'mkk'||vStart+vOutValue-1, '3d57c1b093de654a28293819ba30f469', NULL, '1', '0', '2016-10-14 03:19:40.426', NULL, NULL, 'zh_CN', 'GMT+08:00', 'player', '30', 'f', '1', '1', NULL, NULL, NULL, '2016-12-16 02:34:28.098', '2130706433', '2016-12-16 03:35:25.396', 'localhost', '2016-12-15 05:43:32.162', '2130706433', '60451', NULL, 'mkk'||vStart+vOutValue-1, NULL, NULL, '', NULL, NULL, NULL, 'images/head/comm_1_icon_head_00.jpg', '31f36db5d4ddd4eef988b03f00aba9b4', NULL, NULL, NULL, NULL, NULL, '', NULL, '', '', NULL, '0', NULL, NULL, NULL, '2016-12-16 03:36:51.598', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '01a96d78-1c2c-47ff-8d2f-4e7871a2712d');
    INSERT INTO "circle"."user_player" ("id", "player_group_id", "wallet_balance", "recharge_count", "recharge_total", "recharge_max_amount", "withdraw_count", "withdraw_total", "freezing_funds_balance", "invitation_code")
    VALUES (maxId, NULL, 1000000+vOutValue*100000, NULL, NULL, NULL, NULL, NULL, NULL, 'N'||maxId);
    INSERT INTO "circle"."ai_user" ("id", "user_id", "ai_room_id")
    VALUES (vOutValue, maxId, jj);
    maxId := maxId + 1;
  END LOOP;
  END LOOP;
END;
$$
LANGUAGE plpgsql;
SELECT createUser(1, 200);
SELECT count(id) from  "circle"."sys_user" where username LIKE 'mkk%';