public class Track {
    private int id;
    private char[] name;
    private char[] type;
    private int boost;

    public Track(int id, char[] name, char[] type, int boost){
        this.id = id;
        this.name = name;
        this.type = type;
        this.boost = boost;
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

    public char[] getType() {
        return type;
    }
    public String getTypeAsString() {
        return new String(type);
    }

    public int getBoost() {
        return boost;
    }
}
