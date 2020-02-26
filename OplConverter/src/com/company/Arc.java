package com.company;

public class Arc {

    //An Arc is expressed in the form (tail)->(head) and has a nonnegative capacity value

    public Node head;
    public Node tail;
    public int capacity;



    public Arc(Node tail, Node head, int capacity){

        this.head= new Node (head.ID,head.type);
        this.tail= new Node (tail.ID,tail.type);
        this.capacity=capacity;
    }


    public boolean equals (Arc a){

        if (this.head.equals(a.head) && this.tail.equals(a.tail) ) return true;


        return false;
    }

}
