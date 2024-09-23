package invincibleDevs.bookpago.users.domain;

import invincibleDevs.bookpago.BaseEntity;
import invincibleDevs.bookpago.profile.model.Profile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    // 스프링 배치 - book 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nickname;
    private Gender gender;
    private LocalDate birth;

    // 연동 프로필
    @OneToOne(mappedBy = "users", cascade = CascadeType.REMOVE)
    private Profile profile;
}

