create table
    category (
        id varchar(255) not null,
        name varchar(255) not null,
        primary key (id)
    ) engine = InnoDB;

create table
    order_detail (
        order_id varchar(255) not null,
        product_id varchar(255) not null,
        quantity integer not null,
        primary key (order_id, product_id)
    ) engine = InnoDB;

create table
    orders (
        id varchar(255) not null,
        status varchar(255) not null,
        user_id varchar(255) not null,
        billed_date datetime(6) not null,
        primary key (id)
    ) engine = InnoDB;

create table
    permission (
        id varchar(255) not null,
        code varchar(255) not null,
        primary key (id)
    ) engine = InnoDB;

create table
    product (
        id varchar(255) not null,
        name varchar(255) not null,
        category_id varchar(255) not null,
        price bigint not null,
        amount integer not null,
        description varchar(255) not null,
        primary key (id)
    ) engine = InnoDB;

create table
    product_image (
        id varchar(255) not null,
        link varchar(255) not null,
        product_id varchar(255) not null,
        primary key (id)
    ) engine = InnoDB;

create table
    role (
        id varchar(255) not null,
        code varchar(255) not null unique,
        name varchar(255) not null,
        primary key (id)
    ) engine = InnoDB;

create table
    role_detail (
        role_id varchar(255) not null,
        user_id varchar(255) not null,
        primary key (role_id, user_id)
    ) engine = InnoDB;

create table
    role_permission (
        role_id varchar(255) not null,
        permission_id varchar(255) not null,
        primary key (permission_id, role_id)
    ) engine = InnoDB;

create table
    user (
        id varchar(255) not null,
        username varchar(255) not null,
        email varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    ) engine = InnoDB;

alter table permission add constraint UKa7ujv987la0i7a0o91ueevchc unique (code);

alter table user add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

alter table user add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

alter table order_detail add constraint FKrws2q0si6oyd6il8gqe2aennc foreign key (order_id) references orders (id);

alter table order_detail add constraint FKb8bg2bkty0oksa3wiq5mp5qnc foreign key (product_id) references product (id);

alter table orders add constraint FKel9kyl84ego2otj2accfd8mr7 foreign key (user_id) references user (id);

alter table product add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category (id);

alter table product_image add constraint FK6oo0cvcdtb6qmwsga468uuukk foreign key (product_id) references product (id);

alter table role_detail add constraint FKb1vbrf8hys9jcxih691vulahl foreign key (role_id) references role (id);

alter table role_detail add constraint FK3a2s4b9b41jhsl2fjgk2vmmtq foreign key (user_id) references user (id);

alter table role_permission add constraint FKf8yllw1ecvwqy3ehyxawqa1qp foreign key (permission_id) references permission (id);

alter table role_permission add constraint FKa6jx8n8xkesmjmv6jqug6bg68 foreign key (role_id) references role (id);
