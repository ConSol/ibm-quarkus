CREATE SEQUENCE app_color_seq_id INCREMENT BY 1 START WITH 1;

CREATE TABLE public.app_color (
    id BIGINT CONSTRAINT app_colorr_pk_id PRIMARY KEY DEFAULT NEXTVAL('app_color_seq_id'),
    hexdec VARCHAR(6) NOT NULL CONSTRAINT app_color_unique_hexdec UNIQUE,
    name VARCHAR(255) NOT NULL CONSTRAINT app_color_unique_name UNIQUE
);

ALTER SEQUENCE app_color_seq_id
    OWNED BY public.app_color.id;