package mainbot.helper;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class SlashCommandHelper {
    public SlashCommandHelper() {
    }

    public Boolean is(@NotNull SlashCommandInteractionEvent event, String expect) {
        return event.getName().equals(expect);
    }
}