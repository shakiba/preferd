package x.preferd;

public class UsageException extends Exception {
    private static final long serialVersionUID = -3038574576981390837L;

    public UsageException() {
        super("Invalid usage!");
    }

    public UsageException(String msg) {
        super(msg);
    }
}
