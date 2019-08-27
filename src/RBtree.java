/*
Cosimo Gonnelli

This is the file that create the RB tree where all the work of insertion is done.
*/

public class RBtree {
    // tNil is a sentinel node to handle easier the edge cases.
    // the tNil node is connected to every leaf and the root.
    protected rb_node tNil;
    protected rb_node root;

    /* classic constructor when sentinel node is not used
    public RBtree(){
        this.root = null;
    }
    */

    // this constructor uses the implementation of a "sentinel" pointer
    // like mentioned in "introduction to Algorithms" ed.4 ch.13
    public RBtree(){
        tNil = new rb_node();
        tNil.left = null;
        tNil.right = null;
        tNil.parent = null;
        tNil.myTopic = null;
        tNil.color = 'B';
        root = tNil;
    }

    // example of the implementation of a destructor.
    // we don't really need it since java supports garbage collector
    // wrapper for deleting the tree
    public int destroyAll(){
        if(this.root == tNil)
            return 0;

        this.root = destroyAll(root);
        return 1;
    }

    // recursive method for deleting the tree
    protected rb_node destroyAll(rb_node root){
        if(root == tNil)
            return root;

        destroyAll(root.goLeft());
        destroyAll(root.goRight());
        root = null;
        return root;
    }

    // the algorithm used is from the book "Introduction to algorithms,
    // 4th edition, ch. 13
    // 1. Every node is either red or black.
    // 2. The root is black.
    // 3. Every leaf (NIL) is black.
    // 4. If a node is red, then both its children are black.
    // 5. For each node, all simple paths from the node to descendant leaves contain the same number of black nodes.
    // iterative add method
    protected int insertTopic(topic toAdd) {
        // create a new node to add
        rb_node newNode = new rb_node();
        newNode.myTopic = toAdd;
        newNode.setLeft(tNil);
        newNode.setRight(tNil);
        newNode.setColor('R');

        rb_node myParent = tNil;
        rb_node curr = this.root;

        while (curr != tNil) {
            myParent = curr;
            if ((curr.myTopic.compareTopic(toAdd)) > 0)
                curr = curr.left;
            else
                curr = curr.right;
        }

        newNode.parent = myParent;

        if (myParent == tNil) {
            root = newNode;
            newNode.setColor('B');
        }
        else if ((myParent.myTopic.compareTopic(toAdd)) > 0) {
            myParent.setLeft(newNode);
        }
        else{
            myParent.setRight(newNode);
        }

        // if one node or not grand parent no need for fixup
        if(myParent == tNil || myParent.parent == tNil)
            return 1;

        rbInsertFixup(newNode);
        return 1;
    }

    // perform rotations and color setting to respect RBtree characteristics
    protected int rbInsertFixup(rb_node newNode) {
        rb_node uncle;
        rb_node myParent = newNode.parent;
        rb_node myGranParent = newNode.parent.parent;

        while (myParent.color == 'R') {
            // we are the LEFT child, set uncle as RIGHT node
            if (myParent == myGranParent.goLeft()) {
                uncle = myGranParent.goRight();
                // case 1: node uncle is RED
                if (uncle.color == 'R') {
                    myParent.setColor('B');
                    uncle.setColor('B');
                    myGranParent.setColor('R');
                    newNode = myGranParent;
                } else {
                    // case 2: node's uncle is BLACK and node is a RIGHT child
                    if (newNode == myParent.goRight()) {
                        newNode = myParent;
                        leftRotate(newNode);
                    }
                    // cas3 3: node's uncle is BLACK and node is a LEFT child
                    myParent.setColor('B');
                    myGranParent.setColor('R');
                    rightRotate(myGranParent);
                }
            }
            // we are the RIGHT child, set uncle as LEFT node
            else {
                uncle = myGranParent.goLeft();
                // case 1: node uncle is RED
                if (uncle.color == 'R') {
                    myParent.setColor('B');
                    uncle.setColor('B');
                    myGranParent.setColor('R');
                    newNode = myGranParent;
                } else {
                    // case 2: node's uncle is BLACK and node is a RIGHT child
                    if (newNode == myParent.goLeft()) {
                        newNode = myParent;
                        rightRotate(newNode);
                    }
                    // cas3 3: node's uncle is BLACK and node is a RIGHT child
                    myParent.setColor('B');
                    myGranParent.setColor('R');
                    leftRotate(myGranParent);
                }
            }
        }
        root.setColor('B');
        return 1;
    }

