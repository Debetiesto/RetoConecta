package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.ConvocatoriaExamen;

public class Main {
    
    
    static DaoImplementacionMySql dao = DaoImplementacionMySql.getInstance();
    
    
    public static void main(String[] args) {
        //DaoImplementacionMySql dao = DaoImplementacionMySql.getInstance();
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
        
        
        
       /* List<ConvocatoriaExamen> convocatorias = dao.listarConvocatorias();

        if (!convocatorias.isEmpty()) {
            for (ConvocatoriaExamen convocatoria : convocatorias) {
                System.out.println("Convocatoria: " + convocatoria.getConvocatoria());
                System.out.println("Descripción: " + convocatoria.getDescripcion());
                System.out.println("Curso: " + convocatoria.getCurso());
                System.out.println("Fecha: " + convocatoria.getFecha());
               
            }
        } else {
            System.out.println("No se encontraron convocatorias.");
        }*/
    }
    
    public static  List<ConvocatoriaExamen> listaConvocatorias(){
     return dao.listarConvocatorias();
    }
    

}
