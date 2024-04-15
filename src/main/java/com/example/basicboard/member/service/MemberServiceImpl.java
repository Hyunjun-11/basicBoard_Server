package com.example.basicboard.member.service;


import com.example.basicboard.common.util.Message;
import com.example.basicboard.common.util.StatusEnum;
import com.example.basicboard.member.dto.MemberRequestDTO;
import com.example.basicboard.member.dto.MemberResponseDTO;
import com.example.basicboard.member.dto.SignUpRequestDTO;
import com.example.basicboard.member.entity.Member;
import com.example.basicboard.member.interfaces.MemberService;
import com.example.basicboard.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;

    //멤버 전체조회
    public ResponseEntity<Message> readAll() {

        List<Member> members = memberRepository.findAll();
        Message message = Message.setSuccess(StatusEnum.OK, "멤버 전체 조회 성공", members);

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //멤버 단일조회
    public ResponseEntity<Message> readById(Long id) {

        Member member = findById(id);

        MemberResponseDTO responseDTO = new MemberResponseDTO(member);

        Message message = Message.setSuccess(StatusEnum.OK, " 멤버 단일 조회 성공", responseDTO);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> signUp(SignUpRequestDTO requestDTO) {

        if (isMemberExists(requestDTO.getMemberId())) {
            Message message = Message.setSuccess(StatusEnum.CONFLICT, "이미 존재하는 회원입니다.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }

        Member member = createMember(requestDTO);
        memberRepository.save(member);
        Message message = Message.setSuccess(StatusEnum.OK, "회원가입 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);

    }


    //로그인
    public ResponseEntity<Message> signIn(MemberRequestDTO requestDTO) {
        String memberId = requestDTO.getMemberId();
        String password = requestDTO.getPassword();

        Member member = findByMemberId(memberId);

        if (!member.getPassword().equals(password)) {
            Message message = Message.setSuccess(StatusEnum.BAD_REQUEST, "회원 정보가 올바르지 않습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        // 로그인에 성공했을 경우 성공 메시지와 함께 회원 정보를 반환합니다.
        MemberResponseDTO responseDTO = new MemberResponseDTO(member);
        Message message = Message.setSuccess(StatusEnum.OK, "로그인 성공", responseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Message> userInfoChange(MemberRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Message> signOut(MemberRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Message> withdrawn(MemberRequestDTO requestDTO) {
        return null;
    }

    //Pk를 통한 회원정보 검색 메서드
    private Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));

    }

    //MemberId를 통한 회원정보
    private Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));
    }


    //회원이 존재하면true 아니면false
    private boolean isMemberExists(String memberId) {
        return memberRepository.findByMemberId(memberId).isPresent();
    }

    private Member createMember(SignUpRequestDTO requestDTO) {
        return Member.builder()
                .memberId(requestDTO.getMemberId())
                .memberName(requestDTO.getMemberName())
                .password(requestDTO.getPassword())
                .memberPhone(requestDTO.getMemberPhone())
                .gender(requestDTO.getGender())
                .build();
    }


}
