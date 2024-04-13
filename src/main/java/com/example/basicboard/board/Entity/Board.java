package com.example.basicboard.board.Entity;


import com.example.basicboard.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String author;

    @Column
    private int hits;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;



}
