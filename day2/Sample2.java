package day2;

import java.util.Scanner;

public class Sample2 {

	public void main(String[] args) {
		Scanner sc = new Scanner (System. in);

		System.out.println("1~7入力");
		int dice = sc.nextInt();

		System.out.println("さいころの目:"+dice);

		if (1 <= dice && dice <= 6) {

			if (dice % 2 == 0 ) {
				//（ｄｉｓｅ　％　２　＝＝　０　）
				System.out.println("丁です");
			}else{
				System.out.println("半です");
			}
		}else{
			System.out.println("範囲外の数直です");
			}



		}

	}
