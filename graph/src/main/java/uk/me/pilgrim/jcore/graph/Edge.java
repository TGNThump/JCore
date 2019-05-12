package uk.me.pilgrim.jcore.graph;

public class Edge {

    public Node origin;
    public Node destination;
    public boolean isDirected = false;

    public Edge(Node origin, Node destination, boolean isDirected){
        this.origin = origin;
        this.destination = destination;
        this.isDirected = isDirected;

        origin.out.add(this);
        destination.in.add(this);

        if (!isDirected) {
            origin.in.add(this);
            destination.out.add(this);
        }

    }

    public Node followFrom(Node from) {
        if (isDirected) {
            if (from.equals(destination)) throw new IllegalArgumentException("Cannot follow directed edge followFrom destination to source");
            else return destination;
        } else {
            if (from.equals(origin)) return destination;
            else return origin;
        }
    }

    public void reverse() {
        if (!isDirected) return;
        origin.in.remove(this);
        origin.out.remove(this);
        destination.in.remove(this);
        destination.out.remove(this);

        Node swap = this.origin;
        this.origin = this.destination;
        this.destination = swap;

        origin.out.add(this);
        destination.in.add(this);
    }

    public String toString() {
        return origin.toString() + (isDirected ? "->" : "<->") + destination.toString();
    }

    public Double getWeight() { return 1d; }
}
