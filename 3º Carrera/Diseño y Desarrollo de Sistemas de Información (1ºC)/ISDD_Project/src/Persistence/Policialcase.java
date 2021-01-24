package Persistence;

/**
 * Policial cases of the database
 * @author Álvaro Esteban Muñoz
 */
public class Policialcase {
    
    private String codCase;
    private String name;
    private String startDate;
    private String endDate;
	
    /**
     * Default constructor  
     */
    public Policialcase() {
        codCase = null;
        name = null;
        startDate = null;
        endDate = null;
    }
    
    /**
     * Constructor with parameters
     * @param cc code of the case
     * @param n name of the case
     * @param sd start date of the case
     * @param ed end date of the case
     */
    public Policialcase(String cc,String n, String sd, String ed) {
        codCase = cc;
        name = n;
        startDate = sd;
        endDate = ed;
    }
    
    /**
     * 
     * @return the code of the case
     */
    public String getCodCaso(){
        return codCase;
    }	
	
    /**
     * 
     * @return the name of the case
     */
    public String getNombre(){
        return name;
    }

    /**
     * 
     * @return the start date of the case
     */
    public String getFechaInicio(){
        return startDate;
    }

    /**
     * 
     * @return the end date of the case
     */
    public String getFechaFin(){
        return endDate;
    }

    /**
     * Set the code of the case to the value of the parameter
     * @param c the code of the case
     */
    public void setCodCaso(String c){
        codCase = c;
    }

    /**
     * Set the name of the case to the value of the parameter
     * @param n the name of the case
     */
    public void setNombre(String n){
        name = n;
    }

    /**
     * Set the start date of the case to the value of the parameter
     * @param sd the start date of the case
     */
    public void setFechaInicio(String sd){
        startDate = sd;
    }
	  
    /**
     * Set the end date of the case to the value of the parameter
     * @param ed the end date of the case
     */
    public void setFechaFin(String ed){
        endDate = ed;
    }	    
}
