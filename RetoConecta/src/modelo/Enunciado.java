package modelo;

import java.util.List;

public class Enunciado {

    private int idE;
    private String descripcion;
    private Dificultad nivel;
    private boolean disponible;
    private String ruta;
    private List<UnidadDidactica> unidades;

    public Enunciado(int idE, String descripcion, Dificultad nivel, boolean disponible, String ruta, List<UnidadDidactica> unidades) {
        this.idE = idE;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.disponible = disponible;
        this.ruta = ruta;
        this.unidades = unidades;
    }
    
    
    
    
    public Enunciado(List<UnidadDidactica> unidades) {
        this.unidades = unidades;
    }

    public Enunciado() {
        
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dificultad getNivel() {
        return nivel;
    }

    public void setNivel(Dificultad nivel) {
        this.nivel = nivel;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public List<UnidadDidactica> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadDidactica> unidades) {
        this.unidades = unidades;
    }

}
