package com.dingtalk.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;
import com.taobao.api.TaobaoObject;

import com.taobao.api.TaobaoResponse;

/**
 * TOP DingTalk-API: dingtalk.oapi.workspace.circle.group.list response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OapiWorkspaceCircleGroupListResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1226559622291181565L;

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
	private OpenQueryGroupResponseDto result;


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

	public void setResult(OpenQueryGroupResponseDto result) {
		this.result = result;
	}
	public OpenQueryGroupResponseDto getResult( ) {
		return this.result;
	}

	public boolean isSuccess() {
        return getErrcode() == null || getErrcode().equals(0L);
    }
	
	/**
	 * 圈子群列表
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class OpenGroupDto extends TaobaoObject {
		private static final long serialVersionUID = 2713928779421826562L;
		/**
		 * 群头像mediaId，需要再调用开放平台官网提供的接口转成http地址
		 */
		@ApiField("avatar_media_id")
		private String avatarMediaId;
		/**
		 * 群id
		 */
		@ApiField("conversation_id")
		private String conversationId;
		/**
		 * 当前人数
		 */
		@ApiField("member_count")
		private Long memberCount;
		/**
		 * 群人数上限
		 */
		@ApiField("member_limit")
		private Long memberLimit;
		/**
		 * 群名称
		 */
		@ApiField("name")
		private String name;
	
		public String getAvatarMediaId() {
			return this.avatarMediaId;
		}
		public void setAvatarMediaId(String avatarMediaId) {
			this.avatarMediaId = avatarMediaId;
		}
		public String getConversationId() {
			return this.conversationId;
		}
		public void setConversationId(String conversationId) {
			this.conversationId = conversationId;
		}
		public Long getMemberCount() {
			return this.memberCount;
		}
		public void setMemberCount(Long memberCount) {
			this.memberCount = memberCount;
		}
		public Long getMemberLimit() {
			return this.memberLimit;
		}
		public void setMemberLimit(Long memberLimit) {
			this.memberLimit = memberLimit;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 返回值
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class OpenQueryGroupResponseDto extends TaobaoObject {
		private static final long serialVersionUID = 6513687441185163811L;
		/**
		 * 圈子群列表
		 */
		@ApiListField("groups")
		@ApiField("open_group_dto")
		private List<OpenGroupDto> groups;
		/**
		 * 是否有更多数据
		 */
		@ApiField("has_more")
		private Boolean hasMore;
		/**
		 * 下一次分页的游标
		 */
		@ApiField("next_cursor")
		private Long nextCursor;
	
		public List<OpenGroupDto> getGroups() {
			return this.groups;
		}
		public void setGroups(List<OpenGroupDto> groups) {
			this.groups = groups;
		}
		public Boolean getHasMore() {
			return this.hasMore;
		}
		public void setHasMore(Boolean hasMore) {
			this.hasMore = hasMore;
		}
		public Long getNextCursor() {
			return this.nextCursor;
		}
		public void setNextCursor(Long nextCursor) {
			this.nextCursor = nextCursor;
		}
	}
	


}
