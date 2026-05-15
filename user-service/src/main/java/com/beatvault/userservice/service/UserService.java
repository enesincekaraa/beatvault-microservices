package com.beatvault.userservice.service;

import com.beatvault.userservice.dto.CreateUserRequest;
import com.beatvault.userservice.dto.UpdateBioRequest;
import com.beatvault.userservice.dto.UserResponse;
import com.beatvault.userservice.exception.UserAlreadyExistsException;
import com.beatvault.userservice.exception.UserNotFoundException;
import com.beatvault.userservice.model.User;
import com.beatvault.userservice.model.UserRole;
import com.beatvault.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest req){
        if (userRepository.existsByEmail(req.getEmail())) {
            log.error("Bu kullanıcı adı zaten kullanımda: {}", req.getUsername());
            throw new UserAlreadyExistsException("Username",req.getUsername());
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            log.error("Bu email zaten kullanımda: {}", req.getEmail());
            throw new UserAlreadyExistsException("Email", req.getEmail());
        }


        log.info("Yeni kullanıcı oluşturuluyor: {}", req.getUsername());
        User user = new User(
                req.getUsername(),
                req.getEmail(),
                req.getPassword()
        );

        log.info("Kullanıcı başarıyla oluşturuldu: {}", user.getUsername());
        return new UserResponse(userRepository.save(user));
    }


    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user= userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException(id)
        );

        log.info("Kullanıcı bulundu: {}", user.getUsername());
        return new UserResponse(user);
    }


    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user= userRepository.findByUsername(username).orElseThrow(
                ()-> new UserNotFoundException(username)
        );

        log.info("Kullanıcı bulundu: {}", user.getUsername());
        return new UserResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();

    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllDJs() {
        return userRepository.findAllByRole(UserRole.DJ)
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @Transactional
    public UserResponse updateBio(Long id, UpdateBioRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Kullanıcı bulundu: {}", user.getUsername());
        user.updateBio(req.getBio());
        log.info("Kullanıcı'nın bio'su güncellendi: {}", user.getUsername());
        return new UserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse promoteToDJ(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Kullanıcı bulundu: {}", user.getUsername());

        user.promoteToDj();
        log.info("Kullanıcı dj olarak güncellendi: {}", user.getUsername());
        return new UserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse banUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Kullanıcı bulundu: {}", user.getUsername());

        user.ban();
        log.info("Kullanıcı banlandı: {}", user.getUsername());
        return new UserResponse(userRepository.save(user));
    }


    @Transactional
    public UserResponse activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Kullanıcı bulundu: {}", user.getUsername());

        user.activate();
        log.info("Kullanıcı akfit hale getirildi: {}", user.getUsername());

        return new UserResponse(userRepository.save(user));
    }
}
