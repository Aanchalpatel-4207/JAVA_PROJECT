//Phase 1
import java.util.Scanner;

class Passenger 
{
    String name;
    int age;
    String gender; 

    Passenger(String name, int age,String gender) 
    {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    void Passenger()
    {
        System.out.println("name="+name);
        System.out.println("age="+age);
        System.out.println("gender="+gender);
    }

}

class Coach 
{
    String coachNumber;
    int capacity;

    Coach(String coachNumber, int capacity) 
    {
        this.coachNumber = coachNumber;
        this.capacity = capacity;
    }

    void Coach()
    {
        System.out.println("coachNumber="+coachNumber);
        System.out.println("capacity="+capacity);
    }
}

class ACCoach extends Coach 
{
    int Acseats;

    ACCoach(String coachNumber, int capacity, int Acseats) 
    {
        super(coachNumber, capacity);
        this.Acseats = Acseats;
    }

    void ACCoach()
    {
        System.out.println("Acseats="+Acseats);
    }
}

class SleeperCoach extends Coach 
{
    int sleeperseats;

    SleeperCoach(String coachNumber, int capacity, int sleeperseats) 
    {
        super(coachNumber, capacity);
        this.sleeperseats = sleeperseats;
    }

    void SleeperCoach()
    {
        System.out.println("sleeperseats="+sleeperseats);
    }
}

class Train
{
    String trainNumber;

    Train(String trainNumber) 
    {
        this.trainNumber = trainNumber;
    }
    
    void Train()
    {
        System.out.println("trainNumber="+trainNumber);
    }
}

class Ticket  extends Coach
{
    String ticketNumber;
    int ticketCounter;

    Ticket(String coachNumber, int capacity,String ticketNumber, int ticketCounter)
    {
        super(coachNumber, capacity);
        this.ticketNumber = ticketNumber;
        this.ticketCounter = ticketCounter;
    }

    void Ticket()
    {
        System.out.println("ticketNumber="+ticketNumber);
        System.out.println("ticketCounter="+ticketCounter);
    }
}

public class RailwayBooking
{
    public static void main(String[] args) 
    {
        Scanner sc=new Scanner(System.in);

        System.out.println("name=");
        String name = sc.next();
        System.out.println("age=");
        int age = sc.nextInt();
        System.out.println("gender=");
        String gender = sc.next();
        System.out.println("coachNumber=");
        String coachNumber = sc.next();
        System.out.println("capacity=");
        int capacity = sc.nextInt();
        System.out.println("Acseats=");
        int Acseats = sc.nextInt();
        System.out.println("sleeperseats=");
        int sleeperseats = sc.nextInt();
        System.out.println("trainNumber=");
        String trainNumber = sc.next();
        System.out.println("ticketNumber=");
        String ticketNumber = sc.next();
        System.out.println("ticketCounter=");
        int ticketCounter = sc.nextInt();

        Passenger p = new Passenger(name, age, gender);
        p.Passenger();

        Coach c = new Coach(coachNumber, capacity);
        c.Coach();

        ACCoach ac = new ACCoach(coachNumber, capacity, Acseats);
        ac.ACCoach();

        SleeperCoach sl = new SleeperCoach(coachNumber, capacity, sleeperseats);
        sl.SleeperCoach();

        Train t = new Train(trainNumber);
        t.Train();

        Ticket tkt = new Ticket(coachNumber, capacity,ticketNumber,ticketCounter);
        tkt.Ticket();
    }
}