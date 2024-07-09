package day5;

public class Test {

	public static void main(String[] args) {
		int a = (int) (Math.random() * 10) + 1;
		int b = (int) (Math.random() * 10) + 1;
		int result = add(a,b);
		int resulte = addy(a,b);
		int ras = addey(a,b);
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println(a + " + " + b + " = " + result);
		System.out.println(a + " - " + b + " = " + resulte);
		System.out.println(a + " x " + b + " = " + ras);
	}


	static int add(int a,int b) {
		return a + b;
	}
	static int addy(int a,int b) {
		return a - b;
	}
	static int addey(int a,int b) {
		return a * b;
	}
}

