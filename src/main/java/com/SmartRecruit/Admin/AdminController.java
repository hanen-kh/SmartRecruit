package com.SmartRecruit.Admin;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin (origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminController {

private  final AdminServiceImpl adminService;


@PostMapping
    public AdminDto create (@RequestBody Admin admin){
    return adminService.create(admin);
}
}
