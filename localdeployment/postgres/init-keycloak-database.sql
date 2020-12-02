CREATE USER keycloak_user PASSWORD 'keycloak_user';
CREATE DATABASE keycloak;
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak_user;
