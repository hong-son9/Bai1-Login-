package com.example.Login.Controller;

import com.example.Login.Dto.Request.ApiResponse;
import com.example.Login.Dto.Request.UserRequest;
import com.example.Login.Dto.Request.UserUpdateRequest;
import com.example.Login.Entity.User;
import com.example.Login.Service.UserService;
import jakarta.validation.Valid;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
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
        return "User has been deleted";
    }
    @PutMapping("/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }
}
