-- auto gen by  20170413 

CREATE INDEX "idx_bet_id" ON "circle"."bet_detail" USING btree ("bet_id");

CREATE INDEX "idx_match_id" ON "circle"."bet_detail" USING btree ("match_id");

CREATE INDEX "idx_game_id" ON "circle"."match" USING btree ("game_id");

CREATE INDEX "idx_game_model_id" ON "circle"."match" USING btree ("game_model_id");

CREATE INDEX "idx_sys_user_id_settle_status_is_delete" ON "circle"."bet" USING btree ("sys_user_id", "settle_status", "is_deleted");