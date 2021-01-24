package Persistence;

/**
 * Collaboration of an expert in a case
 * @author Álvaro Esteban Muñoz
 */
public class Collaborates {
    
    private String codEXPERT;
    private String codCase;
    private String dateC;
    private String descripcion;
	
    /**
     * Default constructor
     */
    public Collaborates() {
	codEXPERT = null;
        codCase = null;
        dateC = null;
        descripcion = null;
    }
    
    /**
     * Create a new collaboration with the data given.
     * @param ce
     * @param cc
     * @param dc
     * @param d 
     */
    public Collaborates(String ce,String cc,String dc, String d) {
	codEXPERT = ce;
        codCase = cc;
        dateC = dc;
        descripcion = d;
    }
    
    /**
     * 
     * @return the code of the expert
     */
    public String getCodExperto(){
        return codEXPERT;
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
     * @return the date of the collaboration
     */
    public String getFecha(){
        return dateC;
    }

    /**
     * 
     * @return the description of the collaboration
     */
    public String getDescripcionColaboracion(){
        return descripcion;
    }

    /**
     * Set the code of the expert to the value of the parameter
     * @param ce the code of the expert
     */
    public void setCodExperto(String ce){
        codEXPERT = ce;
    }

    /**
     * Set the code of the case to the value of the parameter
     * @param cc the code of the case
     */
    public void setCodcaso(String cc){
        codCase = cc;
    }

    /**
     * Set the date of the collaboration to the value of the parameter
     * @param dc the value of the date
     */
    public void setFecha(String dc){
        dateC = dc;
    }

    /**
     * Set the description of the collaboration to the value of the parameter
     * @param d the description of the collaboration
     */
    public void setDescripcionColaboracion(String d){
        descripcion = d;
    }
}
