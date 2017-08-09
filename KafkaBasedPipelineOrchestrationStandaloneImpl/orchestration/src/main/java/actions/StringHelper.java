package actions;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringHelper {

	String rev(String str){
		return "BA";
	}
	
	public void jsonPrint(){
		JSONArray jsonArr = new JSONArray();
		JSONObject jobj = new JSONObject();
		jobj.put("name", "nam");
		jsonArr.put(jobj);
		jobj = new JSONObject();
		jobj.put("name", "mer");
		jsonArr.put(0,jobj);
		System.out.println(jsonArr.toString());
	}
	/*
	public static void main(String[] args){
		StringHelper strobj = new StringHelper();
		strobj.jsonPrint();
	}*/
}
