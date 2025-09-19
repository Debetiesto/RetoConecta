package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ConvocatoriaExamen;
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


    /**
     * 
     * @param El método listarConvocatorias es de prueba, borrar al final. 
     */
    @Override
	public List<ConvocatoriaExamen> listarConvocatorias() {
             List<ConvocatoriaExamen> lista = new ArrayList<>();
		ResultSet rs = null;
		ConvocatoriaExamen con = null;
                Connection conn = null;


		try {
			openConnection();
			stmt = this.con.prepareStatement(VERTODO);
			//stmt.setInt(1, idC);
                    
                        rs = stmt.executeQuery();

			while (rs.next()) {
				con = new ConvocatoriaExamen();
				con.setConvocatoria(rs.getString("Convocatoria"));
				con.setDescripcion(rs.getString("Descripcion"));
				 con.setCurso(rs.getString("Curso"));
                                    java.sql.Date sqlDate = rs.getDate("Fecha");
                                if (sqlDate != null) {
                                 con.setFecha(sqlDate.toLocalDate());
                                    }
                                lista.add(con);
                                

				
			}
		} catch (SQLException e) {
			 e.printStackTrace();
                         throw new RuntimeException("Error al leer datos", e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lista;

	}



}
