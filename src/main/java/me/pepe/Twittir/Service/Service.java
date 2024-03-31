package me.pepe.Twittir.Service;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import me.pepe.Twittir.Database.Login.LoginRepository;
import me.pepe.Twittir.Database.Login.LoginTokenData;
import me.pepe.Twittir.Database.User.UserAboutData;
import me.pepe.Twittir.Database.User.UserData;
import me.pepe.Twittir.Database.User.UserLoginData;
import me.pepe.Twittir.Database.User.UserRepository;
import me.pepe.Twittir.Utils.Dto.Credentials.CredentialsDto;

@org.springframework.stereotype.Service
public class Service {
    private final PasswordEncoder passwordEncoder;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private UserRepository userRepository;
    public Service(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public UserData authenticate(CredentialsDto ctdto) {
    	System.out.println("intentando autenticar: " + ctdto.getUsername());
    	Optional<UserData> userData = userRepository.findByUserName(ctdto.getUsername());
    	if (userData.isPresent()) {
    		UserLoginData userLoginData = userRepository.findLoginData(userData.get().getID());
            String encodedMasterPassword = passwordEncoder.encode(CharBuffer.wrap(ctdto.getPassword()));
            if (passwordEncoder.matches(CharBuffer.wrap(userLoginData.getPassword()), encodedMasterPassword)) {
            	System.out.println("usuario logueado!");
        		return userData.get();
            } else {
            	System.out.println("contraseña incorrecta!");
            }
    	} else {
    		System.out.println("usuario no encontrado!");
    	}
		return null;
    }
    public LoginTokenData findByLogin(String loginKey) {
    	Optional<LoginTokenData> tkData = loginRepository.findByLoginKey(loginKey);
    	if (tkData.isPresent()) {
    		return tkData.get();
    	}
    	return null;
    }
    public long newUser(UserData userData, String password) {
    	return newUser(userData, password, new UserAboutData());
    }
    public long newUser(UserData userData, String password, UserAboutData userAboutData) {
    	userRepository.newUser(userData.getUserName(), userData.getName(), userData.isVerified());
    	long userID = userRepository.getLastUserRegistered();
    	userRepository.newUserLogin(userID, password);
    	userRepository.newUserAbout(userID, userAboutData.getDescription(), userAboutData.getLocation(), userAboutData.getBirthDate(), System.currentTimeMillis(), userAboutData.getWork(), userAboutData.getEmail(), 0, 0);
    	return userID;
    }
}