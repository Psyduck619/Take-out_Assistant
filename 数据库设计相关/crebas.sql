/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/3 15:49:18                            */
/*==============================================================*/


drop table if exists getCoupon;

drop table if exists orderInfo;

drop table if exists goodsComment;

drop table if exists tbl_address;

drop table if exists tbl_admin;

drop table if exists tbl_coupon;

drop table if exists tbl_manjian;

drop table if exists tbl_goodsOrder;

drop table if exists tbl_goods;

drop table if exists tbl_goodsType;

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
   user_id              varchar(20) not null,
   coupon_id            varchar(20) not null,
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
   order_id             varchar(20) not null,
   goods_id               varchar(20) not null,
   goods_quantity         int not null,
   order_price          float(10) not null,
   per_discount         float(20) not null,
   primary key (order_id, goods_id)
);

/*==============================================================*/
/* Table: goodsComment                                        */
/*==============================================================*/
create table goodsComment
(
   goods_id               varchar(20) not null,
   user_id              varchar(20) not null,
   com_content          varchar(20) not null,
   com_date             date not null,
   com_level            int not null,
   com_picture          longblob not null,
   primary key (goods_id, user_id)
);

/*==============================================================*/
/* Table: tbl_address                                           */
/*==============================================================*/
create table tbl_address
(
   add_id               varchar(20) not null,
   user_id              varchar(20),
   address              varchar(60) not null,
   linkMan              varchar(20) not null,
   linkPhone            int not null,
   primary key (add_id)
);

/*==============================================================*/
/* Table: tbl_admin                                             */
/*==============================================================*/
create table tbl_admin
(
   admin_id             varchar(20) not null,
   admin_name           varchar(20) not null,
   admin_pwd            varchar(20) not null,
   primary key (admin_id)
);

/*==============================================================*/
/* Table: tbl_coupon                                            */
/*==============================================================*/
create table tbl_coupon
(
   coupon_id            varchar(20) not null,
   seller_id            varchar(20),
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
   manjian_id           varchar(20) not null,
   seller_id            varchar(20),
   manjian_amount       int not null,
   discount_amount      int not null,
   ifTogether           bool,
   primary key (manjian_id)
);

/*==============================================================*/
/* Table: tbl_goodsOrder                                          */
/*==============================================================*/
create table tbl_goodsOrder
(
   order_id             varchar(20) not null,
   user_id              varchar(20),
   account_id           varchar(20),
   seller_id            varchar(20),
   rider_id             varchar(20),
   original_price       float(20) not null,
   final_price          float(20) not null,
   manji2an_id          varchar(20),
   cou2pon              varchar(20),
   order_time           time not null,
   request_time         time,
   add_id2              varchar(20) not null,
   order_state          varchar(20) not null,
   primary key (order_id)
);

/*==============================================================*/
/* Table: tbl_goods                                           */
/*==============================================================*/
create table tbl_goods
(
   goods_id               varchar(20) not null,
   type_id              varchar(20),
   goods_type             varchar(20) not null,
   goods_name             varchar(20) not null,
   price                float,
   discount_price       float,
   primary key (goods_id)
);

/*==============================================================*/
/* Table: tbl_goodsType                                       */
/*==============================================================*/
create table tbl_goodsType
(
   type_id              varchar(20) not null,
   seller_id            varchar(20),
   type_name            varchar(20) not null,
   quantity             int not null,
   primary key (type_id)
);

/*==============================================================*/
/* Table: tbl_rider                                             */
/*==============================================================*/
create table tbl_rider
(
   rider_id             varchar(20) not null,
   rider_name           varchar(20) not null,
   entry_date           date not null,
   rider_status         varchar(20),
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
   seller_id            varchar(20) not null,
   seller_name          varchar(20) not null,
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
   user_id              varchar(20) not null,
   user_name            varchar(20) not null,
   user_gender          bool not null,
   user_pwd             varchar(20) not null,
   user_phone           int not null,
   user_email           varchar(20),
   user_city            varchar(20),
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
   user_id              varchar(20) not null,
   seller_id            varchar(20) not null,
   coupon_request       int not null,
   coupon_get           int not null,
   primary key (user_id, seller_id)
);

alter table getCoupon add constraint FK_getCoupon foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table getCoupon add constraint FK_getCoupon2 foreign key (coupon_id)
      references tbl_coupon (coupon_id) on delete restrict on update restrict;

alter table orderInfo add constraint FK_orderInfo foreign key (order_id)
      references tbl_goodsOrder (order_id) on delete restrict on update restrict;

alter table orderInfo add constraint FK_orderInfo2 foreign key (goods_id)
      references tbl_goods (goods_id) on delete restrict on update restrict;

alter table goodsComment add constraint FK_goodsComment foreign key (goods_id)
      references tbl_goods (goods_id) on delete restrict on update restrict;

alter table goodsComment add constraint FK_goodsComment2 foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table tbl_address add constraint FK_拥有 foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table tbl_coupon add constraint FK_提供 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table tbl_manjian add constraint FK_发布2 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table tbl_goodsOrder add constraint FK_下单 foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table tbl_goodsOrder add constraint FK_接单 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table tbl_goodsOrder add constraint FK_有 foreign key (account_id)
      references tbl_riderAccount (account_id) on delete restrict on update restrict;

alter table tbl_goodsOrder add constraint FK_配送 foreign key (rider_id)
      references tbl_rider (rider_id) on delete restrict on update restrict;

alter table tbl_goods add constraint FK_包含 foreign key (type_id)
      references tbl_goodsType (type_id) on delete restrict on update restrict;

alter table tbl_goodsType add constraint FK_公布 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

alter table userJidan add constraint FK_userJidan foreign key (user_id)
      references tbl_user (user_id) on delete restrict on update restrict;

alter table userJidan add constraint FK_userJidan2 foreign key (seller_id)
      references tbl_seller (seller_id) on delete restrict on update restrict;

