package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.ConvocatoriaExamen;

public interface Dao {
       // public ConvocatoriaExamen leerConvocatoria(int idC);
        
        //public ConvocatoriaExamen leerConvocatoria(int idC);
        public  List<ConvocatoriaExamen> listarConvocatorias();
}