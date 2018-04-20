package com.iheart.nforum.alt.template.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemplateDataVO {
	private int templateSeq;
	private String senderkey;
	private String templateCode;
	private String templateName;
	private String templateContent;
	private String inspectionStatus;
	private Date createdAt;
	private Date modifiedAt;
	private Date regDate;
	private Date modDate;
	private String regId;
	private String modId;
	private String status;
	private int curStatus;
	private String processIng;
	
	public TemplateDataVO() {
		//
	}
	
	public TemplateDataVO(ResponseJsonData resData){
		try {
			this.senderkey 			= resData.getData().getSenderKey();
			this.templateName 		= resData.getData().getTemplateName();
			this.templateContent 	= resData.getData().getTemplateContent();
			this.inspectionStatus 	= resData.getData().getInspectionStatus();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
			this.createdAt 			= dateFormat.parse(resData.getData().getCreatedAt());
			this.modifiedAt 		= resData.getData().getModifiedAt() == null ? null : dateFormat.parse(resData.getData().getModifiedAt());
			this.status 			= resData.getData().getStatus();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public TemplateDataVO(int templateSeq, String senderkey, String templateCode, String templateName,
			String templateContent, String inspectionStatus, Date createdAt, Date modifiedAt, Date regDate,
			Date modDate, String regId, String modId, String status, int curStatus, String processIng) {
		super();
		this.templateSeq = templateSeq;
		this.senderkey = senderkey;
		this.templateCode = templateCode;
		this.templateName = templateName;
		this.templateContent = templateContent;
		this.inspectionStatus = inspectionStatus;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.regDate = regDate;
		this.modDate = modDate;
		this.regId = regId;
		this.modId = modId;
		this.status = status;
		this.curStatus = curStatus;
		this.processIng = processIng;
	}
	
	
	public int getTemplateSeq() {
		return templateSeq;
	}
	public void setTemplateSeq(int templateSeq) {
		this.templateSeq = templateSeq;
	}
	public String getSenderkey() {
		return senderkey;
	}
	public void setSenderkey(String senderkey) {
		this.senderkey = senderkey;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getInspectionStatus() {
		return inspectionStatus;
	}
	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCurStatus() {
		return curStatus;
	}
	public void setCurStatus(int curStatus) {
		this.curStatus = curStatus;
	}
	public String getProcessIng() {
		return processIng;
	}
	public void setProcessIng(String processIng) {
		this.processIng = processIng;
	}
	
	
}
