package controlador;

import Modelo.Dificultad;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import modelo.UnidadDidactica;

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
    final String CREARUNIDAD = "INSTERT INTO unidaddidactica (IdU, Acronimo, Titulo, Evaluacion, Descripcion) VALUES (?,?,?,?,?)";
    final String CREARCONVOCATORIA = "INSERT INTO convocatoriaexamen (IdC, Convocatoria, Descripcion, Fecha, Curso, IdE) VALUES (?,?,?,?,?,?)";
    final String ENUNEX = "SELECT * FROM ConvocatoriaExamen WHERE IdE=?";
    final String ENUNIDAD = "SELECT e.* FROM Enunciado e JOIN Tiene t ON e.IdE = t.IdE WHERE t.IdU=?";
    final String LISTAR_UNIDADES = "SELECT * FROM UnidadDidactica";
    final String LISTAR_ENUNCIADOS = "SELECT * FROM Enunciado";

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
                                con.setFecha(rs.getDate("Fecha"));
                                con.setIdE(rs.getInt("IdE"));
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

    @Override
    public void crearCovocatoria(ConvocatoriaExamen convo) {
        try{
            openConnection();
            stmt = con.prepareStatement(CREARCONVOCATORIA);
            
            stmt.setInt(1, convo.getIdC());
            stmt.setString(2, convo.getConvocatoria());
            stmt.setString(3, convo.getDescripcion());
            stmt.setDate(4, convo.getFecha());
            stmt.setString(5, convo.getCurso());
            stmt.setInt(6, convo.getIdE());
            
            stmt.executeUpdate();
            
        }catch(SQLException e){
            
        }finally{
            closeConnection();
        }
    }

    @Override
    public void crearUnidad(UnidadDidactica unid) {
        try{
            openConnection();
            stmt = con.prepareStatement(CREARUNIDAD);
            
            stmt.setInt(1, unid.getIdU());
            stmt.setString(2, unid.getAcronimo());
            stmt.setString(3, unid.getTitulo());
            stmt.setString(4, unid.getEvaluacion());
            stmt.setString(5, unid.getDescripcion());
            
            stmt.executeUpdate();
            
        }catch(SQLException e){
            
        }finally{
            closeConnection();
        }
    }

    @Override
    public List<ConvocatoriaExamen> convocatoriasPorEnunciado(int idE) {
        List<ConvocatoriaExamen> lista = new ArrayList<>();
        try {
            openConnection();
            stmt = con.prepareStatement(ENUNEX);
            stmt.setInt(1, idE);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ConvocatoriaExamen c = new ConvocatoriaExamen();
                c.setIdC(rs.getInt("IdC"));
                c.setConvocatoria(rs.getString("Convocatoria"));
                c.setDescripcion(rs.getString("Descripcion"));
                c.setCurso(rs.getString("Curso"));
                c.setFecha(rs.getDate("Fecha"));
                lista.add(c);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return lista;
    }

    @Override
    public List<Enunciado> enunciadoPorUnidadDidactica(int idU) {
         List<Enunciado> lista = new ArrayList<>();

        try {
            openConnection();
            stmt = con.prepareStatement(ENUNIDAD);
            stmt.setInt(1, idU);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Enunciado e = new Enunciado();
                e.setIdE(rs.getInt("IdE"));
                e.setDescripcion(rs.getString("Descripcion"));
                String nivelStr = rs.getString("Nivel");
                if (nivelStr != null && !nivelStr.isEmpty()) {
                    e.setNivel(Dificultad.valueOf(nivelStr.toUpperCase()));
                }

                e.setDisponible(rs.getBoolean("Disponible"));
                e.setRuta(rs.getString("Ruta"));

                lista.add(e);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return lista;

    }

    @Override
    public List<UnidadDidactica> listarUnidadesDidacticas() {
        List<UnidadDidactica> lista = new ArrayList<>();
        ResultSet rs = null;
        try {
            openConnection();
            stmt = con.prepareStatement(LISTAR_UNIDADES);
            rs = stmt.executeQuery();
            while (rs.next()) {
                UnidadDidactica u = new UnidadDidactica();
                u.setIdU(rs.getInt("IdU"));
                u.setAcronimo(rs.getString("Acronimo"));
                u.setTitulo(rs.getString("Titulo"));
                u.setEvaluacion(rs.getString("Evaluacion"));
                u.setDescripcion(rs.getString("Descripcion"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResult(rs);
            closeConnection();
        }
        return lista;
    }

    @Override
    public List<Enunciado> listarEnunciados() {
       List<Enunciado> lista = new ArrayList<>();
        ResultSet rs = null;
        try {
            openConnection();
            stmt = con.prepareStatement(LISTAR_ENUNCIADOS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Enunciado u = new Enunciado();
                u.setIdE(rs.getInt("IdE"));
                u.setDescripcion(rs.getString("Descripcion"));
                String nivelStr = rs.getString("Nivel");
                try {
                    u.setNivel(Dificultad.valueOf(nivelStr.toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    u.setNivel(null);
                }
                u.setDisponible(rs.getBoolean("Disponible"));
                u.setRuta(rs.getString("Ruta"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResult(rs);
            closeConnection();
        }
        return lista;
    }
    
}
