package Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DatabaseUtlityTest {

  @Test
  public void testGetNodeTypeFromNodeID() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    String nodetype = SQLRepo.INSTANCE.getNodeTypeFromNodeID(1200);
    assertEquals("HALL", nodetype);

    String nodetype2 = SQLRepo.INSTANCE.getNodeTypeFromNodeID(1320);
    assertEquals("RETL", nodetype2);

    String nodetype3 = SQLRepo.INSTANCE.getNodeTypeFromNodeID(1360);
    assertEquals("DEPT", nodetype3);
  }

  @Test
  public void testGetNodeFromFloor() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    List<HospitalNode> nodelist = SQLRepo.INSTANCE.getNodesFromFloor(Floor.LOWER_ONE);

    assertEquals(45, nodelist.size());
  }
}
