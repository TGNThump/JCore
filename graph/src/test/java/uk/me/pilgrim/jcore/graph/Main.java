package uk.me.pilgrim.jcore.graph;

public class Main {

    public static void main(String[] args) {
        new Graph(){{
            addNodes(8);

            addEdges(1,2,3,7);
            addEdges(2,3);
            addEdges(3,4,7);
            addEdges(4,8);

            getNCore(2);
        }};
    }

}
