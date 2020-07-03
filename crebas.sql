/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/3 15:49:18                            */
/*==============================================================*/


drop table if exists getCoupon;

drop table if exists orderInfo;

drop table if exists productComment;

drop table if exists tbl_address;

drop table if exists tbl_admin;

drop table if exists tbl_coupon;

drop table if exists tbl_manjian;

drop table if exists tbl_proOrder;

drop table if exists tbl_product;

drop table if exists tbl_productType;

drop table if exists tbl_rider;

drop table if exists tbl_riderAccount;

drop table if exists tbl_seller;

drop table if exists tbl_user;

drop table if exists userJidan;

/*==============================================================*/
/* Table: getCoupon                                             */
/*==============================================================*/
create table getCoupon
(
   user_id              varchar(50) not null,
   coupon_id            varchar(50) not null,
   coupon_amount        int not null,
   coupon_count         int not null,
   end_date             date not null,
   primary key (user_id, coupon_id)
);

/*==============================================================*/
/* Table: orderInfo                                             */
/*==============================================================*/
create table orderInfo
(
   order_id             varchar(50) not null,
   pro_id               varchar(50) not null,
   pro_quantity         int not null,
   order_price          float(255) not null,
   per_discount         float(50) not null,
   primary key (order_id, pro_id)
);

/*==============================================================*/
/* Table: productComment                                        */
/*==============================================================*/
create table productComment
(
   pro_id               varchar(50) not null,
   user_id              varchar(50) not null,
   com_content          varchar(50) not null,
   com_date             date not null,
   com_level            int not null,
   com_picture          longblob not null,
   primary key (pro_id, user_id)
);

/*==============================================================*/
/* Table: tbl_address                                           */
/*==============================================================*/
create table tbl_address
(
   add_id               varchar(20) not null,
   user_id              varchar(50),
   address              varchar(255) not null,
   linkMan              varchar(20) not null,
   linkPhone            int not null,
   primary key (add_id)
);

/*==============================================================*/
/* Table: tbl_admin                                             */
/*==============================================================*/
create table tbl_admin
(
   admin_id             varchar(50) not null,
   admin_name           varchar(50) not null,
   admin_pwd            varchar(50) not null,
   primary key (admin_id)
);

/*==============================================================*/
/* Table: tbl_coupon                                            */
/*==============================================================*/
create table tbl_coupon
(
   coupon_id            varchar(50) not null,
   seller_id            varchar(50),
   coupon_amount        int not null,
   coupon_request       int not null,
   begin_date           date,
   end_date             date,
   primary key (coupon_id)
);

/*==============================================================*/
/* Table: tbl_manjian                                           */
/*==============================================================*/
create table tbl_manjian
(
   manjian_id           varchar(50) not null,
   seller_id            varchar(50),
   manjian_amount       int not null,
   discount_amount      int not null,
   ifTogether           bool,
   primary key (manjian_id)
);

/*==============================================================*/
/* Table: tbl_proOrder                                          */
/*==============================================================*/
create table tbl_proOrder
(
   order_id             varchar(50) not null,
   user_id              varchar(50),
   account_id           varchar(20),
   seller_id            varchar(50),
   rider_id             varchar(50),
   original_price       float(50) not null,
   final_price          float(50) not null,
   manji2an_id          varchar(50),
   cou2pon              varchar(50),
   order_time           time not null,
   request_time         time,
   add_id2              varchar(50) not null,
   order_state          varchar(50) not null,
   primary key (order_id)
);

/*==============================================================*/
/* Table: tbl_product                                           */
/*==============================================================*/
create table tbl_product
(
   pro_id               varchar(50) not null,
   type_id              varchar(50),
   pro_type             varchar(50) not null,
   pro_name             varchar(50) not null,
   price                float,
   discount_price       float,
   primary key (pro_id)
);

/*==============================================================*/
/* Table: tbl_productType                                       */
/*==============================================================*/
create table tbl_productType
(
   type_id              varchar(50) not null,
   seller_id            varchar(50),
   type_name            varchar(50) not null,
   quantity             int not null,
   primary key (type_id)
);

/*==============================================================*/
/* Table: tbl_rider                                             */
/*==============================================================*/
create table tbl_rider
(
   rider_id             varchar(50) not null,
   rider_name           varchar(50) not null,
   entry_date           date not null,
   rider_status         varchar(50),
   primary key (rider_id)
);

/*==============================================================*/
/* Table: tbl_riderAccount                                      */
/*==============================================================*/
create table tbl_riderAccount
(
   account_id           varchar(20) not null,
   finish_time          datetime,
   order_comment        varchar(255),
   per_income           float,
   rider_id6            varchar(20),
   primary key (account_id)
);

/*==============================================================*/
/* Table: tbl_seller                                            */
/*==============================================================*/
create table tbl_seller
(
   seller_id            varchar(50) not null,
   seller_name          varchar(50) not null,
   seller_star          int not null,
   per_cost             float not null,
   total_sales          int not null,
   primary key (seller_id)
);

/*==============================================================*/
/* Table: tbl_user                                              */
/*==============================================================*/
create table tbl_user
(
   user_id              varchar(50) not null,
   user_name            varchar(50) not null,
   user_gender          bool not null,
   user_pwd             varchar(20) not null,
   user_phone           int not null,
   user_email           varchar(50),
   user_city            varchar(50),
   user_reg_time        time not null,
   isVIP                bool not null,
   VIP_end_time         date not null,
   primary key (user_id)
);

/*==============================================================*/
/* Table: userJidan                                             */
/*==============================================================*/
create table userJidan
(
   user_id              varchar(50) not null,
   seller_id            varchar(50) not null,
   coupon_request       int not null,
   coupon_get           int not null,
   primary key (user_id, seller_id)
);

alter table getCoupon add constraint FK_getCoupon foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table getCoupon add constraint FK_getCoupon2 foreign key (coupon_id)
      references tbl_coupon (coupon_id) on delete restrict on update restrict;

alter table orderInfo add constraint FK_orderInfo foreign key (order_id)
      references tbl_proOrder (order_id) on delete restrict on update restrict;

alter table orderInfo add constraint FK_orderInfo2 foreign key (pro_id)
      references tbl_product (pro_id) on delete restrict on update restrict;

alter table productComment add constraint FK_productComment foreign key (pro_id)
      references tbl_product (pro_id) on delete restrict on update restrict;

alter table productComment add constraint FK_productComment2 foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table tbl_address add constraint FK_拥有 foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table tbl_coupon add constraint FK_提供 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table tbl_manjian add constraint FK_发布2 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table tbl_proOrder add constraint FK_下单 foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table tbl_proOrder add constraint FK_接单 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table tbl_proOrder add constraint FK_有 foreign key (account_id)
      references tbl_riderAccount (account_id) on delete restrict on update restrict;

alter table tbl_proOrder add constraint FK_配送 foreign key (rider_id)
      references tbl_rider (rider_id) on delete restrict on update restrict;

alter table tbl_product add constraint FK_包含 foreign key (type_id)
      references tbl_productType (type_id) on delete restrict on update restrict;

alter table tbl_productType add constraint FK_公布 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table userJidan add constraint FK_userJidan foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table userJidan add constraint FK_userJidan2 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

