package com.task.crud.response_templates;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private int StatusCode;
    private T data;
}
