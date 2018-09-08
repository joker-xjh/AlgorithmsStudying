package hdoj;

import java.util.Scanner;

public class 过山车2063 {
	
	static int[][] line;
	static int[] used;
	static int[] boy;
	static int k, m, n;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextInt()) {
			k = scanner.nextInt();
			if(k == 0)
				break;
			m = scanner.nextInt();
			n = scanner.nextInt();
			used = new int[505];
			boy = new int[505];
			line = new int[505][505];
			int a, b, sum = 0;
			for(int i=0; i<k; i++) {
				a = scanner.nextInt();
				b = scanner.nextInt();
				line[a][b] = 1;
			}
			for(int i=1; i<=m; i++) {
				for(int j=1; j<=n; j++) {
					used[j] = 0;
				}
				if(find(i)) {
					sum++;
				}
			}
			System.out.println(sum);
		}
		scanner.close();
	}
	
	private static boolean find(int x) {
		for(int j=1; j<=n; j++) {
			if(line[x][j] == 1 && used[j] == 0) {
				used[j] = 1;
				if(boy[j] == 0 || find(boy[j])) {
					boy[j] = x;
					return true;
				}
			}
		}
		return false;
	}

}
