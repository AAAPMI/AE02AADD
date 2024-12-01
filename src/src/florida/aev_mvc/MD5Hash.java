package src.florida.aev_mvc;


import java.security.MessageDigest;

public class MD5Hash {
	/**
	 * Esta clase contiene métodos para generar un valor hash MD5 de una cadena de texto.
	 * Utiliza la clase `MessageDigest` de Java para realizar el cálculo del hash MD5, 
	 * que se representa como una cadena hexadecimal.
	 */
    public static String MD5HashS(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

