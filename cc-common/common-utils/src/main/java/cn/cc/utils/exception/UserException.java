package cn.cc.utils.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ICode code;

    public UserException(final UserException e) {
        super(e.getMessage(), e);
        this.code = e.getCode();
        if (Code.A00000 == this.code) {
            this.code = Code.A00001;
        }
        log.error("{}:{}", this.code, this.getMessage());
    }

    public UserException(final ICode code, final String message) {
        super(message);
        this.code = code;
        if (Code.A00000 == this.code) {
            this.code = Code.A00001;
        }
        log.error("{}:{}", this.code, this.getMessage());
    }

    public UserException(final ICode code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
        if (Code.A00000 == this.code) {
            this.code = Code.A00001;
        }
        log.error("{}:{}", this.code, this.getMessage());
    }

    public ICode getCode() {
        return code;
    }
    
}
