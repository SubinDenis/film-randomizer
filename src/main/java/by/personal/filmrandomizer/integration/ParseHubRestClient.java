package by.personal.filmrandomizer.integration;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

@Component
public class ParseHubRestClient {

    private static String runProjectUrl = "https://www.parsehub.com/api/v2/projects/{projectToken}/run";
    private static String runInfoUrl = "https://www.parsehub.com/api/v2/runs/{runToken}";
    private static String runDataUrl = "https://www.parsehub.com/api/v2/runs/{runToken}/data";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GsonJsonParser gsonJsonParser;

    @Autowired
    private OkHttp3ClientHttpRequestFactory requestFactory;

    @Value("${parsehub.auth-token}")
    private String authToken;

    @PostConstruct
    public void init() {
        runProjectUrl = runProjectUrl + "?api_key=" + authToken;
        runInfoUrl = runInfoUrl + "?api_key=" + authToken;
        runDataUrl = runDataUrl + "?api_key=" + authToken;
    }

    public Map<String, String> runProject(String projectToken) {
        return restTemplate.postForObject(runProjectUrl, null, Map.class, Collections.singletonMap("projectToken", projectToken));
    }

    public Map<String, String> getRunInfo(String runToken) {
        return restTemplate.getForObject(runInfoUrl, Map.class, Collections.singletonMap("runToken", runToken));
    }

    public Map<String, Object> getRunData(String runToken) throws IOException, URISyntaxException {
        URI uri = new URI(runDataUrl.replace("{runToken}", runToken));
        ClientHttpRequest request = requestFactory.createRequest(uri, HttpMethod.GET);
        ClientHttpResponse response = request.execute();
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(response.getBody());
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        return gsonJsonParser.parseMap(sb.toString());
    }
}
