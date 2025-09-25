package controlador;

import modelo.ConvocatoriaExamen;
import modelo.Enunciado;

import modelo.UnidadDidactica;

public interface Dao {
    // public ConvocatoriaExamen leerConvocatoria(int idC);

    //public ConvocatoriaExamen leerConvocatoria(int idC);
    public List<ConvocatoriaExamen> listarConvocatorias();

    public List<ConvocatoriaExamen> convocatoriasPorEnunciado(int idE);

    public List<Enunciado> enunciadoPorUnidadDidactica(int idU);

    public List<UnidadDidactica> listarUnidadesDidacticas();

    public List<Enunciado> listarEnunciados();
  
    public Enunciado obtenerRuta(int IdE);
  
    public void asignarEnunciadoAConvocatoria(int idE, int idC);
}

