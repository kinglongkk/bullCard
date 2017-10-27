-- auto gen by MK 20170405

SELECT redo_sqls($$
  ALTER TABLE circle.bet ADD water_amount BIGINT DEFAULT 0 NOT NULL;
  COMMENT ON COLUMN circle.bet.water_amount IS '抽水金额';
$$);
update bet set water_amount=profit_amount*0.02 where profit_amount=single_amount*0.98;
