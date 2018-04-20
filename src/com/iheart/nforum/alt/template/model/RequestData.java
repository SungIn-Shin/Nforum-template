package com.iheart.nforum.alt.template.model;

import ssi.lib.utils.StringUtil;

public class RequestData {
	private String senderKey;
	private String senderKeyType;
	private String templateCode;
	private String templateName;
	private String templateContent;
	private String buttons;
	private String inspectionStatus;
	
	
	public RequestData(TemplateDataVO dataVO, String buttons) {
		this.senderKey 			= StringUtil.nvl(dataVO.getSenderkey());
		this.senderKeyType 		= StringUtil.nvl(dataVO.getSenderkey());
		this.templateCode 		= StringUtil.nvl(dataVO.getTemplateCode());
		this.templateName 		= StringUtil.nvl(dataVO.getTemplateName());
		this.templateContent 	= StringUtil.nvl(dataVO.getTemplateContent());
		this.inspectionStatus 	= StringUtil.nvl(dataVO.getInspectionStatus());
		this.buttons 			= StringUtil.nvl(buttons);
	}
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
	public String getButtons() {
		return StringUtil.nvl(buttons);
	}
	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
	public String getInspectionStatus() {
		return StringUtil.nvl(inspectionStatus);
	}
	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}
	@Override
	public String toString() {
		return "RequestData [senderKey=" + senderKey + ", senderKeyType=" + senderKeyType + ", templateCode="
				+ templateCode + ", templateName=" + templateName + ", templateContent=" + templateContent
				+ ", buttons=" + buttons + ", inspectionStatus=" + inspectionStatus + "]";
	}
	
	
}
