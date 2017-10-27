-- auto gen by double 20170330
--修改连赢派彩预警存储过程

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

											ELSE
												delete from player_warning_win_count_copy where player_id=winCopy.player_id;--删除临时表的用户信息
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
										ELSE
											delete from player_warning_win_count_copy where player_id=winCopy.player_id;--删除临时表的用户信息
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


