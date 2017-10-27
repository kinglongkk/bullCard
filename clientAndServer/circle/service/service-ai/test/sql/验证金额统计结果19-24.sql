/** 房间池总盈亏 */
SELECT sum(a.jackpot-b.jackpot_sum + a.jackpot_overflow) from game_room a,game_room_config_bull100 b where a.id=b.id;
     sum
-------------
 12723701600

SELECT count(b.id),sum(b.wallet_balance-b.recharge_total) from user_player b where is_ai=false;;
m bet b,user_player a where a.is_ai=false and a.id=b.sys_user_id;
 count |       sum
-------+-----------------
  1302 | -21328684890.00
(1 row)

/** 玩家投注输赢统计 */
SELECT count(b.id), sum(b.single_amount), sum(b.profit_amount), sum(b.water_amount), sum(b.profit_amount + b.water_amount)
from bet b,user_player a where a.is_ai=false and a.id=b.sys_user_id;

  count   |       sum       |       sum       |    sum     |       sum
----------+-----------------+-----------------+------------+-----------------
 30494646 | 822720457600.00 | -21328684890.00 | 8604983290 | -12723701600.00

 select count(id) from bet
  count
----------
 60667821

circle=> select count(id) from match
 count
--------
 125920

 SELECT a.id,b.jackpot_sum,a.jackpot,a.jackpot_overflow,(a.jackpot-b.jackpot_sum + a.jackpot_overflow) from game_room a,game_room_config_bull100 b
 where a.id=b.id ORDER BY a.id;
  id | jackpot_sum |  jackpot  | jackpot_overflow |  ?column?
 ----+-------------+-----------+------------------+------------
   1 |     2000000 |   3830300 |         40717000 |   42547300
   2 |     4000000 |   7252200 |         56586600 |   59838800
   3 |     8000000 |  13085500 |        136120500 |  141206000
   4 |    16000000 |  31455000 |        495985000 |  511440000
   5 |    32000000 |  55020000 |       1731696000 | 1754716000
   6 |    64000000 |  64165000 |       2955840000 | 2956005000
   7 |     3000000 |   6000000 |         31939900 |   34939900
   8 |     6000000 |   9908400 |        108651200 |  112559600
   9 |    12000000 |  23840500 |        259388500 |  271229000
  10 |    24000000 |  38728000 |        567132000 |  581860000
  11 |    48000000 |  82990000 |       2170510000 | 2205500000
  12 |    96000000 | 165130000 |       3982730000 | 4051860000


SELECT a.ai_room_id,count(b.id), sum(b.single_amount), sum(b.profit_amount), sum(b.water_amount), sum(b.profit_amount + b.water_amount)
from bet b,ai_user a where a.id%2=0 and a.user_id=b.sys_user_id GROUP BY a.ai_room_id ORDER BY a.ai_room_id;
 ai_room_id |  count  |       sum       |      sum       |    sum     |      sum
------------+---------+-----------------+----------------+------------+----------------
          1 | 2064728 |   6079222800.00 |  -150109752.00 |  107474452 |   -42635300.00
          2 | 2038629 |  12680301400.00 |  -245221980.00 |  223590580 |   -21631400.00
          3 | 2205023 |  28324489500.00 |  -640894820.00 |  499664820 |  -141230000.00
          4 | 1871316 |  53350272000.00 | -1451802480.00 |  931662480 |  -520140000.00
          5 | 2036974 | 126986156000.00 | -3949374040.00 | 2210588040 | -1738786000.00
          6 | 2061038 | 264554595000.00 | -7564987800.00 | 4610182800 | -2954805000.00
          7 | 2992887 |   3627416400.00 |   -34939900.00 |          0 |   -34939900.00
          8 | 3047248 |   8415013000.00 |  -112559600.00 |          0 |  -112559600.00
          9 | 3081010 |  18149828500.00 |  -271229000.00 |          0 |  -271229000.00
         10 | 2974808 |  36031414000.00 |  -581860000.00 |          0 |  -581860000.00
         11 | 3054298 |  84267264000.00 | -2205500000.00 |          0 | -2205500000.00
         12 | 3062675 | 180251750000.00 | -4051160000.00 |          0 | -4051160000.00

盈利最高
SELECT max(b.wallet_balance-b.recharge_total), a.ai_room_id from user_player b,ai_user a where
 a.user_id=b.id and b.wallet_balance-b.recharge_total>0
 group by a.ai_room_id ORDER by a.ai_room_id asc;
     max     | ai_room_id
-------------+------------
  3101402.00 |          1
  3115648.00 |          2
 10485940.00 |          3
 13313980.00 |          4
 35806240.00 |          5
 79396900.00 |          6
  1840600.00 |          7
  4270800.00 |          8
  7906000.00 |          9
 10772000.00 |         10
 67182000.00 |         11
 55745000.00 |         12

SELECT c.ai_room_id,count(a.aid),ROUND(avg(a.asum),0),max(a.asum) from (
select b.sys_user_id aid,sum(profit_amount) asum from bet b
where bet_time >= '2017-04-19' and bet_time < '2017-04-21' GROUP by b.sys_user_id
) a,ai_user c where a.aid=c.user_id and a.asum>0 GROUP by c.ai_room_id ORDER BY c.ai_room_id asc;
 ai_room_id | count |  round   |     max
