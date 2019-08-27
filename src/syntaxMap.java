/*
Cosimo Gonnelli

This is the file of the information derived class named syntax map.
The information of this class will be used to create a CLL of syntax information.
*/

class syntaxMap extends information{
    protected String cPlusPlus;
    protected String java;

    // default constructor
    public syntaxMap(){
        super();
        this.cPlusPlus = null;
        this.java = null;
    }

    // constructor with args to copy from a file
    public syntaxMap(String name, String type, String topicName, String cPlusPlus, String java){
        super(name, type, topicName);
        this.cPlusPlus = cPlusPlus;
        this.java = java;
    }

    // copy constructor
    public syntaxMap(information toAdd){
        super(toAdd);
    }

    // copy constructor
    public syntaxMap(syntaxMap toAdd){
        super(toAdd);
        this.cPlusPlus = toAdd.cPlusPlus;
        this.java = toAdd.java;
    }
    // this is how we override functions in java. Used for dynamic binding
    // use @Override to make sure I am actually overriding a method
    @Override
    public int display() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + type);
        System.out.println("Topic name: " + topicName);
        System.out.println("C++ syntax: " + cPlusPlus);
        System.out.println("Java syntax: " + java);
        System.out.println();
        return 1;
    }
}

