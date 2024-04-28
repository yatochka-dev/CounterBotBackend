package mainbot.dotenv;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnv {

    Dotenv dotenv = null;


    private static DotEnv instance = null;

    private DotEnv() {
        // Constructor
        dotenv =
                Dotenv.configure()
                        .directory("src/main/resources")
                        .filename(".env")
                        .ignoreIfMissing()
                        .load();
    }

    public static DotEnv getInstance() {
        if (instance == null) {
            instance = new DotEnv();
        }
        return instance;
    }

    public String get(String key) {
        return dotenv.get(key);
    }

}
