-- 修改sys_user的用户头像
CREATE OR REPLACE FUNCTION circle.update_sys_user_head_image()
  RETURNS bool
AS
$BODY$
/**
* Created by black on 2017/04/21
* 修改玩家的头像
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

    -- 将随机的头像分配给玩家
    UPDATE
      sys_user
    SET
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