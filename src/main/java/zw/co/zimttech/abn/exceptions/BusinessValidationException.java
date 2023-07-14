

package zw.co.zimttech.abn.exceptions;

public class BusinessValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessValidationException() {
    }

    public BusinessValidationException(final String message) {
        super(message);
    }
}
