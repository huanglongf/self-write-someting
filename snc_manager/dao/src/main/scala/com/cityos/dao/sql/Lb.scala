package com.cityos.dao.sql

/**
 * Created by @author Andy on 2020/12/8 20:43
 * At Jd
 */
trait Lb extends Assign {

  def lb_w(ptype: String, strategy: String, period: Int): String =
    s"""
       |REPLACE INTO `shishi`.`lb` ( `plugin_type`, `strategy`, `period` )
       |VALUES
       |	( '$ptype','$strategy', '$period' );
       |""".stripMargin


  val lb_r =
    s"""
       |SELECT
       |	plugin_type,
       |	strategy,
       |	period
       |FROM
       |	lb
       |""".stripMargin

  val taskUpdate =
    """
      |SELECT
      |	b.kafka_topic,
      |	b.pdk,
      |	b.pre_id as task_id,
      |	b.task_map_api,
      | b.partition_num,
      |	a.group_id,
      |	a.group_ids_used,
      |	a.group_dependencies,
      |	a.group_schedule_typ,
      |	a.group_schedule,
      |	a.update_time,
      |	a.`status`,
      |	a.group_ids_interval,
      |	c.api_id,
      |	c.request_method,
      |	c.url,
      |	c.request_body,
      |	c.data_key,
      |	c.update_time_format,
      |	c.update_time_field,
      |	c.max_length,
      |	c.success_code_field,
      |	c.success_code,
      |	c.total_field,
      |	c.api_out_vars,
      |	c.table_schema,
      |	c.head,
      | c.page_total_field
      |FROM
      |	shishi.interface_info_table a
      |	INNER JOIN (
      |SELECT
      |	m.group_id,
      |	m.pre_id,
      |	m.api_id AS task_map_api,
      |	n.pdk,
      |	n.topic AS kafka_topic,
      | n.partition_num
      |FROM
      |	dcp_pre_api m
      |	INNER JOIN dcp_task_kafka_join n ON m.pre_id = n.pre_id
      |	) b ON a.group_id = b.group_id
      |	INNER JOIN shishi.api_content_info c ON find_in_set( c.api_id, a.group_ids_used )
      |WHERE
      |	a.`status` != '0'
      |GROUP BY
      |	a.group_id,
      |	b.pre_id,
      |	c.api_id,
      |	c.is_encode
      |""".stripMargin

}
