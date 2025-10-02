package modelo;

import excepciones.ValidacionException;
import java.util.List;

public class UnidadDidactica {
    private int idU;
    private String acronimo;
    private String titulo;
    private String evaluacion;
    private String descripcion;
    

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDatos() throws ValidacionException{
        
      //  this.idU = utilidades.Utilidades.leerInt("introduce el ID de la unidad didactica");
        this.acronimo = utilidades.Utilidades.introducirCadena("introduce el acronimo");
        this.titulo = utilidades.Utilidades.introducirCadena("introduce el titulo");
        this.evaluacion = utilidades.Utilidades.introducirCadena("Introduce la evaluacion");
        this.descripcion = utilidades.Utilidades.introducirCadena("Introduce la descripcion");
    }
    
    
    
}
