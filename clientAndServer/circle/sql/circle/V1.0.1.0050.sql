CREATE INDEX CONCURRENTLY "IDX_BET_USER_ID" ON "circle"."bet" USING hash ("sys_user_id");
CREATE INDEX CONCURRENTLY "IDX_BET_D_BET_ID" ON "circle"."bet_detail" USING hash ("bet_id");
CREATE INDEX CONCURRENTLY "IDX_TRANSACTION_USER_ID" ON "circle"."player_transaction" USING hash ("player_id");

