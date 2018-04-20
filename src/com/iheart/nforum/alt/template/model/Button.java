package com.iheart.nforum.alt.template.model;

import ssi.lib.utils.StringUtil;

public class Button {
	private int ordering; // ��ư ���� ����
	private String name; // ��ư��
	private String linkType; // ��ư ��ũ Ÿ��(DS:�����ȸ, WL:����ũ, AL : �۸�ũ, BK :
							// ��Ű����, MD : �޽�������)
	private String linkTypeName; // ��ư ��ũŸ�Ը�(�����ȸ, ����ũ, �۸�ũ, ��Ű����, �޽�������)
	private String linkMo; // ����� ����ũ �ּ�
	private String linkPc; // PC ����ũ �ּ�
	private String linkIos; // IOS ����ũ �ּ�
	private String linkAnd; // �ȵ���̵� ����ũ �ּ�
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
