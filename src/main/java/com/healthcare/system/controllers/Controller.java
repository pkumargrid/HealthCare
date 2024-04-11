package com.healthcare.system.controllers;

import com.healthcare.system.dto.ResponseCrudDTO;

public interface Controller<T> {
    ResponseCrudDTO<T> execute();
}
