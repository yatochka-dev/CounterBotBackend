package mainbot;

import mainbot.bot.Bot;
import mainbot.db.DataBase;
import mainbot.dotenv.DotEnv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@SpringBootApplication
public class Main {

    private static final DataBase db = DataBase.getInstance();
    private static final Bot bot = Bot.getInstance();

    private static final DotEnv dotenv = DotEnv.getInstance();

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(Main.class);

        // Run spring boot on 8000 port
        System.out.println(dotenv.get("NEO4J_URI"));
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));

        app.run(args);


    }
}