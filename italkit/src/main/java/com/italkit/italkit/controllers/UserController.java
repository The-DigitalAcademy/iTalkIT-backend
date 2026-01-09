package com.italkit.italkit.controllers;


import com.italkit.italkit.models.UserModel;
import com.italkit.italkit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<UserModel> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/getUser/{id}")
    public Optional<UserModel> getUserById(@PathVariable(value = "id") Integer id){
        return userService.findUserById(id);
    }

    @PostMapping("/saveUser")
    public UserModel save(@RequestBody UserModel user){
        return userService.addUser(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer id){
        String msg = "User not found";
        Optional<UserModel> user = userService.findUserById(id);
        if(!user.isEmpty()){
        userService.deleteUser(id);
         msg = "user deleted successful";
        }

        return msg;
    }

}
