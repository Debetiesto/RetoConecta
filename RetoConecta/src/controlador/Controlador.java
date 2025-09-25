package controlador;


//import static controlador.Controlador.dao;
import java.util.List;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import controlador.DaoImplementacionMySql;
import modelo.UnidadDidactica;
import utilidades.Utilidades;

public class Controlador {
    
    public Controlador() {
        this.dao = DaoImplementacionMySql.getInstance();
    }
    /*
   public static ConvocatoriaExamen leerConvocatoria(int idC) {
        return dao.leerConvocatoria(idC);
        
    }
     */


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
        System.out.println("7. Asignar un enunciado a una convocatoria existente");
        System.out.println("8. Salir");
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

    public static void visualizarDocumento() {
        int idE = Utilidades.leerInt("Introduce el ID del enunciado que quieres visualizar:");
        Enunciado enun = dao.obtenerRuta(idE);

        if (enun != null && enun.getRuta() != null) {
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
        } else {
            System.out.println("No se encontró el enunciado o no tiene documento asociado.");
        }
    }


    public List<ConvocatoriaExamen> listarConvocatorias() {
        return dao.listarConvocatorias();
    }
    private static void asignarEnunciadoAConvocatoria() {
    System.out.println("Asignar un enunciado a una convocatoria existente:");

    int idE = Utilidades.leerInt("Introduce el ID del enunciado:");
    int idC = Utilidades.leerInt("Introduce el ID de la convocatoria:");

    dao.asignarEnunciadoAConvocatoria(idE, idC);   }
  
  
    public static Enunciado obtenerRuta() {
        int idEnn;

        idEnn = Utilidades.leerInt("Introduce el id");

        return dao.obtenerRuta(idEnn);
    }
  
}

