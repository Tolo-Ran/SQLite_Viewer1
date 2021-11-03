package viewer;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTableJDBC {
    Object[][] data;

    protected SelectTableJDBC(String databaseName, String queryFromTextArea) {

        String url = "jdbc:sqlite:C:\\SQLiteViewerProjectDatabases\\";
        url = url.concat(databaseName);
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        String queryCount = "SELECT COUNT(tcontact_id) AS count FROM contacts;";
        try (Connection con = dataSource.getConnection()) {
            try (Statement stmt = con.createStatement()) {
                ResultSet rsCount = stmt.executeQuery(queryCount);
                int totalRows = 0;
                while (rsCount.next()) {
                    totalRows = rsCount.getInt("count");
                }

                this.data = new Object[totalRows][5];
                ResultSet rs = stmt.executeQuery(queryFromTextArea);

                int row = 0;
                int column = 0;

                while (rs.next()) {

                    int id = rs.getInt("tcontact_id");
                    String first_name = rs.getString("tfirst_name");
                    String last_name = rs.getString("tlast_name");
                    String email = rs.getString("temail");
                    String phone = rs.getString("tphone");

                    this.data[row][column] = id;
                    column++;
                    this.data[row][column] = first_name;
                    column++;
                    this.data[row][column] = last_name;
                    column++;
                    this.data[row][column] = email;
                    column++;
                    this.data[row][column] = phone;
                    column = 0;
                    row++;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
