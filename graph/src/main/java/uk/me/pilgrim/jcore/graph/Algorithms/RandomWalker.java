/**
 * This file is part of network.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package uk.me.pilgrim.jcore.graph.Algorithms;


import uk.me.pilgrim.jcore.graph.Node;

import java.util.function.Function;

/**
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
public class RandomWalker implements Function<Node, Node> {

	@Override
	public Node apply(Node current) {
		return current.randomNeighbour();
	}
	
}
