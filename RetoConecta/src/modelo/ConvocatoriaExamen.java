package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ConvocatoriaExamen {
    private int idC;
    private String convocatoria;
    private String descripcion;
    private Date fecha;
    private String curso;
    private int IdE;  

    
    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }
    
    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getIdE() {
        return IdE;
    }

    public void setIdE(int IdE) {
        this.IdE = IdE;
    }
    public void setDatos(){
        
        this.idC = utilidades.Utilidades.leerInt("introduce el ID de la convocatoria");
        this.convocatoria = utilidades.Utilidades.introducirCadena("introduce la convocatoria");
        this.fecha = Date.valueOf(utilidades.Utilidades.introducirCadena("Introducir fecha"));
        this.descripcion = utilidades.Utilidades.introducirCadena("introduce la descripcion");
        this.curso = utilidades.Utilidades.introducirCadena("introducir curso");
        this.IdE = utilidades.Utilidades.leerInt("introducir ID del enunciado correspondiente");
    }
   

    

    
    
}
