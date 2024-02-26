package com.epf.rentmanager.exception;

public class DaoException extends Exception{

    // Constructeur sans argument
    public DaoException() {
        super();
    }

    // Constructeur qui accepte un message d'erreur
    public DaoException(String message) {
        super(message);
    }

    // Constructeur qui accepte une cause (Throwable)
    public DaoException(Throwable cause) {
        super(cause);
    }

    // Constructeur qui accepte un message d'erreur et une cause
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur qui accepte un message d'erreur, une cause,
    // la suppression activée ou désactivée, et la traçabilité des piles d'exécution écrites ou non
    public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }




}
