public class Complex {

	double real;
	double imaginary;
	
	public static final Complex ONE = new Complex(1,0);
	public static final Complex I = new Complex(0,1);
	public static final Complex I_NEG = new Complex(0,-1);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex ZERO = new Complex(0,0);
	
	public Complex(double real,double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	public String toString() {
		if(imaginary == 0) {
			return(real+"");
		}else {
			if(imaginary==1) return(real+" +i");
			else if (imaginary==(-1))return(real+" -i");
			else return (real+" + "+imaginary+"i");
		}
		
	}
	
	private boolean isRealPrime(int num) {
		
		num = Math.abs(num);
		
		if (num==1) return false;
		else if(num == 2) return true;
		else if(num == 3) return true;
		
		for(int i = 2; i <= Math.ceil(num/2);i++) {
			if(num % i == 0) return false;
		}
		
		return true;
	}
	
	public boolean isPrime() {
		
		if(real==0 && isRealPrime((int) Math.abs(imaginary))) {
			if((Math.abs(imaginary) - 3) / 4 == Math.round((Math.abs(imaginary) - 3) / 4))return true;
			//else return false;
		}
		else if(imaginary==0 && isRealPrime((int) Math.abs(real))) {
			if((Math.abs(real) - 3) / 4 == Math.round((Math.abs(real) - 3) / 4))return true;
			//else return false;
		}
		else if(isRealPrime((int) Math.pow(real, 2) + (int) Math.pow(imaginary, 2))) return true;
		
		return false;
	}
	
	public double modulus() {
		double mod = Math.pow(real, 2) + Math.pow(imaginary, 2);
		return(Math.sqrt(mod));
	}
	
	public double args() {
		if(real != 0) return Math.atan(imaginary / real);
		else if (imaginary >= 0) return -1 * Math.PI / 2;
		else return Math.PI / 2;
	}
	
	public Complex add_inv() {
		Complex c = new Complex(-1 * real,-1 * imaginary);
		return c;
	}
	
	public Complex conjugate() {
		Complex a = new Complex(real,-1 * imaginary);
		return a;
	}
	
	public Complex add(Complex a) {
		Complex b = new Complex(real + a.real,imaginary + a.imaginary);
		return b;
	}
	
	public boolean equals(Complex a) {
		if(real==a.real && imaginary==a.imaginary) return true;
		else return false;
	}
	
	public Complex multiply(Complex num) {
		double Real = real*num.real - num.imaginary*imaginary;
		double Imaginary = real*num.imaginary + num.real*imaginary;
		Complex number = new Complex(Real,Imaginary);
		return number;
	}
	
	public Complex scalarMult(double scalar) {
		Complex num = new Complex(scalar*real, imaginary*scalar);
		return num;
	}
	
	public Complex inverse() {
		
		double Real = real / Math.pow(modulus(),2);
		double Imaginary = -1*imaginary / Math.pow(modulus(),2);

		return new Complex(Real,Imaginary);
	}
	
	public Complex euclid_alg(Complex num) { // euclid_alg is programmed to be commutative
		//a.euclid(b) == b.euclid(a)
		// a = mb + r if |a| > |b|, a.euclid(b) -> r
		//b = na + t if |b| > |a|, a.euclid(b) -> t
		//euclidian division algorithm only works for integers,i.e.3 + 4i not 3.24 + 6.7i
		//this ring is Z[i], which is contained in the field of complex numbers
		if(num.modulus() > this.modulus()) {
			
			Complex number = num.multiply(this.conjugate()).scalarMult(1/Math.pow(this.modulus(), 2));
			int m_real = (int) Math.round(number.real);
			int m_imag = (int) Math.round(number.imaginary);
			
			Complex M = new Complex(m_real,m_imag);
			number = M.multiply(this).add_inv().add(num);
			
			return number;
		}else {
			Complex number = this.multiply(num.conjugate()).scalarMult(1/Math.pow(num.modulus(), 2));
			int m_real = (int) Math.round(number.real);
			int m_imag = (int) Math.round(number.imaginary);
			
			Complex M = new Complex(m_real,m_imag);
			number = M.multiply(num).add_inv().add(this);
			
			return number;
		}
		
		///return numerator;
	}
	
	public boolean isUnit() {
		if(this.equals(ONE)||this.equals(I)||this.equals(I_NEG)||this.equals(ONE_NEG)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isGCD(Complex a, Complex b) {
		
		if(this.equals(a.GCD(b))) return true;
		else if(this.multiply(I).equals(a.GCD(b))) return true;
		else if(this.multiply(I_NEG).equals(a.GCD(b))) return true;
		else if(this.multiply(ONE_NEG).equals(a.GCD(b))) return true;
		
		return false;
	}
	
	public Complex GCD(Complex z) {
		
		//if(z.equals(ONE))return this;
		Complex remainder = this.euclid_alg(z);//System.out.println(number);
		Complex new_remainder = remainder; Complex multiple;
		
		if(this.modulus() > z.modulus()) multiple = z;
		else multiple = this;
		
		while(!new_remainder.isUnit() && !new_remainder.equals(ZERO)) {
		new_remainder = multiple.euclid_alg(remainder);
		multiple = remainder;
		remainder = new_remainder; //System.out.println(new_remainder);
		}
	if(new_remainder.equals(ZERO)) return multiple;
	return remainder;
	}
	
}
