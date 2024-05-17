package ivancecchi.reservations.dao;

import lombok.extern.slf4j.Slf4j;
import ivancecchi.reservations.entities.Workspace;
import ivancecchi.reservations.entities.WorkspaceType;
import ivancecchi.reservations.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WorkspacesService {
    @Autowired
    private WorkspacesDAO ws;

    public void save(Workspace workspace) {
        try {
            findExistingWorkspace(workspace);
        } catch (RecordNotFoundException e) {
            ws.save(workspace);
            log.info("Workspace saved: " + workspace);
        }
    }

    public Workspace findExistingWorkspace(Workspace workspace) {
        return ws.findFirstByDescriptionAndTypeAndBuildingAndMaxOccupants(workspace.getDescription(), workspace.getType(), workspace.getBuilding(), workspace.getMaxOccupants()).orElseThrow(() -> new RecordNotFoundException("Workspace not found"));
    }


    public Workspace getByDescription(String description) {
        return ws.findFirstByDescription(description).orElseThrow(() -> new RecordNotFoundException("Workspace not found"));
    }


    public List<Workspace> getAllByBuildingCity(String buildingName, WorkspaceType type) {
        return ws.findAllByCityAndType(buildingName, type);
    }
}
