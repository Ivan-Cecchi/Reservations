package ivancecchi.reservations.dao;

import lombok.extern.slf4j.Slf4j;
import ivancecchi.reservations.entities.Reservation;
import ivancecchi.reservations.entities.User;
import ivancecchi.reservations.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ReservationsService {
    @Autowired
    private ReservationsDAO rs;

    public void save(Reservation reservation) {
        if (rs.findReserved(reservation.getDate(), reservation.getUser(), reservation.getWorkspace()).isEmpty() && reservation.getWorkspace().getMaxOccupants() >= reservation.getNPeople()) {
            rs.save(reservation);
            log.info("Reservation saved: {}", reservation);
        } else {
            log.warn("Reservation not saved: {}Possible causes: You have already a reservation for this day (only one is allowed) and/or the people are more than the workspace allows", reservation + System.lineSeparator());
        }
    }

    public List<Reservation> getAllByUser(User user) {
        return rs.findAllByUser(user);
    }


    public List<Reservation> getAllByDate(LocalDate date) {
        return rs.findAllByDate(date);
    }

    public List<Workspace> getAvailableWorkspaces(LocalDate date, int nPeople) {
        return rs.findAvailableWorkspaces(date, nPeople);
    }
}
