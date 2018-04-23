package com.iheart.nforum.alt.template.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iheart.nforum.alt.template.model.Button;
import com.iheart.nforum.alt.template.model.ResponseJsonData;
import com.iheart.nforum.alt.template.model.TemplateButtonVO;
import com.iheart.nforum.alt.template.model.TemplateDataVO;
import com.iheart.nforum.alt.template.model.TemplateHistoryVO;

import ssi.lib.utils.MybatisHandler;
import ssi.lib.utils.StringUtil;

public class TemplateDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateDAO.class);
	
	private SqlSessionFactory sessionFactory;

	public static final String ENVI = "oracle";

	private static TemplateDAO instance;

	private TemplateDAO() {
		init();
	}

	private void init() {
		MybatisHandler mybatis = MybatisHandler.getInstance();
		mybatis.makeSessionFactory(ENVI, 
				"jdbc:oracle:thin:@localhost:1521:xe", 
				"test", 
				"test",
				"D:\\workspace\\n-forum_alt\\src\\mybatis\\mybatis-config.xml",
				"D:\\workspace\\n-forum_alt\\src\\mybatis\\oracle-mapper.xml");
		sessionFactory = mybatis.getSessionFactory(ENVI);
	}

	public static TemplateDAO getInstance() {
		//
		if (instance == null) {
			return instance = new TemplateDAO();
		} else {
			return instance;
		}
	}
	
	public int test() {
		int result = 0;
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			result = session.selectOne("oracle.test");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public List<TemplateDataVO> selectRegistTemplateData() {
		//
		List<TemplateDataVO> rsltList = new ArrayList<TemplateDataVO>();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			rsltList = session.selectList(ENVI + "." + "selectRegistTemplateData");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return rsltList;
	}

	public List<Button> selectTemplateButtons(String templateCode) {
		//
		List<Button> rsltList = new ArrayList<Button>();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			rsltList = session.selectList(ENVI + "." + "selectTemplateButtons", templateCode);
		} catch (Exception e) {
			logger.error("TEMPLATE_CODE : " + templateCode + "//STACK_TRACE : " + StringUtil.stackTrace(e));
		} finally {
			if (session != null)
				session.close();
		}
		return rsltList;
	}

	public int updateCurStatusAndProcessIng(TemplateDataVO dataVO) {
		//
		int result = 0;
		SqlSession session = null;
		try {
			session = sessionFactory.openSession(false);
			result = session.update(ENVI + "." + "updateCurStatusAndProcessIng", dataVO);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			logger.error("TEMPLATE_CODE : " + dataVO.getTemplateCode() + "//STACK_TRACE : " + StringUtil.stackTrace(e));
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	

	/**
	 * Http Response 데이터 Update
	 * @param regRes
	 * @return
	 */
	public int updateTemplateResponseData(ResponseJsonData regRes) {
		//
		int result = 0;

		SqlSession session = null;

		try {
			session = sessionFactory.openSession(false);

			result = session.update(ENVI + "." + "updateTemplateResponseData");
			session.commit();
		} 
		catch (Exception e) {
			session.rollback();
			logger.error("TEMPLATE_CODE : " + regRes.getData().getTemplateCode() + "//STACK_TRACE : " + StringUtil.stackTrace(e));
		} 
		finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public int updateREGOrMODResponseTemplateData(ResponseJsonData regRes) {
		//
		TemplateDataVO dataVO = new TemplateDataVO(regRes);
		
		logger.error(dataVO.toString());
		List<Button> buttons = regRes.getData().getButtons();
		
		int result = 0;
		SqlSession session = null;

		try {
			session = sessionFactory.openSession(false);

			result += session.update(ENVI + "." + "updateREGOrMODResponseTemplateData", dataVO);
			for(Button button : buttons) {
				result += session.update(ENVI + "." + "updateREGOrMODResponseTemplateButtons", button);
			}
			session.commit();
		} 
		catch (Exception e) {
			session.rollback();
			logger.error("TEMPLATE_CODE : " + dataVO.getTemplateCode() + "//STACK_TRACE : " + StringUtil.stackTrace(e));
			return -1;
		} 
		finally {
			if (session != null) session.close();
		}
		return result;	
	}
	
	public int updateREQResponseTemplateData(TemplateDataVO dataVO) {
		//
		dataVO.setInspectionStatus("REQ");
		int result = 0;
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			result = session.update(ENVI + "." + "updateREQResponseTemplateData", dataVO);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			logger.error("TEMPLATE_CODE : " + dataVO.getTemplateCode() + "//STACK_TRACE : " + StringUtil.stackTrace(e));
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	
	

	public int insertTemplateHistory(TemplateDataVO dataVO, ResponseJsonData regRes) {
		int result = 0;
		SqlSession session = null;
		TemplateHistoryVO history = new TemplateHistoryVO(dataVO, regRes);
		try {
			session = sessionFactory.openSession(false);
			result += session.insert(ENVI + "." + "insertTemplateHistory", history);
			session.commit();
		}
		catch (Exception e) {
			session.rollback();
			logger.error("TEMPLATE_CODE : " + dataVO.getTemplateCode() + "//STACK_TRACE : " + StringUtil.stackTrace(e));
			result = -1;
		} 
		finally {
			if (session != null) session.close();
		}
		return result;
	}
	
	
	
	/** 
	 * 테스트 템플릿 등록 
	 * @param vo
	 * @return
	 */
	public int testInsertTemplate(TemplateDataVO template, List<TemplateButtonVO> buttons) {
		int result = 0;
		SqlSession session = null;
		try {
			session = sessionFactory.openSession(false);
			result += session.insert(ENVI + "." + "insertTemplateData", template);
			for(TemplateButtonVO buttonVO : buttons) {
				buttonVO.setTemplateCode(template.getTemplateCode());
				result += session.insert(ENVI + "." + "insertTemplateButton", buttonVO);
			}
			session.commit();
		}
		catch (Exception e) {
			session.rollback();
			System.out.println(StringUtil.stackTrace(e));
		} 
		finally {
			if (session != null) session.close();
		}
		return result;
	}

	public List<TemplateDataVO> selectInspectTemplateList() {
		//
		List<TemplateDataVO> rsltList = new ArrayList<TemplateDataVO>();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			rsltList = session.selectList(ENVI + "." + "selectInspectTemplateList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return rsltList;
	}

	public List<TemplateDataVO> selectModifyTemplateList() {
		List<TemplateDataVO> rsltList = new ArrayList<TemplateDataVO>();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			rsltList = session.selectList(ENVI + "." + "selectModifyTemplateList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return rsltList;
	}

	

}
