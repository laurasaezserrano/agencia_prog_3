package agencia_prog_3_GUI;

public class AlmacenajeSesion {
    private static String nombreUsuario;
    private static String password;

    public static void setNombreUsuario(String usuario) {
        nombreUsuario = usuario;
    }


    public static String getNombreUsuario() {
        return nombreUsuario;
    }


    public static void setPassword(String pass) {
        password = pass;
    }


    public static String getPassword() {
        return password;
    }


    public static void limpiarSesion() {
        nombreUsuario = null;
        password = null;
    }

     public static boolean sesionActiva() {
        return nombreUsuario != null && password != null;
    }
    
     public static void iniciarSesion(String usuario, String pass) {
     	AlmacenajeSesion.nombreUsuario = usuario;
     	AlmacenajeSesion.password = pass;
     }
}