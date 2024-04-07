package com.epf.rentmanager.exception;

/**
 * Exception spécifique à l'interface utilisateur dans l'application RentManager.
 * Utilisée pour signaler des erreurs survenant au niveau de l'interface utilisateur.
 */
public class UiException extends RuntimeException {

    public UiException() {
        super();
    }

    public UiException(String message) {
        super(message);
    }

    public UiException(Throwable cause) {
        super(cause);
    }

    public UiException(String message, Throwable cause) {
        super(message, cause);
    }

    public UiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
