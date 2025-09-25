package controlador;

import static controlador.Controlador.dao;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import modelo.Enunciado;
import utilidades.Utilidades;

public class Controlador {

    static DaoImplementacionMySql dao = DaoImplementacionMySql.getInstance();


    public static void main(String[] args) {
    dao.probarConexion(); // solo prueba la conexión
        int opc;
        do {
            mostrarMenu();
            opc = Utilidades.leerInt("***** Selecciona la opción que deseas realizar *****");
            switch (opc) {
                case 1:
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    visualizarDocumento();
                    break;
                case 6:
                    asignarEnunciadoAConvocatoria();
                    break;
                default:
                    if (Utilidades.introducirCadena("Opción no válida.\n¿Deseas continuar?").equalsIgnoreCase("si")) {
                        opc = 11;
                    }
                    break;
            }
        } while (opc != 0);
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

    public static Enunciado obtenerRuta() {
        int idEnn;

        idEnn = Utilidades.leerInt("Introduce el id");

        return dao.obtenerRuta(idEnn);
    }

    /*
   public static ConvocatoriaExamen leerConvocatoria(int idC) {
        return dao.leerConvocatoria(idC);
        
    }
     */
    private static void mostrarMenu() {
        System.out.println("*** Bienvenido al programa de gestión de exámenes académicos ***");
        System.out.println("1.  Crear una unidad didáctica y una convocatoria de examen.");
        System.out.println("2.  Crear un enunciado de examen y asociar unidades didácticas + convocatoria.");
        System.out.println("3.  Consultar los enunciados que tratan una unidad didáctica concreta.");
        System.out.println("4.  Consultar en qué convocatorias se ha utilizado un enunciado concreto.");
        System.out.println("5.  Visualizar el documento de texto asociado a un enunciado.");
        System.out.println("6.  Asignar un enunciado a una convocatoria existente.");
        System.out.println("0.  Salir del programa.");
    }

    private static void asignarEnunciadoAConvocatoria() {
    System.out.println("Asignar un enunciado a una convocatoria existente:");

    int idE = Utilidades.leerInt("Introduce el ID del enunciado:");
    int idC = Utilidades.leerInt("Introduce el ID de la convocatoria:");

    dao.asignarEnunciadoAConvocatoria(idE, idC);   }
}