    // left rotation method
    protected int leftRotate(rb_node node){
        rb_node temp = node.goRight();
        rb_node myParent = node.parent;
        node.setRight(temp.goLeft()); // turn temp's left subtree into node's right subtree

        if(temp.goLeft() != tNil)
            temp.goLeft().parent = node;

        temp.parent = myParent; // link temp's parent to node

        if(myParent == tNil)
            this.root = temp;
        else if(node == myParent.goLeft())
            myParent.setLeft(temp);
        else
            myParent.setRight(temp);

        temp.setLeft(node);
        node.setParent(temp);
        return 1;
    }

    // right rotation method
    protected int rightRotate(rb_node node){
        rb_node temp = node.goLeft();
        rb_node myParent = node.parent;
        node.setLeft(node.goRight()); // turn temp's left subtree into node's right subtree

        if(temp.goRight() != tNil)
            temp.goRight().parent = node;

        temp.parent = myParent; // link temp's parent to node

        if(myParent == tNil)
            this.root = temp;
        else if(node == myParent.goRight())
            myParent.setRight(temp);
        else
            myParent.setLeft(temp);

        temp.setLeft(node);
        node.setParent(temp);
        return 1;
    }

    // wrapper method for finding a matching node
    public rb_node findMatch(String topicName) {
        rb_node match = findMatch(this.root, topicName);
        return match;
    }

    // recursive method to retrieve info
    protected rb_node findMatch(rb_node root, String topicName){
        rb_node curr = root;

        while(curr != tNil){
            if(curr.myTopic.compareTopic(topicName) == 0)
                return curr;

            if ((curr.myTopic.compareTopic(topicName)) > 0)
                curr = curr.goLeft();
            else
                curr = curr.goRight();
        }
        return tNil;
    }

    // wrapper method for deletion
    public int rbDelete(String topicName) {
        rb_node match = findMatch(topicName);
        if (match == tNil)
            return 0;

        rbDelete(match);
        return 1;
    }
    // the algorithm used is from the book "Introduction to algorithms,
    // 4th edition, ch. 13
    // iterative add method
    // deletion of a single node. This will delete also the information attached to it
    protected int rbDelete(rb_node toDelete){
        rb_node x;
        rb_node y = toDelete;
        char matchOriginalColor;

        matchOriginalColor = y.getColor();
        if(toDelete.goLeft() == tNil) {
           x = toDelete.goRight();
           rbTransplant(toDelete, toDelete.goRight());
        }
        else if(toDelete.goRight() == tNil) {
            x = toDelete.goLeft();
            rbTransplant(toDelete, toDelete.goLeft());
        }
        else {
            y = tMinimum(toDelete.goRight());
            matchOriginalColor = y.getColor();
            x = y.goRight();
            if(y.getParent() == toDelete) {
                x.setParent(y);
            } else{
                rbTransplant(y, y.goRight());
                y.setRight(toDelete.goRight());
                y.goRight().setParent(y);
            }
            rbTransplant(toDelete, y);
            y.setLeft(toDelete.goLeft());
            y.goLeft().setParent(y);
            y.setColor(toDelete.getColor());
        }
        if(matchOriginalColor == 'B')
            rbDeleteFixup(x);
        return 1;
    }

    protected int rbTransplant(rb_node x, rb_node y){
        if(x.parent == tNil)
            this.root = y;
        else if(x == x.parent.goLeft())
            x.parent.setLeft(y);
        else
            x.parent.setRight(y);
        y.setParent(x.parent);
        return 1;
    }

