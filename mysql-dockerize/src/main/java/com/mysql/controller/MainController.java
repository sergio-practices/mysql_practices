package com.mysql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.entity.User;
import com.mysql.repository.UserRepository;

@RestController // This means that this class is a Controller
@RequestMapping("/demo") // This means URL's start with /demo (after Application path)
public class MainController {
  private UserRepository userRepository;
	
	MainController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
  
  @PostMapping(path="/add") // Map ONLY POST Requests
  public String addNewUser (@RequestParam String name, @RequestParam String email) {

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path="/all")
  public Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }
}