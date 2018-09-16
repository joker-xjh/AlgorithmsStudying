package hdoj;

import java.util.Scanner;

public class BeingaGoodBoyinSpringFestival {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int m = scanner.nextInt();
			if(m == 0)
				break;
			int[] array = new int [m];
			int xor = 0;
			for(int i=0; i<m; i++) {
				array[i] = scanner.nextInt();
				xor ^= array[i];
			}
			int answer = 0;
			if(xor != 0) {
				for(int i=0; i<m; i++) {
					int temp = array[i] ^ xor;
					if(temp < array[i]) {
						answer++;
					}
				}
			}
			System.out.println(answer);
			
		}
		scanner.close();
	}

}
