package security.module;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

public class TwitterCallbackHandler implements CallbackHandler{
	private String username;
	private String password;

	public TwitterCallbackHandler(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public void handle(Callback[] cb) {
		for (int i = 0; i < cb.length; i++){
			if (cb[i] instanceof NameCallback){
				NameCallback nc = (NameCallback)cb[i];
				nc.setName(username);
			} else if (cb[i] instanceof PasswordCallback){
				PasswordCallback pc = (PasswordCallback)cb[i];
				pc.setPassword(password.toCharArray());
				password = null;
			}
		}
	}

}