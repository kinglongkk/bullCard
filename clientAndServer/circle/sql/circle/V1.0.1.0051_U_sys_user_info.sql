-- 修改玩家的头像以及昵称
DROP FUNCTION update_sys_user_head_image();

CREATE OR REPLACE FUNCTION circle.update_sys_user_info()
  RETURNS bool
AS
$BODY$
  /**
* Created by black on 2017/06/08
* 修改玩家的头像以及昵称
*/
DECLARE
  isSuccess BOOLEAN = FALSE;

  userId INT;

  myCursor refcursor;
BEGIN

  -- 打开游标
  OPEN myCursor FOR
  SELECT
    id
  FROM
    sys_user;

  -- 从游标中获取数据
  FETCH NEXT FROM myCursor INTO userId;

  -- 遍历游标数据
  WHILE FOUND LOOP

    -- 将随机的昵称、头像分配给玩家
    UPDATE
      sys_user
    SET
      nickname = (SELECT
                    nickname
                  FROM
                    user_nickname
                  OFFSET
                    floor(random() * (SELECT max(id) FROM user_nickname))
                  LIMIT
                    1),
      avatar_url = (SELECT
                      avatar_url
                    FROM
                      user_avatar_url
                    OFFSET
                      floor(random() * (SELECT max(id) FROM user_avatar_url))
                    LIMIT
                      1)
    WHERE
      id = userId;
    FETCH NEXT FROM myCursor INTO userId;

  END LOOP;
  isSuccess = TRUE;
  RETURN isSuccess;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;