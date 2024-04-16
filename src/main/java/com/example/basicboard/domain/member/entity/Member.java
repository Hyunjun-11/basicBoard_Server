package com.example.basicboard.domain.member.entity;


import com.example.basicboard.domain.board.Entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends TimeStamped {

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberId;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String memberPhone;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean withdrawn;
}

