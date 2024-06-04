CREATE TABLE IF NOT EXISTS product
(
    product_id BIGSERIAL NOT NULL,
    name character varying(200) COLLATE pg_catalog."default",
    price real,
    added_at timestamp without time zone,
    labels character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT product_id_pkey PRIMARY KEY (product_id),
    CONSTRAINT product_name_ukey UNIQUE (name)
    );

CREATE TABLE IF NOT EXISTS cart
(
    cart_id BIGSERIAL NOT NULL,
    checked_out boolean,
    total_cost real,
    CONSTRAINT cart_id_pkey PRIMARY KEY (cart_id)
);

CREATE TABLE IF NOT EXISTS product_info
(
    product_id bigint NOT NULL,
    cart_id bigint NOT NULL,
    CONSTRAINT product_id_fkey FOREIGN KEY (product_id)
    REFERENCES public.product (product_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    quantity real,
    CONSTRAINT cart_id_fkey FOREIGN KEY (cart_id)
    REFERENCES public.cart (cart_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );
