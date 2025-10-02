package modelo;

import excepciones.ValidacionException;
import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import utilidades.Utilidades;

public class ConvocatoriaExamen implements Serializable {

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

    public void setDatos() throws ValidacionException {

        this.convocatoria = utilidades.Utilidades.introducirCadena("introduce la convocatoria");
        this.descripcion = utilidades.Utilidades.introducirCadena("introduce la descripcion");
        try {
            String fechaInput = Utilidades.introducirCadena("Introduce la fecha (YYYY-MM-DD): ");
            LocalDate fechaLocal = LocalDate.parse(fechaInput);

            LocalDate hoy = LocalDate.now();
            LocalDate limiteFutura = hoy.plusYears(30);

            if (fechaLocal.isBefore(hoy)) {
                throw new ValidacionException("La fecha no puede ser anterior a hoy.");
            }
            if (fechaLocal.isAfter(limiteFutura)) {
                throw new ValidacionException("La fecha es muy lejana en el tiempo.");
            }

            this.fecha = Date.valueOf(fechaLocal);

        } catch (IllegalArgumentException e) {
            throw new ValidacionException("Formato de fecha inválido. Usa YYYY-MM-DD.");
        }

        this.curso = Utilidades.introducirCadena("Introduce el curso (YYYY-YYYY): ");
        if (!this.curso.matches("\\d{4}-\\d{4}")) {
            throw new ValidacionException("Formato de curso inválido. Debe ser YYYY-YYYY.");
        }
    }
}
