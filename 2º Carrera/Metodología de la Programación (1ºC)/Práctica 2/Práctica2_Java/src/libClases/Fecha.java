package libClases;
import java.util.Scanner;

public final class Fecha implements Cloneable, Proceso {
	//ATRIBUTOS
	private int Dia, Mes, Anio;
	
	//MÉTODOS
	public Fecha(int d, int m, int a)
	{
		setFecha(d, m, a);
	}
	public Fecha(Fecha f)
	{
		this(f.Dia, f.Mes, f.Anio);
	}
	
	public int getDia()
	{
		return Dia;
	}
	public int getMes()
	{
		return Mes;
	}
	public int getAnio() 
	{
		return Anio;
	}
	public void setFecha(int d, int m, int a)//Clase mutable
	{
		//Checkea si el año es bisiesto o no
		//--------------------------------------------------------------------------------
		    boolean bisiesto = false;
		    if ((a % 400 == 0) || (a%4 == 0 && a % 100 != 0))
		        bisiesto = true;
		//---------------------------------------------------------------------------------


		//Corrige la fecha (Debe haber alguna manera más sencilla de hacerlo...)
		//---------------------------------------------------------------------------------
		    if(d<1)
		        d=1;

		    switch(m)
		    {
		    case 1:
		    case 3:
		    case 5:
		    case 7:
		    case 8:
		    case 10:
		    case 12:{
		                if(d>31)
		                    d=31;
		            }
		            break;
		    case 4:
		    case 6:
		    case 9:
		    case 11:{
		                if(d>30)
		                    d=30;
		            }
		            break;
		    case 2:{
		                if(bisiesto==true && d>29)
		                    d=29;
		                else
		                    if(bisiesto==false && d>28)
		                        d=28;
		            }
		            break;
		    default:{
		                if(m>12)
		                        m=12;
		                else
		                    if(m<1)
		                        m=1;
		            }
		            break;
		    }
		    this.Anio=a;
		    this.Mes=m;
		    this.Dia=d;

		//---------------------------------------------------------------------------------
	}
	public void setDia(int dia)
	{
		this.Dia=dia;
	}
	public void setMes(int mes)
	{
		this.Mes=mes;
	}
	public void setAnio(int anio)
	{
		this.Anio=anio;
	}
	
	public static Fecha pedirFecha() 
	{
		//SI SE INTRODUCE FECHA NO VALIDA, SE PIDE DE NUEVO HASTA QUE SE INTRODUZCA CORRECTAMENTE
		int d, m, a;
		Scanner Lector = new Scanner (System.in);
		boolean fechaValida;
		
		do
		{
			//Creamos una string que contenga los datos y los divida en 3 sub-arrays divididos
			//por el caracter "/", cada uno contendrá un dato de la fecha.
			System.out.println("Introduce Fecha (dd/mm/yyyy): ");
			String[] fechaIntroducida = Lector.nextLine().split("/");
			d = Integer.parseInt(fechaIntroducida[0]);
			m = Integer.parseInt(fechaIntroducida[1]);
			a = Integer.parseInt(fechaIntroducida[2]);
			
			//COMPROBAMOS QUE LA FECHA INTRODUCIDA POR TECLADO ES VALIDA
			//Es el mismo código usado para corregir pero un poco alterado
			//----------------------------------------------------------
			
			//Checkea si el año es bisiesto o no
			//--------------------------------------------------------------------------------
			    boolean bisiesto = false;
			    if ((a % 400 == 0) || (a%4 == 0 && a % 100 != 0))
			        bisiesto = true;
			//---------------------------------------------------------------------------------


			//Comprueba que la fecha es valida (Se puede simplificar...)
			//---------------------------------------------------------------------------------
			    fechaValida = true;
			    
			    if(d<1)
			        fechaValida=false;

			    switch(m)
			    {
			    case 1:
			    case 3:
			    case 5:
			    case 7:
			    case 8:
			    case 10:
			    case 12:{
			                if(d>31)
			                    fechaValida=false;
			            }
			            break;
			    case 4:
			    case 6:
			    case 9:
			    case 11:{
			                if(d>30)
			                    fechaValida=false;
			            }
			            break;
			    case 2:{
			                if(bisiesto==true && d>29)
			                    fechaValida=false;
			                else if(bisiesto==false && d>28)
			                        fechaValida=false;
			            }
			            break;
			    default:{
			                if(m>12)
			                        fechaValida=false;
			                else
			                    if(m<1)
			                        fechaValida=false;
			            }
			            break;
			    }
			//---------------------------------------------------------------------------------
			if(fechaValida==false)
				System.out.println("Fecha no valida, introduzca de nuevo\n");
			
		}while(fechaValida==false);
		
		//Una vez la fecha es introducida correctamente, se construye su instancia y se devuelve.
		Fecha f = new Fecha(d,m,a);
		return f;
	}
	public static boolean mayor(Fecha f2, Fecha f1)
	{
		boolean mayorPrimero=false;
		
		if(f2.Anio>f1.Anio)
			mayorPrimero=true;
		if(f2.Anio == f1.Anio && f2.Mes > f1.Mes)
			mayorPrimero=true;
		if(f2.Anio == f1.Anio && f2.Mes == f1.Mes && f2.Dia > f1.Dia)
			mayorPrimero=true;
		
		return mayorPrimero;
	}
	
