package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.ConvocatoriaExamen;
import modelo.UnidadDidactica;

public class Main {
    
    
    static DaoImplementacionMySQL dao = DaoImplementacionMySQL.getInstance();
    
    
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
                System.out.println("IdE: " + convocatoria.getIdE());
               
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
    public void crearConvocat(){
        ConvocatoriaExamen co = new ConvocatoriaExamen();
        co.setDatos();
        crearCovocatoria(co);
    }
    public static void crearCovocatoria(ConvocatoriaExamen convo){
        
    }
    public void crearUnidadDidactica(){
        UnidadDidactica uni = new UnidadDidactica();
        uni.setDatos();
        crearUnidad(uni);
    }
    public static void crearUnidad(UnidadDidactica unid){
        
    }

    
}
