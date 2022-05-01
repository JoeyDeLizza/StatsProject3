
public class StatsTester {

	public static void main(String[] args) {
		Stats test = new Stats();
		
		// testProbDensFunc(a, b, c, d, cons...)
		// [a,b]=upper and lower bound of probability interval where Y falls
		// [c,d]= interval of Dist Function
		// cons = parametized list of coefficients of a polynomial cons[0] = constant cons[1] = x cons[2] = x^2 ...
		test.testProbDensFunc(1, 2, 0, 2, 0, 0, .375);
		
		// testUniformDist(a, b, c, d)
		// [a,b]= upper and lower bound of probability interval where Y falls
		// [c,d]= upper and lower bound of entire Dist Function
		test.testUniformDist(25, 30, 0, 30);
	}

}
