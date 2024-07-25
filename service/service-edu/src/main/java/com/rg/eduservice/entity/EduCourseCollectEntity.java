package com.rg.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程收藏
 * 
 * @author Leo
 * @email 2422737092@qq.com
 * @date 2024-07-25 09:41:19
 */
@Data
@TableName("edu_course_collect")
public class EduCourseCollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 收藏ID
	 */
	@TableId(value = "id", type = IdType.ID_WORKER_STR)
	private String id;
	/**
	 * 课程讲师ID
	 */
	private String courseId;
	/**
	 * 课程专业ID
	 */
	private String memberId;
	/**
	 * 逻辑删除 1（true）已删除， 0（false）未删除
	 */
	private Integer isDeleted;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date gmtCreate;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date gmtModified;

}
