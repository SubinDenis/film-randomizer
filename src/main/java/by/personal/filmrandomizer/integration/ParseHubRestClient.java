package by.personal.filmrandomizer.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

@Component
public class ParseHubRestClient {

    private static String runProjectUrl = "https://www.parsehub.com/api/v2/projects/{projectToken}/run";
    private static String runInfoUrl = "https://www.parsehub.com/api/v2/runs/{runToken}";
    private static String runDataUrl = "https://www.parsehub.com/api/v2/runs/{runToken}/data";

    @Autowired
    private RestTemplate restTemplate;

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

    public Map<String, String> getRunData(String runToken) {
        return restTemplate.getForObject(runDataUrl, Map.class, Collections.singletonMap("runToken", runToken));
    }
}
