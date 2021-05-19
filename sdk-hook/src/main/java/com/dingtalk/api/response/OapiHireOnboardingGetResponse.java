package com.dingtalk.api.response;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.hire.onboarding.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiHireOnboardingGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 4311515296884734741L;

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
	 * 引导结果
	 */
	@ApiField("result")
	private OnboardingVo result;


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

	public void setResult(OnboardingVo result) {
		this.result = result;
	}
	public OnboardingVo getResult( ) {
		return this.result;
	}

	public boolean isSuccess() {
        return getErrcode() == null || getErrcode().equals(0L);
    }
	
	/**
	 * 简历邮箱设置引导结果
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class FeatureGuideVo extends TaobaoObject {
		private static final long serialVersionUID = 3383738322364628598L;
		/**
		 * 功能引导状态
		 */
		@ApiField("guide_status")
		private String guideStatus;
		/**
		 * 功能引导创建时间
		 */
		@ApiField("guide_time")
		private Long guideTime;
	
		public String getGuideStatus() {
			return this.guideStatus;
		}
		public void setGuideStatus(String guideStatus) {
			this.guideStatus = guideStatus;
		}
		public Long getGuideTime() {
			return this.guideTime;
		}
		public void setGuideTime(Long guideTime) {
			this.guideTime = guideTime;
		}
	}
	
	/**
	 * 引导结果
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class OnboardingVo extends TaobaoObject {
		private static final long serialVersionUID = 8869621689295114434L;
		/**
		 * 人才推荐引导结果
		 */
		@ApiField("candidate_recommend_guide")
		private FeatureGuideVo candidateRecommendGuide;
		/**
		 * 新手引导创建时间
		 */
		@ApiField("create_time")
		private Long createTime;
		/**
		 * 职位引导结果
		 */
		@ApiField("job_guide")
		private FeatureGuideVo jobGuide;
		/**
		 * 简历邮箱设置引导结果
		 */
		@ApiField("resume_collect_mail_guide")
		private FeatureGuideVo resumeCollectMailGuide;
	
		public FeatureGuideVo getCandidateRecommendGuide() {
			return this.candidateRecommendGuide;
		}
		public void setCandidateRecommendGuide(FeatureGuideVo candidateRecommendGuide) {
			this.candidateRecommendGuide = candidateRecommendGuide;
		}
		public Long getCreateTime() {
			return this.createTime;
		}
		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}
		public FeatureGuideVo getJobGuide() {
			return this.jobGuide;
		}
		public void setJobGuide(FeatureGuideVo jobGuide) {
			this.jobGuide = jobGuide;
		}
		public FeatureGuideVo getResumeCollectMailGuide() {
			return this.resumeCollectMailGuide;
		}
		public void setResumeCollectMailGuide(FeatureGuideVo resumeCollectMailGuide) {
			this.resumeCollectMailGuide = resumeCollectMailGuide;
		}
	}
	


}
