package com.mars.mars_test.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {
    private String fileId;
    private String fullUrl;
}
