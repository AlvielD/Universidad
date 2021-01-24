#define _GLIBCXX_USE_CXX11_ABI 0
#include <iostream>
#include <cstdlib>
#include "grafo.h"
#include "conjunto.h"
#include <queue>
#include <sstream>
#include <map>
#include <stack>



using namespace std;

//Ejercicio 1
template <typename T>
T verticeMaxCoste(const Grafo<T, float>& G)
{

/*
    Podríamos hacer lo siguiente para obtener una solución:
    • Usar un objeto diccionario (map) para almacenar los costes de cada vértice
    • Inicializar dicho diccionario a 0
    • Procesar todas las aristas del grafo, y en cada una de ellas, sumar el coste (etiqueta) de dicha arista al coste de su vértice origen
    • Procesar el diccionario para obtener el vértice con el coste máximo
    • Devolver dicho vértice
*/

/********************************************************************/
/**************************DATA SEGMENT******************************/
/********************************************************************/


    map<T, float> coste;                                //Diccionario con los costes de salida de los nodos
    T v, vmax;                                          //Variable que contendrá el nodo solución
    float mayorSalida=0;                                //Variable que guardará el valor de salida mayor
    //Arista<T, float> a;                               //Variable que guardará las aristas que se supriman del conjunto de aristas para poder manejar sus valores (COMO NO TENEMOS CONSTRUCTOR SIN PARÁMETROS, INICIALIZAMOS EN LÍNEA 56)
    Conjunto<Vertice<T> > cv = G.vertices();            //Conjunto de vertices que usaremos para inicializar los valores del diccionario
    Conjunto<Arista<T, float> > ca = G.aristas();       //Conjunto de aristas que usaremos para extraer los valores de salida de los nodos


/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/


    //Inicializamos el diccionario de costes a 0
    while(!cv.esVacio()) //Mientras el conjunto de vértices no sea vacío, repite
    {
        v = cv.quitar().getObj(); //Quita el vértice del conjunto y lo guarda en v
        coste[v] = 0;   //Accede a la posición del diccionario que indica v y la inicializa a 0
    }

    //Llenamos el diccionario con los valores de salida de los nodos
    while(!ca.esVacio()) //Mientras el conjunto de aristas no esté vacío, repite
    {
        Arista<T, float> a = ca.quitar(); //Quitamos una arista del conjunto y la guardamos en a para procesarla
        coste[a.getOrigen()] += a.getEtiqueta(); //Suma al valor que ya se encuentra en el map, el valor de salida
    }




    //Solo nos queda procesar el diccionario para sacar el nodo que posee el mayor valor de salida
    for(typename map<T, float>::iterator it = coste.begin(); it != coste.end(); it++)
    {
        if(it->second >= mayorSalida)
        {
            mayorSalida = it->second;
            vmax = it->first;
        }
    }



    return vmax;
}


//Ejercicio 2
template <typename T, typename U>
void inaccesibles(const Grafo<T, U>& G)
{
/*
    El método debe mostrar por pantalla aquellos vértices del grafo considerados
    como inaccesibles, para lograr una solución al problema podemos hacer lo siguiente
    * Para cada vertice, comprobar si existe una arista que lo tenga como destino
*/

/********************************************************************/
/**************************DATA SEGMENT******************************/
/********************************************************************/

   Conjunto<Vertice<T> > cv = G.vertices();
   Conjunto<Arista<T, U> > ca = G.aristas();
   //Arista<T, float> a
   map<T, bool> inacc;
   T v;


/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/


   //Inicializamos el diccionario a false
   while(!cv.esVacio())
   {
       v = cv.quitar().getObj();
       inacc[v] = false;
   }

   for(typename map<T, bool>::iterator it = inacc.begin(); it!=inacc.end(); it++)
   {
        ca = G.aristas();
        while(!ca.esVacio())
        {
            Arista<T, float> a = ca.quitar();
            if(it->first==a.getDestino())
                inacc[it->first]=true;
        }
   }

   cout << "Lo nodos inaccesibles del grafo son: " << endl;
   //Recorremos el diccionario mostrando por pantalla, aquellos nodos que son inaccesibles
   for(typename map<T, bool>::iterator it = inacc.begin(); it!=inacc.end(); it++)
   {
       if(inacc[it->first]==false)
        cout << "El nodo " << it->first << endl;
   }

}


