/*
Cosimo Gonnelli

This file is the head of a LLL of information organized by the same topic name.
"It has a head pointer of type l_node" to create a LLL of same topic information.
*/

public class topic {
    //protected Scanner input = new Scanner(System.in);
    protected l_node head;
    protected String topicName; //e.g dynamic binding, operator overloading, initialization list

    // default constructor
    public topic() {
        this.head = null;
        this.topicName = null;
    }

    // constructor with arguments
    public topic(String topicName) {
        this.topicName = topicName;
    }

    // ------------------------------DELETE-----------------------------------
    // this function was required for the assignment but we don't really need
    // it since java have a garbage collector.
    // delete the LLL go all the way down to the end and set pointer to null
    // this will let, later on, the garbage collector to delete the memory
    // wrapper for deletion LLL
    public int destroyLLL() {
        if (this.head == null)
            return 0;

        this.head = destroyLLL(head);
        return 1;
    }

    protected l_node destroyLLL(l_node head) {
        if (head == null)
            return null;

        destroyLLL(head.goNext());
        head = null;
        return head;
    }

    // wrapper for adding an new information
    public int insertInfo(information toAdd) {
        this.head = insertInfo(head, toAdd);
        return 1;
    }

    // add new information
    protected l_node insertInfo(l_node head, information toAdd) {
        if (head == null) {// empty list
            head = new l_node(); // head = new node;
            head.myInformation = toAdd; // head -> data = toAdd
        }
        else if (head.myInformation.compare(toAdd) < 0) {// insert at the beginning
            head.setNext(insertInfo(head.goNext(), toAdd));
        }
        return head;
    }

    // wrapper method for deletion a specific information
    public int lDelete(String infoName){
        return lDelete(this.head, infoName);
    }
    // Method to delete a specific information
    protected int lDelete(l_node head, String infoName){
        if(head == null)
            return 0;

        l_node curr = head;
        l_node prev = null;

        if(curr.myInformation.compare(infoName) == 0) {
            this.head = curr.goNext();
            return 1;
        }

        while (curr != null && curr.myInformation.compare(infoName) != 0) {
            prev = curr;
            curr = curr.goNext();
        }

        if(curr != null) {
            prev.setNext(curr.goNext());
            return 1;
        }
        return -1;
    }

    /*
    DELETE RECURSIVE
    // wrapper method for deletion a specific information
    public l_node lDelete(String infoName){
        this.head = lDelete(this.head, infoName);
        return this.head;
    }

    //method to delete an information
    public l_node lDelete(l_node head, String infoName){
        if(head == null)
            return null;

        else if(head.myInformation.compare(infoName) == 0){
            head = head.goNext();
            return head;
        }
        else {
            head.setNext((lDelete(head.goNext(), infoName)));
            return head;
        }
    }
    */

    // wrapper for display
    public int display(){
        System.out.println("-------------------------");
        System.out.println("Topic name: " + topicName);
        return display(this.head);
    }

    // display all LLL
    protected int display(l_node head){
        if (head == null)
            return 0;

        head.display();
        return display(head.goNext()) + 1;
    }

    // compare topic name to topic name
    protected int compareTopic(topic toCompare){
        return topicName.compareTo(toCompare.topicName);
    }

    // compare topic name to topic name
    protected int compareTopic(String topicN){
        return this.topicName.compareTo(topicN);
    }

    // compare topic name to information name
    protected int compareTopicName(information toCompare){
        return topicName.compareTo(toCompare.topicName);
    }
}
