public class TrackUnit {
    private int position;
    private char[] effect; //normal mi, teleport mu, reset mi
    private int teleportValue; //+ileri, -geri, 0normal

    public TrackUnit(int position){
        this.position = position;
        this.effect = "NORMAL".toCharArray();
        this.teleportValue = 0;
    }


    public int getPosition() {
        return position;
    }

    public char[] getEffect() {
        return effect;
    }
    public String getEffectAsString() {
        return new String(effect);
    }
    public void setEffect(char[] effect) {
        this.effect = effect;
    }

    public int getTeleportValue() {
        return teleportValue;
    }
    public void setTeleportValue(int teleportValue) {
        this.teleportValue = teleportValue;
    }
}
