package com.iheart.nforum.alt.template.model;

import ssi.lib.utils.StringUtil;

public class Comment {
	private int id; // ��� ���̵�
	private String content; // ��۳���
	private String userName; // �ۼ���
	private String createdAt; // �����
	private String status; // ��� ����(INQ: ����, APR: ����, REJ: �ݷ�, REP: �亯)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return StringUtil.nvl(content);
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return StringUtil.nvl(userName);
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreatedAt() {
		return StringUtil.nvl(createdAt);
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getStatus() {
		return StringUtil.nvl(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
