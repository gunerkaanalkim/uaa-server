create table permission
(
    id                 bigint auto_increment
        primary key,
    created_by         varchar(255) null,
    created_date       datetime(6) null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime(6) null,
    controller         varchar(255) null,
    controller_alias   varchar(255) null,
    description        varchar(255) null,
    permission_name    varchar(255) null
);

create table role
(
    id                 bigint auto_increment
        primary key,
    created_by         varchar(255) null,
    created_date       datetime(6) null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime(6) null,
    code               varchar(255) null,
    name               varchar(255) null
);

create table role_permission
(
    id                 bigint auto_increment
        primary key,
    created_by         varchar(255) null,
    created_date       datetime(6) null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime(6) null,
    permission_id      bigint null,
    role_id            bigint null,
    constraint FKa6jx8n8xkesmjmv6jqug6bg68
        foreign key (role_id) references role (id),
    constraint FKf8yllw1ecvwqy3ehyxawqa1qp
        foreign key (permission_id) references permission (id)
);

create table users
(
    id                 bigint auto_increment
        primary key,
    created_by         varchar(255) null,
    created_date       datetime(6) null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime(6) null,
    email              varchar(255) null,
    name               varchar(255) null,
    password           varchar(255) null,
    surname            varchar(255) null,
    username           varchar(255) null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table role_user
(
    id                 bigint auto_increment
        primary key,
    created_by         varchar(255) null,
    created_date       datetime(6) null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime(6) null,
    role_id            bigint null,
    user_id            bigint null,
    constraint FKhvai2k29vlwpt9wod4sw4ghmn
        foreign key (user_id) references users (id),
    constraint FKiqpmjd2qb4rdkej916ymonic6
        foreign key (role_id) references role (id)
);
