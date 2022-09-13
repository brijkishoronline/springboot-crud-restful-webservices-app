package net.javaguides.springboot.entity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.entity.exception.ResourceNotFoundException;
import net.javaguides.springboot.entity.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@GetMapping
	public List<User> getAllUsers() {

		return this.repository.findAll();

	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {
		return this.repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.repository.save(user);
	}

	@PutMapping("/{id}")
	public User udpateUser(@RequestBody User user, @PathVariable("id") long userId) {

		User existingUser = this.repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());

		return this.repository.save(existingUser);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long userId) {

		User existingUser = this.repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));
		this.repository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
