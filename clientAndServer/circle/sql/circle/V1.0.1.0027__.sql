-- auto gen by lenovo 2017-03-28 10:43:23
--修改过盈亏统计存储过程-------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION "circle"."player_data_statistics"("begintime" timestamp, "endtime" timestamp)
  RETURNS "pg_catalog"."bool" AS $BODY$--玩家数据详细
DECLARE
username VARCHAR;--用户名
nickname VARCHAR;--昵称
ownerUsername VARCHAR;--代理商用户名
ownerNickname VARCHAR;--代理商昵称
gameId INTEGER;--所属游戏
gameName VARCHAR;-- 游戏名称
gameModelId INTEGER;-- 所属游戏玩法
gameModelName VARCHAR;-- 玩法名称
roomId INTEGER; -- 房间ID
roomName VARCHAR; -- 房间名称
betNo VARCHAR;-- 注单号
betDate TIMESTAMP;-- 游戏日期
singleAmount NUMERIC;-- 下单金额
profitAmount NUMERIC;-- 盈亏结果金额
winAmount NUMERIC;-- 实际获利金额
status VARCHAR; -- 状态:[10=未确认,20=已确认(有效),30=取消]
result VARCHAR;-- 结果:[10=赢,20=输,30=平,40=赢半,50=输半]
betTime TIMESTAMP;--注单时间
confirmTime TIMESTAMP;--确认时间
settleTime TIMESTAMP;--结算时间
effectiveAmount NUMERIC;--有效交易量
playerstatis refcursor;--定义游标
-- 执行成功标识
isSuccess BOOLEAN DEFAULT FALSE;
BEGIN
 --查询表最小时间和现在当前时间相差天数
--删除保留超过60天的数据
   DELETE from player_data_statistics where bet_date<=(current_timestamp- interval '60 day');
--删除数据避免执行以下过程插入重复的
 -- DELETE FROM player_data_statistics where bet_date BETWEEN begintime and endtime;
	--打开游标
 OPEN playerstatis FOR
		select
		s1.username,
		s1.nickname,
		s2.username as ownerUsername,
		s2.nickname as ownerNickname,
		ga.id as gameId,
		ga.name as gameName,
		gm.id as gameModelId,
		gm.name as gameModelName,
		ga.id as roomId,
		ga.name as roomName,
		b.bet_no as betNo,
		b.single_amount as singleAmount,
		b.profit_amount as profitAmount,
		(case when  b.profit_amount>0 then  -b.profit_amount else  b.single_amount end) as winAmount,
		b.status,
		b.result,
		b.bet_time as betTime,
		b.confirm_time as confirmTime,
		b.settle_time as settleTime,
		b.effective_amount as effectiveAmount
		from bet b
		inner join bet_detail bd on bd.bet_id=b.id
		inner join match ma on ma.id=bd.match_id
		inner join game_room gr on ma.game_room_id=gr.id
		left join game ga on gr.game_id=ga.id
		left join game_model gm on ma.game_model_id=gm.id
		inner join sys_user s1 on b.sys_user_id = s1.id and s1.user_type='30'
		left join sys_user s2 on s1.owner_id= s2.id and s2.user_type='20'
		where b.bet_time BETWEEN begintime and endtime;
--从游标中获取
  FETCH NEXT FROM playerstatis INTO username,nickname,ownerUsername,ownerNickname,gameId,gameName,gameModelId,gameModelName,roomId,roomName,betNo,singleAmount,profitAmount,winAmount,status,result,betTime,confirmTime,settleTime,effectiveAmount;
	-- 游标数据查询循环插入

 WHILE FOUND LOOP
	INSERT INTO player_data_statistics(username, nickname, owner_username, owner_nickname, game_id, game_name, game_model_id, game_model_name,room_id,room_name,bet_no,bet_date,single_amount,profit_amount,win_amount,status,result,bet_time,confirm_time,settle_time,effective_amount)
  VALUES(username,nickname,ownerUsername,ownerNickname,gameId,gameName,gameModelId,gameModelName,roomId,roomName,betNo,current_timestamp- interval '1 day',singleAmount,profitAmount,winAmount,status,result,betTime,confirmTime,settleTime,effectiveAmount);
	FETCH NEXT FROM playerstatis INTO username,nickname,ownerUsername,ownerNickname,gameId,gameName,gameModelId,gameModelName,roomId,roomName,betNo,singleAmount,profitAmount,winAmount,status,result,betTime,confirmTime,settleTime,effectiveAmount;
END LOOP;
--执行代理商下总盈亏汇总
EXECUTE circle.player_data_statistics_report(begintime,endtime);
isSuccess = TRUE;
RETURN isSuccess;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

