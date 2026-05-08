import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static SingleLinkedList carList = new SingleLinkedList();
    static SingleLinkedList trackList = new SingleLinkedList();
    static SingleLinkedList raceLog = new SingleLinkedList();

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

        Track selectedTrack = playerSelectTrack();
        Car playerCar = playerSelectCar();
        Car computerCar = computerSelectBest(playerCar, selectedTrack);

        System.out.printf("> Computer Car    : [%d] %s (%s vs %s: +%d)%n",
            computerCar.getId(),
            computerCar.getNameAsString(),
            computerCar.getTypeAsString(),
            playerCar.getTypeAsString(),
            getMatchupBonus(computerCar.getType(), playerCar.getType()));

        Race race1a = new Race(playerCar, computerCar, selectedTrack);
        Car winner1a = race1a.run();

        RaceLog entry1a = new RaceLog("1a".toCharArray(),
            selectedTrack.getName(),
            playerCar.getName(),
            computerCar.getName(),
            winner1a.getName(),
            race1a.getTotalIterations()
        );
        entry1a.setStepLog1(race1a.getStepLog1());
        entry1a.setStepLog2(race1a.getStepLog2());
        raceLog.insertEnd(entry1a);

        trackList.deleteTrack(selectedTrack.getId());
        System.out.printf("Status: [%d] %s is no longer available.%n",
        selectedTrack.getId(),
        selectedTrack.getNameAsString());
        printTrackList();

        SingleLinkedList remainingCars = new SingleLinkedList();
        sllNode current = carList.getHead();
        while(current != null){
            Car car = (Car) current.getData();
            if( car.getId() != playerCar.getId() && car.getId() != computerCar.getId()){
                remainingCars.insertEnd(car);
            }
            current = current.getLink();
        }

        Random rnd = new Random();
        int size = remainingCars.size();
        int pick1 = rnd.nextInt(size);
        int pick2;

        do{
            pick2 = rnd.nextInt(size);
        } while(pick2 == pick1);

        Car compA = null;
        Car compB = null; 
        sllNode temp = remainingCars.getHead();
        int index = 0;
        
        while(temp != null){
            Car car = (Car) temp.getData();
            if(index == pick1)
                compA = car;
            else if(index == pick2)
                compB = car;
            else 
                car.setActive(false);
            index++;
            temp = temp.getLink();  
        }

        int trackSize = trackList.size();
        int trackPick = rnd.nextInt(trackSize);
        sllNode tempT = trackList.getHead();
        int indexT = 0;
        Track race1bT = null;

        while(tempT != null){
            if(indexT == trackPick){
                race1bT = (Track) tempT.getData();
                break;
            }
            indexT++;
            tempT = tempT.getLink();
        }

        System.out.println("\n-- Race 1b: Computer vs Computer --");
        System.out.println("[PRE-RACE SELECTION]");
        System.out.println("> Track Selection : Random Assignment");
        System.out.printf("> Selected Track  : [%d] %s (%s | Boost: +%d)%n",
        race1bT.getId(),
        race1bT.getNameAsString(),
        race1bT.getTypeAsString(),
        race1bT.getBoost());
        System.out.printf("> Computer Car A  : [%d] %s (%s)%n",
        compA.getId(),
        compA.getNameAsString(),
        compA.getTypeAsString());
        System.out.printf("> Computer Car B  : [%d] %s (%s)%n",
        compB.getId(),
        compB.getNameAsString(),
        compB.getTypeAsString());

        Race race1b = new Race(compA, compB, race1bT);
        Car winner1b = race1b.run();

     
        

    

        trackList.deleteTrack(race1bT.getId());
        System.out.printf("Status: [%d] %s is no longer available.%n",
        race1bT.getId(),
        race1bT.getNameAsString());

        RaceLog entry1b = new RaceLog("1b".toCharArray(),
            race1bT.getName(),
            compA.getName(),
            compB.getName(),
            winner1b.getName(),
            race1b.getTotalIterations()
        );

        entry1b.setStepLog1(race1b.getStepLog1());
        entry1b.setStepLog2(race1b.getStepLog2());
        raceLog.insertEnd(entry1b);

        printTrackList();

        Track finalT;
        System.out.println("\n======================================================================");
        System.out.println(" FINAL RACE");
        System.out.println("======================================================================");

        if (winner1a == playerCar) {
        finalT = playerSelectTrack();
        } else {

        int finalTrackSize = trackList.size();
        int finalTrackPick = new Random().nextInt(finalTrackSize);
        sllNode finalTemp = trackList.getHead();
        int finalIndex = 0;
        finalT = null;
        while (finalTemp != null) {
            if (finalIndex == finalTrackPick) {
            finalT = (Track) finalTemp.getData();
            break;
            }
            finalIndex++;
            finalTemp = finalTemp.getLink();
        }
        System.out.println("\n-- " + winner1a.getNameAsString() + " vs " + winner1b.getNameAsString() + " --");
        System.out.println("[PRE-RACE SELECTION]");
        System.out.println("> Track Selection : Random Assignment");
        System.out.printf("> Selected Track  : [%d] %s (%s | Boost: +%d)%n",
        finalT.getId(),
        finalT.getNameAsString(),
        finalT.getTypeAsString(),
        finalT.getBoost());
}

        Race finalRace = new Race(winner1a, winner1b, finalT);
        Car champion = finalRace.run();

        RaceLog entryFinal = new RaceLog("Final".toCharArray(),
            finalT.getName(),
            winner1a.getName(),
            winner1b.getName(),
            champion.getName(),
            finalRace.getTotalIterations()
        );
        entryFinal.setStepLog1(finalRace.getStepLog1());
        entryFinal.setStepLog2(finalRace.getStepLog2());
        raceLog.insertEnd(entryFinal);

        trackList.deleteTrack(finalT.getId());
        System.out.printf("Status: [%d] %s is no longer available.%n",
        finalT.getId(),
        finalT.getNameAsString());
        printTrackList();

        System.out.println("\n======================================================================");
        System.out.printf(" TOURNAMENT CHAMPION: %s%n", champion.getNameAsString());
        System.out.println("======================================================================");
        printRaceLog();
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

    static Track playerSelectTrack(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n-- Race 1a: Player vs Computer --");
        System.out.println("[Pre-Race SELECTION]");
        System.out.println("> Track Selections:  Priority by selected Player");
        System.out.println();

        sllNode current = trackList.getHead();
        while(current != null){
            Track track = (Track) current.getData();
                System.out.printf("  [%d] %s (Type: %s, Boost: %d)%n",
                        track.getId(),
                        track.getNameAsString(),
                        track.getTypeAsString(),
                        track.getBoost());
                current = current.getLink();
        }

        System.out.print("Select a track by ID: ");
        int trackId = scanner.nextInt();
        current = trackList.getHead();
        while(current != null){
            Track track = (Track) current.getData();
            if(track.getId() == trackId){
                System.out.printf("> Selected Track  : [%d] %s (%s | Boost: +%d)%n",
                    track.getId(),
                    track.getNameAsString(),
                    track.getTypeAsString(),
                    track.getBoost());
            return track;
            }
            current = current.getLink();
        }

        System.out.println("Invalid track ID. Please try again.");
        return playerSelectTrack();
                              
    }

    static Car playerSelectCar(){
        Scanner scanner = new Scanner(System.in);
        sllNode current = carList.getHead();
        while(current!= null){
            Car car = (Car) current.getData();
            if(car.isActive()){
                System.out.printf("  [%d] %s (Score: %d, Type: %s)%n",
                        car.getId(),
                        car.getNameAsString(),
                        car.getPerformanceScore(),
                        car.getTypeAsString());
            }
            current = current.getLink();
        }

        System.out.print("Select a car by ID: ");
        int carId = scanner.nextInt();
        current = carList.getHead();
        while(current != null){
            Car car = (Car) current.getData();
            if(car.getId() == carId && car.isActive()){
                System.out.printf("> Selected Car    : [%d] %s (Score: %d   | Type: %s)%n",
                        car.getId(),
                        car.getNameAsString(),
                        car.getPerformanceScore(),
                        car.getTypeAsString());
                return car;     
            }
            current = current.getLink();
        }

        System.out.println("Invalid car ID or car is inactive. Please try again.");
        return (Car) carList.getHead().getData();
        
    }

    static void printRaceLog(){
        System.out.println("\n======================================================================");
        System.out.println(" RACE LOG");
        System.out.println("======================================================================");

        sllNode current = raceLog.getHead();
        int entryNumber = 1;

       


        while(current != null){
            RaceLog  entry = (RaceLog) current.getData();

            boolean car1IsWinner = new String(entry.getWinnerName()).equals(new String(entry.getCar1Name()));

             System.out.printf("%n[ ENTRY %d ] Race %s: %s%n", entryNumber,
                new String(entry.getRaceId()),
                new String(entry.getRaceId()).equals("Final") ? "Final Race" : 
                new String(entry.getRaceId()).equals("1a") ? "Player vs Computer" : "Computer vs Computer");

            System.out.printf("            Track: %s%n", new String(entry.getTrackName()));
            System.out.printf("            Matchup: %s vs %s%n",
                new String(entry.getCar1Name()),
                new String(entry.getCar2Name()));
            System.out.printf("            Winner: %s%n", new String(entry.getWinnerName()));

            System.out.println("    |");
            System.out.println("    V");

            System.out.printf("%-4s | %-30s | %-30s%n", "Step",
                new String(entry.getCar1Name()),
                new String(entry.getCar2Name()));
            System.out.println("-----+--------------------------------+--------------------------------");
    
            sllNode step1 = entry.getStepLog1().getHead();
            sllNode step2 = entry.getStepLog2().getHead();
            int stepNumber = 1;
            int total = entry.getTotalSteps();
            while (step1 != null || step2 != null) {
            boolean skip = stepNumber > 3 && stepNumber < total - 1;

            if (skip) {
                if (stepNumber == 4) {
                    System.out.printf(" ... | [Steps 04-%02d skipped]%n", total - 2);
                }
                step1 = step1 != null ? step1.getLink() : null;
                step2 = step2 != null ? step2.getLink() : null;
                stepNumber++;
                continue;
            }

            System.out.printf(" %02d  | ", stepNumber);

            if (step1 != null) {
                int[] s1data = (int[]) step1.getData();
                printLogMove(s1data, s1data[2] >= 50);
            } else {
            System.out.printf("%-30s", "");
            }

            System.out.print(" | ");

           if (step2 != null) {
                int[] s2data = (int[]) step2.getData();
                boolean isFinish = s2data[2] >= 50 || (stepNumber == total && !car1IsWinner);
                printLogMove(s2data, isFinish);
            } else {
                System.out.printf("%-30s", "");
}

            System.out.println();


            step1 = step1 != null ? step1.getLink() : null;
            step2 = step2 != null ? step2.getLink() : null;
            stepNumber++;
        }
            System.out.println("-----+--------------------------------+--------------------------------");
            System.out.println("=".repeat(70));

            entryNumber++;
            current = current.getLink();

        }
      
    }

    static void printLogMove(int[] result, boolean isWinner) {
        int startPos   = result[0];
        int newPos     = result[1];
        int finalPos   = result[2];
        int effectType = result[3];
        int teleportCount = result[4];

        System.out.printf("Pos %02d -> %02d", startPos, newPos);

        if (effectType == 1) {
            System.out.printf(" (Boost %d) -> %02d", teleportCount, finalPos);
        } else if (effectType == 2) {
            System.out.print(" (Reset Trap) -> 01");
        }

        if (isWinner || finalPos >= 50) {
            System.out.print(" (Finish)");
        }
    }


}