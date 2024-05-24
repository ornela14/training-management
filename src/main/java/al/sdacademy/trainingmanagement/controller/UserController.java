package al.sdacademy.trainingmanagement.controller;

import al.sdacademy.trainingmanagement.dto.authDtos.PasswordDto;
import al.sdacademy.trainingmanagement.dto.userDtos.NewUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UpdateUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UserDto;
import al.sdacademy.trainingmanagement.repository.criteria.UserCriteria;
import al.sdacademy.trainingmanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    /**
     * Get all users. if criteria is applied, users are filter accordingly
     *
     * @param criteria filter object
     */
    @PostMapping("/filter")
    public ResponseEntity<Page<UserDto>> listUsers(@RequestBody UserCriteria criteria) {
        return new ResponseEntity<>(userService.filterUsers(criteria), HttpStatus.OK);
    }

    /**
     * Retrieve user details
     *
     * @param id user id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * Save user
     *
     * @param user user dto
     */
    @PostMapping()
    public ResponseEntity<UserDto> saveUser(@RequestBody NewUserDto user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.ACCEPTED);
    }


    /**
     * Update user
     *
     * @param user updated dto
     */
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }


    /**
     * Delete user by id (soft deletion)
     *
     * @param id user id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Change user password
     *
     * @param passwordDto old password, new password
     */
    @PostMapping("/changePassword")
    public ResponseEntity<UserDto> changePassword(@RequestBody PasswordDto passwordDto) {
        return new ResponseEntity<>(userService.changePassword(passwordDto), HttpStatus.OK);
    }
}
