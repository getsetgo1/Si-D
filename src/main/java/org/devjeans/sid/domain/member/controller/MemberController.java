package org.devjeans.sid.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devjeans.sid.domain.member.dto.*;
import org.devjeans.sid.domain.member.entity.Member;
import org.devjeans.sid.domain.member.service.MemberService;
import org.devjeans.sid.global.external.mail.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.devjeans.sid.domain.member.dto.MemberInfoResponse;
import org.devjeans.sid.domain.member.dto.RegisterMemberRequest;
import org.devjeans.sid.domain.member.dto.UpdateMemberRequest;
import org.devjeans.sid.domain.member.dto.UpdateMemberResponse;
import org.devjeans.sid.domain.auth.entity.KakaoRedirect;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final EmailService emailService;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable("memberId") Long memberId) {
        MemberInfoResponse memberInfo = memberService.getMemberInfo(memberId);

        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @PostMapping("/{memberId}/update")
    public ResponseEntity<UpdateMemberResponse> updateMemberInfo(
            @PathVariable Long memberId,
            @RequestBody UpdateMemberRequest updateMemberRequest) {

        UpdateMemberResponse updateMemberResponse = memberService.updateMemberInfo(memberId, updateMemberRequest);

        return new ResponseEntity<>(updateMemberResponse, HttpStatus.OK);
    }


    @PostMapping("/{memberId}/update/email")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody UpdateEmailRequest updateEmailRequest, @PathVariable Long memberId) {
        emailService.sendEmailNotice(updateEmailRequest.getEmail(), memberId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

//    // FIXME: 인증 구현 후 경로 수정 필요
//    @PostMapping("/code/{memberId}/{code}")
//    public ResponseEntity<?> updateEmail(@PathVariable String code,) {
//        memberService.updateEmail(code);
//    }

}
