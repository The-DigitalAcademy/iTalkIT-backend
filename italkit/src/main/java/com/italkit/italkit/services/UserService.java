package com.italkit.italkit.services;

import com.italkit.italkit.models.UserModel;
import com.italkit.italkit.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //get All users from the Database
    public List<UserModel> getUsers(){
        return userRepo.findAll();
    }

    //This is when we register a new user
    public UserModel addUser(UserModel user){
        return userRepo.save(user);
    }

    public Optional<UserModel> findUserById(Integer id){
        return userRepo.findById(id);
    }

    public void deleteUser(Integer user_id){
        userRepo.deleteById(user_id);
    }

}
