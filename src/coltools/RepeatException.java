package coltools;

/**
 * Created by Sam Leese on 16/07/2018.
 */
public class RepeatException extends Exception {
    public RepeatException(String message) {
        super("Base URL already exists in system");
    }

    public RepeatException() {

    }
}
