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

import com.dingtalk.api.response.OapiHireOnboardingGuideCompleteResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.hire.onboarding.guide.complete request
 * 
 * @author top auto create
 * @since 1.0, 2020.08.30
 */
public class OapiHireOnboardingGuideCompleteRequest extends BaseTaobaoRequest<OapiHireOnboardingGuideCompleteResponse> {
	
	

	/** 
	* 引导步骤名称
	 */
	private String guideName;

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getGuideName() {
		return this.guideName;
	}

	public String getApiMethodName() {
		return "dingtalk.oapi.hire.onboarding.guide.complete";
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
		txtParams.put("guide_name", this.guideName);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OapiHireOnboardingGuideCompleteResponse> getResponseClass() {
		return OapiHireOnboardingGuideCompleteResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(guideName, "guideName");
	}
	

}