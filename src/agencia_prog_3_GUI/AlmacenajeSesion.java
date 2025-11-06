package agencia_prog_3_GUI;

public class AlmacenajeSesion {
	private static String nombreUsuario;
    private static String contraseñaUsuario;

    // Método para guardar los datos después del login
    public static void iniciarSesion(String usuario, String nombreCompleto) {
    	AlmacenajeSesion.nombreUsuario = usuario;
    	AlmacenajeSesion.contraseñaUsuario = nombreCompleto;
    }

    //OBTENER DATOS DESDE CUALQUIER CLASE
    public static String getNombreUsuario() {
        // Devuelve el usuario, o null si no hay sesión
        return nombreUsuario;
    }

    public static String getPassword() {
        return contraseñaUsuario;
    }
    
//    //CERRAR SESIÓN
//    public static void cerrarSesion() {
//    	AlmacenajeSesion.nombreUsuarioLogeado = null;
//    	AlmacenajeSesion.nombreCompletoLogeado = null;
//    }
}

