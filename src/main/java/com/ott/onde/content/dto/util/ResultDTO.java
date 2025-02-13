package com.ott.onde.content.dto.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

@Builder@Data@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResultDTO {
    private String result;
    private Object data;
}
