package com.iheart.nforum.alt.template.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iheart.nforum.alt.template.model.Button;
import com.iheart.nforum.alt.template.model.ResponseJsonData;
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

	public List<TemplateDataVO> selectTemplateData() {
		//
		List<TemplateDataVO> rsltList = new ArrayList<TemplateDataVO>();
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			rsltList = session.selectList(ENVI + "." + "selectTemplateData");
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

	public int updateTemplateCurStatus(TemplateDataVO dataVO) {
		//
		int result = 0;

		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			result = session.update(ENVI + "." + "updateTemplateCurStatus", dataVO);
		} catch (Exception e) {
			logger.error("TEMPLATE_CODE : " + dataVO.getTemplateCode() + "//STACK_TRACE : " + StringUtil.stackTrace(e));
		} finally {
			if (session != null)
				session.close();
		}
		return result;

	}

	/**
	 * Http Response µ•¿Ã≈Õ Update
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

	public int updateResponseTemplateData(ResponseJsonData regRes) {
		//
		TemplateDataVO dataVO = new TemplateDataVO(regRes);
		List<Button> buttons = regRes.getData().getButtons();
		
		int result = 0;
		SqlSession session = null;

		try {
			session = sessionFactory.openSession(false);

			result += session.update(ENVI + "." + "updateResponseTemplateData", dataVO);
			for(Button button : buttons) {
				result += session.update(ENVI + "." + "updateResponseTemplateButtons", button);
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

	public int insertTemplateHistory(TemplateDataVO dataVO, ResponseJsonData regRes) {
		int result = 0;
		SqlSession session = null;
		TemplateHistoryVO history = new TemplateHistoryVO(dataVO, regRes);
		for (int i = 0; i < 2;) {
			try {
				session = sessionFactory.openSession(false);
				
				result += session.insert(ENVI + "." + "insertTemplateHistory", history);
				session.commit();
				break;
			}
			catch (Exception e) {
				session.rollback();
				logger.error("TEMPLATE_CODE : " + dataVO.getTemplateCode() + "//STACK_TRACE : " + StringUtil.stackTrace(e));
				result = -1;
			} 
			finally {
				if (session != null) session.close();
			}
		}
		return result;
	}

	public int testInsertTemplate(TemplateDataVO vo) {
		int result = 0;
		SqlSession session = null;
		for (int i = 0; i < 2;) {
			try {
				session = sessionFactory.openSession(false);
				
				result += session.insert(ENVI + "." + "insertTemplateHistory", vo);
				session.commit();
				break;
			}
			catch (Exception e) {
				System.out.println(StringUtil.stackTrace(e));
			} 
			finally {
				if (session != null) session.close();
			}
		}
		return result;
		
	}

}
