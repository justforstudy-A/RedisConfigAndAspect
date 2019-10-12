package com.springboot.demo.Controller;

import com.springboot.demo.Req.TestValidatedReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validated")
public class TestValidationController {

    @PostMapping("testValidated")
    public String testValidated(@RequestBody @Validated TestValidatedReq req){

        return "success";
    }
}
