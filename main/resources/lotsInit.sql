SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lots_menu
-- ----------------------------
DROP TABLE IF EXISTS `lots_menu`;
CREATE TABLE `lots_menu`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`   bigint NULL DEFAULT NULL COMMENT '父级ID',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `title`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
    `level`       int NULL DEFAULT NULL COMMENT '菜单级数',
    `sort`        int NULL DEFAULT NULL COMMENT '菜单排序',
    `name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端名称',
    `icon`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端图标',
    `hidden`      int NULL DEFAULT NULL COMMENT '前端隐藏',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_menu
-- ----------------------------
INSERT INTO `lots_menu`
VALUES (1, 0, '2020-02-07 16:29:13', '权限', 0, 0, 'ums', 'ums', 0);
INSERT INTO `lots_menu`
VALUES (2, 1, '2020-02-07 16:29:51', '用户列表', 1, 0, 'admin', 'ums-admin', 0);
INSERT INTO `lots_menu`
VALUES (3, 1, '2020-02-07 16:30:13', '角色列表', 1, 0, 'role', 'ums-role', 0);
INSERT INTO `lots_menu`
VALUES (4, 1, '2020-02-07 16:30:53', '菜单列表', 1, 0, 'menu', 'ums-menu', 0);
INSERT INTO `lots_menu`
VALUES (5, 1, '2020-02-07 16:31:13', '资源列表', 1, 0, 'resource', 'ums-resource', 0);

-- ----------------------------
-- Table structure for lots_resource
-- ----------------------------
DROP TABLE IF EXISTS `lots_resource`;
CREATE TABLE `lots_resource`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `name`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
    `url`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源URL',
    `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `category_id` bigint NULL DEFAULT NULL COMMENT '资源分类ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_resource
-- ----------------------------
INSERT INTO `lots_resource`
VALUES (1, '2021-05-10 16:58:21', '后台用户管理', '/admin/**', '', 1);
INSERT INTO `lots_resource`
VALUES (2, '2021-05-10 16:58:21', '后台用户角色管理', '/role/**', '', 1);
INSERT INTO `lots_resource`
VALUES (3, '2021-05-10 16:58:21', '后台菜单管理', '/menu/**', '', 1);
INSERT INTO `lots_resource`
VALUES (4, '2021-05-10 16:58:21', '后台资源分类管理', '/resourceCategory/**', '', 1);
INSERT INTO `lots_resource`
VALUES (5, '2021-05-10 16:58:21', '后台资源管理', '/resource/**', '', 1);
INSERT INTO `lots_resource`
VALUES (6, '2021-05-10 16:58:21', 'test', '/test/**', '测试接口', 3);

-- ----------------------------
-- Table structure for lots_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `lots_resource_category`;
CREATE TABLE `lots_resource_category`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `name`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
    `sort`        int NULL DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_resource_category
-- ----------------------------
INSERT INTO `lots_resource_category`
VALUES (1, '2021-05-10 16:59:29', '权限模块', 0);
INSERT INTO `lots_resource_category`
VALUES (2, '2021-05-10 16:59:29', '内容模块', 0);
INSERT INTO `lots_resource_category`
VALUES (3, '2021-05-10 16:59:29', '其他模块', 0);

-- ----------------------------
-- Table structure for lots_role
-- ----------------------------
DROP TABLE IF EXISTS `lots_role`;
CREATE TABLE `lots_role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
    `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `admin_count` int NULL DEFAULT NULL COMMENT '后台用户数量',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `status`      int NULL DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
    `sort`        int NULL DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_role
-- ----------------------------
INSERT INTO `lots_role`
VALUES (1, '超级管理员', '拥有所有查看和操作功能', 0, '2021-05-10 16:55:28', 1, 0);
INSERT INTO `lots_role`
VALUES (2, '普通用户', '普通用户，有普通的查看权限', 0, '2021-05-10 16:55:29', 1, 0);

-- ----------------------------
-- Table structure for lots_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `lots_role_menu_relation`;
CREATE TABLE `lots_role_menu_relation`
(
    `id`      bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
    `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 176 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_role_menu_relation
