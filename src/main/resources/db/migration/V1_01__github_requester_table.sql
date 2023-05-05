CREATE TABLE github_user
(
    id uuid not null
        constraint user_pkey
            primary key,
    login varchar(255),
    request_count integer,
    create_ts timestamp not null default now(),
    modify_ts timestamp
);

CREATE FUNCTION trigger_modify_ts()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL
AS $$
BEGIN
    NEW.modify_ts = NOW();
RETURN NEW;
END;
$$
;

CREATE TRIGGER update_modify_ts BEFORE UPDATE ON flyway_schema_history FOR EACH ROW EXECUTE PROCEDURE trigger_modify_ts();
CREATE TRIGGER update_modify_ts BEFORE UPDATE ON github_user FOR EACH ROW EXECUTE PROCEDURE trigger_modify_ts();