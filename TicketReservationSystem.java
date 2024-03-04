import java.util.Scanner;

class Passenger {
    String name;
    int age;
    String gender;
    String berthPreference;
    String allocatedBerth;
    String allocatedBerthType;

    public Passenger(String name, int age, String gender, String berthPreference) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
        this.allocatedBerth = null;
        this.allocatedBerthType = null;
    }
}


public class TicketReservationSystem {
    private Passenger[] confirmedTickets = new Passenger[63]; // 63 confirmed ticket berths
    private Passenger[] racTickets = new Passenger[18]; // 18 RAC ticket berths
    private Passenger[] waitingList = new Passenger[10]; // 10 waiting list tickets
    private int bookedTicketsCount = 0;

    public TicketReservationSystem() {
        for (int i = 0; i < confirmedTickets.length; i++) {
            confirmedTickets[i] = null;
        }
        for (int i = 0; i < racTickets.length; i++) {
            racTickets[i] = null;
        }
        for (int i = 0; i < waitingList.length; i++) {
            waitingList[i] = null;
        }
    }

    private String[] allocateBerth(Passenger passenger) {
        if (passenger.age > 60 || (passenger.gender.equals("F") && passenger.age > 5)) {
            for (int i = 0; i < confirmedTickets.length; i++) {
                if (confirmedTickets[i] == null && i % 3 == 0) {
                    return new String[]{"C" + (i + 1), "Lower"};
                }
            }
        } else if (passenger.berthPreference.equals("SL")) {
            if (racTickets[0] == null) {
                return new String[]{"R1", "Side-Lower"};
            }
        } else if (passenger.berthPreference.equals("MW")) {
            for (int i = 0; i < confirmedTickets.length; i++) {
                if (confirmedTickets[i] == null && i % 3 == 1) {
                    return new String[]{"C" + (i + 1), "Middle"};
                }
            }
        } else {
            for (int i = 0; i < confirmedTickets.length; i++) {
                if (confirmedTickets[i] == null) {
                    String berthType = i % 3 == 0 ? "Lower" : (i % 3 == 1 ? "Middle" : "Upper");
                    return new String[]{"C" + (i + 1), berthType};
                }
            }
            if (racTickets[0] == null) {
                return new String[]{"R1", "Side-Lower"};
            }
            if (waitingList[0] == null) {
                return new String[]{"W1", "Waiting List"};
            }
        }
        return new String[]{null, null};
    }

    public void bookTicket(Passenger passenger) {
        if (passenger.age < 5) {
            System.out.println("No tickets available for children below 5 years.");
            return;
        }

        String[] result = allocateBerth(passenger);
        String berth = result[0];
        String berthType = result[1];

        if (berth == null) {
            System.out.println("No tickets available.");
            return;
        }

        if (berth.startsWith("C")) {
            confirmedTickets[Integer.parseInt(berth.substring(1)) - 1] = passenger;
        } else if (berth.startsWith("R")) {
            racTickets[0] = passenger;
        } else if (berth.startsWith("W")) {
            waitingList[0] = passenger;
        }

        passenger.allocatedBerth = berth;
        passenger.allocatedBerthType = berthType;

        System.out.println("Ticket booked successfully. Berth: " + berth + " (" + berthType + ")");
        bookedTicketsCount++;
    }

    public void cancelTicket(String berth) {
        if (berth.startsWith("C")) {
            confirmedTickets[Integer.parseInt(berth.substring(1)) - 1] = null;
        } else if (berth.startsWith("R")) {
            racTickets[0] = null;
        } else if (berth.startsWith("W")) {
            waitingList[0] = null;
        }
    }

    public void printBookedTickets() {
        System.out.println("Booked Tickets:");
        for (int i = 0; i < confirmedTickets.length; i++) {
            if (confirmedTickets[i] != null) {
                System.out.println("Berth C" + (i + 1) + " (" + confirmedTickets[i].allocatedBerthType + "): " + confirmedTickets[i].name + ", Age: " + confirmedTickets[i].age + ", Gender: " + confirmedTickets[i].gender);
            }
        }

        if (racTickets[0] != null) {
            System.out.println("Berth R1: " + racTickets[0].name + ", Age: " + racTickets[0].age + ", Gender: " + racTickets[0].gender);
        }

        if (waitingList[0] != null) {
            System.out.println("Berth W1: " + waitingList[0].name + ", Age: " + waitingList[0].age + ", Gender: " + waitingList[0].gender);
        }

        System.out.println("Total Booked Tickets: " + bookedTicketsCount);
    }

    public void printAvailableTickets() {
        System.out.println("Available Tickets:");

        for (int i = 0; i < confirmedTickets.length; i++) {
            if (confirmedTickets[i] == null) {
                System.out.println("Berth C" + (i + 1) + " is available.");
            }
        }

        for (int i = 0; i < racTickets.length; i++) {
            if (racTickets[i] == null) {
                System.out.println("Berth R" + (i + 1) + " is available.");
            }
        }

        for (int i = 0; i < waitingList.length; i++) {
            if (waitingList[i] == null) {
                System.out.println("Berth W" + (i + 1) + " is available.");
            }
        }
    }

    public static void main(String[] args) {
        TicketReservationSystem railwaySystem = new TicketReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nIndian Railway Ticket Reservation System");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Print Booked Tickets");
            System.out.println("4. Print Available Tickets");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter passenger name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter passenger age: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter passenger gender (M/F): ");
                    String gender = scanner.nextLine().toUpperCase();
                    System.out.print("Enter berth preference (SL/UP/MW/LW): ");
                    String berthPreference = scanner.nextLine().toUpperCase();

                    Passenger passenger = new Passenger(name, age, gender, berthPreference);
                    railwaySystem.bookTicket(passenger);
                    break;
                case "2":
                    System.out.print("Enter berth to cancel: ");
                    String berth = scanner.nextLine();
                    railwaySystem.cancelTicket(berth);
                    System.out.println("Ticket for berth " + berth + " cancelled successfully.");
                    break;
                case "3":
                    railwaySystem.printBookedTickets();
                    break;
                case "4":
                    railwaySystem.printAvailableTickets();
                    break;
                case "5":
                    System.out.println("Exiting the application. Thank you!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
