package uni.fmi.masters.metaverse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.masters.metaverse.entities.NotificationEntity;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer>{

}
