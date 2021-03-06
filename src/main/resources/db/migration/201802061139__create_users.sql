SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';


CREATE SCHEMA auditing;
CREATE EXTENSION IF NOT EXISTS unaccent SCHEMA public;

CREATE TABLE auditing.revision (
  id          bigserial NOT NULL PRIMARY KEY,
  "timestamp" bigint    NOT NULL,
  user_id     bigint
);

-- tabela user virou pessoa

/*
CREATE TABLE auditing.user_audited (
  id                              bigint NOT NULL,
  revision                        bigint NOT NULL,
  revision_type                   smallint,
  disabled                        boolean,
  email                           character varying(144),
  last_login                      timestamp without time zone,
  name                            character varying(50),
  password                        character varying(100),
  role                            integer,
  password_reset_token            varchar,
  password_reset_token_expiration timestamp with time zone,
  CONSTRAINT fk_user_audited_revision FOREIGN KEY (revision) REFERENCES auditing.revision (id),
  CONSTRAINT pk_user_audited PRIMARY KEY (id, revision)
);

CREATE TABLE "user" (
  id                              bigint                   NOT NULL PRIMARY KEY,
  created                         timestamp with time zone NOT NULL,
  updated                         timestamp with time zone,
  disabled                        boolean                  NOT NULL,
  email                           character varying(144)   NOT NULL,
  last_login                      timestamp with time zone,
  name                            character varying(50)    NOT NULL,
  password                        character varying(100)   NOT NULL,
  role                            integer                  NOT NULL,
  password_reset_token            varchar,
  password_reset_token_expiration timestamp with time zone
);

CREATE UNIQUE INDEX idx_user_email
  ON "user" (lower(email));

-----------------------
-- DEFAULT DATA
-----------------------
INSERT INTO "user" (
  id, created, updated, email, disabled, name, password, role)
VALUES (1, NOW(), NULL, 'admin@admin.com', FALSE, 'Administrador de Sistemas',
        '$2a$10$bAdAVLvM.k3DqPaPYi0gnO1OffPSHLref8MElAk.u.fFQ17v9YKC2', 0);
*/

-- Table: pessoa

-- DROP TABLE pessoa;

-- Table: pessoa

-- DROP TABLE pessoa;

CREATE TABLE pessoa
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  data_nascimento date NOT NULL,
  email character varying(144) NOT NULL,
  genero integer NOT NULL,
  is_ativo boolean NOT NULL,
  last_login timestamp without time zone,
  login character varying(255),
  nome character varying(50) NOT NULL,
  objetivo character varying(500),
  senha character varying(100),
  token_jwt character varying(500),
  CONSTRAINT pessoa_pkey PRIMARY KEY (id),
  CONSTRAINT uk_csab60w9yycjb127hsdv7wqf5 UNIQUE (login),
  CONSTRAINT uk_mc87q8fpvldpdyfo9o5633o5l UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pessoa
  OWNER TO gym;


  
 CREATE UNIQUE INDEX idx_user_email
   ON "pessoa" (lower(email));

-- Table: auditing.pessoa_audited

-- DROP TABLE auditing.pessoa_audited;

CREATE TABLE auditing.pessoa_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  data_nascimento date,
  email character varying(144),
  genero integer,
  is_ativo boolean,
  last_login timestamp without time zone,
  login character varying(255),
  nome character varying(50),
  objetivo character varying(500),
  senha character varying(100),
  token_jwt character varying(500),
  CONSTRAINT pessoa_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkkdnauw1bst0gotpc94b6a3887 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.pessoa_audited
  OWNER TO gym;

  -- Table: pessoa_papeis

-- DROP TABLE pessoa_papeis;

CREATE TABLE pessoa_papeis
(
  pessoa_id bigint NOT NULL,
  papeis integer NOT NULL,
  CONSTRAINT pessoa_papeis_pkey PRIMARY KEY (pessoa_id, papeis),
  CONSTRAINT fktqv1owkp6wslf4vwpua9tevr2 FOREIGN KEY (pessoa_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pessoa_papeis
  OWNER TO gym;

  
-- Table: auditing.pessoa_papeis_audited

-- DROP TABLE auditing.pessoa_papeis_audited;

CREATE TABLE auditing.pessoa_papeis_audited
(
  revision bigint NOT NULL,
  pessoa_id bigint NOT NULL,
  papeis integer NOT NULL,
  revision_type smallint,
  CONSTRAINT pessoa_papeis_audited_pkey PRIMARY KEY (revision, pessoa_id, papeis),
  CONSTRAINT fk91gw47e45eglu9i1r6x51tvrm FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.pessoa_papeis_audited
  OWNER TO gym;

  
INSERT INTO pessoa(
    id, 
    created, 
    updated, 
    data_nascimento, 
    email, 
    genero, 
    is_ativo, 
    last_login, 
    login, 
    nome, 
    objetivo, 
    senha
)
VALUES (
	1, 
	NOW(), 
	null, 
	now(), 
	'admin@admin.com', 
	0, 
	true, 
    null, 
    'admin', 
    'Administrador', 
    null, 
    '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC'
);
  
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1, 2);

  
ALTER SEQUENCE pessoa_id_seq RESTART WITH 2;    
    
-- INSERT INTO pessoa(
--             id, created, updated, email, is_ativo, last_login, login, nome, 
--             objetivo, papel, password_reset_token, password_reset_token_expiration, 
--             senha, data_nascimento, genero)
--     VALUES (1, NOW() , null, 'admin@admin.com', true, null, 'admin', 'Administrador', 
--             null, 0, null , null, 
--            '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC', now(), 0
--     );

-- senha testeteste    
-- password generated by https://www.dailycred.com/article/bcrypt-calculator

-- select 4

  
-----------------------
-- DEFAULT DATA
-----------------------