#include "Empresa.h"

Empresa::Empresa()
{
    nCli = 0;
    nCont = 0;
    maxCont = 10;

    Contratos = new Contrato*[10];
}

Empresa::~Empresa()
{
    //ELIMINAMOS LOS CONTRATOS
    for(int i=0; i<this->nCont; i++)
        delete this->Contratos[i];

    //ELIMINAMOS EL ARRAY DE CONTRATOS
    delete [] this->Contratos;

    //ELIMINAMOS LOS CLIENTES
    for(int i=0; i<this->nCli; i++)
        delete this->Clientes[i];
    //NO ELIMINAMOS EL ARRAY DE CLIENTES PORQUE NO ES DINÁMICO
}

void Empresa::cargarDatos()
{
    Fecha f1(29,2,2001), f2(31,1,2002), f3(1,2,2002);
    Clientes[nCli++] = new Cliente(75547001, "Peter Lee", f1);
    Clientes[nCli++] = new Cliente(45999000, "Juan Perez", Fecha(29,2,2000));
    Clientes[nCli++] = new Cliente(37000017, "Luis Bono", f2);

    Contratos[nCont++] = new ContratoMovil(75547001, f1, 0.12, 110, "DANES"); //habla 110m a 0.12
    Contratos[nCont++] = new ContratoMovil(75547001, f2, 0.09, 170, "DANES"); //habla 170m a 0.09
    Contratos[nCont++] = new ContratoTP(37000017, f3, 250); //habla 250m (300m a 10€, exceso 0.15
    Contratos[nCont++] = new ContratoTP(75547001, f1, 312); //habla 312m (300m a 10€, exceso 0.15
    Contratos[nCont++] = new ContratoMovil(45999000, f2, 0.10, 202, "ESPAÑOL"); //habla 202m a 0.1
    Contratos[nCont++] = new ContratoMovil(75547001, f2, 0.15, 80, "DANES"); //habla 80m a 0.15
    Contratos[nCont++] = new ContratoTP(45999000, f3, 400); //habla 400m (300m a 10€, exceso 0.15
}

void Empresa::ver()
{
    //Esquema de recorrido mostrando todos los clientes de la tabla estática
    //y todos los contratos de la tabla dinámica.

    cout << "La Empresa tiene " << nCli << " clientes y " << nCont << " contratos\nClientes:\n";
    for(int i=0; i<nCli; i++)
    {
        cout << Clientes[i]->getNombre() << " (" << Clientes[i]->getDni() << " - " << Clientes[i]->getFecha() << ")\n";
    }
    cout << endl << "Contratos:\n";
    for(int j=0; j<nCont; j++)
    {
        Contratos[j]->ver();
        cout << " - " << Contratos[j]->factura() << endl;
    }

}

int Empresa::nContratosTP()
{
    int acc=0;
    for(int i=0; i<nCont; i++)
    {
        if(typeid(*Contratos[i])==typeid(ContratoTP))
            acc++;
    }

    return acc;
}

