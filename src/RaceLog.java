public class RaceLog {

    private char[] raceId;        // 1a, 1b, Final  
    private char[] trackName;
    private char[] car1Name;
    private char[] car2Name;
    private char[] winnerName;
    private int totalSteps;
    private SingleLinkedList stepLog; 
    private SingleLinkedList stepLog1;
    private SingleLinkedList stepLog2;

    public RaceLog(char[] raceId, char[] trackName, char[] car1Name, char[] car2Name, char[] winnerName, int totalSteps) {
        this.raceId = raceId;
        this.trackName = trackName;
        this.car1Name = car1Name;
        this.car2Name = car2Name;
        this.winnerName = winnerName;
        this.totalSteps = totalSteps;
        this.stepLog = new SingleLinkedList();
    }

    public char[] getRaceId() { 
        return raceId; 
    }
    public char[] getTrackName() { 
        return trackName; 
    }
    public char[] getCar1Name() { 
        return car1Name; 
    }
    public char[] getCar2Name() { 
        return car2Name; 
    }
    public char[] getWinnerName() { 
        return winnerName; 
    }
    public int getTotalSteps() { 
        return totalSteps; 
    }
    public SingleLinkedList getStepLog() { 
        return stepLog; 
    }
    public SingleLinkedList getStepLog1() { 
    return stepLog1; 
    }
    public SingleLinkedList getStepLog2() { 
    return stepLog2; 
    }
    public void setStepLog1(SingleLinkedList log) { 
        this.stepLog1 = log; 
    }
    public void setStepLog2(SingleLinkedList log) { 
        this.stepLog2 = log; 
    }
    
    
}
