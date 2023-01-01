#include "Productos.h"
#include "Ventas.h"

#define SALTO 2

using namespace std;

void intercambiar(producto *p1, producto *p2);

void Burbuja(producto *tabla, int inicio, int fin);

void ventas::anadirventa(int num)
{
    venta vr, vw, vaux;
    int fichtamano;
    int fechaescrita, fechaleida;
    bool introducido=false;

    detalle.open(fichero, ios::binary | ios::in | ios::out);

    if(detalle)
    {
        vw.producto=num;
        cout << "Introduzca fecha: ";
        cin >> vw.fecha.dia >> vw.fecha.mes >> vw.fecha.anno;
        cout << "Unidades: ";
        cin >> vw.unidades;
        cout << "Importe: ";
        cin >> vw.importe; cout << endl;

        fechaescrita = (vw.fecha.anno*365)+(vw.fecha.mes*30)+(vw.fecha.dia);

        detalle.seekg(0, ios::beg);
        detalle.read((char*)&vr, sizeof(venta));

        int j=0;
        while(!detalle.eof() && introducido==false)
        {
            fechaleida = (vr.fecha.anno*365)+(vr.fecha.mes*30)+(vr.fecha.dia);
            if(fechaescrita <= fechaleida)
            {
                detalle.seekg(0, ios::end);
                fichtamano = detalle.tellg()/sizeof(venta);
                for(int i=0; i<(fichtamano-j); i++)
                {
                    detalle.seekg((fichtamano-(i+1))*sizeof(venta), ios::beg);
                    detalle.read((char*)&vaux, sizeof(venta));
                    detalle.seekp((fichtamano-i)*sizeof(venta), ios::beg);
                    detalle.write((char*)&vaux, sizeof(venta));
                }
                detalle.seekp(j*sizeof(venta), ios::beg);
                detalle.write((char*)&vw, sizeof(venta));
                introducido=true;
            }
            else
            {
                detalle.read((char*)&vr, sizeof(venta));
                j++;
                if(j>=fichtamano)
                {
                    detalle.seekp(0, ios::end);
                    detalle.write((char*)&vw, sizeof(venta));
                }
            }
        }
    }
    detalle.clear();
    detalle.close();

}

void ventas::mostrarventas()
{
    venta Vsalida;
    producto Psalida;
    int i=1;

    detalle.open(fichero, ios::in | ios::binary);
    ifstream prods;
    prods.open(ficheroresumen, ios::binary);

    cout << "\t ---Lista de Ventas---\n\n";

    //Leemos el fichero
    detalle.seekg(0, ios::beg);
    detalle.read((char*)&Vsalida, sizeof(venta));
    while(!detalle.eof())
    {
        prods.seekg(sizeof(producto)*(Vsalida.producto-1), ios::beg);
        prods.read((char*)&Psalida, sizeof(producto));

        if(prods && Psalida.tipo!=-1)
        {
            cout << "VENTA " << i << endl;
            cout << "Fecha de venta: " << Vsalida.fecha.dia << "/" << Vsalida.fecha.mes << "/" << Vsalida.fecha.anno << endl;
            cout << "Producto: " << Psalida.nombre << endl;
            cout << "Código: " << Vsalida.producto << endl;
            cout << "Unidades: " << Vsalida.unidades << endl;
            cout << "Importe: " << Vsalida.importe << endl;
            cout << "___________________________________________\n";

        }
        else if(!prods)
        {
            cout << "Error en la lectura del archivo.";
            prods.clear();
        }
        detalle.read((char*)&Vsalida, sizeof(venta));
        i++;
    }
    prods.close();
    detalle.close();

}

void ventas::resumirfichero()
{
    venta vr;
    producto pw;
    fstream resumen;
    int fechaventa, fechaproducto;

    resumen.open(ficheroresumen, ios::in | ios::out | ios::binary);
    detalle.open(fichero, ios::in | ios::binary);

    if(resumen && detalle)
    {
        detalle.seekg(0, ios::beg);
        detalle.read((char*)&vr, sizeof(venta));

        while(!detalle.eof())
        {
            //Leemos el producto de la posición i-ésima
            resumen.seekg(sizeof(producto)*(vr.producto-1), ios::beg);
            resumen.read((char*)&pw, sizeof(producto));
            //Obtenemos un número para representar las fechas y compararlas
            fechaventa = (vr.fecha.anno*10000)+(vr.fecha.mes*100)+(vr.fecha.dia);
            fechaproducto = (pw.ultimaventa.anno*10000)+(pw.ultimaventa.mes*100)+(pw.ultimaventa.dia);
            //Si la fecha de la venta es superior a la de la ultima venta del producto.
            if(fechaventa>fechaproducto)
            {
                //Actualizo Datos
                pw.ultimaventa=vr.fecha;

                pw.importe+=vr.importe;

                pw.unidades+=vr.unidades;

                resumen.seekp(sizeof(producto)*(pw.producto-1), ios::beg);
                resumen.write((char*)&pw, sizeof(producto));
            }
            detalle.read((char*)&vr, sizeof(venta));
        }
    }
    else
    {
        cout << "Error en la apertura de los ficheros.";
        detalle.clear();
        resumen.clear();
    }
    detalle.close();
    resumen.close();

}

