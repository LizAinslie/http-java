package tk.racket.utils.http.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpClient {
    private final String userAgent;

    public HttpClient(String userAgent) {
        this.userAgent = userAgent;
    }

    public HttpClient() {
        this("Mozilla/5.0");
    }

    public String get(URL url, Map<String, String> headers) {
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", this.userAgent);

			if (headers != null) headers.forEach((name, value) -> {
				con.setRequestProperty(name, value);
			});

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) response.append(inputLine);
			in.close();
			
			return response.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public String get(String urlString, Map<String, String> headers) throws MalformedURLException {
		return this.get(new URL(urlString), headers);
    }
    
    public String post(URL url, String body, Map<String, String> headers) {
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", this.userAgent);

			if (headers != null) headers.forEach((name, value) -> {
				con.setRequestProperty(name, value);
			});
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) response.append(inputLine);
			in.close();
			
			return response.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public String post(String urlString, String body, Map<String, String> headers) throws Exception {
		return post(new URL(urlString), body, headers);
	}
}