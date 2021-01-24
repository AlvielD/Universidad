#include <iostream>
#include <conio.h>
#include <stdlib.h>
#include <string.h>
#include <cctype>

using namespace std;

class PalabraOculta
{
    char Palabra[30];
    int Puntos;
public:
    void Iniciar();
    int Jugar();
    void MostrarSecreta();
};

void PalabraOculta::Iniciar()
{
    char letra;
    int i=0;

    Puntos = 9;

    cout << "Inserte la palabra que debe adivinar: ";
    do
    {
        letra = toupper(getch());

        if(letra!=13)
        {
            Palabra[i] = letra;
            cout<<'*';
        }
        else
            Palabra[i] = '\0';

        i++;

    }while(letra!='\r');

}
//i es el indice del bucle de la palabramostrada a guiones, h es el indice del bucle que nos permite repetir
//el proceso de introducir las letras, k es el indice del bucle para saber si hemos ganado o no y j es el indice
//del bucle de busqueda para saber si la letra introducida esta en la palabra a adivinar.
int PalabraOculta::Jugar()
{
    int i, Longitud, h, k, j;

    Longitud = strlen(Palabra);

    char PalabraMostrada[Longitud];
    char Letra;
    bool Acabado=true, encontrado;

    h=0;

    //Muestra la palabra oculta por guiones
    for(i=0; i<Longitud; i++)
        PalabraMostrada[i] = '-';


    system("CLS");

    cout << endl << PalabraMostrada;


    while(strchr(PalabraMostrada, '-')!= NULL && Puntos!=0)
    {
        cout << endl << "Introduzca letra: ";
        Letra = toupper(getch());

        encontrado=false;

        for(j=0; j<=Longitud; j++)
        {
            if(Letra==Palabra[j])
            {
                PalabraMostrada[j] = Letra;
                encontrado=true;
            }
        }

        if(encontrado==false)
            {
                cout << "Esa letra no esta en la palabra, -1 punto :c";
                Puntos = Puntos - 1;
                system("pause");
            }


        system("CLS");

        cout << PalabraMostrada;

        h++;

    }

    for(k=0; k<Longitud; k++)
        if(PalabraMostrada[k]=='-')
            Acabado=false;

    if(Acabado==false)
        cout << endl << "Lo siento, se te acabaron los punticos :c";
    else
        cout << endl << "Tu ganas :D";


    return Puntos;
}

void PalabraOculta::MostrarSecreta()
{
    cout << endl << "La palabra secreta es: " << Palabra;
}

int main()
{
    PalabraOculta P;
    int Puntos;

    P.Iniciar();
    Puntos = P.Jugar();
    P.MostrarSecreta();

    cout << endl << "Tus puntos son: " << Puntos;

    return 0;
}
