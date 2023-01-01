#include <iostream>
#include<conio.h>
#include<cstring>

using namespace std;

class PalabraOculta
{
    char palabrasecreta[15];
    int puntos;
public:
    void iniciar();
    int jugar();
    void mostarsecreta();
};

void PalabraOculta::iniciar()
{
    char letra;
    int i=0;

    cout<<"Introduzca la palabra a adivinar: ";
    while (c != '\r')
    {
        cout<<'*';
        letra = getch();
        palabrasecreta[i] = letra;

        i++;

    }
    strupr(palabrasecreta);

    cout << palabrasecreta;
}
int PalabraOculta::jugar()
{
    char palabraadivinar[20], c;
    int i,x;
    for (i=0;i<strlen(palabrasecreta);i++)
    palabraadivinar[i]='-';

    palabraadivinar[i] = '\0';

    cout<<"La palabra a adivinar es "<<palabraadivinar;

    while((strchr(palabraadivinar,'-')!= NULL)&&puntos<9)
    {
        cout<<"Introduce una letra : ";
        c = toupper(getch());
        x = 0;
        while(x<strlen(palabrasecreta)&&c!=palabrasecreta[x])
            x++;
        if (c == palabrasecreta[x])
            palabraadivinar[x] = c;
        else
                puntos--;
        cout<<"Tus puntos son : "<<puntos;
        cout<<endl<<"La palabra a adivinar es :"<<palabraadivinar;
    }
    if (puntos>0)
        cout<<"bien has adivinado la palabra crack";
    else
        cout<<"Se te acabaron los puntos mastodonte";
        mostarsecreta();
}


void palabraoculta::mostarsecreta()
{
    cout<<endl<<"La palabra oculta es "<<palabrasecreta;
}*/


int main()
{
    PalabraOculta P;

    P.iniciar();
    P.jugar();
    //p.mostarsecreta();

    return 0;
}
