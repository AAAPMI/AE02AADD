package src.florida.aev_mvc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserController {
    private LoginView loginView;
    private MainView mainView;
    private DatabaseManager databaseManager;

    /**
     * Constructor de la clase UserController.
     * Inicializa el controlador con las vistas de login y la instancia de DatabaseManager.
     * 
     * @param databaseManager El manejador de la base de datos utilizado para la conexión y consultas.
     * @param loginView La vista de login utilizada para la autenticación de usuario.
     */
    public UserController(DatabaseManager databaseManager, LoginView loginView) {
        this.databaseManager = databaseManager;
        this.loginView = loginView;

        this.loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    /**
     * Muestra la vista de login al usuario.
     * 
     * Esta función se encarga de crear una nueva instancia de la vista de login y hacerla visible.
     * Además, agrega el listener para el botón de login.
     */
    private void showLoginView() {
        loginView = new LoginView();
        loginView.setVisible(true);

        loginView.getLoginButton().addActionListener(e -> handleLogin());
    }

    /**
     * Maneja el inicio de sesión del usuario.
     * 
     * Esta función valida las credenciales del usuario, realiza la conexión a la base de datos
     * y muestra un mensaje indicando si el login fue exitoso o fallido.
     */
    private void handleLogin() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        String hashedPassword = MD5Hash.MD5HashS(password);
        databaseManager.connect(username, hashedPassword);
        if(databaseManager.connect(username, hashedPassword)) {
        	JOptionPane.showMessageDialog(loginView, "Login exitoso.");
            loginView.dispose();
            showMainView();
        }else {
        	JOptionPane.showMessageDialog(loginView, "Fallo de la autentificacion de usuario y contraseña");

        }
        
    }

    /**
     * Muestra la vista principal después de un login exitoso.
     * 
     * Esta función inicializa y muestra la vista principal, y agrega listeners para los botones
     * de registrar usuario, importar CSV, realizar consultas y exportar CSV.
     */
    private void showMainView() {
        mainView = new MainView();
        mainView.setVisible(true);

        mainView.getRegisterUserButton().addActionListener(e -> handleRegisterUser());
        mainView.getImportCSVButton().addActionListener(e -> handleImportCSV());
        mainView.getQueryButton().addActionListener(e -> handleQuery());
        mainView.getExportCSVButton().addActionListener(e -> handleExportCSV());
        mainView.getLogoutButton().addActionListener(e -> handleLogout());
    }

    /**
     * Maneja el registro de un nuevo usuario.
     * 
     * Este método verifica si el usuario tiene permisos de administrador. Si tiene permisos,
     * permite al usuario registrar un nuevo usuario proporcionando su nombre, contraseña y confirmación.
     * Los datos se insertan en la base de datos.
     */
    private void handleRegisterUser() {
        try {
            String currentUser = databaseManager.getCurrentUser();
            ResultSet rs = databaseManager.executeQuery("SELECT type FROM users WHERE login = '" + currentUser + "'");
            if (rs.next() && !"admin".equalsIgnoreCase(rs.getString("type"))) {
                JOptionPane.showMessageDialog(mainView, "No tiene permisos para registrar usuarios.");
                return;
            }

            AddUserView addUserView = new AddUserView();
            addUserView.setVisible(true);

            addUserView.getRegisterButton().addActionListener(e -> {
                String newUser = addUserView.getLogin();
                String password = addUserView.getPassword();
                String confirmPassword = addUserView.getConfirmPassword();

                if (newUser.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    addUserView.showError("Todos los campos son obligatorios.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    addUserView.showError("Las contraseñas no coinciden.");
                    return;
                }

                String hashedPassword = MD5Hash.MD5HashS(password);

                try {
                    String createUserQuery = "CREATE USER ? IDENTIFIED BY ?";
                    databaseManager.executeUpdatePrepared(createUserQuery, newUser, hashedPassword);

                    String grantPermissionsQuery = "GRANT SELECT ON population.population TO ?";
                    databaseManager.executeUpdatePrepared(grantPermissionsQuery, newUser);

                    String insertUserQuery = "INSERT INTO users (login, password, type) VALUES (?, ?, 'client')";
                    databaseManager.executeUpdatePrepared(insertUserQuery, newUser, hashedPassword);

                    addUserView.showSuccess("Usuario registrado exitosamente.");
                    addUserView.dispose();
                } catch (SQLException ex) {
                    addUserView.showError("Error al registrar el usuario: " + ex.getMessage());
                }
            });

            addUserView.getCancelButton().addActionListener(e -> addUserView.dispose());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainView, "Error al verificar permisos: " + ex.getMessage());
        }
    }

    /**
     * Procesa un archivo CSV y genera archivos XML correspondientes.
     * 
     * @param csvFile El archivo CSV que contiene los datos.
     * @param xmlDirectory El directorio donde se guardarán los archivos XML generados.
     * @param concatenatedXMLContent Contenido XML concatenado para mostrarlo en la vista.
     * @return Una lista de nombres de archivos XML generados.
     * @throws Exception Si ocurre un error durante el procesamiento del archivo CSV o la generación de XML.
     */
    private List<String> processCSVAndGenerateXML(File csvFile, File xmlDirectory, StringBuilder concatenatedXMLContent) throws Exception {
        List<String> generatedFiles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));

        String header = reader.readLine(); // Leer el encabezado
        if (header == null) {
            throw new Exception("El archivo CSV está vacío.");
        }
        String[] columns = header.split(";");
        String line;

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(";");
            Map<String, String> data = new HashMap<>();
            for (int i = 0; i < columns.length; i++) {
                data.put(columns[i].trim(), values[i].trim());
            }

            String country = values[0].trim();
            if (country == null || country.isEmpty()) {
                throw new Exception("El valor del país no se encuentra o está vacío en la fila.");
            }

            File xmlFile = new File(xmlDirectory, country + ".xml");
            StringBuilder xmlContent = new StringBuilder();
            xmlContent.append("<CountryData>\n");

            for (Map.Entry<String, String> entry : data.entrySet()) {
                xmlContent.append("\t<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">\n");
            }

            xmlContent.append("</CountryData>\n");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile))) {
                writer.write(xmlContent.toString());
            }

            concatenatedXMLContent.append(xmlContent.toString());

            generatedFiles.add(xmlFile.getName());
        }

        reader.close();
        return generatedFiles;
    }

    /**
     * Maneja la importación de un archivo CSV y la generación de archivos XML correspondientes.
     * 
     * Este método permite al usuario seleccionar un archivo CSV, procesarlo y generar archivos XML
     * basados en los datos del CSV. Los archivos XML generados se concatenan y se muestran en la vista principal.
     */
    private void handleImportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(mainView);

        if (result == JFileChooser.APPROVE_OPTION) {
            File csvFile = fileChooser.getSelectedFile();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(csvFile));
                String header = reader.readLine(); 
                if (header == null) {
                    throw new Exception("El archivo CSV está vacío.");
                }
                String[] columns = header.split(";");

                String dropTableQuery = "DROP TABLE IF EXISTS population";
                databaseManager.executeUpdate(dropTableQuery);

                StringBuilder createTableQuery = new StringBuilder("CREATE TABLE population (");
                for (String column : columns) {
                    createTableQuery.append(column.trim()).append(" VARCHAR(30), ");
                }
                createTableQuery.setLength(createTableQuery.length() - 2); 
                createTableQuery.append(")");
                databaseManager.executeUpdate(createTableQuery.toString());

                String insertQuery = "INSERT INTO population (" + String.join(", ", columns) + ") VALUES (" +
                                      String.join(", ", Collections.nCopies(columns.length, "?")) + ")";
                PreparedStatement statement = databaseManager.getConnection().prepareStatement(insertQuery);

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(";");
                    for (int i = 0; i < values.length; i++) {
                        statement.setString(i + 1, values[i].trim());
                    }
                    statement.executeUpdate();
                }
                reader.close();

                File xmlDirectory = new File("xml"); 
                if (!xmlDirectory.exists()) {
                    xmlDirectory.mkdir();
                }

                StringBuilder concatenatedXMLContent = new StringBuilder();
                List<String> generatedFiles = processCSVAndGenerateXML(csvFile, xmlDirectory, concatenatedXMLContent);

                JTextArea textArea = new JTextArea(concatenatedXMLContent.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                mainView.setQueryResultPane(scrollPane);

                JOptionPane.showMessageDialog(mainView, "CSV importado correctamente y archivos XML generados.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainView, "Error al importar el CSV: " + ex.getMessage());
            }
        }
    }




    /**
     * Maneja la ejecución de una consulta SQL proporcionada por el usuario.
     * 
     * Este método toma la consulta proporcionada por el usuario en la vista principal, la ejecuta
     * en la base de datos y muestra los resultados en una tabla dentro de la vista principal.
     */
    private void handleQuery() {
        String query = mainView.getQueryText();
        try {
            ResultSet rs = databaseManager.executeQuery(query);

            int columnCount = rs.getMetaData().getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = rs.getMetaData().getColumnName(i);
            }

            List<String[]> data = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                data.add(row);
            }

            String[][] dataArray = data.toArray(new String[0][0]);

            JTable table = new JTable(dataArray, columnNames);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane scrollPane = new JScrollPane(table);

            mainView.setQueryResultPane(scrollPane);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainView, "Error al realizar la consulta: " + ex.getMessage());
        }
    }
    
    /**
     * Maneja la exportación de los resultados de una consulta a un archivo CSV.
     * 
     * Este método permite al usuario guardar los resultados de una consulta SQL en un archivo CSV
     * en su sistema local. Si no hay conexión con la base de datos, muestra un mensaje de error.
     */
    private void handleExportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(mainView);

        if (result == JFileChooser.APPROVE_OPTION) {
            File csvFile = fileChooser.getSelectedFile();

            if (!csvFile.getName().endsWith(".csv")) {
                csvFile = new File(csvFile.getAbsolutePath() + ".csv");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
                if (!databaseManager.isConnected()) {
                    JOptionPane.showMessageDialog(mainView, "No hay conexión a la base de datos.");
                    return;
                }

                String query = mainView.getQueryText();

                try (ResultSet rs = databaseManager.executeQuery(query)) {
                    int columnCount = rs.getMetaData().getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        bw.write(rs.getMetaData().getColumnName(i));
                        if (i < columnCount) bw.write(";");
                    }
                    bw.write("\n");

                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            String cellValue = rs.getString(i);
                            if (cellValue != null) {
                                bw.write(cellValue.replace(";", ","));
                            }
                            if (i < columnCount) bw.write(";");
                        }
                        bw.write("\n");
                    }
                    JOptionPane.showMessageDialog(mainView, "Consulta exportada exitosamente a " + csvFile.getAbsolutePath());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainView, "Error al ejecutar la consulta: " + ex.getMessage());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainView, "Error al escribir el archivo CSV: " + ex.getMessage());
            }
        }
    }
    private void createPopulationTable() {
        try {
            // Eliminar la tabla si ya existe
            String dropTableQuery = "DROP TABLE IF EXISTS population";
            databaseManager.executeUpdate(dropTableQuery);

            // Crear la tabla
            String createTableQuery = "CREATE TABLE population (country VARCHAR(30), city VARCHAR(30), population VARCHAR(30), area VARCHAR(30))";
            databaseManager.executeUpdate(createTableQuery);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainView, "Error al crear la tabla 'population': " + ex.getMessage());
        }
    }


    /**
     * Maneja el cierre de sesión del usuario.
     * 
     * Este método desconecta al usuario de la base de datos, muestra un mensaje de éxito y
     * regresa a la vista de login.
     */
    private void handleLogout() {
        databaseManager.disconnect();
        JOptionPane.showMessageDialog(mainView, "Sesión cerrada.");
        mainView.dispose();
        showLoginView();
    }
}