// Ejercicio 3
template <typename T, typename U>
bool caminoEntreDos(const Grafo<T, U>& G, const T& vo, const T& vd)
{
/*
    Indicar si existe o no un camino entre los vértices vo y vd, el algoritmo
    no puede ser recursivo y además se debe hacer uso del TAD cola (queue de la STL)
    Tendremos que hacer un algoritmo de búsqueda en un grafo, como
    nos piden que usemos una cola, estamos hablando de un BFS (Breadth First Search)
*/


/********************************************************************/
/**************************DATA SEGMENT******************************/
/********************************************************************/


    Conjunto<Vertice<T> > cvaux;            //Conjunto auxiliar de vertices que irá guardando los vértices adyacentes del nodo que está siendo explorado
    Conjunto<Vertice<T> > explorado;        //Conjunto de vertices que guarda los vértices del grafo que ya han sido explorados
    queue<T> frontera;                      //Cola que contiene los vértices pendientes de ser explorados
    T vaux;                                 //Objeto de tipo T que guardará el nodo que está siendo explorado
    bool encontrado=false;                  //Variable booleana que indica si se ha encontrado un camino


/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/


    cvaux = G.adyacentes(vo);
    //Cargamos la cola con los adyacentes del nodo inicial
    while(!cvaux.esVacio())
        frontera.push(cvaux.quitar().getObj());

    //Repetimos lo siguiente, mientras no se haya encontrado el camino
    //y la cola no esté vacía
    while(!encontrado && !frontera.empty())
    {
        vaux = frontera.front();                    //Miramos el primer nodo de la cola
        frontera.pop();                             //Sacamos el primer nodo de la cola
        if(vaux==vd)                                //Si el nodo es solución, encontrado pasa a valer "true"
            encontrado=true;
        else                                        //Si el nodo no es solución, exploramos sus nodos adyacentes
        {
            cvaux = G.adyacentes(vaux);             //Cargamos el conjunto auxiliar con los adyacentes del nodo auxiliar

            while(!cvaux.esVacio())                 //Cargamos la cola con los nodos adyacentes
            {
                vaux = cvaux.quitar().getObj();
                if(!explorado.pertenece(vaux))      //Si el nodo NO ESTÁ EXPLORADO, se carga en la cola, en caso contrario no se hace nada
                    frontera.push(vaux);

            }
        }
        explorado.anadir(vaux);                     //Añadimos el nodo al conjunto de nodos explorados
    }

    return encontrado;

}


//Ejercicio 4
template <typename T>
void caminosAcotados(const Grafo<T, float>& G, const T& u, float maxCoste)
{
/*
    Esta función deberá mostrar todos los caminos que pueden darse desde un vertice en un grafo dados sin que sobrepase el valor
    maxCoste. (Se recomienda usar un algoritmo recursivo)
    Caso base --> No existen más aristas con el nodo en que nos encontramos
    como origen || se ha sobrepasado el valor de la variable maxCoste

    Ideas:
    *Pasar como parámetro un conjunto sobre el que se vayan añadiendo los nodos solución
     dicho parámetro debe ser pasado por copia para devolver tantos conjuntos como caminos diferentes haya
    *Añadir el nodo a la pila fuera del bucle
*/

/********************************************************************/
/**************************DATA SEGMENT******************************/
/********************************************************************/


    stringstream solv;


/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/


    solv << u;
    caminosAcotados(G, u, maxCoste, solv.str(), 0);

}

template <typename T>
void caminosAcotados(const Grafo<T, float>& G, const T& u, float maxCoste, string solv, float costac)
{

    Conjunto<Arista<T, float> > caux = G.aristas();

    cout << "(" << solv << ")   coste = " << costac << endl;
    while(!caux.esVacio())
    {
        Arista<T, float> aux = caux.quitar();
        if(aux.getOrigen()==u && (costac + aux.getEtiqueta() <= maxCoste))
        {
            stringstream saux;
            saux << aux.getDestino();
            caminosAcotados(G, aux.getDestino(), maxCoste, solv + ", " + saux.str(), costac + aux.getEtiqueta());
        }
    }
}


