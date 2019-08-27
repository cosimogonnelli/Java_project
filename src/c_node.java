/*
Cosimo Gonnelli

This is the CLL node that holds two arrays of information.
*/

public class c_node {

    private c_node next;
    private information[] array1;
    private information[] array2;
    private int index;

    // default constructor
    public c_node() {
        this.next = null;
        this.array1 = null;
        this.array2 = null;
        this.index = 0;
    }

    // constructor with arguments
    public c_node(int size){
        this.array1 = new information[size]; //create an array of 10 info.
        this.array2 = new information[size]; //create an array of 10 info.
        this.setNext(this);
        this.index = 0;
    }

    // getter
    public c_node goNext() {
        return next;
    }

    // setter
    public void setNext(c_node next) {
        this.next = next;
    }

    // add to the first or second array
    protected int addInfo(syntaxMap toAdd){
        if(index == 10){
            return 0;
        }
        else if(index < 5) {
            this.array1[index] = toAdd;
        }
        else{
            this.array2[index - 5] = toAdd;
        }
        ++index;
        return 1;
    }

    // display arrays values
    protected void display(){
        for(int i = 0; i < 5; ++i) {// display first
            if(this.array1[i] != null)
                this.array1[i].display();
        }
        for(int i = 0; i < 5; ++i ) {// display second
            if(this.array2[i] != null)
                this.array2[i].display();
        }
    }
}
