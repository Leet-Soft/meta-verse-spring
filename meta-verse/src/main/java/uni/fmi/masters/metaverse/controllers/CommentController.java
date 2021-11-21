package uni.fmi.masters.metaverse.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.masters.metaverse.entities.CommentEntity;
import uni.fmi.masters.metaverse.entities.UserEntity;
import uni.fmi.masters.metaverse.repositories.CommentRepository;

@RestController
public class CommentController {

	
	CommentRepository commentRepo;
	
	public CommentController(CommentRepository commentRepo) {
		this.commentRepo = commentRepo;
	}
	
	
	@PostMapping(path = "/comment/add")
	public String addComment(
			@RequestParam(value = "city") String city,
			@RequestParam(value = "temp") double temp,
			@RequestParam(value = "comment") String comment,
			@RequestParam(value = "icon") String icon,
			HttpSession session			
			) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user != null) {
			
			CommentEntity newComment = new CommentEntity();
			
			newComment.setCity(city);
			newComment.setIcon(icon);
			newComment.setTemp(temp);
			newComment.setComment(comment);
			newComment.setUser(user);
			
			newComment = commentRepo.saveAndFlush(newComment);			
			
			if(newComment != null) {
				return String.valueOf(newComment.getId());
			}
			
			return "Error: insert unsuccessfull!!!";
		}
		
		return "Error: there is no logged user!!!";		
	}
	
	@GetMapping(path ="/comment/all")
	public List<CommentEntity> getAllComments(){
		return commentRepo.findAll();		
	}
	
	
	@DeleteMapping(path = "/comment/delete")
	//@Secured("ROLE_ADMIN")
	public ResponseEntity<Boolean> deleteComment(
			@RequestParam(value = "id") int id,
			HttpSession session){
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
		}
		
		Optional<CommentEntity> optinalComment = commentRepo.findById(id);
		
		if(optinalComment.isPresent()) {
			CommentEntity comment = optinalComment.get();
			
			if(comment.getUser().getId() == user.getId()) {
				commentRepo.delete(comment);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}else {
				return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
			}
						
		}else {
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);			
		}		
	}	
}
