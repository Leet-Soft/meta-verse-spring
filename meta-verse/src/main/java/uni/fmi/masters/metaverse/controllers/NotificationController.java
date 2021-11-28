package uni.fmi.masters.metaverse.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.metaverse.entities.UserEntity;
import uni.fmi.masters.metaverse.repositories.NotificationRepository;
import uni.fmi.masters.metaverse.repositories.UserRepository;

@RestController
public class NotificationController {
	
	NotificationRepository notificationRepo;
	UserRepository userRepo;
	
	public NotificationController(NotificationRepository notificationRepo, UserRepository userRepo) {
		this.notificationRepo = notificationRepo;
		this.userRepo = userRepo;
	}
	
	
	@GetMapping(path = "/notification/search")
	public ResponseEntity<List<UserEntity>> searchUsers(
			@RequestParam(value = "filter") String filter,
			HttpSession session) {
		
		
		UserEntity user = (UserEntity)session.getAttribute("user");
		
		if(user == null)
			return new ResponseEntity<List<UserEntity>>(new ArrayList<UserEntity>(), HttpStatus.UNAUTHORIZED);
		
		List<UserEntity> users = userRepo.findByUsernameContainingAndIdNot(filter, user.getId());
		
		
		return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);		
	}
	
	

}
