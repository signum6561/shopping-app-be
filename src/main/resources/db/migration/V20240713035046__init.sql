create table
    categories (
        id varchar(255) not null,
        category_name varchar(255) not null,
        -- created_at timestamp not null,
        -- updated_at timestamp not null,
        primary key (id)
    ) engine = InnoDB;

create table
    orders (
        id varchar(255) not null,
        billed_date datetime not null,
        status varchar(255) not null,
        user_id varchar(255) not null,
        -- created_at timestamp not null,
        -- updated_at timestamp not null,
        primary key (id)
    ) engine = InnoDB;

create table
    order_details (
        order_id varchar(255) not null,
        product_id varchar(255) not null,
        quantity integer not null,
        primary key (order_id, product_id)
    ) engine = InnoDB;

create table
    products (
        id varchar(255) not null,
        product_name varchar(255) not null,
        amount integer not null,
        price bigint not null,
        category_id varchar(255) not null,
        description TEXT not null,
        -- created_at timestamp not null,
        -- updated_at timestamp not null,
        primary key (id)
    ) engine = InnoDB;

create table
    product_images (
        id varchar(255) not null,
        product_id varchar(255) not null,
        link varchar(255) not null,
        primary key (id)
    ) engine = InnoDB;

create table
    roles (
        id varchar(255) not null,
        role_name varchar(255) not null,
        -- created_at timestamp not null,
        -- updated_at timestamp not null,
        primary key (id)
    ) engine = InnoDB;

create table
    role_details (
        role_id varchar(255) not null,
        user_id varchar(255) not null,
        primary key (role_id, user_id)
    ) engine = InnoDB;

create table
    users (
        id varchar(255) not null,
        email varchar(255) not null unique,
        username varchar(255) not null unique,
        password varchar(255) not null,
        -- created_at timestamp not null,
        -- updated_at timestamp not null,
        primary key (id)
    ) engine = InnoDB;

alter table orders add constraint FKt7abetueht6dd1gs9jyl3o4t7 foreign key (user_id) references users (id);

alter table order_details add constraint FKplam7wxc4tjbgex0xyk8f0qxo foreign key (order_id) references orders (id);

alter table order_details add constraint FKb8bg2bkty0oksa3wiq5mp5qnc foreign key (product_id) references products (id);

alter table products add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references categories (id);

alter table product_images add constraint FK6oo0cvcdtb6qmwsga468uuukk foreign key (product_id) references products (id);

alter table role_details add constraint FKb1vbrf8hys9jcxih691vulahl foreign key (role_id) references roles (id);

alter table role_details add constraint FK3a2s4b9b41jhsl2fjgk2vmmtq foreign key (user_id) references users (id);