package ivancecchi.reservations.dao;

import ivancecchi.reservations.entities.Building;
import ivancecchi.reservations.entities.Workspace;
import ivancecchi.reservations.entities.WorkspaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspacesDAO extends JpaRepository<Workspace, UUID> {
    Optional<Workspace> findFirstByDescriptionAndTypeAndBuildingAndMaxOccupants(String description, WorkspaceType type, Building building, int maxOccupants);

    Optional<Workspace> findFirstByDescription(String description);

    @Query("SELECT ws FROM Workspace ws WHERE LOWER(ws.building.city) = LOWER(:buildingName) AND ws.type =:type")
    List<Workspace> findAllByCityAndType(String buildingName, WorkspaceType type);
    
}
