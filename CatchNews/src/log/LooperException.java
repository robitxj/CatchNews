package log;

import java.io.Serializable;

/**
 * 
 * @author guojin
 */

public class LooperException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -8753177202907981954L;

	public LooperException(final String message) {
		super(message);
	}

	public LooperException(final String message, final Exception ex) {
		super(message);
		initCause(ex);
	}

	public LooperException(final String message, final Throwable e) {
		super(message);
		initCause(e);
	}
}
