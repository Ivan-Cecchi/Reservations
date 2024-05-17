package ivancecchi.reservations.dao;

import lombok.extern.slf4j.Slf4j;
import ivancecchi.reservations.entities.Building;
import ivancecchi.reservations.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BuildingsService {
    @Autowired
    private BuildingsDAO bd;

    public Building save(Building building) {
        try {
            return findExistingBuilding(building);
        } catch (RecordNotFoundException e) {
            building = bd.save(building);
            log.info("Building saved: {}", building);
            return building;
        }
    }

    private Building findExistingBuilding(Building b) {
        return bd.findFirstByNameAndAddressAndCity(b.getName(), b.getAddress(), b.getCity()).orElseThrow(() -> new RecordNotFoundException("Building not found"));
    }

    public List<Building> getAll() {
        return bd.findAll();
    }

}
