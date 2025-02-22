package com.example.FXstatic.service.impl;

import com.example.FXstatic.dto.UserDTO;
import com.example.FXstatic.models.AppRole;
import com.example.FXstatic.models.Role;
import com.example.FXstatic.models.User;
import com.example.FXstatic.repository.RoleRepo;
import com.example.FXstatic.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleRepo roleRepository;


    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        AppRole appRole = AppRole.valueOf(roleName);
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public UserDTO getUserById(Long id) {
//        return userRepository.findById(id).orElseThrow();
        User user = userRepository.findById(id).orElseThrow();
        return convertToDto(user);
    }

    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.getTwoFactorSecret(),
                user.isTwoFactorEnabled(),
                user.getSignUpMethod(),
                user.getRole(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }


    public void updateAccountLockStatus(Long userId, boolean lock) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        user.setAccountNonLocked(!lock);
        userRepository.save(user);
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void updateAccountExpiryStatus(Long userId, boolean expire) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        user.setAccountNonExpired(!expire);
        userRepository.save(user);
    }

    public void updateAccountEnabledStatus(Long userId, boolean enabled) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    public void updateCredentialsExpiryStatus(Long userId, boolean expire) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        user.setCredentialsNonExpired(!expire);
        userRepository.save(user);
    }


    public void updatePassword(Long userId, String password) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update password");
        }
    }

//    public void generatePasswordResetToken(String email){
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String token = UUID.randomUUID().toString();
//        Instant expiryDate = Instant.now().plus(24, ChronoUnit.HOURS);
//        PasswordResetToken resetToken = new PasswordResetToken(token, expiryDate, user);
//        passwordResetTokenRepository.save(resetToken);
//
//        String resetUrl = frontendUrl + "/reset-password?token=" + token;
//        // Send email to user
//        emailService.sendPasswordResetEmail(user.getEmail(), resetUrl);
//    }


//    public void resetPassword(String token, String newPassword) {
//        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
//                .orElseThrow(() -> new RuntimeException("Invalid password reset token"));
//
//        if (resetToken.isUsed())
//            throw new RuntimeException("Password reset token has already been used");
//
//        if (resetToken.getExpiryDate().isBefore(Instant.now()))
//            throw new RuntimeException("Password reset token has expired");
//
//        User user = resetToken.getUser();
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//
//        resetToken.setUsed(true);
//        passwordResetTokenRepository.save(resetToken);
//    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


//    public User registerUser(User user) {
//        if (user.getPassword() != null)
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }

//    public GoogleAuthenticatorKey generate2FASecret(Long userId){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        GoogleAuthenticatorKey key = totpService.generateSecret();
//        user.setTwoFactorSecret(key.getKey());
//        userRepository.save(user);
//        return key;
//    }

    //    public boolean validate2FACode(Long userId, int code){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return totpService.verifyCode(user.getTwoFactorSecret(), code);
//    }
//
//    public void enable2FA(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        user.setTwoFactorEnabled(true);
//        userRepository.save(user);
//    }
//
//    public void disable2FA(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        user.setTwoFactorEnabled(false);
//        userRepository.save(user);
//    }
}

