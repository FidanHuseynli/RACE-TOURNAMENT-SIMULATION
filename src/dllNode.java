public class dllNode {
    private Object data;
    private dllNode next;
    private dllNode prev;

    public dllNode(Object data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public dllNode getNext() { return next; }
    public void setNext(dllNode next) { this.next = next; }

    public dllNode getPrev() { return prev; }
    public void setPrev(dllNode prev) { this.prev = prev; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}