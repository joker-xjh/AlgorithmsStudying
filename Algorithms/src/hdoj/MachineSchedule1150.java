package hdoj;

import java.util.Arrays;
import java.util.Scanner;

public class MachineSchedule1150 {
	
	static int n, m, k;
	static int[][] graph;
	static boolean[] used;
	static int[] father;
	
	static boolean find(int x) {
		for(int i=1; i<graph[x].length; i++) {
			if(graph[x][i] == 1 && !used[i]) {
				used[i] = true;
				if(father[i] == 0 || find(father[i])) {
					father[i] = x;
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			n = scanner.nextInt();
			if(n == 0)
				break;
			m = scanner.nextInt();
			k = scanner.nextInt();
			graph = new int[n+1][m+1];
			used = new boolean[m+1];
			father = new int[m+1];
			for(int i=0; i<k; i++) {
				scanner.nextInt();
				int a = scanner.nextInt();
				int b = scanner.nextInt();
				graph[a][b] = 1;
			}
			int answer = 0;
			for(int i=1; i<=n; i++) {
				Arrays.fill(used, false);
				if(find(i)) {
					answer++;
				}
			}
			System.out.println(answer);
		}
		
		scanner.close();
	}

}
