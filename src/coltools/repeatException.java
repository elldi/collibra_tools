package coltools;

/**
 * Created by Sam Leese on 16/07/2018.
 */
public class repeatException extends Exception {
    public repeatException(String message) {
        super("Base URL already exists in system");
    }

    public repeatException() {

    }
}
