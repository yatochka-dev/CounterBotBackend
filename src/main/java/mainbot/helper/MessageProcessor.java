package mainbot.helper;


import net.dv8tion.jda.api.entities.Message;

public class MessageProcessor {


    // This method checks if the message has content+not empty and if the author is not a bot
    public Boolean isEligible(Message message) {
        return !message.getContentDisplay().isEmpty() && !message.getAuthor().isBot();
    }

    /**
     * This method splits the message into words, converts the words to lowercase,
     * and removes any special characters.
     *
     * @param message The message to split.
     * @return An array of words.
     */
    public String[] splitMessage(Message message) {
        String content = message.getContentDisplay();
        String[] result = content.toLowerCase().replaceAll("[^a-z ]", "").split(" ");

        for (String w : result) {
            if (w.isEmpty()) {
                result = removeElement(result, w);
            }
        }

        return result;
    }

    /**
     * This method removes an element from an array.
     *
     * @param array The array to remove the element from.
     * @param el    The element to remove.
     * @return The array without the element.
     */
    private String[] removeElement(String[] array, String el) {
        String[] newResult = new String[array.length - 1];
        int index = 0;
        for (String word : array) {
            if (word.equals(el)) {
                continue;
            }
            newResult[index] = word;
            index++;
        }
        return newResult;
    }

    /**
     * This method builds a query to add the words to the database.
     * It creates Word nodes for each word in the message and connects them to the User node with a Said relationship.
     *
     * @param words The words to add to the database.
     * @param id    The ID of the user who said the words.
     * @return The query to execute.
     */
    public String buildQuery(Message message, String id) {
        String[] words = splitMessage(message);

        if (words.length == 0) {
            return "";
        }

        StringBuilder query = new StringBuilder();
        int index = 1;
        for (String word : words) {
            // Check if the word is empty
            if (word.isEmpty()) {
                continue;
            }

            query.append(String.format("MERGE (w%d:Word {name: \"%s\"})\n", index, word));
            index++;
        }

        // Add WITH w1, w2, w3, w4, w5
        query.append("WITH ");
        for (int i = 1; i < index; i++) {
            query.append(String.format("w%d, ", i));
        }

        // Remove the last comma and space
        query.delete(query.length() - 2, query.length());

        query.append(String.format("\nMATCH (u:User {id: \"%s\"})\n", id));
        index = 1;
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            query.append(String.format("""
                    CREATE (u)-[:Said {guild: $gID, channel: $cID, at: timestamp()}]->(w%d)
                    """, index));
            index++;
        }

        return query.toString();
    }
}
