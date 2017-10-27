-- auto gen by Double 2017-03-16 10:13:03
CREATE OR REPLACE FUNCTION bet_ids (size integer) RETURNS bigint[]
LANGUAGE plpgsql
AS $$
declare
  id int4;
  ids int8[];
begin

  FOR i IN 1..size LOOP
    --do something
    select nextval('bet_id_seq'::regclass) into id;
    select array_append(ids,id::BIGINT) into ids;
  END LOOP;
  return ids;
end
$$
