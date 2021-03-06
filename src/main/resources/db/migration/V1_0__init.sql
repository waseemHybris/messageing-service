ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO PUBLIC;

--
-- Name:  message; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE message (
    tenant_id character varying(31) NOT NULL,
    id character varying(255) NOT NULL,
    ts character varying(255) NOT NULL,
    sender character varying(255) NOT NULL,
    message text NOT NULL,
    sent_from_ip character varying(255),
    priority integer
);
