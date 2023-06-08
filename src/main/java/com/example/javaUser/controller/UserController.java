package com.example.javaUser.controller;

import com.example.javaUser.UserException.ResourceNotFoundException;
import com.example.javaUser.entity.UserEntity;
import com.example.javaUser.repository.UserRepo;
import com.example.javaUser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/allUsers")
    public List<UserEntity> getAllUsers(){
        return this.userRepo.findAll();
    }
    @GetMapping("/{id}")
    public UserEntity getUserByID(@PathVariable(value = "id") Integer userId){
        return this.userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException(" user not found with id:" +userId));
    }
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user){
        return this.userRepo.save(user);
    }
    @PutMapping("/{id}")
    public UserEntity updateUser(@RequestBody UserEntity user,@PathVariable("id")Integer userId){
        UserEntity existingUser = this.userRepo.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("user not found with id:"+userId));
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        return this.userRepo.save(existingUser);
    }
    @DeleteMapping("/{id}")
 public ResponseEntity<UserEntity>deleteUser(@PathVariable("id")Integer userId){
        UserEntity existingUser = this.userRepo.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("user not found with id:"+userId));
        this.userRepo.delete(existingUser);
        return ResponseEntity.ok().build();

 }
}
