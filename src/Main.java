/*
Cosimo Gonnelli - Grad student at PSU - Computer Science 

The purpose of this program is to practice data structures and OOP.
A Rad-Black tree is created where each node is a topic name (category) 
that holds a LLL of information divided in website, Linux tools and 
differences between C++ syntax and Java syntax.
Also a CLL is created, reading in from file, using the syntax differences
between C++ and Java syntax. This program uses recursive and iterative methods.

.txt files for testing are provided with the project.
*/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        RBtree myRBtree = new RBtree(); // tree of topics
        CLL myCLL = new CLL();  // list of syntax maps

        readInTopic(myRBtree); // read in a file of topics (e.g. dynamic binding, initialization list)
        readInInfo(myRBtree); // read in information (e.g. websites for dynamic binding, linux tool for deletion topic)
        //myRBtree.displayRB(); // uncomment to print the tree for checking correct insert implementation
        userMenu(myRBtree, myCLL);
    }

    // menu to handle the tree by the user
    public static int userMenu(RBtree myRBtree, CLL myList) {
        Scanner input = new Scanner(System.in);
        int option;
        int flag;

        do {
            System.out.print("\n\n\t|~~~~~~~~~~~~~~~~~~~~~ Menu ~~~~~~~~~~~~~~~~~~~~|\n");
            System.out.print("\t|1 - Display all information                    |\n");
            System.out.print("\t|2 - Delete a list of information by topic name |\n");
            System.out.print("\t|3 - Make a list of syntax mapping              |\n");
            System.out.print("\t|4 - Display list                               |\n");
            System.out.print("\t|5 - Delete a specific information in a list    |\n");
            System.out.print("\t|6 - Quit                                       |\n");
            System.out.print("\t|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
            System.out.print("\nSelect one of the above options (from 1 to 6)\n>");
            option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    myRBtree.displayAll();
                    break;
                case 2:
                    flag = deleteList(myRBtree);
                    if(flag == 0)
                        System.out.println("\nMatch not found, try again\n");
                    else
                        System.out.println("\nMatch found, list deleted\n");
                        //myRBtree.displayRB(); // uncomment to print the tree for checking correct insert implementation
                    break;
                case 3:
                    readInCLL(myList);
                    break;
                case 4:
                    flag = myList.displayCLL();
                    if(flag == 0)
                        System.out.println("\nNothing to display! make a list first\n");
                    break;
                case 5:
                    flag = deleteInfo(myRBtree);
                    if(flag == 0)
                        System.out.println("\nTopic name not found, try again\n");
                    else if(flag == -1)
                        System.out.println("\nInfo name not found, try again\n");
                    else
                        System.out.println("\nMatch found, element deleted\n");
                    break;
                case 6:
                    System.out.print("\tBye Bye!\n");
                    break;
            }
        } while (option != 6); // loop until quit selected
        return option;
    }

    // rear in a topic file
    public static int readInTopic(RBtree myRBtree) {
        try {
            File toRear = new File("topic.txt");
            Scanner IN = new Scanner(toRear);
            String buffer;

            String topicName;

            while (IN.hasNextLine()) {
                buffer = IN.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(buffer, "|");
                topicName = tokenizer.nextToken();

                topic temp = new topic(topicName);
                myRBtree.insertTopic(temp); // add a topic to the tree
            }
            IN.close();
        }
        // catch the case where the file is empty
        catch (IOException noFile) {
            noFile.printStackTrace();
        }
        return 1;
    }

    // read in information to a specific topic
    public static int readInInfo(RBtree myRBtree) {
        try {
            File toRead = new File("information.txt");
            Scanner IN = new Scanner(toRead);
            String buffer;

            String name, type, topicName, address, evaluation, description, command, cPlusPlus, java;

            while (IN.hasNextLine()) {
                buffer = IN.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(buffer, "|");
                name = tokenizer.nextToken();
                type = tokenizer.nextToken();
                topicName = tokenizer.nextToken();

                if (type.equals("website")) { // add a website
                    address = tokenizer.nextToken();
                    evaluation = tokenizer.nextToken();

                    information temp = new website(name, type, topicName, address, evaluation);
                    myRBtree.addInfo(temp, topicName);
                }
                else if (type.equals("Linux tool")) { // add a linux tool
                    description = tokenizer.nextToken();
                    command = tokenizer.nextToken();

                    information temp = new linuxTool(name, type, topicName, description, command);
                    myRBtree.addInfo(temp, topicName);
                }
                else if (type.equals("syntax map")) { // add a syntax map
                    cPlusPlus = tokenizer.nextToken();
                    java = tokenizer.nextToken();

                    information temp = new syntaxMap(name, type, topicName, cPlusPlus, java);
                    myRBtree.addInfo(temp, topicName);
                }
            }
            IN.close();
        }
        // catch the case where the file is empty
        catch (IOException noFile) {
            noFile.printStackTrace();
        }
        return 1;
    }
    // make a CLL list of syntax map
    public static int readInCLL(CLL myCLL) {
        try {
            File toRear = new File("list.txt");
            Scanner IN = new Scanner(toRear);
            String buffer;

            String name, type, topicName, cPlusPlus, java;

            while (IN.hasNextLine()) {
                buffer = IN.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(buffer, "|");

                name = tokenizer.nextToken();
                type = tokenizer.nextToken();
                topicName = tokenizer.nextToken();
                cPlusPlus = tokenizer.nextToken();
                java = tokenizer.nextToken();

                syntaxMap temp = new syntaxMap(name, type, topicName, cPlusPlus, java);
                myCLL.addInfo(temp); // add a topic to the tree
            }
            IN.close();
        }
        // catch the case where the file is empty
        catch (IOException noFile) {
            noFile.printStackTrace();
        }
        System.out.println("\nA list of syntax map created\n");
        return 1;
    }

    // delete list of information by topic name
    public static int deleteList(RBtree myRBtree){
        Scanner input = new Scanner(System.in);
        String topicName;

        System.out.print("\nWhat is the name of the topic of the list to delete(e.g.topic2)\n>");
        topicName = input.nextLine();

        return myRBtree.rbDelete(topicName);
    }

    // delete a specific information by information name
    public static int deleteInfo(RBtree myRBtree){
        Scanner input = new Scanner(System.in);
        String topicName;
        String infoName;

        System.out.print("\nWhat is the name of the topic from where to delete an information?(e.g.topic2)\n>");
        topicName = input.nextLine();
        System.out.print("\nWhat is the name of the information to delete(e.g.name2)\n>");
        infoName = input.nextLine();

        return myRBtree.rbDeleteInfo(topicName, infoName);
    }
}
