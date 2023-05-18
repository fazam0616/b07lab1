public class Polynomial{
	public double[] coeff;
	public Polynomial(){
		this.coeff = new double[]{0};
	}
	public Polynomial(double[] nums){
		this.coeff = nums;
	}

	public Polynomial add(Polynomial other){
		double[] newP = new double[Math.max(this.coeff.length,other.coeff.length)];
		for (int i = 0;i<newP.length;i++){
			if (i<this.coeff.length){
				newP[i] += this.coeff[i];
			}
			if (i<other.coeff.length){
				newP[i] += other.coeff[i];
			}
		}
		return new Polynomial(newP);
	}
	
	public double evaluate(double x){
		double val = 0;
		for(int i = 0;i<this.coeff.length;i++){
			val += this.coeff[i]*Math.pow(x,i);
		}
		return val;
	}

	public boolean hasRoot(double x){
		return (evaluate(x) == 0);
	}
}