package com.iheart.nforum.alt.template.model;

public class TemplateButtonTypeVO {
	private String linktype;
	private String linktypename;

	public TemplateButtonTypeVO() {
		//
	}
	
	public TemplateButtonTypeVO(String linktype, String linktypename) {
		super();
		this.linktype = linktype;
		this.linktypename = linktypename;
	}

	public String getLinktype() {
		return linktype;
	}

	public void setLinktype(String linktype) {
		this.linktype = linktype;
	}

	public String getLinktypename() {
		return linktypename;
	}

	public void setLinktypename(String linktypename) {
		this.linktypename = linktypename;
	}

}
