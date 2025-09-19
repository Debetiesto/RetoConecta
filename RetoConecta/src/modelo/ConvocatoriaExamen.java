package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ConvocatoriaExamen {
    private int idC;
    private String convocatoria;
    private String descripcion;
    private LocalDate fecha;
    private String curso;
    private String ruta;
    private Enunciado enunciado;  

    
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Enunciado getEnunciados() {
        return enunciado;
    }

    public void setEnunciados(Enunciado enunciado) {
        this.enunciado = enunciado;
    }

    
    
}
