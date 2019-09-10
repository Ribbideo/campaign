package com.kencorhealth.campaign.dm.exception;

public class ExistsException extends CampaignBasedException {
    public ExistsException() {
        super();
    }

    public ExistsException(String message) {
        super(message);
    }

    public ExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsException(Throwable cause) {
        super(cause);
    }

    public ExistsException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int errorCode() {
        return 409;
    }
}
