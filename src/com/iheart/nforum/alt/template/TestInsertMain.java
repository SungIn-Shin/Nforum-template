package com.iheart.nforum.alt.template;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.iheart.nforum.alt.template.dao.TemplateDAO;
import com.iheart.nforum.alt.template.model.TemplateDataVO;

import ssi.lib.utils.MybatisHandler;

public class TestInsertMain {
	private SqlSessionFactory sessionFactory;

	public static final String ENVI = "oracle";

	
	public static String TEST_USER = "TEST_NFORUM";
	
	public static String TEST_TEMPLATE_NAME = "테스트 템플릿 이름";
	
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
		TemplateDataVO vo = new TemplateDataVO();
		vo.setSenderkey("4fe04f2c0cccde3d61ac8a3bd92c386a07d1f8cd");
		vo.setTemplateCode(TEST_USER);
		vo.setTemplateName(TEST_TEMPLATE_NAME);
		vo.setTemplateContent("");
		vo.setRegId(TEST_USER);
		vo.setCurStatus(0);
		
		int insertCnt = dao.testInsertTemplate(vo);
		System.out.println(insertCnt);
	}
	
	public void test() {
		System.out.println(dao.test());;
	}
	
	public static void main(String[] args) {
		TemplateDAO dao = TemplateDAO.getInstance();
		System.out.println(dao.test());
	}
}
