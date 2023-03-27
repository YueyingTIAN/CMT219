public class MostPracticalStrategy implements ChoiceStrategy {
  	// Complete this with an implementation of
	// chooseBetween(Product a, Product b) which returns
	// Product a if its practicality is greater than or equal
	// to that of b; and returns Product b otherwise

	public Product chooseBetween(Product a, Product b) {
		if (a.practicality >= b.practicality) {
			return a;
		} else {
			return b;
		}	
	}
}
   
