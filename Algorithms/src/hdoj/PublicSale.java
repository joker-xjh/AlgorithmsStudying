package hdoj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PublicSale {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int m = scanner.nextInt();
			int n = scanner.nextInt();
			if(m % (n+1) == 0) {
				System.out.println("none");
				continue;
			}
			List<Integer> answer = new ArrayList<>();
			for(int i=1; i<=n; i++) {
				int temp = m - i;
				if(temp <= 0) {
					answer.add(i);
					continue;
				}
				if(temp % (n+1) == 0) {
					answer.add(i);
				}
			}
			for(int i=0; i<answer.size(); i++) {
				if(i == answer.size()-1) {
					System.out.println(answer.get(i));
				}
				else {
					System.out.print(answer.get(i)+" ");
				}
			}
		}
		
		scanner.close();
	}

}
