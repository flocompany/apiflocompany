package com.flocompany.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.flocompany.dao.impl.ParameterImpl;

public class RestUtil {
	
	
	// PATH of the different rest service
	public final static String PATH_SIGNUP = "person/signup";
	public final static String PATH_LOGIN = "person/login";
	public final static String PATH_ADD_FRIEND = "friend/add";
	public final static String PATH_SEARCH_PERSON = "person/search";
	public final static String PATH_LIST_FRIEND = "friend/mylist";
	
	
	//Resource parameter of Person
	public final static String ID = "id";
	public final static String PSEUDO = "pseudo";
	public final static String MAIL = "mail";
	public final static String PWD = "pwd";
	
	//Resource parameter of Friend
	public final static String ID_FRIEND = "id_friend";
	public final static String ID_APPLICANT = "id_applicant";
	public final static String ID_PERSON = "id_person";
	
	//Resource parameter of Parametre
	public final static String NAME = "name";
	public final static String VALUE = "value";
	public final static String URL_WEB_SERVICE = "url_web_service";
	public final static String MAIL_ADMIN = "mail_admin";
	
	//Friend status
	public final static String ACCEPTED = "accepted";
	public final static String REFUSED = "refused";
	public final static String BLOCKED = "blocked";
	
	
	
	public static String callRestService(String path, String method,
		String type, List<String> params) {
		String result = "";
		try {

			String parametres = "";
			String output;
			String url = ParameterImpl.getInstance().getValueByName(
					URL_WEB_SERVICE)
					+ path;
			int i = 0;
			if (params != null) {
				for (String param : params) {
					if (i == 0) {
						parametres = param;
					} else {
						parametres += "&" + param;
					}
					i++;
				}
			}
			if (method.equals("GET")) {
				url += "?" + parametres;
			}
			URL restServiceURL = new URL(url);
			System.out.println(url);

			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL
					.openConnection();
			httpConnection.setRequestProperty("Accept", type);
			httpConnection.setInstanceFollowRedirects(false);
			httpConnection.setRequestProperty("Accept-Charset", "utf8");

			httpConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=" + "utf8");
			// httpConnection.setConnectTimeout(60000); //60 Seconds
			// httpConnection.setReadTimeout(60000); //60 Seconds

			httpConnection.setDoOutput(true); // Triggers POST.

			if (method.equals("GET")) {
				httpConnection.setRequestMethod(method);
			}
			if (method.equals("POST")) {
				OutputStreamWriter writer = new OutputStreamWriter(
						httpConnection.getOutputStream(), "UTF-8");
				System.out.println(parametres);
				writer.write(parametres);
				writer.flush();
			}

			if (httpConnection.getResponseCode() != 200) {
				output = "HTTP GET Request Failed with Error code : "
						+ httpConnection.getResponseCode();
			}

			InputStreamReader ipsr = new InputStreamReader(
					httpConnection.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				System.out.println(ligne);
				result += ligne;
			}
			br.close();

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
