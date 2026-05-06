import java.util.Random;

public class DoubleLinkedList {
    private dllNode head;
    private dllNode tail;

    public DoubleLinkedList(){
        head = null;
        tail = null;
    }

    public void add(Object data) {
        dllNode newNode;
        if (head == null) {
            newNode = new dllNode(data);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new dllNode(data);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    public void buildTrack(){
        for(int i = 0; i < 50; i++){
            TrackUnit unit = new TrackUnit(i);
            add(unit);
        }
        assignSpecialUnits();
    }

    public dllNode getNode(int position){
        dllNode temp = head;
        while(temp != null){
            TrackUnit unit = (TrackUnit) temp.getData();
            if (unit.getPosition() == position) return temp;
            temp = temp.getNext();
        }
        return null;
    }

    private void assignSpecialUnits(){
        Random rnd = new Random();

        int resetPos = rnd.nextInt(48)+2;
        TrackUnit resetUnit = (TrackUnit) getNode(resetPos).getData();
        resetUnit.setEffect("RESET".toCharArray());

        int teleportCount = 0;
        while(teleportCount < 10) {
            int pos = rnd.nextInt(48) + 2;


            dllNode node = getNode(pos);
            TrackUnit unit = (TrackUnit) node.getData();
            if (unit.getEffectAsString().equals("NORMAL")) {
                unit.setEffect("TELEPORT".toCharArray());
                int value = rnd.nextInt(5) + 1;
                if (rnd.nextBoolean()) value = -value;

                if (pos + value < 1) value = 1 - pos;
                if (pos + value > 50) value = 50 - pos;
                unit.setTeleportValue(value);
                teleportCount++;
            }
        }



    }

    public dllNode getHead() { return head; }
    public dllNode getTail() { return tail; }

}
