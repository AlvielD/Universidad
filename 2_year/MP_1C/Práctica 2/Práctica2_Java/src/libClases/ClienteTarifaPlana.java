package libClases;

public class ClienteTarifaPlana extends Cliente {

	//ATRIBUTOS
	private float minutos;
	private String nacionalidad;
	private static float limite=300;
	private static float precioMin=20f;
	
	//MÉTODOS
	public ClienteTarifaPlana(String NIF, String nom, Fecha fNac, Fecha fAlta, float mins, String nac) {
		super(NIF, nom, fNac, fAlta);
		minutos = mins;
		nacionalidad = nac;
	}
	public ClienteTarifaPlana(String NIF, String nom, Fecha fNac, float mins, String nac) {
		super(NIF, nom, fNac);
		minutos = mins;
		nacionalidad = nac;
	}
	public ClienteTarifaPlana(ClienteTarifaPlana c) {
		super(c);
		minutos = c.minutos;
		nacionalidad = c.nacionalidad;
	}

	public float getMinutos()
	{
		return minutos;
	}
	public String getNacionalidad()
	{
		return nacionalidad;
	}
	public void setMinutos(float mins)
	{
		minutos = mins;
	}
	public void setNacionalidad(String nac)
	{
		nacionalidad = nac;
	}
	
	@Override
	public Object clone()
	{
		ClienteTarifaPlana c = new ClienteTarifaPlana(getNif(), getNombre(), getFechaNac(), getFechaAlta(), minutos, nacionalidad);
		
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
		return(super.toString() + nacionalidad + " [" + ClienteTarifaPlana.getLimite() + " por " + ClienteTarifaPlana.getTarifa() + "] " + minutos +" --> " + factura());
	}
	
	public static float getLimite()
	{
		return limite;
	}
	public static float getTarifa()
	{
		return precioMin;
	}
	public static void setTarifa(float mins, float pMin)
	{
		limite = mins;
		precioMin = pMin;
	}

	public float factura()
	{
		float total = ClienteTarifaPlana.getTarifa();
		
		if(minutos > ClienteTarifaPlana.getLimite())
			total += ((minutos - ClienteTarifaPlana.getLimite()) * 0.15);
		
		return total;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
