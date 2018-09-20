package hdoj;

import java.util.Scanner;

public class GoodLuckinCETEverybody {
	
	
	static boolean solve(int n) {
		boolean[] sg = new boolean[n+1];
		for(int i=0; i<=n; i++) {
			if(!sg[i]) {
				for(int j=0; j<10; j++) {
					int turn = i + (1 << j);
					if(turn <= n) {
						sg[turn] = true;
					}
				}	
			}
		}
		return sg[n];
	}
	

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			if(solve(n)) {
				System.out.println("Kiki");
			}
			else {
				System.out.println("Cici");
			}
		}
		scanner.close();
	}

}
