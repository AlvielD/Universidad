package com.si.util;

public class Permutaciones {

	private final static boolean LEFT_TO_RIGHT = true;
    private final static boolean RIGHT_TO_LEFT = false;
	
	public static int factorial (double numero) {
	  if (numero==0) return 1;
	  else return (int) (numero * factorial(numero-1));
	}
	
	private static int mayor(int a[], int n, int mobile) {
		for (int i = 0; i < n; i++)
			if (a[i] == mobile) return i + 1;
		return 0;
	}
	
	private static int getElementoMovil(int a[], boolean dir[], int n) {
		int movil = 0, antMovil = 0;
		
		for (int i = 0; i < n; i++) {
			if (i != 0 && dir[a[i] - 1] == RIGHT_TO_LEFT)
				if(a[i] > a[i - 1] && a[i] > antMovil)
					antMovil = movil = a[i];
			
			if (i != n - 1 && dir[a[i] - 1] == LEFT_TO_RIGHT)
				if (a[i] > a[i + 1] && a[i] > antMovil)
					antMovil = movil = a[i];
		}
		
		if (movil == 0 && antMovil == 0) return 0;
		else return movil;
	}
	
	private static int[] getUnaPermutacion(int a_original[], boolean dir[], int n) {
		int a[] = new int[a_original.length]; 
		System.arraycopy(a_original, 0, a, 0, a_original.length);
		
		int movil = getElementoMovil(a, dir, n);
		int pos = mayor(a, n, movil);
		
		if (pos > 0 && dir[a[pos - 1] - 1] == RIGHT_TO_LEFT) {
			int temp = a[pos - 1];
			a[pos - 1] = a[pos - 2];
			a[pos - 2] = temp;
		} else if (pos > 0 && dir[a[pos - 1] - 1] == LEFT_TO_RIGHT) {
			int temp = a[pos];
			a[pos] = a[pos - 1];
			a[pos - 1] = temp;
		}
		
		for (int i = 0; i < n; i++) {
			if (a[i] > movil) {
				if (dir[a[i] - 1] == LEFT_TO_RIGHT)
					dir[a[i] - 1] = RIGHT_TO_LEFT;
				else if (dir[a[i] - 1] == RIGHT_TO_LEFT)
					dir[a[i] - 1] = LEFT_TO_RIGHT;
			}
		}
		
		return a;
	}
	
	public static int[][] generarPermutaciones(int n) {
		int fact = factorial(n);
		int devolver[][] = new int[fact][n];
		
		int[] a = new int[n];
        boolean[] dir = new boolean[n];
  
        for (int i = 0; i < n; i++) {
        	a[i] = i + 1;
        	dir[i] = RIGHT_TO_LEFT;
        }
            
		for (int i = 0; i < n; i++)
			devolver[0][i] = i + 1;
				
		for (int i = 1; i < fact; i++)
			devolver[i] = getUnaPermutacion(devolver[i-1], dir, n);
			
		
		return devolver;
	}
	
}
