/*
Cosimo Gonnelli

This is the the CLL list where each node has two arrays of information.
When the two arrays are full a new node gets created.
The insertion in the array doesn't follow any particular order.
*/

public class CLL {
    protected c_node rear;

    // default constructor
    public CLL(){
        rear = null;
    }

    // add information to the CLL
    protected int addInfo(syntaxMap toAdd) {
        if (rear == null) {// add at empty list
            rear = new c_node(10);
            rear.addInfo(toAdd);
            return 1;
        }
        if(rear.goNext().addInfo(toAdd) == 0) {// add at beginning when the arrays are full
            c_node temp = rear.goNext(); // hold on the front
            rear.setNext(new c_node(5)); // make the node
            rear.goNext().setNext(temp); // reconnect
            rear.goNext().addInfo(toAdd); // add tot he next node
            return 1;
        }
        return 0;
    }

    // wrapper for display
    public int displayCLL(){
        if(this.rear == null)
            return 0;
        return displayCLL(this.rear.goNext()) +1;
    }

    // recursive method for display starting from rear.next
    protected int displayCLL(c_node current){
        if(rear == null)
            return 0;

        if(current == this.rear){
            current.display();
            return 1;
        }
        current.display();
        return displayCLL(current.goNext());
    }
}
