package ivancecchi.reservations;

import ivancecchi.reservations.dao.BuildingsService;
import ivancecchi.reservations.dao.ReservationsService;
import ivancecchi.reservations.dao.UsersService;
import ivancecchi.reservations.dao.WorkspacesService;
import ivancecchi.reservations.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class ReservationsRunner implements CommandLineRunner {
    @Autowired
    private BuildingsService bs;
    @Autowired
    private WorkspacesService ws;
    @Autowired
    private UsersService us;
    @Autowired
    private ReservationsService rs;


    @Override
    public void run(String... args) throws Exception {

        Building building1 = bs.save(new Building("Building 1", "Via Giberti 1", "Torino"));
        Building building2 = bs.save(new Building("Building 2", "Via Venezia 46", "Cosenza"));
        Building building3 = bs.save(new Building("Building 3", "Via Solfatara 16", "Asti"));

        ws.save(new Workspace("Workspace 1", WorkspaceType.MEETING_ROOM, building1, 10));
        ws.save(new Workspace("Workspace 2", WorkspaceType.CONFERENCE_ROOM, building1, 50));
        ws.save(new Workspace("Workspace 3", WorkspaceType.OPENSPACE, building2, 15));
        ws.save(new Workspace("Workspace 4", WorkspaceType.CONFERENCE_ROOM, building2, 60));
        ws.save(new Workspace("Workspace 5", WorkspaceType.PRIVATE, building3, 3));
        ws.save(new Workspace("Workspace 6", WorkspaceType.CONFERENCE_ROOM, building3, 40));


        us.save(new User("mconsiglio", "Mattia Consiglio", "mattia.consiglio@gmail.com"));

        rs.save(new Reservation(LocalDate.now(), us.getByUsername("mconsiglio"), ws.getByDescription("Workspace 1"), 10));
    }
}
