package topcoder;

public class AB {
	
	public String createString(int N, int K) {
		int[] array = new int[N];
		StringBuilder sb = new StringBuilder();
		boolean success = false;
		array[N-1] = 1;
		if(K > 0) {
			int sub = 1;
			while(K > 0) {
				for(int i=N-sub-1; i>=0; i--) {
					array[i+1] = 0;
					if(array[i] == 1)
						break;
					array[i] = 1;
					K--;
					if(K == 0) {
						success = true;
						break;
					}
				}
				sub++;
			}
		}
		else
			success = true;
		if(success) {
			for(int num : array)
				if(num == 1)
					sb.append('A');
				else
					sb.append('B');
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		AB test = new AB();
		System.out.println(test.createString(3, 2));
	}

}
