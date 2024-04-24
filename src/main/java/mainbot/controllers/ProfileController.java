package mainbot.controllers;

import mainbot.db.DataBase;
import mainbot.models.ProfileStats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
public class ProfileController {

    private final DataBase db = DataBase.getInstance();
//    private final Bot bot = Bot.getInstance();

    @GetMapping("/profile")
    public ProfileStats profile(@RequestParam(name = "id") String id) {

        var userExists = checkUserExists(id);
        if (userExists) {
            return new ProfileStats(
                    false,
                    null,
                    null,
                    null,
                    null
            );
        }

        var totalWordsSaid = getTotalWordsSaid(id);
        var totalWordsUsed = getTotalWordsUsed(id);
        var mostUsedWords = getMostUsedWords(id);
        var leastUsedWords = getLeastUsedWords(id);

        System.out.println("mostUsedWords: " + Arrays.toString(mostUsedWords));
        System.out.println("leastUsedWords: " + Arrays.toString(leastUsedWords));

        //noinspection unchecked
        return new ProfileStats(
                true,
                totalWordsUsed,
                totalWordsSaid,
                mostUsedWords,
                leastUsedWords
        );

    }

    public Boolean checkUserExists(String id) {
        var executed = db.executableQuery("MATCH (u:User {id: $id}) RETURN u")
                .withParameters(Map.of("id", id))
                .execute();

        return executed.records().isEmpty();
    }

    public Long getTotalWordsSaid(String id) {
        return db.executableQuery("MATCH (:User {id: $id})-[s:Said]->(:Word)\n" +
                        "RETURN count(s) as totalWordsSaid")
                .withParameters(Map.of("id", id))
                .execute()
                .records()
                .getFirst()
                .get("totalWordsSaid")
                .asLong();
    }

    public Long getTotalWordsUsed(String id) {
        return db.executableQuery("""
                        MATCH (:User {id: $id})-[:Said]->(w:Word)
                        WITH DISTINCT w as words
                        RETURN count(words) as totalWordsUsed
                        """)
                .withParameters(
                        Map.of("id", id)
                )
                .execute()
                .records()
                .getFirst()
                .get("totalWordsUsed")
                .asLong();
    }

    public Map[] getMostUsedWords(String id) {
        var mostUsedWordsRecords = db.executableQuery("""
                        MATCH (:User {id: $id})-[:Said]->(w:Word)
                               RETURN w, COUNT(*) as saidCount
                               ORDER BY saidCount DESC
                               LIMIT 10
                        """)
                .withParameters(
                        Map.of("id", id)
                )
                .execute()
                .records();

        var most = mostUsedWordsRecords.stream()
                .map(record -> Map.of(
                        record.get("w").get("name").asString(),
                        record.get("saidCount").asInt()
                ));

        return most.toArray(Map[]::new);
    }

    public Map[] getLeastUsedWords(String id) {
        var leastUsedWordsRecords = db.executableQuery("""
                        MATCH (:User {id: $id})-[:Said]->(w:Word)
                               RETURN w, COUNT(*) as saidCount
                               ORDER BY saidCount ASC
                               LIMIT 10
                        """)
                .withParameters(
                        Map.of("id", id)
                )
                .execute()
                .records();

        var least = leastUsedWordsRecords.stream()
                .map(record -> Map.of(
                        record.get("w").get("name").asString(),
                        record.get("saidCount").asInt()
                ));
        return least.toArray(Map[]::new);
    }
}