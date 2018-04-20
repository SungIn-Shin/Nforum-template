package com.iheart.nforum.alt.template.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import ssi.lib.utils.StringUtil;

/*
 * [���� ����]
{
  "code": "200",
  "data": {
    "senderKey": "72cede3f8ad4c53ca662c315b065a6fc44c9fef6",
    "senderKeyType": "S",
    "templateCode": "TESTCD01",
    "templateName": "�׽�Ʈ ���ø�1",
    "templateContent": "#{����}�� �ֹ��Ͻ� #{������} ���� ��� ����Ͽ����ϴ�.
    "inspectionStatus": "REG",
    "createdAt": "2017-11-11 13:28:08",
    "modifiedAt": "",
    "status": "R",
    "comments": [],
    "buttons": [{
          		��ordering�� : 1,
				��name�� : ����� ��ȸ�ϱ⡱,
				��linkType�� : ��DS��,
				��linkTypeName�� : �������ȸ��,
				��linkMo�� : null,
				��linkPc�� : null,
				��linkIos�� : null,
				��linkAnd�� : null
				}, {
				��ordering�� : 2,
				��name�� : ���ٷΰ��⡱,
				��linkType�� : ��WL��,
				��linkTypeName�� : ������ũ��,
				��linkMo�� : ��http://daum.net��,
				��linkPc�� : null,
				��linkIos�� : null,
				��linkAnd�� : null
	    	}]
  }
}

���� ���� ���¸� ��ü�������� ������ Class.. 
Gson ���̺귯���� ����� �� �̷� ���·� �����ϸ� ������.
*/
public class ResponseJsonData {
	String code;

	Data data;
	
	public String getCode() {
		return StringUtil.nvl(code);
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static void main(String[] args) {
		ResponseJsonData res = new ResponseJsonData();
		res.code = "200";
		Data data = new Data();
		data.setSenderKey("72cede3f8ad4c53ca662c315b065a6fc44c9fef6");
		data.setSenderKeyType("S");
		data.setTemplateCode("1234");
		data.setTemplateContent("aaaa");
		data.setTemplateName("name test");
		
		List<Button> buttons = new ArrayList<Button>();
		
		for(int i = 1; i < 4; i++) {
			Button button = new Button();
			button.setOrdering(i);
			button.setName("test btn" + i);
			button.setLinkType("WL");
			button.setLinkTypeName("��ư�׽�Ʈ");
			button.setLinkPc("http://www.naver.com");
			buttons.add(button);
		}
		
		data.setButtons(buttons);
		
		res.data = data;
		
		
		String g = new Gson().toJson(res);
		System.out.println(g);
		
		ResponseJsonData res1 = new Gson().fromJson(g, ResponseJsonData.class);
		
		System.out.println(res1.code);
		System.out.println(res1.getData().getSenderKey());
		
	}
}
