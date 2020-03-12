package com.telegramBot.command;



public class ParsedCommand {
    Command command = Command.NONE;
    String text="";

    public ParsedCommand(Command command, String text) {
        this.command = command;
        this.text = text;
    }

    public ParsedCommand() {
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
