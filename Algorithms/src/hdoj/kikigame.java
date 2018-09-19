package hdoj;

import java.util.Scanner;

public class kikigame {
	
	static boolean solve(int n, int m) {
		boolean[][] dp = new boolean[n+1][m+1];
		dp[n][1] = false;
		int[][] dirs = {{0,-1}, {1,0}, {1,-1}};
		for(int j=1; j<=m; j++) {
			for(int i=n; i>=1; i--) {
				boolean state = false;
				for(int[] d : dirs) {
					int x = i + d[0];
					int y = j + d[1];
					if(x<1||x>n||y<1||y>m)
						continue;
					if(!dp[x][y])
						state = true;
				}
				dp[i][j] = state;
			}
		}
		return dp[1][m];
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			if(n == 0 && m == 0)
				break;
			if(m%2==0 || n %2==0) {
				System.out.println("Wonderful!");
			}
			else {
				System.out.println("What a pity!");
			}
		}
		scanner.close();
	}

}
