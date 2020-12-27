package by.personal.filmrandomizer.integration;

import by.personal.filmrandomizer.entity.Film;
import by.personal.filmrandomizer.entity.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        List<Map<String, Object>> dataForAll = getDataForAll(runTokens);
        List<Film> films = extractFilmData(dataForAll);
        logger.info("Integration ended!");
    }

    public List<Film> extractFilmData(List<Map<String, Object>> dataForAll) {
        List<Film> filmList = new ArrayList<>();
        for (Map<String, Object> filmData : dataForAll){

            List rawFilms = (ArrayList) filmData.get("film");
            for (int a = 0; a < rawFilms.size(); a++) {
                Map film = (Map) rawFilms.get(a);
                Film f = new Film();
                f.setName((String) film.get("name"));
                f.setSourceLink((String) film.get("url"));
                List<Tag> tags = new ArrayList<>();
                f.setTags(tags);
                List t = (ArrayList) film.get("tag");
                for (int i = 0; i < t.size(); i++) {

                    Map map = (Map) t.get(i);
                    Tag tag = new Tag();
                    tag.setName((String) map.get("name"));
                    tags.add(tag);

                }
                filmList.add(f);
            }
        }
        return filmList;
    }

    public void mergeData(List<Film> films) {



    }

    public List<Map<String, Object>> getDataForAll(List<String> runTokens) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (String runToken : runTokens) {
            try {
                Map<String, Object> runData = parseHubRestClient.getRunData(runToken);
                result.add(runData);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private void waitAll(List<String> runTokens) throws InterruptedException {
        List<String> statuses = new ArrayList<>();
        boolean wait = true;
        while (wait) {
            for (String runToken : runTokens) {
                Map<String, String> info = parseHubRestClient.getRunInfo(runToken);
                statuses.add(info.get("status"));
            }
            wait = statuses.stream().anyMatch(status -> !status.equals("complete"));
            if (wait) {
                statuses.clear();
                Thread.sleep(15 * 60 * 1000);
            }
        }
    }
}
