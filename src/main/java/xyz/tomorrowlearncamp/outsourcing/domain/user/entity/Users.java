package xyz.tomorrowlearncamp.outsourcing.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String phone;
    private String nickname;
    private String name;
    private String address;
    private Usertype usertype;

    public Users(String email, String password, String phone, String nickname, String name, String address, Usertype usertype) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
        this.address = address;
        this.usertype = usertype;
    }
}
