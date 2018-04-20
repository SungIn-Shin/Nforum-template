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
 * @author �ż��� MTS(īī�� �˸��� ���� 1�� ��ü) ���ø� �ڵ����� �԰ݼ��� �������� ��������ϴ�. ���ø� ���, ����(��������
 *         ����),����, ��ȸ ����� ������ ������ ��� API�� HTTP REQUEST�� POST �����(form-data) ���·�
 *         ���۵Ǹ� RESPONSE�� JSON(String) ���·� �޽��ϴ�. MTS_�˸������ø� �ڵ�ȭ �����԰ݼ�_v1.0.docs
 *         ������ �����Ͻñ� �ٶ��ϴ�.
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
	 * ���ø� ��� URI
	 */
	public static final String REG_TEMPLATE_URI = "/mts/api/create/template";

	/**
	 * ���ø� ��ȸ URI
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
				// ���
				httpPost = registTemplate(vo);
				break;
			case SELECT:
				// ��ȸ
				httpPost = selectTemplate(vo);
				break;
			case DELETE:
				// ����
				httpPost = deleteTemplate(vo);
				break;
			case INSPECT:
				httpPost = inspectTemplate(vo);
				break;
			case MODIFY:
				httpPost = modifyTemplate(vo);
				break;
			default:
				responseJson = "{\"code\":\"400\", \"data\":\"�������� �ʴ� ������ϴ�. (��� ��ü �Ǵ�)\"}";
				break;
			}
		} catch (UnsupportedEncodingException e1) {
			responseJson = "{\"code\":\"500\", \"data\":\"�������� �ʴ� ���ڵ� Ÿ�� (��� ��ü �Ǵ�)\"}";
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
		// code 400 : �ʼ� ��û ���� ����
		if ("400".equals(code)){
			errMsg = "[400] : �ʼ� ��û ���� ����. �����ڿ��� �����ϼ���.";
		}
		// code 401 : ��û ���� ����
		else if ("401".equals(code)){
			errMsg = "[401] : ��û ���� ����. �����ڿ��� �����ϼ���.";
		}
		// code 403 : ���� ����
		else if ("403".equals(code)) {
			errMsg = "[403] : ���� ����. �����ڿ��� �����ϼ���.";
		}
		// code 405 : �Ķ���� ����
		else if ("405".equals(code)){
			errMsg = "[405] : �Ķ���� ����. �����ڿ��� �����ϼ���.";
		}
		// code 504 : ���ø� �ڵ� �ߺ�
		else if ("504".equals(code)){
			errMsg = "[504] : ���ø� �ڵ� �ߺ�. �����ڿ��� �����ϼ���.";
		}
		// code 505 : ���ø� �̸� �ߺ�
		else if ("505".equals(code)){
			errMsg = "[505] : ���ø� �̸� �ߺ�. �����ڿ��� �����ϼ���.";
		}
		// code 506 : ���ø� ���� 1000�� �ʰ�
		else if ("506".equals(code)){
			errMsg = "[506] : ���ø� ���� 1000�� �ʰ�. �����ڿ��� �����ϼ���.";
		}
		// code 507 : ��ȿ���� ���� �߽� ������
		else if ("507".equals(code)){
			errMsg = "[507] : ��ȿ���� ���� �߽� ������. �����ڿ��� �����ϼ���.";
		}
		// code 508 : ��û �����Ͱ� ����
		else if ("508".equals(code)){
			errMsg = "[508] : ��û �����Ͱ� ����. �����ڿ��� �����ϼ���.";
		}
		// code 509 : ��û�� ó���� �� �ִ� ���°� �ƴ�. (���ø� �˼���û ������ ���°� �ƴ�)
		else if ("509".equals(code)){
			errMsg = "[509] : ��û ó�� ���°� �ƴ�. �����ڿ��� �����ϼ���.";
		}
		// code 999 : MTS - īī���� ���� ����
		else if ("999".equals(code)){
			errMsg = "[999] : MTS-īī���� ���� ����. �����ڿ��� �����ϼ���.";
		}
		return errMsg;
	}

}
