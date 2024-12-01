package src.florida.aev_mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton btnLogin;

    /**
     * Constructor de la clase LoginView.
     * Configura la ventana de inicio de sesión, inicializa los componentes,
     * define su disposición y establece propiedades básicas de la ventana.
     */
    public LoginView() {
    	getContentPane().setBackground(new Color(0, 255, 255));
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        getContentPane().setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("       Usuario:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("       Contraseña:");
        passwordField = new JPasswordField();
        
        btnLogin = new JButton("Iniciar sesión");

        getContentPane().add(usernameLabel);
        getContentPane().add(usernameField);
        getContentPane().add(passwordLabel);
        getContentPane().add(passwordField);
        getContentPane().add(btnLogin);

        setVisible(true);
    }

    /**
     * Obtiene el botón de inicio de sesión.
     * 
     * @return JButton El botón "Iniciar sesión".
     */
    public JButton getLoginButton() {
        return btnLogin;
    }

    /**
     * Obtiene el nombre de usuario ingresado por el usuario.
     * 
     * @return String El texto ingresado en el campo de usuario.
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Obtiene la contraseña ingresada por el usuario.
     * 
     * @return String La contraseña ingresada en el campo de contraseña.
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    /**
     * Agrega un ActionListener al botón de inicio de sesión.
     * Permite capturar el evento de clic en el botón.
     * 
     * @param listener El ActionListener a agregar.
     */
    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }
}
