--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET row_security = OFF;

--
-- Name: gluu_log; Type: DATABASE; Schema: -; Owner: root
--

CREATE DATABASE gluu_log WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


ALTER DATABASE gluu_log
OWNER TO root;

\connect gluu_log

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET row_security = OFF;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: gluu
--

CREATE SEQUENCE hibernate_sequence
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO gluu;

SET default_tablespace = '';

SET default_with_oids = FALSE;

--
-- Name: oauth2_audit_logging_event; Type: TABLE; Schema: public; Owner: gluu
--

CREATE TABLE oauth2_audit_logging_event (
  id          BIGINT NOT NULL,
  action      CHARACTER VARYING(255),
  client_id   CHARACTER VARYING(255),
  ip          CHARACTER VARYING(255),
  mac_address CHARACTER VARYING(255),
  scope       CHARACTER VARYING(255),
  success     BOOLEAN,
  "timestamp" TIMESTAMP WITHOUT TIME ZONE,
  username    CHARACTER VARYING(255)
);


ALTER TABLE oauth2_audit_logging_event OWNER TO gluu;

--
-- Name: oxauth_server_logging_event; Type: TABLE; Schema: public; Owner: gluu
--

CREATE TABLE oxauth_server_logging_event (
  id                BIGINT NOT NULL,
  formatted_message TEXT,
  level             CHARACTER VARYING(255),
  logger_name       CHARACTER VARYING(255),
  "timestamp"       TIMESTAMP WITHOUT TIME ZONE
);


ALTER TABLE oxauth_server_logging_event OWNER TO gluu;

--
-- Name: oxauth_server_logging_event_exception; Type: TABLE; Schema: public; Owner: gluu
--

CREATE TABLE oxauth_server_logging_event_exception (
  id                             BIGINT NOT NULL,
  index                          INTEGER,
  trace_line                     TEXT,
  oxauth_server_logging_event_id BIGINT
);


ALTER TABLE oxauth_server_logging_event_exception OWNER TO gluu;

--
-- Name: oauth2_audit_logging_event_pkey; Type: CONSTRAINT; Schema: public; Owner: gluu
--

ALTER TABLE ONLY oauth2_audit_logging_event
ADD CONSTRAINT oauth2_audit_logging_event_pkey PRIMARY KEY (id);

--
-- Name: oxauth_server_logging_event_exception_pkey; Type: CONSTRAINT; Schema: public; Owner: gluu
--

ALTER TABLE ONLY oxauth_server_logging_event_exception
ADD CONSTRAINT oxauth_server_logging_event_exception_pkey PRIMARY KEY (id);

--
-- Name: oxauth_server_logging_event_pkey; Type: CONSTRAINT; Schema: public; Owner: gluu
--

ALTER TABLE ONLY oxauth_server_logging_event
ADD CONSTRAINT oxauth_server_logging_event_pkey PRIMARY KEY (id);

--
-- Name: oauth2_audit_logging_event_timestamp; Type: INDEX; Schema: public; Owner: gluu
--

CREATE INDEX oauth2_audit_logging_event_timestamp ON oauth2_audit_logging_event USING BTREE ("timestamp");

--
-- Name: oxauth_server_logging_event_timestamp; Type: INDEX; Schema: public; Owner: gluu
--

CREATE INDEX oxauth_server_logging_event_timestamp ON oxauth_server_logging_event USING BTREE ("timestamp");

--
-- Name: fktp5p28uolrsx6vlj6annm7255; Type: FK CONSTRAINT; Schema: public; Owner: gluu
--

ALTER TABLE ONLY oxauth_server_logging_event_exception
ADD CONSTRAINT fktp5p28uolrsx6vlj6annm7255 FOREIGN KEY (oxauth_server_logging_event_id) REFERENCES oxauth_server_logging_event (id);

--
-- Name: public; Type: ACL; Schema: -; Owner: root
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM root;
GRANT ALL ON SCHEMA public TO root;
GRANT ALL ON SCHEMA public TO PUBLIC;

--
-- PostgreSQL database dump complete
--

