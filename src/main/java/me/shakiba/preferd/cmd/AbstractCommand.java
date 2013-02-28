package me.shakiba.preferd.cmd;

import me.shakiba.preferd.Options;

public abstract class AbstractCommand {

    protected final String user;

    protected final String space;

    public AbstractCommand() {
	user = System.getProperty("user.name");
	space = user.replaceAll(".", " ");
    }

    public abstract void execute(Options opts) throws Exception;
}