package mainbot.db;

import mainbot.dotenv.DotEnv;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.ExecutableQuery;
import org.neo4j.driver.GraphDatabase;

public class DataBase {
    private static mainbot.db.DataBase instance = null;

    private static final DotEnv dotenv = DotEnv.getInstance();
    final String dbUri = dotenv.get("NEO4J_URI");
    final String dbUser = dotenv.get("NEO4J_USER");

    final String dbPassword = dotenv.get("NEO4J_PASSWORD");
    public Driver driver = null;

    private DataBase() {
        // Constructor
        connect();
    }

    public static mainbot.db.DataBase getInstance() {
        if (instance == null) {
            instance = new mainbot.db.DataBase();
        }
        return instance;
    }

    public void connect() {
        // Connect to the database
        this.driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
    }

    public ExecutableQuery executableQuery(String query) {
        return this.driver.executableQuery(query);
    }

}