------------+-------+----------+-------------
          1 |    61 |   338620 |  1253274.00
          2 |    80 |   746360 |  2076792.00
          3 |    74 |  1582831 |  4719980.00
          4 |    78 |  2168108 |  6897720.00
          5 |    61 |  6677962 | 17816880.00
          6 |    56 | 13571754 | 35471700.00
          7 |    83 |   222014 |   809600.00
          8 |    81 |   555546 |  2341200.00
          9 |    90 |  1268394 |  4889500.00
         10 |    80 |  2368125 |  6503000.00
         11 |    69 |  5453188 | 16656000.00
         12 |    65 | 12494462 | 43240000.00

SELECT c.ai_room_id,count(a.aid),ROUND(avg(a.asum),0),max(a.asum) from (
select b.sys_user_id aid,sum(profit_amount) asum from bet b
where bet_time >= '2017-04-21' and bet_time < '2017-04-22' GROUP by b.sys_user_id
) a,ai_user c where a.aid=c.user_id and a.asum>0 GROUP by c.ai_room_id ORDER BY c.ai_room_id asc;
 ai_room_id | count |  round   |     max
------------+-------+----------+-------------
          1 |    60 |   405635 |  1304552.00
          2 |    58 |   914174 |  2961340.00
          3 |    59 |  1951878 |  8794010.00
          4 |    57 |  3844341 | 14324360.00
          5 |    53 |  7594058 | 38102760.00
          6 |    53 | 17383192 | 42129200.00
          7 |    86 |   345878 |  1240200.00
          8 |    77 |   674055 |  3237000.00
          9 |    77 |  1494468 |  5511000.00
         10 |    65 |  2632169 |  8262000.00
         11 |    61 |  5280656 | 21120000.00
         12 |    66 | 12973030 | 65975000.00


SELECT c.ai_room_id,count(a.aid),ROUND(avg(a.asum),0),max(a.asum) from (
select b.sys_user_id aid,sum(profit_amount) asum from bet b
where bet_time >= '2017-04-22' and bet_time < '2017-04-23' GROUP by b.sys_user_id
) a,ai_user c where a.aid=c.user_id and a.asum>0 GROUP by c.ai_room_id ORDER BY c.ai_room_id asc;
 ai_room_id | count |  round   |     max
------------+-------+----------+-------------
          1 |    62 |   424027 |  1638870.00
          2 |    62 |   782749 |  2549060.00
          3 |    59 |  1654329 |  4825870.00
          4 |    53 |  3805867 | 12634020.00
          5 |    52 |  8447859 | 29454720.00
          6 |    58 | 19756124 | 62351100.00
          7 |    81 |   298230 |   982900.00
          8 |    71 |   586107 |  1931200.00
          9 |    61 |  1139730 |  4985000.00
         10 |    65 |  2617754 |  9996000.00
         11 |    70 |  6641629 | 25416000.00
         12 |    76 | 14865987 | 52465000.00

SELECT c.ai_room_id,count(a.aid),ROUND(avg(a.asum),0),max(a.asum) from (
select b.sys_user_id aid,sum(profit_amount) asum from bet b
where bet_time >= '2017-04-23' and bet_time < '2017-04-24' GROUP by b.sys_user_id
) a,ai_user c where a.aid=c.user_id and a.asum>0 GROUP by c.ai_room_id ORDER BY c.ai_room_id asc;
 ai_room_id | count |  round   |     max
------------+-------+----------+-------------
          1 |    56 |   419841 |  1822078.00
          2 |    53 |   709129 |  2509220.00
          3 |    47 |  1865587 |  6141200.00
          4 |    61 |  3780041 | 11913320.00
          5 |    58 |  9712825 | 33276320.00
          6 |    40 | 17767338 | 78571800.00
          7 |    71 |   283499 |  1008600.00
          8 |    83 |   713884 |  2294200.00
          9 |    79 |  1285430 |  4342000.00
         10 |    66 |  3050606 |  8219000.00
         11 |    54 |  6988296 | 28334000.00
         12 |    62 | 10732339 | 40575000.00

SELECT c.ai_room_id,count(a.aid),ROUND(avg(a.asum),0),min(a.asum) from (
select b.sys_user_id aid,sum(profit_amount) asum from bet b
where bet_time >= '2017-04-23' and bet_time < '2017-04-24' GROUP by b.sys_user_id
) a,ai_user c where a.aid=c.user_id and a.asum<0 GROUP by c.ai_room_id ORDER BY c.ai_room_id asc;
 ai_room_id | count |   round   |     min
------------+-------+-----------+--------------
          1 |   137 |   -679305 |  -1738590.00
          2 |   119 |  -1505009 |  -5265852.00
          3 |   134 |  -2736509 |  -8502860.00
          4 |   117 |  -7469102 | -29983300.00
          5 |   130 | -14951110 | -42194760.00
          6 |   141 | -31549253 | -95332600.00
          7 |   128 |   -387322 |  -1051900.00
          8 |   111 |   -815995 |  -2738800.00
          9 |   111 |  -1962086 |  -5752500.00
         10 |   119 |  -3859765 | -10665000.00
         11 |   132 |  -9639894 | -37422000.00
         12 |   136 | -18203493 | -56680000.00
