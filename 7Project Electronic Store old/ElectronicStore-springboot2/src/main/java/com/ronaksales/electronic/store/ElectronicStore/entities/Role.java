package com.ronaksales.electronic.store.ElectronicStore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    private String roleId;
    private String roleName;

    // Roles
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
