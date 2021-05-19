package com.dingtalk.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.ats.job.list response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiAtsJobListResponse extends TaobaoResponse {

	private static final long serialVersionUID = 7117757584814989458L;

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
	@ApiField("result")
	private AtsPageResult result;


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

	public void setResult(AtsPageResult result) {
		this.result = result;
	}
	public AtsPageResult getResult( ) {
		return this.result;
	}

	public boolean isSuccess() {
        return getErrcode() == null || getErrcode().equals(0L);
    }
	
	/**
	 * 职位信息列表
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class JobSimpleVo extends TaobaoObject {
		private static final long serialVersionUID = 5597573513929168144L;
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
		 * 职位地址 区县
		 */
		@ApiField("district")
		private String district;
		/**
		 * 职位编码
		 */
		@ApiField("job_code")
		private String jobCode;
		/**
		 * 职位id
		 */
		@ApiField("job_id")
		private String jobId;
		/**
		 * 职位类型：FULL-TIME:全职，PART-TIME:兼职，INTERNSHIP:实习，OTHER:其他
		 */
		@ApiField("job_nature")
		private String jobNature;
		/**
		 * 最高薪水，单位元
		 */
		@ApiField("max_salary")
		private Long maxSalary;
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
		 * 薪资月数
		 */
		@ApiField("salary_month")
		private Long salaryMonth;
		/**
		 * 薪资类型，HOUR:小时，DAY:天，WEEK:周，MONTH:月，BY_TIME:次
		 */
		@ApiField("salary_period")
		private String salaryPeriod;
	
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
		public String getDistrict() {
			return this.district;
		}
		public void setDistrict(String district) {
			this.district = district;
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
		public Long getMaxSalary() {
			return this.maxSalary;
		}
		public void setMaxSalary(Long maxSalary) {
			this.maxSalary = maxSalary;
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
		public Long getSalaryMonth() {
			return this.salaryMonth;
		}
		public void setSalaryMonth(Long salaryMonth) {
			this.salaryMonth = salaryMonth;
		}
		public String getSalaryPeriod() {
			return this.salaryPeriod;
		}
		public void setSalaryPeriod(String salaryPeriod) {
			this.salaryPeriod = salaryPeriod;
		}
	}
	
	/**
	 * 职位数据列表
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class AtsPageResult extends TaobaoObject {
		private static final long serialVersionUID = 5794379457752559152L;
		/**
		 * 是否还有数据
		 */
		@ApiField("has_more")
		private Boolean hasMore;
		/**
		 * 职位信息列表
		 */
		@ApiListField("list")
		@ApiField("job_simple_vo")
		private List<JobSimpleVo> list;
		/**
		 * 游标，下次分页请求使用
		 */
		@ApiField("next_cursor")
		private String nextCursor;
	
		public Boolean getHasMore() {
			return this.hasMore;
		}
		public void setHasMore(Boolean hasMore) {
			this.hasMore = hasMore;
		}
		public List<JobSimpleVo> getList() {
			return this.list;
		}
		public void setList(List<JobSimpleVo> list) {
			this.list = list;
		}
		public String getNextCursor() {
			return this.nextCursor;
		}
		public void setNextCursor(String nextCursor) {
			this.nextCursor = nextCursor;
		}
	}
	


}
