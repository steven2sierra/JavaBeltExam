package belt.spring.repositories;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import belt.spring.models.Idea;

@Repository
public interface IdeaRepository extends CrudRepository<Idea,Long>, JpaRepository<Idea,Long> {

	
	List<Idea>findAll();
	
	// descending order
	List<Idea>findByOrderByLikedUserDesc();
	
}
