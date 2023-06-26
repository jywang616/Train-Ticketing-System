drop table if exists `city`;
create table `city` (
                          `id` bigint not null comment 'id',
                          `city_name` varchar(11) comment '城市名称',
                          `station` varchar(11) comment '站点',
                          primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='城市';
