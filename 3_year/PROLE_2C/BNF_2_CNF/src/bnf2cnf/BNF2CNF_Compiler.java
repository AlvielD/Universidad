
package bnf2cnf;

import java.io.*;

import bnf2cnf.ast.Gramatica;
import bnf2cnf.chomskyGenerator.chomskyGenerator;
import bnf2cnf.parser.*;


public class BNF2CNF_Compiler {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// Busca el directorio de trabajo
		String path = (args.length == 0 ? System.getProperty("user.dir") : args[0]);
		File workingdir = new File(path);
		
		Gramatica ast = null;			// �rbol de sintaxis abstracta de la gram�tica introducida
		Gramatica astCNF = null;		// �rbol de sintaxis abstracta de la gram�tica traducida
		
		File mainfile = null;			// Fichero main
		FileInputStream fis = null;		// Entrada de texto para el parser
		BNF2CNF_Parser parser = null;	// Parser de la aplicaci�n
		
		try
		{
			mainfile = new File(workingdir, "Main.bnc");	// Inicializamos el fichero main
			fis = new FileInputStream(mainfile);			// A�adimos el fichero main como entrada de texto
			
			parser = new BNF2CNF_Parser(fis);				// Analizamos la entrada de texto
			
			ast = parser.Gramatica();						// Generamos el �rbol de sintaxis abstracta a partir de la gram�tica introducida
			astCNF = chomskyGenerator.generarCNF(ast);		// Traducimos la gram�tica a forma normal de Chomsky y obtenemos su AST
			
			if (parser.getErrorCount() > 0) 
			{
				printError(workingdir,parser.getErrorCount(), parser.getErrorMsg());
			}
			else
				printOutput(workingdir, astCNF);	// Sacamos por el fichero de texto la gram�tica traducida
		} 
		catch (FileNotFoundException ex1) 
		{
			printError(workingdir, 1, "File Main.bnc doesn't exist.");
		}
		catch(Exception ex) 
		{
			printError(workingdir, parser.getErrorCount(), parser.getErrorMsg());
		}
	}
	
	/**
	 * Genera el fichero de error
	 * @param workingdir Directorio de trabajo
	 * @param count N�mero de errores obtenidos
	 * @param msg Mensaje de error a mostrar
	 */
	private static void printError(File workingdir, int count, String msg) 
	{
		try 
		{
			FileOutputStream errorfile =  new FileOutputStream(new File(workingdir, "BNF2CNF_Errors.txt"));
			PrintStream errorStream = new PrintStream(errorfile);
			errorStream.println("[File Main.bnc] "+count+" error"+(count>0?"s":"")+" found:");
			errorStream.println(msg);
			errorStream.close();
			System.out.println(msg);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Genera el fichero de salida
	 * @param workingdir Directorio de trabajo
	 * @param G Gram�tica a mostrar en la salida
	 */
	private static void printOutput(File workingdir, Gramatica G) 
	{
		try 
		{
			FileOutputStream outputfile =  new FileOutputStream(new File(workingdir, "BNF2CNF_Output.txt"));
			PrintStream stream = new PrintStream(outputfile);
			
			if (G == null) {	// Si la gram�tica no se gener� correctamente imprimimos un error
				stream.println("Error inexperado.");
			} else {			// Si la gram�tica se gener� correctamente, la sacamos por el fichero de texto
				stream.println(G.toString());
			}
			stream.close();		// Cerramos el fichero para guardar los cambios
		}
		catch(Exception ex)
		{
		}
	}
}
