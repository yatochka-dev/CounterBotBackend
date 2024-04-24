package mainbot.helper;

// singleton
public class Config {


    public static final int embedColor = 0x00ff00;
    public static final String websiteURL = "https://example.com";

    public static Config instance = null;

    private Config() {
        // Constructor

    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }


    public int getEmbedColor() {
        return embedColor;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }
}
