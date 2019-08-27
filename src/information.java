/*
Cosimo Gonnelli

This file is the abstract base class information. The user will have to create a derived
class of type website, Linux tool or syntax map from it.
*/

// experimenting the use of abstract class in java.
abstract class information {
    private String name;
    private String type;
    private String topicName;

    // default constructor
    public information(){
        this.name = null;
        this.type = null;
        this.topicName = null;
    }

    // constructor with arguments
    public information(String name, String type, String topicName){
        this.name = name;
        this.type = type;
        this.topicName = topicName;
    }

    // this will function as a copy constructor
    public information(information toAdd){
        this.name = toAdd.name;
        this.type = toAdd.type;
        this.topicName = toAdd.topicName;
    }

    // compare information names
    protected int compare(information toCompare){
        return name.compareTo(toCompare.name);
    }

    // compare information names
    protected int compare(String toCompare){
        return name.compareTo(toCompare);
    }

    // compare information type
    protected int compareType(String toCompare){
        return type.compareTo(toCompare);
    }

    //abstract method for dynamic binding. It require the use of @Override on the hierarchy
    //It can't have a body like for pure virtual f. in C++
    public abstract int display();
}
