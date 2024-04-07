package com.epf.rentmanager.exception;

/**
 * Exception spécifique aux services dans l'application RentManager.
 * Utilisée pour signaler des erreurs survenant au niveau de la couche service.
 */
public class ServiceException extends RuntimeException {

    /**
     * Construit une nouvelle instance de ServiceException sans message d'erreur ni cause.
     */
    public ServiceException() {
        super();
    }

    /**
     * Construit une nouvelle instance de ServiceException avec un message d'erreur spécifique.
     *
     * @param message le message d'erreur détaillant la cause de l'exception.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Construit une nouvelle instance de ServiceException causée par une autre exception.
     *
     * @param cause l'exception sous-jacente qui est la cause de cette exception.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Construit une nouvelle instance de ServiceException avec un message d'erreur spécifique et une cause.
     *
     * @param message le message d'erreur détaillant la cause de l'exception.
     * @param cause l'exception sous-jacente qui est la cause de cette exception.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construit une nouvelle instance de ServiceException avec un message d'erreur spécifique, une cause,
     * et la possibilité de contrôler l'activation de la suppression et si la pile d'exécution est écrite.
     *
     * @param message le message d'erreur détaillant la cause de l'exception.
     * @param cause l'exception sous-jacente qui est la cause de cette exception.
     * @param enableSuppression indique si la suppression est activée ou non pour cette exception.
     * @param writableStackTrace indique si la pile d'exécution est écrite ou non pour cette exception.
     */
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