-- ----------------------------
INSERT INTO `lots_role_menu_relation`
VALUES (1, 1, 1);
INSERT INTO `lots_role_menu_relation`
VALUES (2, 1, 2);
INSERT INTO `lots_role_menu_relation`
VALUES (3, 1, 3);
INSERT INTO `lots_role_menu_relation`
VALUES (4, 1, 4);
INSERT INTO `lots_role_menu_relation`
VALUES (5, 1, 5);

-- ----------------------------
-- Table structure for lots_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `lots_role_resource_relation`;
CREATE TABLE `lots_role_resource_relation`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`     bigint NULL DEFAULT NULL COMMENT '角色ID',
    `resource_id` bigint NULL DEFAULT NULL COMMENT '资源ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 209 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台角色资源关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_role_resource_relation
-- ----------------------------
INSERT INTO `lots_role_resource_relation`
VALUES (1, 1, 1);
INSERT INTO `lots_role_resource_relation`
VALUES (2, 1, 2);
INSERT INTO `lots_role_resource_relation`
VALUES (3, 1, 3);
INSERT INTO `lots_role_resource_relation`
VALUES (4, 1, 4);
INSERT INTO `lots_role_resource_relation`
VALUES (5, 1, 5);
INSERT INTO `lots_role_resource_relation`
VALUES (6, 1, 6);

-- ----------------------------
-- Table structure for lots_server_exception_log
-- ----------------------------
DROP TABLE IF EXISTS `lots_server_exception_log`;
CREATE TABLE `lots_server_exception_log`
(
    `logid`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志id',
    `ipaddress`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
    `port`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口号',
    `exceptionname`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '异常名称',
    `content`          longblob NULL COMMENT '异常内容',
    `createtime`       datetime NULL DEFAULT NULL COMMENT '创建时间',
    `syspath`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统上下文',
    `clientip`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端ip',
    `url`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
    `menuid`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单id',
    `menuname`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
    `useragent`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `exceptiontype`    varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常类型',
    `requestparameter` longblob NULL COMMENT '请求参数',
    PRIMARY KEY (`logid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '服务器异常日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lots_server_exception_log
-- ----------------------------

-- ----------------------------
-- Table structure for lots_user
-- ----------------------------
DROP TABLE IF EXISTS `lots_user`;
CREATE TABLE `lots_user`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
    `phone`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
    `id_card`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
    `icon`        varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
    `email`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `nick_name`   varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `note`        varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `login_time`  datetime NULL DEFAULT NULL COMMENT '最后登录时间',
    `status`      int NULL DEFAULT 1 COMMENT '帐号启用状态：0->禁用；1->启用',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE COMMENT '用户名唯一索引',
    UNIQUE KEY `uk_email` (`email`) USING BTREE COMMENT '邮箱地址唯一索引',
    UNIQUE KEY `uk_phone` (`phone`) USING BTREE COMMENT '手机号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_user
-- ----------------------------
INSERT INTO `lots_user`
VALUES (1, 'lots', '$2a$10$63bXv3XYEK6I5AydNc8sBOoi3qIgfpMBW.0tcr2LGEQBRqoVwQXcG', '17723542494', NULL, NULL,
        '3434757230@qq.com', NULL, '备注', '2021-05-07 18:11:28', NULL, 1);
INSERT INTO `lots_user`
VALUES (2, 'admin', '$2a$10$GB9QPypyw0gDVmDMPhVJgOcMEGldeCREXkkoYKmNKuDl/MKFZnRJ6', '17723542493', NULL, NULL,
        '3434757231@qq.com', NULL, '管理员', '2021-05-07 18:11:45', NULL, 1);

-- ----------------------------
-- Table structure for lots_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `lots_user_login_log`;
CREATE TABLE `lots_user_login_log`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     bigint NULL DEFAULT NULL COMMENT '用户ID',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `ip`          varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP',
    `address`     varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
    `user_agent`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器登录类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 233 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_user_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for lots_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `lots_user_role_relation`;
CREATE TABLE `lots_user_role_relation`
(
    `id`      bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
    `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户和角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lots_user_role_relation
-- ----------------------------
INSERT INTO `lots_user_role_relation`
VALUES (1, 1, 1);
INSERT INTO `lots_user_role_relation`
VALUES (2, 2, 1);

SET
FOREIGN_KEY_CHECKS = 1;
