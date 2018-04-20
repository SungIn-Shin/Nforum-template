package ssi.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iheart.nforum.alt.template.model.TemplateDataVO;

public class MybatisHandler {
	private Map<String, SqlSessionFactory> sessionMap = new HashMap<String, SqlSessionFactory>();
	//
	private static final Logger logger = LoggerFactory.getLogger(MybatisHandler.class);

	
	public static String TEST_USER = "TEST_NFORUM";
	
	public static String TEST_TEMPLATE_NAME = "테스트 템플릿 이름";
	
	public static String TEST_FAIL_TEMPLATE_CONTENT = "테스트 템플릿 내용입니다. 반려좀 시켜주세요. TEST NUMBER : ";
	
	public static String TEST_SUCC_TEMPLATE_CONTENT = "테스트 템플릿 내용입니다. 템플릿 등록 성공좀 시켜주시길 바랍니다. TEST NUMBER : ";
	
	//
	private static MybatisHandler instance;

	private MybatisHandler() {
	}

	public static MybatisHandler getInstance() {
		if (instance == null) {
			instance = new MybatisHandler();
		}
		return instance;
	}

	public MybatisHandler makeSessionFactory(String dbms, String url, String username, String password,
			String configPath, String... mappers) {
		SqlSessionFactory sessionFactory = null;
		try {
			Properties props = new Properties();
			props.setProperty("url", url);
			props.setProperty("username", username);
			props.setProperty("password", password);
			props.setProperty("environment", dbms);

			InputStream configIs = new FileInputStream(new File(configPath));
			sessionFactory = new SqlSessionFactoryBuilder().build(configIs, props);

			for (String mapperFile : mappers) {
				InputStream in = new FileInputStream(new File(mapperFile));
				Configuration configuration = sessionFactory.getConfiguration();
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, mapperFile,
						configuration.getSqlFragments());
				xmlMapperBuilder.parse();
			}

			if (sessionMap.get(dbms) == null) {
				sessionMap.put(dbms, sessionFactory);
			}
		} catch (FileNotFoundException e) {
			//
			logger.error(StringUtil.stackTrace(e));
			return null;
		} catch (Exception e) {
			logger.error(StringUtil.stackTrace(e));
			return null;
		}
		return null;
	}
	
	public SqlSession getSession(String envi) {
		return sessionMap.get(envi).openSession();
	}
	
	public SqlSessionFactory getSessionFactory(String env) {
		return sessionMap.get(env);
	}
	
	public static void main(String[] args) {
		MybatisHandler mybatis = MybatisHandler.getInstance();
		mybatis.makeSessionFactory(	"oracle", 
//									"jdbc:oracle:thin:@61.250.86.178:1521:orcl", 
//									"igovus", 
//									"igovus", 
									"jdbc:oracle:thin:@localhost:1521:xe", 
									"test",
									"test",
									"D:\\workspace\\n-forum_alt\\src\\mybatis\\mybatis-config.xml", 
									"D:\\workspace\\n-forum_alt\\src\\mybatis\\oracle-mapper.xml");
		
		SqlSession session = mybatis.getSession("oracle");		
		
		TemplateDataVO vo = new TemplateDataVO();
		vo.setSenderkey("4fe04f2c0cccde3d61ac8a3bd92c386a07d1f8cd");
		vo.setTemplateCode(TEST_USER);
		vo.setTemplateName(TEST_TEMPLATE_NAME);
		vo.setTemplateContent(TEST_SUCC_TEMPLATE_CONTENT);
		vo.setRegId(TEST_USER);
		vo.setCurStatus(0);
		
		try { 
			System.out.println(session.update("oracle.insertTemplateData", vo));
			session.commit();
		} catch (Exception e) {
			System.out.println(StringUtil.stackTrace(e));
		} finally {
			session.close();
		}
		
		
		
	}

}
