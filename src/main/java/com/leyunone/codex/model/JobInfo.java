package com.leyunone.codex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo {

	/**
	 * 任务id
	 */
	private Integer id;

	/**
	 * 任务描述
	 */
	private String jobDesc;

	/**
	 * 任务添加时间
	 */
	private LocalDateTime createTime;

	/**
	 * 任务更新时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 任务负责人
	 */
	private String author;

	/**
	 * 调度类型
	 */
	private String scheduleType;

	/**
	 * 调度时间
	 */
	private String scheduleTime;

	/**
	 * 调度状态：0-停止，1-运行
	 */
	private int status;

}