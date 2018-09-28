package hdoj;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ASimpleNim {
	
	static void table() {
		int N = 100;
		int[] sg = new int[N+1];
		Set<Integer> set = new HashSet<>();
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=i; j++) {
				set.add(sg[i-j]);
				for(int k=1; k+j<i; k++) {
					set.add(sg[j] ^ sg[k] ^ sg[i-j-k]);
				}
			}
			int counter = 0;
			while(set.contains(counter))
				counter++;
			sg[i] = counter;
			set.clear();
		}
		for(int i=0; i<=N; i++) {
			System.out.println(i+":"+sg[i]);;
		}
		
	}
	
	static int sg(int x) {
		if(x % 8 == 0)
			return x-1;
		if(x % 8 == 7)
			return x+1;
		return x;
	}
	

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int T = scanner.nextInt();
			while(T-- > 0) {
				int N = scanner.nextInt();
				int xor = 0;
				for(int i=0; i<N; i++) {
					xor ^= sg(scanner.nextInt());
				}
				if(xor > 0) {
					System.out.println("First player wins.");
				}
				else {
					System.out.println("Second player wins.");
				}
			}
		}
		scanner.close();
	}

}
