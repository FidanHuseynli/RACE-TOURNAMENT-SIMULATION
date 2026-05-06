import java.util.Random;

public class Race{
    private Car car1;
    private Car car2;
    private Track track;
    private DoubleLinkedList raceTrack;
    private Random rnd;
    
    public Race(Car car1, Car car2, Track track){
        this.car1 = car1;
        this.car2 = car2;
        this.track = track;
        this.raceTrack = new DoubleLinkedList();
        this.rnd = new Random(); 

        car1.reset4Race();
        car2.reset4Race();
        car1.setCurrentScore(calculateInitialScore(car1, car2));
        car2.setCurrentScore(calculateInitialScore(car2, car1));

        raceTrack.buildTrack();
    }

    private int calculateInitialScore(Car car, Car opponent) {
        int score = car.getPerformanceScore();

        if (new String(car.getType()).equals(new String(track.getType()))) {
            score += track.getBoost();
        }
        score += getMatchupBonus(car.getType(), opponent.getType());

        return score;
    }

    private int getMatchupBonus(char[] typeA, char[] typeB) {
        String a = new String(typeA);
        String b = new String(typeB);

        if (a.equals("Electric") && b.equals("Water"))    return 15;
        if (a.equals("Water")    && b.equals("Fire"))     return 15;
        if (a.equals("Fire")     && b.equals("Earth"))    return 15;
        if (a.equals("Earth")    && b.equals("Electric")) return 15;
        if (a.equals("Air")      && b.equals("Earth"))    return 10;
        if (a.equals("Heavy")    && b.equals("Air"))      return 10;

        return 0;
    }

    public Car run() {
        System.out.println("\n[RACE PROGRESS]");
        System.out.println("+------+--------------------------------+--------------------------------+");
        System.out.printf("| %-4s | %-30s | %-30s |%n",
                "Step", car1.getNameAsString(), car2.getNameAsString());
        System.out.println("+------+--------------------------------+--------------------------------+");

        Car winner = null;
        int iteration = 0;

        while (winner == null) {
            iteration++;

            int[] car1Result = calculateMove(car1);
            int[] car2Result = calculateMove(car2);

            System.out.printf("| %02d   | ", iteration);
            printMove(car1Result, 30);
            System.out.print(" | ");
            printMove(car2Result, 30);
            System.out.println(" |");

            if (car1.getCurrentPosition() >= 50 && car2.getCurrentPosition() >= 50) {
                winner = car1.getIterationCount() <= car2.getIterationCount() ? car1 : car2;
            } else if (car1.getCurrentPosition() >= 50) {
                winner = car1;
            } else if (car2.getCurrentPosition() >= 50) {
                winner = car2;
            } else if (car1.getCurrentScore() <= 0) {
                winner = car2;
            } else if (car2.getCurrentScore() <= 0) {
                winner = car1;
            }
        }

        System.out.println("+------+--------------------------------+--------------------------------+");
        System.out.printf("%nResult: %s wins in %d steps.%n",
                winner.getNameAsString(), iteration);

        return winner;
    }

    private int[] calculateMove(Car car) {
        int startPos = car.getCurrentPosition();
        int steps = rnd.nextInt(3) + 1;
        int newPos = startPos + steps;
        if (newPos > 50) newPos = 50;

        car.setCurrentScore(car.getCurrentScore() - (steps * 5));

        TrackUnit unit = (TrackUnit) raceTrack.getNode(newPos).getData();
        int effectType = 0;
        int finalPos = newPos;
        int teleportCount = 0;

        if (new String(unit.getEffect()).equals("TELEPORT")) {
            effectType = 1;
            finalPos = newPos + unit.getTeleportValue();
            if (finalPos < 1) finalPos = 1;
            if (finalPos > 50) finalPos = 50;
            car.incrementTeleportCount();
            teleportCount = car.getTeleportCount();
            car.setCurrentScore(car.getCurrentScore() - 5);

        } else if (new String(unit.getEffect()).equals("RESET")) {
            effectType = 2;
            finalPos = 1;
            car.setCurrentScore(car.getCurrentScore() - 5);
        }

        car.setCurrentPosition(finalPos);
        car.incrementIteration();

        return new int[]{startPos, newPos, finalPos, effectType, teleportCount};
    }

    private void printMove(int[] result, int columnWidth) {
        int startPos      = result[0];
        int newPos        = result[1];
        int finalPos      = result[2];
        int effectType    = result[3];
        int teleportCount = result[4];

        int printed = 0;

        System.out.printf("Pos %02d > %02d", startPos, newPos);
        printed += 12;

        if (effectType == 1) {
            System.out.printf(" (Boost %d) > %02d", teleportCount, finalPos);
            printed += 15; 
        } else if (effectType == 2) {
            System.out.print(" (Trap) > 01");
            printed += 12;
        }

        if (finalPos >= 50) {
            System.out.print(" (Winner)");
            printed += 9;
        }

        for (int i = printed; i < columnWidth; i++) {
            System.out.print(" ");
        }
    }

    public Car getCar1() { 
        return car1;
        }

    public Car getCar2() { 
        return car2;
        }

    public Track getTrack() { 
        return track;
        }


}