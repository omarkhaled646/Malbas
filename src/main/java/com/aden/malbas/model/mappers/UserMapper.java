package com.aden.malbas.model.mappers;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.model.classes.User;
import com.aden.malbas.model.enums.AdultSize;
import com.aden.malbas.model.enums.Gender;
import com.aden.malbas.model.enums.Role;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    default Role mapRole(String role){
        return Role.valueOf(role.toUpperCase());
    }

    default Gender mapGender(String gender){
        return Gender.valueOf(gender.toUpperCase());
    }

    default AdultSize mapSize(String size){

        if(size == null){
            return null;
        }

        return AdultSize.valueOf(size.toUpperCase());
    }


    User userDTOToUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);

}
