package com.dingtalk.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.edu.family.child.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiEduFamilyChildGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 2425853766242674848L;

	/** 
	 * 错误代码
	 */
	@ApiField("errcode")
	private Long errcode;

	/** 
	 * 错误信息
	 */
	@ApiField("errmsg")
	private String errmsg;

	/** 
	 * 结果数据
	 */
	@ApiField("result")
	private ChildDto result;

	/** 
	 * 是否成功
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

	public void setResult(ChildDto result) {
		this.result = result;
	}
	public ChildDto getResult( ) {
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
	 * 孩子信息
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class BindStudent extends TaobaoObject {
		private static final long serialVersionUID = 1229616938351562165L;
		/**
		 * 班级ID
		 */
		@ApiField("class_id")
		private String classId;
		/**
		 * 学校CorpId
		 */
		@ApiField("corp_id")
		private String corpId;
	
		public String getClassId() {
			return this.classId;
		}
		public void setClassId(String classId) {
			this.classId = classId;
		}
		public String getCorpId() {
			return this.corpId;
		}
		public void setCorpId(String corpId) {
			this.corpId = corpId;
		}
	}
	
	/**
	 * 结果数据
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class ChildDto extends TaobaoObject {
		private static final long serialVersionUID = 4788154963788589496L;
		/**
		 * 孩子信息
		 */
		@ApiListField("bind_students")
		@ApiField("bind_student")
		private List<BindStudent> bindStudents;
		/**
		 * 孩子nick
		 */
		@ApiField("nick")
		private String nick;
		/**
		 * 孩子userid
		 */
		@ApiField("userid")
		private String userid;
	
		public List<BindStudent> getBindStudents() {
			return this.bindStudents;
		}
		public void setBindStudents(List<BindStudent> bindStudents) {
			this.bindStudents = bindStudents;
		}
		public String getNick() {
			return this.nick;
		}
		public void setNick(String nick) {
			this.nick = nick;
		}
		public String getUserid() {
			return this.userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
	}
	


}
