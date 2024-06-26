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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;

    //멤버 전체조회
    @Override
    public ResponseEntity<Message<List<MemberResponseDTO>>> readAll() {

        List<Member> members = memberRepository.findAll();
        List<MemberResponseDTO> memberResponseDTOS = new ArrayList<>();
        for (Member member : members) {
            memberResponseDTOS.add(new MemberResponseDTO(member));
        }
        Message<List<MemberResponseDTO>> message = Message.setSuccess(StatusEnum.OK, "멤버 전체 조회 성공", memberResponseDTOS);

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //멤버 단일조회
    @Override
    public ResponseEntity<Message<MemberResponseDTO>> readById(Long id) {

        Member member = findById(id);

        MemberResponseDTO responseDTO = new MemberResponseDTO(member);

        Message<MemberResponseDTO> message = Message.setSuccess(StatusEnum.OK, " 멤버 단일 조회 성공", responseDTO);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //회원가입
    @Override
    @Transactional
    public ResponseEntity<Message<MemberResponseDTO>> signUp(SignUpRequestDTO requestDTO) {

        if (isMemberExists(requestDTO.getMemberId())) {
            Message<MemberResponseDTO> message = Message.setSuccess(StatusEnum.CONFLICT, "이미 존재하는 회원입니다.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }

        Member member = createMember(requestDTO);
        memberRepository.save(member);
        MemberResponseDTO responseDTO = new MemberResponseDTO(member);
        Message<MemberResponseDTO> message = Message.setSuccess(StatusEnum.OK, "회원가입 성공", responseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }


    //로그인
    @Override
    public ResponseEntity<Message<MemberResponseDTO>> signIn(SignInRequestDTO requestDTO) {
        String memberId = requestDTO.getMemberId();
        String password = requestDTO.getPassword();

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        // 회원이 아이디가 일치하지않거나 비밀번호가 일치하지않을때
        if (optionalMember.isEmpty() || !optionalMember.get().getPassword().equals(password)) {
            Message<MemberResponseDTO> message = Message.setSuccess(StatusEnum.BAD_REQUEST, "회원 정보가 올바르지 않습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        // 로그인 성공
        MemberResponseDTO responseDTO = new MemberResponseDTO(optionalMember.get());
        Message<MemberResponseDTO> message = Message.setSuccess(StatusEnum.OK, "로그인 성공", responseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //회원정보 변경
    @Override
    @Transactional
    public ResponseEntity<Message<MemberResponseDTO>> userInfoChange(Long id, MemberRequestDTO requestDTO) {

        //멤버아이디와 principal 일치 확인로직작성하기
        Member member = findById(id);

        member.setMemberName(requestDTO.getMemberName());
        member.setMemberPhone(requestDTO.getMemberPhone());
        memberRepository.save(member);

        MemberResponseDTO responseDTO = new MemberResponseDTO(member);
        Message<MemberResponseDTO> message = Message.setSuccess(StatusEnum.OK, "회원정보 변경 성공", responseDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);


    }

    //로그아웃
    @Override
    public ResponseEntity<Message<String>> signOut(SignInRequestDTO requestDTO) {
        Message<String> message = Message.setSuccess(StatusEnum.OK, "로그아웃 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //회원탈퇴
    @Override
    @Transactional
    public ResponseEntity<Message<String>> withdrawn(Long id, String password) {
        Member member = findById(id);
        if (checkPassword(member, password)) {
            member.setWithdrawn(true);
            member.setMemberId(member.getMemberId() + "withdrawn");
            memberRepository.save(member);
        }
        Message<String> message = Message.setSuccess(StatusEnum.OK, "회원 탈퇴 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //Pk를 통한 회원정보 검색 메서드
    private Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));

    }

    //회원이 존재하면 true 아니면 false
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
