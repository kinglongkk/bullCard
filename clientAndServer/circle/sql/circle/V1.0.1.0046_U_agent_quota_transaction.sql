-- 代理额度消耗表 black
CREATE SEQUENCE agent_quota_transaction_id_seq;
CREATE TABLE circle.agent_quota_transaction (
  id INTEGER NOT NULL DEFAULT nextval('agent_quota_transaction_id_seq'::regclass), -- 主键
  agent_id INTEGER NOT NULL, -- 代理id
  quota NUMERIC(20,2) NOT NULL, -- 额度
  type CHARACTER VARYING(20) NOT NULL DEFAULT 'SETTLE', -- 类型[TransactionTypeEnum][DEPOSIT:存款;SETTLE:结算]
  date DATE DEFAULT now(), -- 日期
  nickname CHARACTER VARYING(32) -- 昵称
);
COMMENT ON TABLE circle.agent_quota_transaction IS '代理额度消耗';
COMMENT ON COLUMN circle.agent_quota_transaction.id IS '主键';
COMMENT ON COLUMN circle.agent_quota_transaction.agent_id IS '代理id';
COMMENT ON COLUMN circle.agent_quota_transaction.quota IS '额度';
COMMENT ON COLUMN circle.agent_quota_transaction.type IS '类型[TransactionTypeEnum][DEPOSIT:存款;SETTLE:结算]';
COMMENT ON COLUMN circle.agent_quota_transaction.date IS '日期';
COMMENT ON COLUMN circle.agent_quota_transaction.nickname IS '昵称';

