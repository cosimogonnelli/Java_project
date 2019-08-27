/*
Cosimo Gonnelli

This file is the node class for the LLL besides setters and getters it "has a pointer
to an information".
*/

public class l_node {
    protected l_node next;
    protected information myInformation;

    // default constructor
    public l_node(){
        this.next = null;
        this.myInformation = null;
    }

    // getter
    public l_node goNext(){
        return this.next;
    }

    // setter
    public l_node setNext(l_node next) {
        return this.next = next;
    }

    // display an information
    public void display(){
        myInformation.display();
    }
}
