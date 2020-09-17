package com.crazymakercircle.imClient.command;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service("LogoutConsoleCommand")
public class LogoutConsoleCommand implements BaseCommand {
    public static final String KEY = "10";

    @Override
    public void exec(Scanner scanner) {

    }


    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTip() {
        return "退出";
    }

}
