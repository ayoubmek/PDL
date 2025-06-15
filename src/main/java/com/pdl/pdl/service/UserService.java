package com.pdl.pdl.service;

import com.pdl.pdl.entity.AppUser;
import com.pdl.pdl.entity.History;
import com.pdl.pdl.entity.Role;
import com.pdl.pdl.repository.HistoryRepository;
import com.pdl.pdl.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HistoryRepository historyRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, HistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.historyRepository = historyRepository;
    }

    public AppUser loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<AppUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public AppUser createUser(AppUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser savedUser = userRepository.save(user);

        History history = new History();
        history.setAction("Create User");
        history.setDetails("Created user with ID: " + savedUser.getUserId());
        history.setActionDate(LocalDate.now());
        historyRepository.save(history);

        return savedUser;
    }

    public AppUser updateUser(Long id, AppUser userDetails) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean changed = false;

        if (!user.getUsername().equals(userDetails.getUsername()) &&
                userRepository.existsByUsername(userDetails.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setUsername(userDetails.getUsername());
        user.setBirthdate(userDetails.getBirthdate());
        changed = true;

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            changed = true;
        }

        AppUser updatedUser = userRepository.save(user);

        if (changed) {
            History history = new History();
            history.setAction("Update User");
            history.setDetails("Updated user with ID: " + id);
            history.setActionDate(LocalDate.now());
            historyRepository.save(history);
        }

        return updatedUser;
    }

    public void deleteUser(Long id) {
        Optional<AppUser> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);

            History history = new History();
            history.setAction("Delete User");
            history.setDetails("Deleted user with ID: " + id);
            history.setActionDate(LocalDate.now());
            historyRepository.save(history);
        }
    }

    public AppUser updateUserRoles(Long userId, Set<Role> roles) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setAuthorities(roles);
        AppUser updated = userRepository.save(user);

        History history = new History();
        history.setAction("Update User Roles");
        history.setDetails("Updated roles for user with ID: " + userId);
        history.setActionDate(LocalDate.now());
        historyRepository.save(history);

        return updated;
    }
}