/*
//EJERCICIO 4 CON COLA
template <typename T>
void caminosAcotados(const Grafo<T, float>& G, const T& u, float maxCoste)
{
/*
    Esta función deberá mostrar todos los caminos que pueden darse desde un vertice en un grafo dados sin que sobrepase el valor
    maxCoste. (Se recomienda usar un algoritmo recursivo)
    Caso base --> No existen más aristas con el nodo en que nos encontramos
    como origen || se ha sobrepasado el valor de la variable maxCoste

    Ideas:
    *Pasar como parámetro un conjunto sobre el que se vayan añadiendo los nodos solución
     dicho parámetro debe ser pasado por copia para devolver tantos conjuntos como caminos diferentes haya
    *Añadir el nodo a la pila fuera del bucle
*/

/********************************************************************/
/**************************DATA SEGMENT******************************/
/********************************************************************/
/*

    queue<T> solv;

*/
/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/
/*

    solv.push(u);
    caminosAcotados(G, u, maxCoste, solv, 0);

}

template <typename T>
void caminosAcotados(const Grafo<T, float>& G, const T& u, float maxCoste, queue<T> solv, float costac)
{

    Conjunto<Arista<T, float> > caux = G.aristas();

    queue<T> qaux;
    while(!solv.empty())
    {
        cout << "(" solv.front() << ") " << costac << endl;
        qaux.push(solv.front());
        solv.pop();
    }

    while(!caux.esVacio())
    {
        Arista<T, float> aux = caux.quitar();
        if(aux.getOrigen()==u && (costac + aux.getEtiqueta() <= maxCoste))
        {
            qaux.push(aux.getDestino());
            caminosAcotados(G, aux.getDestino(), maxCoste, qaux, costac + aux.getEtiqueta());
        }
    }
}
*/


//Ejercicio 5
template <typename T, typename U>
T outConectado(const Grafo<T, U>& G)
{
/*
    Devolver vertice outConectado de un grafo
    Vértice outConectado --> mayor número de conexiones de salida que de entrada
    Devolver solo uno.

    Una solución podría ser la siguiente:
    Rellenaremos dos diccionario con las conexiones de salida y de entrada
    de cada vértice, de esta forma podremos comparar las respectivas entradas
    de cada diccionario para cada vértice y localizar un vértice outConectado

*/

/********************************************************************/
/**************************DATA SEGMENT******************************/
/********************************************************************/

    map<T, float> entradas;
    map<T, float> salidas;
    Conjunto<Vertice<T> > verconj;
    Conjunto<Arista<T, float> > arsconj;
    T vaux;
    //Arista<T, float> arisaux;


/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/

    verconj = G.vertices();
    //Inicializamos cada diccionario a 0
    while(!verconj.esVacio())
    {
        vaux = verconj.quitar().getObj();
        entradas[vaux]=0;
        salidas[vaux]=0;
    }


    //Recorremos todas las aristas del grafo, sumando 1 salida por cada origen y una entrada por cada destino
    arsconj = G.aristas();
    while(!arsconj.esVacio())
    {
        Arista<T, float> arisaux = arsconj.quitar();
        entradas[arisaux.getDestino()] += 1;
        salidas[arisaux.getOrigen()] += 1;
    }


    //Recorremos cada diccionario, comparando y devolviendo los vértices outConectados
    verconj = G.vertices();                     //Rellenamos el conjunto de vértices otra vez
    while(!verconj.esVacio())
    {
        vaux = verconj.quitar().getObj();
        if( salidas[vaux] > entradas[vaux] )
            return vaux;
    }

}

//Ejercicio 6
template <typename T, typename U>
void recorrido_profundidad(const Grafo<T, U>& G, const T& v)
{


/********************************************************************/
/**************************DATA SEGMENT******************************/
/********************************************************************/


    map<T, bool> explorados;


/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/


    //Inicializamos el diccionario a false
    Conjunto<Vertice<T> > conjvers = G.vertices();
    while(!conjvers.esVacio())
    {
        T veraux = conjvers.quitar().getObj();
        explorados[veraux] = false;
    }

    recorrido_profundidad(G, v, explorados);
}



