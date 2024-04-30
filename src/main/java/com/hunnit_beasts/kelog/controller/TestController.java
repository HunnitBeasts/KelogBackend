package com.hunnit_beasts.kelog.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Swagger Test", description = "This is a test API")
//@Hidden // 일단 숨겨놓고 필요에 따라 다시 올리자
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class TestController {

    @Operation(summary = "스웨거 GET 테스트", description = "테스트 api 1 입니다!")
    @GetMapping("/read")
    public String getTest(){
        return "Hello, Swagger!";
    }

    @Operation(summary = "스웨거 PUT 테스트", description = "테스트 api 2 입니다!")
    @ApiResponse(responseCode = "200", description = "이럴 경우 성공 응답입니다.")
    @ApiResponse(responseCode = "400", description = "이럴 경우 실패 응답입니다.")
    @PutMapping("update")
    public String putTest(){
        return "update Complete";
    }

    @Hidden
    @GetMapping("/ignore")
    public String ignore(){
        return "무시되는 API";
    }
}
