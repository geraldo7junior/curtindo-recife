package persistencia;

public class LoginBS {
	
	public static boolean validarLogin(String email, String senha){
		boolean validar=false;
		//ser� mudado
		if ((email.length()>4)&&(senha.length()>4)){
			validar=true;
		}
		
		return validar;
	}

}
