package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class MyHttpClient {

	private CloseableHttpClient client;

	private HttpGet Get;
	private HttpPost Post;
	private HttpResponse Response;

	private Map<String, String> parameterMap = new HashMap<String, String>();

	private HttpState state = HttpState.Error;

	private enum HttpState {
		Get, Post, Error
	}

	public MyHttpClient() {
		client = HttpClients.createDefault();
		//HttpParams params = httpclient.getParams();
		//params.setParameter(ClientPNames.HANDLE_REDIRECTS, false);
	}

	public void Close() throws IOException {
		client.close();
	}

	/**
	 * 新建HTTP的GET命令
	 */
	public void NewHttpGet(String URL) {
		if(state == HttpState.Get)
		{
			Get.releaseConnection();
		}
		else if(state == HttpState.Post)
		{
			Post.releaseConnection();
		}
		Get = new HttpGet(URL);
		state = HttpState.Get;
	}

	/**
	 * 新建HTTP的POST命令
	 */
	public void NewHttpPost(String URL) {
		if(state == HttpState.Get)
		{
			Get.releaseConnection();
		}
		else if(state == HttpState.Post)
		{
			Post.releaseConnection();
		}
		parameterMap.clear();
		Post = new HttpPost(URL);
		state = HttpState.Post;
	}

	/**
	 * 对于POST请求，加入请求数�?
	 */
	public void AddParam(String key, String value) {
		if (state == HttpState.Post) {
			parameterMap.put(key, value);
		}
	}

	/**
	 * 对于HTTP请求，添加自定义消息�?
	 */
	public void AddHeader(String name, String value) {
		if (state == HttpState.Get) {
			Get.addHeader(name, value);
		} else {
			Post.addHeader(name, value);
		}
	}

	public  List<NameValuePair> getParam(Map<String, String> parameterMap) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry parmEntry = (Entry) it.next();
			param.add(new BasicNameValuePair((String) parmEntry.getKey(),
					(String) parmEntry.getValue()));
		}
		return param;
	}
	
	/**
	 * 运用构�?好的HTTP请求
	 */
	public void Visit() throws ClientProtocolException, IOException {
		if (state == HttpState.Get) {
			Response = client.execute(Get);
		} else {
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
					getParam(parameterMap), "UTF-8");
			Post.setEntity(postEntity);
			Response = client.execute(Post);
		}
	}

	/**
	 * 获取HTTP请求的响应消息实�?
	 */
	public String GetResponseStr() throws Exception {
		HttpEntity ResponseEntity = Response.getEntity();
		if (ResponseEntity == null) {
			return "";
		}
		
		String ResponseString = EntityUtils.toString(ResponseEntity);
		return ResponseString;
	}
	
	
	/**
	 * 获取HTTP请求的响应消息实�?
	 */
	public byte[] GetResponseByte() throws Exception {
		HttpEntity ResponseEntity = Response.getEntity();
		if (ResponseEntity == null) {
			return new byte[0];
		}
		return EntityUtils.toByteArray(ResponseEntity);
	}

	/**
	 * 判断HTTP请求的响应是否含有响应头
	 */
	public Boolean ResponseContainsHeader(String name) {
		return Response.containsHeader(name);
	}

	/**
	 * 获取HTTP请求的特定响应头
	 */
	public String GetResponseHeaderStr(String name) {
		return Response.getFirstHeader(name).getValue();
	}
	
	/**
	 * 打印返回数据
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void PrintResponseAllStr() throws ParseException, IOException {
		printResponse(Response);
		return;
	}

	private static void printResponse(HttpResponse httpResponse)
			throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状�?
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString);
		}
	}
}
