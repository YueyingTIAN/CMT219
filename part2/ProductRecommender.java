public class ProductRecommender {
  ChoiceStrategy myStrategy;

  public static void main(String args[]) {
    ProductRecommender recommender=new ProductRecommender();
    recommender.doExample();
  }

  public void doExample() {
    Product p1=new Product("DeLorean DMC-12", 5, 1);
    Product p2=new Product("LDV Maxus", 1, 5);

    System.out.println("Current strategy: choose most futuristic");
  	// Add code here to create a MostFuturisticStrategy and
   	// print out the chosen vehicle according to this strategy
    myStrategy = new MostFuturisticStrategy();
    Product p = myStrategy.chooseBetween(p1, p2);
    System.out.println(p.name);

    System.out.println("Strategy changed: choose most practical");
    // Add code here to create a MostPracticalStrategy and
    // print out the chosen vehicle according to this strategy
    myStrategy = new MostPracticalStrategy();
    p = myStrategy.chooseBetween(p1, p2);
    System.out.println(p.name);
  }
}

