package com.iheart.nforum.alt.template.model;

import java.util.Date;

import ssi.lib.utils.CodeMapper;

public class TemplateHistoryVO {
	private String templateCode;
	private int curStatus;
	private String inspectionStatus;
	private Date regDate;
	private String resultCode;
	private String resultDesc;

	public TemplateHistoryVO() {
		super();
	}

	public TemplateHistoryVO(String templateCode, int curStatus, String inspectionStatus, Date regDate, String resultCode,
			String resultDesc) {
		super();
		this.templateCode = templateCode;
		this.curStatus = curStatus;
		this.inspectionStatus = inspectionStatus;
		this.regDate = regDate;
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}

	public TemplateHistoryVO(TemplateDataVO dataVO, ResponseJsonData regRes) {
		this.templateCode 		= dataVO.getTemplateCode();
		this.curStatus			= dataVO.getCurStatus();
		this.inspectionStatus 	= dataVO.getInspectionStatus();
		this.regDate			= new Date();
		this.resultCode			= regRes.getCode();
		this.resultDesc 		= CodeMapper.getDesc(regRes.getCode());
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public int getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(int curStatus) {
		this.curStatus = curStatus;
	}

	public String getInspectionStatus() {
		return inspectionStatus;
	}

	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

}
