#include <conio.h>

#include "Productos.h"

productos::productos(cadena Fichero,cadena FicheroVentas)
{
    producto paux;
    bool ejecutado=true;

    numproductos=0;
    maxcatalogo=0;

    strcpy(fichero, Fichero);
    //Abrimos el fichero en entrada/salida.
    resumen.clear();
    resumen.open(fichero, ios::in | ios::out | ios::binary);
    if(resumen.fail())//Si la apertura falla porque no existe
    {
        resumen.clear();
        resumen.close();
        //Lo abrimos solo en lectura para crearlo
        resumen.open(fichero, ios::out);
        if(resumen.fail())//Si la apertura falla porque no se puede crear
        {
            resumen.clear();
            resumen.close();
            ejecutado=false;
        }
        else
            resumen.close();
    }
    else//Si el fichero ya existe
    {
        resumen.seekg(0, ios::beg);
        resumen.read((char*)&paux, sizeof(producto));
        while(!resumen.eof())
        {
            if(paux.tipo!=-1)
            {
                numproductos++;
                if(paux.producto>maxcatalogo)
                    maxcatalogo = paux.producto;/*Código mayor existente en el fichero
                                                (Preguntar si no se refiere al numero de productos del fichero)*/
            }

            resumen.seekg(sizeof(producto), ios::cur);
            resumen.read((char*)&paux, sizeof(producto));
        }

    }
    resumen.close();

    if(ejecutado==true)
        ven.asignar(FicheroVentas, Fichero);
    else
        cout << "Error en la asignacion.";


}

productos::~productos()
{

}

int productos::getmaxcatalogo()
{
    return maxcatalogo;
}

void productos::setmaxcatalogo(int num)
{
    maxcatalogo = num;
}

int productos::getnumproductos()
{
    return numproductos;
}

void productos::setnumproductos(int num)
{
    numproductos=num;
}


void productos::mostrarproductos()
{
    producto pr;

    resumen.open(fichero, ios::in | ios::binary);
    if(resumen)
    {
        resumen.seekg(0, ios::beg);
        resumen.read((char*)&pr, sizeof(producto));
        cout << endl << "\t\t*** Fichero de productos ***" << endl <<
        "\t----------------------------------------------" << endl;

        while(!resumen.eof())
        {
            if(pr.tipo!= -1)
            {
                cout << "\tProducto: " << pr.nombre << endl <<
                "\tUltima Venta: " << pr.ultimaventa.dia << "/" << pr.ultimaventa.mes << "/" << pr.ultimaventa.anno << endl <<
                "\tImporte: " << pr.importe << endl <<
                "\tUnidades: " << pr.unidades << endl <<
                "\tTipo Producto: " << pr.tipo << endl <<
                "\t------------------------------------" << endl;
            }
            resumen.read((char*)&pr, sizeof(producto));
        }
    }
    else
    {
        cout << "Error al abrir el archivo.";
        resumen.clear();
    }
    resumen.close();

}

void productos::mostrarventas()
{
    ven.mostrarventas();
}

bool productos::anadirventa()
{
    int num;
    bool Agnadido=false;

    cout << "Introduzca codigo de venta a anadir: ";
    cin >> num;

    resumen.open(fichero, ios::in | ios::out | ios::binary);
    if(resumen)
    {
        //Comprobamos que si el producto existe
        producto paux;
        resumen.seekg(0, ios::end);
        maxcatalogo=resumen.tellg()/sizeof(producto);
        if(num<=maxcatalogo)
        {
            //El producto existe y se comprueba que no esté descatalogado para añadir las ventas
            resumen.seekg((num-1)*sizeof(producto), ios::beg);
            resumen.read((char*)&paux, sizeof(producto));

            if(paux.tipo!=-1)
            {
                ven.anadirventa(num); //El producto existe y procede a venderse
                Agnadido=true;
            }
            else
                cout << "El producto a vender no se encuentra disponible.";
        }
        else
        {
            //El producto no existe, por lo tanto se crea y añade al fichero
            cout << "El producto no existe, se procede a crearlo:" << endl;
            cout << "Nombre del producto: "; cin >> paux.nombre;
            cout << "Tipo de producto: "; cin >> paux.tipo;
            paux.producto=num;
            paux.importe=0;
            paux.unidades=0;
            paux.ultimaventa.anno=0;
            paux.ultimaventa.dia=0;
            paux.ultimaventa.mes=0;
            //Añadimos el producto al fichero de productos
            resumen.seekp((num-1)*sizeof(producto), ios::beg);
            resumen.write((char*)&paux, sizeof(producto));
            //Añadimos la venta
            ven.anadirventa(num);
            Agnadido=true;
        }
    }
    else
    {
        cout << "Error en la apertura del archivo.";
        resumen.clear();
    }
    resumen.close();

    return Agnadido;
}

void productos::actualizarresumen()
{
    ven.resumirfichero();
}

void productos::obtenerestadisticas(int tipo,int annoini,int annofin)
{
    ven.estadisticas(tipo, annoini, annofin);
}
