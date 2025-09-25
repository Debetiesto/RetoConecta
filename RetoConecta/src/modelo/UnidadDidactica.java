package modelo;

import java.util.List;

public class UnidadDidactica {

    private int idU;
    private String acronimo;
    private String titulo;
    private String evaluacion;
    private String descripcion;
    private List<Enunciado> enunciados;

    public UnidadDidactica(List<Enunciado> enunciados) {
        this.enunciados = enunciados;
    }

    public UnidadDidactica() {

    }

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

    public List<Enunciado> getEnunciados() {
        return enunciados;
    }

    public void setEnunciados(List<Enunciado> enunciados) {
        this.enunciados = enunciados;
    }

}
