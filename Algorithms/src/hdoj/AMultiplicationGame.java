package hdoj;

import java.util.Scanner;

public class AMultiplicationGame {
	
	static long[] array1 = new long[10];
	static long[] array2 = new long[10];
	
	static void init() {
		array1[0] = 9;
		array2[0] = 18;
		for(int i=1; i<=9; i++) {
			array1[i] = array1[i-1] * 18;
			array2[i] = array2[i-1] * 18;
		}
	}
	static boolean lose(long x) {
		for(int i=0; i<10; i++) {
			if(x>array1[i] && x<= array2[i])
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		init();
		while(scanner.hasNext()) {
			long n = scanner.nextLong();
			if(lose(n)) {
				System.out.println("Ollie wins.");
			}
			else {
				System.out.println("Stan wins.");
			}
		}
		
		scanner.close();
	}

}
