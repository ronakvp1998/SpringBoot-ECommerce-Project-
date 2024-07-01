package com.ronaksales.electronic.store.ElectronicStore.dtos;

import com.ronaksales.electronic.store.ElectronicStore.entities.Role;
import com.ronaksales.electronic.store.ElectronicStore.validate.ImageNameValid;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min=3, max=15, message="Invalid name !!")
    private String name;

//    @Email(message="Invalid User Email !!")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid User Email !")
    @NotBlank(message = "Email is required !!")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(min = 4,max=6,message="invalid gender")
    @NotBlank(message="gender cannot be blank")
    private String gender;

    @NotBlank(message = "About cannot be blank !")
    private String about;

    @ImageNameValid
    private String imageName;

    private Set<RoleDto> roles = new HashSet<>();


}
