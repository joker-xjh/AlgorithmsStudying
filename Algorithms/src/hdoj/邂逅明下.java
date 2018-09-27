package hdoj;

import java.util.Scanner;

public class 邂逅明下 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int p = scanner.nextInt();
			int q = scanner.nextInt();
			int mod = n % (p + q);
			if(mod == 0 || mod > p) {
				System.out.println("WIN");
			}
			else {
				System.out.println("LOST");
			}
		}
		
		scanner.close();
	}

}
