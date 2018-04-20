package com.iheart.nforum.alt.template.model;

public class TemplateButtonVO {
	private String templateCode;
	private int ordering;
	private String buttonName;
	private String linktype;
	private String linkmo;
	private String linkpc;
	private String linkios;
	private String linkand;
	
	
	public TemplateButtonVO() {
		//
	}
	
	public TemplateButtonVO(String templateCode, int ordering, String buttonName, String linktype, String linkmo,
			String linkpc, String linkios, String linkand) {
		super();
		this.templateCode = templateCode;
		this.ordering = ordering;
		this.buttonName = buttonName;
		this.linktype = linktype;
		this.linkmo = linkmo;
		this.linkpc = linkpc;
		this.linkios = linkios;
		this.linkand = linkand;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getLinktype() {
		return linktype;
	}

	public void setLinktype(String linktype) {
		this.linktype = linktype;
	}

	public String getLinkmo() {
		return linkmo;
	}

	public void setLinkmo(String linkmo) {
		this.linkmo = linkmo;
	}

	public String getLinkpc() {
		return linkpc;
	}

	public void setLinkpc(String linkpc) {
		this.linkpc = linkpc;
	}

	public String getLinkios() {
		return linkios;
	}

	public void setLinkios(String linkios) {
		this.linkios = linkios;
	}

	public String getLinkand() {
		return linkand;
	}

	public void setLinkand(String linkand) {
		this.linkand = linkand;
	}

}
