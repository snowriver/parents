package com.tenzhao.common.push;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 推送内容模板
 * 属性字典值：mediaType 当前推送的媒体类型 1 图文信息 2 文字信息 3 图片信息 4语音 5视频 6充值提醒
 * pushType  推送类型 0 商家推送 1系统推送  2代理商推送
 * @author chenxj
 */
public class PushModel {

	/** 推送日期 格式:2015年08月04日 16:57 */
	private String createDate;
	/** 推送标题 */
	private String title;
	/** 封面图片路径 */
	private String coverPhoto;
	/** 消息id,用于打开详情 */
	private String id;
	/** 推送摘要 */
	private String remark;
	/** 推送类型 0 商家推送 1系统推送  2代理商推送 */
	private Short pushType;
	/** 当前推送的媒体类型 1 图文信息 2 文字信息 3 图片信息 4语音 5视频 6充值提醒  */
	private Short mediaType;
	
	/** 推送内容的详情页路径 */
	private String url ;
	
	/** 只为满足app1.5.1以下的版本 */
	
	private Object otherParams ;
	private Company company ;
	/** 分享的图片的路径  */
	private String shareIcoUrl ;
	private Integer createTime ;
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getPushType() {
		return pushType;
	}

	public void setPushType(Short pushType) {
		this.pushType = pushType;
	}

	public Short getMediaType() {
		return mediaType;
	}

	public void setMediaType(Short mediaType) {
		this.mediaType = mediaType;
	}

	public static String toString(Object[] object) {
		return String.format("{\"publicNumName\":\"%s\",\"coverPhoto\":\"%s\",\"createDate\":\"%s\",\"id\":\"%s\",\"mediaType\":%d,\"pushType\":%d,\"remark\":\"%s\",\"title\":\"%s\",\"url\":\"%s\"}"
				,object[0],object[1],object[2],object[3],object[4],object[5],object[6],object[7],object[8]);
	}
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(Object otherParams) {
		this.otherParams = otherParams;
	}

	/**
	 * 从OSS获取分享图片的路径
	 * @return
	 */
	public String getShareIcoUrl() {
		if(StringUtils.isNotEmpty(this.coverPhoto) && StringUtils.isEmpty(shareIcoUrl)){
			shareIcoUrl = this.coverPhoto.replaceAll("!pushcover", "!share");
		}
		return shareIcoUrl;
	}

	public void setShareIcoUrl(String shareIcoUrl) {
		this.shareIcoUrl = shareIcoUrl;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
}
