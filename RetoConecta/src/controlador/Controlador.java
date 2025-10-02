package controlador;

import java.util.List;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import controlador.DaoImplementacionMySql;
import excepciones.ValidacionException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import modelo.Dificultad;
import modelo.UnidadDidactica;
import utilidades.Utilidades;

public class Controlador {

    private DaoImplementacionMySql dao;

    public Controlador() {
        this.dao = DaoImplementacionMySql.getInstance();
    }

    public void iniciar() throws ValidacionException {
        precargarConvocatorias();
        int opcion;
        do {
            mostrarMenu();
            opcion = Utilidades.leerInt(1, 8, "Elige opción: ");
            try {
                switch (opcion) {
                    case 1:
                        crearUnidad();
                        break;
                    case 2:
                        crearConvocatoria();
                        break;
                    case 3:
                        crearEnunciado();
                        break;
                    case 4:
                        consultarEnunciadosPorUnidadDidactica();
                        break;
                    case 5:
                        consultarConvocatoriasPorEnunciado();
                        break;
                    case 6:
                        visualizarDocumento();
                        break;
                    case 7:
                        asignarEnunciadoAConvocatoria();
                        break;
                    case 8:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (ValidacionException ex) {
                System.out.println("❌ Error de validación: " + ex.getMessage());
            }
        } while (opcion != 8);
    }

    private void mostrarMenu() {
        System.out.println("\n--- MENÚ EXÁMENES ---");
        System.out.println("1. Crear unidad didáctica");
        System.out.println("2. Crear convocatoria");
        System.out.println("3. Crear enunciado de examen");
        System.out.println("4. Consultar enunciados por unidad");
        System.out.println("5. Consultar convocatorias de un enunciado");
        System.out.println("6. Visualizar documento asociado a enunciado");
        System.out.println("7.Asignar enunciado a convocatoria");
        System.out.println("8. Salir");
    }

    private void crearUnidad() throws ValidacionException {
        UnidadDidactica uni = new UnidadDidactica();
        uni.setDatos();
        int idUnidad;

        idUnidad = dao.crearUnidad(uni);
        if (idUnidad == -1) {
            System.out.println("Error al crear unidad.");

        } else {

            System.out.println("Unidad creada con IdU: " + idUnidad);

            String resp = utilidades.Utilidades.introducirCadena("¿Quieres asignar enunciados a esta unidad? (s/n): ");
            if (resp.equalsIgnoreCase("s")) {

                List<Enunciado> enunciados = dao.listarEnunciados();
                if (enunciados.isEmpty()) {
                    System.out.println("No hay enunciados disponibles.");

                } else {

                    System.out.println("Enunciados disponibles:");

                    for (int i = 0; i < enunciados.size(); i++) {
                        Enunciado e = enunciados.get(i);
                        System.out.println(e.getIdE() + " - " + e.getDescripcion());
                    }

                    boolean seguir = true;
                    while (seguir) {
                        int idE = utilidades.Utilidades.leerInt("Introduce el ID del enunciado a asignar: ");

                        boolean encontrado = false;
                        for (int i = 0; i < enunciados.size(); i++) {
                            if (enunciados.get(i).getIdE() == idE) {
                                encontrado = true;
                            }
                        }

                        if (!encontrado) {
                            System.out.println("❌ ID inválido, inténtalo de nuevo.");
                            throw new ValidacionException("El enunciado con ID " + idE + " no existe.");
                        }

                        seguir = utilidades.Utilidades.leerRespuesta("¿Quieres asignar otro enunciado? (s/n): ");
                    }
                }
            } else if (resp.equalsIgnoreCase("n")) {
                System.out.println("No se asignaron enunciados a esta unidad.");
            } else {
                throw new ValidacionException("Respuesta inválida. Solo se admite 's' o 'n'.");
            }
        }
    }

    private void crearConvocatoria() throws ValidacionException {
        ConvocatoriaExamen con = new ConvocatoriaExamen();
        con.setDatos();
        dao.crearConvocatoria(con);
    }

    public void consultarEnunciadosPorUnidadDidactica() throws ValidacionException {
        List<UnidadDidactica> unidades = dao.listarUnidadesDidacticas();
        boolean existe = true;
        if (unidades.isEmpty()) {
            existe = false;
        }
        if (!existe) {
            throw new ValidacionException("No hay unidad didactica.");
        } else {
            System.out.println("Unidades disponibles:");
            for (int i = 0; i < unidades.size(); i++) {
                UnidadDidactica u = unidades.get(i);
                System.out.println(u.getIdU() + " - " + u.getTitulo());
            }
            int idUnidad = Utilidades.leerInt("Introduce la unidad didáctica por id: ");
            List<Enunciado> lista = dao.enunciadoPorUnidadDidactica(idUnidad);

            if (!lista.isEmpty()) {
                System.out.println("Enunciados asociados a la unidad didáctica:");
                for (Enunciado en : lista) {
                    System.out.println("ID: " + en.getIdE());
                    System.out.println("Descripción: " + en.getDescripcion());
                    System.out.println("Ruta: " + en.getRuta());
                    System.out.println("-----------------------");
                }
            } else {
                System.out.println("No se encontraron enunciados.");
            }
        }
    }

    public void consultarConvocatoriasPorEnunciado() throws ValidacionException {
        List<Enunciado> enunciados = dao.listarEnunciados();
        boolean existe = true;
        if (enunciados.isEmpty()) {
            existe = false;
        }
        if (!existe) {
            System.out.println("No existen enunciados");

        } else {
            System.out.println("Enunciados disponibles");

            for (int i = 0; i < enunciados.size(); i++) {
                Enunciado e = enunciados.get(i);
                System.out.println(e.getIdE() + " - " + e.getDescripcion());
            }
            int idEnunciado = Utilidades.leerInt("Introduce el ID del enunciado: ");
            boolean existeEnunciado = false;

            for (int i = 0; i < enunciados.size(); i++) {
                Enunciado e = enunciados.get(i);
                if (e.getIdE() == idEnunciado) {
                    existeEnunciado = true;
                }
            }

            if (!existeEnunciado) {
                throw new ValidacionException("El enunciado con ID " + idEnunciado + " no existe.");
            }

            List<ConvocatoriaExamen> lista = dao.convocatoriasPorEnunciado(idEnunciado);

            if (!lista.isEmpty()) {
                for (ConvocatoriaExamen ce : lista) {
                    System.out.println("ID: " + ce.getIdC());
                    System.out.println("Convocatoria: " + ce.getConvocatoria());
                    System.out.println("Descripción: " + ce.getDescripcion());
                    System.out.println("Curso: " + ce.getCurso());
                    System.out.println("Fecha: " + ce.getFecha());
                    System.out.println("-----------------------");
                }
            } else {
                System.out.println("No se encontraron convocatorias.");
            }
        }
    }

    public void visualizarDocumento() throws ValidacionException {
        List<Enunciado> enunciados = dao.listarEnunciados();

        if (enunciados.isEmpty()) {
            System.out.println("No hay enunciados disponibles.");

        } else {

            System.out.println("Enunciados disponibles:");
            for (int i = 0; i < enunciados.size(); i++) {
                Enunciado e = enunciados.get(i);
                System.out.println(e.getIdE() + " - " + e.getDescripcion());
            }

            int idE = Utilidades.leerInt("Introduce el ID del enunciado que quieres visualizar:");
            Enunciado enun = dao.obtenerRuta(idE);

            if (enun == null) {
                throw new ValidacionException("El enunciado con ID " + idE + " no existe.");
            }

            if (enun.getRuta() == null || enun.getRuta().trim().isEmpty()) {
                throw new ValidacionException("El enunciado no tiene documento asociado.");
            }

            System.out.println("Ruta del documento: " + enun.getRuta());
            try {
                File archivo = new File(enun.getRuta());
                if (archivo.exists()) {
                    Desktop.getDesktop().open(archivo);
                } else {
                    System.out.println("El archivo no existe en la ruta especificada.");
                }
            } catch (IOException e) {
                System.out.println("Error al abrir el documento: " + e.getMessage());
            }
        }
    }

    public Enunciado obtenerRuta() throws ValidacionException {
        int idEnn;

        idEnn = Utilidades.leerInt("Introduce el id");

        return dao.obtenerRuta(idEnn);
    }

    private void asignarEnunciadoAConvocatoria() throws ValidacionException {
        System.out.println("Asignar un enunciado a una convocatoria existente:");
        int idC;
        List<ConvocatoriaExamen> convocatorias = dao.listarConvocatorias();
        if (convocatorias.isEmpty()) {
            System.out.println("No hay convocatorias disponibles.");

        } else {

            System.out.println("Convocatorias disponibles:");
            for (int i = 0; i < convocatorias.size(); i++) {
                ConvocatoriaExamen c = convocatorias.get(i);
                System.out.println(c.getIdC() + " - " + c.getConvocatoria() + " (" + c.getCurso() + ")");
            }

            idC = Utilidades.leerInt("Introduce el ID de la convocatoria: ");

            boolean existeConvocatoria = false;
            for (int i = 0; i < convocatorias.size(); i++) {
                if (convocatorias.get(i).getIdC() == idC) {
                    existeConvocatoria = true;
                }
            }
            if (!existeConvocatoria) {
                throw new ValidacionException("La convocatoria con ID " + idC + " no existe.");
            }

            List<Enunciado> enunciados = dao.listarEnunciados();
            if (enunciados.isEmpty()) {
                System.out.println("No hay enunciados disponibles.");

            } else {

                System.out.println("Enunciados disponibles:");
                for (int i = 0; i < enunciados.size(); i++) {
                    Enunciado e = enunciados.get(i);
                    System.out.println(e.getIdE() + " - " + e.getDescripcion());
                }

                int idE = Utilidades.leerInt("Introduce el ID del enunciado a asignar: ");

                boolean existeEnunciado = false;
                for (int i = 0; i < enunciados.size(); i++) {
                    if (enunciados.get(i).getIdE() == idE) {
                        existeEnunciado = true;
                    }
                }
                if (!existeEnunciado) {
                    throw new ValidacionException("El enunciado con ID " + idE + " no existe.");
                }

                for (int i = 0; i < convocatorias.size(); i++) {
                    if (convocatorias.get(i).getIdC() == idC) {
                        convocatorias.get(i).setIdE(idE);
                        System.out.println("✅ Enunciado " + idE + " asignado a convocatoria " + idC);
                    }
                }

                dao.guardarConvocatorias(convocatorias);
            }
        }
    }

    private void crearEnunciado() throws ValidacionException {
        Enunciado e = new Enunciado();
        e.setDatos();

        try {
            int id = dao.crearEnunciado(e);
            System.out.println("✅ Enunciado creado con IdE: " + id);
        } catch (Exception ex) {
            System.err.println("❌ Error al crear Enunciado: " + ex.getMessage());
        }
    }

    private void precargarConvocatorias() {
        List<ConvocatoriaExamen> existentes = dao.listarConvocatorias();
        if (!existentes.isEmpty()) {
            System.out.println("✅ Convocatorias ya precargadas.");

        } else {

            ConvocatoriaExamen c1 = new ConvocatoriaExamen();
            c1.setConvocatoria("Convocatoria Enero");
            c1.setDescripcion("Examen de álgebra lineal");
            c1.setCurso("2024-2025");
            c1.setFecha(java.sql.Date.valueOf("2025-01-15"));
            c1.setIdE(1);
            ConvocatoriaExamen c2 = new ConvocatoriaExamen();
            c2.setConvocatoria("Convocatoria Junio");
            c2.setDescripcion("Examen de cinemática");
            c2.setCurso("2024-2025");
            c2.setFecha(java.sql.Date.valueOf("2025-06-20"));
            c2.setIdE(2);

            ConvocatoriaExamen c3 = new ConvocatoriaExamen();
            c3.setConvocatoria("Convocatoria Extra");
            c3.setDescripcion("Examen de recursión en Python");
            c3.setCurso("2024-2025");
            c3.setFecha(java.sql.Date.valueOf("2025-07-10"));
            c3.setIdE(3);

            dao.crearConvocatoria(c1);
            dao.crearConvocatoria(c2);
            dao.crearConvocatoria(c3);

            System.out.println("✅ Precarga de convocatorias con enunciados completada.");
        }
    }

}
