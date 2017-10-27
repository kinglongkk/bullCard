-- auto gen by MK 20170222
-- 用户表增加一个字段表示是否是AI
select redo_sqls($$
   alter table user_player add column is_ai BOOLEAN DEFAULT FALSE  NOT NULL;
$$);

COMMENT ON COLUMN circle.user_player.is_ai IS '是否是AI用户';