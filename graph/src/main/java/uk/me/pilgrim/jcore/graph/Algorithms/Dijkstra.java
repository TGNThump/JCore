/**
 * This file is part of network.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package uk.me.pilgrim.jcore.graph.Algorithms;

import uk.me.pilgrim.jcore.graph.Edge;
import uk.me.pilgrim.jcore.graph.Graph;
import uk.me.pilgrim.jcore.graph.Node;

import java.util.*;

/**
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
public class Dijkstra {
	private final Map<Node, Double> distances = new HashMap<Node, Double>();
	private final Map<Node, Node> previous = new HashMap<>();
	
	public Dijkstra(Graph graph, Node initial) {
		Set<Node> unvisited = new HashSet<>();
		
		for (Node node : graph.nodes) {
			distances.put(node, Double.POSITIVE_INFINITY);
			unvisited.add(node);
		}
	
		distances.put(initial, 0d);
		
		while(!unvisited.isEmpty()) {
			Node u = unvisited.stream().min(Comparator.comparing(node -> distances.get(node))).get();
			unvisited.remove(u);

			for (Edge edge : u.out) {
				Node v = edge.destination;
				double alt = distances.get(u) + edge.getWeight();
				if (alt < distances.get(v)) {
					distances.put(v, alt);
					previous.put(v, u);
				}
			}
		}
	}
	
	public Map<Node, Double> getDistances(){
		return distances;
	}
}
