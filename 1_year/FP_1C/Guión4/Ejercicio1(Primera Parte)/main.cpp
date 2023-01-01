#include <iostream>
#include <string.h>

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
    stock = stock - cantidad;

    if(stock<0)
    {
        cout << "Fuera de stock";
        stock = stock + cantidad;
    }
    else
        {
            cout << "El precio de la venta es: " << precio*cantidad << endl;
            cout << "El Stock restante es: " << stock;
        }
}

int main()
{
    tprod producto1;
    cad nom;
    float prec;
    int st, cantidad;

    //Proceso para mostrar los valores asignados por el constructor
    producto1.leenombre(nom);
    cout << "Nombre: " << nom << endl;

    prec = producto1.leeprecio();
    cout << "Precio: " << prec << endl;

    producto1.leestock(st);
    cout << "Stock: " << st << endl;

    cout << endl;

    //Proceso para cambiar nombre, precio y stock por defecto a los introducidos por teclado
    cout << "Introduzca el nombre, el precio y el stock del almacen del produto deseado: " << endl;
    cout << "Nombre: "; cin >> nom;
    cout << "Precio: "; cin >> prec;
    cout << "Stock: "; cin >> st;

    producto1.cambiarnombre(nom);
    producto1.cambiarprecio(prec);
    producto1.cambiarstock(st);

    cout << endl;

    //Proceso para mostrar por pantalla los nuevos resultados
    producto1.leenombre(nom);
    cout << "Nombre: " << nom << endl;

    prec = producto1.leeprecio();
    cout << "Precio: " << prec << endl;

    producto1.leestock(st);
    cout << "Stock: " << st << endl;

    cout << endl;

    //Proceso de venta de un producto
    cout << "Introduzca la cantidad de producto que desea vender: ";
    cin >> cantidad;

    producto1.vender(cantidad);

    return 0;
}
