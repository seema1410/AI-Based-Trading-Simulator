/*package com.app.trading.Service;

import org.springframework.stereotype.Service;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class GeminiAIServiceImpl implements GeminiAIService {

    private final String API_KEY = "AIzaSyAZTBNQtsDckAzNbYbhGDB5U9rWmrMGsq0"; 

    @Override
    public String getAIAdvice(String prompt) throws Exception {
        URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String jsonInputString = """
        {
          "contents": [
            {
              "parts": [
                {"text": "%s"}
              ]
            }
          ]
        }
        """.formatted(prompt);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        // 🌟 Log Raw Response
        System.out.println("🔵 Gemini Raw Response:");
        System.out.println(response.toString());

        // 🌟 Extract Gemini's Advice Text from JSON
        JSONObject json = new JSONObject(response.toString());
        JSONArray candidates = json.getJSONArray("candidates");
        JSONObject firstCandidate = candidates.getJSONObject(0);
        JSONObject content = firstCandidate.getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");
        String aiAdvice = parts.getJSONObject(0).getString("text");

        // 🌟 Log Clean Advice
        System.out.println("✅ Gemini Advice Text:");
        System.out.println(aiAdvice);

        return aiAdvice;
    }
}
*/

package com.app.trading.Service;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.http.*;
import java.net.URI;
import java.time.Duration;

@Service
public class GeminiAIServiceImpl implements GeminiAIService {

	@Value("${gemini.api.key}")
	private String apiKey;

	@Value("${gemini.api.url}")
	private String apiUrl;

	@Override
	public String getAIAdvice(String prompt) throws Exception {
		HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

		JSONObject requestBody = new JSONObject();
		JSONArray contents = new JSONArray();
		JSONObject content = new JSONObject();
		JSONArray parts = new JSONArray();
		parts.put(new JSONObject().put("text", prompt));
		content.put("parts", parts);
		contents.put(content);
		requestBody.put("contents", contents);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl + "?key=" + apiKey))
				.timeout(Duration.ofMinutes(1)).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new Exception("Gemini API error: " + response.body());
		}

		JSONObject jsonResponse = new JSONObject(response.body());
		return jsonResponse.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts")
				.getJSONObject(0).getString("text");
	}
}
