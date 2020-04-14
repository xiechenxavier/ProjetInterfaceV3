package application;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestJson01 {
	public static void main(String[] args) {
		//��ʼ��JSONObject ����һ
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("Name", "Tom");
		jsonObject1.put("age", "11");

		//��ʼ��JSONObject ������
		JSONObject jsonObject2 = new JSONObject("{'Name':'Tom','age':'11'}");

		//��ʼ��JSONArray ����һ
		JSONArray jsonArray1 = new JSONArray();
		jsonArray1.put("abc");
		jsonArray1.put("xyz");

		//��ʼ��JSONArray ������
		JSONArray jsonArray2 = new JSONArray("['abc','xyz']");

		System.out.println("jsonObject1:" + "\r" + jsonObject1.toString());
		System.out.println("jsonObject2:" + "\r" + jsonObject2.toString());
		System.out.println("jsonArray1:" + "\r" + jsonArray1.toString());
		System.out.println("jsonArray2:" + "\r" + jsonArray2.toString());

	}
}