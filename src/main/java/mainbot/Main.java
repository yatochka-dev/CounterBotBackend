package mainbot;

import mainbot.bot.Bot;
import mainbot.db.DataBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Main {

    private static final DataBase db = DataBase.getInstance();
    private static final Bot bot = Bot.getInstance();

    public static void main(String... args) {


        // Run spring boot on 8000 port
        SpringApplication.run(Main.class);


    }
}