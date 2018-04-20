
public class TestCode {
	public static void main(String[] args) {
		String[] strArr = new String[3];
		strArr[0] = "aa";
		strArr[1] = "bb";
		strArr[2] = "cc";
		String rslt = "";
		System.out.println(strArr.length);
		for(int i = 0; i < strArr.length; i++) {
			if(i < strArr.length -1 ) {
				rslt += strArr[i] + ",";
			} else {
				rslt += strArr[i];
			}
		}
		System.out.println(rslt);
	}
}
