package xyz.tomorrowlearncamp.outsourcing.domain.user.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "is_deleted = :isDeleted")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "email")
    private String email;
    private String password;
    private String phone;
    private String nickname;
    private String name;
    private String address;
    private Usertype usertype;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted;

    public UserEntity(String email, String password, @Nullable String phone, String nickname, String name, @Nullable String address, Usertype usertype) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
        this.address = address;
        this.usertype = usertype;
        this.isDeleted = false;
    }


    public void patchInfoUser(String nickname, String address) {
        this.nickname = nickname;
        this.address = address;
    }

    public void patchPasswordUser(String encodedPassword) {
        this.password = encodedPassword;
    }
}
