package com.luisfelipedejesusm.simplecrudwithformbasedauth.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date publish_date;

    @ManyToMany(
            mappedBy = "books",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        users.add(user);
        user.getBooks().add(this);
    }

//    TODO: One to One Relationship
//    private Author author;

}
