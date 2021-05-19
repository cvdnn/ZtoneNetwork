package com.dingtalk.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.edu.course.list response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiEduCourseListResponse extends TaobaoResponse {

	private static final long serialVersionUID = 4763274779892681246L;

	/** 
	 * 错误编码
	 */
	@ApiField("errcode")
	private Long errcode;

	/** 
	 * 错误信息
	 */
	@ApiField("errmsg")
	private String errmsg;

	/** 
	 * result
	 */
	@ApiField("result")
	private ListCourseResponse result;

	/** 
	 * 请求是否成功
	 */
	@ApiField("success")
	private Boolean success;


	public void setErrcode(Long errcode) {
		this.errcode = errcode;
	}
	public Long getErrcode( ) {
		return this.errcode;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getErrmsg( ) {
		return this.errmsg;
	}

	public void setResult(ListCourseResponse result) {
		this.result = result;
	}
	public ListCourseResponse getResult( ) {
		return this.result;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean getSuccess( ) {
		return this.success;
	}

	public boolean isSuccess() {
        return getErrcode() == null || getErrcode().equals(0L);
    }
	
	/**
	 * list
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class CourseVO extends TaobaoObject {
		private static final long serialVersionUID = 3789273359373881663L;
		/**
		 * 业务唯一键
		 */
		@ApiField("biz_key")
		private String bizKey;
		/**
		 * 课程编码
		 */
		@ApiField("code")
		private String code;
		/**
		 * 结束时间，Unix毫秒时间戳
		 */
		@ApiField("end_time")
		private Long endTime;
		/**
		 * 课程介绍
		 */
		@ApiField("introduce")
		private String introduce;
		/**
		 * 课程名称
		 */
		@ApiField("name")
		private String name;
		/**
		 * 开始时间，Unix毫秒时间戳
		 */
		@ApiField("start_time")
		private Long startTime;
		/**
		 * 老的的组织CorpId
		 */
		@ApiField("teacher_corpid")
		private String teacherCorpid;
		/**
		 * 老师的用户ID
		 */
		@ApiField("teacher_userid")
		private String teacherUserid;
	
		public String getBizKey() {
			return this.bizKey;
		}
		public void setBizKey(String bizKey) {
			this.bizKey = bizKey;
		}
		public String getCode() {
			return this.code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Long getEndTime() {
			return this.endTime;
		}
		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
		public String getIntroduce() {
			return this.introduce;
		}
		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getStartTime() {
			return this.startTime;
		}
		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}
		public String getTeacherCorpid() {
			return this.teacherCorpid;
		}
		public void setTeacherCorpid(String teacherCorpid) {
			this.teacherCorpid = teacherCorpid;
		}
		public String getTeacherUserid() {
			return this.teacherUserid;
		}
		public void setTeacherUserid(String teacherUserid) {
			this.teacherUserid = teacherUserid;
		}
	}
	
	/**
	 * result
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class ListCourseResponse extends TaobaoObject {
		private static final long serialVersionUID = 5437526728489722984L;
		/**
		 * 是否还有更多数据
		 */
		@ApiField("has_more")
		private Boolean hasMore;
		/**
		 * list
		 */
		@ApiListField("list")
		@ApiField("course_v_o")
		private List<CourseVO> list;
		/**
		 * 表示下一次分页的游标，如果next_corsor为null或者has_more为false，表示没有更多的分页数据
		 */
		@ApiField("next_cursor")
		private Long nextCursor;
	
		public Boolean getHasMore() {
			return this.hasMore;
		}
		public void setHasMore(Boolean hasMore) {
			this.hasMore = hasMore;
		}
		public List<CourseVO> getList() {
			return this.list;
		}
		public void setList(List<CourseVO> list) {
			this.list = list;
		}
		public Long getNextCursor() {
			return this.nextCursor;
		}
		public void setNextCursor(Long nextCursor) {
			this.nextCursor = nextCursor;
		}
	}
	


}
