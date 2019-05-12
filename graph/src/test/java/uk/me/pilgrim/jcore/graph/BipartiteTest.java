package uk.me.pilgrim.jcore.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BipartiteTest {

    @Test
    public void BipartiteTest1(){
        new Graph(){{
            addNodes(7);

            addEdges(1, 6,7);
            addEdges(2, 7);
            addEdges(3, 4,7);
            addEdges(4, 3,5);
            addEdges(5, 4,6);
            addEdges(6, 1,5,7);
            addEdges(7, 1,2,3,6);

            assertFalse(isBipartite());
        }};
    }

    @Test
    public void BipartiteTest2(){
        new Graph(){{
            addNodes(9);

            addEdges(1, 6);
            addEdges(2, 6, 7);
            addEdges(3, 8, 9);
            addEdges(4, 7);
            addEdges(5, 6, 9);

            assertTrue(isBipartite());
        }};
    }

    @Test
    public void BipartiteTest3(){
        new Graph(){{
            addNodes(2);
            addEdges(1, 2);
            assertTrue(isBipartite());
        }};
    }

    @Test
    public void BipartiteTest4(){
        new Graph(){{
           addNodes(7);

           addEdges(1, 2, 3);
           addEdges(2, 1, 4);
           addEdges(3, 1, 7);
           addEdges(4, 2, 3, 5);
           addEdges(5, 4, 6);
           addEdges(6, 5, 7);
           addEdges(7, 3, 6);

           assertFalse(isBipartite());
        }};
    }
}
