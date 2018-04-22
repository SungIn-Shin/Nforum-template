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
	public String templateRegist(TemplateDataVO dataVO, RequestData reqData) {
		String resCode = "2000";
		dataVO.setCurStatus(1);
		int updateCurStatus1 = dao.updateTemplateCurStatus(dataVO);
		
		if(updateCurStatus1 > 0) {
			//
			String regResJson = api.requestPost(reqData, MODE.REGIST);
//			logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
//			logger.debug(regResJson);
//			logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			ResponseJsonData regRes = gson.fromJson(regResJson, ResponseJsonData.class);
			dataVO.setCurStatus(2);
			int updateCurStatus2 = dao.updateTemplateCurStatus(dataVO);
			if (updateCurStatus2 > 0) {
				String code = regRes.getCode();
				if ("200".equals(code)) {
					logger.info("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "//DESC:Request Success");
					// 등록 성공 결과처리...
					int succDataCnt = dao.updateREGResponseTemplateData(regRes);
					if(succDataCnt > 0) {
						// 모두 성공
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
						// CUR_STATUS는 업데이트 했는데, RES 데이터를 업데이트 치려니 DB에러가났는 경우
						logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//CODE://" + code + "//DESC:Update Template Response Data Fail");
					}
				}
				else {
					// CUR_STATUS = 2, INSPECTION_STATUS = NULL
					// 등록 실패 결과처리...
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
				// CUR_STATUS를 2로 변경(결과수신) 하려 하니 DB에러가 났는 경우
				logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-RES//DESC:CUR_STATUS [1 -> 2] UPDATE Fail");
			}
			
		} 
		else {
			logger.error("TEMPLATE_CODE:" + dataVO.getTemplateCode() + "//ACTION:REG-REQ//DESC:CUR_STATUS [0 -> 1] UPDATE Fail");
		}
		
		return resCode;
	}
}
