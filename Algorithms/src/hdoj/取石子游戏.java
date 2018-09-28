package hdoj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 取石子游戏 {
	
	static List<Integer> F;
	static void init() {
		F = new ArrayList<>();
		F.add(1);
		F.add(2);
		for(int i=1; i<=43; i++) {
			int one = F.get(F.size()-1);
			int two = F.get(F.size()-2);
			F.add(one + two);
		}
	}

	public static void main(String[] args) {
		init();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			if(n == 0)
				break;
			if(F.contains(n)) {
				System.out.println("Second win");
			}
			else {
				System.out.println("First win");
			}
		}
		
		scanner.close();
	}

}
