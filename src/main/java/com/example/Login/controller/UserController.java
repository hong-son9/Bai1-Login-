package com.example.Login.controller;

import com.example.Login.dtos.request.ApiResponse;
import com.example.Login.dtos.request.UserRequest;
import com.example.Login.dtos.request.UserUpdateRequest;
import com.example.Login.dtos.request.response.UserResponseDTO;
import com.example.Login.entity.User;
import com.example.Login.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    @GetMapping(value = "/login_user", produces = MediaType.TEXT_HTML_VALUE)
//    public String showLogin() throws IOException {
//        Resource resource = resourceLoader.getResource("classpath:/templates/login.html");
//        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
//    }
//    @PostMapping(value = "/checklogin", produces = MediaType.TEXT_HTML_VALUE)
//    public String checkLogin(ModelMap model, @RequestParam("username")String username, @RequestParam("password") String password
//            , HttpSession session){
//
//        if(userService.checkLogin(username, password)){
//            System.out.println("Login thanh cong");
////            session.setAttribute("USERNAME", username);
//////            model.addAttribute("USERS", userService.getAllUsers());
//////        userService.findAll();
//            return "Post_Comment";
//        }
//        else{
//            System.out.println("Login that bai");
//            model.addAttribute("ERROR", "Username and password not exist");
////        userService.findAll();
//        }
//        return "login";
//    }
@PostMapping()
public UserResponseDTO createUser(@RequestBody @Valid UserRequest request) {
    User user = userService.createUser(request);
    UserResponseDTO userResponse = new UserResponseDTO();
    userResponse.setUsername(user.getUsername());
    userResponse.setEmail(user.getEmail());
    userResponse.setPhone(user.getPhone());
    userResponse.setRoles(user.getRoles());

    return userResponse;
}
    @GetMapping()
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        for (User user : users) {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setRoles(user.getRoles());
            userResponseDTOs.add(dto);
        }
        return userResponseDTOs;
    }


    @GetMapping("/{username}")
    UserResponseDTO getUser(@PathVariable("username") String username){
        User user = userService.getUser(username);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setRoles(user.getRoles());
        return userResponseDTO;

    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted"; }
    @PutMapping("/{username}")
    UserResponseDTO updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request){
        User user = userService.updateUser(username, request);
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRoles(user.getRoles());
        return userResponse;
    }
}
