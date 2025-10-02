
package main;

import controlador.Controlador;
import excepciones.ValidacionException;

public class Main {


    public static void main(String[] args) throws ValidacionException {
        Controlador controlador = new Controlador();
        controlador.iniciar(); 
    }

}
