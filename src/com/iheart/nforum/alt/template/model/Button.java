package com.iheart.nforum.alt.template.model;

import ssi.lib.utils.StringUtil;

public class Button {
	private int ordering; // 버튼 노출 순서
	private String name; // 버튼명
	private String linkType; // 버튼 링크 타입(DS:배송초회, WL:웹링크, AL : 앱링크, BK :
							// 봇키워드, MD : 메시지전달)
	private String linkTypeName; // 버튼 링크타입명(배송조회, 웹링크, 앱링크, 봇키워드, 메시지전달)
	private String linkMo; // 모바일 웹링크 주소
	private String linkPc; // PC 웹링크 주소
	private String linkIos; // IOS 웹링크 주소
	private String linkAnd; // 안드로이드 웹링크 주소
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getName() {
		return StringUtil.nvl(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkType() {
		return StringUtil.nvl(linkType);
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public String getLinkTypeName() {
		return StringUtil.nvl(linkTypeName);
	}
	public void setLinkTypeName(String linkTypeName) {
		this.linkTypeName = linkTypeName;
	}
	public String getLinkMo() {
		return StringUtil.nvl(linkMo);
	}
	public void setLinkMo(String linkMo) {
		this.linkMo = linkMo;
	}
	public String getLinkPc() {
		return StringUtil.nvl(linkPc);
	}
	public void setLinkPc(String linkPc) {
		this.linkPc = linkPc;
	}
	public String getLinkIos() {
		return StringUtil.nvl(linkIos);
	}
	public void setLinkIos(String linkIos) {
		this.linkIos = linkIos;
	}
	public String getLinkAnd() {
		return StringUtil.nvl(linkAnd);
	}
	public void setLinkAnd(String linkAnd) {
		this.linkAnd = linkAnd;
	}
	
	
}
