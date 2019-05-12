package uk.me.pilgrim.jcore.graph;

import uk.me.pilgrim.jcore.graph.Algorithms.Dijkstra;
import uk.me.pilgrim.jcore.graph.utils.MathUtils;
import uk.me.pilgrim.jcore.graph.utils.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Node {

    private static int count = 0;
    public int id = count++;

    private final Graph graph;
    public Node(Graph graph){this.graph = graph;}

    public List<Edge> out = new ArrayList();
    public List<Edge> in = new ArrayList();

    public String toString(){ return "" + (id+1); }
    public long degree(){ return edges().count(); }

    public Stream<Edge>   out(){ return out.stream(); }
    public Stream<Edge>    in(){ return in.stream(); }
    public Stream<Edge> edges(){ return Stream.concat(out(), in()).distinct(); }

    public Set<Node> neighbours(){
        return out().map(edge -> edge.followFrom(this)).collect(Collectors.toSet());
    }
    public Node randomNeighbour() {
        return out.get(Random.getInt(out.size())).followFrom(this);
    }



    public double localClusteringCoefficient() {
        if (degree() < 2) return 0;

        Set<Node> neighbours = neighbours();

//		System.out.println("Neighbours: " + neighbours);

        Set<Edge> edgesBetweenNeighbours = neighbours.stream()
                .flatMap(node -> node.out())
                .filter(edge -> neighbours.contains(edge.destination) && neighbours.contains(edge.origin))
                .collect(Collectors.toSet());

//		System.out.println("Edges Between Neighbours: " + edgesBetweenNeighbours);

        return (double) edgesBetweenNeighbours.size() / (double) MathUtils.binomialCoeff((int)degree(), 2);
    }

    public double getClosenessCentrality() {
        Dijkstra dijkstra = new Dijkstra(graph, this);
        double sum = dijkstra.getDistances().values().stream().reduce(0.0, Double::sum);
        return sum / graph.nodes.size()-1;
    }

    public double getCentrality() {
        Dijkstra dijkstra = new Dijkstra(graph, this);

        dijkstra.getDistances().remove(this);
        return dijkstra.getDistances().values().stream().mapToDouble(value -> {
            if (Double.isInfinite(value)) return 0;
            else return 1/value;
        }).average().getAsDouble();
    }
}
