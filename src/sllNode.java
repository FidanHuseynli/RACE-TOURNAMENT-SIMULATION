public class sllNode {
    private Object data;
    private sllNode link;

    public sllNode(Object dataToAdd){
        data = dataToAdd;
        link = null;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public sllNode getLink(){
        return link;
    }

    public void setLink(sllNode link){
        this.link = link;
    }
}
