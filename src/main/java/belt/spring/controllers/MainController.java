package belt.spring.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import belt.spring.models.User;
import belt.spring.services.IdeaService;
import belt.spring.services.UserService;
import belt.spring.validator.UserValidator;
import belt.spring.models.Idea;

@Controller
public class MainController {

	// private final
		private final UserService userService;
		private final IdeaService ideaService;
		// validations
		private final UserValidator userValidator;
		
		public MainController(UserService userService, UserValidator userValidator, IdeaService ideaService) {
			this.userService = userService;
			this.ideaService = ideaService;
			this.userValidator = userValidator;
		}
		
		// 'USER controller'
		
		// welcome, login and registration page
		@GetMapping("/")
		public String welcome(@ModelAttribute("user") User user) {
			return "index.jsp";
		}
		
		// POST, register user
		@PostMapping("/registration")
	    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
	    	userValidator.validate(user, result);
	    	if(result.hasErrors()) {
	    		return "index.jsp";
	    	} else {
	    		User u = userService.registerUser(user);
	    		session.setAttribute("u_id", u.getId());
	    		return "redirect:/ideas";
	    	}
	        // if result has errors, return the registration page (don't worry about validations just now)
	        // else, save the user in the database, save the user id in session, and redirect them to the /home route
	    }
		
		// POST, login user
		@PostMapping("/login")
	    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
	    	boolean isAuthenticated = userService.authenticateUser(email, password);
			if(isAuthenticated) {
				User u = userService.findByEmail(email);
				session.setAttribute("u_id", u.getId());
				return "redirect:/ideas";
			}
			else {
				model.addAttribute("error", "invalid credentials");
				return "index.jsp";	
			}
	        // if the user is authenticated, save their user id in session
	        // else, add error messages and return the login page
	    }
		
		// logout user
	    @RequestMapping("/logout")
	    public String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/";
	        // invalidate session
	        // redirect to login page
	    }
	    
	 /*
	  * SPACE
	  */
	    
	   // IDEAS CONTROLLER
	   
	 // idea dash-board
	    @GetMapping("/ideas")
	    public String dashboard(HttpSession session, Model model) {
	    	
	    	// get user in session by id
	    	// cast
	    	Long u_id = (Long) session.getAttribute("u_id");
	    	User u = userService.findUserById(u_id);
	    	model.addAttribute("user", u);
	    	
	    	// display all ideas 
	    	List<Idea> allIdeas = ideaService.findAll();
	    	model.addAttribute("allIdeas", allIdeas);
	    	
	    	return "dashboard.jsp";
	    }
	    
	  // new idea page, renders page
	    @GetMapping("/ideas/new")
	    public String newIdeaPage(@Valid @ModelAttribute("newIdea") Idea newIdea) {
	    	return "new.jsp";
	    }
	    
	    // creation of new idea,  POST
	    @PostMapping("/ideas/new")
	    public String newIdea(@Valid @ModelAttribute("newIdea") Idea newIdea, HttpSession session, Model model, BindingResult result) {
	    	// get user in session by id
	    	Long u_id = (Long) session.getAttribute("u_id");
	    	User u = userService.findUserById(u_id);
	    	model.addAttribute("user", u);
	    	
	    	// create the idea 
	    	if(result.hasErrors()) {
	    		return "new.jsp";
	    	} else {
	    		// attaching user to idea 
	    		newIdea.setUser(u);
	    		ideaService.createIdea(newIdea);
	    		
	    		return "redirect:/ideas";
	    	}
	    }
	    
	    
	    // edit idea page, RENDERS PAGE
	    @GetMapping("/ideas/edit/{id}")
	    public String editIdeaPage(@PathVariable("id") Long id, Model model, @Valid @ModelAttribute("updateIdea") Idea updateIdea, HttpSession session) {
	    	
	    	// get idea by id
	    	Idea idea = ideaService.getIdeaById(id);
	    	model.addAttribute("idea", idea);
	    	
	    	// get user in session 
	    	Long u_id = (Long) session.getAttribute("u_id");
	    	
	    	// verification between user in session/ brute force and the actual creator of the idea
	    	if(idea.getUser().getId().equals(u_id)) {
	    		return "edit.jsp";
	    	} else {
	    		
	        	return "redirect:/ideas";    		
	    	}
	    } 
	    
	    
	    // edit an idea, POST, update information identified by id
	    @PostMapping("ideas/edit/{id}")
	    public String edit(@PathVariable("id") Long id, @Valid @ModelAttribute("updateIdea") Idea updateIdea, BindingResult result, HttpSession session) {
	    	
	    	if(result.hasErrors()) {
	    		return "redirect:/ideas/edit/"+id;
	    	} else {
	    		// get user in session
	        	Long u_id = (Long) session.getAttribute("u_id");
	        	User u = userService.findUserById(u_id);
	        	
	        	// update the idea
	        	updateIdea.setUser(u);
	        	ideaService.updateIdea(updateIdea);
	        	
	        	return "redirect:/ideas";
	    	}
	    }
	    
	    // show idea page
	    @GetMapping("ideas/show/{id}")
	    public String showIdeaPage(@PathVariable("id") Long id, Model model) {
	    	
	    	// get idea by id
	    	Idea idea = ideaService.getIdeaById(id);
	    	model.addAttribute("idea", idea);
	    	
	    	return "show.jsp";
	    }
	    
	    // delete an idea
	    @PostMapping("/ideas/delete/{id}")
	    public String deleteIdea(@PathVariable("id") Long id) {
	    	ideaService.delete(id);
	    	return "redirect:/ideas";   
	    }
	    
	    // like an idea
	    @PostMapping("/ideas/like/{id}")
	    public String likeIdea(@PathVariable("id") Long id, HttpSession session) {
	    	
	    	// find one idea by id
	    	Idea idea = ideaService.getIdeaById(id);
	    	// find user in session by id
	    	Long u_id = (Long) session.getAttribute("u_id");
	    	User u = userService.findUserById(u_id);
	    	
	    	// like an idea
	    	List<User> liked = idea.getLikedUser();
	    	liked.add(u); // add liked user to idea
	    	idea.setLikedUser(liked);
	    	ideaService.updateIdea(idea);
	    	return "redirect:/ideas";
	    }
	    
	    // dislike or 'unlike idea'
	    @PostMapping("/ideas/unlike/{id}") 
	    public String unlikeIdea(@PathVariable("id") Long id, HttpSession session) {
	    	
	    	// find one idea by id
	    	Idea idea = ideaService.getIdeaById(id);
	    	// find user in session by id
	    	Long u_id = (Long) session.getAttribute("u_id");
	    	User u = userService.findUserById(u_id);
	    	
	    	// unlike idea 
	    	List<User> liked = idea.getLikedUser();
	    	liked.remove(u); // remove like from idea for user
	    	idea.setLikedUser(liked);
	    	ideaService.updateIdea(idea);
	    	
	    	return "redirect:/ideas";
	    }
	    
	    // low likes
	    
	    // high likes
	   
}
