package day3;

public class Hukusyuu {

    public static void main(String[] args) {

    	int num = 1;
    	switch(num) {
    	case 1:
    		System.out.println("one");
    		break; //이게 없으면 브레이크 걸리지않고 계속 진행. 해당 브레이크가 없을시 one , two가 출력됨.
    	case 2:
    		System.out.println("two");
    		break;
    	case 3:
    		System.out.println("three");
    		break;
		default:
    		System.out.println("x");
    	}
    }
}