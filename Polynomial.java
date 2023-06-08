import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.util.Arrays.copyOfRange;

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

	public Polynomial(File f) throws FileNotFoundException {
		Scanner s = new Scanner(f);
		String eqn = s.nextLine();
		String[] terms = eqn.split("(?=-)|(?=\\+)");
		coeff = new double[terms.length];
		exponent = new double[terms.length];

		for (int i = 0; i < terms.length; i++) {
			String term = terms[i];
			String[] parts = term.split("(?=[a-zA-Z])");
			coeff[i] = parseDouble(parts[0]);

			if (parts.length > 1){
				if (parts[1].length() > 1){
					exponent[i] = parseDouble(parts[1].substring(1));
				}else{
					exponent[i] = 1;
				}
			}else{
				exponent[i] = 0;
			}
		}
	}

	public String getTerm(int i){
		StringBuilder s = new StringBuilder();
		//Remove unnecessary trailing zeros
		DecimalFormat f = new DecimalFormat("0.#");

		s.append(f.format(coeff[i]));
		if (this.exponent[i] >= 1){
			s.append("x");
			if (this.exponent[i] > 1){
				s.append(f.format(exponent[i]));
			}
		}

		return String.valueOf(s);
	}
	public void saveToFile(String name) throws FileNotFoundException {
		StringBuilder s = new StringBuilder();
		PrintWriter p = new PrintWriter(new File(name));
		s.append(getTerm(0));

		for (int i = 1; i < this.coeff.length; i++) {
			if (coeff[i] >=0)
				s.append("+");
			s.append(getTerm(i));
		}

		p.print(s);
		p.close();
	}

	public Polynomial add(Polynomial other){
		double[] coeff = new double[this.coeff.length+other.coeff.length];
		double[] expo  = new double[coeff.length];
		int pos = 0;
		boolean found = false;
		Arrays.fill(expo,-1);

		for (int i = 0; i < this.coeff.length; i++) {
			coeff[i] = this.coeff[i];
			expo[i] = this.exponent[i];
			pos++;
		}

		for (int i = 0; i < other.coeff.length; i++) {
			found = false;
			for (int j = 0; j < coeff.length; j++) {
				if (other.exponent[i] == expo[j]){
					coeff[j] += other.coeff[i];
					found = true;
				}
			}
			if (!found){
				coeff[pos] = other.coeff[i];
				expo[pos] = other.exponent[i];
				pos++;
			}
		}

		coeff = copyOfRange(coeff,0,pos);
		expo = copyOfRange(expo,0,pos);



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

			output = output.add(cur);
		}

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