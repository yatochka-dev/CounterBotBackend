package mainbot;

import mainbot.db.Bot;
import mainbot.db.DataBase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import mainbot.commands.BotCommands;
import mainbot.events.GuildReadyEvent;
import mainbot.events.MessageReceiveListener;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class Main {


    ;
    private static final String BOT_TOKEN = "MTAwNzU4MjA2MTY3MjAxNzk1MQ.Gi3eNu._BPWq15l406sAqUsqv3cWGTmG8jfBpBFTEDhis";
    private static final DataBase db = DataBase.getInstance();

    @RequestMapping("/")
    String home() {
        String query = "MATCH (a) RETURN a";
        return db.executableQuery(query).execute().toString();


    }

    public static void main(String... args) throws InterruptedException {

        var bot = Bot.getInstance();


        // Run spring boot on 8000 port
        SpringApplication.run(Main.class);


    }
}
//import org.springframework.boot.*;
//import org.springframework.boot.autoconfigure.*;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@SpringBootApplication
//public class Main {
//
//    @RequestMapping("/")
//    String home() {
//        return "Hello World!";
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
//    }
//
//}