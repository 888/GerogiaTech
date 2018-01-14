public class Program {
	public static void main(String [] args) {
		/*if we dont use the f at the end, twoThirds will equal 0.0 because 2 and 3 
		are both ints so the result is an int. */
		float twoThirds = 2/ 3.0f; 
		System.out.println("twothirds="+ twoThirds
		 + " -- length=" + String.valueOf(twoThirds).length());
		double dtwoThirds = 2.0/3;
		System.out.println("dtwothirds="+ dtwoThirds
		 + " -- length=" + String.valueOf(dtwoThirds).length());
		/*A float if a single percision while a double is a doule-percision value
		which specifies the amount of space in memory it holds
		*/

		double one = twoThirds / dtwoThirds;
		System.out.println("one="+one);
	}
}