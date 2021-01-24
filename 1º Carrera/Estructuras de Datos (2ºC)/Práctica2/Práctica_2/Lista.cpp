#include "Lista.h"
#include <string.h>

lista::lista()
{
    /*elementos=new peluquero[INCREMENTO];
    if (elementos!=NULL)
    {
        Tama=INCREMENTO;
        n=0;
    }
    else
    {
        Tama=n=-1;
    }*/
    elementos=NULL;
    vaciar();
}

lista::~lista()
{
    if (elementos!=NULL)
        delete [] elementos;
    elementos=NULL;
    Tama=n=0;
}

lista::lista(peluquero &e)
{
    vaciar();
    insertar(1, e);
}

void lista::vaciar()
{
    if(elementos != NULL)
        delete [] elementos;
    elementos = new peluquero[INCREMENTO];
    if(elementos == NULL)
    {
        Tama = n = -1;
    }
    else
    {
        Tama = INCREMENTO;
        n = 0;
    }
}

bool lista::esvacia()
{
    return(n==0);
}
int lista::longitud()
{
    return n;
}

//void anadirIzq(peluquero e); No necesario implementar
//void anadirDch(peluquero e); No necesario implementar
//void eliminarIzq(); No necesario implementar
//void eliminarDch(); No necesario implementar
//peluquero observarIzq(); No necesario implementar
//peluquero observarDch(); No necesario implementar
//void concatenar(lista l); No necesario implementar

bool lista::pertenece(peluquero &e)
{
    return (posicion(e) != -1);
}

void lista::insertar(int i, peluquero &e)
{
  if (n==Tama)
  {
    peluquero *NuevaZona=new peluquero[Tama+INCREMENTO];
    if (NuevaZona!=NULL)
    {
        for (int j=1;j<=n; j++)
            VolcarPeluquero(NuevaZona[j], elementos[j]);

        Tama+=INCREMENTO;
        delete [] elementos;
        elementos=NuevaZona;
    }
  }
  if (n<Tama)
  {
    for (int pos = n-1; pos >= i-1; pos--)
    {
        VolcarPeluquero(elementos[pos+1], elementos[pos]);
    }
    VolcarPeluquero(elementos[i-1], e);
    n++;
  }
}

void lista::eliminar(int i)
{
  while (i<n)
  {
    VolcarPeluquero(elementos[i-1], elementos[i]); // Desplazamiento
    i++;
  }
  n--;
  if (Tama-n>=INCREMENTO && Tama>INCREMENTO)
  {
    peluquero *NuevaZona=new peluquero[Tama-INCREMENTO];
    if (NuevaZona!=NULL)
    {
        for (int j=0;j<Tama-INCREMENTO; j++)
            VolcarPeluquero(NuevaZona[j], elementos[j]);
        Tama-=INCREMENTO;
        delete [] elementos;
        elementos=NuevaZona;
    }
  }
}

void lista::modificar(int i, peluquero &e)
{
    VolcarPeluquero(elementos[i-1], e);
}

peluquero& lista::observar(int i)
{
    return(elementos[i-1]);
}

int lista::posicion(peluquero &e)
{
  int i=0;
  while (elementos[i].Codigo!=e.Codigo && i < n)
      i++;
  return (i == n ? -1 : i+1);
}

void VolcarPeluquero(peluquero &aux, peluquero &original)
{
    aux.Codigo=original.Codigo;
    aux.TipoServicio=original.TipoServicio;
    strcpy(aux.Nombre, original.Nombre);
    strcpy(aux.Apellidos, original.Apellidos);
    aux.Col.copiarcola(original.Col);
}
