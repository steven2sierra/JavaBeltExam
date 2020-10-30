package belt.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import belt.spring.models.Idea;
import belt.spring.repositories.IdeaRepository;

@Service
public class IdeaService {

private final IdeaRepository ideaRepository;
	
	public IdeaService(IdeaRepository ideaRepository) {
		this.ideaRepository = ideaRepository;
	}
	
	// create a new idea
	public Idea createIdea(Idea newIdea) {
		
		return ideaRepository.save(newIdea);
	}
	
	// find all ideas
	public List<Idea> findAll() {
		
		return ideaRepository.findAll();
	}
	
	public List<Idea> sortByDesc() {
		return ideaRepository.findByOrderByLikedUserDesc();
	}
	
	// find an idea by id
	public Idea getIdeaById(Long id) {
		
		Optional<Idea> idea = ideaRepository.findById(id);
		if(idea.isPresent()) {
			return idea.get();
		} else {
			return null;
		}
	}
	
	// update an idea
	public Idea updateIdea(Idea updatedIdea) {
		
		return ideaRepository.save(updatedIdea);
	}
	
	// delete an idea
	public void delete(Long id) {
		
		ideaRepository.deleteById(id);
	}
}
