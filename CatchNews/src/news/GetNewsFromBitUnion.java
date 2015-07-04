package news;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import Email.SendMail;
import log.Log;
import utils.ScriptConfig;
import utils.StringUtils;

public class GetNewsFromBitUnion {
	static org.slf4j.Logger logger = Log.getErrorLogger();
	static String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	static String AcceptEncoding = "";
	static String AcceptLanguage = "zh-CN,zh;q=0.8,en;q=0.6";
	static String CacheControl = "max-age=0";
	static String Connection = "keep-alive";
	static String Cookie = "_cookietime=31536000; _discuz_user=%C3%F0%C9%F1%D2%BB%BA%C5; _discuz_pw=105884e3a7c0277297b90ddae43dff6a; "
			+ "oldtopics=%0910597579%0910597576%0910597509%09; lastvisit=1434438205; __utmt=1; sid=PqHs2o7d; bu_styleid=8; "
			+ "__utma=17892390.819386564.1432964496.1434525436.1434529150.19; __utmb=17892390.3.10.1434529150; __utmc=17892390; "
			+ "__utmz=17892390.1434529150.19.6.utmcsr=bitren.com|utmccn=(referral)|utmcmd=referral|utmcct=/";
	static String Host = "www.bitunion.org";
	static String UserAgent = "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36";
	
	static String split = "��ͨ����";
	
	private static String getBitUnionAdress(){
		if(ScriptConfig.loadConfig().containsKey("Bitunion"))
			return ScriptConfig.loadConfig().get("Bitunion").toString();
		else {
			logger.error("��ȡ����BITUnion��Ϣ");
			return null;
		}
	}
	
	private static String getLikes(){
		if(ScriptConfig.loadConfig().containsKey("Attention"))
			return ScriptConfig.loadConfig().get("Attention").toString();
		else {
			logger.error("��ȡ����Attention��Ϣ");
			return null;
		}
	}
	
	private static String[] getFirstPage(String url){
		
		HttpURLConnection connection;
		String firstPage = "";
		
		// ����ƴ�յ�URL�������ӣ�URL.openConnection���������URL�����ͣ�
		try {
			URL getUrl = new URL(url);
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setReadTimeout(10000);
			
			connection.addRequestProperty("Accept",Accept);
			connection.addRequestProperty("Accept-Encoding",AcceptEncoding);
			connection.addRequestProperty("Accept-Language",AcceptLanguage);
			connection.addRequestProperty("Cache-Control",CacheControl);
			connection.addRequestProperty("Connection",Connection);
			connection.addRequestProperty("Cookie",Cookie);
			connection.addRequestProperty("Host",Host);
			connection.addRequestProperty("User-Agent",UserAgent);
			
			//.openConnection();
			// �������ӣ�����ʵ����get requestҪ����һ���connection.getInputStream()�����вŻ���������
			// ������
			connection.connect();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = reader2.readLine()) != null){
				firstPage += (line+"\n");
			}		
			// �Ͽ�����
			connection.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return firstPage.split(split)[1].split("<script>")[0].split("target=\"_blank\"");
	}		
	
	
	private static String visit(String url){
		String firstPage = "";
		HttpURLConnection connection;
		// ����ƴ�յ�URL�������ӣ�URL.openConnection���������URL�����ͣ�
		try {
			URL getUrl = new URL(url);
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setReadTimeout(10000);
			
			connection.addRequestProperty("Accept",Accept);
			connection.addRequestProperty("Accept-Encoding",AcceptEncoding);
			connection.addRequestProperty("Accept-Language",AcceptLanguage);
			connection.addRequestProperty("Cache-Control",CacheControl);
			connection.addRequestProperty("Connection",Connection);
			connection.addRequestProperty("Cookie",Cookie);
			connection.addRequestProperty("Host",Host);
			connection.addRequestProperty("User-Agent",UserAgent);
			
			//.openConnection();
			// �������ӣ�����ʵ����get requestҪ����һ���connection.getInputStream()�����вŻ���������
			// ������
			connection.connect();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = reader2.readLine()) != null){
				firstPage += (line+"\n");
			}		
			// �Ͽ�����
			connection.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return firstPage;
	}
	
	private static void sendMessage(String title, String link,  String content, String time){
		SendMail sm=new SendMail("smtp.163.com");
		sm.setNamePass("jsxjgood123@163.com","921004");
		sm.setSubject(title);
		sm.setFrom("jsxjgood123@163.com");
		sm.setTo("752377627@qq.com");
		content = "content: "+ content + "\n" 
		+ "============================" + "\n"
		+	"time:" + time
		+ "============================" + "\n" 
		+ "link:" + link;
		sm.setBody(content);
		sm.setNeedAuth(true);
		boolean b=sm.setOut();
		if(!b){
			logger.error("�ʼ�����ʧ��!!!!");
		}
	}
	
	public static void looper() {
		String url = getBitUnionAdress();
		String likes = getLikes();
		String attention[] = likes.split(";");
		
		String [] goods = getFirstPage(url);
		for(int i = 1; i < goods.length; i++){
			for(int j = 0; j < attention.length; j++){
				if(goods[i].contains(attention[j])){            //����еĻ����鿴�Ƿ������µ���Ϣ
					String link = StringUtils.getSubString(goods[i], "a", "thread","html");
					link = "http://www.bitunion.org/thread" + link + "html";
					String detail = visit(link);
					String title = StringUtils.getSubString(detail, "����:", "����:","</div>");
					
					String content = StringUtils.getSubString(detail, "pm_", ">","</div>");
					content = content.replaceAll("&nbsp;", "").replace("<br />", "");
					
					
					String temp = StringUtils.getSubString(detail, "��̳����", "</a>","<a").replace("\n", "");
					
					String time = temp.replace(" PM", "").replace(" AM", "");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					try {
						Date sendTime = sdf.parse(time);
						if(temp.contains("PM")){
							int hours = sendTime.getHours() + 12;
							sendTime.setHours(hours);
						}
						Date now = new Date();
						long diff = now.getTime() - sendTime.getTime();
					    long minutes = diff / (1000 * 60 );
					    if(minutes > 5){
					    	logger.error("����̫��");
					    	return;
					    }
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					sendMessage(title, link, content, time);
				}
			}
		}
	}
	
	public static void main(final String[] args) throws InterruptedException {
		while (true) {
			looper();
			Thread.sleep(60000 * 1); 
		}
	}
}
