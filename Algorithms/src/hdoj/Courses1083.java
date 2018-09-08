package hdoj;

import java.util.Arrays;
import java.util.Scanner;

public class Courses1083 {
	
	static boolean find(int x, int[][] graph, boolean[] used, int N, int[] father) {
		for(int i=1; i<=N; i++) {
			if(graph[x][i] == 1 && !used[i]) {
				used[i] = true;
				if(father[i] == 0 || find(father[i], graph, used, N, father)) {
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
			int T = scanner.nextInt();
			while(T-- > 0) {
				int P = scanner.nextInt();
				int N = scanner.nextInt();
				int[][] graph = new int[P+1][N+1];
				boolean[] used = new boolean[N+1];
				int[] father = new int[N+1];
				int courses = 0;
				for(int i=1; i<=P; i++) {
					int count = scanner.nextInt();
					for(int j=0; j<count; j++) {
						int student = scanner.nextInt();
						graph[i][student] = 1;
					}
				}
				for(int i=1; i<=P; i++) {
					if(find(i, graph, used, N, father)) {
						courses++;
					}
					Arrays.fill(used, false);
				}
				if(courses == P) {
					System.out.println("YES");
				}
				else {
					System.out.println("NO");
				}
			}
		}
		scanner.close();
	}

}
