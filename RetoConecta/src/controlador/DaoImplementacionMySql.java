package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import modelo.Enunciado;

public final class DaoImplementacionMySql implements Dao {

    private static DaoImplementacionMySql instance;

    // Atributo para conexion
    private ResourceBundle configFile;
    private String urlBD, userBD, passwordBD;
    // Atributos
    private Connection con;
    private PreparedStatement stmt;

    // Sentencias SQL
    final String VERTODO = "SELECT * FROM CONVOCATORIAEXAMEN";
    final String VERRUTA = "SELECT RUTA FROM ENUNCIADO WHERE IDE = ?";
    final String ASIGNARENUNCIADO = "UPDATE CONVOCATORIAEXAMEN SET IDE = ? WHERE IdC = ?";

    private DaoImplementacionMySql() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Esperando...");
        }
        this.configFile = ResourceBundle.getBundle("modelo.configClass");
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
    }

    public static DaoImplementacionMySql getInstance() {
        if (instance == null) {
            instance = new DaoImplementacionMySql();
        }
        return instance;
    }

    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
            /*
			 * con = DriverManager.getConnection(
			 * "jdbc:mysql://localhost:3306/tienda_brico?serverTimezone=Europe/Madrid&useSSL=false",
			 * "root", "abcd*1234");
             */
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResult(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean probarConexion() {
        try {
            openConnection(); // llama al private
            if (con != null) {
                System.out.println("¡Conexión exitosa a la base de datos!");
                return true;
            } else {
                System.out.println("No se pudo conectar a la base de datos.");
                return false;
            }
        } finally {
            closeConnection();
        }
    }

    @Override
    public Enunciado obtenerRuta(int idE) {
        ResultSet rs = null;
        Enunciado enun = null;

        try {
            openConnection();
            stmt = con.prepareStatement(VERRUTA);
            stmt.setInt(1, idE);
            rs = stmt.executeQuery();

            if (rs.next()) {
                enun = new Enunciado();
                enun.setRuta(rs.getString("Ruta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer datos", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return enun;
    }

    @Override
    public void asignarEnunciadoAConvocatoria(int idE, int idC) {

        int filasModificadas;

        try {
            openConnection();
            stmt = con.prepareStatement(ASIGNARENUNCIADO);
            stmt.setInt(1, idE);
            stmt.setInt(2, idC);
            filasModificadas = stmt.executeUpdate();
            if (filasModificadas > 0) {
                System.out.println("Enunciado actualizado. ");
            } else {
                System.out.println("No se encontró este enunciado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer datos", e);
        } finally {
                closeConnection();
        }
    }

}
