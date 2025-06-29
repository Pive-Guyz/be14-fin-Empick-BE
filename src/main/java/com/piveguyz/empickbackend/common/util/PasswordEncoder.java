package com.piveguyz.empickbackend.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordEncoder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        while(true) {
            System.out.print("Enter password : ");
            String rawPassword = sc.nextLine();
            if(rawPassword.equals("quit")){
                break;
            }
            String encodedPassword = encoder.encode(rawPassword);
            System.out.println("BCrypt 암호화된 비밀번호 : " + encodedPassword);
        }
    }
}
