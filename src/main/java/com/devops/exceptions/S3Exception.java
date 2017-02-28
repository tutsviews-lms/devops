package com.devops.exceptions;

/**
 * Created by ALadin Zaier PC IBS on 28/02/2017.
 */
public class S3Exception extends RuntimeException {

    public S3Exception(Throwable e) {
        super(e);
    }
}
