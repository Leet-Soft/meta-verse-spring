package uni.fmi.masters.metaverse.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.metaverse.entities.UserEntity;
import uni.fmi.masters.metaverse.repositories.UserRepository;

@RestController
public class LoginController {
	
	private UserRepository userRepository;
	
	
	
	public LoginController(UserRepository userRepo) {
		userRepository = userRepo;
	}
	
	
	@PostMapping(path = "/register")
	public UserEntity register( @RequestParam(value = "email") String email,
								@RequestParam(value = "username") String username,
								@RequestParam(value = "password") String password,
								@RequestParam(value = "repeatPassword") String repeatPassword) {
		
		if(password.equals(repeatPassword)) {
			UserEntity user = new UserEntity(username, hashMe(password), email);
			
			userRepository.saveAndFlush(user);
		}
									
		return null;
	}

	
	private String hashMe(String password) {		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(password.getBytes());
			
			byte[] arr = md.digest();
			
			StringBuilder hash = new StringBuilder();
			
			for(int i = 0; i < arr.length; i++) {
				hash.append((char)arr[i]);
			}
			
			return hash.toString();
			
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		}		
				
		return null;
	}
}
