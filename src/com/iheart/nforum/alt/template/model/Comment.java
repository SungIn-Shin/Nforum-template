package com.iheart.nforum.alt.template.model;

import ssi.lib.utils.StringUtil;

public class Comment {
	private int id; // 댓글 아이디
	private String content; // 댓글내용
	private String userName; // 작성자
	private String createdAt; // 등록일
	private String status; // 댓글 상태(INQ: 문의, APR: 승인, REJ: 반려, REP: 답변)
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
