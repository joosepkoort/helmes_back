DROP SCHEMA public CASCADE;

CREATE SEQUENCE seq1 START WITH 1;

CREATE TABLE USERS (
                       id BIGINT NOT NULL,
                       username VARCHAR(255) NOT NULL PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       sectors_data BLOB NULL,
                       agreed_to_terms BOOLEAN NULL,
);

CREATE TABLE TREE (
                      id BIGINT,
                      parent_id INTEGER NULL,
                      child_id INTEGER NULL,
                      name VARCHAR(255) NULL
);

insert into USERS (id, username,password,enabled,name) VALUES (NEXT VALUE FOR seq1, 'adminhelmes','$2a$10$I5kA8aNRw88XDB1PtDueLulWtCz8Q5Io9s69DVoBGoauUITgx3dou',TRUE,'');
insert into USERS (id, username,password,enabled,name) VALUES (NEXT VALUE FOR seq1, 'userhelmes','$2a$10$Q5h61V3LfQnswub9ix3mcOoqzzOx5AnwwvvNzDANYAJ2242SF9aJ2',TRUE,'');
CREATE TABLE AUTHORITIES (
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             FOREIGN KEY (username) REFERENCES USERS
                                 ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_auth_username ON AUTHORITIES (username, authority);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('userhelmes', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('adminhelmes', 'ROLE_ADMIN');




