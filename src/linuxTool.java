/*
Cosimo Gonnelli

This is the file of the information derived class named Linux tool
*/

class linuxTool extends information {
    protected String description;
    protected String command;

    // default constructor
    public linuxTool(){
        super();
        this.description = null;
        this.command = null;
    }

    // constructor with args to copy from a file
    public linuxTool(String name, String type, String topicName, String description, String command){
        super(name, type, topicName);
        this.description = description;
        this.command = command;
    }

    // copy constructor
    public linuxTool(information toAdd){
        super(toAdd);
    }

    // copy constructor
    public linuxTool(linuxTool toAdd){
        super(toAdd);
        this.description = toAdd.description;
        this.command = toAdd.command;
    }

    //this is how we override functions in java. Used for dynamic binding
    @Override
    public int display() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + type);
        System.out.println("Topic name: " + topicName);
        System.out.println("Description: " + description);
        System.out.println("Command: " + command);
        System.out.println();
        return 1;
    }
}
