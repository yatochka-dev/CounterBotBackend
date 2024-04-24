package mainbot.events;

import net.dv8tion.jda.api.entities.Guild;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildReadyEvent extends ListenerAdapter {
    @Override
    public void onGuildReady(net.dv8tion.jda.api.events.guild.GuildReadyEvent event) {

        Guild g = event.getGuild();
        System.out.println("Guild ready: " + g.getName());


        g.upsertCommand("profile", "Get your profile link")
                .queue();


    }
}
