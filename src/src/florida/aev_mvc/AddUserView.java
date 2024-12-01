package src.florida.aev_mvc;

import javax.swing.*;
import java.awt.*;

public class AddUserView extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton btnRegister;
    private JButton btnCancel;

    /**
     * Constructor para inicializar y configurar la ventana para registrar un nuevo usuario.
     * Establece las propiedades principales de la ventana, crea y organiza los elementos
     * de la interfaz gráfica, incluyendo campos de texto, contraseñas y botones.
     */
    public AddUserView() {
        setTitle("Registrar Nuevo Usuario");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 255, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(null);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBounds(10, 10, 314, 98);
        fieldsPanel.setBackground(new Color(0, 255, 255));
        fieldsPanel.setLayout(new GridLayout(3, 2, 10, 10));

        fieldsPanel.add(new JLabel("Usuario"));
        loginField = new JTextField();
        fieldsPanel.add(loginField);

        fieldsPanel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        fieldsPanel.add(passwordField);

        fieldsPanel.add(new JLabel("Confirmar Contraseña:"));
        confirmPasswordField = new JPasswordField();
        fieldsPanel.add(confirmPasswordField);

        mainPanel.add(fieldsPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(10, 118, 314, 33);
        buttonsPanel.setBackground(new Color(0, 255, 255));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnRegister = new JButton("Registrar");
        btnCancel = new JButton("Cancelar");
        buttonsPanel.add(btnRegister);
        buttonsPanel.add(btnCancel);

        mainPanel.add(buttonsPanel);

        getContentPane().add(mainPanel);

        setVisible(true);
    }
    /**
     * Obtiene el texto ingresado en el campo de usuario.
     *
     * @return Una cadena de texto con el nombre de usuario ingresado.
     */

    public String getLogin() {
        return loginField.getText();
    }

    /**
     * Obtiene la contraseña ingresada en el campo de contraseña.
     *
     * @return Una cadena de texto que representa la contraseña ingresada.
     */

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    /**
     * Obtiene la contraseña ingresada en el campo de confirmación de contraseña.
     *
     * @return Una cadena de texto que representa la contraseña de confirmación ingresada.
     */

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    /**
     * Obtiene el botón de registro para asignar acciones asociadas.
     *
     * @return Un objeto `JButton` que representa el botón "Registrar".
     */
    public JButton getRegisterButton() {
        return btnRegister;
    }

    /**
     * Obtiene el botón de cancelar para asignar acciones asociadas.
     *
     * @return Un objeto `JButton` que representa el botón "Cancelar".
     */

    public JButton getCancelButton() {
        return btnCancel;
    }

    /**
     * Muestra un cuadro de diálogo de error con el mensaje especificado.
     *
     * @param message El mensaje que se mostrará en el cuadro de diálogo.
     */

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo de error con el mensaje especificado.
     *
     * @param message El mensaje que se mostrará en el cuadro de diálogo.
     */

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
