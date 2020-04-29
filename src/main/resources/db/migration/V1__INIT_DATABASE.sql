CREATE TABLE "jooq"."book" (
  id bigserial NOT NULL PRIMARY KEY,
  title VARCHAR(50),
  name VARCHAR(50),
  author JSONB,
  authors JSONB,
  page INTEGER,
  -- This column indicates when each book record was modified for the last time
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted bool default false
);






