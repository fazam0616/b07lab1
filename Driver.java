import java.io.File;
import java.io.FileNotFoundException;

public class Driver{
	public static void main(String[] args) throws FileNotFoundException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,0,0,5};
		double[] co = {0,1,2,3};
		Polynomial p1 = new Polynomial(c1,co);
		double [] c2 = {0,-2,0,0,-9};
		double [] c02 = {0,1,2,3,4};
		Polynomial p2 = new Polynomial(c2,c02);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");

		Polynomial p3 = new Polynomial(new double[]{1,1,7},new double[]{2,3,0});
		Polynomial p4 = new Polynomial(new double[]{1,2},new double[]{4,1});
		Polynomial p5 = p3.multiply(p4);
		Polynomial p6 = new Polynomial(new File("eqn.txt"));
		p6.saveToFile("out.txt");
	}
}