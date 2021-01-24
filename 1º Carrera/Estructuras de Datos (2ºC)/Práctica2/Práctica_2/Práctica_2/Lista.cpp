#include "Lista.h"

lista::lista()
{
    elementos=new peluquero[INCREMENTO];
    if (elementos!=NULL)
    {
        Tama=INCREMENTO;
        n=0;
    }
    else
    {
        Tama=n=-1;
    }
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
    elementos=new peluquero[INCREMENTO];
    if (elementos!=NULL)
    {
        Tama=INCREMENTO;
        n=1;
        elementos[0]=e;
    }
    else
    {
        Tama=n=-1;
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
  int pos;
  if (n==Tama)
  {
    peluquero *NuevaZona=new peluquero[Tama+INCREMENTO];
    if (NuevaZona!=NULL)
    {
      for (int i=0;i<n; i++)
        NuevaZona[i]=elementos[i];
      Tama+=INCREMENTO;
      delete [] elementos;
      elementos=NuevaZona;
    }
  };
  if (n<Tama)
  {
    for (pos=n-1; pos>=i-1; pos--)
      elementos[pos+1]=elementos[pos];  // Desplazamiento
    elementos[i-1]=e;
    n++;
  }
}

void lista::eliminar(int i)
{
  while (i<n)
  {
    elementos[i-1]=elementos[i]; // Desplazamiento
    i++;
  }
  n--;
  if (Tama-n>=INCREMENTO && Tama>INCREMENTO)
  {
    peluquero *NuevaZona=new peluquero[Tama-INCREMENTO];
    if (NuevaZona!=NULL)
    {
      Tama-=INCREMENTO;
      for (int i=0;i<Tama; i++)
        NuevaZona[i]=elementos[i];
      delete [] elementos;
      elementos=NuevaZona;
    };
  };
}

void lista::modificar(int i, peluquero &e)
{
    elementos[i-1]=e;
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
