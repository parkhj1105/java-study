package day5;

public class Sample {

	public Sample() {
		System.out.println("コンストラクタ");
	}
	public static void foo() {
		System.out.println("ンスタンスメソッド");
	}
	public static void bar() {
		System.out.println("静的メソッド");
	}
	public static void main(String[] args) {
		Sample i = new Sample();
		Sample.foo();
		Sample.bar();
		bar();

		System.gc();

	}

}
