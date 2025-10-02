package modelo;

import excepciones.ValidacionException;
import java.util.ArrayList;
import java.util.List;
import utilidades.Utilidades;

public class Enunciado {

    private int idE;
    private String descripcion;
    private Dificultad nivel;
    private boolean disponible;
    private String ruta;
    private List<UnidadDidactica> unidades;

    public Enunciado(List<UnidadDidactica> unidades) {
        this.unidades = unidades;
    }

    public Enunciado() {
        unidades = new ArrayList<>();
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

    public void setDatos() throws ValidacionException {
        String nivelStr;
        this.descripcion = Utilidades.introducirCadena("Introduce la descripción del enunciado: ");

        nivelStr = Utilidades.introducirCadena("Introduce el nivel (ALTA, MEDIA, BAJA): ");
        try {
            this.nivel = Dificultad.obtenerPorNombre(nivelStr);
        } catch (IllegalArgumentException e) {
            throw new ValidacionException("Nivel no válido. Solo: ALTA, MEDIA o BAJA.");
        }

        this.disponible = Utilidades.leerRespuesta("¿Está disponible? (s/n): ");

        this.ruta = Utilidades.introducirCadena("Introduce la ruta del documento (ej: docs/enunciado1.pdf): ");
    }
    
        public void agregarUnidades(UnidadDidactica u) {
        unidades.add(u);
    }

}