    protected int rbDeleteFixup(rb_node node){
        rb_node mySibling;
        rb_node myParent = node.parent;

        while(node != root && node.getColor() == 'B') {
            if(node == myParent.goLeft()) {
                mySibling = myParent.goRight();
                // case 1: node's sibling is RED
                if(mySibling.getColor() == 'R') {
                    mySibling.setColor('B');
                    myParent.setColor('R');
                    leftRotate(myParent);
                    mySibling = myParent.goRight();
                }
                // case 2: node's sibling is BLACK and both children are BLACK
                if(mySibling.goLeft().getColor() == 'B' && mySibling.goRight().getColor() == 'B') {
                    mySibling.setColor('R');
                    node = myParent;
                }
                else {
                    // case 3: node's sibling is BLACK and sibling's LEFT child is RED and sibling's RIGHT child is BLACK
                    if(mySibling.goRight().getColor() == 'B') {
                        mySibling.goLeft().setColor('B');
                        mySibling.setColor('R');
                        rightRotate(mySibling);
                        mySibling = myParent.goRight();
                    }
                    // case 4: node's sibling is BLACK and sibling's RIGHT child is RED
                    mySibling.setColor(myParent.getColor());
                    myParent.setColor('B');
                    mySibling.goRight().setColor('B');
                    leftRotate(myParent);
                    node = this.root;
                }
            }
            else {
                mySibling = myParent.goLeft();
                // case 1: node's sibling is RED
                if(mySibling.getColor() == 'R'){
                    mySibling.setColor('B');
                    myParent.setColor('R');
                    rightRotate(myParent);
                    mySibling = myParent.goLeft();
                }
                // case 2: node's sibling is BLACK and both children are BLACK
                if(mySibling.goRight().getColor() == 'B' && mySibling.goLeft().getColor() == 'B') {
                    mySibling.setColor('R');
                    node = myParent;
                }
                else {
                    // case 3: node's sibling is BLACK and sibling's RIGHT child is RED and sibling's LEFT child is BLACK
                    if(mySibling.goLeft().getColor() == 'B') {
                        mySibling.goRight().setColor('B');
                        mySibling.setColor('R');
                        leftRotate(mySibling);
                        mySibling = myParent.goLeft();
                    }
                    // case 4: x's sibling is BLACK and sibling's LEFT child is RED
                    mySibling.setColor(myParent.getColor());
                    myParent.setColor('B');
                    mySibling.goLeft().setColor('B');
                    rightRotate(myParent);
                    node = this.root;
                }
            }
        }
        node.setColor('B');
        return 1;
    }

    // helper method to find the node with min value
    protected rb_node tMinimum(rb_node node){
        while(node.goLeft() != tNil)
            node = node.goLeft();
        return node;
    }

    // wrapper add information
    public int addInfo(information toAdd, String topicName){
        this.root = addInfo(root, toAdd, topicName);
        return 1;
    }

    // method to insert an information
    protected rb_node addInfo(rb_node root, information toAdd, String topicName){
        if(root == tNil)
            return null;

        if(root.myTopic.compareTopicName(toAdd) == 0) {// match found
            root.myTopic.insertInfo(toAdd);
            return root;
        }
        else if(root.myTopic.compareTopicName(toAdd) > 0) {
            root.setLeft(addInfo(root.goLeft(), toAdd, topicName));
        }
        else {// root.myTopic.compareTopicName(toAdd) < 0)
            root.setRight(addInfo(root.goRight(), toAdd, topicName));
        }
        return root;
    }

    // wrapper for deleting info
    public int rbDeleteInfo(String topicName, String infoName){

        return rbDeleteInfo(this.root, topicName, infoName);
    }

    // method to delete an info
    protected int rbDeleteInfo(rb_node root, String topicName, String infoName){
        rb_node curr = root;

        while(curr != tNil){
            if(curr.myTopic.compareTopic(topicName) == 0)
                return curr.myTopic.lDelete(infoName);

            if ((curr.myTopic.compareTopic(topicName)) > 0)
                curr = curr.goLeft();
            else
                curr = curr.goRight();
        }
        return 0;
    }


