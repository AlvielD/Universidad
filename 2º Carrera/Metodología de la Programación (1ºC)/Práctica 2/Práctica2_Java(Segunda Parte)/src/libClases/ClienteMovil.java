package libClases;

public class ClienteMovil extends Cliente {

	//ATRIBUTOS
	private Fecha fPermanencia;
	private float minutosHablados;
	private float precioMinuto;
	
	//MÉTODOS
	public ClienteMovil(String NIF, String nom, Fecha fNac, Fecha fAlta, Fecha fPerm, float mins, float precioMin)
	{
		super(NIF, nom, fNac, fAlta);
		fPermanencia=(Fecha)fPerm.clone();
		minutosHablados=mins;
		precioMinuto=precioMin;
		
	}
	public ClienteMovil(String NIF, String nom, Fecha fNac, float mins, float precioMin)
	{
		super(NIF, nom, fNac);
		fPermanencia=new Fecha(getFechaAlta().getDia(), getFechaAlta().getMes(), getFechaAlta().getAnio()+1);
		minutosHablados=mins;
		precioMinuto=precioMin;
	}
	public ClienteMovil(ClienteMovil c) {
		super(c);
		fPermanencia=(Fecha)c.fPermanencia.clone();
		minutosHablados=c.minutosHablados;
		precioMinuto=c.precioMinuto;
	}
	
	public Fecha getFPermanencia()
	{
		Fecha f = (Fecha)fPermanencia.clone();
		
		return f;
	}
	public float getMinutos()
	{
		return minutosHablados;
	}
	public float getPrecio()
	{
		return precioMinuto;
	}
	public void setFPermanencia(Fecha f)
	{
		fPermanencia.setFecha(f.getDia(), f.getMes(), f.getAnio());
	}
	public void setMinutos(float mins)
	{
		minutosHablados = mins;
	}
	public void setPrecio(float precio)
	{
		precioMinuto = precio;
	}
	
	@Override
	public Object clone()
	{
		ClienteMovil c = new ClienteMovil(getNif(), getNombre(), getFechaNac(), getFechaAlta(), fPermanencia, minutosHablados, precioMinuto); 
		
		return c;
	}
	@Override
	public void ver()
	{
		System.out.println(toString());
	}
	@Override
	public String toString()
	{
		return(super.toString() + fPermanencia + " " + minutosHablados + " x " + precioMinuto + " --> " + factura());
	}
	
	public float factura()
	{
		float total;
		
		total = precioMinuto * minutosHablados;
		
		return total;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
