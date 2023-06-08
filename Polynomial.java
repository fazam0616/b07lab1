import java.util.Arrays;

public class Polynomial{
	private double[] coeff;
	private double[] exponent;
	public Polynomial(){
		this.coeff = new double[]{0};
		this.exponent = new double[]{0};
	}
	public Polynomial(double[] coeff, double[] exponent){
		this.coeff = coeff;
		this.exponent=exponent;
	}

	public Polynomial add(Polynomial other){
		double[] coeff = new double[(this.coeff.length+other.coeff.length)];
		double[] expo = new double[coeff.length];
		Polynomial longer,shorter;
		
		if (this.coeff.length >= other.coeff.length){
			longer = this;
			shorter = other;
		}else{
			longer=other;
			shorter=this;
		}
		
		for (int i = 0; i < expo.length; i++) {
			expo[i] = -1;
		}
		int pos = 0;
		for (int i = 0;i<longer.coeff.length;i++){
			for (int j = 0; j < shorter.coeff.length; j++) {
				if (longer.exponent[i] == shorter.exponent[j]){
					coeff[pos] = longer.coeff[i]+shorter.coeff[j];
					expo[pos] = longer.exponent[i];
					break;
				}
			}
			if (expo[pos] == -1){
				coeff[pos] = longer.coeff[i];
				expo[pos] = longer.exponent[i];
			}
			pos++;
		}

		boolean found = false;
		for (int i = 0; i < shorter.coeff.length; i++) {
			for (int j = 0; j < pos && !found; j++) {
				if (shorter.exponent[i] == expo[j])
					found = true;
			}
			if (!found) {
				coeff[pos] = shorter.coeff[i];
				expo[pos] = shorter.exponent[i];
				pos++;
			}
		}

		return new Polynomial(coeff,expo);
	}

	public Polynomial scale(double d){
		double[] co = this.coeff.clone();
		for (int i = 0; i < co.length; i++) {
			co[i] *= d;
		}

		return new Polynomial(co,this.exponent.clone());
	}

	public Polynomial multiply(Polynomial other){
		Polynomial output = new Polynomial();
		for (int i = 0; i < this.coeff.length; i++) {
			Polynomial cur = other.scale(this.coeff[i]);

			for (int j = 0; j < cur.exponent.length; j++) {
				cur.exponent[j] += this.exponent[i];
			}
//			System.out.println(Arrays.toString(cur.coeff));
//			System.out.println(Arrays.toString(cur.exponent));
//			System.out.println(Arrays.toString(output.coeff));
//			System.out.println(Arrays.toString(output.exponent));
//			System.out.println();
			output = output.add(cur);
//			System.out.println(Arrays.toString(output.coeff));
//			System.out.println(Arrays.toString(output.exponent));
//			System.out.println("----------");
		}

		System.out.println(Arrays.toString(output.coeff));
		System.out.println(Arrays.toString(output.exponent));
		return output;
	}
	
	public double evaluate(double x){
		double val = 0;
		for(int i = 0;i<this.coeff.length;i++){
			val += coeff[i]*Math.pow(x,exponent[i]);
		}
		return val;
	}

	public boolean hasRoot(double x){
		return (evaluate(x) == 0);
	}
}