package com.iheart.nforum.alt.template.model;

import java.util.List;

import ssi.lib.utils.StringUtil;

public class Data {
	private String senderKey;
	private String senderKeyType;
	private String templateCode;
	private String templateName;
	private String templateContent;
	private String inspectionStatus;
	private String createdAt;
	private String modifiedAt;
	private String status;
	private List<Comment> comments;
	private List<Button> buttons;
	
	
	public String getSenderKey() {
		return StringUtil.nvl(senderKey);
	}
	public void setSenderKey(String senderKey) {
		this.senderKey = senderKey;
	}
	public String getSenderKeyType() {
		return StringUtil.nvl(senderKeyType);
	}
	public void setSenderKeyType(String senderKeyType) {
		this.senderKeyType = senderKeyType;
	}
	public String getTemplateCode() {
		return StringUtil.nvl(templateCode);
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public String getTemplateName() {
		return StringUtil.nvl(templateName);
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateContent() {
		return StringUtil.nvl(templateContent);
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getInspectionStatus() {
		return StringUtil.nvl(inspectionStatus);
	}
	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}
	public String getCreatedAt() {
		return StringUtil.nvl(createdAt);
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getModifiedAt() {
		return StringUtil.nvl(modifiedAt);
	}
	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public String getStatus() {
		return StringUtil.nvl(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Button> getButtons() {
		return buttons;
	}
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
	
	
}
