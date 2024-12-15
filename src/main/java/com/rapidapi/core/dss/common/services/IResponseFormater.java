package com.rapidapi.core.dss.common.services;

import org.springframework.http.ResponseEntity;

public interface IResponseFormater {
    public ResponseEntity<Object> getForbidden ();
    public ResponseEntity<Object> getNotFound ();
    ResponseEntity<Object> getBadRequest(String error);
}
