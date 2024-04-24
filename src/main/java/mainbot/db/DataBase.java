package mainbot.db;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.ExecutableQuery;
import org.neo4j.driver.GraphDatabase;

public class DataBase {
    private static mainbot.db.DataBase instance = null;

    final String dbUri = "neo4j+s://aa12c4ff.databases.neo4j.io";
    final String dbUser = "neo4j";
    final String dbPassword = "PPpNndojWtHaV2u5QSEnFdTtl42t5uIva7Mm9txKCqQ";
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
    
    public void disconnect() {
        // Disconnect from the database
        this.driver.close();
    }
}