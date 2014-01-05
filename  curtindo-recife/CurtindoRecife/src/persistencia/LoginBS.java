package persistencia;

public class LoginBS {
	
	public static boolean validarLogin(String email, String senha){
		boolean validar=false;
		//será mudado
		if ((email.length()>7)&&(senha.length()>7)){
			validar=true;
		}
		
		return validar;
	}

}
