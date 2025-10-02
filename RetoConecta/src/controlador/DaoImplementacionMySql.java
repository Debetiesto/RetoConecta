package controlador;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.Dificultad;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private static final String FICHERO_CONVOCATORIAS = "convocatorias.dat";

    // Atributos
    private Connection con;
    private PreparedStatement stmt;
    // Sentencias SQL
    
    final String CREARUNIDAD = "INSERT INTO UnidadDidactica (Acronimo, Titulo, Evaluacion, Descripcion) VALUES (?,?,?,?)";
    final String ASIGNARENUNCIADO_UNIDAD = "INSERT INTO Tiene (IdE, IdU) VALUES (?, ?)";

    final String ENUNEX = "SELECT * FROM ConvocatoriaExamen WHERE IdE=?";
    final String ENUNIDAD = "SELECT e.* FROM Enunciado e JOIN Tiene t ON e.IdE = t.IdE WHERE t.IdU=?";
    final String LISTAR_UNIDADES = "SELECT * FROM UnidadDidactica";
    final String LISTAR_ENUNCIADOS = "SELECT * FROM Enunciado";
    final String VERRUTA = "SELECT RUTA FROM ENUNCIADO WHERE IDE = ?";
    final String ASIGNARENUNCIADO = "UPDATE CONVOCATORIAEXAMEN SET IDE = ? WHERE IdC = ?";
    final String CREARENUM = "INSERT INTO Enunciado (Descripcion, Nivel, Disponible, Ruta) VALUES (?, ?, ?, ?)";

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
            openConnection();
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
    public void crearConvocatoria(ConvocatoriaExamen convo) {
        List<ConvocatoriaExamen> lista = listarConvocatorias();
        int nuevoId = lista.stream()
                .mapToInt(ConvocatoriaExamen::getIdC)
                .max()
                .orElse(0) + 1;

        convo.setIdC(nuevoId);
        lista.add(convo);
        guardarConvocatorias(lista);
    }

    @Override
    public List<ConvocatoriaExamen> listarConvocatorias() {
        List<ConvocatoriaExamen> lista = new ArrayList<>();
        ObjectInputStream ois = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(FICHERO_CONVOCATORIAS);
            ois = new ObjectInputStream(fis);
            lista = (List<ConvocatoriaExamen>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }

        return lista;
    }

    public void guardarConvocatorias(List<ConvocatoriaExamen> lista) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(FICHERO_CONVOCATORIAS);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar convocatorias", e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    @Override
    public int crearUnidad(UnidadDidactica unid) {
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            openConnection();
            stmt = con.prepareStatement(
                    CREARUNIDAD,
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, unid.getAcronimo());
            stmt.setString(2, unid.getTitulo());
            stmt.setString(3, unid.getEvaluacion());
            stmt.setString(4, unid.getDescripcion());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    unid.setIdU(idGenerado);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResult(rs);
            closeConnection();
        }
        return idGenerado;
    }

    @Override
    public void asignarEnunciadoAUnidad(int idE, int idU) {
        try {
            openConnection();
            stmt = con.prepareStatement(ASIGNARENUNCIADO_UNIDAD);
            stmt.setInt(1, idE);
            stmt.setInt(2, idU);
            stmt.executeUpdate();
            System.out.println("Enunciado " + idE + " asignado a unidad " + idU);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<ConvocatoriaExamen> convocatoriasPorEnunciado(int idE) {
        List<ConvocatoriaExamen> todas = listarConvocatorias();
        List<ConvocatoriaExamen> resultado = new ArrayList<>();

        for (ConvocatoriaExamen c : todas) {
            if (c.getIdE() == idE) {
                resultado.add(c);
            }
        }
        return resultado;
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
                u.setNivel(Dificultad.obtenerPorNombre(rs.getString("Nivel")));
                u.setDisponible(rs.getBoolean("Disponible"));
                u.setRuta(rs.getString("Ruta"));
                lista.add(u);
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

    @Override
    public int crearEnunciado(Enunciado enun) {
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            openConnection();
            stmt = con.prepareStatement(CREARENUM, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, enun.getDescripcion());
            stmt.setString(2, enun.getNivel().getNombre());
            stmt.setBoolean(3, enun.isDisponible());
            stmt.setString(4, enun.getRuta());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    enun.setIdE(idGenerado);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al crear Enunciado", ex);
        } finally {
            closeResult(rs);
            closeConnection();
        }

        return idGenerado;
    }

}
