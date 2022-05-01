import java.util.ArrayList;

public class SetOperations {
	ArrayList<Integer> set;
	
	/**
	 * Constructs a set with the variable number of Integers
	 * @param n
	 */
	SetOperations(Integer... n) {
		if(n.length==0)
			return;
		set = new ArrayList<>();
		for(Integer num : n) {
			set.add(num);
		}
		reduce(set);
	}
	/**
	 * Computes the union of two sets of numbers
	 * @param x first set of numbers
	 * @param y second set of numbers
	 * @return The unioned set
	 */
	public ArrayList<Integer> union(ArrayList<Integer> x, ArrayList<Integer> y) {
		ArrayList<Integer> xy = new ArrayList<Integer>(x);
		xy.addAll(y);
		xy.sort(null);
		
		ArrayList<Integer> set = new ArrayList<>();
		set.add(xy.get(0));
		
		int prev = xy.get(0);
		for(int i = 1; i < xy.size(); i++) {
			if (prev != xy.get(i) ) {
				set.add(xy.get(i));
			} 
			prev = xy.get(i);
		}
		
		return set;
		
	}
	/**
	 * Computes the number of combinations given n and r
	 * Should change to BigInteger
	 * @param n
	 * @param r
	 * @return returns the number of combinations
	 */
	public double combinations(int n, int r) {
		if (n == 0 || r == 0) {
			return 0;
		} else if(n == 1) {
			return 1;
		}else  if(r > n || r < 0) {
		
			throw new IllegalArgumentException("n >= r >= 0");
		} else {
		return ((factorial(n) / (factorial(r) * (factorial(n - r)))));
		}
	}
	/**
	 * Computes the factorial 
	 * should change to BigInteger
	 * @param n
	 * @return returns factorial of n
	 */
	public long factorial(int n) {
		if (n == 1 || n == 0) {
			return 1;
		} else return (n* factorial(n-1));
	}
	/**
	 * Computes the number of permutations given n and r
	 * @param n
	 * @param r
	 * @return returns the number of computations
	 */
	public double permutations(int n, int r) {
		if (n == 0 || r == 0) {
			return 0;
		} else if(n == r) {
			return factorial(n);
		}else  if(r > n || r < 0) {
			throw new IllegalArgumentException("n >= r >= 0");
		} else {
		return ((factorial(n) / (factorial(n - r))));
		}	}
	
	/**
	 * Computes the intersection of two sets of Integers
	 * @param x
	 * @param y
	 * @return Returns the intersection
	 */
	public ArrayList<Integer> intersect(ArrayList<Integer> x, ArrayList<Integer> y) {
		ArrayList<Integer> set = new ArrayList<>();
		x = new ArrayList<>(reduce(x));
		y = new ArrayList<>(reduce(y));

		for(int i = 0; i < x.size(); i++) {
			for(int j = 0; j < y.size(); j++) {
				if (x.get(i) == y.get(j))
						set.add(x.get(i));
			}
		}
		
		set.sort(null);
		//reduce(set);
		return set;
	
	}
	/**
	 * Computes the compliment of two given sets of Integers
	 * @param a
	 * @param x
	 * @return Returns the compliment
	 */
	public ArrayList<Integer> compliment(ArrayList<Integer> a, ArrayList<Integer> x) {
		ArrayList<Integer> set = new ArrayList<>(a);
		
		for(int i = 0; i < x.size(); i++) {
			set.remove(x.get(i));
		}
		return set;
	}
	/**
	 * Prints Number of combinations of permutations given n and r
	 * @param n
	 * @param r
	 */
	public void testCombAndPerm(int n, int r) {
		System.out.println("Combinations: " + combinations(n, r));
		System.out.println("Permutations: " + permutations(n, r));

	}
	/**
	 * Prints the various set operations between this objects set and a list of Integers passed
	 * @param n Variable amount of Integers
	 */
	public void testSetOps(Integer... n) {
		ArrayList<Integer> set2 = new ArrayList<>();
		for (Integer num : n) {
			set2.add(num);
		}
		
		System.out.println("Union: " + union(this.set, set2));
		System.out.println("Compliment: " + compliment(this.set, set2));
		System.out.println("Intersect: " + intersect(this.set, set2));


	}
	/**
	 * Gets rid of duplicate Integers in a list
	 * @param s list of Integers
	 * @return returns list without any duplicate numbers
	 */
	private ArrayList<Integer> reduce(ArrayList<Integer> s) {
		s.sort(null);

		ArrayList<Integer> set = new ArrayList<>();
		int prev = s.get(0);
		set.add(prev);
		for (int i = 1; i < s.size(); i++) {
			if (prev != s.get(i))
				set.add(s.get(i));
			prev = s.get(i);
		}
		return set;
		
	}
	
}