void ventas::estadisticas(int tipo,int annoini,int annofin)
{
    int MaxBucle=SALTO;//Tamaño inicial de la tabla dinámica
    int i = 0; //Controlador del bucle para la tabla dinámica de productos
    int numprod = 0; //Número de elementos de la tabla dinámica
    venta vaux;
    producto paux;
    ifstream resumen;

    producto *tabla = new producto[MaxBucle];
    if(tabla==NULL)
    {
        cout << "Error en la reserva de memoria";
    }
    else
    {
        //Abrimos nuestros ficheros de ventas y productos
        detalle.open(fichero, ios::in | ios::out | ios::binary);
        resumen.open(ficheroresumen, ios::binary);

        if(detalle.fail() || resumen.fail())
        {
            cout << "Error en la apertura de ficheros";

            resumen.clear();
            detalle.clear();
            resumen.close();
            detalle.close();
        }
        else
        {
            detalle.seekg(0, ios::beg);
            detalle.read((char*)&vaux, sizeof(venta));
            while(!detalle.eof())
            {
                //Si existen ventas comprendidas en el intervalo y su producto posee el tipo pasado por parámetro, se resumen.
                if(vaux.fecha.anno>=annoini && vaux.fecha.anno<=annofin)
                {
                    resumen.seekg(sizeof(producto)*(vaux.producto-1), ios::beg);
                    resumen.read((char*)&paux, sizeof(producto));
                    if(paux.tipo==tipo)
                    {
                        while(i < numprod && tabla[i].producto!=vaux.producto)//Donde he inicializado la tabla???
                            i++;

                        if(tabla[i].producto==vaux.producto)
                        {
                            //Si el producto ya ha sido añadido antes
                            tabla[i].importe += vaux.importe;
                            tabla[i].unidades += vaux.unidades;
                        }
                        else
                        {
                            //Si el producto no se ha añadido todavía
                            tabla[i].importe = vaux.importe;
                            tabla[i].unidades = vaux.unidades;
                            strcpy(tabla[i].nombre, paux.nombre);
                            tabla[i].producto = vaux.producto;

                            numprod++;
                        }
                    }
                }
                detalle.read((char*)&vaux, sizeof(venta));

                if(numprod==MaxBucle)
                {
                    producto *newtabla = new producto[MaxBucle+SALTO];
                    if(newtabla == NULL)
                        cout << "Error al reservar memoria";
                    else
                    {
                        for(int j=0; j<MaxBucle; j++)
                            newtabla[j] = tabla[j];
                        delete []tabla;
                        tabla = newtabla;
                        MaxBucle = MaxBucle + SALTO;
                    }
                }
            }

            //Aplicamos ordenación en la tabla dinámica
            Burbuja(tabla, 0, numprod);

            //Mostramos los productos más vendidos atendiendo al número de unidades gracias al método de ordenación aplicado en la tabla dinámica
            cout << "\t <Productos mas vendidos>\n" <<
            "\t--------------------------\n";
            for(int j=0; j<numprod; j++)
            {
                cout << "\tNombre: " << tabla[j].nombre << endl <<
                "\tUnidades:" << tabla[j].unidades << endl <<
                "\tImporte: " << tabla[j].importe << endl;
            }
            delete []tabla;
            resumen.close();
            detalle.close();
        }
    }
}

bool ventas::asignar(cadena Fichero,cadena FicheroResumen)
{
    strcpy(fichero, Fichero);
    strcpy(ficheroresumen, FicheroResumen);

    bool Ejecutado=true;

    detalle.clear();
    detalle.open(fichero, ios::in | ios::out | ios::binary);//abrimos el fichero
    if(detalle.fail())
    {
        detalle.clear();
        detalle.close();

        detalle.open(fichero, ios::out);//Abrimos en modo salida para crearlo
        if(detalle.fail())
        {
            detalle.clear();
            Ejecutado=false;
        }

    }
    detalle.close();
    return Ejecutado;
}

void Burbuja(producto *tabla, int inicio, int fin)
{
    int pos,ele;
    for (pos=inicio; pos<fin; pos++)
        for (ele=fin; ele>pos; ele--)
            if(tabla[ele-1].unidades>tabla[ele].unidades)
                intercambiar(&tabla[ele-1], &tabla[ele]);//Función genérica que tendrá que ser implementada
}

void intercambiar(producto *p1, producto *p2)
{
    producto aux;
    aux = *p1;
    *p1 = *p2;
    *p2 = aux;
}
