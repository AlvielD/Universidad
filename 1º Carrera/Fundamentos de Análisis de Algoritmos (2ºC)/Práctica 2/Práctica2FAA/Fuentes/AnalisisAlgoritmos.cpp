#include "TestOrdenacion.h"
#include <iostream>
#include <clocale>
using namespace std;
/** Programa principal **/
int main()
{
	//** Introducir menú **//
	TestOrdenacion T;
	char spanishN = 164;

	char opc;
	do {
		system("cls");
		cout << endl << endl << "\t\t\t\t *** MENU PRINCIPAL ***" << endl <<
			"\t\t *** ANALISIS EXPERIMENTAL DE ALGORITMOS DE ORDENACION ***\n\n" <<
			"\t\t\t\t\t\t\tAlvaro Esteban Mu" << spanishN << "oz\n" << endl <<
			"\t\t------------------------------------------------------------" << endl <<
			"\t\t\t1.- Probar los metodos de ordenacion\n\n" <<
			"\t\t\t2.- Obtener el caso medio de un metodo de ordenacion\n\n" <<
			"\t\t\t3.- Comparar dos metodos\n\n" <<
			"\t\t\t0.- Salir\n\n" <<
			"\t\t\t----------\n\n"
			"\t\t\tElige Opcion: ";
		cin >> opc;

		switch (opc) {
		case '1': {
			system("cls");
			T.comprobarMetodosOrdenacion();
			break;
		}
		case '2': {
			int opsubmenu2;
			system("cls");
			cout << endl << endl << "\t\t\t *** Metodo a estudiar para el caso medio ***\n\n" <<
			"\t\t------------------------------------------------------------" << endl <<
			"\t\t\t0:- Burbuja\n\n" <<
			"\t\t\t1:- Insercion\n\n" <<
			"\t\t\t2:- Seleccion\n\n" <<
			"\t\t\t----------\n\n"
			"\t\t\tElige Opcion: ";
			cin >> opsubmenu2;

			system("cls");
			T.casoMedio(opsubmenu2);
			system("pause");
			system("cls");
			break;
		}
		case '3':{
			int metodo1, metodo2;
			system("cls");
			cout << endl << endl << "\t\t\t *** Metodo a estudiar para el caso medio ***\n\n" <<
				"\t\t------------------------------------------------------------" << endl <<
				"\t\t\t0:- Burbuja\n\n" <<
				"\t\t\t1:- Insercion\n\n" <<
				"\t\t\t2:- Seleccion\n\n" <<
				"\t\t\t----------\n\n"
				"\t\t\tElige metodo 1: ";
			cin >> metodo1;
			cout << "\t\t\tElige metodo 2: ";
			cin >> metodo2;

			system("cls");
			T.comparar(metodo1, metodo2);
			system("pause");
			system("cls");
			break;
		}
		case '0': {
			cout << endl << "\t\t\tGracias por usar mis servicios uwu" << endl << endl;
			system("pause");
			system("cls");
			break;
		}
		default: {
			cout << endl << "\t\t\tOpcion no valida :c" << endl << endl;
		}
		}
	} while (opc != '0');

	return 0;
};