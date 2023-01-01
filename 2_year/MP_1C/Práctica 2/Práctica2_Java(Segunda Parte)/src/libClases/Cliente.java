package libClases;

public abstract class Cliente implements Cloneable, Proceso{
	
	//ATRIBUTOS
	private final String nif; //dni del cliente (letra incluida) (NO puede cambiar)
	private final int codCliente; //codigo único (y fijo) generado por la aplicación
	private String nombre; //nombre completo del cliente (SI se puede modificar)
	private final Fecha fechaNac; //fecha nacimiento del cliente (NO se puede cambiar)
	private final Fecha fechaAlta; //fecha de alta del cliente (SI se puede modificar)
	
	private static int nCli=1; //Número de clientes (Para asignar el código)
	private static Fecha fechaPorDefecto = new Fecha(1,1,2018);
	
	//MÉTODOS
	public Cliente(String NIF, String nom, Fecha fNac, Fecha fAlta)
	{
		this.nif = NIF;
		this.nombre = nom;
		this.fechaNac = (Fecha)fNac.clone();
		this.fechaAlta = (Fecha)fAlta.clone();
		this.codCliente = nCli++;
	}
	public Cliente(String NIF, String nom, Fecha fNac)
	{
		this.nif = NIF;
		this.nombre = nom;
		this.fechaNac = (Fecha)fNac.clone();
		this.fechaAlta = Cliente.getFechaPorDefecto();
		this.codCliente = nCli++;
	}
	public Cliente(Cliente c)
	{
		this(c.nif, c.nombre, (Fecha)c.fechaNac.clone(), (Fecha)c.fechaAlta.clone());
	}
	
	public String getNif()
	{
		return nif;
	}
	public String getNombre()
	{
		return nombre;
	}
	public int getCodCliente()
	{
		return codCliente;
	}
	public Fecha getFechaNac()
	{
		//No podemos devolver la referencia ya que la clase fecha es mutable
		//Devolveremos una copia
		Fecha f = (Fecha)fechaNac.clone();
		
		return f;
	}
	public Fecha getFechaAlta()
	{
		Fecha f = (Fecha)fechaAlta.clone();
		
		return f;
	}
	public static Fecha getFechaPorDefecto()
	{
		Fecha fpdCopia = (Fecha)fechaPorDefecto.clone();
		
		return fpdCopia;
	}
	public void setNombre(String nom)
	{
		nombre = nom;
	}
	public void setFechaAlta(Fecha f)
	{
		fechaAlta.setFecha(f.getDia(), f.getMes(), f.getAnio());
	}
	public static void setFechaPorDefecto(Fecha f)
	{
		fechaPorDefecto.setFecha(f.getDia(), f.getMes(), f.getAnio());
	}
	
	@Override
	public String toString() //devuelve una cadena con la información del cliente
	{
		return(nif + " " + fechaNac + ": " + nombre + " (" + codCliente + " - " + fechaAlta + ")");
	}
	@Override
	public abstract Object clone();
	@Override
	public boolean equals(Object obj)
	{
		boolean iguales=false;
		Cliente c;
		
		if((obj instanceof Cliente))
		{
			c = (Cliente)obj;
			if(nif==c.nif && fechaNac.equals(c.fechaNac))
				iguales = true;
		}
		
		return iguales;
	}
	@Override
	public void ver()
	{
		System.out.println(toString());
	}
	
	public abstract float factura();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
