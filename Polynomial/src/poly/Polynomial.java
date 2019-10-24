package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients
	 *         and degrees read from scanner
	 */
	public static Node read(Scanner sc) throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/

		if (poly1 == null && poly2 == null) {
			return null;
		}
		if (poly1 == null) {
			return poly2;
		}
		if (poly2 == null) {
			return poly1;
		}

		// declare a node called "result" as a new polynomial

		Node result = new Node(0, 0, null);
		Node prevPtr = null;
		Node ptr = result;
		boolean pol = false;

		// create a loop: compare each node of poly1 to each node of poly2
		while (poly1 != null && poly2 != null) {

			if (poly1.term.degree == poly2.term.degree) {
				if (prevPtr == null) {
					ptr.term.coeff = poly1.term.coeff + poly2.term.coeff;
					ptr.term.degree = poly1.term.degree;
					prevPtr = ptr;
					ptr = ptr.next;
				} else {
					ptr = new Node(poly1.term.coeff + poly2.term.coeff, poly1.term.degree, null);
					prevPtr.next = ptr;
					prevPtr = ptr;
					ptr = ptr.next;
				}

				poly1 = poly1.next;
				poly2 = poly2.next;
				pol = (poly1 == null) ? true : false;
			} else if (poly1.term.degree > poly2.term.degree) {
				if (prevPtr == null) {
					ptr.term.coeff = poly2.term.coeff;
					ptr.term.degree = poly2.term.degree;
					prevPtr = ptr;
					ptr = ptr.next;
				} else {
					ptr = new Node(poly2.term.coeff, poly2.term.degree, null);
					prevPtr.next = ptr;
					prevPtr = ptr;
					ptr = ptr.next;
				}
				poly2 = poly2.next;

			} else if (poly2.term.degree > poly1.term.degree) {
				if (prevPtr == null) {
					ptr.term.coeff = poly1.term.coeff;
					ptr.term.degree = poly1.term.degree;
					prevPtr = ptr;
					ptr = ptr.next;
				} else {
					ptr = new Node(poly1.term.coeff, poly1.term.degree, null);
					prevPtr.next = ptr;
					prevPtr = ptr;
					ptr = ptr.next;
				}
				poly1 = poly1.next;
				pol = (poly1 == null) ? true : false;
			}

		}
		if (pol == true) {
			prevPtr.next = poly2;
		} else {
			prevPtr.next = poly1;
		}
		while(result.term.coeff == 0) {
			result = result.next;
			if(result == null) {
				return null;
			}
		}
		ptr = result.next;
		prevPtr = ptr;
	
		while(ptr != null) {
			if(ptr.term.coeff == 0) {
				ptr = ptr.next;
				prevPtr = ptr;
			}else {
				prevPtr = prevPtr.next;
				ptr = ptr.next;
			}
		}
		return result;
	}

	// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
	// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		if (poly1 == null || poly2 == null) {
			return null;
		}

		Node result = new Node(0, 0, null);
		Node prevPtr = null;
		Node ptr = result;
		Node polyPtr = poly2;

		// declare a new node "mult" that will contain multiple of poly1 and poly2
		// create loop that will multiply first node in poly1 by each node in poly2
		// until poly2.next = null (multiply coefficients, add degrees)

		while (poly1 != null) {
			while (polyPtr != null) { // do math until poly1.next = null
				if (prevPtr == null) {
					ptr.term.coeff = poly1.term.coeff * polyPtr.term.coeff;
					ptr.term.degree = poly1.term.degree + polyPtr.term.degree;
					prevPtr = ptr;
					ptr = ptr.next;
				} else {
					ptr = new Node(poly1.term.coeff * polyPtr.term.coeff, poly1.term.degree + polyPtr.term.degree,
							null);
					prevPtr.next = ptr;
					prevPtr = ptr;
					ptr = ptr.next;
				}
				polyPtr = polyPtr.next;
			}
			polyPtr = poly2;
			poly1 = poly1.next;
		}
		
		ptr = result;
		prevPtr = result;
		Node subPtr = ptr.next;
		
		//adds together like terms
		while (ptr != null) {
			subPtr = ptr.next;
			while (subPtr != null) {
				if (subPtr.term.degree == ptr.term.degree) {
					ptr.term.coeff = ptr.term.coeff + subPtr.term.coeff;
					subPtr = subPtr.next;
					prevPtr.next = subPtr;
				} else {
					prevPtr = subPtr;
					subPtr = subPtr.next;
				}
			}
			ptr = ptr.next;
			prevPtr = ptr;
		}
		//eliminates zeroes
		while(result.term.coeff == 0) {
			result = result.next;
			if(result == null) {
				return null;
			}
		}
		ptr = result.next;
		prevPtr = ptr;
		
		//eliminates zeroes
		while(ptr != null) {
			if(ptr.term.coeff == 0) {
				ptr = ptr.next;
				prevPtr = ptr;
			}else {
				prevPtr = prevPtr.next;
				ptr = ptr.next;
			}
		}
		
		/*ptr = result;
		prevPtr = result;
		subPtr = result.next;
		while(ptr != null) {
			while(prevPtr.term.degree > subPtr.term.degree) {
				prevPtr = subPtr.next;
				
			}
			
		}
		for(ptr; ptr != null; ptr = ptr.next) {
			
		}
			
		
		
		*/
		
		
		
		
		
		// simplifies/ puts poly in ascending order
		//Node simpResult = new Node(result.term.coeff, result.term.degree, null);
		//Node simPtr = simpResult;
		//Node resPtr = result.next;
		//Node minPtr = null;
		
		/*while(ptr != null) {
			if(result.term.degree > resPtr.term.degree ) {
				minPtr = 
			}
			
		}
		*/
		

		return result;
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x    Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/

		float result = 0;
		while (poly != null) {

			result = result + poly.term.coeff * ((float) Math.pow(x, poly.term.degree));
			poly = poly.next;
		}

		return result;

		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		// return 0;
	}

	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}

		String retval = poly.term.toString();
		for (Node current = poly.next; current != null; current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
}
