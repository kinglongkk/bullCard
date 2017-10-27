DROP DATABASE IF EXISTS  circle;
drop schema if exists circle CASCADE;
DROP USER IF EXISTS circle;
create user circle with password 'circle';
CREATE DATABASE circle;
\c circle;
create schema if not exists circle AUTHORIZATION circle;
CREATE EXTENSION tablefunc SCHEMA circle VERSION "1.0";
