public class Car {
    private int id;
    private char[] name;
    private int performanceScore;
    private int currentScore;
    private char[] type;
    private boolean isActive;
    private int currentPosition;
    private int iterationCount;

    public Car(int id, char[] name, int performanceScore, char[] type){
        this.id = id;
        this.name = name;
        this.performanceScore = performanceScore;
        this.currentScore = currentScore;
        this.type = type;
        this.isActive = true;
        this.currentPosition = 1;
        this.iterationCount = 0;
    }

    public int getId() {
        return id;
    }

    public char[] getName() {
        return name;
    }
    public String getNameAsString() {
        return new String(name);
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public char[] getType() {
        return type;
    }
    public String getTypeAsString() {
        return new String(type);
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getIterationCount() {
        return iterationCount;
    }
    public void incrementIteration() {
        this.iterationCount++;
    }

    public void resetForRace() {
        this.currentScore = this.performanceScore;
        this.currentPosition = 1;
        this.iterationCount = 0;
    }
}
