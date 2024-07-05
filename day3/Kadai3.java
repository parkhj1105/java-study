package day3;

import java.util.Scanner;

public class Kadai3 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("整数を入力してください： ");
		int number = scanner.nextInt();

		if (number % 2 == 0){
			System.out.println("偶数です");
		}else{
			System.out.println("奇数です");

		}

	}
}
