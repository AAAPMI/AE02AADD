package src.florida.aev_mvc;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

public class MainView extends JFrame {
    private JButton btnRegisterUser;  
    private JButton btnImportCSV;    
    private JButton btnQuery;        
    private JButton btnExportCSV;   
    private JButton btnLogout;
    private JTextArea queryField;    
    private JPanel queryResultPanel;

    /**
     * Constructor de la clase MainView.
     * Configura la ventana principal de la aplicación con su título, tamaño, y
     * ubicación inicial. Inicializa los componentes gráficos como botones,
     * etiquetas, áreas de texto y paneles. También define la disposición y diseño
     * de los elementos en la interfaz usando un GroupLayout.
     */
    public MainView() {
        setTitle("Gestión de Base de Datos");
        setSize(804, 623);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnRegisterUser = new JButton("Registrar Usuario");
        btnImportCSV = new JButton("Importar CSV");
        btnQuery = new JButton("Hacer Consulta");
        btnExportCSV = new JButton("Exportar a CSV");
        btnLogout = new JButton("Cerrar Sesión");

        JLabel lblQueryZone = new JLabel("Zona de Consulta:");
        JLabel lblResponseZone = new JLabel("Zona de Respuesta:");

        queryField = new JTextArea(5, 40);
        queryResultPanel = new JPanel();
        queryResultPanel.setLayout(new BorderLayout()); // Para manejar componentes dinámicos

        JScrollPane queryScrollPane = new JScrollPane(queryField);
        JScrollPane resultScrollPane = new JScrollPane(queryResultPanel);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 255, 255));

        GroupLayout layout = new GroupLayout(panel);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblQueryZone)
                        .addComponent(queryScrollPane, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                        .addComponent(lblResponseZone)
                        .addComponent(resultScrollPane, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(29)
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addComponent(btnRegisterUser, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                .addComponent(btnImportCSV, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                .addComponent(btnQuery, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18)
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addComponent(btnExportCSV, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                .addComponent(btnLogout, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(29)
                    .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblQueryZone)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(queryScrollPane, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnImportCSV, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(btnRegisterUser, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(btnQuery, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblResponseZone)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(resultScrollPane, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnExportCSV, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(103, Short.MAX_VALUE))
        );
        panel.setLayout(layout);

        getContentPane().add(panel);
    }

    /**
     * Devuelve el botón utilizado para registrar un nuevo usuario.
     * 
     * @return JButton - Instancia del botón "Registrar Usuario".
     */
    public JButton getRegisterUserButton() {
        return btnRegisterUser;
    }

    /**
     * Devuelve el botón utilizado para importar un archivo CSV.
     * 
     * @return JButton - Instancia del botón "Importar CSV".
     */
    public JButton getImportCSVButton() {
        return btnImportCSV;
    }

    /**
     * Devuelve el botón utilizado para realizar consultas en la base de datos.
     * 
     * @return JButton - Instancia del botón "Hacer Consulta".
     */
    public JButton getQueryButton() {
        return btnQuery;
    }

    /**
     * Devuelve el botón utilizado para exportar datos a un archivo CSV.
     * 
     * @return JButton - Instancia del botón "Exportar a CSV".
     */
    public JButton getExportCSVButton() {
        return btnExportCSV;
    }

    /**
     * Devuelve el botón utilizado para cerrar la sesión actual.
     * 
     * @return JButton - Instancia del botón "Cerrar Sesión".
     */
    public JButton getLogoutButton() {
        return btnLogout;
    }

    /**
     * Obtiene el texto ingresado por el usuario en el área de consulta.
     * 
     * @return String - El texto ingresado en el área de consulta.
     */
    public String getQueryText() {
        return queryField.getText();
    }

    /**
     * Establece el componente que se mostrará en el panel de resultados de la consulta.
     * Este método elimina todos los componentes existentes en el panel y añade el nuevo
     * componente especificado.
     * 
     * @param component JComponent - Componente que se añadirá al panel de resultados.
     */
    public void setQueryResultPane(JComponent component) {
        queryResultPanel.removeAll();
        queryResultPanel.add(component, BorderLayout.CENTER);
        queryResultPanel.revalidate();
        queryResultPanel.repaint();
    }
   

    
}
