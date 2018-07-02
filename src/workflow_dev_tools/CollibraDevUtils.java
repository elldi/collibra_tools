package workflow_dev_tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

//import com.google.gson.Gson;

//import Results;



public class CollibraDevUtils {
	
	private String baseUrl;
	private String username;
	private String password;
	private String baseAuth; 
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36";
	
	public CollibraDevUtils(){
		this("http://localhost:4400/rest/2.0/", "Admin", "admin");
	}
	
	public CollibraDevUtils(String username, String password){
		this("http://localhost:4400/rest/2.0/", username, password);
	}
	
	public CollibraDevUtils(String baseUrl, String username, String password){
		
		this.baseUrl = baseUrl;
		this.username = username;
		this.password = password; 
		try {
			baseAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes("UTF-8"));
		} catch (Exception e1){
			System.out.println("Encoding failed. ");
		}
	}
	
	public boolean getData(String uri) {
		
		String url = baseUrl + uri;
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("Authorization", baseAuth);
		request.addHeader("Accept", "application/json");
		
		try{
			HttpResponse response = client.execute(request);
			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			//Gson gson = new Gson();
			//Results r1  = gson.fromJson(json, Results.class);
			
			//System.out.println(r1.total);
			
		} catch (IOException e1){
			
			return false; 
		}
		
		return true; 
	}
	
	public boolean postFile(String uri, String filePath){
		String url = baseUrl + uri;
		
		System.out.println(url);
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		
		// add request header
		//post.addHeader("User-Agent", USER_AGENT);
		post.addHeader("Authorization", baseAuth);
		post.addHeader("Accept", "application/json");
		//post.addHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryZ8EBt5GkFIIXl0XE");
		//post.addHeader("Content-Disposition", "form-data; name=\"file\"; filename=\"ExtractComments.bpmn\"");
		
		File bpmnFile = new File(filePath);
		
		FileBody fileBody = new FileBody(bpmnFile, ContentType.DEFAULT_BINARY);
		StringBody fileName = new StringBody(bpmnFile.getName(), ContentType.MULTIPART_FORM_DATA);
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("fileName", fileName);
		builder.addPart("file", fileBody);
		
		
		
		HttpEntity entity = builder.build();
		
		post.setEntity(entity);
		
		try{
			HttpResponse response = client.execute(post);
			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (IOException e1){
			e1.printStackTrace();
			return false; 
		}
				
		return true;
	}
	
	public boolean postData(String uri){
		
		String url = baseUrl + uri;
		String JSON_STRING = "{\"userName\": \"test\",\"firstName\": \"test\",\"lastName\": \"ets\",\"emailAddress\": \"elliot.dines@db.com\",\"gender\": \"MALE\"}";
		
		StringEntity requestEntity = new StringEntity(JSON_STRING,ContentType.APPLICATION_JSON);
		
		HttpPost post = new HttpPost(url);
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		post.addHeader("Authorization", baseAuth);
		post.addHeader("Content-Type", "application/json");
		
		post.setEntity(requestEntity);
		
		try{
			HttpResponse response = client.execute(post);
			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (IOException e1){
			e1.printStackTrace();
			return false; 
		}
		
		
		
		return true;
	}
	
	public TestResults runTests(){
		System.out.println("Starting Testing");
		
		TestResults testing = new TestResults();
		
		int pass = 0;
		
		pass = (new CollibraDevUtils().getData("assets")) ? testing.another().passed() : testing.another().failed();
		
		pass = (new CollibraDevUtils().getData("application/info")) ? testing.another().passed() :  testing.another().failed();
		pass = (new CollibraDevUtils().getData("workflowDefinitions")) ? testing.another().passed() :  testing.another().failed();
		
		pass = (new CollibraDevUtils().postFile("workflowDefinitions", "C:\\Users\\Public\\Documents\\eclipse neon\\workspace\\wd-tool\\workflows\\ExtractComments.bpmn")) ? testing.another().passed() :  testing.another().failed();
		pass = (new CollibraDevUtils().postData("users")) ? testing.another().passed() :  testing.another().failed();
	
		return testing;
	}
	
	public static void main(String args[]){
		
		System.out.println(new CollibraDevUtils().runTests());
		
		
	}


}
