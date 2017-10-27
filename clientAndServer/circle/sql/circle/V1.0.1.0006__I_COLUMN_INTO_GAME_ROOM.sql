-- auto gen by lance 20170213 
-- 脚本说明：game_room表添加以下4个字段
-- 脚本内容
select redo_sqls($$
	ALTER TABLE game_room_config_bull100 ADD COLUMN jackpot_sum INTEGER;
	ALTER TABLE game_room_config_bull100 ADD COLUMN max_limit_game_lose INTEGER;
	ALTER TABLE game_room_config_bull100 ADD COLUMN min_jackpot_limit INTEGER;
	ALTER TABLE game_room_config_bull100 ADD COLUMN max_jackpot_limit INTEGER;

	COMMENT ON COLUMN game_room_config_bull100.jackpot_sum IS '奖池金额';
	COMMENT ON COLUMN game_room_config_bull100.max_limit_game_lose IS '单局最高可输金额';
	COMMENT ON COLUMN game_room_config_bull100.min_jackpot_limit IS '奖池最低下限金额';
	COMMENT ON COLUMN game_room_config_bull100.max_jackpot_limit IS '奖池最高积累金额';
$$);



