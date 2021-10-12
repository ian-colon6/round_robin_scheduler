package prj_01;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

// Ián G. Colón ian.colon6@upr.edu
class Node {
    public int id;
    public Node next;
    public Node previous;
    public Boolean proccessed_flag;

    public Node (int id) {
        this.id = id;
        proccessed_flag = true;
    }
}

interface RoundRobinCLLInterface {
    abstract void findEmptySlot();
    abstract void findFilledSlot();
}

public class RoundRobinCLL implements RoundRobinCLLInterface {
    private int num_nodes = 5;
    public Node head = null;
    public Node tail = null;
    public Boolean stopLoop = false;
    private int termination_limit;
    /** INITIALIZED LINKED LIST TO BE USED FOR THE PROGRAM */
    public LinkedList<Node> processes = new LinkedList<Node>();

    private void holdon() {
        try{
            Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(500, 3000));
        }
        catch(Exception e){
            System.out.println("Something went wrong.");
        }
    }

    @Override
    public String toString () {
        String s = new String(""+ Thread.currentThread().getName() + " ");
        Node node = head;
        s+= "(Node-1: " + node.proccessed_flag + ")";
        s+= " ==> ";

        for (int i=1; i<num_nodes; i++) {
            node = node.next;
            s+= "(Node-"+(i+1)+": "+node.proccessed_flag + ")";
            if (i<num_nodes-1)
                s+= " ==> ";
        }
        return s;
    }

    private synchronized void holdRR(Node node, Boolean set_slot) {
        System.out.println("Thread " + Thread.currentThread().getName() + " Holding Resources");
        node.proccessed_flag = set_slot ;
        System.out.println("Thread " + Thread.currentThread().getName() + " Releasing Resources");
        if (set_slot) holdon();
    }

    public void findEmptySlot() {
        holdon();
        /* PUT YOUR CODE HERE TO FIND AN EMPTY SLOT */
        /* STARTING FROM THE FIRST NODE IN THE LINKED LIST */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        /*** TO AVOID RACE CONDITION ***/


        /** 
         * count VARIABLE IS USED TO LOOP THE PROGRAM UNTIL TOLD OTHERWISE BY THE termination_limit VARIABLE
         * 
         * index VARIABLE IS USED TO ACCESS processes LINKED LIST, IF count IS USED THIS WOULD CAUSE AN IndexOutOfBounds EXCEPTION
         * 
         */
        int count = 0;
        int index = 0;
        while(count <= termination_limit && !stopLoop){

            /** 
             * FIRST IF STATEMENT CHECKS WHETHER THE NODE IS PROCESSED OR NOT
             * 
             * SETS TO PROCESSED (true) IF FOUND NOT_PROCESSED (false)
             * 
             */

            if(processes.get(index).proccessed_flag == false){
                holdRR(processes.get(index), true);
            }

            /** RESETS THE INDEX VARIABLE SO THAT IT DOES NOT EXCEED THE SIZE OF THE LINKED LIST AS IT ACCESSES IT */

            if(index == processes.size() - 1) { index = 0; }
            count++;
            index++;
        }
    }

    public void findFilledSlot() {
        /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
        /* FOR THE MAIN PROCESS                        */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/

        /** 
         * count VARIABLE IS USED TO LOOP THE PROGRAM UNTIL TOLD OTHERWISE BY THE termination_limit VARIABLE
         * 
         * index VARIABLE IS USED TO ACCESS processes LINKED LIST, IF count IS USED THIS WOULD CAUSE AN IndexOutOfBounds EXCEPTION
         * 
         */
        int count = 0 ;
        int index = 0;
        while (count <= termination_limit && !stopLoop) {
            /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */

            /** 
             * FIRST IF STATEMENT CHECKS WHETHER THE NODE IS PROCESSED OR NOT
             * 
             * SETS TO NOT_PROCESSED (false) IF FOUND PROCESSED (true)
             * 
             */

            if( processes.get(index).proccessed_flag == true){
                holdRR(processes.get(index), false);
            }

            /** RESETS THE INDEX VARIABLE SO THAT IT DOES NOT EXCEED THE SIZE OF THE LINKED LIST AS IT ACCESSES IT */
            if(index == processes.size() - 1) { index = 0; }

            System.out.println("Main Move No.: " + count%num_nodes + "\t" + toString());
            count++;
            index++;
        }
    }

    private void fillRoundRubin () {
        /* PUT YOUR CODE HERE INITIATE THE CIRCULAR LINKED LIST */
        /* WITH DESIRED NUMBER OF NODES BASED TO THE PROGRAM   */

        /**
         *  INITIALIZES NEW NODE OBJECTS TO BE INSERTED INTO THE LINKED LIST AND PROCESSED
         * 
         * FIRSTLY ASSIGNS proNode TO head ELSE THE tail POINTS TO proNode INSTEAD
         * 
         * WILL ASSIGN proNode TO tail AND ITS NEXT NODE TO head WHILE i < num_nodes
         * 
         * FINALLY ADDS proNode TO LINKED LIST processes
         */

        for(int i = 0; i < num_nodes; i++){

            Node proNode = new Node(i);
            if(head == null){
                head = proNode;
            }else{
                tail.next = proNode;
            }

            tail = proNode;
            tail.next = head;

            processes.add(proNode);
        }
    }

    public RoundRobinCLL(int num_nodes, int termination_limit) {
        this.num_nodes = num_nodes;
        this.termination_limit = termination_limit;
        fillRoundRubin();
    }
    public RoundRobinCLL(int num_nodes) {
        this.num_nodes = num_nodes;
        fillRoundRubin();
    }

    public RoundRobinCLL() {
        fillRoundRubin();
    }

}