    /*
    // DELETE RECURSIVE
    //wrapper method for deleting an information
    public rb_node rbDeleteInfo(String topicName, String infoName){
        return rbDeleteInfo(this.root, topicName, infoName);
    }

    //method for deleting an information in a list
    protected rb_node rbDeleteInfo(rb_node root, String topicName, String infoName){
        if(root == tNil)
            return tNil;

        if(root.myTopic.compareTopic(topicName) == 0){// if match
            root.myTopic.lDelete(infoName);
        }
        else if(root.myTopic.compareTopic(topicName) > 0){
            root.setLeft(rbDeleteInfo(root.goLeft(), topicName, infoName));
        }
        else if(root.myTopic.compareTopic(topicName) < 0) {
            root.setRight(rbDeleteInfo(root.goRight(), topicName, infoName));
        }
        return root;
    }
    */

    // wrapper to display topics in the tree
    public int displayAll(){
        return displayAll(this.root);
    }

    // display in order
    protected int displayAll(rb_node root){
        if(root == tNil)
            return 0;

        displayAll(root.goLeft());
        root.display(); // display topic
        displayAll(root.goRight());
        return 1;
    }

    // wrapper to display the BR tree for testing
    public int displayRB(){
        System.out.println("-Printed in pre order-");
        return displayRB(this.root);
    }

