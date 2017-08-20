package Algorithms;

public class Sort {
	
	public static void bubbleSort(int[] array) {
		int length = array.length;
		int temp = 0;
		boolean b = false;
		for(int i=0; i<length-1; i++) {
			for(int j=0; j<length-i-1;j++) {
				if(array[j] > array[j+1]) {
					temp = array[j];
					array[j] = array[j+1];
					array[j+1] = temp;
					b = true;
				}
			}
			if(!b)
				break;
			//Hashtable
			//HashMap
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
