package com.dingtalk.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.ats.job.listbyowner response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiAtsJobListbyownerResponse extends TaobaoResponse {

	private static final long serialVersionUID = 5571327644768146455L;

	/** 
	 * 错误码
	 */
	@ApiField("errcode")
	private Long errcode;

	/** 
	 * 错误描述
	 */
	@ApiField("errmsg")
	private String errmsg;

	/** 
	 * 职位数据列表
	 */
	@ApiListField("result")
	@ApiField("job_simple_vo")
	private List<JobSimpleVo> result;


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

	public void setResult(List<JobSimpleVo> result) {
		this.result = result;
	}
	public List<JobSimpleVo> getResult( ) {
		return this.result;
	}

	public boolean isSuccess() {
        return getErrcode() == null || getErrcode().equals(0L);
    }
	
	/**
	 * 职位地址详情
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class JobAddressVo extends TaobaoObject {
		private static final long serialVersionUID = 3678246377856861684L;
		/**
		 * 地点详情
		 */
		@ApiField("detail")
		private String detail;
		/**
		 * 经度
		 */
		@ApiField("latitude")
		private String latitude;
		/**
		 * 纬度
		 */
		@ApiField("longitude")
		private String longitude;
		/**
		 * 地点名
		 */
		@ApiField("name")
		private String name;
	
		public String getDetail() {
			return this.detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
		public String getLatitude() {
			return this.latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return this.longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 职位数据列表
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class JobSimpleVo extends TaobaoObject {
		private static final long serialVersionUID = 8568547583719633126L;
		/**
		 * 职位地址详情
		 */
		@ApiField("address")
		private JobAddressVo address;
		/**
		 * true :校招，false：社招
		 */
		@ApiField("campus")
		private Boolean campus;
		/**
		 * 职位分类
		 */
		@ApiField("category")
		private String category;
		/**
		 * 职位地址 市
		 */
		@ApiField("city")
		private String city;
		/**
		 * 企业id
		 */
		@ApiField("corpid")
		private String corpid;
		/**
		 * 职位描述
		 */
		@ApiField("description")
		private String description;
		/**
		 * 职位地址 区县
		 */
		@ApiField("district")
		private String district;
		/**
		 * 招募人数
		 */
		@ApiField("head_count")
		private Long headCount;
		/**
		 * 职位编码
		 */
		@ApiField("job_code")
		private String jobCode;
		/**
		 * 职位唯一标识
		 */
		@ApiField("job_id")
		private String jobId;
		/**
		 * 职位类型：FULL-TIME:全职，PART-TIME:兼职，INTERNSHIP:实习，OTHER:其他
		 */
		@ApiField("job_nature")
		private String jobNature;
		/**
		 * 职位部门id
		 */
		@ApiField("main_dept_id")
		private Long mainDeptId;
		/**
		 * 最高工作年限
		 */
		@ApiField("max_job_experience")
		private Long maxJobExperience;
		/**
		 * 最高薪水，单位元
		 */
		@ApiField("max_salary")
		private Long maxSalary;
		/**
		 * 最低工作年限
		 */
		@ApiField("min_job_experience")
		private Long minJobExperience;
		/**
		 * 最低薪水，单位元
		 */
		@ApiField("min_salary")
		private Long minSalary;
		/**
		 * 职位名称
		 */
		@ApiField("name")
		private String name;
		/**
		 * 职位地址 省
		 */
		@ApiField("province")
		private String province;
		/**
		 * 最低职位要求 1小学 2初中 3高中 4中专 5大专 6本科 7硕士 8 博士 9其他
		 */
		@ApiField("required_edu")
		private Long requiredEdu;
		/**
		 * 薪资月数
		 */
		@ApiField("salary_month")
		private Long salaryMonth;
		/**
		 * 是否薪资面议
		 */
		@ApiField("salary_negotiable")
		private Boolean salaryNegotiable;
		/**
		 * 薪资类型，HOUR:小时，DAY:天，WEEK:周，MONTH:月，BY_TIME:次
		 */
		@ApiField("salary_period")
		private String salaryPeriod;
		/**
		 * 职位标签
		 */
		@ApiListField("tags")
		@ApiField("string")
		private List<String> tags;
	
		public JobAddressVo getAddress() {
			return this.address;
		}
		public void setAddress(JobAddressVo address) {
			this.address = address;
		}
		public Boolean getCampus() {
			return this.campus;
		}
		public void setCampus(Boolean campus) {
			this.campus = campus;
		}
		public String getCategory() {
			return this.category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getCity() {
			return this.city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCorpid() {
			return this.corpid;
		}
		public void setCorpid(String corpid) {
			this.corpid = corpid;
		}
		public String getDescription() {
			return this.description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDistrict() {
			return this.district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public Long getHeadCount() {
			return this.headCount;
		}
		public void setHeadCount(Long headCount) {
			this.headCount = headCount;
		}
		public String getJobCode() {
			return this.jobCode;
		}
		public void setJobCode(String jobCode) {
			this.jobCode = jobCode;
		}
		public String getJobId() {
			return this.jobId;
		}
		public void setJobId(String jobId) {
			this.jobId = jobId;
		}
		public String getJobNature() {
			return this.jobNature;
		}
		public void setJobNature(String jobNature) {
			this.jobNature = jobNature;
		}
		public Long getMainDeptId() {
			return this.mainDeptId;
		}
		public void setMainDeptId(Long mainDeptId) {
			this.mainDeptId = mainDeptId;
		}
		public Long getMaxJobExperience() {
			return this.maxJobExperience;
		}
		public void setMaxJobExperience(Long maxJobExperience) {
			this.maxJobExperience = maxJobExperience;
		}
		public Long getMaxSalary() {
			return this.maxSalary;
		}
		public void setMaxSalary(Long maxSalary) {
			this.maxSalary = maxSalary;
		}
		public Long getMinJobExperience() {
			return this.minJobExperience;
		}
		public void setMinJobExperience(Long minJobExperience) {
			this.minJobExperience = minJobExperience;
		}
		public Long getMinSalary() {
			return this.minSalary;
		}
		public void setMinSalary(Long minSalary) {
			this.minSalary = minSalary;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProvince() {
			return this.province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public Long getRequiredEdu() {
			return this.requiredEdu;
		}
		public void setRequiredEdu(Long requiredEdu) {
			this.requiredEdu = requiredEdu;
		}
		public Long getSalaryMonth() {
			return this.salaryMonth;
		}
		public void setSalaryMonth(Long salaryMonth) {
			this.salaryMonth = salaryMonth;
		}
		public Boolean getSalaryNegotiable() {
			return this.salaryNegotiable;
		}
		public void setSalaryNegotiable(Boolean salaryNegotiable) {
			this.salaryNegotiable = salaryNegotiable;
		}
		public String getSalaryPeriod() {
			return this.salaryPeriod;
		}
		public void setSalaryPeriod(String salaryPeriod) {
			this.salaryPeriod = salaryPeriod;
		}
		public List<String> getTags() {
			return this.tags;
		}
		public void setTags(List<String> tags) {
			this.tags = tags;
		}
	}
	


}
