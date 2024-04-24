package mainbot.models;

import java.util.Map;

public record ProfileStats(
        Boolean exists,
        Long totalWordsUsed,
        Long totalWordsSaid,
        Map<String, Integer>[] mostUsedWords,
        Map<String, Integer>[] leastUsedWords
) {

}