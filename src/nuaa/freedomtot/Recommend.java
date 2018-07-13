package nuaa.freedomtot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class Recommend {

	
	private enum Type{
		ZHU(1),YONG(2),XIN(3),JIN(4),XIAO(5);
		int index;
		Type(int i){
			index=i;
		}
	}
	
	
	private final static String token="5020a62d91d3bd7fb321d7a3c9e58671";
	private final static String DEFAULT_URL = "http://haoren.dc.10086.cn/hrhs/recommend_goodpeople.do?srcMobile=15905136766&srcName=%E6%AD%A6%E5%AE%9C%E6%B6%9B&srcProvince=320000&srcCity=320700&recProvince=320000&recCity=100079";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Recommend r=new Recommend();
		
		
		
		//r.recommendNewOne(r.getNewUrl());
	//	System.out.println(r.getNewUrl());

	}
	
	private String getNewUrl() throws UnsupportedEncodingException {
		StringBuffer strbuf = new StringBuffer(DEFAULT_URL);
		String name=java.net.URLEncoder.encode("冯思喆", "UTF-8");
		int type=1;
		String content=java.net.URLEncoder.encode("冯思喆，江苏连云港海州区龙河小区人，助。在社区看见有不良现象总会主动上前纠正，协调社区工作，主动关爱老人，献爱心、做力所能及的事。", "UTF-8");
		Date now = new Date();
		strbuf.append("&categoryType="+type);
		strbuf.append("&recName="+name);
		strbuf.append("&content="+content);
		strbuf.append("&timestamp="+now.getTime());
		return strbuf.toString();
	}

	private int getUserMobile() throws IOException {
		Date now = new Date();
		String url="http://haoren.dc.10086.cn/hrhs/getUserMobileInfo.do?srcMobile=15905136766&channelId=3&timestamp="+now.getTime();
		Connection con3 = Jsoup.connect(url);// 获取连接
		con3.header("User-Agent",
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Mobile Safari/537.36");
		con3.header("Referer", "http://haoren.dc.10086.cn/");
		con3.header("Connection", "keep-alive");
		con3.header("Accept", "application/json, text/plain, */*");
		con3.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		con3.header("Accept-Encoding", "gzip, deflate");
		con3.header("Host", "haoren.dc.10086.cn");
		con3.header("Origin", "http://haoren.dc.10086.cn");
		con3.header("Cookie", "haoren_mobile=15905136766; fangshuaiSheji="+token);
		Response response = con3.ignoreContentType(true).method(Method.GET).execute();
		return response.statusCode();
		
	}
	private int tokenVerify() throws IOException {
		Date date=new Date();
		String url="http://dc.10086.cn/SSOServer/user/tokenVerify.do?token="+token+"&timestamp="+date.getTime();
	
		Connection con3 = Jsoup.connect(url);// 获取连接
		con3.header("User-Agent",
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Mobile Safari/537.36");
		con3.header("Referer", "http://haoren.dc.10086.cn/");
		con3.header("Connection", "keep-alive");
		con3.header("content-type", "application/x-www-form-urlencoded");
		con3.header("Accept", "application/json, text/plain, */*");
		con3.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		con3.header("Accept-Encoding", "gzip, deflate");
		con3.header("Host", "dc.10086.cn");
		con3.header("Origin", "http://haoren.dc.10086.cn");
		con3.header("X-Requested-With", "XMLHttpRequest");
		Response response = con3.ignoreContentType(true).method(Method.GET).execute();
		return response.statusCode();
		
	}
	private void recommendNewOne(String url) throws IOException {
		System.out.println("get mobileL "+getUserMobile());
		System.out.println("token verify: "+tokenVerify());
		System.out.println();
		Map<String, String> cookiesMap = parse(
				"haoren_mobile=15905136766; fangshuaiSheji=5020a62d91d3bd7fb321d7a3c9e58671");
		Connection con3 = Jsoup.connect(url);// 获取连接
		con3.header("User-Agent",
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Mobile Safari/537.36");
		con3.header("Referer", "http://haoren.dc.10086.cn/");
		con3.header("Connection", "keep-alive");
		con3.header("content-type", "application/x-www-form-urlencoded");
		con3.header("Accept", "application/json, text/plain, */*");
		con3.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		con3.header("Accept-Encoding", "gzip, deflate");
		con3.cookies(cookiesMap);
		Response response = con3.ignoreContentType(true).method(Method.GET).execute();
		System.out.println("response: "+response.statusCode());
	}

	private static void showBody(Response res) {
		System.out.println("BODY:");
		System.out.println(res.body());
		for (String key : res.headers().keySet()) {
			System.out.println(key + " :　" + res.headers().get(key));
		}
	}

	private static void showCookies(Response res) {
		System.out.println("COOKIES:");
		for (String key : res.cookies().keySet()) {
			System.out.println(key + " :　" + res.headers().get(key));
		}
	}

	private static Map<String, String> parse(String str) {
		Map<String, String> map = new TreeMap<String, String>();
		String[] strs = str.split(";");
		for (String string : strs) {
			String key = string.split("=")[0].replaceAll(" ", "");
			String value = string.substring(key.length() + 1).replaceAll(" ", "");
			map.put(key, value);
		}
		return map;
	}


}
