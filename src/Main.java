import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    static SingleLinkedList carList = new SingleLinkedList();
    static SingleLinkedList trackList = new SingleLinkedList();

    public static void main(String[] args) {

        for(int i = 0; i<70; i++)
            System.out.print("=");
        System.out.println("\n                  RACE TOURNAMENT SIMULATOR\n");
        for(int i = 0; i<70; i++)
            System.out.print("=");
        System.out.println();

        System.out.println("[1] SYSTEM INITIALIZATION");
        loadCars("cars.txt");
        loadTracks("tracks.txt");
        System.out.println();

        printCarList();
        System.out.println();

        printTrackList();
    }
    static void loadCars(String filename) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                char[] name = parts[1].trim().toCharArray();
                int score = Integer.parseInt(parts[2].trim());
                char[] type = parts[3].trim().toCharArray();

                Car car = new Car(id, name, score, type);
                carList.insertSorted(car);
                count++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Hata: " + filename + " okunamadi.");
        }
        System.out.printf("  . Loading Car Data   ................................ [ OK ] %d found%n", count);
    }

    static void loadTracks(String filename) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                char[] name = parts[1].trim().toCharArray();
                char[] type = parts[2].trim().toCharArray();
                int boost = Integer.parseInt(parts[3].trim());


                Track track = new Track(id, name, type, boost);
                trackList.insertEnd(track);
                count++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Hata: " + filename + " okunamadi.");
        }
        System.out.printf("  . Loading Track Data ................................ [ OK ] %d found%n", count);
    }

    static void printCarList() {
        System.out.println("[2] REGISTERED CARS (Ranked by Performance Score)");
        System.out.println("  +-----+--------------+-------+----------+");
        System.out.println("  | ID  | NAME         | SCORE | TYPE     |");
        System.out.println("  +-----+--------------+-------+----------+");

        sllNode current = carList.getHead();
        while (current != null) {
            Car car = (Car) current.getData();
            System.out.printf("  | %-3d | %-12s | %-5d | %-8s |%n",
                    car.getId(),
                    car.getNameAsString(),
                    car.getPerformanceScore(),
                    car.getTypeAsString());
            current = current.getLink();
        }
        System.out.println("  +-----+--------------+-------+----------+");
    }

    static void printTrackList() {
        System.out.println("[3] CURRENT TRACK LIST");
        System.out.print("  ");
        sllNode current = trackList.getHead();
        while (current != null) {
            Track track = (Track) current.getData();
            System.out.print(track.getNameAsString());
            if (current.getLink() != null) System.out.print(" -> ");
            current = current.getLink();
        }
        System.out.println(" -> [END]");
    }

    static int getMatchupBonus(char[] typeA , char[] typeB){
        String a = new String(typeA);
        String b = new String(typeB);

        if (a.equals("Electric") && b.equals("Water"))
            return 15;
        if (a.equals("Water")    && b.equals("Fire"))
            return 15;
        if (a.equals("Fire")     && b.equals("Earth"))
            return 15;
        if (a.equals("Earth")    && b.equals("Electric"))
            return 15;
        if (a.equals("Air")      && b.equals("Earth"))
            return 10;
        if (a.equals("Heavy")    && b.equals("Air"))
            return 10;

        return 0;

    }

    static int calculateInitialScore(Car car, Track track, Car opponent){
        int score = car.getPerformanceScore();

        if (new String(car.getType()).equals(new String(track.getType()))) {
            score += track.getBoost();
        }
        score += getMatchupBonus(car.getType(), opponent.getType());

        return score;
    }

    static Car computerSelectBest(Car playerCar, Track selectedTrack){
        sllNode current = carList.getHead();
        Car bestCar = null;
        int bestScore = -1;

        while(current != null){
            Car candidate = (Car) current.getData();

            if(candidate.getId() != playerCar.getId() && candidate.isActive()){
                int score = calculateInitialScore(candidate, selectedTrack, playerCar);

                if(score > bestScore){
                    bestScore = score;
                    bestCar = candidate;
                }
            }
            current = current.getLink();
        }
        return bestCar;
    }
}