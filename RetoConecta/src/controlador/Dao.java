package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import modelo.UnidadDidactica;

public interface Dao {


    public void crearCovocatoria(ConvocatoriaExamen convo);
        
    public void crearUnidad(UnidadDidactica unid);
    
    public List<ConvocatoriaExamen> listarConvocatorias();

       // public ConvocatoriaExamen leerConvocatoria(int idC);
        
        //public ConvocatoriaExamen leerConvocatoria(int idC);
        public  List<ConvocatoriaExamen> listarConvocatorias();
        
        public void crearCovocatoria(ConvocatoriaExamen convo);
        
        public void crearUnidad(UnidadDidactica unid);
        


    public List<ConvocatoriaExamen> convocatoriasPorEnunciado(int idE);

    public List<Enunciado> enunciadoPorUnidadDidactica(int idU);

    public List<UnidadDidactica> listarUnidadesDidacticas();

    public List<Enunciado> listarEnunciados();

  
    public Enunciado obtenerRuta(int IdE);
  
    public void asignarEnunciadoAConvocatoria(int idE, int idC);
}

