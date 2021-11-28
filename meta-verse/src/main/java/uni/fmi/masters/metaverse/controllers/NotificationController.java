package uni.fmi.masters.metaverse.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.metaverse.entities.NotificationEntity;
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
	
	@PostMapping(path = "/notification/add")
	public ResponseEntity<Boolean> sendFriendRequest(
			@RequestParam(value = "comment") String comment,
			@RequestParam(value = "userId") int userId,			
			HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("user");
		
		if(user != null) {			
			Optional<UserEntity> toUser = userRepo.findById(userId);
			
			if(toUser.isPresent()) {
				NotificationEntity notification = new NotificationEntity();
				
				notification.setComment(comment);
				notification.setFromUser(user);
				notification.setToUser(toUser.get());
				notification.setStatus(NotificationEntity.STATUS_REQUESTED);
				notification.setDate(new Date());
				
				notificationRepo.saveAndFlush(notification);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}else {
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}	
		}	
		return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path = "/notification/all")
	public ResponseEntity<List<NotificationEntity>> getAllUserNotifications(HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("user");
		
		if(user != null) {
			List<NotificationEntity> notifications = notificationRepo.findByToUser(user);
			
			return new ResponseEntity<List<NotificationEntity>>(notifications, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<NotificationEntity>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);		
	}
	
	@PostMapping(path = "/notification/change")
	public ResponseEntity<Boolean> changeNotificationStatus(
			@RequestParam( value = "notificationId") int notificationId,
			@RequestParam( value = "status") String status,
			HttpSession session){
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user != null) {
			
			Optional<NotificationEntity> notification = notificationRepo.findById(notificationId);
			
			if(notification.isPresent()) {
				NotificationEntity notificatioEntity = notification.get();
				notificatioEntity.setStatus(status);
				
				notificationRepo.saveAndFlush(notificatioEntity);		
				
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);			
		}		
		return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);		
	}
	

}
