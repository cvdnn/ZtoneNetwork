package com.dingtalk.api.response;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.workspace.circle.group.create response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiWorkspaceCircleGroupCreateResponse extends TaobaoResponse {

	private static final long serialVersionUID = 6497624821635395894L;

	/** 
	 * dingOpenErrcode
	 */
	@ApiField("errcode")
	private Long errcode;

	/** 
	 * errorMsg
	 */
	@ApiField("errmsg")
	private String errmsg;

	/** 
	 * 返回值
	 */
	@ApiField("result")
	private OpenCreateGroupResponseDto result;


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

	public void setResult(OpenCreateGroupResponseDto result) {
		this.result = result;
	}
	public OpenCreateGroupResponseDto getResult( ) {
		return this.result;
	}

	public boolean isSuccess() {
        return getErrcode() == null || getErrcode().equals(0L);
    }
	
	/**
	 * 返回值
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class OpenCreateGroupResponseDto extends TaobaoObject {
		private static final long serialVersionUID = 4763726533858755359L;
		/**
		 * 群id
		 */
		@ApiField("conversation_id")
		private String conversationId;
	
		public String getConversationId() {
			return this.conversationId;
		}
		public void setConversationId(String conversationId) {
			this.conversationId = conversationId;
		}
	}
	


}
