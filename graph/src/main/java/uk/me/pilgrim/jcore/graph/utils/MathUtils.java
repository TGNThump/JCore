/**
 * This file is part of network.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package uk.me.pilgrim.jcore.graph.utils;


/**
 * @author Benjamin Pilgrim &lt;ben@pilgrim.me.uk&gt;
 */
public class MathUtils {
	
    public static double binomialCoeff(int n, int k) {
        double C[] = new double[k + 1];

        // nC0 is 1 
        C[0] = 1;

        for (int i = 1; i <= n; i++) {
            // Compute next row of pascal  
            // triangle using the previous row 
            for (int j = Math.min(i, k); j > 0; j--)
                C[j] = C[j] + C[j - 1];
        }
        return C[k];
    }
}
