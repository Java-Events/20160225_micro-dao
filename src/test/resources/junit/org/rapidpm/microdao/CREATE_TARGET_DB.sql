CREATE TABLE USERS (
  id           INTEGER GENERATED ALWAYS AS IDENTITY (
  START WITH 0 )  PRIMARY KEY,
  login        VARCHAR(255) NOT NULL,
  name         VARCHAR(255)   DEFAULT NULL,
  company      VARCHAR(255)   DEFAULT NULL,
  location     VARCHAR(255)   DEFAULT NULL,
  email        VARCHAR(255)   DEFAULT NULL,
  created_at   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
  type         VARCHAR(255)   DEFAULT 'USR',
  fake         NUMERIC(1, 0)  DEFAULT '0',
  deleted      NUMERIC(1, 0)  DEFAULT '0',
  long         DECIMAL(11, 8) DEFAULT NULL,
  lat          DECIMAL(10, 8) DEFAULT NULL,
  country_code CHAR(3)        DEFAULT NULL,
  state        VARCHAR(255)   DEFAULT NULL,
  city         VARCHAR(255)   DEFAULT NULL,
  UNIQUE (login),
  UNIQUE (email)

);

CREATE TABLE FOLLOWERS (
  follower_id INTEGER GENERATED ALWAYS AS IDENTITY (
  START WITH 0 ),
  user_id     INTEGER NOT NULL,
  createdAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (follower_id, user_id),
  CONSTRAINT follower_fk1 FOREIGN KEY (follower_id) REFERENCES USERS (id),
  CONSTRAINT follower_fk2 FOREIGN KEY (user_id) REFERENCES USERS (id)
);

CREATE TABLE ORGANIZON_MEMBERS (
  org_id     INTEGER GENERATED ALWAYS AS IDENTITY (
  START WITH 0 ),
  user_id    INTEGER NOT NULL,
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (org_id, user_id),
  CONSTRAINT organization_members_ibfk_1 FOREIGN KEY (org_id) REFERENCES USERS (id),
  CONSTRAINT organization_members_ibfk_2 FOREIGN KEY (user_id) REFERENCES USERS (id)
);

CREATE TABLE PROJECTS (
  id          INTEGER GENERATED ALWAYS AS IDENTITY (
  START WITH 0 ),
  url         VARCHAR(255)  DEFAULT NULL,
  owner_id    INTEGER       DEFAULT NULL,
  name        VARCHAR(255) NOT NULL,
  description VARCHAR(255)  DEFAULT NULL,
  language    VARCHAR(255)  DEFAULT NULL,
  createdAt  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
  forked_from INTEGER       DEFAULT NULL,
  deleted     NUMERIC(1, 0) DEFAULT '0',
  updated_at  TIMESTAMP     DEFAULT '1000-01-01 00:00:00',
  PRIMARY KEY (id),
  UNIQUE (name, owner_id),
  CONSTRAINT projects_ibfk_1 FOREIGN KEY (owner_id) REFERENCES USERS (id),
  CONSTRAINT projects_ibfk_2 FOREIGN KEY (forked_from) REFERENCES PROJECTS(id)
);

CREATE TABLE PROJECT_MEMBERS (
  repo_id    INTEGER NOT NULL,
  user_id    INTEGER NOT NULL,
  createdAt TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
  extRefId VARCHAR(24) DEFAULT '0',
  PRIMARY KEY (repo_id, user_id),
  CONSTRAINT project_members_ibfk_1 FOREIGN KEY (repo_id) REFERENCES PROJECTS (id),
  CONSTRAINT project_members_ibfk_2 FOREIGN KEY (user_id) REFERENCES USERS (id)
);
