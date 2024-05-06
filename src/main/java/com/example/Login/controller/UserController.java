package com.example.Login.controller;

import com.example.Login.dtos.request.UserRequest;
import com.example.Login.dtos.request.UserUpdateRequest;
import com.example.Login.dtos.request.response.UserResponse;
import com.example.Login.entity.User;
import com.example.Login.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public UserResponse createUser(@RequestBody @Valid UserRequest request) {
    User user = userService.createUser(request);
    UserResponse userResponse = new UserResponse();
    userResponse.setUsername(user.getUsername());
    userResponse.setEmail(user.getEmail());
    userResponse.setPhone(user.getPhone());
    userResponse.setRoles(user.getRoles());

    return userResponse;
}
    @GetMapping()
    public List<UserResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponseDTOs = new ArrayList<>();
        for (User user : users) {
            UserResponse dto = new UserResponse();
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setRoles(user.getRoles());
            userResponseDTOs.add(dto);
        }
        return userResponseDTOs;
    }


    @GetMapping("/{username}")
    public UserResponse getUser(@PathVariable("username") String username){
        User user = userService.getUser(username);
        UserResponse userResponseDTO = new UserResponse();
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setRoles(user.getRoles());
        return userResponseDTO;

    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted"; }
    @PutMapping("/{username}")
    public UserResponse updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request){
        User user = userService.updateUser(username, request);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRoles(user.getRoles());
        return userResponse;
    }
}
