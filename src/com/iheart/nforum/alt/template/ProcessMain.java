package com.iheart.nforum.alt.template;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.iheart.nforum.alt.template.dao.MTSTemplateManager;
import com.iheart.nforum.alt.template.dao.MTSTemplateManager.MODE;
import com.iheart.nforum.alt.template.dao.TemplateDAO;
import com.iheart.nforum.alt.template.model.Button;
import com.iheart.nforum.alt.template.model.RequestData;
import com.iheart.nforum.alt.template.model.ResponseJsonData;
import com.iheart.nforum.alt.template.model.TemplateDataVO;

public class ProcessMain implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessMain.class);
	
	private TemplateDAO dao;
	
	private TemplateProcess process;
	
	private Gson gson = new Gson();
	
	
	public static void main(String[] args) {
		ProcessMain main = new ProcessMain();
		new Thread(main).start();
	}
	public ProcessMain() {
		init();
	}
	
	
	private void init() {
		dao = TemplateDAO.getInstance();
		process = new TemplateProcess(dao);
	}
	
	
	
	
	@Override
	public void run() {
		//
		
		while(true) {
			List<TemplateDataVO> tmpList = dao.selectTemplateData();
			
			if(!tmpList.isEmpty()) {
				logger.info("");
				logger.info("DataBase Template Data List : " + tmpList.size());
				logger.info("");
				for(TemplateDataVO dataVO : tmpList)
				{
					String templateCode = dataVO.getTemplateCode();
					List<Button> btnList = dao.selectTemplateButtons(templateCode);
					String buttons = btnList.isEmpty() ? null : makeButtonArray(btnList);
					RequestData reqData = new RequestData(dataVO, buttons);
					logger.info(reqData.toString());
					// 1. MTS 템플릿 등록 로직
					process.templateRegist(dataVO, reqData);
				}
			} 
			else {
				// 1초 sleep...
				logger.debug("DataBase Template Data List IS EMPTY ... 3 second sleep ...");
				try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * {buttons1...}, {buttons2...}, {buttons3...} 형태로 만들어줌
	 * @param btnList
	 * @return
	 */
	private String makeButtonArray(List<Button> btnList) {
		String rslt = "";
		int listSize = btnList.size();
		for(int i = 0; i < listSize; i++) {
			if(i < listSize - 1) {
				rslt += gson.toJson(btnList.get(i)) + ",";
			} else {
				rslt += gson.toJson(btnList.get(i));
			}
		}
		return rslt;
	}
}
