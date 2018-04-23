package com.iheart.nforum.alt.template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import com.iheart.nforum.alt.template.dao.TemplateDAO;
import com.iheart.nforum.alt.template.model.TemplateButtonVO;
import com.iheart.nforum.alt.template.model.TemplateDataVO;

public class TestInsertMain {
	private SqlSessionFactory sessionFactory;

	public static final String ENVI = "oracle";

	
	public static String TEST_USER = "TEST_NFORUM";
	
	public static String TEST_TEMPLATE_NAME = "태스트템플릿명";
	
	public static String TEST_FAIL_TEMPLATE_CONTENT = "테스트 템플릿 내용입니다. 반려좀 시켜주세요. TEST NUMBER : ";
	
	public static String TEST_SUCC_TEMPLATE_CONTENT = "테스트 템플릿 내용입니다. 템플릿 등록 성공좀 시켜주시길 바랍니다. TEST NUMBER : ";
	
	private TemplateDAO dao;
	
	public TestInsertMain() {
		init();
	}

	private void init() {
		dao = TemplateDAO.getInstance();
	}
	
	
	public void insert() {
		
		String yyyymmddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		
		TemplateDataVO templateVO = new TemplateDataVO();
		templateVO.setSenderkey("4fe04f2c0cccde3d61ac8a3bd92c386a07d1f8cd");
		templateVO.setTemplateCode(TEST_USER + yyyymmddhhmmss);
		templateVO.setTemplateName(TEST_TEMPLATE_NAME + yyyymmddhhmmss);
		templateVO.setTemplateContent(TEST_SUCC_TEMPLATE_CONTENT + yyyymmddhhmmss);
		templateVO.setRegId(TEST_USER);
		templateVO.setCurStatus(0);
		
		
		List<TemplateButtonVO> btnList = new ArrayList<>();
		// button 입력...
		for(int i = 1; i <= 5; i++) {
			TemplateButtonVO btnVO = new TemplateButtonVO();
			btnVO.setOrdering(i);
			btnVO.setButtonName(TEST_USER + "_TEST_BUTTON_" + i);
			btnVO.setLinktype("WL");
			btnVO.setLinkpc("http://www.testweblink.com/" + i);
			btnList.add(btnVO);
		}
		
		int insertCnt = dao.testInsertTemplate(templateVO, btnList);
		System.out.println(insertCnt);
	}
	
//	public void test() {
//		System.out.println(dao.test());;
//	}
	
	public static void main(String[] args) {
		TestInsertMain test = new TestInsertMain();
		test.insert();
	}
}
