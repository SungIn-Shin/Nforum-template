package ssi.lib.utils;

public class CodeMapper {
	
	public static String getDesc(String code) {
		String desc = "";
		
		switch (code) {
		
		// MTS CODE
		case "200"  : desc = "성공"; break;
		case "300"  :
		case "400"  : desc = "필수 요청 변수 누락"; break;
		case "401"  : desc = "요청 인증 실패"; break;
		case "402"  : 
		case "403"  : desc = "권한 없음"; break;
		case "404"  : 
		case "405"  : desc = "파라미터 오류"; break;
		case "500"  : 
		case "501"  : 
		case "502"  : 
		case "503"  : 
		case "504"  : desc = "템플릿 코드 중복"; break;
		case "505"  : desc = "템플릿 이름 중복"; break;
		case "506" 	: desc = "템플릿 내용이 1000자 초과"; break;
		case "507" 	: desc = "유효하지 않은 발신 프로필"; break;
		case "508" 	: desc = "요청한 데이터가 없음"; break;
		case "509" 	: desc = "요청을 처리할 수 있는 상태가 아님 (ex. 템플릿 검수요청 가능한 상태가 아님)"; break;
		case "999" 	: desc = "엠티에스 - 카카오톡 연동 오류"; break;
		
		// 모듈 자체 코드
		case "5000" : desc = "";
		case "5001" : desc = "DB Insert or Update Fail";
		case "5002" : desc = "";
		case "5003" : desc = "";
		case "5004" : desc = "";
		case "5005" : desc = "";
		default:
			desc = "지원하지 않는 코드 : " + code;
			break;
		}
		return desc;
	}
}
