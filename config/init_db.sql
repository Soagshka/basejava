create table resume
(
    uuid      char(36) not null primary key,
    full_name text     not null
);

create table contact
(
    id          serial   not null primary key,
    resume_uuid char(36) not null references resume on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

CREATE TABLE public.section
(
    id          integer                                    NOT NULL DEFAULT nextval('section_id_seq'::regclass),
    type        text COLLATE pg_catalog."default"          NOT NULL,
    value       text COLLATE pg_catalog."default"          NOT NULL,
    resume_uuid character(36) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT section_pk PRIMARY KEY (id),
    CONSTRAINT section_resume_uuid_fk FOREIGN KEY (resume_uuid)
        REFERENCES public.resume (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX section_uuid_type_index
    ON public.section USING btree
        (resume_uuid COLLATE pg_catalog."default" ASC NULLS LAST, type COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;





