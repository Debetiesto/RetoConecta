package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.ConvocatoriaExamen;
import utilidades.Utilidades;

public class Controlador {
    
    
    static DaoImplementacionMySql dao = DaoImplementacionMySql.getInstance();
    
    public static  List<ConvocatoriaExamen> listaConvocatorias(){
     return dao.listarConvocatorias();
    }
    
    
    public void iniciar(){
        int opc;
        System.out.println("******MENÚ******");
        System.out.println("1. Create.");
        System.out.println("2. Read.");
        System.out.println("3. Update.");
        System.out.println("4. Delete.");
        System.out.println("5. Salir.");
        opc = Utilidades.leerInt("ELIJA UNA OPCIÓN: ");

        switch (opc) {
            case 1: 
                listar();
                break;
            case 2: 
                System.out.println("Usted eligió la opcion 2.");
                break;
            case 3: 
                System.out.println("Usted eligió la opcion 3.");
                break;
            default: 
                System.out.println("Opcion incorrecta");
            break;
        }
    }

    private void listar() {
                dao.probarConexion(); // solo prueba la conexión
        
        List<ConvocatoriaExamen> convocatorias = dao.listarConvocatorias();
         if (!convocatorias.isEmpty()) {
            for (ConvocatoriaExamen convocatoria : convocatorias) {
                System.out.println("Convocatoria: " + convocatoria.getConvocatoria());
                System.out.println("Descripción: " + convocatoria.getDescripcion());
                System.out.println("Curso: " + convocatoria.getCurso());
                System.out.println("Fecha: " + convocatoria.getFecha());
               
            }
        } else {
            System.out.println("No se encontraron convocatorias.");
        }
    }
}
