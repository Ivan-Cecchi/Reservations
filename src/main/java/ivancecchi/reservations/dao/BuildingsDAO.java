package ivancecchi.reservations.dao;

import mattiaconsiglio.gestionePrenotazioni.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BuildingsDAO extends JpaRepository<Building, UUID> {
    public Optional<Building> findFirstByNameAndAddressAndCity(String name, String address, String city);
}
