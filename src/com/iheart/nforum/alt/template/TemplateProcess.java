package com.iheart.nforum.alt.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.iheart.nforum.alt.template.dao.MTSTemplateManager;
import com.iheart.nforum.alt.template.dao.MTSTemplateManager.MODE;
import com.iheart.nforum.alt.template.dao.TemplateDAO;
import com.iheart.nforum.alt.template.model.RequestData;
import com.iheart.nforum.alt.template.model.ResponseJsonData;
import com.iheart.nforum.alt.template.model.TemplateDataVO;

public class TemplateProcess {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateProcess.class);
	
	private TemplateDAO dao;
	
	MTSTemplateManager api = new MTSTemplateManager();
	
	private Gson gson = new Gson();
	
	public TemplateProcess(TemplateDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @param dataVO
	 * @param reqData
	 * @return 
	 */
	public void templateRegist(TemplateDataVO dataVO, RequestData reqData) {
		//
		int updateCurStatus1 = updateCurStatusAndProcessIng(dataVO, 1, "Y");
		if(updateCurStatus1 > 0) {
			//
			String regResJson = api.requestPost(reqData, MODE.REGIST);
			logger.info("REQ-RES-JSON : " + regResJson);
			ResponseJsonData regRes = gson.fromJson(regResJson, ResponseJsonData.class);
			
			int updateCurStatus2 = updateCurStatusAndProcessIng(dataVO, 2, "N");
			if (updateCurStatus2 > 0) {
				String code = regRes.getCode();
				if ("200".equals(code)) {
					logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "//DESC:Request Success");
					// ��� ���� ���ó��...
					int succDataCnt = dao.updateREGOrMODResponseTemplateData(regRes);
					if(succDataCnt > 0) {
						// ��� ����
						logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "//DESC:Update REG Response Template Data Success");
						int succHistory = dao.insertTemplateHistory(dataVO, regRes);
						if(succHistory > 0) {
							logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "//DESC:Insert Template History Success");
						}
						else {
							logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "//DESC:Insert Template History Fail");
						}
					}
					else {
						// *****************************************************************
						// CUR_STATUS = 2, INSPECTION_STATUS = null
						// CUR_STATUS�� ������Ʈ �ߴµ�, RES �����͸� ������Ʈ ġ���� DB���������� ���
						logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "//DESC:Update Template Response Data Fail");
					}
				}
				else {
					// CUR_STATUS = 2, INSPECTION_STATUS = NULL
					// ��� ���� ���ó��...
					int failHistory = dao.insertTemplateHistory(dataVO, regRes);
					if(failHistory > 0) {
						logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "DESC:Insert Template History Success");
					}
					else {
						logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "DESC:Insert Template History Fail");
					}
				}
			} 
			else {
				// *****************************************************************
				// CUR_STATUS = 1, INSPECTION_STATUS = null
				// CUR_STATUS�� 2�� ����(�������) �Ϸ� �ϴ� DB������ ���� ���
				logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//DESC:CUR_STATUS [1 -> 2] UPDATE Fail");
			}
			
		} 
		else {
			logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-REQ//DESC:CUR_STATUS [0 -> 1] UPDATE Fail");
		}
	}
	
	
	/**
	 * ���ø� �˼� ��û
	 * CUR_STATUS IN (2, 7)
	 * PROCESS_ING 		= 'N'
	 * INSPECTION_STATUS 	= 'REG'
	 * @param dataVO
	 * @param reqData
	 */
	public void templateInspect(TemplateDataVO dataVO, RequestData reqData) {
		
		int updateCurStatus3 = updateCurStatusAndProcessIng(dataVO, 3, "Y");
		
		if (updateCurStatus3 > 0) {
			String regResJson = api.requestPost(reqData, MODE.INSPECT);
			logger.info("REG-RES-JSON : " + regResJson);
			ResponseJsonData reqRes = gson.fromJson(regResJson, ResponseJsonData.class);
			int updateCurStatus4 = updateCurStatusAndProcessIng(dataVO, 4, "N");
			if(updateCurStatus4 > 0) {
				String code = reqRes.getCode();
				if ("200".equals(code)) {
					int reqSuccDataUpdate = dao.updateREQResponseTemplateData(dataVO);
					if (reqSuccDataUpdate > 0) {
						logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REQ-RES//CODE://" + code + "//DESC:Request Success");
						int succHistory = dao.insertTemplateHistory(dataVO, reqRes);
						if(succHistory > 0) {
							logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REQ-RES//CODE://" + code + "//DESC:Insert Template History Success");
						}
						else {
							logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REQ-RES//CODE://" + code + "//DESC:Insert Template History Fail");
						}
					}
					else {
						logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REQ-RES//CODE://" + code + "//DESC:Update Template Response Data Fail");
					}
				} 
				else {
					// CUR_STATUS = 2, INSPECTION_STATUS = NULL
					// ��� ���� ���ó��...
					int failHistory = dao.insertTemplateHistory(dataVO, reqRes);
					if(failHistory > 0) {
						logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REQ-RES//CODE://" + code + "DESC:Insert Template History Success");
					}
					else {
						logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REQ-RES//CODE://" + code + "DESC:Insert Template History Fail");
					}
				}
			}
			else {
				logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//DESC:CUR_STATUS [3 -> 4] UPDATE Fail");
			}
		} 
		else {
			// CUR_STATUS = 3 �������. 
			logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REQ-REQ//DESC:CUR_STATUS [2 -> 3] UPDATE Fail");
		}
	}
	
	
	/**
	 * ������û ���� ������ ó��
	 * CUR_STATE = 5
	 * PROCESS_ING = 'N'
	 * STATUS = 'R'
	 * INSPECTION_STATUS IN ('REG', 'REJ')
	 * @param dataVO
	 * @param reqData
	 */
	public void templateModify(TemplateDataVO dataVO, RequestData reqData) {
		int updateCurStatus6 = updateCurStatusAndProcessIng(dataVO, 6, "Y");
		
		if (updateCurStatus6 > 0) {
			String modResJson = api.requestPost(reqData, MODE.MODIFY);
			logger.info("MOD-RES-JSON : " + modResJson);
			ResponseJsonData modRes = gson.fromJson(modResJson, ResponseJsonData.class);
			int updateCurStatus7 = updateCurStatusAndProcessIng(dataVO, 7, "N");
			if(updateCurStatus7 > 0) {
				String code = modRes.getCode();
				if ("200".equals(code)) {
					logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//CODE://" + code + "//DESC:Request Success");
					int modRequestUpdateCnt = dao.updateREGOrMODResponseTemplateData(modRes);
					if(modRequestUpdateCnt > 0) {
						// ��� ����
						logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//CODE://" + code + "//DESC:Update MOD Response Template Data Success");
						int succHistory = dao.insertTemplateHistory(dataVO, modRes);
						if(succHistory > 0) {
							logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//CODE://" + code + "//DESC:Insert Template History Success");
						}
						else {
							logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//CODE://" + code + "//DESC:Insert Template History Fail");
						}
					}
					else {
						// CUR_STATUS�� ������Ʈ �ߴµ�, RES �����͸� ������Ʈ ġ���� DB���������� ���
						logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//CODE://" + code + "//DESC:Update Template Response Data Fail");
					}
				} 
				else {
					// ��� ���� ���ó��...
					int failHistory = dao.insertTemplateHistory(dataVO, modRes);
					if(failHistory > 0) {
						logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//CODE://" + code + "DESC:Insert Template History Success");
					}
					else {
						logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//CODE://" + code + "DESC:Insert Template History Fail");
					}
				}
			} 
			else {
				logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-RES//DESC:CUR_STATUS [6 -> 7] UPDATE Fail");
			}
		} 
		else {
			logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:MOD-REQ//DESC:CUR_STATUS [5 -> 6] UPDATE Fail");
		}
	}
	
	
	
	/**
	 * ���ø� ���� ��ȸ
	 * CUR_STATUS = 4, 9
	 * INSPECTION_STATUS NOT IN ('APR', 'KRR', 'REJ')
	 * @param dataVO
	 * @param reqData
	 */
	public void templateStateCheck(TemplateDataVO dataVO, RequestData reqData) {
		// senderkey, templatecode
		String modResJson = api.requestPost(reqData, MODE.MODIFY);
		logger.info("MOD-RES-JSON : " + modResJson);
		ResponseJsonData stateRes = gson.fromJson(modResJson, ResponseJsonData.class);
		String code = stateRes.getCode();
		if ("200".equals(code)) {
			String inspectStatus = stateRes.getData().getInspectionStatus();
			if("APR".equals(inspectStatus) || "KRR".equals(inspectStatus) || "REJ".equals(inspectStatus)) {
				
			}
			else {
				// ���� �������γ��...
			}
		}
		else {
			// 200 �ڵ尡 �ȳѾ�Դµ� ��� ó���ұ�??��;;;
			
		}
		
	}
	
	
	private int updateCurStatusAndProcessIng(TemplateDataVO dataVO, int curStatus, String processIng) {
		dataVO.setCurStatus(curStatus);
		dataVO.setProcessIng(processIng);
		return dao.updateCurStatusAndProcessIng(dataVO);
	}
	
}
