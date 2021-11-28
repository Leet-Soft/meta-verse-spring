package uni.fmi.masters.metaverse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.masters.metaverse.entities.NotificationEntity;
import uni.fmi.masters.metaverse.entities.UserEntity;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer>{

	List<NotificationEntity> findByToUser(UserEntity user);
}
