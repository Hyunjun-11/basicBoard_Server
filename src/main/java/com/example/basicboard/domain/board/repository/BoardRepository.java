package com.example.basicboard.domain.board.repository;

import com.example.basicboard.domain.board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b JOIN b.member m WHERE m.memberName = :username")
    List<Board> findByMemberUsername(@Param("username") String username);

}
