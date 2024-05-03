package com.example.Login.service;

import com.example.Login.dtos.request.UserRequest;
import com.example.Login.dtos.request.UserUpdateRequest;
import com.example.Login.dtos.request.response.UserResponseDTO;
import com.example.Login.entity.User;
import com.example.Login.repository.UserRepository;
import com.example.Login.exception.AppException;
import com.example.Login.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Tạo một đối tượng người dùng mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Xác định vai trò của người dùng
        if ("ADMIN".equalsIgnoreCase(request.getRoles())) {
            user.setRoles("ADMIN");
        } else {
            user.setRoles("USER");
        }
        return userRepository.save(user);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUser(String username){
        return  userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }


    public User updateUser(String username, UserUpdateRequest request) {
            User user = getUser(username);
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(user);
        }

    public boolean checkLogin(String username, String password) {
        Optional<User> optionalUser = Optional.ofNullable(getUser(username));
        if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)){
            return true;
        }
        return false;
    }

}

