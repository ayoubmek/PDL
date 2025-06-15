package com.pdl.pdl.controller;

import com.pdl.pdl.entity.AppUser;
import com.pdl.pdl.entity.Role;
import com.pdl.pdl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{userId}/roles")
    public ResponseEntity<AppUser> updateUserRoles(
            @PathVariable Long userId,
            @RequestBody Set<Role> roles) {
        return ResponseEntity.ok(userService.updateUserRoles(userId, roles));
    }
}