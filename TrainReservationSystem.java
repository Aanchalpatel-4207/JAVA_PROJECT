import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TrainReservationSystem {

    static int pnrCounter = 100000001;
    static Map<Integer, Train> trains = new HashMap<>();

    public static void main(String[] args) {
        initializeTrains(); // Predefined trains

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Starting Point: ");
        String start = scanner.nextLine();

        System.out.print("Enter Destination: ");
        String end = scanner.nextLine();

        System.out.print("Enter class (S/B/A/H): ");
        String coachClass = scanner.nextLine().toUpperCase();

        System.out.print("No. of passengers: ");
        int passengers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Train> availableTrains = findAvailableTrains(start, end, coachClass);

        if (availableTrains.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }

        System.out.println("Total Trains=" + availableTrains.size());
        for (int i = 0; i < availableTrains.size(); i++) {
            System.out.println((i + 1) + ". " + availableTrains.get(i));
        }

        System.out.print("Enter Train number to book Ticket: ");
        int selectedTrainNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Train selectedTrain = trains.get(selectedTrainNumber);

        if (selectedTrain == null) {
            System.out.println("Invalid train number.");
            return;
        }

        int fare = calculateFare(selectedTrain, coachClass, passengers);
        if(fare == -1){
            return;
        }
        System.out.println("Total Fare= " + fare);

        System.out.print("Booking Confirm (Yes/No): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Yes")) {
            if (bookTickets(selectedTrain, coachClass, passengers)) {
                System.out.println("Ticket Booked");
                System.out.println("PNR:" + pnrCounter);
                System.out.print("Seat Allocated: " + selectedTrain.trainNumber + " " + selectedTrain.source + " " + selectedTrain.destination + " ");
                printSeatAllocations(selectedTrain, coachClass, passengers);
                pnrCounter++;
            } else {
                System.out.println("No Seats Available");
            }
        } else {
            System.out.println("Booking Cancelled.");
        }

    }

    static void initializeTrains() {
        trains.put(17726, new Train(17726, "Rajkot", "Mumbai", "S1-72,S2-72,B1-72,A1-48,H1-24"));
        trains.put(17728, new Train(17728, "Rajkot", "Mumbai", "S1-15,S2-20,S3-50,B1-36,B2-48"));
    }

    static List<Train> findAvailableTrains(String source, String destination, String coachClass) {
        List<Train> result = new ArrayList<>();
        for (Train train : trains.values()) {
            if (train.source.equalsIgnoreCase(source) && train.destination.equalsIgnoreCase(destination)) {
                result.add(train);
            }
        }
        return result;
    }

    static int calculateFare(Train train, String coachClass, int passengers) {
        int distance = 750; // Distance between Rajkot and Mumbai
        int pricePerKm = 0;

        switch (coachClass) {
            case "S":
                pricePerKm = 1;
                break;
            case "B":
                pricePerKm = 2;
                break;
            case "A":
                pricePerKm = 3;
                break;
            case "H":
                pricePerKm = 4;
                break;
            default:
                System.out.println("Invalid coach class.");
                return -1;
        }

        return distance * pricePerKm * passengers;
    }

    static boolean bookTickets(Train train, String coachClass, int passengers) {
        Map<String, List<Integer>> coachSeats = train.getCoachSeats();
        List<String> coachOrder = getCoachOrder(coachClass);

        List<String> allocatedSeats = new ArrayList<>();

        for (String coach : coachOrder) {
            if (coachSeats.containsKey(coach)) {
                List<Integer> seats = coachSeats.get(coach);
                for (int seat : seats) {
                    allocatedSeats.add(coach + ":" + seat);
                    if (allocatedSeats.size() == passengers) {
                        allocateSeats(train, allocatedSeats);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static void allocateSeats(Train train, List<String> allocatedSeats) {
        Map<String, List<Integer>> coachSeats = train.getCoachSeats();
        for (String allocation : allocatedSeats) {
            String[] parts = allocation.split(":");
            String coach = parts[0];
            int seat = Integer.parseInt(parts[1]);
            coachSeats.get(coach).remove(Integer.valueOf(seat));
        }
    }

    static void printSeatAllocations(Train train, String coachClass, int passengers) {
         Map<String, List<Integer>> coachSeats = train.getCoachSeats();
        List<String> coachOrder = getCoachOrder(coachClass);
        int seatCount = 0;
        for(String coach : coachOrder){
            if(coachSeats.containsKey(coach)){
                for(int seat: train.getOriginalCoachSeats().get(coach)){
                    if(!coachSeats.get(coach).contains(seat) && seatCount < passengers){
                        System.out.print(coach + ":" + seat + ",");
                        seatCount++;
                    }
                }
            }
        }
    }

    static List<String> getCoachOrder(String coachClass) {
        List<String> order = new ArrayList<>();
        if (coachClass.equals("S")) {
            order.add("S1");
            order.add("S2");
            order.add("S3");
        } else if (coachClass.equals("B")) {
            order.add("B1");
            order.add("B2");
        } else if (coachClass.equals("A")) {
            order.add("A1");
        } else if (coachClass.equals("H")) {
            order.add("H1");
        }
        return order;
    }
}

class Train {
    int trainNumber;
    String source;
    String destination;
    String coachInfo;
    Map<String, List<Integer>> coachSeats;
    Map<String, List<Integer>> originalCoachSeats;

    public Train(int trainNumber, String source, String destination, String coachInfo) {
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.coachInfo = coachInfo;
        this.coachSeats = parseCoachInfo(coachInfo);
        this.originalCoachSeats = parseCoachInfo(coachInfo);
    }

    private Map<String, List<Integer>> parseCoachInfo(String coachInfo) {
        Map<String, List<Integer>> map = new HashMap<>();
        String[] coaches = coachInfo.split(",");
        for (String coach : coaches) {
            String[] parts = coach.split("-");
            String coachName = parts[0];
            int seats = Integer.parseInt(parts[1]);
            List<Integer> seatList = new ArrayList<>();
            for (int i = 1; i <= seats; i++) {
                seatList.add(i);
            }
            map.put(coachName, seatList);
        }
        return map;
    }

    public Map<String, List<Integer>> getCoachSeats() {
        return coachSeats;
    }
     public Map<String, List<Integer>> getOriginalCoachSeats() {
        return originalCoachSeats;
    }

    @Override
    public String toString() {
        return trainNumber + " " + source + " " + destination + " " + coachInfo;
    }
}