//Phase 2
import java.util.Scanner;

interface TicketCalculations 
{
    double calculateFare(String coach, int distance, int passengers);
}

abstract class AbstractTrain
{
    String source;
    String destination;
    int distance;

    public AbstractTrain(String source, String destination, int distance) 
    {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public abstract void displayTrainDetails();
}

class RajkotMumbaiTrain extends AbstractTrain implements TicketCalculations
{

    public RajkotMumbaiTrain(String source, String destination, int distance) 
    {
        super(source, destination, distance);
    }

    public void displayTrainDetails()
    {
        System.out.println("Train Details:");
        System.out.println("Source=" + source);
        System.out.println("Destination=" + destination);
        System.out.println("Distance=" + distance + " KM");
    }

    public double calculateFare(String coach, int distance, int passengers)
    {
        double farePerKM = 0;
        
        switch (coach.toUpperCase()) 
        {
            case "SL":
                farePerKM = 1;
                break;
            case "3A":
                farePerKM = 2;
                break;
            case "2A":
                farePerKM = 3;
                break;
            case "1A":
                farePerKM = 4;
                break;
        }
        return distance * farePerKM * passengers;
    }
}


public class TicketBooking 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        AbstractTrain train = new RajkotMumbaiTrain("Rajkot", "Mumbai", 750);
        train.displayTrainDetails();

        try 
        {
            System.out.print("Enter number of tickets=");
            int passengers = scanner.nextInt();

            System.out.print("Enter coach type (SL/3A/2A/1A)=");
            String coach = scanner.next();

            TicketCalculations ticketCalculator = (TicketCalculations) train;
            double fare = ticketCalculator.calculateFare(coach, train.distance, passengers);

            System.out.println("Total fare=" + fare);

        } 
        catch (IllegalArgumentException e) 
        {
            System.out.println("Error=" + e.getMessage());
        } 
        
        finally 
        {
            scanner.close();
            System.out.println("Booking process completed.");
        }

    }
}