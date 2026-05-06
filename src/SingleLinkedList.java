import org.w3c.dom.Node;

public class SingleLinkedList {
    sllNode head;

    public void insertSorted(Car car) {
        sllNode newNode = new sllNode(car);
        if (head == null) {
            head = newNode;
        } else if (car.getPerformanceScore() <= ((Car) head.getData()).getPerformanceScore()) {
            newNode.setLink(head);
            head = newNode;
        } else {
            sllNode temp = head;
            sllNode previous = null;
            while (temp != null && ((Car) temp.getData()).getPerformanceScore() < car.getPerformanceScore()) {
                previous = temp;
                temp = temp.getLink();
            }
            newNode.setLink(temp);
            previous.setLink(newNode);
        }
    }

    public void insertEnd(Object data) {
        sllNode newNode = new sllNode(data);
        if (head == null) {
            head = newNode;
        } else {
            sllNode temp = head;
            while (temp.getLink() != null) {
                temp = temp.getLink(); // addToEnd'de bug var, temp.getLink() yazılmış ama temp'e atanmamış
            }
            temp.setLink(newNode);
        }
    }

    public int size(){
        if (head == null)
            return 0;
        else{
            int count = 0;
            sllNode temp = head;
            while(temp != null){
                temp = temp.getLink();
                count++;
            }

            return count;
        }
    }

    public void display(){
        if(head == null)
            System.out.println("Single linked list is empty.");
        else{
            sllNode temp = head;
            while(temp!=null){
                System.out.print(temp.getData() + " ");
                temp = temp.getLink();
            }
        }
    }

    public void delete(Object dataToDelete){
        if(head == null)
            System.out.println("Single linked list is empty.");
        else{
            while((Integer)head.getData() == (Integer)dataToDelete){
                head = head.getLink();

                sllNode temp =  head;
                sllNode previous = null;
                while(temp!=null){
                    if((Integer)temp.getData() == (Integer)dataToDelete){
                        previous.setLink(temp.getLink());
                        temp = previous;
                    }
                    previous = temp;
                    temp = temp.getLink();
                }

            }
        }
    }

    public sllNode getHead() {
        return head;
    }


}