ALTER FUNCTION "circle"."player_data_statistics"("begintime" timestamp, "endtime" timestamp) OWNER TO "circle";


--修改过连赢派彩存储过程-------------------------------------------------------------------------------------------------------------------------------

--连赢派彩次数存储过程计算连赢算法
--此过程设计到统计表 player_warning_win_count
--临时表:player_warning_win_count_copy
--上一次结束时间表:player_warning_win_count_time
--此过程避免一天时间内返回几百万千万级数据导致执行缓慢

--今日汇总连赢派彩次数查询
DECLARE
payoutTimes1 numeric;--派彩赢数黄色预警零界点
payoutTimes2 numeric;--派彩赢数红色预警零界点
warn record;--声明预警行变量
re record;-- 定义行变量
userId INTEGER ;--定义临时玩家变量ID
n INTEGER DEFAULT 0 ;--定义增量
winCur refcursor;  -- 定义游标变量
profitAmount numeric DEFAULT 0;--临时赢的金币汇总
effectiveAmount numeric DEFAULT 0;--临时有效的投注交易量汇总
inTime TIMESTAMP; -- 临时时间变量存贮记录连赢记录赛事第一条开始时间
outTime TIMESTAMP;-- 临时时间变量存贮记录连赢记录赛事最后一条结束时间
temusername VARCHAR;--临时用户名
temnickname VARCHAR;--临时用户昵称
temregisterDate TIMESTAMP;--临时用户注册日期
temagentusername VARCHAR;--代理商用户名
temagentId INTEGER;--代理商ID
startTime TIMESTAMP;--开始时间
resultTime TIMESTAMP;--结束时间
playerId INTEGER;-- 用户id
username VARCHAR;-- 用户名
nickname VARCHAR;-- 昵称
registerDate TIMESTAMP;-- 注册日期
winAmount numeric;-- 今日赢得金额
betAmount numeric;-- 今日投注金额
matchId INTEGER;--赛事ID
matchIdarr INTEGER[];--字符串赛事ID数组
begintimeData record;--声明执行开始时间行
winCopy record;--查找临时表用户数据行
winCountId INTEGER DEFAULT NULL;--定义插入数据返回的id
agentusername VARCHAR;--代理商用户名
agentId INTEGER;--代理商ID
BEGIN
  --判断每次执行查看上一次执行的结束时间作为下一次执行开始时间查询
  select * into  begintimeData from player_warning_win_count_time;
  if begintimeData is not null THEN
			begintime := begintimeData.beginTime;
			--同时把触发的末尾时间更新到时间临界点表里
      update player_warning_win_count_time set beginTime=endtime;
   ELSE
      --没有数据就插入一条今日开始执行的结束时间
			INSERT into player_warning_win_count_time(beginTime)VALUES(endtime);
  end if;
