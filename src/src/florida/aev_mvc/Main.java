package src.florida.aev_mvc;


public class Main {
	/**
	 * Punto de entrada principal de la aplicación.
	 * 
	 * Este método inicializa los componentes del sistema, configurando el patrón
	 * MVC (Modelo-Vista-Controlador). Se crea una instancia de cada componente:
	 * - `DatabaseManager`: Encargado de manejar la base de datos.
	 * - `LoginView`: La interfaz gráfica de usuario para el login.
	 * - `UserController`: Controlador que conecta la vista y el modelo.
	 * 
	 * Finalmente, hace visible la vista del login.
	 * 
	 * @param args Argumentos de la línea de comandos (no utilizados en este caso).
	 */
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        LoginView loginView = new LoginView();
        UserController userController = new UserController(databaseManager, loginView);

        loginView.setVisible(true);
    }
}


