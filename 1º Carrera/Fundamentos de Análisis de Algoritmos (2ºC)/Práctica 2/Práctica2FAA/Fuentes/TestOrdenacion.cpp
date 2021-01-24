/* 
 * La clase TestOrdenacion contiene los metodos para:
 * 1. Comprobar que los métodos de ordenacion de la clase Ordenacion funcionan adecuadamente.
 * 2. Calcular la eficiencia para el caso medio de un método de ordenación,
 *    guardando los datos y permitiendo imprimir la gráfica correspondiente 
 * 3. Comparar el coste temporal de dos de los métodos de ordenación 
 *    burbuja, inserción, y selección, guardando los datos y permitiendo imprimir la gráfica correspondiente.
 */
#include "AlgoritmosOrdenacion.h"
#include "TestOrdenacion.h"
#include "ConjuntoInt.h"
#include "Graficas.h"
#include "Mtime.h"
#include "Constantes.h"
#include <string>
#include <fstream>
#include <iomanip>
#include <iostream>
using namespace std;


TestOrdenacion::TestOrdenacion()
{
	nombreAlgoritmo.push_back("Burbuja");
	nombreAlgoritmo.push_back("Insercion");
	nombreAlgoritmo.push_back("Seleccion");
} 
TestOrdenacion::~TestOrdenacion(){}

/*
 * Ordena un array de enteros según el método indicado
 * param v: el array de enteros a ordenar
 * param size: tamaño del array de enteros a ordenar
 * param metodo: Metodo de ordenacion a utilizar
 * return Tiempo empleado en la ordenación (en milisegundos)
 */
double TestOrdenacion::ordenarArrayDeInt(int v[],int size,int metodo) 
{
	AlgoritmosOrdenacion estrategia;
	Mtime t;
	LARGE_INTEGER t_inicial, t_final;
	QueryPerformanceCounter(&t_inicial);
	// Invoca al método de ordenación elegido
	switch (metodo){
		case BURBUJA: estrategia.ordenaBurbuja(v, size);
			break;
		case INSERCION: estrategia.ordenaInsercion(v, size);
			break;
		case SELECCION: estrategia.ordenaSeleccion(v, size);
			break;
	}
	QueryPerformanceCounter(&t_final);
	return t.performancecounter_diff(&t_final, &t_inicial) * 1000;   
}

/*
 * Comprueba los metodos de ordenacion
 */
void TestOrdenacion::comprobarMetodosOrdenacion()
{
	int talla;
	cout<<endl<<endl<<"Introduce la talla: ";
	cin>>talla; 
	system("cls"); 
	for (int metodo = 0; metodo < nombreAlgoritmo.size(); metodo++)
	{
		ConjuntoInt *v= new ConjuntoInt(talla); 
		v->GeneraVector(talla);
		cout <<endl<<endl<< "vector inicial para el metodo "<<nombreAlgoritmo[metodo]<< ":"<<endl<<endl;
		v->escribe();
		double secs=ordenarArrayDeInt(v->getDatos(),talla,metodo);
		cout<<endl<<endl<<"Array ordenado con metodo "<<nombreAlgoritmo[metodo]<< ":"<<endl<<endl;
		v->escribe();
		cout<<endl;
		v->vaciar(); 
		system("pause");
		system("cls");
	} /* fin del for */
	system("cls");
}
    
/*
 * Calcula el caso medio de un metodo de ordenacion,
 * Permite las opciones de crear el fichero de datos y la gráfica correspondiente.
 * param metodo: metodo de ordenacion a estudiar.
 */
void TestOrdenacion::casoMedio(int metodo)
{
	Graficas G;
	double sumatiempo;
	ofstream f(nombreAlgoritmo[metodo] + "Medio.dat");

	cout << "\tTalla" << "\t\tTiempo" << endl;

	if (f)
	{
		for (int talla = tallaIni; talla <= tallaFin; talla += incTalla)
		{
			sumatiempo = 0;
			for (int i = 0; i < NUMREPETICIONES; i++)
			{
				ConjuntoInt *v = new ConjuntoInt(talla);
				v->GeneraVector(talla);
				double secs = ordenarArrayDeInt(v->getDatos(), talla, metodo);
				v->vaciar();
				delete v;
				sumatiempo += secs;
			}
			cout << "\t" << talla << "\t\t" << (sumatiempo) / NUMREPETICIONES << "\n";
			f << talla << "\t" << (sumatiempo) / NUMREPETICIONES << "\n";
		}
		f.close();
		cout << "Datos guardados en el fichero " << nombreAlgoritmo[metodo] << "Medio.dat" << endl;
	}
	else
		cout << "Error al en la apertura del archivo";

	G.generarGraficaMEDIO(nombreAlgoritmo[metodo], CUADRADO);
	//Parametrizar el orden de la función para la AAD.
}
/*
 * Compara dos metodos de ordenacion. 
 * Genera el fichero de datos y permite las opcion de crear la gráfica correspondiente.
 * param metodo1: Primer metodo de ordenacion a comparar
 * param metodo2: Segundo metodo de ordenacion a comparar
 */
void TestOrdenacion::comparar(int metodo1, int metodo2)
{
	Graficas G;
	double sumatiempo;
	ofstream f("Compara" + nombreAlgoritmo[metodo1] + nombreAlgoritmo[metodo2] + ".dat");

	cout << "\tTalla\t\tTiempo " + nombreAlgoritmo[metodo1] + "\t\tTiempo " + nombreAlgoritmo[metodo2] << endl;

	if (f)
	{
		for (int talla = tallaIni; talla <= tallaFin; talla += incTalla)
		{
			sumatiempo = 0;
			for (int i = 0; i < NUMREPETICIONES; i++)
			{
				ConjuntoInt *v = new ConjuntoInt(talla);
				v->GeneraVector(talla);
				double secs = ordenarArrayDeInt(v->getDatos(), talla, metodo1);
				v->vaciar();
				delete v;
				sumatiempo += secs;
			}
			cout << "\t" << talla << "\t\t" << setprecision(5) << fixed << (sumatiempo) / NUMREPETICIONES << "\t\t";
			f << talla << "\t" << setprecision(5) << fixed << (sumatiempo) / NUMREPETICIONES << "\t";

			sumatiempo = 0;
			for (int i = 0; i < NUMREPETICIONES; i++)
			{
				ConjuntoInt *v = new ConjuntoInt(talla);
				v->GeneraVector(talla);
				double secs = ordenarArrayDeInt(v->getDatos(), talla, metodo2);
				v->vaciar();
				delete v;
				sumatiempo += secs;
			}
			cout << setprecision(5) << fixed << (sumatiempo) / NUMREPETICIONES << "\n";
			f << setprecision(5) << fixed << (sumatiempo) / NUMREPETICIONES << "\n";

		}
		f.close();
		cout << "Datos guardados en el fichero Compara" + nombreAlgoritmo[metodo1] + nombreAlgoritmo[metodo2] + ".dat" << endl;
	}
	else
		cout << "Error al en la apertura del archivo";

	G.generarGraficaCMP(nombreAlgoritmo[metodo1], nombreAlgoritmo[metodo2]);
}	
