package Database;

import static edu.wpi.teame.map.LocationName.NodeType.stringToNodeType;

import edu.wpi.teame.map.LocationName;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocationDAO<E> extends DAO<LocationName> {
  List<LocationName> locationNames;

  public LocationDAO(Connection c) {
    activeConnection = c;
    table = "\"LocationName\"";
  }

  @Override
  List<LocationName> get() {
    locationNames = new LinkedList<>();

    try {
      Statement stmt = DatabaseController.INSTANCE.getC().createStatement();

      String sql = "SELECT \"longName\", \"shortName\", \"nodeType\" FROM teame.\"LocationName\";";
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        locationNames.add(
            new LocationName(
                rs.getString("longName"),
                rs.getString("shortName"),
                stringToNodeType(rs.getString("nodeType"))));
      }

      return locationNames;
    } catch (SQLException e) {
      throw new RuntimeException("Something went wrong");
    }
  }

  @Override
  void update(LocationName locationName, String attribute, String value) {
    String longName = locationName.getLongName();
    String sqlUpdate =
        "UPDATE \"LocationName\" "
            + "SET \""
            + attribute
            + "\" = '"
            + value
            + "' WHERE \"longName\" = '"
            + longName
            + "';";

    try {
      Statement stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlUpdate);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(
          "Exception: Cannot duplicate two set of the same locationNames, longName has to exist, shortName can be any, node type has a specific enum");
    }
  }

  @Override
  void delete(LocationName locationName) {
    String lName = locationName.getLongName();
    String sqlDelete = "DELETE FROM \"LocationName\" WHERE \"longName\" = '" + lName + "';";

    try {
      Statement stmt = activeConnection.createStatement();
      stmt.execute(sqlDelete);
      stmt.close();
    } catch (SQLException e) {
      System.out.println("error deleting");
    }
  }

  @Override
  void add(LocationName locationName) {
    String lName = locationName.getLongName();
    String shortName = locationName.getShortName();
    String nodeType = LocationName.NodeType.nodeToString(locationName.getNodeType());
    String sqlAdd =
        "INSERT INTO \"LocationName\" VALUES('"
            + lName
            + "','"
            + shortName
            + "','"
            + nodeType
            + "');";

    Statement stmt;
    try {
      stmt = activeConnection.createStatement();
      stmt.executeUpdate(sqlAdd);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  void importFromCSV(String filePath, String tableName) {
    try {
      BufferedReader lreader = new BufferedReader(new FileReader(filePath));
      String line;
      List<String> rows = new ArrayList<>();
      while ((line = lreader.readLine()) != null) {
        rows.add(line);
      }
      rows.remove(0);
      lreader.close();
      Statement stmt = activeConnection.createStatement();

      String sqlDelete = "DELETE FROM \"" + tableName + "\";";
      stmt.execute(sqlDelete);

      for (String l1 : rows) {
        String[] splitL1 = l1.split(",");
        String sql =
            "INSERT INTO \""
                + tableName
                + "\""
                + " VALUES ('"
                + splitL1[0]
                + "','"
                + splitL1[1]
                + "','"
                + splitL1[2]
                + "'); ";
        stmt.execute(sql);
      }

      System.out.println(
          "Imported " + (rows.size()) + " rows from " + filePath + " to " + tableName);

    } catch (IOException | SQLException e) {
      System.err.println("Error importing from " + filePath + " to " + tableName);
      e.printStackTrace();
    }
  }
}
