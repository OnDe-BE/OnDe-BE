package com.ott.onde.content.dto.result;

import com.ott.onde.content.dto.request.ContentRequest;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Data
@Getter@Setter
public class ContentPreferResult {
    private Page<ContentRequest> contents;
    private String titleTypes;

    @Builder
    private ContentPreferResult(Page<ContentRequest> contents, String titleTypes) {
        this.contents = contents;
        this.titleTypes = titleTypes;
    }
}
