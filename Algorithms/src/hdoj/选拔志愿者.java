package hdoj;

import java.util.Scanner;

public class 选拔志愿者 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int T = scanner.nextInt();
			while(T-- > 0) {
				int n = scanner.nextInt();
				int m = scanner.nextInt();
				if(m >= n) {
					System.out.println("Grass");
					continue;
				}
				if(n % (m+1) == 0 ) {
					System.out.println("Rabbit");
				}
				else {
					System.out.println("Grass");
				}
			}
		}
		
		scanner.close();
	}

}
