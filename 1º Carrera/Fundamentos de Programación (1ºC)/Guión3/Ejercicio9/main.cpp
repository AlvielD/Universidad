#include <iostream>
#include <string.h>
#define TAMA 25

using namespace std;

class Analisis
{
    int Datos[1000];
    int NDatos;
    int Valores[TAMA];
public:
    void PedirDatos();
    void AnalizarDatos();
    bool EstanTodos();
    int ValorRepetido();
    int ValorMasRepetido();
    void MostrarDatos();
    void MostrarAnalisis();
};

void Analisis::PedirDatos()
{
    char Respuesta;
    int i, NI;

    cout << "Desea introducir los datos manualmente o aleatoriamente? Pulse 'm' para manualmente y 'a' para aleatoriamente: ";

    do
    {
        cin >> Respuesta;

        if(Respuesta!='m' && Respuesta!='a')
            cout << "Opcion no valida. Introduzca de nuevo: ";

    }while(Respuesta!='m' && Respuesta!='a');

    //Proceso manual
    if(Respuesta=='m')
    {
        cout << "A continuacion introduzca los valores deseados recordando que dicho valor debe estar entre 0 y 24 (Pulse -1 para terminar el proceso)" << endl;

        i=0;
        //NI = Numero introducido
        while(NI!=-1 && i<=1000)
        {
            cout << "Introduzca el valor " << i << ":";

            do
            {
                cin >> NI;

                if(NI<-1 || NI>=TAMA)
                    cout << "Valor no valido, introduzca de nuevo: ";

            }while(NI<-1 || NI>=TAMA);

            if(NI>=0 && NI<TAMA)
            {
                Datos[i]=NI;
                i++;
            }
            else
                NDatos = i;
        }
    }
    //else Proceso aleatorio.
        //Generar valor aleatorio tanto para NDatos como para los diferentes datos.

}

void Analisis::AnalizarDatos()
{
    int i, h;

    //Inicializacion de los valores a 0
    for(i=0; i<TAMA; i++)
        Valores[i]=0;

    for(i=0; i<TAMA; i++)
    {
        for(h=0; h<=NDatos; h++)
        {
            if(Datos[h]==i)
                Valores[i]++;
        }
    }
}


bool Analisis::EstanTodos()
{
    int i;
    bool Estan=true;

    for(i=0; i<=TAMA; i++)
    {
        if(Valores[i]<1)
            Estan=false;
    }

    return Estan;
}

int Analisis::ValorRepetido()
{
    int N, Vrepe;

    cout << "Introduzca el valor que desea ver: ";

    do
    {
        cin >> N;

        if(N<0 || N>TAMA)
            cout << "Este valor no se encuentra en las posibilidades, inserte de nuevo: ";

    }while(N<0 || N>TAMA);

    Vrepe = Valores[N];

    return Vrepe;
}

int Analisis::ValorMasRepetido()
{
    int i, Vmasrepe, Nvalor;
    Vmasrepe=Valores[0];


    for(i=1; i<TAMA; i++)
    {
        if(Valores[i]>Vmasrepe)
            {
                Vmasrepe=Valores[i];
                Nvalor=i;
            }
    }


    return Nvalor;
}

void Analisis::MostrarDatos()
{
    int i;

    for(i=0; i<NDatos; i++)
    {
        cout << Datos[i] << "\t";
    }
}

void Analisis::MostrarAnalisis()
{
    int i;

    for(i=0; i<TAMA; i++)
    {
        cout << Valores[i] << "\t";
    }
}

int main()
{
    Analisis A;
    bool p;
    int Vrepe;

    A.PedirDatos();
    A.AnalizarDatos();
    p = A.EstanTodos();

    if(p==true)
        cout << endl << "Todos los datos se encuentran al menos una vez repetidos en la tabla." << endl;
    else
        cout << endl << "No todos los datos se encuentran al menos una vez repetidos." << endl;

    Vrepe = A.ValorRepetido();

    cout << "Su valor se encuentra " << Vrepe << " veces repetido." << endl;

    Vrepe = A.ValorMasRepetido();

    cout << "El valor mas repetido es: " << Vrepe << endl;

    A.MostrarDatos();
    cout << endl << endl;
    A.MostrarAnalisis();

    return 0;
}
