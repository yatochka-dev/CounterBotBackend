package mainbot.commands;

import mainbot.helper.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import mainbot.helper.SlashCommandHelper;
import org.jetbrains.annotations.NotNull;

public class BotCommands extends ListenerAdapter {

    private final SlashCommandHelper slashCommandHelper = new SlashCommandHelper();
    public final Config config = Config.getInstance();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (slashCommandHelper.is(event, "profile")) {
            try {
                profileCommand(event);
            } catch (Exception e) {
                event.reply("An error occurred while processing the command :cry:").queue();
            }
            return;
        }

        event.reply("There's no such command :cry:").queue();
    }


    public void profileCommand(@NotNull SlashCommandInteractionEvent event) {

        EmbedBuilder embed = new EmbedBuilder();
        // Build the profile URL
        // The URL is the website URL + /profile/ + the user's ID
        String url = config.getWebsiteURL() +
                "/profile/" +
                event.getUser().getId();



        embed.setTitle("Profile");
        embed.setDescription(String.format("Here is your profile: [%s](%s)", event.getUser().getName(), url));
        embed.setColor(this.config.getEmbedColor());

        event.replyEmbeds(embed.build()).queue();
    }

}
