package controlador;

import modelo.ConvocatoriaExamen;
import modelo.Enunciado;

public interface Dao {
        public Enunciado obtenerRuta(int IdE);
        public void asignarEnunciadoAConvocatoria(int idE, int idC);
        
        
}