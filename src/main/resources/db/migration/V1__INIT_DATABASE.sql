CREATE TABLE IF NOT EXISTS "jooq"."author" (
  "id"         BIGSERIAL         NOT NULL
    CONSTRAINT "author_pk" PRIMARY KEY,

  "name"       VARCHAR(255),
  "age"        INTEGER DEFAULT 0 NOT NULL,
  "gender"     BOOL,
  "remarks"    VARCHAR(255),

  "created_date"       TIMESTAMP,
  "created_by"       VARCHAR(255),
  "updated_date"       TIMESTAMP,
  "updated_by"       VARCHAR(255),
  "ver"              INTEGER DEFAULT 0     NOT NULL
);

CREATE TABLE IF NOT EXISTS "jooq"."book" (
  id bigserial NOT NULL PRIMARY KEY,
  title VARCHAR(50),
  name VARCHAR(50),
  author_id BIGINT REFERENCES "jooq"."author" ("id"),
  authors JSONB,
  page INTEGER,
  deleted bool default false,

  "created_date"       TIMESTAMP,
  "created_by"       VARCHAR(255),
  "updated_date"       TIMESTAMP,
  "updated_by"       VARCHAR(255),
  "ver"              INTEGER DEFAULT 0     NOT NULL
);







