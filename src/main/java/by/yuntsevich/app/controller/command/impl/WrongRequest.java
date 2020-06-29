package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;

public class WrongRequest implements Command {
    @Override
    public String execute(String request) {
        return "Wrong request";
    }
}
