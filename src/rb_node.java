/*
Cosimo Gonnelli

This file is the node class for the RB tree. It has setters, getters and helper methods.
*/

public class rb_node {
    protected rb_node left;
    protected rb_node right;
    protected rb_node parent;
    protected topic myTopic; // topic obj.
    protected char color;

    /* constructor to use without sentinel node
    rb_node(){
        this.left = null;
        this.right = null;
        this.parent = null;
        this.myTopic = null;
        this.color = 'R'; //every node set to red by default
    }
    */

    public void setColor(char color) {
        this.color = color;
    }

    public void setLeft(rb_node left) {
        this.left = left;
    }

    public void setRight(rb_node right) {
        this.right = right;
    }

    public void setParent(rb_node parent) {
        this.parent = parent;
    }

    public rb_node goLeft() {
        return this.left;
    }

    public rb_node goRight() {
        return this.right;
    }

    public rb_node getParent() {
        return this.parent;
    }
    public char getColor(){
        return this.color;
    }

    //compare topic to go left or right return an integer
    public int compare(rb_node toCompare){
        return myTopic.compareTopic(toCompare.myTopic);
    }

    // display topics
    public void display(){
            myTopic.display();
        }
}
