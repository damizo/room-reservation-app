package com.cosmose.mapper;

import com.cosmose.dto.UserDTO;
import com.cosmose.entity.User;
import org.springframework.stereotype.Component;

/**
 * Created by damian on 24.08.18.
 */

public interface UserMapper<S extends User, T extends UserDTO> {

    S toDomain(T user);

    T fromDomain(S user);
}