void Empresa::crearContrato()
{
    bool encontrado=false;
    int i=0;

    //Variables para la creación de un nuevo cliente
    long int dniCliente;
    char NombreNewCliente[50];
    char Nombre[15], Apellido1[15], Apellido2[15];
    int dia, mes, anio;

    //Variables para la creación de un nuevo contrato
    int opcCont;
    //int dia, mes, anio; Reutilizo las usadas en la creación del cliente que ya no me hacen falta
    int minutosHablados;
    float precMint;
    char Nac[20];

    cout << "Introduzca DNI: "; cin >> dniCliente;
    while(i<nCli && encontrado==false)
    {
        if(Clientes[i]->getDni()==dniCliente)
            encontrado=true;
        else
           i++;
    }


    if(encontrado==false)//CLIENTE NO ENCONTRADO --> CREAMOS UNO NUEVO
    {
        //Trozo de código para dar de alta a un cliente nuevo
        cout << "Nombre del cliente: "; cin.ignore(); cin.getline(NombreNewCliente, 50);
        cout << "Dia: "; cin >> dia;
        cout << "Mes: "; cin >> mes;
        cout << "Anio: "; cin >> anio;
        Fecha f(dia, mes, anio);
        Clientes[nCli++] = new Cliente(dniCliente, NombreNewCliente, f);
    }
    //CLIENTE ENCONTRADO --> NO CREAMOS UNO NUEVO


//Crearle un contrato (TP o Móvil)
//----------------ESTRUCTURA DE CONTROL PARA LA SELECCIÓN DEL TIPO DE CONTRATO---------------------
    do
    {
        cout << "Tipo de contrato a abrir (1-Tarifa Plana, 2-Movil): ";
        cin >> opcCont;

        if(opcCont!=1 && opcCont!=2)
            cout << "ERROR - El tipo de contrato seleccionado no existe.\n";

    }while(opcCont!=1 && opcCont!=2);

//-------------------------------------------------------------------------------------------------

    if(opcCont==1)//CREAMOS UN CONTRATO DE TARIFA PLANA
    {
        //ContratoTP
        cout << "Fecha del Contrato\nDia: "; cin >> dia;
        cout << "Mes: "; cin >> mes;
        cout << "Anio: "; cin >> anio;
        cout << "Minutos hablados: "; cin >> minutosHablados;
        Contratos[nCont++] = new ContratoTP(dniCliente, Fecha(dia, mes, anio), minutosHablados);
    }
    else if(opcCont==2)//CREAMOS UN CONTRATO MÓVIL
    {
        //ContratoMovil
        cout << "Fecha del Contrato\nDia: "; cin >> dia;
        cout << "Mes: "; cin >> mes;
        cout << "Anio: "; cin >> anio;
        cout << "Minutos hablados: "; cin >> minutosHablados;
        cout << "Precio minuto: "; cin >> precMint;
        cout << "Nacionalidad: "; cin >> Nac;
        Contratos[nCont++] = new ContratoMovil(dniCliente, Fecha(dia, mes, anio), precMint, minutosHablados, Nac);
    }
    cout << endl;

    //TENDREMOS QUE COMPROBAR SI SE HA LLEGADO AL LÍMITE DE LA TABLA DINÁMICA
//-------------------------------------------------------------------------------------------------
    if(nCont==maxCont)
    {
        maxCont *= 2;//DOBLAMOS EL TAMAÑO MÁXIMO DE LOS CONTRATOS
        Contrato **aux;//PUNTERO AUXILIAR
        aux = Contratos;//HACEMOS QUE AUX APUNTE AL MISMO SITIO QUE CONTRATOS PARA DEJAR ESTA LIBRE
        Contratos = new Contrato*[maxCont];//CREAMOS EL NUEVO ARRAY CON EL DOBLE DE TAMAÑO
        for(int i=0; i<nCont; i++)//MOVEMOS LOS CONTRATOS DEL ARRAY AUXILIAR AL NUEVO ARRAY
            Contratos[i]=aux[i];
        delete []aux;
    }
//-------------------------------------------------------------------------------------------------
}

bool Empresa::cancelarContrato(int idContrato)
{
    //Primero comprobamos si el contrato existe (Esquema de búsqueda)
    bool encontrado=false;
    int i=0;
    while(encontrado==false && i<nCont)
    {
        if(Contratos[i]->getIdContrato()==idContrato)
            encontrado=true;
        else
            i++;
    }
    //Si el contrato ha sido encontrado --> Está en la posición "i" y además es el elemento número "i+1"

    if(encontrado==true)
    {
        //ELIMINAMOS EL CONTRATO DE LA MEMORIA Y MOVEMOS EL RESTO UNA POSICIÓN
        delete Contratos[i];
        while(i<nCont)
        {
            Contratos[i]=Contratos[i+1];
            i++;
        }
        nCont--;
    }
    return encontrado;
}

bool Empresa::bajaCliente(long int dni)
{
    //BUSCAMOS EL CLIENTE, LO BORRAMOS Y LOS MOVEMOS
    int j=0;
    bool encontrado=false;
    while(j<nCli && encontrado==false)
    {
        if(Clientes[j]->getDni()==dni)
        {
            encontrado=true;
            delete Clientes[j];
            while(j<nCli)
            {
                Clientes[j]=Clientes[j+1];
                j++;
            }
            nCli--;
        }
        else
            j++;
    }
    //BUSCAMOS LOS CONTRATOS CUYO DNI CORRESPONDA AL PASADO POR PARÁMETRO Y LOS BORRAMOS
    if(encontrado==true)
    {
        for(int i=0; i<nCont; i++)
        {
            if(Contratos[i]->getDniContrato()==dni)
            {
                delete Contratos[i];
                int k=i;//Usamos una variable auxiliar para no alterar la que estamos usando como indice
                while(k<nCont)
                {
                    Contratos[k]=Contratos[k+1];
                    k++;
                }
                nCont--;
                i--;//Restamos uno para volver a contar la posición en la que estamos
            }
        }
    }
    return encontrado;
}

void Empresa::descuento(int dispercen)
{
    //RECORREMOS LA LISTA DE CONTRATOS APLICANDO EL DESCUENTO A TODOS LOS CONTRATOS MÓVIL (TYPEID)
    float desc;
    for(int i=0; i<nCont; i++)
    {
        if(typeid(*Contratos[i])==typeid(ContratoMovil))
        {
            desc = ((ContratoMovil *)Contratos[i])->getPrecioMinuto()*(1-(dispercen/100.0));
            ((ContratoMovil *)Contratos[i])->setPrecioMinuto(desc);
        }
    }
}
