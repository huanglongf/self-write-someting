CREATE TABLE `lb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `plugin_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '插件类型',
  `modified_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `strategy` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '策略',
  `period` int(50) NOT NULL COMMENT '更新周期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pt` (`plugin_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='负载表'






CREATE TABLE `assign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `tid` varchar(100) NOT NULL COMMENT '任务id',
  `modified_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `nodes` varchar(50) NOT NULL COMMENT '部署节点',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tid` (`tid`)
) COMMENT='资源分配表'