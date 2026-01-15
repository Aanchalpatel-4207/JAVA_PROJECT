//Phase 3
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class InvalidTicketQuantityException extends Exception 
{
    public InvalidTicketQuantityException(String message) 
    {
        super(message);
    }
}

class DateTimePrinter extends Thread 
{
    public void run() 
    {
        while (true) 
        {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("Current Date and Time: " + now.format(formatter));
            try 
            {
                Thread.sleep(5000); 
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }
}

public class TicketBookingException 
{

    private static Map<Integer, String> bookedTickets = new HashMap<>();
    private static int nextSeatNumber = 1;

    public static void main(String[] args) 
    {
        DateTimePrinter dateTimePrinter = new DateTimePrinter();
        dateTimePrinter.start();

        Scanner scanner = new Scanner(System.in);

        while (true) 
        {
            System.out.println("\nEnter your Choice:");
            System.out.println("1: Book Ticket");
            System.out.println("2: View Available Tickets");
            System.out.println("3: Cancel Ticket");
            System.out.println("4: Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) 
            {
                case 1:
                    bookTicket(scanner);
                    break;
                case 2:
                    viewAvailableTickets();
                    break;
                case 3:
                    cancelTicket(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void bookTicket(Scanner scanner) 
    {
        System.out.println("Welcome to Ticket Booking");
        int numberOfTickets = 0;
        try 
        {
            System.out.print("Enter no. of tickets: ");
            numberOfTickets = scanner.nextInt();
            scanner.nextLine(); 
            if (numberOfTickets <= 0) 
            {
                throw new InvalidTicketQuantityException("Ticket quantity must be greater than zero.");
            }
        } 
        catch (InvalidTicketQuantityException e) 
        {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.print("Enter Source: ");
        String source = scanner.nextLine();

        System.out.print("Enter Destination: Mumbai (750 KM) / Ahmedabad (230 KM): ");
        String destination = scanner.nextLine();

        System.out.print("Enter/Select Coach: 1AC/2AC/3AC/Sleeper: ");
        String coach = scanner.nextLine().toUpperCase();

        double distance = 0;
        if (destination.equalsIgnoreCase("Mumbai")) 
        {
            distance = 750;
        } else if (destination.equalsIgnoreCase("Ahmedabad")) 
        {
            distance = 230;
        } else 
        {
            System.out.println("Invalid destination.");
            return;
        }

        double pricePerKm = 0;
        switch (coach) 
        {
            case "1AC":
                pricePerKm = 10;
                break;
            case "2AC":
                pricePerKm = 7;
                break;
            case "3AC":
                pricePerKm = 5;
                break;
            case "SLEEPER":
                pricePerKm = 2;
                break;
            default:
                System.out.println("Invalid coach selection.");
                return;
        }

        double totalPrice = distance * pricePerKm * numberOfTickets;

        System.out.println("Allocated Seat Numbers:");
        for (int i = 0; i < numberOfTickets; i++) 
        {
            bookedTickets.put(nextSeatNumber, source + " to " + destination + " (" + coach + ")");
            System.out.println("Seat " + nextSeatNumber);
            nextSeatNumber++;
        }

        System.out.println("Total Ticket Price: $" + totalPrice);
    }

    private static void viewAvailableTickets() 
    {
        if (bookedTickets.isEmpty()) {
            System.out.println("No tickets booked yet.");
        } else {
            System.out.println("Booked Tickets:");
            for (Map.Entry<Integer, String> entry : bookedTickets.entrySet()) {
                System.out.println("Seat " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    private static void cancelTicket(Scanner scanner) 
    {
        System.out.print("Enter Seat Number to cancel: ");
        int seatNumber = scanner.nextInt();
        scanner.nextLine();

        if (bookedTickets.containsKey(seatNumber)) {
            bookedTickets.remove(seatNumber);
            System.out.println("Ticket for Seat " + seatNumber + " cancelled.");
        } else {
            System.out.println("Seat " + seatNumber + " not found.");
        }
    }
}