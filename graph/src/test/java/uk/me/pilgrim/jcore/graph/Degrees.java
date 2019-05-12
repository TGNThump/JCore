package uk.me.pilgrim.jcore.graph;

import org.junit.jupiter.api.Test;
import uk.me.pilgrim.jcore.graph.utils.MathUtils;

import static org.junit.jupiter.api.Assertions.*;

public class Degrees {

    @Test
    public void degreeTest(){
        new Graph(){{
            addNodes(7);

            addEdges(1,2,3);
            addEdges(2,1,3,4,5);
            addEdges(3,1,2,4);
            addEdges(4,2,3,5);
            addEdges(5,2,4,6,7);
            addEdges(6,5,7);
            addEdges(7,5,6);

            assertEquals(2, get(1).degree());
            assertEquals(4, get(2).degree());

            assertEquals(10, m());
            assertEquals(20, sumDegrees());

            assertEquals(2, averageDegree());

            assertEquals(10d/21d, density());
        }};
    }

}
