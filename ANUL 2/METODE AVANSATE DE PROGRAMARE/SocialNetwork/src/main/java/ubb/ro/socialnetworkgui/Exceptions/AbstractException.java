package ubb.ro.socialnetworkgui.Exceptions;

public class AbstractException extends RuntimeException{
    public AbstractException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
