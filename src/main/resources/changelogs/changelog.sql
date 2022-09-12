create table "user"
(
    "id"                   bigserial not null,
    "email"                varchar(255),
    "password"             varchar(255),
    "profile_picture_path" varchar(255),
    "role"                 varchar(255),
    "username"             varchar(255),
    primary key ("id")
);
create table "articles"
(
    "id"               bigserial not null,
    "article_headline" varchar(255),
    "caption"          varchar(255),
    "content"          varchar(255),
    "location"         varchar(255),
    "picture"          varchar(255),
    "subcategory"      varchar(255),
    "author_id"        int8,
    primary key ("id")
);
create table "comments"
(
    "id"            int8 not null,
    "created_at"    timestamp,
    "text"          varchar(255),
    "updated_at"    timestamp,
    "article_id"    int8,
    "created_by_id" int8,
    primary key ("id")
);
alter table "articles"
    add constraint "FKiujgap5iw44w7ibck7e62fo2h" foreign key ("author_id") references "user";
alter table "comments"
    add constraint "FKt3o4cckaf1q274ijgeihggf" foreign key ("article_id") references "articles";
alter table "comments"
    add constraint "FK2pevlmsa4khk16hs2iah5ynj2" foreign key ("created_by_id") references "user";
