package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import modelo.UnidadDidactica;

public interface Dao {

    
      public void asignarEnunciadoAUnidad(int idE, int idU);

    public void crearConvocatoria(ConvocatoriaExamen convo);


    public List<ConvocatoriaExamen> listarConvocatorias();

       // public ConvocatoriaExamen leerConvocatoria(int idC);
        
        //public ConvocatoriaExamen leerConvocatoria(int idC);
        


    public List<ConvocatoriaExamen> convocatoriasPorEnunciado(int idE);
    

    public int crearUnidad(UnidadDidactica unid);

    public List<Enunciado> enunciadoPorUnidadDidactica(int idU);

    public List<UnidadDidactica> listarUnidadesDidacticas();

    public List<Enunciado> listarEnunciados();


    public Enunciado obtenerRuta(int IdE);
    
    public int crearEnunciado(Enunciado enun);


    public void asignarEnunciadoAConvocatoria(int idE, int idC);
}

