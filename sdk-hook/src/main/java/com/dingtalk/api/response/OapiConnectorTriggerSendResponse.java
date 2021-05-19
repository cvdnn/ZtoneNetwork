package com.dingtalk.api.response;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.connector.trigger.send response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiConnectorTriggerSendResponse extends TaobaoResponse {

	private static final long serialVersionUID = 8249781486144847478L;

	/** 
	 * 错误码
	 */
	@ApiField("errcode")
	private Long errcode;

	/** 
	 * 错误信息
	 */
	@ApiField("errmsg")
	private String errmsg;

	/** 
	 * 返回结果
	 */
	@ApiField("result")
	private TriggerMsgResponse result;


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

	public void setResult(TriggerMsgResponse result) {
		this.result = result;
	}
	public TriggerMsgResponse getResult( ) {
		return this.result;
	}

	public boolean isSuccess() {
        return getErrcode() == null || getErrcode().equals(0L);
    }
	
	/**
	 * 返回结果
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class TriggerMsgResponse extends TaobaoObject {
		private static final long serialVersionUID = 3357374475528897364L;
		/**
		 * 请求ID
		 */
		@ApiField("request_id")
		private String requestId;
	
		public String getRequestId() {
			return this.requestId;
		}
		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}
	}
	


}
