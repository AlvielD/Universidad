package si2021.p02.jesusvaleo679;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CalcularMedia {
	
	public static float calcularMedia(final String nombrebase, final int numPartidas) {
		float suma = 0;
		
		for (int i = 0; i < numPartidas; i++) {
			try {
			File file = new File("partidas/"+nombrebase+i+".txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			br.close();
			fr.close();
			file.delete();
			String[] parts = line.split(" ");
			
			suma += Float.parseFloat(parts[2]);
			
			} catch (Exception e) {
				System.err.println("Error al leer el archivo: " + nombrebase + i + ".txt");
			}
		}
		
		return (suma / numPartidas);
	}
	
}
