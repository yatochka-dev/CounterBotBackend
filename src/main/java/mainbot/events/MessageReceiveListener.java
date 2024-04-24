package mainbot.events;

import mainbot.db.DataBase;
import mainbot.helper.MessageProcessor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * This class listens for messages received in a Discord server and processes them.
 * It extends the ListenerAdapter class from the JDA library.
 */
public class MessageReceiveListener extends ListenerAdapter {


    public static DataBase database = DataBase.getInstance();
    public static MessageProcessor messageProcessor = new MessageProcessor();

    /**
     * This method is called whenever a message is received in the server.
     * It logs the message details, adds the user to the database, splits the message into words,
     * and stores the words in the database, connecting them to the user who said them.
     *
     * @param event The event triggered when a message is received.
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // Log the message details
        System.out.printf("[%s] %#s: %s\n",
                event.getChannel(),
                event.getAuthor(),
                event.getMessage().getContentDisplay());

        // Get the author's ID
        String id = event.getAuthor().getId();


        // Add the user to the database
        database.executableQuery("""
                        MERGE (u:User {id: $id})\s
                        RETURN u""")
                .withParameters(Map.of("id", id))
                .execute();


        var eligible = messageProcessor.isEligible(event.getMessage());

        if (!eligible) {
            System.out.println("The message doesn't contain any words.");
            return;
        }

        // Build a query to add the words to the database
        var query = messageProcessor.buildQuery(event.getMessage(), id);

        // Execute the query
        database.executableQuery(query)
                .withParameters(Map.of("gID", event.getGuild().getId(), "cID", event.getChannel().getId()))
                .execute();


        System.out.println("Added words to the database");
        System.out.println(query);
    }
}