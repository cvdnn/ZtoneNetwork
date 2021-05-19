package com.dingtalk.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;
import java.util.List;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.dingtalk.api.DingTalkConstants;
import com.taobao.api.Constants;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.TaobaoUtils;

import com.dingtalk.api.response.OapiImChatScencegroupInteractivecardCallbackRegisterResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.im.chat.scencegroup.interactivecard.callback.register request
 * 
 * @author top auto create
 * @since 1.0, 2020.12.23
 */
public class OapiImChatScencegroupInteractivecardCallbackRegisterRequest extends BaseTaobaoRequest<OapiImChatScencegroupInteractivecardCallbackRegisterResponse> {
	
	

	/** 
	* 加密密钥用于校验来源
	 */
	private String apiSecret;

	/** 
	* 回调地址
	 */
	private String callbackUrl;

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getApiSecret() {
		return this.apiSecret;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getCallbackUrl() {
		return this.callbackUrl;
	}

	public String getApiMethodName() {
		return "dingtalk.oapi.im.chat.scencegroup.interactivecard.callback.register";
	}

	private String topResponseType = Constants.RESPONSE_TYPE_DINGTALK_OAPI;

     public String getTopResponseType() {
        return this.topResponseType;
     }

     public void setTopResponseType(String topResponseType) {
        this.topResponseType = topResponseType;
     }

     public String getTopApiCallType() {
        return DingTalkConstants.CALL_TYPE_OAPI;
     }

     private String topHttpMethod = DingTalkConstants.HTTP_METHOD_POST;

     public String getTopHttpMethod() {
     	return this.topHttpMethod;
     }

     public void setTopHttpMethod(String topHttpMethod) {
        this.topHttpMethod = topHttpMethod;
     }

     public void setHttpMethod(String httpMethod) {
         this.setTopHttpMethod(httpMethod);
     }

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("api_secret", this.apiSecret);
		txtParams.put("callback_url", this.callbackUrl);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OapiImChatScencegroupInteractivecardCallbackRegisterResponse> getResponseClass() {
		return OapiImChatScencegroupInteractivecardCallbackRegisterResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(callbackUrl, "callbackUrl");
	}
	

}