package com.company;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //Preparation for reading the file
        String path = "/home/nikola/UniCa/Magistrale/[2 anno - I] NFO - Di Francesco/Project/Topologies/300_nodes_6320_arcs.txt";

        FileReader input = new FileReader(path);
        BufferedReader bufRead = new BufferedReader(input);
        String myLine = null;
        String[] row, rowElements;
        //Auxiliary variables

        Node[] nodeList=null;
        List<Arc> arcList = new LinkedList<Arc>();;
        int sourceID=0, sinkID=0,nodeNumber=0,arcNumber=0, counter=0,auxCapacity;
        Arc auxArc;
        Node auxHead, auxTail;

        while ( (myLine = bufRead.readLine()) != null)
        {


            row = myLine.split("\n");
            // check to make sure you have valid data

            rowElements = row[0].split(" ");
            if(counter==0) {
                nodeNumber = Integer.parseInt(row[0]);
                nodeList = new Node[nodeNumber];

                for(int i=0;i<nodeNumber;i++){

                    nodeList[i]= new Node(i+1,0);

                }

            }

            if(counter==1){
                arcNumber=Integer.valueOf(rowElements[0]);
                arcNumber++;
                arcNumber--;

               }

            if(counter==2){ sourceID= Integer.valueOf(rowElements[0]);}
            if(counter==3){ sinkID= Integer.valueOf(rowElements[0]);}

            if(counter>3){

                auxTail=new Node (nodeList[Integer.parseInt(rowElements[0])-1].ID,
                        nodeList[Integer.parseInt(rowElements[0])-1].type);

                //I save the data about the head from nodeList
                auxHead=new Node (nodeList[Integer.parseInt(rowElements[1])-1].ID,
                        nodeList[Integer.parseInt(rowElements[1])-1].type);

                //I save the arc capacity
                auxCapacity=Integer.parseInt(rowElements[2]);
                auxArc = new Arc(auxTail,auxHead,auxCapacity);

                arcList.add(auxArc);
            }





        counter++;
        }

        for (Arc a: arcList
        ) {

            nodeList[a.head.ID-1].inflow.add(a);
            nodeList[a.tail.ID-1].outflow.add(a);

        }

        System.out.println("WeweStop ci sono tot archi:"+arcNumber+"\n sorgente:"+sourceID+"\npozzo: "+sinkID);


        for (Arc a: nodeList[sinkID-1].inflow
             ) {

            if(nodeList[a.tail.ID-1].inflow.size()==0){

                System.err.println("Il nodo"+a.tail.ID+" ha inflow 0");
            }

        }

        String filename = nodeNumber+"_nodes_"+arcNumber+"_arcs.mod";
        File newfile = new File ("/home/nikola/UniCa/Magistrale/[2 anno - I] NFO - Di Francesco/Project/Mod Files/"+filename);
        newfile.createNewFile();
        FileWriter writer = new FileWriter(newfile);

        //First I have to define all the flow variables

        for (Arc a: arcList
             ) {

            writer.write("dvar int+ x"+a.tail.ID+"to"+a.head.ID+";\n");
        }


        //Then I write the objective function
        writer.write("\n\n\nmaximize ");

        int outflowSize=nodeList[sourceID-1].outflow.size();
        for (int i=0; i<outflowSize-1;i++){

            writer.write("x"+nodeList[sourceID-1].outflow.get(i).tail.ID+"to"
                    +nodeList[sourceID-1].outflow.get(i).head.ID+"+");


        }

        writer.write("x"+nodeList[sourceID-1].outflow.get(outflowSize-1).tail.ID+"to"
                +nodeList[sourceID-1].outflow.get(outflowSize-1).head.ID+";\n\n");




        writer.write("subject to {\n");
        for (Node n: nodeList
             ) {

            if(n.ID==sinkID || n.ID==sourceID){}
            else{

                //Mass Balance Constraint

                for (int i=0; i<n.outflow.size();i++){

                    if (i==0){

                        writer.write("x"+n.outflow.get(i).tail.ID+"to"+n.outflow.get(i).head.ID);
                    }else{

                        writer.write("+x"+n.outflow.get(i).tail.ID+"to"+n.outflow.get(i).head.ID);

                    }


                }

                for (Arc a: nodeList[n.ID-1].inflow
                     ) {

                    writer.write("-x"+a.tail.ID+"to"+a.head.ID);
                }
                writer.write("==0;\n");
            }

            writer.write("");



        }

        for (Arc a: arcList
             ) {

            writer.write("x"+a.tail.ID+"to"+a.head.ID+"<="+a.capacity+";\n");

        }

        writer.write("\n}");
        writer.flush();
        writer.close();

    }
}
