package com.cosmose.service;


import com.cosmose.dto.UserDTO;

/**
 * Created by damian on 24.08.18.
 */
public interface UserService<T extends UserDTO> {

    T create(T t);
}
