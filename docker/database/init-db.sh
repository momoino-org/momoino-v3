#!/bin/bash

set -eu

function create_database() {
  local database="$1"
  local username="$2"
  local password="$3"

  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
  CREATE DATABASE $database;
  CREATE USER $username WITH ENCRYPTED PASSWORD '$password';
  GRANT ALL PRIVILEGES ON DATABASE $database TO $username;

  GRANT ALL ON SCHEMA public TO $username;
EOSQL

  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$database" <<-EOSQL
  GRANT ALL ON SCHEMA public TO $username;
EOSQL
}

function drop_database() {
  local database="$1"

  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
  DROP DATABASE $database;
EOSQL
}

# drop_database "root"
create_database "${KEYCLOAK_DB_NAME}" "${KEYCLOAK_DB_USERNAME}" "${KEYCLOAK_DB_PASSWORD}"
create_database "${MOMOINO_DB_NAME}" "${MOMOINO_DB_USERNAME}" "${MOMOINO_DB_PASSWORD}"