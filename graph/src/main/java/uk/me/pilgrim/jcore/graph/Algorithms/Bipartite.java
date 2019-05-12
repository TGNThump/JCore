package uk.me.pilgrim.jcore.graph.Algorithms;

import uk.me.pilgrim.jcore.graph.Node;

import java.util.List;
import java.util.Stack;

public class Bipartite {

    public static boolean isBipartite(List<Node> nodes){
        int[] colors = new int[nodes.size()];

        for (int i=0;i < nodes.size(); i++) {
            if (colors[i] != 0) continue;
            Node currentNode = nodes.get(i);
            colors[i] = 1;

            Stack<Node> nodeStack = new Stack();
            nodeStack.push(currentNode);

            while (!nodeStack.isEmpty()) {
                Node u = nodeStack.pop();
                int uIndex = nodes.indexOf(u);

                for (Node neighbour : u.neighbours()) {
                    int index = nodes.indexOf(neighbour);
                    if (colors[index] == colors[uIndex]) {
                        return false;
                    } else if (colors[index] == 0) {
                        nodeStack.push(neighbour);
                        colors[index] = 3 - colors[uIndex];
                    }
                }
            }
        }

        return true;
    }
}
