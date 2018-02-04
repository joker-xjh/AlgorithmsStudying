package lintcode;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hard {
	
	public List<Map.Entry<Integer, Double>> dicesSum(int n) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<>();
        long[][] dp = new long[n+1][6*n+1];
        double total = Math.pow(6, n);
        dp[1][1] = 1; dp[1][2] = 2; dp[1][3] = 1; dp[1][4] = 1; dp[1][5] = 1; dp[1][6] = 1;
        for(int i=2; i<=n; i++) {
        	for(int j=i; j<= i* 6; j++) {
        		if(j - 1 > 0) {
        			dp[i][j] += dp[i-1][j-1];
        		}
        		if(j - 2 > 0) {
        			dp[i][j] += dp[i-1][j-2];
        		}
        		if(j - 3 > 0) {
        			dp[i][j] += dp[i-1][j-3];
        		}
        		if(j - 4 > 0) {
        			dp[i][j] += dp[i-1][j-4];
        		}
        		if(j - 5 > 0) {
        			dp[i][j] += dp[i-1][j-5];
        		}
        		if(j - 6 > 0) {
        			dp[i][j] += dp[i-1][j-6];
        		}
        	}
        }
        for(int i=n; i<= n*6; i++) {
        	list.add(new AbstractMap.SimpleEntry<Integer, Double>(i, dp[n][i] / total));
        }
        return list;
    }

}
