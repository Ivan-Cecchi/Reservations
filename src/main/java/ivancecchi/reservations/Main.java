package ivancecchi.reservations;

import lombok.extern.slf4j.Slf4j;
import ivancecchi.reservations.dao.BuildingsService;
import ivancecchi.reservations.dao.ReservationsService;
import ivancecchi.reservations.dao.UsersService;
import ivancecchi.reservations.dao.WorkspacesService;
import ivancecchi.reservations.entities.*;
import ivancecchi.reservations.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static ivancecchi.reservations.Utilities.*;

@SpringBootApplication
@Slf4j
public class GestionePrenotazioniApplication {


    private static Scanner scanner;


    private static Utilities utils;

    private static WorkspacesService ws;
    private static ReservationsService rs;
    private static UsersService us;
    private static BuildingsService bs;


    @Autowired
    //initialize static fields with beans
    public GestionePrenotazioniApplication(Scanner scanner, Utilities utils, WorkspacesService ws, ReservationsService rs, UsersService us, BuildingsService bs) {
        GestionePrenotazioniApplication.scanner = scanner;
        GestionePrenotazioniApplication.utils = utils;
        GestionePrenotazioniApplication.ws = ws;
        GestionePrenotazioniApplication.rs = rs;
        GestionePrenotazioniApplication.us = us;
        GestionePrenotazioniApplication.bs = bs;
    }

    public static void main(String[] args) {
        SpringApplication.run(GestionePrenotazioniApplication.class, args);

        mainMenu();
    }

    public static void mainMenu() {

        while (true) {
            System.out.println("--------- Menu principale ---------");
            System.out.println();
            System.out.println("Scegli un'opzione");
            System.out.println("1. Search workspaces by city");
            System.out.println("2. See your reservations");
            System.out.println("3. Create a reservation");
            System.out.println("4. Create a user");
            System.out.println("5. Create a workspace");
            System.out.println("6. Create a building");
            System.out.println("0. Exit");
            System.out.println();


            String option = scanner.nextLine().trim();

            switch (option) {
                case "0": {
                    System.out.println("Closing application...");
                    return;
                }
                case "1": {
                    String city = askAndVerifyString("Insert a city");
                    WorkspaceType type = askAndVerifyEnum("Choose a workspace type", WorkspaceType.class);
                    List<Workspace> workspaces = ws.getAllByBuildingCity(city, type);
                    if (workspaces.isEmpty()) {
                        System.err.println("No workspaces found");
                        break;
                    }
                    System.out.println("Workspaces found:");
                    workspaces.forEach(System.out::println);
                    pressEnterToContinue();

                    break;
                }
                case "2": {
                    String emailOrUsername = askAndVerifyString("Insert your email/username");
                    try {
                        User user = us.getByUsernameOrEmail(emailOrUsername);
                        List<Reservation> reservations = rs.getAllByUser(user);
                        if (reservations.isEmpty()) {
                            System.out.println("No reservations found");
                            break;
                        }
                        System.out.println("Reservations found:");
                        reservations.forEach(System.out::println);
                        pressEnterToContinue();
                    } catch (RecordNotFoundException e) {
                        log.error("User not found for email/username: {}", emailOrUsername);
                    }

                    break;
                }
                case "3": {
                    String emailOrUsername = askAndVerifyString("Insert your email/username");
                    try {
                        User user = us.getByUsernameOrEmail(emailOrUsername);
                        while (true) {
                            LocalDate date = askAndVerifyDate("Insert the date");

                            if (!rs.getAllByDate(date).isEmpty()) {
                                String answer = askAndVerifyStringList("There is already a reservation for this date. Do you want to choose another date? ", List.of("yes", "no"));
                                assert answer != null;
                                if (answer.equals("no")) {
                                    break;
                                }
                            } else {
                                int nPeople = askAndVerifyInt("Insert the number of people");
                                List<Workspace> workspaces = rs.getAvailableWorkspaces(date, nPeople);
                                if (workspaces.isEmpty()) {
                                    System.err.println("No workspaces found");
                                    break;
                                }
                                System.out.println("Workspaces found:");
                                Workspace workspace = askAndVerifyList("Choose a workspace", workspaces, "Workspace", true);
                                Reservation reservation = new Reservation(date, user, workspace, nPeople);
                                rs.save(reservation);
                                pressEnterToContinue();
                                break;
                            }
                        }

                    } catch (RecordNotFoundException e) {
                        log.error("User not found for email/username: {}", emailOrUsername);
                    }
                    break;
                }
                case "4": {
                    String username = askAndVerifyString("Insert your username");
                    String email = askAndVerifyString("Insert your email");
                    String fullName = askAndVerifyString("Insert your full name");
                    User user = new User(username, email, fullName);
                    us.save(user);
                    pressEnterToContinue();
                    break;
                }
                case "5": {
                    String description = askAndVerifyString("Insert the name of the workspace");
                    WorkspaceType type = askAndVerifyEnum("Choose a workspace type", WorkspaceType.class);
                    Building building = askAndVerifyList("Choose a building", bs.getAll(), "Building", true);
                    int maxOccupants = askAndVerifyInt("Insert the maximum number of occupants");
                    Workspace workspace = new Workspace(description, type, building, maxOccupants);
                    ws.save(workspace);
                    pressEnterToContinue();
                    break;
                }
                case "6": {
                    String name = askAndVerifyString("Insert the name of the building");
                    String address = askAndVerifyString("Insert the address");
                    String city = askAndVerifyString("Insert the city");
                    Building building = new Building(name, address, city);
                    bs.save(building);
                    pressEnterToContinue();
                    break;
                }
                default:
                    System.err.println("Opzione non valida, scegliere un'opzione valida");
            }
        }
    }

}
