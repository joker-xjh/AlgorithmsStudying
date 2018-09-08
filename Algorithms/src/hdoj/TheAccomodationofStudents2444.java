package hdoj;

import java.util.Arrays;
import java.util.Scanner;

public class TheAccomodationofStudents2444 {
	
	static int[][] graph;
	
	static boolean DFS(int index, int[] color, int tag) {
		color[index] = tag;
		for(int i=1; i<graph.length; i++) {
			if(graph[index][i] == 1) {
				if(color[i] == tag)
					return false;
				if(color[i] == 0 && ! DFS(i, color, -tag))
					return false;
			}
		}
		return true;
	}
	
	static boolean find(int x, boolean[] used, int[] father) {
		for(int i=1; i<graph.length; i++) {
			if(graph[x][i] == 1 && !used[i]) {
				used[i] = true;
				if(father[i] == 0 || find(father[i], used, father)) {
					father[i] = x;
					return true;
				}
			}
		}
		return false;
	}
	

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		outer:
		while(scanner.hasNext()) {
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			graph = new int[n+1][n+1];
			int[] color = new int[n+1];
			for(int i=0; i<m; i++) {
				int a = scanner.nextInt();
				int b = scanner.nextInt();
				graph[a][b] = 1;
				graph[b][a] = 1;
			}
			for(int i=1; i<=n; i++) {
				if(color[i] != 0)
					continue;
				if(!DFS(i, color, 1)) {
					System.out.println("No");
					continue outer;
				}
			}
			
			boolean[] used = new boolean[n+1];
			int[] father = new int[n+1];
			int pairs = 0;
			for(int i=1; i<=n; i++) {
				Arrays.fill(used, false);
				if(find(i, used, father))
					pairs++;
			}
			System.out.println(pairs/2);
		}
		
		scanner.close();
	}

}
