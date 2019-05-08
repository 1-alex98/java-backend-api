
-- Extensions

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Common functions

create or replace function update_update_time()
  returns trigger AS
$$
begin
  NEW.update_time = now();
  return NEW;
end;
$$
  language plpgsql;

-- Hibernate tries to insert null into create_time and update_time, this function replaces it before insert
create or replace function set_insert_times()
  returns trigger AS
$$
begin
  NEW.create_time = now();
  NEW.update_time = now();
  return NEW;
end;
$$
  language plpgsql;

CREATE OR REPLACE FUNCTION f_if(boolean, anyelement, anyelement)
  RETURNS anyelement AS
$$
BEGIN
  CASE WHEN ($1)
    THEN
      RETURN ($2);
    ELSE
      RETURN ($3);
    END CASE;
  EXCEPTION
  WHEN division_by_zero
    THEN
      RETURN ($3);
END;
$$
  LANGUAGE plpgsql;

-- Terms of Service

create table terms_of_service
(
  id          serial primary key,
  content     text      not null,
  published   boolean   not null default false,
  valid_from  timestamp not null,
  create_time timestamp NOT NULL DEFAULT current_timestamp,
  update_time timestamp NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER terms_of_service_create_time
  BEFORE INSERT
  ON terms_of_service
  FOR EACH ROW
EXECUTE PROCEDURE set_insert_times();
CREATE TRIGGER terms_of_service_update_time
  BEFORE UPDATE
  ON terms_of_service
  FOR EACH ROW
EXECUTE PROCEDURE update_update_time();

-- Account

create table account
(
  id                    serial primary key,
  email                 varchar(255)                         not null,
  display_name          varchar(255)                         not null,
  password              varchar(255)                         null,
  last_agreed_tos_id    int references terms_of_service (id) null,
  last_login_user_agent varchar(255)                         null,
  last_login_ip_address varchar(255)                         null,
  last_login_time       timestamp                            null,
  create_time           timestamp                            NOT NULL DEFAULT current_timestamp,
  update_time           timestamp                            NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER account_create_time
  BEFORE INSERT
  ON account
  FOR EACH ROW
EXECUTE PROCEDURE set_insert_times();
CREATE TRIGGER account_update_timespan
  BEFORE UPDATE
  ON account
  FOR EACH ROW
EXECUTE PROCEDURE update_update_time();

CREATE unique index on account (lower(display_name));

create table group_of_users(
    id              serial          primary key,
    name            varchar(255),
    password        varchar(255),
    create_time     timestamp       NOT NULL DEFAULT current_timestamp,
    update_time     timestamp       NOT NULL DEFAULT current_timestamp
);

CREATE TRIGGER group_of_users_create_time
  BEFORE INSERT
  ON group_of_users
  FOR EACH ROW
EXECUTE PROCEDURE set_insert_times();
CREATE TRIGGER group_of_users_update_timespan
  BEFORE UPDATE
  ON group_of_users
  FOR EACH ROW
EXECUTE PROCEDURE update_update_time();

create table oauth_client
(
  id                   serial primary key,
  name                 varchar(255) unique not null,
  client_id            uuid unique         not null default uuid_generate_v4(),
  client_secret        varchar(255) unique not null,
  redirect_uris        text                not null,
  default_redirect_uri varchar(2000)       not null,
  default_scope        text                not null,
  icon_url             varchar(2000)       null,
  auto_approve_scopes  boolean             null,
  create_time          timestamp           NOT NULL DEFAULT current_timestamp,
  update_time          timestamp           NOT NULL DEFAULT current_timestamp,
  first_name            varchar(255),
  sur_name              varchar(255)
);
CREATE TRIGGER oauth_client_create_time
  BEFORE INSERT
  ON oauth_client
  FOR EACH ROW
EXECUTE PROCEDURE set_insert_times();
CREATE TRIGGER oauth_client_update_time
  BEFORE UPDATE
  ON oauth_client
  FOR EACH ROW
EXECUTE PROCEDURE update_update_time();
create unique index on oauth_client (lower(name));

create type group_role as enum ('ADMIN', 'USER', 'OBSERVER', 'FOUNDER');

create table group_membership
(
  id                   serial primary key,
  group_id             integer references group_of_users not null,
  account_id           integer references account not null,
  role                 group_role not null default 'USER',
  create_time          timestamp           NOT NULL DEFAULT current_timestamp,
  update_time          timestamp           NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER group_membership_create_time
  BEFORE INSERT
  ON group_membership
  FOR EACH ROW
EXECUTE PROCEDURE set_insert_times();
CREATE TRIGGER group_membership_update_time
  BEFORE UPDATE
  ON group_membership
  FOR EACH ROW
EXECUTE PROCEDURE update_update_time();

create table money_pool
(
  id                   serial primary key,
  display_name         varchar(255)        not null,
  description          text                ,
  currency             varchar(3)          not null default 'EUR', --ISO 4217
  group_id             integer references group_of_users not null,
  create_time          timestamp           NOT NULL DEFAULT current_timestamp,
  update_time          timestamp           NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER money_pool_create_time
  BEFORE INSERT
  ON money_pool
  FOR EACH ROW
EXECUTE PROCEDURE set_insert_times();
CREATE TRIGGER money_pool_update_time
  BEFORE UPDATE
  ON money_pool
  FOR EACH ROW
EXECUTE PROCEDURE update_update_time();

create table expense
(
  id                   serial primary key,
  display_name         varchar(255)        not null,
  description          text                ,
  value_with_fraction  long                not null,
  money_pool_id        integer references money_pool not null,
  create_time          timestamp           NOT NULL DEFAULT current_timestamp,
  update_time          timestamp           NOT NULL DEFAULT current_timestamp
);
CREATE TRIGGER expense_create_time
  BEFORE INSERT
  ON expense
  FOR EACH ROW
EXECUTE PROCEDURE set_insert_times();
CREATE TRIGGER expense_update_time
  BEFORE UPDATE
  ON expense
  FOR EACH ROW
EXECUTE PROCEDURE update_update_time();

