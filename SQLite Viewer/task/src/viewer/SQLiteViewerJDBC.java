package viewer;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLiteViewerJDBC {

    private final List<String> tableNames;

    protected SQLiteViewerJDBC(String databaseName) {

        this.tableNames = new ArrayList<>();

        String url = "jdbc:sqlite:C:\\SQLiteViewerProjectDatabases\\".concat(databaseName);
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        //SQL to get table' names
        String query = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String name = rs.getString("name");
                    tableNames.add(name);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTableNames() {
        return tableNames;
    }
}
