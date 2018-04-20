package ssi.lib.utils;

public class CodeMapper {
	
	public static String getDesc(String code) {
		String desc = "";
		
		switch (code) {
		
		// MTS CODE
		case "200"  : desc = "����"; break;
		case "300"  :
		case "400"  : desc = "�ʼ� ��û ���� ����"; break;
		case "401"  : desc = "��û ���� ����"; break;
		case "402"  : 
		case "403"  : desc = "���� ����"; break;
		case "404"  : 
		case "405"  : desc = "�Ķ���� ����"; break;
		case "500"  : 
		case "501"  : 
		case "502"  : 
		case "503"  : 
		case "504"  : desc = "���ø� �ڵ� �ߺ�"; break;
		case "505"  : desc = "���ø� �̸� �ߺ�"; break;
		case "506" 	: desc = "���ø� ������ 1000�� �ʰ�"; break;
		case "507" 	: desc = "��ȿ���� ���� �߽� ������"; break;
		case "508" 	: desc = "��û�� �����Ͱ� ����"; break;
		case "509" 	: desc = "��û�� ó���� �� �ִ� ���°� �ƴ� (ex. ���ø� �˼���û ������ ���°� �ƴ�)"; break;
		case "999" 	: desc = "��Ƽ���� - īī���� ���� ����"; break;
		
		// ��� ��ü �ڵ�
		case "5000" : desc = "";
		case "5001" : desc = "DB Insert or Update Fail";
		case "5002" : desc = "";
		case "5003" : desc = "";
		case "5004" : desc = "";
		case "5005" : desc = "";
		default:
			desc = "�������� �ʴ� �ڵ� : " + code;
			break;
		}
		return desc;
	}
}