    // method to display the BR tree for testing
    protected int displayRB(rb_node root){
        if(root == tNil)
            return 0;

        if(root.goLeft() == tNil && root.goRight() == tNil)
            System.out.println(root.myTopic.topicName + root.color + "(leaf)");
        else
            System.out.println(root.myTopic.topicName + root.color);

        displayRB(root.goLeft());
        displayRB(root.goRight());
        return 1;
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//
   /* TODO working on recursive add function, problem with rotation during recursive calls
    //the algorithm works except for when is time to unwind the stack since Java is not
    //pass by reference
    //this is implemented without the use of tNil node

    //wrapper for recursive add method
    public int addTopic(topic toAdd){
        this.root = addTopic(root, root, toAdd);
        return 1;
    }

    //insert a topic in a RB tree
    // 1 - if the tree is empty insert a B root node
    // 2 - insert at leaf a R node
    //   - if its parent is B OK
    //   - if its parent is R
    //      - if sibling is B or null, rotate, recolor DONE
    //      - if sibling is red, recolor, check again

    protected rb_node addTopic(rb_node parent, rb_node root, topic toAdd) {
        // insert a black node if NOT parent, insert R node if parent
        // root is always B
        if(root == null) {
            root = new rb_node();
            root.myTopic = toAdd;
            //root.setLeft(null);
            //root.setRight(null);

            if(parent == null) { // add a B node when root
                root.setColor('B');
            }
            else {// add a R node when tree is not empty and parent is not null
                root.setParent(parent);
                
                if(parent.color == 'R'){
                    int retVal = parentSibling(root);

                    if (retVal == 1){// parent sibling is red
                        if(checkIfAtRoot(root) == 0){// grandparent is NOT root
                            // change color of parent and sibling
                            rb_node myParent = root.parent;
                            rb_node myGrandParent = myParent.parent;
                            myParent.setColor('B');
                            myGrandParent.setColor('R');
                            myGrandParent.goLeft().setColor('B');
                        }
                        else{
                            rb_node myParent = root.parent;
                            rb_node myGrandParent = myParent.parent;
                            myParent.setColor('B');
                            myGrandParent.goLeft().setColor('B');
                        }
                        return root;
                    }
                    else if ((retVal == 0) || (retVal == 2)) // there is not parent sibling or it is B
                    {
                        boolean left = false;
                        boolean right = false;
                        rb_node myParent = root.parent;
                        rb_node myGrandParent = myParent.parent;

                        //check if I am the L or R child
                        if(root.parent.myTopic.compareTopic(toAdd) <= 0)// go right
                            right = true;
                        if(root.parent.myTopic.compareTopic(toAdd) > 0)// go left
                            left = true;

                        // check for applying the right rotation
                        // child is RIGHT, parent is LEFT child
                        if(right && myParent == myGrandParent.goLeft()) {
                            root = rotateLEFT_RIGHT(root);
                        }
                        // child is LEFT, parent is LEFT child
                        else if(left && myParent == myGrandParent.goLeft()) {
                            root = rotateRIGHT(root);
                        }
                        // child is LEFT, parent is RIGHT child
                        else if(left && myParent == myGrandParent.goRight()) {
                            root = rotateRIGHT_LEFT(root);
                        }

                        // child is RIGHT, parent is RIGHT child
                        //else if (root == myParent.goRight())
                        else if (right && myParent == myGrandParent.goRight()) {
                            root = rotateLEFT(root);
                        }
                    }
                }
            }
            return root;
        }

        // try to go left for insertion
        if (root.myTopic.compareTopic(toAdd) > 0) {// go left if tree > than insert
            root.setLeft(addTopic(root, root.goLeft(), toAdd));
        }

        // try to go right
        if (root.myTopic.compareTopic(toAdd) <= 0) {// go right instead
            root.setRight(addTopic(root, root.goRight(), toAdd));
        }
        return root;
    }

    //-------------------------ROTATION---------------------------//
    //fist rotate LEFT, then rotate RIGHT
    protected rb_node rotateLEFT_RIGHT(rb_node root) {
        rb_node myP = root.parent;
        rb_node myGrandP = myP.parent;
        rb_node myGranGranParent = myGrandP.parent;

        myGranGranParent.setLeft(myP);
        myP.setColor('B');
        return root;
    }

    // rotate RIGHT
    protected rb_node rotateRIGHT(rb_node root) {
        rb_node myP = root.parent;
        rb_node myGrandP = myP.parent;
        rb_node myGranGranP= myGrandP.parent;

        myGranGranP.setLeft(myP);
        myP.setLeft(myGrandP);
        myP.goRight().setColor('R');
        myP.setColor('B');
        return root;
    }

    // rotate RIGHT first, then LEFT
    protected rb_node rotateRIGHT_LEFT(rb_node root){
        rb_node myParent = root.parent;
        rb_node myGrandParent = myParent.parent;

        rb_node myP = root.parent;
        rb_node myGrandP = myP.parent;
        rb_node myGranGranParent = myGrandP.parent;

        myGrandParent.setRight(root);
        root.setRight(myP);
        myGranGranParent.setRight(myP);
        myP.setLeft(myGrandParent);
        myP.setColor('B');
        myGrandParent.setColor('R');
        return root;
    }

    // rotate LEFT
    protected rb_node rotateLEFT(rb_node root){
        rb_node myP = root.parent;
        rb_node myGrandP = myP.parent;
        rb_node myGranGranParent = myGrandP.parent;

        myGranGranParent.setRight(myP);
        myP.setLeft(myGrandP);
        myP.setColor('B');
        myP.goLeft().setColor('R');

        myGrandP.parent.setParent(myP);
        myP.parent.setParent(myGranGranParent);
        myGrandP.setRight(null);
        myGrandP.setLeft(null);
        return root;
    }
    //-----------------------------------------------------------//

    protected int checkIfAtRoot(rb_node root){
        rb_node myGrandParent = root.parent.parent; // get grandparent

        if(myGrandParent == this.root) // check if we are at root
            return 1;
        else
            return 0;
    }

    // check parent's sibling going back to grandparent while keeping track of the parent
    protected int parentSibling(rb_node root){
        rb_node myParent = root.parent; // parent
        rb_node myGrandParent = root.parent.parent; // grandparent

        if(myGrandParent.goRight() == myParent) // we came from the right subtree
            return checkLeft(myGrandParent);
        else // we came from the left subtree
            return checkRight(myGrandParent);
    }

    // utility method to check left of a tree
    protected int checkLeft(rb_node root){
        if (root.goLeft() == null)  // check if there is a sibling
            return 0;
        if (root.goLeft().color == 'R') // check the siblings color
            return 1;
        else
            return 2;
    }

    // utility method to check right of a tree
    protected int checkRight(rb_node root){
        if (root.goRight() == null) // check if there is a sibling
            return 0;
        if (root.goRight().color == 'R')
            return 1;
        else
            return 2;
    }
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//
*/
}
