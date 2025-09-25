package controlador;

import java.util.List;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import controlador.DaoImplementacionMySql;
import modelo.UnidadDidactica;
import utilidades.Utilidades;

public class Controlador {

    private DaoImplementacionMySql dao;

    public Controlador() {
        this.dao = DaoImplementacionMySql.getInstance();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Utilidades.leerInt(1, 7, "Elige opción: ");
            switch (opcion) {
                case 1:
                    //crearUnidad();
                    break;
                case 2:
                    //crearConvocatoria();
                    break;
                case 3:
                    //crearEnunciado();
                    break;
                case 4:
                    consultarEnunciadosPorUnidadDidactica();
                    break;
                case 5:
                    consultarConvocatoriasPorEnunciado();
                    break;
                case 6:
                    //visualizarDocumento();
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 7);
    }

    private void mostrarMenu() {
        System.out.println("\n--- MENÚ EXÁMENES ---");
        System.out.println("1. Crear unidad didáctica");
        System.out.println("2. Crear convocatoria");
        System.out.println("3. Crear enunciado de examen");
        System.out.println("4. Consultar enunciados por unidad");
        System.out.println("5. Consultar convocatorias de un enunciado");
        System.out.println("6. Visualizar documento asociado a enunciado");
        System.out.println("7. Salir");
    }

    public void consultarEnunciadosPorUnidadDidactica() {
        List<UnidadDidactica> unidades = dao.listarUnidadesDidacticas();
        boolean existe = true;
        if (unidades.isEmpty()) {
           existe = false;
        }
        if(!existe){
            System.out.println("No existen unidades didacticas");
            
        }else{
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

    public void consultarConvocatoriasPorEnunciado() {
        List<Enunciado> enunciados = dao.listarEnunciados();
         boolean existe = true;
        if (enunciados.isEmpty()) {
           existe = false;
        }
        if(!existe){
            System.out.println("No existen enunciados");
            
        }else{
        System.out.println("Enunciados disponibles");

        for (int i = 0; i < enunciados.size(); i++) {
            Enunciado e = enunciados.get(i);
            System.out.println(e.getIdE() + " - " + e.getDescripcion());
        }
        int idEnunciado = Utilidades.leerInt("Introduce el ID del enunciado: ");
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

    public void visualizarDocumento() {
        System.out.println("Funcionalidad para visualizar documento (pendiente).");
    }

    public List<ConvocatoriaExamen> listarConvocatorias() {
        return dao.listarConvocatorias();
    }
}
