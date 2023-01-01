#include <iostream>
#include <string.h>
#include <ctype.h>
#include <conio.h>
#include <stdlib.h>
#define MAX 5

using namespace std;

typedef char cad[20];

class tprod
{
    cad nombre;
    float precio;
    int stock;
public:
    tprod();
    void cambiarnombre(cad nom);
    void cambiarprecio(float prec);
    void cambiarstock(int stoc);
    void leenombre(cad nom);
    float leeprecio();
    void leestock(int &st);
    void vender(int cantidad);
};

tprod::tprod()
{
    precio = 0;
    stock = 0;
    strcpy(nombre, "NO HAY PRODUCTO");
}

void tprod::cambiarnombre(cad nom)
{
    strcpy(nombre, nom);
}

void tprod::cambiarprecio(float prec)
{
    precio = prec;
}

void tprod::cambiarstock(int stoc)
{
    stock = stoc;
}

void tprod::leenombre(cad nom)
{
    strcpy(nom, nombre);
}

float tprod::leeprecio()
{
    return precio;
}

void tprod::leestock(int &st)
{
    st = stock;
}

void tprod::vender(int cantidad)
{
    if(stock<0)
    {
        cout << "Fuera de stock";
    }
    else
        if(stock >= cantidad)
        {
            stock = stock - cantidad;
            cout << "El precio de la venta es: " << precio*cantidad << endl;
            cout << "El Stock restante es: " << stock;
        }
        else
        {
            cout << "No hay stock suficiente.";
        }
}

class almacen
{
    tprod productos[MAX];
    int nprod;
public:
    almacen();
    void vaciar();
    int existe(cad nom);
    void verprod(int pos, tprod &prod);
    int insertar(tprod P);
    void vertabla();
    void vender(int pos, int cant);
    char menu();
};

almacen::almacen()
{
    vaciar();
}

void almacen::vaciar()
{
    nprod = 0;
}

int almacen::existe(cad nom)
{
    int i, pos;
    i = 0;
    pos = -1;
    cad nombreprod;

    while(i<nprod && pos == -1)
    {
        productos[i].leenombre(nombreprod);
        if(strcmp(nombreprod, nom)==0)
        {
            pos = i;
        }
        i++;
    }

    return pos;
}

void almacen::verprod(int pos, tprod &prod)
{
    prod = productos[pos];
}

int almacen::insertar(tprod P)
{
    int resultado;
    cad nombre;

    if(nprod >= MAX)
    {
        resultado = 2;
    }
    else
    {
        P.leenombre(nombre);
        if(existe(nombre)!=-1)
        {
            resultado = 1;
        }
        else
        {
            productos[nprod] = P;
            nprod++;
            resultado = 0;
        }
    }

    return resultado;
}

void almacen::vertabla()
{
    cad nombreprod;
    float precioprod;
    int stockprod, i;

    for(i=0; i<nprod; i++)
    {
        productos[i].leenombre(nombreprod);
        precioprod = productos[i].leeprecio();
        productos[i].leestock(stockprod);

        cout << "Producto " << i+1 << endl << "Nombre: " << nombreprod << "\t" << "Precio: " << precioprod << "\t" << "Stock: " << stockprod << endl;

    }
}

void almacen::vender(int pos, int cant)
{
    productos[pos].vender(cant);
}

char almacen::menu()
{
    char respuesta;

    do
    {
        cout << endl << "\t************* MENU ******************\n\t*******A.- Visualizar tabla.\t ****\n\t*******B.- Insertar producto.\t ****\n\t*******C.- Vender un producto.\t ****\n\t*******D.- Vaciar el almacen.\t ****\n\t*******E.- Salir.\t\t ****\n\t*************************************" << endl;
        respuesta = toupper(getch());

        if(respuesta!='A' && respuesta!='B' && respuesta!='C' && respuesta!='D' && respuesta!='E')
        {
            system("CLS");
        }

    }while(respuesta!='A' && respuesta!='B' && respuesta!='C' && respuesta!='D' && respuesta!='E');

    return respuesta;
}

int main()
{
    almacen A;
    char respuesta;

    //Variable para guardar los diferentes return de las diferentes funciones
    int returnedres;

    //Variables de la opcion B
    tprod ProductoInsertado;
    cad NomprodI;
    float PrecioprodI;
    int StockprodI;
    char nespagnola = 164;

    //Variables de la opcion C
    cad Prodavender;
    int cant;


    do
    {
        respuesta = A.menu();

        switch(respuesta)
        {
            case 65: A.vertabla();
                     break;
            case 66: cout << "Introduzca un producto en el almacen" << endl;
                     cout << "Nombre del producto: "; cin >> NomprodI;
                     cout << "Precio por unidad del producto: "; cin >> PrecioprodI;
                     cout << "Stock del producto: "; cin >> StockprodI;
                     ProductoInsertado.cambiarnombre(NomprodI);
                     ProductoInsertado.cambiarprecio(PrecioprodI);
                     ProductoInsertado.cambiarstock(StockprodI);
                     returnedres = A.insertar(ProductoInsertado);
                     switch(returnedres)
                     {
                         case 2: cout << "Lo siento, el almacen esta completamente lleno";
                                break;
                         case 1: cout << "Disculpe pero ese producto ya se encuentra en el almacen";
                                break;
                         case 0: cout << "Producto a" << nespagnola << "adido al almacen correctamente";
                                break;
                     };

                     break;
            case 67: cout << "Introduzca el producto que desea vender: ";
                     cin >> Prodavender;
                     returnedres = A.existe(Prodavender);
                     if(returnedres==-1)
                         cout << "Ese producto no existe.";
                     else
                     {
                         cout << "Introduzca la cantidad de ese producto que desea vender: ";
                         cin >> cant;
                         A.vender(returnedres, cant);
                     }
                     break;
            case 68: A.vaciar();
                     cout << "Almacen vaciado.";
                     break;
            case 69: cout << "Muchas gracias, hasta la proxima :)";
                     break;

        }
    }while(respuesta!=69);

    return 0;
}
