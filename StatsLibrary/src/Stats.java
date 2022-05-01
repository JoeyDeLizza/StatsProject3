// Joint effort by Joe DeLizza and Joshua Distefano

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public class Stats {

	private ArrayList<Double> nums;
	
	/**
	 * Copies passed ArrayList into nums
	 * @param nums list of Double numbers
	 */
	Stats(ArrayList<Double> nums) {
		this.nums = new ArrayList<>(nums);
		nums.sort(null);
	}
	
	/**
	 * Copies variable amount of Double numbers into nums
	 * @param n list of Double numbers
	 */
	Stats(Double... n) {
		this.nums = new ArrayList<>();
		for(Double x : n) {
			nums.add(x);
		}
		nums.sort(null);
	}
	
	public ArrayList<Double> getNums() {
		return nums;
	}

	public void setNums(ArrayList<Double> nums) {
		this.nums = nums;
	}
	
	/**
	 * 
	 * @return return the mean of the numbers in num ArrayList
	 */
	public double mean() {
		return sum() / nums.size();
	}
	
	/**
	 * 
	 * @return returns the sum of the numbers in num ArrayList
	 */
	public double sum() {
		return sum(this.nums);
	}
	
	/**
	 * Calculates the variance of the num ArrayList
	 * @return returns variance
	 */
	public double variance() {
		ArrayList<Double> list = new ArrayList<>(this.nums);
		for(int i = 0; i < list.size(); i++) {
			list.set(i, Math.pow(list.get(i) - mean(), 2));
		}
		
		return sum(list) / (list.size() - 1);
		
		
	}
	/**
	 * Calculates the standard deviation of the nums ArrayList
	 * @return returns the standard deviation
	 */
	public double standardDeviation() {
		return Math.sqrt(variance());
	}
	
	/**
	 * Calculates the sum of all numbers in an ArrayList
	 * @param n ArrayList with Double numbers
	 * @return Returns the total sum
	 */
	private double sum(ArrayList<Double> n) {
		double sum = 0;
		for(Double num : n) {
			sum+= num;
		}
		return sum;
	}
	/**
	 * Finds the median number in the nums ArrayList
	 * @return Returns the median
	 */
	public double median() {
		return median(this.nums);
	}
	
	/**
	 * Finds the mode of the nums ArrayList
	 * @return Returns a list of mode(s)
	 */
	public ArrayList<Double> mode() {
		return mode(this.nums);
	}
	
	/**
	 * Finds the mode of a given ArrayList of Double numbers
	 * @param n List of numbers
	 * @return Returns a list of mode(s)
	 */
	public ArrayList<Double> mode(ArrayList<Double> n) {
		int most = 0;
		ArrayList<Double> mode = new ArrayList<>();
		for(int i = 1; i < n.size(); i++)	 {
			int freq = Collections.frequency(n, n.get(i));
			if (freq > most && (n.get(i) != n.get(i-1))) {
				most = freq;
				mode = new ArrayList<>();
				mode.add(n.get(i));
			} else if(freq == most ) {
				mode.add(n.get(i));
			}
		}
		return reduce(mode);
			
	}
	
	/**
	 * Calculates the binomial distribution of a random variable y
	 * @param n population size
	 * @param y random variable
	 * @param p probability of event
	 * @param q 1-p
	 * @return returns the binomial distribution
	 */
	public double biDist(int n, int y, double p, double q) {
		SetOperations comb = new SetOperations();
		return comb.combinations(n, y) * Math.pow(p, y) * Math.pow(q, (n-y));
	}
	
	public void testBiDist(int n, int y, double p, double q ) {
		System.out.println("Binomial Distribution:");
		System.out.println("P(Y=" + y+ "): " +biDist(n, y, p, q));
	}
	
	/**
	 * Calculates the geometric distribution of a random variable Y
	 * @param y random variable 
	 * @param p
	 * @param q
	 * @return returns the geometric distribution
	 */
	public double geoDist(int y, double p, double q) {
		return Math.pow(q, (y-1)) * p;
	}
	
	public void testGeoDist(int y, double p, double q ) {
		System.out.println("Geometric Distribution:");
		System.out.println("P(Y=" + y+ "): " +geoDist(y, p, q));
	}
	
	/**
	 * Calculates the Hyper Geometric distribution
	 * @param r
	 * @param y
	 * @param BN
	 * @param n
	 * @return
	 */
	public double hyperGeoDist(int r, int y, int N, int n) {
		SetOperations comb = new SetOperations();
		return (comb.combinations(r, y) * comb.combinations((N -r), (n-r))) / comb.combinations(N, n);
	}
	
	public void testHyperGeoDist(int r, int y, int N, int n ) {
		System.out.println("HyperGeometric Distribution:");
		System.out.println("P(Y=" + y+ "): " +hyperGeoDist(r, y, N, n));
	}
	
	public double poisDist(int y, double l) {
		SetOperations fact = new SetOperations();
		return (Math.pow(l, y)/ fact.factorial(y)) * Math.pow(Math.E, l*-1);
	}
	
	public double poisDist(int y, int n, double p) {
		double l = n*p;
		return poisDist(y, l);
	}
	
	public void testPoisDist(int y, int l) {
		System.out.println("Poisson Distribution:");
		System.out.println("P(Y=" + y + "): " +poisDist(y, l));
	}
	
	/**
	 * Calculates the probability of a random variable falling between the intervals [a,b]
	 * Creates a polynomial function using the terms in the c array
	 * Integrates the function at the bounds using Simpson's Rule
	 * @param a lower bound of the interval
	 * @param b upper bound of the interval
	 * @param c an array holding the terms of a polynomial ie. [0] = constant [1] = x [2] = x^2 ...
	 * @return probability
	 */
	public double probDensFunc(double a, double b, double... c ) {
		return probDensFuncArray(a, b, c);
	}
	
	/**
	 * Calculates the probability of a random variable falling between the intervals [a,b]
	 * Creates a polynomial function using the terms in the c array
	 * Integrates the function at the bounds using Simpson's Rule
	 * @param a lower bound of the interval
	 * @param b upper bound of the interval
	 * @param c an array holding the terms of a polynomial ie. [0] = constant [1] = x [2] = x^2 ...
	 * @return probability
	 */
	public double probDensFuncArray(double a, double b, double[] c ) {
		PolynomialFunction f = new PolynomialFunction(c);
		SimpsonIntegrator simp = new SimpsonIntegrator();
		return simp.integrate(100, f, a, b);
	}
	
	public void testProbDensFunc(double a, double b, double c, double d, double... cons) {
		System.out.println("Density Functions Probability:");
		System.out.println("P(1 < Y < 2) = " + probDensFunc(a, b, cons));
		System.out.println("E(Y): " + expecVProbDensFuncArray(c, d, cons));
		System.out.println("V(Y): " + varianceProbDensFuncArray(c, d, cons) + "\n");
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public double expecVProbDensFunc(double a, double b, double... c) {
		return expecVProbDensFuncArray(a, b, c);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public double expecVProbDensFuncArray(double a, double b, double[] c) {
		PolynomialFunction f = new PolynomialFunction(funcTimesY(c));
		SimpsonIntegrator simp = new SimpsonIntegrator();
		return simp.integrate(100000, f, a, b);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public double varianceProbDensFunc(double a, double b, double... c) {
		PolynomialFunction f = new PolynomialFunction(funcTimesY(c));
		// g(y) = f(y^2)
		PolynomialFunction g = new PolynomialFunction(funcTimesYArray(f.getCoefficients()));
		
		SimpsonIntegrator simp = new SimpsonIntegrator();
		
		// V(Y) = E(Y^2) - E(Y)^2
		return simp.integrate(1000, g, a, b) - Math.pow(simp.integrate(1000, f, a, b), 2);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public double varianceProbDensFuncArray(double a, double b, double[] c) {
		PolynomialFunction f = new PolynomialFunction(funcTimesY(c));
		// g(y) = f(y^2)
		PolynomialFunction g = new PolynomialFunction(funcTimesYArray(f.getCoefficients()));
		
		SimpsonIntegrator simp = new SimpsonIntegrator();
		
		// V(Y) = E(Y^2) - E(Y)^2
		return simp.integrate(1000, g, a, b) - Math.pow(simp.integrate(1000, f, a, b), 2);
	}
	
	/**
	 * funcTimesYArray with parameterized list 
	 * a PolynomialFunction.
	 * @param c coefficient array
	 * @return returns an array representing a coefficient array of yf(y)
	 */
	private double[] funcTimesY(double... c) {
		return funcTimesYArray(c);

	}
	
	/**
	 * This function performs the same as funcTimesY() but takes an array and takes the coefficient array from 
	 * a PolynomialFunction.
	 * @param c coefficient array
	 * @return returns an array representing a coefficient array of yf(y)
	 */
	private double[] funcTimesYArray(double[] c) {
		double[] newFunc = new double[c.length+2];
		for(int i = 0; i < c.length; i++) {
			if (c[i] == 0) {
				newFunc[i] = 0;
			} else {
				newFunc[i+1] = c[i];
			}			
		}
		return newFunc;
	}
	
	/**
	 * Finds the probability that a random variable will fall between the interval [c,d] in a uniform dist.
	 * @param a lower bound of the density function
	 * @param b upper bound of the density function
	 * @param c lower bound of the probability interval
	 * @param d upper bound of the probability interval
	 * @return probability that a random variable falls between [c,d]
	 */
	public double UniformDist(double c, double d, double a, double b) {
		double[] coef = new double[1];
		coef[0] = 1 / (b - a);
		PolynomialFunction f = new PolynomialFunction(coef);
		SimpsonIntegrator simp = new SimpsonIntegrator();
		
		return simp.integrate(1000, f, c, d);
	}
	
	/**
	 * Calculates the expected value in a uniform distribution
	 * @param a lower bound 
	 * @param b upper bound
	 * @return E(Y)
	 */
	public double expecVaUniformDist(double a, double b) {
		return (a + b) / 2;
	}
	
	/**
	 * Calculates the variance in a uniform distribution
	 * @param a lower bound
	 * @param b upper bound
	 * @return V(Y)
	 */
	public double variUniformDist(double a, double b) {
		return Math.pow((b - a), 2) / 12;
	}
	
	public void testUniformDist(double c, double d, double a, double b) {
		System.out.println("Uniform Dist: " + UniformDist(c, d, a, b));
		System.out.println("E(Y): " + expecVaUniformDist(a, b));
		System.out.println("V(Y): " + variUniformDist(a, b) + "\n");
	}
	
	
	
	/**
	 * Finds the median of a given ArrayList of Double numbers
	 * @param n List of numbers
	 * @return Returns the median
	 */
	private double median(ArrayList<Double> n) {
		ArrayList<Double> list = new ArrayList<>(n);
		if (list.size() == 2)
			return (sum(list)) / 2;
		else if(list.size() == 1)
			return list.get(0);
		
		list = new ArrayList<>(list.subList(1, list.size()-1));
		return median(list);
	}
	
	/**
	 * Gets rid of any duplicate numbers in an ArrayList
	 * @param s List of numbers
	 * @return Returns a list of number without duplicates
	 */
	private ArrayList<Double> reduce(ArrayList<Double> s) {
		s.sort(null);

		ArrayList<Double> set = new ArrayList<>();
		Double prev = s.get(0);
		set.add(prev);
		for (int i = 1; i < s.size(); i++) {
			if (Double.compare(prev, s.get(i)) != 0)
				set.add(s.get(i));
			prev = s.get(i);
		}
		return set;
		
	}
	/**
	 * Computes and displays stats
	 */
	public void runAllStats() {
		System.out.println("Mean: " + mean());
		System.out.println("Median: " + median());
		System.out.println("Mode: " + mode());
		System.out.println("Standard Deviation: " + standardDeviation());
		System.out.println("Variance: " + variance());
	}
	
	public void runAllDiscreteDist() {
		System.out.println();
	}
	
	public void runAllContinuous(double a, double b, double c, double d, double...cons) {
		testProbDensFunc(a, b, c, d, cons);
		testUniformDist(a, b, c, d);
	}
	
}
