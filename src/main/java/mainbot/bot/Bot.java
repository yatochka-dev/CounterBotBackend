package mainbot.bot;

import mainbot.commands.BotCommands;
import mainbot.events.GuildReadyEvent;
import mainbot.events.MessageReceiveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
    private static Bot instance = null;
    @SuppressWarnings("SpellCheckingInspection")
    private static final String BOT_TOKEN = "MTAwNzU4MjA2MTY3MjAxNzk1MQ.Gi3eNu._BPWq15l406sAqUsqv3cWGTmG8jfBpBFTEDhis";

    public JDA bot = null;

    private Bot() {
        // Constructor
        connect();
    }

    public static Bot getInstance() {
        if (instance == null) {
            instance = new Bot();
        }
        return instance;
    }

    public void connect() {
        bot = JDABuilder.createDefault(BOT_TOKEN)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(
                        Activity.competing("stealing your data!")
                )
                .addEventListeners(
                        new MessageReceiveListener(),
                        new BotCommands(),
                        new GuildReadyEvent()
                )
                .build();
    }


}