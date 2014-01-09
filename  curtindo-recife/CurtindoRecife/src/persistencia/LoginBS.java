package persistencia;

public class LoginBS {
	
	public static boolean validarLogin(String email, String senha){
		boolean validar=false;
		//será mudado
		if ((email.length()>4)&&(senha.length()>4)){
			validar=true;
		}
		
		return validar;
	}

}
