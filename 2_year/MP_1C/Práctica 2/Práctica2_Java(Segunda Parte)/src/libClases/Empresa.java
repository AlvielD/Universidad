package libClases;

import java.util.Scanner;
import libClases.*;

public class Empresa implements Cloneable{
	
	//ATRIBUTOS
	private int N=0;					//Número de clientes de la Empresa
	private Cliente[] ClientesE;		//Array que contiene los clientes de la Empresa
	private final int salto = 2;		//#DEFINE salto 2
	
	//MÉTODOS
	public Empresa()
	{
		ClientesE = new Cliente[salto];
	}
	
	public int getN()
	{
		return N;
	}
	public Cliente[] getClientes()
	{
		return ClientesE;
	}
	
	public void alta(Cliente c)
	{
		//SI EL ARRAY ESTÁ LLENO, LO AUMENTAMOS
		//--------------------------------------------------------------
		if(ClientesE.length==N)	//ClientesE.lenght nos devuelve la capacidad del array
		{
			//El array está lleno, lo aumentamos
			Cliente[] aux = new Cliente[ClientesE.length+salto];
			
			for(int i=0; i<N; i++)
				aux[i]=ClientesE[i];
			
			ClientesE = aux;
			//El recolector de basura se encarga del resto (Qué cómodo)
		}
		//--------------------------------------------------------------
		
		boolean existente=false;
		//Comprobamos que el cliente a añadir no existe
		if(N>0)
		{
			for(int i=0; i<N; i++)
				if(ClientesE[i].getNif().equals(c.getNif()))
					existente=true;
		}
		
		if(existente==false)
			ClientesE[N++] = c;	//Añadimos el cliente al array e incrementamos N
		else
			System.out.println("El cliente ya existe\n");
	}
	public void alta()
	{
		//VARIABLES LOCALES
		Scanner Lector = new Scanner(System.in);	//Scanner
		Cliente c = null;							//Cliente
		String nifIn, nombreIn;						//Datos cliente
		Fecha fAlta, fNac;							//Datos cliente
		boolean existente=false;					//Para comprobar si el cliente existe
		float mins;									//Datos cliente
		int opc;									//Opción tipo cliente
		int posCli=-1;
		
		//SI EL ARRAY ESTÁ LLENO, LO AUMENTAMOS
		//--------------------------------------------------------------
		if(ClientesE.length==N)	//ClientesE.lenght nos devuelve la capacidad del array
		{
			//El array está lleno, lo aumentamos
			Cliente[] aux = new Cliente[ClientesE.length+salto];
			
			for(int i=0; i<N; i++)
				aux[i]=ClientesE[i];
			
			ClientesE = aux;
			//El recolector de basura se encarga del resto (Qué cómodo)
		}
		//--------------------------------------------------------------
		
		//Preguntamos el DNI
		System.out.println("DNI: ");
		nifIn = Lector.nextLine();
		//Comprobamos que no exista un cliente con dicho DNI
		if(N>0)
		{
			for(int i=0; i<N; i++)
				if(ClientesE[i].getNif().equals(nifIn))
				{
					existente=true;
					posCli = i;
				}
		}
		//Si no existe cliente con dicho DNI procedemos a crearlo pidiendo el resto de datos
		if(existente==false)
		{
			System.out.println("Nombre: ");
			nombreIn = Lector.nextLine();
			System.out.println("Fecha de Nacimiento:\n");
			fNac = Fecha.pedirFecha();
			System.out.println("Fecha de Alta:\n");
			fAlta = Fecha.pedirFecha();
			System.out.println("Minutos que habla al mes: ");
			mins = Lector.nextFloat();
			//Pedimos el tipo de cliente y controlamos que se seleccione una opción valida
			do
			{
				System.out.println("Indique tipo de cliente (1-Móvil, 2-Tarifa Plana): ");
				opc = Lector.nextInt();
			}while(opc != 1 && opc != 2);
			//Inicializamos según el tipo de cliente
			switch(opc)
			{
				case 1://Cliente Móvil
				{
					float precioMin;
					Fecha fPerm;
					
					System.out.println("Precio por minuto: ");
					precioMin = Lector.nextFloat();
					System.out.println("Fecha fin permanencia:\n");
					fPerm = Fecha.pedirFecha();
					c = new ClienteMovil(nifIn, nombreIn, fNac, fAlta, fPerm, mins, precioMin);
				}break;
				case 2://Cliente Tarifa Plana
				{
					String nac;
					
					System.out.println("Nacionalidad: ");
					nac = Lector.next();
					c = new ClienteTarifaPlana(nifIn, nombreIn, fNac, fAlta, mins, nac);
				}
			}
			//En este punto ya hemos creado el cliente, ahora debemos añadirlo
			ClientesE[N++] = c;	//Añadimos el cliente al array e incrementamos N
		}
		else//Si existe un cliente con dicho DNI se procede por aquí lanzando un mensaje
		{
			System.out.println("Ya existe un cliente con ese DNI:\n" + ClientesE[posCli]);
			
		}
	}
	public void baja(String nif)
	{
		//Recorremos el array entero buscando todos los clientes cuyo nif sea el pasado por parámetro
		for(int i=0; i<N; i++)
			if(ClientesE[i].getNif().equals(nif))
			{
				//Comprobar funcionamiento
				for(int j=i; j<N-1; j++)
					ClientesE[j] = ClientesE[j+1];
				N--;
			}	
		
		//Comprobamos que podemos hacer el array más pequeño
		if(N < ClientesE.length)
		{
			Cliente[] aux = new Cliente[N];
			for(int i=0; i<N; i++)
				aux[i]=ClientesE[i];
			
			ClientesE = aux;
		}
	}
	public void baja()
	{
		Scanner Lector = new Scanner(System.in);
		String nifIntroducido;
		char opc;
		
		System.out.println("Introduzca nif Cliente a dar de baja: ");
		nifIntroducido = Lector.nextLine();
		
		for(int i=0; i<N; i++)
		{
			if(ClientesE[i].getNif().equals(nifIntroducido))
			{
				//En la posición i se encuentra el cliente a ser eliminado
				System.out.println(ClientesE[i] + "\n¿Seguro que desea eliminarlo (s/n)? ");
				
				//Verificamos que la respuesta dada está en las posibilidades
				do
				{	
					opc = Lector.next().charAt(0);
					
					if(opc != 's' && opc!= 'n')
						System.out.println("Opción incorrecta, introduzca de nuevo.\n");
					
				}while(opc != 's' && opc!= 'n');
				
				//Caso decir que si
				if(opc == 's')
				{	
					System.out.println("El cliente " + ClientesE[i].getNombre() + " con nif " + ClientesE[i].getNif() + " ha sido eliminado\n");
					
					baja(nifIntroducido);
				}
				//Caso decir que no
				else
					System.out.println("El cliente con nif " + ClientesE[i].getNif() + " no se elimina\n");
			}
		}
	}
	public float factura()
	{
		float ganancias=0;
		
		for(int i=0; i<N; i++)
			ganancias += ClientesE[i].factura();
		
		return ganancias;
	}
	public void descuento(int dto)
	{
		//Recorremos el array de la empresa reduciendo el precio a los CLIENTES DE TIPO MÓVIL
		for(int i=0; i<N; i++)
		{
			if(ClientesE[i] instanceof ClienteMovil)
			{
				((ClienteMovil)ClientesE[i]).setPrecio(((ClienteMovil)ClientesE[i]).getPrecio() - ((ClienteMovil)ClientesE[i]).getPrecio()*(dto/100));
			}
			//En caso contrario no se hace nada porque no es instancia de tipo ClienteTarfiaPlana
		}
	}
	public int nClienteMovil()
	{
		int contadorCM=0;
		//Recorremos el array de clientes contando de todos los clientes cuales son de tipo ClienteMovil
		for(int i=0; i<N; i++)
		{
			if(ClientesE[i] instanceof ClienteMovil)
				contadorCM++;
				
		}
		
		return contadorCM;
	}
	
	@Override
	public Object clone()
	{
		Empresa E = new Empresa();
		
		E.N=N;
		//Creamos una copia del array de los clientes de la empresa que llama al método
		Cliente[] ClientesAux = new Cliente[N];
		for(int i=0; i<N; i++)
		{
			ClientesAux[i] = (Cliente)ClientesE[i].clone();
		}
		E.ClientesE = ClientesAux;
		
		return E;
	}
	@Override
	public String toString()
	{
		String EmpresaTextada="";//Se inicializa como cualquier variable numérica
		//Se carga en la variable string como si fuese una variable numérica cualquiera
		for(int i=0; i<N; i++)
		{
			EmpresaTextada += ClientesE[i].toString() + "\n";
		}
		
		return EmpresaTextada;
	}
	
	public static void main(String[] args) {
		//Prueba unitaria
	}
}