package src.florida.aev_mvc;

import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/population";
    private Connection connection;

    /**
     * Establece una conexión a la base de datos utilizando las credenciales proporcionadas.
     *
     * @param user El nombre de usuario para la conexión.
     * @param password La contraseña para el usuario.
     * @return `true` si la conexión fue exitosa; `false` en caso contrario.
     */
    public boolean connect(String user, String password) {
        try {
            connection = DriverManager.getConnection(URL, user, password);
            return true;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cierra la conexión activa a la base de datos, si existe.
     */
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * Ejecuta una consulta SQL de tipo SELECT.
     *
     * @param query La consulta SQL a ejecutar.
     * @return Un objeto `ResultSet` que contiene los resultados de la consulta.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public int executeUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(query);
    }

    /**
     * Prepara una consulta SQL parametrizada.
     *
     * @param query La consulta SQL a preparar.
     * @return Un objeto `PreparedStatement` listo para usarse.
     * @throws SQLException Si ocurre un error al preparar la consulta.
     */
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    /**
     * Verifica si existe una conexión activa a la base de datos.
     *
     * @return `true` si hay una conexión activa; `false` en caso contrario.
     */
    public boolean isConnected() {
        return connection != null;
    }
    /**
     * Ejecuta una consulta SQL parametrizada de tipo INSERT, UPDATE o DELETE.
     *
     * @param query La consulta SQL a ejecutar.
     * @param parameters Los valores a asignar a los parámetros de la consulta.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public void executeUpdatePrepared(String query, String... parameters) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i]);
            }
            preparedStatement.executeUpdate();
        }
    }
    /**
     * Obtiene el nombre del usuario actual de la conexión.
     *
     * @return El nombre de usuario de la conexión actual.
     * @throws SQLException Si no hay conexión activa o si ocurre un error al obtener el usuario.
     */
    public String getCurrentUser() throws SQLException {
        if (connection == null) {
            throw new SQLException("No hay conexión activa.");
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT USER()")) {
            if (rs.next()) {
                return rs.getString(1).split("@")[0]; // Extrae el nombre de usuario sin el host
            } else {
                throw new SQLException("No se pudo determinar el usuario actual.");
            }
        }
    }
    public Connection getConnection() {
        return connection;
    }

    


}
