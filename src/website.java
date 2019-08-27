/*
Cosimo Gonnelli

This is the file of the information derived class named website.
*/

public class website extends information {
    protected String address;
    protected String evaluation; // not useful, useful, very useful

    // default constructor
    public website(){
        super();
        this.address = null;
        this.evaluation = null;
    }

    // constructor with args to copy from a file
    public website(String name, String type, String topicName, String address, String evaluation){
        super(name, type, topicName);
        this.address = address;
        this.evaluation = evaluation;
    }

    // copy constructor
    public website(information toAdd) {
        super(toAdd);
    }

    // copy constructor
    public website(website toAdd){
        super(toAdd);
        this.address = toAdd.address;
        this.evaluation = toAdd.evaluation;
    }

    // this is how we override functions in java. Used for dynamic binding
    @Override
    public int display() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + type);
        System.out.println("Topic name: " + topicName);
        System.out.println("Address: " + address);
        System.out.println("Evaluation: " + evaluation);
        System.out.println();
        return 1;
    }

    // getter
    public String getAddress() {//set parent
        return this.address;
    }

}
