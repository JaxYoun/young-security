-- warehouse.t_user definition
CREATE TABLE `t_user` (
`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
`name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
`password` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
`enabled` tinyint DEFAULT NULL COMMENT '启用状态',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO warehouse.t_user (name,password,enabled) VALUES
('admin','$2a$10$vY32gHb82Yi4NpMYblH4o.opqrg8NRnyItWfycjI8g3htW6hAVmwm',1),
('yang','$2a$10$SdakkWSAn5xEaBBbMOWqmO0Yi29nlUq9x0eKgmqI49bxrvPViYuru',1);