-- 再一次执行该函数把今日所有统计过的数据变为逻辑删除
  --update player_warning_win_count set is_delete=true where (create_time BETWEEN  begintime and endtime) and is_delete=false;
  -- 获取预警变量
  select *  into warn from player_warning_control where status='10';
  payoutTimes1 := warn.payout_times1;--黄色预警零界点
  payoutTimes2 := warn.payout_times2;--红色预警零界点
    -- 打开游标
		open winCur for
		select bb.*,s.username,s.nickname,s.create_time,u.username as agentusername,u.id as agentid from (
		select m.id as match_id,b.sys_user_id,sum(b.profit_amount) as profit_amount,m.begin_time,m.end_time,sum(b.effective_amount) as effective_amount
		from match m
		left join bet_detail bd on m.id=bd.match_id
		left join bet b on b.id=bd.bet_id and (b.bet_time >= begintime and b.bet_time < endtime)
		where (m.begin_time >= begintime and m.begin_time<endtime)  and m.settle_status='20' and profit_amount is not null
		group by m.id,b.sys_user_id
		order by b.sys_user_id asc,m.begin_time asc) as bb
		left join sys_user s on bb.sys_user_id=s.id and s.user_type='30'
    left join sys_user u on s.owner_id=u.id and u.user_type='20'
    order by bb.sys_user_id asc,bb.begin_time asc;
 -- 抓取第一条游标逻辑数据
	FETCH NEXT FROM winCur INTO matchId,playerId,winAmount,startTime,resultTime,betAmount,username,nickname,registerDate,agentusername,agentId;
	userId := playerId;
	-- 游标数据查询循环插入
	 WHILE FOUND LOOP
      winCountId = null;
			if userId = playerId then
				if winAmount>0 then --大于0表示赢了
					profitAmount = profitAmount + winAmount;
					effectiveAmount = effectiveAmount +betAmount;
					matchIdarr[n]=matchId;
					n:=n+1;
					if n =1 THEN
						temusername = username;
						temnickname = nickname;
						temregisterDate = registerDate;
						temagentusername = agentusername;
						temagentId = agentId;
						inTime = startTime;
						outTime = resultTime;
					ELSEIF n>1 then
						outTime = resultTime;
					end IF;
         end IF;
         if winAmount<0 then --小于0表示输了
           --查看临时表有没有改用户上一次的记录
						select * into winCopy from player_warning_win_count_copy where player_id=userId;
						if winCopy is null then  -- 如果临时表里没有数据就插入
									if n>=payoutTimes1 and n<payoutTimes2 then --满足条件黄色预警
										INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
										agent_username,agent_id)
										VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,'1',false,matchIdarr,endtime,temagentusername,temagentId);
									ELSEIF n>=payoutTimes2 THEN--满足条件红色预警
										INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
										agent_username,agent_id)
										VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,'2',false,matchIdarr,endtime,temagentusername,temagentId);
									end IF;
							ELSE--如果临时表有数据就 按照临时表数据对比判断走流程
									if winCopy.win_count_id is not null THEN
											if n>0 then
												if (winCopy.num + n) >= payoutTimes1 and (winCopy.num + n) < payoutTimes2 then
													--更新 并数组合并
													update player_warning_win_count set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,status='1',
													match_id=array_cat(match_id,matchIdarr),
													create_time=endtime
													where id=winCopy.win_count_id;
												ELSEIF (winCopy.num + n)>=payoutTimes2 THEN
													--更新 并数组合并
													update player_warning_win_count set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,status='2',
													match_id=array_cat(match_id,matchIdarr),
													create_time=endtime
													where id=winCopy.win_count_id;
												END if;
											end if;
									ELSE--win_count_id 如果这个ID 等于空 那么表示统计表无数据 需要插入
										if (winCopy.num + n) >= payoutTimes1 and (winCopy.num + n) < payoutTimes2 then
											INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
											agent_username,agent_id)
											VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount+winCopy.win_amount,effectiveAmount+winCopy.bet_amount,winCopy.start_time,outTime,
											n+winCopy.num,'1',false,array_cat(winCopy.match_id,matchIdarr),endtime,temagentusername,temagentId);
										ELSEIF (winCopy.num + n)>=payoutTimes2 THEN
											INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
											agent_username,agent_id)
											VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount+winCopy.win_amount,effectiveAmount+winCopy.bet_amount,winCopy.start_time,outTime,
											n+winCopy.num,'2',false,array_cat(winCopy.match_id,matchIdarr),endtime,temagentusername,temagentId);
										end if;
									end IF;
								delete from player_warning_win_count_copy where player_id=winCopy.player_id;--删除临时表的用户信息
						end if;

					inTime:=null;
					outTime:=null;
					profitAmount:=0;
					effectiveAmount:=0;
					matchIdarr=null;
					temusername = null;
					temnickname = null;
					temagentusername = null;
					temagentId = null;
					temregisterDate = null;
					winCopy = null;
					n:=0;
				end IF;
			ELSE
					---等到不是当前用户匹配是所有参数重置为初始值
					 select * into winCopy from player_warning_win_count_copy where player_id=userId;
						if winCopy is null then  -- 如果临时表里没有数据就插入
							 if n>=payoutTimes1 and n<payoutTimes2 then --满足条件黄色预警
									INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
									agent_username,agent_id)
									VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,'1',false,matchIdarr,endtime,temagentusername,temagentId) returning ID into winCountId;
								ELSEIF n>=payoutTimes2 THEN--满足条件红色预警
									INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
									agent_username,agent_id)
									VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,'2',false,matchIdarr,endtime,temagentusername,temagentId) returning ID into winCountId;
								end IF;
									--判断该用户最后记录有没有赢，有就保存在临时表一条记录用于下一次做比较进行叠加次数
								if n>0 THEN
									--插入到临时表
									INSERT INTO player_warning_win_count_copy(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,is_delete,match_id,create_time,win_count_id)
									VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,false,matchIdarr,endtime,winCountId);
								end if;
						else
							--winCopy不等于空判断
							if winCopy.win_count_id is not null THEN
											if n>0 then
												if (winCopy.num + n) >= payoutTimes1 and (winCopy.num + n) < payoutTimes2 then
													--更新 并数组合并
													update player_warning_win_count set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,status='1',
													match_id=array_cat(match_id,matchIdarr),
													create_time=endtime
													where id=winCopy.win_count_id;
												ELSEIF (winCopy.num + n)>=payoutTimes2 THEN
													--更新 并数组合并
													update player_warning_win_count set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,status='2',
													match_id=array_cat(match_id,matchIdarr),
													create_time=endtime
													where id=winCopy.win_count_id;
												END if;
												--同时也更新临时表的数据
												update player_warning_win_count_copy set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,
												match_id=array_cat(match_id,matchIdarr) where player_id=userId;
											end if;
									ELSE--win_count_id 如果这个ID 等于空 那么表示统计表无数据 需要插入
										if n>0 then
											if (winCopy.num + n) >= payoutTimes1 and (winCopy.num + n) < payoutTimes2 then
												INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
												agent_username,agent_id)
												VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount+winCopy.win_amount,effectiveAmount+winCopy.bet_amount,winCopy.start_time,outTime,
												n+winCopy.num,'1',false,array_cat(winCopy.match_id,matchIdarr),endtime,temagentusername,temagentId) returning ID into winCountId;
											ELSEIF (winCopy.num + n)>=payoutTimes2 THEN
												INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
												agent_username,agent_id)
												VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount+winCopy.win_amount,effectiveAmount+winCopy.bet_amount,winCopy.start_time,outTime,
												n+winCopy.num,'2',false,array_cat(winCopy.match_id,matchIdarr),endtime,temagentusername,temagentId) returning ID into winCountId;
											end if;
												--更新临时表
											update player_warning_win_count_copy set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,
											match_id=array_cat(match_id,matchIdarr),win_count_id=winCountId where player_id=userId;
										end if;
									end IF;
						end if;
       --------------------------------------------------------------------------------------------------------------


				n:=0;
				inTime:=null;
				outTime:=null;
				profitAmount:=0;
				effectiveAmount:=0;
				userId := playerId;
				matchIdarr=null;
				temusername = null;
				temnickname = null;
				temagentusername = null;
				temagentId = null;
				temregisterDate = null;
				winCopy = null;
     --当判断是否是下一个用户临时变量存储值
					if winAmount>0 then --大于0表示赢了
						profitAmount = profitAmount + winAmount;
						effectiveAmount = effectiveAmount +betAmount;
						matchIdarr[n]=matchId;
						n:=n+1;
						if n =1 THEN
							temusername = username;
							temnickname = nickname;
							temagentusername = agentusername;
							temagentId = agentId;
							temregisterDate = registerDate;
							inTime = startTime;
							outTime = resultTime;
						ELSEIF n>1 then
							outTime = resultTime;
						end IF;
           end IF;
					-------------------------------------------------------
					IF winAmount<0 THEN
						------------------------------------------------------
								--判断下一个用户第一条数据派彩额是否小于0 就删除临时表对应的数据
								 select * into winCopy from player_warning_win_count_copy where player_id=userId;
									if winCopy is not null then
												delete from player_warning_win_count_copy where player_id=winCopy.player_id;--删除临时表的用户信息
									end if;
									winCopy = null;
						------------------------------------------------------
					END IF;

			end IF;

	FETCH NEXT FROM winCur INTO matchId,playerId,winAmount,startTime,resultTime,betAmount,username,nickname,registerDate,agentusername,agentId;

   END LOOP;

		---判断最后数据遍历是所赢得派彩金额是否是大于0
	IF n > 0 THEN
			---等到不是当前用户匹配是所有参数重置为初始值
					 select * into winCopy from player_warning_win_count_copy where player_id=userId;
						if winCopy is null then  -- 如果临时表里没有数据就插入
							 if n>=payoutTimes1 and n<payoutTimes2 then --满足条件黄色预警
									INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
									agent_username,agent_id)
									VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,'1',false,matchIdarr,endtime,temagentusername,temagentId) returning ID into winCountId;
								ELSEIF n>=payoutTimes2 THEN--满足条件红色预警
									INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
									agent_username,agent_id)
									VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,'2',false,matchIdarr,endtime,temagentusername,temagentId) returning ID into winCountId;
								end IF;
									--判断该用户最后记录有没有赢，有就保存在临时表一条记录用于下一次做比较进行叠加次数
								if n>0 THEN
									--插入到临时表
									INSERT INTO player_warning_win_count_copy(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,is_delete,match_id,create_time,win_count_id)
									VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount,effectiveAmount,inTime,outTime,n,false,matchIdarr,endtime,winCountId);
								end if;
						else
							--winCopy不等于空判断
							if winCopy.win_count_id is not null THEN
											if n>0 then
												if (winCopy.num + n) >= payoutTimes1 and (winCopy.num + n) < payoutTimes2 then
													--更新 并数组合并
													update player_warning_win_count set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,status='1',
													match_id=array_cat(match_id,matchIdarr),
													create_time=endtime
													where id=winCopy.win_count_id;
												ELSEIF (winCopy.num + n)>=payoutTimes2 THEN
													--更新 并数组合并
													update player_warning_win_count set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,status='2',
													match_id=array_cat(match_id,matchIdarr),
													create_time=endtime
													where id=winCopy.win_count_id;
												END if;
												--同时也更新临时表的数据
												update player_warning_win_count_copy set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,
												match_id=array_cat(match_id,matchIdarr) where player_id=userId;
											end if;
									ELSE--win_count_id 如果这个ID 等于空 那么表示统计表无数据 需要插入
										if n>0 then
											if (winCopy.num + n) >= payoutTimes1 and (winCopy.num + n) < payoutTimes2 then
												INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
												agent_username,agent_id)
												VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount+winCopy.win_amount,effectiveAmount+winCopy.bet_amount,winCopy.start_time,outTime,
												n+winCopy.num,'1',false,array_cat(winCopy.match_id,matchIdarr),endtime,temagentusername,temagentId) returning ID into winCountId;
											ELSEIF (winCopy.num + n)>=payoutTimes2 THEN
												INSERT INTO player_warning_win_count(player_id,date,username,nickname,register_date,win_amount,bet_amount,start_time,end_time,num,status,is_delete,match_id,create_time,
												agent_username,agent_id)
												VALUES(userId,begintime,temusername,temnickname,temregisterDate,profitAmount+winCopy.win_amount,effectiveAmount+winCopy.bet_amount,winCopy.start_time,outTime,
												n+winCopy.num,'2',false,array_cat(winCopy.match_id,matchIdarr),endtime,temagentusername,temagentId) returning ID into winCountId;
											end if;
												--更新临时表
											update player_warning_win_count_copy set win_amount=win_amount+profitAmount,bet_amount=bet_amount+effectiveAmount,end_time=outTime,num=num+n,
											match_id=array_cat(match_id,matchIdarr),win_count_id=winCountId where player_id=userId;
										end if;
									end IF;
						end if;
  end if ;

   -- 逻辑循环
		--<<arrlabel>> foreach x  in array arr loop
