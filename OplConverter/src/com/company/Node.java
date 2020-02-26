package com.company;

import java.util.LinkedList;
import java.util.List;


public class Node {

    public int ID;
    public int type; //type -1 sink; type 0 transport; type 1 source
    public List<Arc> outflow;
    public List<Arc> inflow;


    public Node (int ID, int type){

        this.ID=ID;
        this.type=type;
        this.outflow= new LinkedList<Arc>();
        this.inflow= new LinkedList<Arc>();




    }



    public boolean equals(Node o) {
        if(this.ID == o.ID) return true;


        return false;
    }
}
