

package modelo;

public enum Dificultad {
    ALTA("Alta"), MEDIA("Media"), BAJA("Baja");
    
    private String nombre;

	Dificultad(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    public static Dificultad obtenerPorNombre(String nombre) {
		for (Dificultad n : Dificultad.values()) {
			if (n.getNombre().equalsIgnoreCase(nombre)) {
				return n;
			}
		}
		
		throw new IllegalArgumentException("Nivel no válido: " + nombre);

	}

}
