package com.iheart.nforum.alt.template.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.RequestLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.iheart.nforum.alt.template.model.RequestData;


/**
 * @author 신성인 MTS(카카오 알림톡 연동 1차 업체) 템플릿 자동연동 규격서를 바탕으로 만들었습니다. 템플릿 등록, 수정(구현하지
 *         않음),삭제, 조회 기능을 가지고 있으며 모든 API는 HTTP REQUEST는 POST 방식의(form-data) 형태로
 *         전송되며 RESPONSE는 JSON(String) 형태로 받습니다. MTS_알림톡템플릿 자동화 연동규격서_v1.0.docs
 *         문서를 참조하시길 바랍니다.
 */
public class MTSTemplateManager {
	public static enum MODE {
		REGIST, SELECT, DELETE, MODIFY, INSPECT
	}
	
	/**
	 * MTS URL
	 */
	public static final String URL = "http://talks.mtsco.co.kr";

	/**
	 * 템플릿 등록 URI
	 */
	public static final String REG_TEMPLATE_URI = "/mts/api/create/template";

	/**
	 * 템플릿 조회 URI
	 */
	public static final String SELECT_TEMPLATE_URI = "/mts/api/check/template";
	
	private final Log logger = LogFactory.getLog(MTSTemplateManager.class);
	
	private CloseableHttpClient httpClient = null;
	
	TemplateDAO dao = TemplateDAO.getInstance();
	
	public MTSTemplateManager() {
		init();
	}
	
	
	
	private void init() {
		
	}



	public String requestPost(RequestData vo, MODE mode) {
		httpClient = HttpClients.createDefault();
		HttpPost httpPost = null;
		String responseJson = "";
		try {
			switch (mode) {
			case REGIST:
				// 등록
				httpPost = registTemplate(vo);
				break;
			case SELECT:
				// 조회
				httpPost = selectTemplate(vo);
				break;
			case DELETE:
				// 삭제
				httpPost = deleteTemplate(vo);
				break;
			case INSPECT:
				httpPost = inspectTemplate(vo);
				break;
			case MODIFY:
				httpPost = modifyTemplate(vo);
				break;
			default:
				responseJson = "{\"code\":\"400\", \"data\":\"지원하지 않는 기능힙니다. (모듈 자체 판단)\"}";
				break;
			}
		} catch (UnsupportedEncodingException e1) {
			responseJson = "{\"code\":\"500\", \"data\":\"지원하지 않는 인코딩 타입 (모듈 자체 판단)\"}";
			e1.printStackTrace();
		}
		
		try {
			
			CloseableHttpResponse res = httpClient.execute(httpPost);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
			String readLine = "";
			StringBuffer sb = new StringBuffer();
			while((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			responseJson = sb.toString();
			return responseJson;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return responseJson;
	}


	private HttpPost inspectTemplate(RequestData vo) throws UnsupportedEncodingException {
		String REG_TEMPLATE_URI = "/mts/api/inspect/template";
		HttpPost httpPost = new HttpPost(URL + REG_TEMPLATE_URI);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("senderKey"			, vo.getSenderKey()));
		params.add(new BasicNameValuePair("templateCode"		, vo.getTemplateCode()));
		params.add(new BasicNameValuePair("senderKeyType"		, "S"));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		// Request print
		logger.info(httpRequestToString(httpPost));
		return httpPost;
	}

	private HttpPost deleteTemplate(RequestData vo) throws UnsupportedEncodingException {
		//
		String REG_TEMPLATE_URI = "/mts/api/delete/template";
		HttpPost httpPost = new HttpPost(URL + REG_TEMPLATE_URI);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("senderKey"			, vo.getSenderKey()));
		params.add(new BasicNameValuePair("templateCode"		, vo.getTemplateCode()));
		params.add(new BasicNameValuePair("senderKeyType"		, "S"));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		// Request print
		logger.info(httpRequestToString(httpPost));
		return httpPost;
	}
	
	private HttpPost modifyTemplate(RequestData vo) throws UnsupportedEncodingException {
		//
		String REG_TEMPLATE_URI = "/mts/api/modify/template";
		HttpPost httpPost = new HttpPost(URL + REG_TEMPLATE_URI);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("templateName"			, vo.getTemplateName()));
		params.add(new BasicNameValuePair("templateCode"			, vo.getTemplateCode()));
		params.add(new BasicNameValuePair("templateContent"			, vo.getTemplateContent()));
		params.add(new BasicNameValuePair("senderKey"				, vo.getSenderKey()));
		params.add(new BasicNameValuePair("senderKeyType"			, "S"));
		params.add(new BasicNameValuePair("orgSenderKey"			, vo.getSenderKey()));
		params.add(new BasicNameValuePair("orgSenderKeyType"		, "S"));
		params.add(new BasicNameValuePair("orgTemplateCode"			, vo.getTemplateCode()));
		params.add(new BasicNameValuePair("buttons"					, null));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		// Request print
		logger.info(httpRequestToString(httpPost));
		return httpPost;
	}

