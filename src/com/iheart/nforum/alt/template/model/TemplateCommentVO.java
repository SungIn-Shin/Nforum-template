package com.iheart.nforum.alt.template.model;

import java.util.Date;

public class TemplateCommentVO {
	//
	private int commentsSeq;
	private String templateCode;
	private int id;
	private String content;
	private String userName;
	private Date createdAt;
	private String status;
	
	public TemplateCommentVO() {
		//
	}
	
	public TemplateCommentVO(int commentsSeq, String templateCode, int id, String content, String userName,
			Date createdAt, String status) {
		super();
		this.commentsSeq = commentsSeq;
		this.templateCode = templateCode;
		this.id = id;
		this.content = content;
		this.userName = userName;
		this.createdAt = createdAt;
		this.status = status;
	}

	public int getCommentsSeq() {
		return commentsSeq;
	}

	public void setCommentsSeq(int commentsSeq) {
		this.commentsSeq = commentsSeq;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
