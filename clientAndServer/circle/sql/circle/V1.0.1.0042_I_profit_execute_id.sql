-- id执行表 black
CREATE SEQUENCE profit_execute_id_id_seq;
CREATE TABLE circle.profit_execute_id (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('profit_execute_id_id_seq'::regclass), -- 主键
  execute_id BIGINT NOT NULL, -- 执行id
  explanation CHARACTER VARYING(220) NOT NULL -- explanation
);
CREATE UNIQUE INDEX profit_execute_id_id_uindex ON profit_execute_id USING BTREE (id);
COMMENT ON TABLE circle.profit_execute_id IS 'id执行表';
COMMENT ON COLUMN circle.profit_execute_id.id IS '主键';
COMMENT ON COLUMN circle.profit_execute_id.execute_id IS '执行id';
COMMENT ON COLUMN circle.profit_execute_id.explanation IS 'explanation';

-- 设置默认值
INSERT INTO profit_execute_id(id, execute_id, explanation) VALUES (1, 0, '玩家盈利榜执行id');
INSERT INTO profit_execute_id(id, execute_id, explanation) VALUES (2, 0, '代理额度管理执行id');

