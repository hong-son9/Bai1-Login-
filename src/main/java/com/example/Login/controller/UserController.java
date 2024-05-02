package com.example.Login.controller;

import com.example.Login.dtos.request.ApiResponse;
import com.example.Login.dtos.request.UserRequest;
import com.example.Login.dtos.request.UserUpdateRequest;
import com.example.Login.entity.User;
import com.example.Login.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(){
        return "login"; // Đảm bảo có một file "login.html" trong thư mục template của bạn
    }
    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult((userService.createUser(request)));
        return apiResponse;
    }
    @GetMapping
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{user_id}")
    User getUser(@PathVariable("user_id") String user_id){
        return userService.getUser(user_id);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted"; }
    @PutMapping("/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }
}