template <typename T, typename U>
void recorrido_profundidad(const Grafo<T, U>& G, const T& v, map<T, bool>& explorados)
{
/*
    Realizar un recorrido en profundidad sin la ayuda de una estructura de datos
    solo podemos realizar el algoritmo de forma recursiva.
    Para realizar un DFS (Depth First Search) se suele usar una pila, como
    en este caso no podemos, tendremos que pensar una forma de realizar
    las características que tiene una pila, mediante recursividad.
    Caso base --> No existen más aristas cuyo origen sea el nodo dado
    Usaremos un diccionario para guardar los nodos ya explorados
*/

/********************************************************************/
/**************************CODE SEGMENT******************************/
/********************************************************************/

    cout << v << " ";
    explorados[v]=true;


    //Selección del nodo adyacente
    Conjunto<Arista<T, float> > conjaris = G.aristas();
    while(!conjaris.esVacio())
    {
        Arista<T, float> arisaux = conjaris.quitar();
        if(arisaux.getOrigen()==v && explorados[arisaux.getDestino()]==false)
            recorrido_profundidad(G, arisaux.getDestino(), explorados);
    }
}


//********************************************************************//
int main()
{
    Grafo<int, float> G(7);
    for (int i = 1; i <= 7; i++) G.insertarVertice(i);
    G.insertarArista(2, 1, 4);
    G.insertarArista(1, 3, 3);
    G.insertarArista(1, 4, 2);
    G.insertarArista(1, 6, 1);
    G.insertarArista(6, 4, 5);
    G.insertarArista(4, 7, 3);
    G.insertarArista(5, 1, 6);

    Grafo<string, float> H(7);
    H.insertarVertice("Huelva"); H.insertarVertice("Lepe"); H.insertarVertice("Niebla");
    H.insertarVertice("Mazagon"); H.insertarVertice("Almonte"); H.insertarVertice("Aljaraque");
    H.insertarVertice("Matalascañas");
    H.insertarArista("Lepe", "Huelva", 4);
    H.insertarArista("Huelva", "Niebla", 3);
    H.insertarArista("Huelva", "Mazagon", 2);
    H.insertarArista("Huelva", "Aljaraque", 1);
    H.insertarArista("Mazagon", "Almonte", 3);
    H.insertarArista("Mazagon", "Matalascañas", 4);
    H.insertarArista("Aljaraque", "Mazagon", 5);
    H.insertarArista("Almonte", "Huelva", 6);


    cout << " Vertice de maximo coste en G: " << verticeMaxCoste(G) << endl;
    cout << " Vertice de maximo coste en H: " << verticeMaxCoste(H) << endl;

    cout << endl << " Vertices inaccesibles en G: ";
    inaccesibles(G);
    inaccesibles(H);


    cout << endl << " Camino entre Dos en H de Lepe a Almonte: ";
    cout << (caminoEntreDos(H, string("Lepe"), string("Almonte")) ? " SI " : " NO ") << endl;
    cout << endl << " Camino entre Dos en H de Aljaraque a Lepe: ";
    cout << (caminoEntreDos(H, string("Aljaraque"), string("Lepe")) ? " SI " : " NO ") << endl;
    cout << endl << " Camino entre Dos en G de 5 a 2: ";
    cout << (caminoEntreDos(G, 5, 2) ? " SI " : " NO ") << endl;
    cout << endl << " Camino entre Dos en G de 1 a 7: ";
    cout << (caminoEntreDos(G, 1, 7) ? " SI " : " NO ") << endl;

    cout << endl << " Caminos acotados en G a coste 9 desde el vertice 2:" << endl;
    caminosAcotados(G, 2, 9);

    cout << endl << endl << " Vertice outConectado en G: " << outConectado(G);
    cout << endl << " Vertice outConectado en H: " << outConectado(H);

    cout << endl << endl << " Recorrido en profundidad de H desde el vertice Huelva:  ";
    recorrido_profundidad(H, string("Huelva"));
    cout << endl << endl;


    system("PAUSE");
    return EXIT_SUCCESS;
}
