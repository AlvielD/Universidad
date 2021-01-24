package Persistence;

/**
 * Expert of the database
 * @author Álvaro Esteban Muñoz
 */
public class Expert {

    private String codEXPERT;
    private String name;
    private String country;
    private String sex;
    private String specialism;

    /**
     * Default constructor
     */
    public Expert() {
        codEXPERT = null;
        name = null;
        country = null;
        sex = null;
        specialism = null;
    }
    
    /**
     * Constructor with parameters
     * @param ce code of the expert
     * @param n name of the expert
     * @param c country of the expert
     * @param s sex of the expert
     * @param sp specialism of the expert
     */
    public Expert(String ce, String n, String c, String s, String sp) {
        codEXPERT = ce;
        name = n;
        country = c;
        sex = s;
        specialism = sp;
    }

    /**
     * 
     * @return the code of the expert
     */
    public String getCodEXPERT() {
        return codEXPERT;
    }

    /**
     * 
     * @return the name of the expert
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return the country of the expert
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @return the sex of the expert
     */
    public String getSex() {
        return sex;
    }

    /**
     * 
     * @return the specialism of the expert
     */
    public String getSpecialism() {
        return specialism;
    }

    /**
     * Set the code of the expert to the value of the parameter
     * @param c the code of the expert
     */
    public void setCodEXPERT(String c) {
        codEXPERT = c;
    }

    /**
     * Set the name of the expert to the value of the parameter
     * @param n the name of the expert
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * Set the country of the expert to the value of the parameter
     * @param p the country of the expert
     */
    public void setCountry(String p) {
        country = p;
    }

    /**
     * Set the sex of the expert to the value of the parameter
     * @param s the sex of the expert
     */
    public void setSex(String s) {
        sex = s;
    }

    /**
     * Set the specialism of the expert to the value of the parameter
     * @param e the specialism of the expert
     */
    public void setSpecialism(String e) {
        specialism = e;
    }
}
