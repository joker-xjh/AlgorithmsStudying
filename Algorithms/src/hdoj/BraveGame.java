package hdoj;

import java.util.Scanner;

public class BraveGame {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int C = scanner.nextInt();
			for(int i=0; i<C; i++) {
				int n = scanner.nextInt();
				int m = scanner.nextInt();
				if(n % (m+1) == 0) {
					System.out.println("second");
				}
				else {
					System.out.println("first");
				}
			}
		}
		
		scanner.close();
	}

}