CLOSE winCur;
		--end loop arrlabel;
 --抓取游标数据

  return true;
END

				--


--增加盈亏统计报表存储过程-------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION "circle"."player_data_statistics_report"("begintime" timestamp, "endtime" timestamp)
  RETURNS "pg_catalog"."void" AS $BODY$--总盈亏汇总
DECLARE
dateTime TIMESTAMP;--日期
winAmount numeric;--盈亏金额
agentUsername VARCHAR;--代理商用户名
winCur refcursor;--查找临时表用户数据行
re record;-- 定义行变量
BEGIN
 open winCur for
  select
  bet_date as betdate,sum(win_amount) as winamount,owner_username as agentusername
  from player_data_statistics
  where bet_date BETWEEN begintime and endtime
  group  by owner_username ,bet_date;

		FETCH NEXT FROM winCur INTO dateTime,winAmount,agentUsername;
	-- 游标数据查询循环插入
	 WHILE FOUND LOOP
		select * into re from player_data_statistics_report where agent_username=agentUsername and date BETWEEN begintime and endtime;
		if re is null then
			INSERT INTO player_data_statistics_report(date,win_amount,agent_username) VALUES(dateTime,winAmount,agentUsername);
		end IF;
		FETCH NEXT FROM winCur INTO dateTime,winAmount,agentUsername;
   end LOOP;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

ALTER FUNCTION "circle"."player_data_statistics_report"("begintime" timestamp, "endtime" timestamp) OWNER TO "circle";

--增加盈亏统计报表-------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE "circle"."player_data_statistics_report" (
"id" int4 DEFAULT nextval('player_data_statistics_report_id_seq'::regclass) NOT NULL,
"date" date,
"win_amount" numeric(20),
"agent_username" varchar(30) COLLATE "default",
CONSTRAINT "player_data_statistics_report_pkey" PRIMARY KEY ("id")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "circle"."player_data_statistics_report" OWNER TO "circle";

COMMENT ON COLUMN "circle"."player_data_statistics_report"."date" IS '日期';

COMMENT ON COLUMN "circle"."player_data_statistics_report"."win_amount" IS '实际获利金额';

COMMENT ON COLUMN "circle"."player_data_statistics_report"."agent_username" IS '代理商用户名';
