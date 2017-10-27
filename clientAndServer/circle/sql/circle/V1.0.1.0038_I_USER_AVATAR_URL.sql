-- 初始化用户头像
CREATE SEQUENCE user_avatar_url_id_seq;
CREATE TABLE circle.user_avatar_url (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('user_avatar_url_id_seq'::regclass), -- 主键
  avatar_url CHARACTER VARYING(128) NOT NULL -- 头像
);
CREATE UNIQUE INDEX user_avatar_url_id_uindex ON user_avatar_url USING BTREE (id);
COMMENT ON TABLE circle.user_avatar_url IS '用户头像';
COMMENT ON COLUMN circle.user_avatar_url.id IS '主键';
COMMENT ON COLUMN circle.user_avatar_url.avatar_url IS '头像';

INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_00.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_01.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_02.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_03.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_04.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_05.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_06.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_07.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_08.jpg');
INSERT INTO user_avatar_url(avatar_url) VALUES ('images/head/comm_1_icon_head_09.jpg');

CREATE OR REPLACE FUNCTION user_avatar_url()
  RETURNS bool
AS
$BODY$
/**
* Created by black on 2017/04/21
* 初始化用户头像 初始化为118个头像
*/
DECLARE
  image VARCHAR;
  id INT;
  maxIndex INT = 108;
BEGIN
  id := 1;
  FOR id IN 1..maxIndex LOOP
    image := 'images/head/comm_1_icon_head_' || id || '.jpg';
    INSERT INTO user_avatar_url(avatar_url) VALUES (image);
    id := id + 1;
  END LOOP;
  RETURN TRUE;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

SELECT circle.user_avatar_url();
