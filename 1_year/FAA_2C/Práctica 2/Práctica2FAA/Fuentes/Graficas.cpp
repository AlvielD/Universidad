/* 
 * Clase Graficas, contiene métodos crear los ficheros de comandos para dibujar 
 * la gráfica que corresponda. 
 */
#include "Graficas.h"
#include "Constantes.h"
#include <fstream>
#include <iostream>
using namespace std;

/*
 * Método generarGraficaMEDIO, genera el fichero de comandos para GNUPlot 
 * y dibuja la gráfica del caso medio de un método de
 * ordenación y su ajuste a la función correspondiente.  
 * param nombre_metodo: metodo de ordenacion.
 * param orden: Orden del metodo de ordenacion.
 */
void Graficas::generarGraficaMEDIO(string nombre_metodo ,int orden)
{
	ofstream fplot("CmdMedio.gpl");

	fplot << "#Caso medio para un metodo" << endl;
	fplot << "set title \" " << nombre_metodo << " \" " << endl;
	fplot << "set key top left vertical inside" << endl;
	fplot << "set grid " << endl;
	fplot << "set xlabel \"Talla (n)\" " << endl;
	fplot << "set ylabel \"Tiempo (ms)\" " << endl;
	switch (orden)
	{
	case 0:
	{
		fplot << "Cuadrado(x) = a*x**2 + b*x + c " << endl;
		fplot << "fit Cuadrado(x) \"" << nombre_metodo << "Medio.dat\" using 1:2 via a,b,c " << endl;
		fplot << "plot \"" << nombre_metodo << "Medio.dat\" using 1:2 title \"" << nombre_metodo << "\", Cuadrado(x) " << endl;

		break;
	}
	case 1:
	{
		fplot << "NlogN(x) = a*x*log(x) + b*x + c " << endl;
		fplot << "fit NlogN(x) \"" << nombre_metodo << "Medio.dat\" using 1:2 via a,b,c " << endl;
		fplot << "plot \"" << nombre_metodo << "Medio.dat\" using 1:2 title \"" << nombre_metodo << "\", NlogN(x) " << endl;

		break;
	}
	}

	fplot << "set terminal pdf " << endl;
	fplot << "set output \"" << nombre_metodo << ".pdf\"" << endl;
	fplot << "replot " << endl;
	fplot << "pause -1 \"Pulsa Enter para continuar...\"";

	fplot.close();

	char opc;
	cout << endl << "Generar grafica de resultados? (s/n): ";
	cin >> opc;
	switch (opc) {
	case 's':
	case 'S': {
		system("start CmdMedio.gpl");
		cout << endl << "Grafica guardada en el fichero " << nombre_metodo + ".pdf" << endl;
	}break;
	default: {cout << "Grafica no guardada en fichero " << endl;
	}break;
	}
}	

/*
 * Método generarGraficaCMP, genera el fichero de comandos para GNUPlot.
 * param nombre1: es el nombre del fichero de datos del primer método de ordenación 
 * param nombre2: es el nombre del fichero de datos del segundo método de ordenación 
 */
void  Graficas::generarGraficaCMP(string nombre1,string nombre2)
{
	ofstream fplot("CmdCompara.gpl");

	fplot << "#ESTE ES UN SCRIPT DE GNUPLOT PARA LA COMPARACIÓN DE DOS METODOS DE ORDENACION" << endl <<
		"set title \"Comparacion de los metodos " + nombre1 + " & " + nombre2 + "\"" << endl <<
		"set key top left vertical inside" << endl <<
		"set grid" << endl <<
		"set xlabel \"Talla (n)\"" << endl <<
		"set ylabel \"Tiempo (ms)\"" << endl << endl <<
		"plot \"" + nombre1 + "Medio.dat\" using 1:2 with lines title \"" + nombre1 + "\", \"" + nombre2 + "Medio.dat\" using 1:2 with lines title \"" + nombre2 + "\"" << endl <<
		"set terminal pdf" << endl <<
		"set output \"CMP" + nombre1 + nombre2 + ".pdf\"" << endl <<
		"replot" << endl <<
		"pause -1 \"Pulsa Enter para continuar...\"";

	fplot.close();

	char opc;
	cout << endl << "Generar grafica de resultados? (s/n): ";
	cin >> opc;
	switch (opc) {
	case 's':
	case 'S': {
		system("start CmdCompara.gpl");
		cout << endl << "Grafica guardada en el fichero CMP" + nombre1 + nombre2 + ".pdf" << endl;
	}break;
	default: {cout << "Grafica no guardada en fichero " << endl;
	}break;
	}
	cout << endl;
	system("pause");
	system("cls");
}