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
    id          serial        NOT NULL primary key,
    resume_uuid character(36) NOT NULL references resume on delete cascade,
    type        text          NOT NULL,
    value       text          NOT NULL
);

CREATE UNIQUE INDEX section_uuid_type_index
    ON section (resume_uuid, type);





