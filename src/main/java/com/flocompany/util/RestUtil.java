package com.flocompany.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RestUtil {
	
	
	public final static String URL="http://localhost/rest/";
	
	public final static String PSEUDO = "pseudo";
	public final static String MAIL = "mail";
	public final static String PWD = "pwd";
	
	
	
	public static String callRestService(String path, String method, String type, List<String> params){
		String result = "";
	try {
		  
		  String output;
		  String url = RestUtil.URL + path;
		  URL restServiceURL = new URL(url);
			  
		  HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
		  httpConnection.setRequestProperty("Accept", type);
		  
		  
		  httpConnection.setDoOutput(true); // Triggers POST.
		  if(method.equals("POST")){
			  httpConnection.setRequestProperty("Accept-Charset", "utf8");
			  httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + "utf8");
			  OutputStreamWriter writer = new OutputStreamWriter(httpConnection.getOutputStream());
			  String parametres="";
			  int i=0;
			  for(String param : params){
				  if(i==0){
					  parametres=param;
				  }else{
					  parametres+="&"+param;
				  }
				  i++;
			  }
			  System.out.println(parametres);
//			  writer.write("firstName=salut&lastName=cava");
			  writer.write(parametres);
			  writer.flush();
		  }else{
			  httpConnection.setRequestMethod(method);
		  }
		  
		  
		  
		  if (httpConnection.getResponseCode() != 200) {
			    output = "HTTP GET Request Failed with Error code : "
			                + httpConnection.getResponseCode();
		  }
	      BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
	 
		  
		  System.out.println("Output from Server:  \n");
		 
		  while ((output = responseBuffer.readLine()) != null) {
			  result=output;
		  }
	      httpConnection.disconnect();
	      return result;
	      } catch (MalformedURLException e) {
	            e.printStackTrace();
	      } catch (IOException e) {
	            e.printStackTrace();
		  }
		  return result;
	
	}
}
