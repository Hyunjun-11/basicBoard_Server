package com.example.basicboard.domain.member.service;


import com.example.basicboard.global.message.Message;
import com.example.basicboard.global.message.StatusEnum;
import com.example.basicboard.domain.member.dto.MemberResponseDTO;
import com.example.basicboard.domain.member.dto.SignInRequestDTO;
import com.example.basicboard.domain.member.entity.Member;
import com.example.basicboard.domain.member.repository.MemberRepository;
import com.example.basicboard.domain.member.dto.MemberRequestDTO;
import com.example.basicboard.domain.member.dto.SignUpRequestDTO;
import com.example.basicboard.domain.member.interfaces.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;

    //멤버 전체조회
    @Override
    public ResponseEntity<Message> readAll() {

        List<Member> members = memberRepository.findAll();
        Message message = Message.setSuccess(StatusEnum.OK, "멤버 전체 조회 성공", members);

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //멤버 단일조회
    @Override
    public ResponseEntity<Message> readById(Long id) {

        Member member = findById(id);

        MemberResponseDTO responseDTO = new MemberResponseDTO(member);

        Message message = Message.setSuccess(StatusEnum.OK, " 멤버 단일 조회 성공", responseDTO);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //회원가입
    @Override
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
    @Override
    public ResponseEntity<Message> signIn(SignInRequestDTO requestDTO) {
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

    //회원정보 변경
    @Override
    @Transactional
    public ResponseEntity<Message> userInfoChange(Long id, MemberRequestDTO requestDTO) {


        Member member = findById(id);
        member.setMemberName(requestDTO.getMemberName());
        member.setMemberPhone(requestDTO.getMemberPhone());
        memberRepository.save(member);

        MemberResponseDTO responseDTO = new MemberResponseDTO(member);
        Message message = Message.setSuccess(StatusEnum.OK, "회원정보 변경 성공", responseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);


    }

    //로그아웃
    @Override
    public ResponseEntity<Message> signOut(SignInRequestDTO requestDTO) {
        Message message = Message.setSuccess(StatusEnum.OK, "로그아웃 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //회원탈퇴
    @Override
    @Transactional
    public ResponseEntity<Message> withdrawn(Long id, String password) {
        Member member = findById(id);
        if (checkPassword(member, password)) {
            member.setWithdrawn(true);
            member.setMemberId(member.getMemberId() + "withdrawn");
            memberRepository.save(member);
        }
        Message message = Message.setSuccess(StatusEnum.OK, "회원 탈퇴 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
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

    //비밀번호확인 메서드
    private boolean checkPassword(Member member, String password) {

        return member.getPassword().equals(password);

    }


}
