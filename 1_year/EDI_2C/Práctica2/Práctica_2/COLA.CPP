#include "Cola.h"
#include <iostream>

cola::cola()
{
  elementos=new cliente[INCREMENTO];
  if (elementos==NULL)   {
    ne=fin=inicio=-1;
    Tama=-1;
  }
  else   {
    ne=fin=inicio=0;
    Tama=INCREMENTO;
  }
}

cola::~cola()
{
  if (elementos!=NULL)
    delete [] elementos;

  elementos=NULL;
  ne=fin=inicio=-1;
  Tama=0;
}

void cola::encolar(cliente e)
{
    if(ne==Tama)
    {
        cliente *NuevaZona=new cliente[Tama+INCREMENTO];
        if(NuevaZona!=NULL)
        {
            for(int i=0;i<ne; i++)
            {
                NuevaZona[i]=elementos[inicio];
                inicio=(inicio+1)%Tama;
            }
            inicio=0;
            fin=ne;
            Tama+=INCREMENTO;
            if(elementos!=NULL)
                delete elementos;
            elementos=NuevaZona;
        }
    }
    if(ne<Tama)
    {
        fin-=fin/Tama;
        elementos[fin]=e;
        fin=(fin+1)%Tama;
        ne++;
    }
}

void cola::desencolar()
{
    if(ne==0)return;

    inicio=(inicio+1)%Tama;
    ne--;

    if (Tama-ne>=INCREMENTO && Tama>INCREMENTO)
    {
        cliente *NuevaZona=new cliente[Tama-INCREMENTO];
        if (NuevaZona!=NULL)
        {
            for (int i=0;i<ne; i++)
            {
                NuevaZona[i]=elementos[inicio];
                inicio=(inicio+1%Tama);
            }
            Tama-=INCREMENTO;
            inicio=0;
            fin=ne;
            if(elementos!=NULL)
                delete [] elementos;
            elementos=NuevaZona;
        }
    }
}

bool cola::esvacia()
{
    return (ne==0);
}

cliente cola::primero()
{
    return elementos[inicio];
}

int cola::longitud()
{
    return ne;
}

void cola::vaciar()
{
    if(elementos!=NULL)
        delete [] elementos;
    elementos = new cliente[INCREMENTO];
    ne = fin = inicio = 0;
    Tama = INCREMENTO;
}
void cola::copiarcola(cola &c)
{
    vaciar();
    cola caux;
    while(!c.esvacia())
    {
        encolar(c.primero());
        caux.encolar(c.primero());
        c.desencolar();
    }
    while(!caux.esvacia())
    {
        c.encolar(caux.primero());
        caux.desencolar();
    }
}
