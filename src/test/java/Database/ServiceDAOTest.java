package Database;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.ServiceRequestData;
import java.util.List;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class ServiceDAOTest {

  @Test
  public void testGetAddDelete() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    List<ServiceRequestData> srd = SQLRepo.INSTANCE.getServiceRequestList();
    assertEquals(9, srd.size());

    SQLRepo.INSTANCE.addServiceRequest(
        new ServiceRequestData(
            ServiceRequestData.RequestType.FLOWERDELIVERY,
            new JSONObject(),
            ServiceRequestData.Status.PENDING,
            "Jamie"));
    List<ServiceRequestData> srdAdded = SQLRepo.INSTANCE.getServiceRequestList();
    assertEquals(10, srdAdded.size());

    SQLRepo.INSTANCE.deleteServiceRequest(
        new ServiceRequestData(
            ServiceRequestData.RequestType.FLOWERDELIVERY,
            new JSONObject(),
            ServiceRequestData.Status.PENDING,
            "Jamie"));
    List<ServiceRequestData> srdDeleted = SQLRepo.INSTANCE.getServiceRequestList();
    assertEquals(9, srdDeleted.size());

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testUpdate() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    ServiceRequestData srd1 =
        new ServiceRequestData(
            ServiceRequestData.RequestType.FLOWERDELIVERY,
            new JSONObject(),
            ServiceRequestData.Status.PENDING,
            "Diyar");
    SQLRepo.INSTANCE.addServiceRequest(srd1);
    SQLRepo.INSTANCE.updateServiceRequest(srd1, "status", "DONE");

    ServiceRequestData srd2 =
        new ServiceRequestData(
            ServiceRequestData.RequestType.FLOWERDELIVERY,
            new JSONObject("{\"Hello\":\"Test2\"}"),
            ServiceRequestData.Status.PENDING,
            "Diyar");
    SQLRepo.INSTANCE.addServiceRequest(srd2);
    SQLRepo.INSTANCE.updateServiceRequest(srd2, "requestdata", "{\"Hello\":\"Testing2\"}");

    ServiceRequestData srd3 =
        new ServiceRequestData(
            ServiceRequestData.RequestType.FLOWERDELIVERY,
            new JSONObject("{\"Hello\":\"Test3\"}"),
            ServiceRequestData.Status.PENDING,
            "Diyar");
    SQLRepo.INSTANCE.addServiceRequest(srd3);
    SQLRepo.INSTANCE.updateServiceRequest(srd3, "staffassigned", "Jamie Rapal");

    // SQLRepo.INSTANCE.deleteServiceRequest(srd);
    SQLRepo.INSTANCE.exitDatabaseProgram();
  }

  @Test
  public void testImportExport() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    SQLRepo.INSTANCE.exportToCSV(
        SQLRepo.Table.SERVICE_REQUESTS,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop",
        "ServiceRequests");
    SQLRepo.INSTANCE.importFromCSV(
        SQLRepo.Table.SERVICE_REQUESTS,
        "C:\\Users\\jamie\\OneDrive - Worcester Polytechnic Institute (wpi.edu)\\Desktop\\ServiceRequests");

    SQLRepo.INSTANCE.exitDatabaseProgram();
  }
}
