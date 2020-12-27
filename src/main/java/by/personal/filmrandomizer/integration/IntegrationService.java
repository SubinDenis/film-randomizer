package by.personal.filmrandomizer.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class IntegrationService {

    private Logger logger = LoggerFactory.getLogger(IntegrationService.class);

    @Value("#{'${parsehub.poject-tokens}'.split(',')}")
    private List<String> projectTokens;

    @Autowired
    private ParseHubRestClient parseHubRestClient;

//    @Scheduled(cron = "0 2 * * *")
    public void runIntegration() {
        logger.info("Integration started!");
        List<String> runTokens = new ArrayList<>();
        for (String projectToken : projectTokens) {
            String runToken = parseHubRestClient.runProject(projectToken).get("run_token");
            runTokens.add(runToken);
        }
        try {
            waitAll(runTokens);
        } catch (InterruptedException e) {
            logger.error("waitAll(): InterruptedException: ", e);
        }
        List<Map<String, String>> data = getDataForAll(runTokens);
        mergeData(data);
        logger.info("Integration ended!");
    }

    private void mergeData(List<Map<String, String>> data) {

    }

    private List<Map<String, String>> getDataForAll(List<String> runTokens) {
        List<Map<String, String>> result = new ArrayList<>();
        for (String runToken : runTokens) {
//            parseHubRestClient.getRunData(runToken);
//            result.add();
        }
        return result;
    }

    private void waitAll(List<String> runTokens) throws InterruptedException {
        List<String> statuses = new ArrayList<>();
        boolean wait = true;
        while(wait) {
            for (String runToken : runTokens) {
                Map<String, String> info = parseHubRestClient.getRunInfo(runToken);
                statuses.add(info.get("status"));
            }
            wait = statuses.stream().anyMatch(status -> !status.equals("completed"));
            if (wait) {
                statuses.clear();
                Thread.sleep(5 * 60 * 1000);
            }
        }
    }
}