	@Override
	public String toString()
	{
		return((Dia<10? "0":"")+Dia+"/"+(Mes<10? "0":"")+Mes+"/"+Anio);
	}
	
	@Override
	public Object clone()
	{
		//Código para copias binarias de objetos
		Object obj=null;
		try
		{
			obj=super.clone();
		}catch(CloneNotSupportedException ex)
		{
			System.out.println("No se puede duplicar");
		}
		
		return obj;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean iguales=false;
		Fecha f;
		
		if((obj instanceof Fecha))
		{
			f = (Fecha)obj;
			if(Dia==f.Dia && Mes==f.Mes && Anio==f.Anio)
				iguales = true;
		}
		
		return iguales;
	}
	
	@Override
	public void ver()
	{
		System.out.println(this);
	}
	
	public boolean bisiesto()
	{
	    return ((Anio % 400 == 0) || (Anio%4 == 0 && Anio % 100 != 0));
	    //El operador == compara las direcciones de memoria ¡CUIDADO!
	    //Pero para tipos primitivos NO
	}
	
	public Fecha diaSig()
	{
		Fecha f = (Fecha)clone();
		
		f.Dia++;
		
		//Comprueba que no se ha sobrepasado el número de días del mes respectivo
		//-----------------------------------------------------------------------
		switch(f.Mes)
		{
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: 
		{
			if(f.Dia==32)
		    {
				f.Dia-=31;
				f.Mes++;
	   	    }
		}break;
		case 4:
		case 6:
		case 9:
		case 11:
		{
			if(f.Dia==31)
		    {
				f.Dia-=30;
				f.Mes++;
	   	    }
		}break;
		case 2:
		{
			if(f.Dia==30 && f.bisiesto())
		    {
				f.Dia-=29;
				f.Mes++;
	   	    }
			else if(f.Dia==29 && !f.bisiesto())
			{
				f.Dia-=28;
				f.Mes++;
			}
		}
		}
		
		if(f.Mes==13)
		{
			f.Mes-=12;
			f.Anio++;
		}
		//-----------------------------------------------------------------------
		
		return f;
	}

	public static void main(String[] args) 
	{
		Fecha f1 = new Fecha(29,2,2001), f2 = new Fecha(f1), f3 = new Fecha(29,2,2004);
		final Fecha f4=new Fecha(05,12,2023); //Es constante la referencia f4
		
		System.out.println("Fechas: " + f1.toString() + ", "+f2+ ", " +f3+ ", " +f4+ "\n");
		
		f1=new Fecha(31,12,2016); //31/12/2016
		f4.setFecha(28, 2, 2008); //Pero no es constante el objeto al que apunta
		
		System.out.println(f1 +" "+ f2.toString() +" " + f3 +" "+ f4+" "+ f1);
		
		f1=new Fecha(f4.getDia()-10, f4.getMes(), f4.getAnio()-7); //f1=18/02/2001
		f3=Fecha.pedirFecha(); //Pide una fecha por teclado
		
		if (f3.bisiesto() && Fecha.mayor(f2,f1))
			System.out.print("El " + f3.getAnio() + " fue bisiesto, " + f1 + ", " + f3);

	}
}
