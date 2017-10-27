-- auto gen by solo 20170315
SELECT redo_sqls($$
    ALTER TABLE player_warning_win_count ADD agent_username varchar(30);
    ALTER TABLE player_warning_win_count ADD agent_id int4;
$$);