package com.example.Login.Service;

import com.example.Login.Dto.Request.UserRequest;
import com.example.Login.Dto.Request.UserUpdateRequest;
import com.example.Login.Entity.User;
import com.example.Login.Repository.UserRepository;
import com.example.Login.exception.AppException;
import com.example.Login.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequest request) {
        User user = new User();
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUser(String id){
        return  userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }


    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        return userRepository.save(user);
    }
}