	private HttpPost selectTemplate(RequestData vo) throws UnsupportedEncodingException {
		//
		String SELECT_TEMPLATE_URI = "/mts/api/state/template";
		HttpPost httpPost = new HttpPost(URL + SELECT_TEMPLATE_URI);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("senderKey"			, vo.getSenderKey()));
		params.add(new BasicNameValuePair("templateCode"		, vo.getTemplateCode()));
		params.add(new BasicNameValuePair("senderKeyType"		, "S"));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		// Request print
		logger.info(httpRequestToString(httpPost));
		return httpPost;
	}




	private HttpPost registTemplate(RequestData vo) throws UnsupportedEncodingException {
		String REG_TEMPLATE_URI = "/mts/api/create/template";
		HttpPost httpPost = new HttpPost(URL + REG_TEMPLATE_URI);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("senderKey"			, vo.getSenderKey()));
		params.add(new BasicNameValuePair("templateCode"		, vo.getTemplateCode()));
		params.add(new BasicNameValuePair("templateName"		, vo.getTemplateName()));
		params.add(new BasicNameValuePair("templateContent"		, vo.getTemplateContent()));
		params.add(new BasicNameValuePair("buttons"				, null));
		params.add(new BasicNameValuePair("inspectionStatus"	, "REG"));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		// Request print
		logger.info(httpRequestToString(httpPost));
		return httpPost;
	}
	
	public String httpRequestToString(HttpPost post) {
		RequestLine reqLine = post.getRequestLine();
		StringBuilder sb = new StringBuilder();
		// RequestLine 
		// POST /insert/template HTTP/1.1
		sb.append(reqLine.getMethod()).append(" ").append(reqLine.getUri()).append(" ").append(reqLine.getProtocolVersion()).append("\r\n");
		
		// Header[]
		Header[] header = post.getAllHeaders();
		for(Header h : header) {
			sb.append(h.getName()).append(" : ").append(h.getName()).append("\r\n");
		}
		
		HttpEntity entity = post.getEntity();
		try {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String brLine = "";
			
			while((brLine = br.readLine()) != null) {
				sb.append(brLine);
			}
			sb.append("\r\n");
			logger.info("HTTP Request -> " + sb.toString());
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public String getCodeMsg(String code) {
		String errMsg = "";
		// code 400 : 필수 요청 변수 누락
		if ("400".equals(code)){
			errMsg = "[400] : 필수 요청 변수 누락. 관리자에게 문의하세요.";
		}
		// code 401 : 요청 인증 실패
		else if ("401".equals(code)){
			errMsg = "[401] : 요청 인증 실패. 관리자에게 문의하세요.";
		}
		// code 403 : 권한 없음
		else if ("403".equals(code)) {
			errMsg = "[403] : 권한 없음. 관리자에게 문의하세요.";
		}
		// code 405 : 파라미터 오류
		else if ("405".equals(code)){
			errMsg = "[405] : 파라미터 오류. 관리자에게 문의하세요.";
		}
		// code 504 : 템플릿 코드 중복
		else if ("504".equals(code)){
			errMsg = "[504] : 템플릿 코드 중복. 관리자에게 문의하세요.";
		}
		// code 505 : 템플릿 이름 중복
		else if ("505".equals(code)){
			errMsg = "[505] : 템플릿 이름 중복. 관리자에게 문의하세요.";
		}
		// code 506 : 템플릿 내용 1000자 초과
		else if ("506".equals(code)){
			errMsg = "[506] : 템플릿 내용 1000자 초과. 관리자에게 문의하세요.";
		}
		// code 507 : 유효하지 않은 발신 프로필
		else if ("507".equals(code)){
			errMsg = "[507] : 유효하지 않은 발신 프로필. 관리자에게 문의하세요.";
		}
		// code 508 : 요청 데이터가 없음
		else if ("508".equals(code)){
			errMsg = "[508] : 요청 데이터가 없음. 관리자에게 문의하세요.";
		}
		// code 509 : 요청을 처리할 수 있는 상태가 아님. (템플릿 검수요청 가능한 상태가 아님)
		else if ("509".equals(code)){
			errMsg = "[509] : 요청 처리 상태가 아님. 관리자에게 문의하세요.";
		}
		// code 999 : MTS - 카카오톡 연동 오류
		else if ("999".equals(code)){
			errMsg = "[999] : MTS-카카오톡 연동 오류. 관리자에게 문의하세요.";
		}
		return errMsg;
	}

}
