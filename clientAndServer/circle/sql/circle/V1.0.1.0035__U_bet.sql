-- auto gen by double 20170407 
SELECT redo_sqls($$
  update bet set water_amount=profit_amount*0.02  where ball_type='DOU_NIU' and (single_amount<>0 and profit_amount/single_amount in (2, 3))
$$);