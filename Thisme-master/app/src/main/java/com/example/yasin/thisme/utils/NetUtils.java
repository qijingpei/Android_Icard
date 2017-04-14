package com.example.yasin.thisme.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.example.yasin.thisme.activity.RegistActivity;
import com.example.yasin.thisme.model.User;

/*
 * ����Ĺ�����
 * 		>HttpClient�ķ��ʷ�ʽ
 */
public class NetUtils {
	
	private static final String TAG = "NetUtils";
	
	/*
	 * ʹ��post�ķ�ʽ��¼
	 */
	public static String registOfPost(User user) {
		
		HttpClient client=null;
		try {
			//����һ���ͻ���
			client = new DefaultHttpClient();

			//����post����
			HttpPost post = new HttpPost("http://192.168.1.100:8080/ServerItheima28/RegistServlet");//��ʱ��ʹ��LoginServlet������Ч��һ��

			//JSON封装数据
			JSONObject object = new JSONObject();
			object.put("phoneNumber",user.getPhoneNumber());
			object.put("password",user.getPassword());
			object.put("name",user.getName());

			NameValuePair parameter = new BasicNameValuePair("Data",object.toString());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(parameter);//把JSON加进去


			//��д�������ƵĻ���������������ʱ�����룬��Ҫ�ƶ��ַ���Ϊutf-8
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");
			//���ò���
			post.setEntity(entity);

			//��ʱ��Ҫ��������ͷ��Ϣ
			//		post.addHeader("Content-Length","20");

			//ʹ�ÿͻ���ִ��post���� �� �᷵�ظ�����һ��HttpResponse����
			HttpResponse response = client.execute(post);	//��ʼִ��post����

			//ʹ����Ӧ���󣬻��״̬�룬��������
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200) {
				//ʹ����Ӧ������ʵ�壬 ���������
				InputStream is = response.getEntity().getContent();
				String text = getStringFromInputStream(is);
				return text;
			} else {
				Log.i(TAG, "网络连接失败" +statusCode);
			}
		} catch (Exception e) {
			Log.i(TAG, "post请求出现了异常" );
			e.printStackTrace();
		} finally {
			if(client !=null) {
				client.getConnectionManager().shutdown();	//�ر����Ӻ��ͷ���Դ
			}
		}
		return null;
	}

	/*
	 * ʹ��get�ķ�ʽ��¼
	 * 		>return ��¼��״̬
	 * 		>1.ͨ��url��ȡ����
	 * 		>2.�����ػ���������
	 */
	public static String loginOfGet(String phoneNumber,String password) {
		HttpClient client = null;
		try {
			//����һ���ͻ���
			client = new DefaultHttpClient();

			//����һ��get���󷽷�
			String data = "phoneNumber=" + phoneNumber + "&password=" + password;
			HttpGet get = new HttpGet(
					"http://192.168.1.100:8080/ServerItheima28/LoginServlet?"
							+ data);

			//���response��������Ӧ�������а�����״̬��Ϣ�ͷ��������ص�����
			HttpResponse response = client.execute(get);  //��ʼִ��get��������������
			
			//��ȡ��Ӧ��
			int statusCode = response.getStatusLine().getStatusCode();//��״̬�У�StatusLine���л��
			
			if(statusCode == 200) {	//����ɹ����ʵĻ�
				//��������������������ػ������Ǹ�����
				InputStream is = response.getEntity().getContent();
				String text = getStringFromInputStream(is);
				return text;
			} else {
				Log.i(TAG, "网络连接失败" + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { 
			if(client != null) {
				client.getConnectionManager().shutdown();	//�ر����Ӻ��ͷ���Դ
			}			
		}
		return null;
	}

	/*
	 * 将响应流转换成字符串
	 */
	private static String getStringFromInputStream(InputStream is) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		
		//������is�۲�
		Log.i(TAG, "流的内容是"+is.toString());
		
		//���������е����ݱ��浽�������
		while ((len = is.read(buffer)) !=-1) {//����buffer�У����ж��Ƿ��˽�β
			baos.write(buffer, 0, len);
		}
		is.close();
		
		String html = baos.toString();	//�����е�����ת�����ַ��������õı����ǣ�utf-8
		
//		String html = new String(baos.toByteArray(), "GBK");
		baos.close();
		return html;
	}

	
}




