package allclear.controller;

import allclear.domain.member.UserDetailsImpl;
import allclear.dto.requestDto.*;
import allclear.dto.responseDto.MemberResponseDto;
import allclear.global.exception.GlobalException;
import allclear.global.exception.code.GlobalErrorCode;
import allclear.global.response.ApiResponse;
import allclear.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/test/ok")
    public ApiResponse test(){
        return ApiResponse.onSuccess("mapping test", "");
    }

    @GetMapping("/test/exception")
    public ApiResponse test2(){
        return ApiResponse.onFailure(GlobalErrorCode._BAD_REQUEST, "");
    }

    @PostMapping("/test/post")
    public ApiResponse test3(@RequestBody String string){
        return ApiResponse.onSuccess(string, "");
    }

    @GetMapping("/test/error")
    public ApiResponse test4(){
        memberService.temp();
        return ApiResponse.onSuccess("exception2", "");
    }

    //회원가입
    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody MemberSignupRequestDto userSignupRequestDto){
        try {
            memberService.createMember(userSignupRequestDto);
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), "");
        }
        return ApiResponse.onSuccess("회원가입에 성공했습니다","");
    }

    @Operation(summary = "회원가입 - 이메일 인증")
    @PostMapping("/signup/emailAuth")
    public ApiResponse emailAuth(@RequestBody EmailAuthRequestDto emailAuthRequestDto){
        memberService.sendEmailCode(emailAuthRequestDto);
        return ApiResponse.onSuccess("이메일 인증 코드를 발송했습니다","");
    }

    @Operation(summary = "회원가입 - 이메일 인증 코드 확인")
    @PostMapping("/signup/emailIsValid")
    public ApiResponse emailIsValid(@RequestBody EmailIsValidRequestDto emailIsValidRequestDto){
        Boolean isValid = memberService.isEmailValid(emailIsValidRequestDto);
        if(isValid)
            return ApiResponse.onSuccess("이메일 인증에 성공했습니다","");
        else
            return ApiResponse.onFailure(GlobalErrorCode._INVALID_CODE,"");
    }


    //로그읜
    @Operation(summary = "로그인", description = "이메일, 비밀번호")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequestDto loginRequestDto){
        memberService.login(loginRequestDto);
        return ApiResponse.onSuccess("로그인에 성공했습니다","");
    }

    //업데이트
    @Operation(summary = "정보 업데이트", description = "유세인트 Id, Pwd 필요")
    @PostMapping("/update")
    public ApiResponse update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @RequestBody UpdateRequestDto updateRequestDto){
        try {
            memberService.updateMember(userDetails, updateRequestDto);
        } catch (GlobalException e){
            return ApiResponse.onFailure(e.getErrorCode(), "");
        }
        return ApiResponse.onSuccess("정보 업데이트에 성공했습니다","");
    }


    //로그아웃
    @Operation(summary = "로그아웃", description = "로그아웃")
    @GetMapping("/logout")
    public void logout(){

    }

    //회원탈퇴
    @Operation(summary = "회원탈퇴", description = "회원탈퇴")
    @PostMapping("/delete")
    public ApiResponse delete(Long userId) {
        memberService.deleteMember(userId);
        return ApiResponse.onSuccess("회원 탈퇴에 성공했습니다", "");
    }


    //유저조회
    @Operation(summary = "유저 조회", description = "유저 조회")
    //@GetMapping("") //string 수정 필요
    public ApiResponse<MemberResponseDto> get(Long userId) { //인자 수정 필요
        return ApiResponse.onSuccess("유저 조회에 성공했습니다", memberService.getMember(userId));
    }
}
