-- auto gen by double 20170314
-- 脚本说明：SQL重做脚本
-- 脚本内容
CREATE OR REPLACE FUNCTION redo_sqls (sqls text) RETURNS void
LANGUAGE plpgsql
AS $$
declare
  arr text[];
  tmp varchar[];
  s text;
  obj_name varchar;
  sql_str varchar;
  cnt int := 0;
begin
  SELECT regexp_split_to_array(sqls, ';') INTO arr;
  <<lbl1>>foreach s in array arr
  loop
    s := btrim(s, chr(10));
    s := btrim(s, chr(13));
    s := btrim(s, ' ');
    sql_str := replace(lower(s), '"', '');
    IF regexp_matches(sql_str, 'create\s+sequence \S+','i') is not null THEN
      obj_name := (regexp_matches(sql_str, 'create\s+sequence (\S+)','i'))[1];
      IF position((select "current_schema"()) || '.' in obj_name) = 1 THEN
        obj_name := (regexp_matches(obj_name, (select "current_schema"()) || '\.(\w+)','i'))[1];
      END IF;
      select count(*) into cnt from pg_class where relkind='S' and relname=obj_name and pg_table_is_visible(oid);
    ELSEIF regexp_matches(sql_str, 'alter\s+table\s+\S+\s+add\s+(?:column\s+)?\S+','i') is not null THEN
      tmp := regexp_matches(sql_str, 'alter\s+table\s+(\S+)\s+add\s+(?:column\s+)?(\S+)','i');
      obj_name := tmp[1];
      IF position( (select "current_schema"()) || '.' in obj_name) = 1 THEN
        obj_name := (regexp_matches(obj_name, (select "current_schema"()) || '\.(\w+)','i'))[1];
      END IF;
      select count(*) into cnt from information_schema.columns where table_schema=(SELECT current_schema()) and table_name=obj_name and column_name=tmp[2];
    ELSEIF regexp_matches(sql_str, 'create(\s+\S+\s+|\s+)index\s+\S+\s+on\s+\S+','i') is not null THEN
      obj_name := (regexp_matches(sql_str, 'create(\s+\S+\s+|\s+)index\s+(\S+)\s+on\s+\S+','i'))[2];
      SELECT count(*) into cnt FROM pg_class c JOIN pg_namespace n ON n.oid = c.relnamespace WHERE c.relkind = 'i' AND c.relname = obj_name;
    ELSEIF regexp_matches(sql_str, 'alter\s+table\s+\S+\s+add\s+constraint\s+\S+','i') is not null THEN
      tmp := regexp_matches(sql_str, 'alter\s+table\s+(\S+)\s+add\s+constraint\s+(\S+)','i');
      obj_name := tmp[1];
      IF position( (select "current_schema"()) || '.' in obj_name) = 1 THEN
        obj_name := (regexp_matches(obj_name, (select "current_schema"()) || '\.(\w+)','i'))[1];
      END IF;
      select count(*) into cnt from information_schema.constraint_column_usage where table_name = obj_name and constraint_name = tmp[2];
    END IF;

    IF cnt = 0 AND s <> '' THEN
      execute s;
    END IF;

  end loop lbl1;

end
$$

