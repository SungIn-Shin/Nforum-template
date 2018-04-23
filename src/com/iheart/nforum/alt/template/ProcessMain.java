package com.iheart.nforum.alt.template;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.iheart.nforum.alt.template.dao.TemplateDAO;
import com.iheart.nforum.alt.template.model.Button;
import com.iheart.nforum.alt.template.model.RequestData;
import com.iheart.nforum.alt.template.model.TemplateDataVO;

public class ProcessMain {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessMain.class);
	
	private static TemplateDAO dao;
	
	private static TemplateProcess process;
	
	private static Gson gson = new Gson();
	
	public ProcessMain() {
		init();
	}
	
	private void init() {
		dao = TemplateDAO.getInstance();
		process = new TemplateProcess(dao);
	}
	
	public void startProcess() {
		startRegister();
		startInspecter();
		startModifier();
		startStateChecker();
	}
	
	
	private void startStateChecker() {
		
	}

	/**
	 * 등록용 T
	 */
	private void startRegister() {
		
		new Thread(()-> {
			//
			while(true) {
				//
				List<TemplateDataVO> tmpList = dao.selectRegistTemplateData();
				if(!tmpList.isEmpty()) {
					//
					logger.info("================================================");
					logger.info("[REG] - REQ Data List Size : " + tmpList.size());
					logger.info("================================================");
					for(TemplateDataVO dataVO : tmpList) {
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
		}, "RegistJobThread");
	}
	
	/**
	 * 검수요청 THread
	 */
	private void startInspecter() {
		// 검수요청 Thread
		new Thread(()->{
			//
			while(true) {
				//
				List<TemplateDataVO> tmpList = dao.selectInspectTemplateList();
				if(!tmpList.isEmpty()) {
					//
					logger.info("================================================");
					logger.info("[REQ] - REQ Data List Size : " + tmpList.size());
					logger.info("================================================");
					for(TemplateDataVO dataVO : tmpList) {
						String templateCode = dataVO.getTemplateCode();
						List<Button> btnList = dao.selectTemplateButtons(templateCode);
						String buttons = btnList.isEmpty() ? null : makeButtonArray(btnList);
						RequestData reqData = new RequestData(dataVO, buttons);
						logger.info(reqData.toString());
						// 1. MTS 템플릿 등록 로직
						process.templateInspect(dataVO, reqData);
					}
				}
				else {
					// 1초 sleep...
					logger.debug("DataBase Template Data List IS EMPTY ... 3 second sleep ...");
					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}, "InspectJobThread").start();
	}
	
	
	private void startModifier() {
		new Thread(()->{
			//
			while(true) {
				//
				List<TemplateDataVO> tmpList = dao.selectModifyTemplateList();
				if(!tmpList.isEmpty()) {
					//
					logger.info("[MOD] - REQ Data List Size : " + tmpList.size());
					for(TemplateDataVO dataVO : tmpList) {
						String templateCode = dataVO.getTemplateCode();
						List<Button> btnList = dao.selectTemplateButtons(templateCode);
						String buttons = btnList.isEmpty() ? null : makeButtonArray(btnList);
						RequestData reqData = new RequestData(dataVO, buttons);
						logger.info(reqData.toString());
						// 1. MTS 템플릿 등록 로직
						process.templateModify(dataVO, reqData);
					}
				}
				else {
					// 1초 sleep...
					logger.debug("DataBase Template Data List IS EMPTY ... 3 second sleep ...");
					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}, "ModifyJobThread").start();
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
	
	public static void main(String[] args) {
		TestInsertMain test = new TestInsertMain();
		test.insert();
		
		ProcessMain main = new ProcessMain();
		main.startProcess();
	}
	
	
	
}
