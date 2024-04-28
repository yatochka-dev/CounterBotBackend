package mainbot;

import mainbot.bot.Bot;
import mainbot.db.DataBase;
import mainbot.dotenv.DotEnv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Main {

    private static final DataBase db = DataBase.getInstance();
    private static final Bot bot = Bot.getInstance();

    private static final DotEnv dotenv = DotEnv.getInstance();

    public static void main(String... args) {


        // Run spring boot on 8000 port
        System.out.println(dotenv.get("NEO4J_URI"));
        SpringApplication.run(Main.class);


    }
}