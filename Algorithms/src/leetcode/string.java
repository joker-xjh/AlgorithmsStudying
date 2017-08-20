package leetcode;



public class string {
	
	
    public boolean judgeCircle(String moves) {
        if(moves == null || moves.length()==0)
        	return false;
        int x = 0, y = 0;
        for(char c:moves.toCharArray()) {
        	if( c == 'L') {
        		x--;
        	}
        	else if(c =='R') {
        		x++;
        	}
        	else if(c == 'U') {
        		y++;
        	}
        	else {
        		y--;
        	}
        }
        if(x == 0 && y == 0)
        	return true;
        
        
        return false;
    }
    
    
    public String getHint(String secret, String guess) {
        int a = 0, b = 0;
        char[] secretArray = secret.toCharArray();
        char[] guessArray = guess.toCharArray();
        int length = secret.length();
        int[] map = new int[10];
        for(int i=0; i<length; i++) {
        	char c1 = secretArray[i];
        	char c2 = guessArray[i];
        	if(c1 == c2) {
        		a++;
        	}
        	else
        		map[c1-'0']++;
        }
        	
        for(int i=0; i<length;i ++) {
        	char c1 = guessArray[i];
        	char c2 = secretArray[i];
        	if(c1 != c2) {
        		int count = map[c1-'0'];
        		if(count > 0) {
        			b++;
        			map[c1-'0']--;
        		}
        	}
        	
        }
        
        return a+"A"+b+"B";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
}
