package src.florida.aev_mvc;

public class UserModel {
    private int id;
    private String login;
    private String password;
    private String type;

    /**
     * Constructor de la clase UserModel.
     * 
     * Este constructor inicializa un objeto de tipo UserModel con los valores proporcionados para el id, login, password y type.
     * 
     * @param id El identificador único del usuario.
     * @param login El nombre de usuario para el login.
     * @param password La contraseña del usuario.
     * @param type El tipo de usuario (por ejemplo, administrador, cliente, etc.).
     */
    public UserModel(int id, String login, String password, String type) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    /**
     * Obtiene el identificador único del usuario.
     * 
     * @return El id del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre de usuario para el login.
     * 
     * @return El nombre de usuario.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el tipo de usuario.
     * 
     * @return El tipo de usuario (por ejemplo, administrador, cliente, etc.).
     */
    public String getType() {
        return type;
    }
}
