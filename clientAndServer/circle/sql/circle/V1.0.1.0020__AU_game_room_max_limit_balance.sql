-- auto gen by double 20170314 
ALTER TABLE circle.game_room ALTER COLUMN max_limit_player_blance SET DEFAULT 9999999900;
UPDATE  circle.game_room set max_limit_player_blance=9999999900;