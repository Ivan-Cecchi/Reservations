package ivancecchi.reservations.dao;

import ivancecchi.reservations.entities.Reservation;
import ivancecchi.reservations.entities.User;
import ivancecchi.reservations.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationsDAO extends JpaRepository<Reservation, UUID> {
    @Query("SELECT r FROM Reservation r WHERE (r.date = :date AND r.user = :user) OR (r.workspace = :workspace AND r.date = :date)")
    List<Reservation> findReserved(LocalDate date, User user, Workspace workspace);

    List<Reservation> findAllByUser(User user);

    List<Reservation> findAllByDate(LocalDate date);

    //    @Query("SELECT r.workspace FROM Reservation r WHERE r.date != :date AND r.workspace IN (SELECT w FROM Workspace w WHERE w.maxOccupants >= :nPeople)")
    @Query("SELECT ws FROM Workspace ws WHERE ws.maxOccupants >= :nPeople AND ws.id NOT IN (SELECT r.workspace.id FROM Reservation r WHERE r.date = :date)")
    List<Workspace> findAvailableWorkspaces(LocalDate date, int nPeople);
}
