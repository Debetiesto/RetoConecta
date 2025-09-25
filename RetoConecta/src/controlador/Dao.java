package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.ConvocatoriaExamen;
import modelo.UnidadDidactica;

public interface Dao {
       // public ConvocatoriaExamen leerConvocatoria(int idC);
        
        //public ConvocatoriaExamen leerConvocatoria(int idC);
        public  List<ConvocatoriaExamen> listarConvocatorias();
        
        public void crearCovocatoria(ConvocatoriaExamen convo);
        
        public void crearUnidad(UnidadDidactica unid);
}