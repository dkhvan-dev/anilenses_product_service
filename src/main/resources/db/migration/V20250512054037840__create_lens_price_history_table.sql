create table if not exists lens_price_history(
    id bigserial primary key not null,
    created_at timestamp with time zone not null default now(),
    created_by bigint not null default -10,
    updated_at timestamp with time zone,
    updated_by bigint,
    price decimal(100, 2) not null,
    lens_id bigint not null,
    deleted_at timestamp with time zone,
    is_deleted boolean default false
);

comment on table lens_price_history is 'История изменений цены линз';
comment on column lens_price_history.id is 'ID цены линз';
comment on column lens_price_history.created_at is 'Дата создания цены линз';
comment on column lens_price_history.created_by is 'Автор создания цены линз';
comment on column lens_price_history.updated_at is 'Дата последнего редактирования цены линз';
comment on column lens_price_history.updated_by is 'Автор последнего редактирования цены линз';
comment on column lens_price_history.price is 'Цена линз';
comment on column lens_price_history.lens_id is 'Ссылка на линзу';
comment on column lens_price_history.is_deleted is 'Удалена?';
comment on column lens_price_history.deleted_at is 'Дата удаления';