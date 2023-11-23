package com.avensys.rts.workexperienceservice.APIClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.avensys.rts.workexperienceservice.customresponse.HttpResponse;
import com.avensys.rts.workexperienceservice.interceptor.JwtTokenInterceptor;
import com.avensys.rts.workexperienceservice.payloadnewrequest.DocumentDeleteRequestDTO;
import com.avensys.rts.workexperienceservice.payloadnewrequest.DocumentRequestDTO;

/**
 * author Koh He Xiang
 * This class is an interface to interact with document microservice
 */
@Configuration
@FeignClient(name = "document-service", url = "http://localhost:8500", configuration = JwtTokenInterceptor.class)
public interface DocumentAPIClient {
    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    HttpResponse createDocument(@ModelAttribute DocumentRequestDTO documentRequest);

    @PutMapping(value = "/documents" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    HttpResponse updateDocument(@ModelAttribute DocumentRequestDTO documentRequest);

    @DeleteMapping("/documents")
    HttpResponse deleteDocumentByEntityIdAndType(@RequestBody DocumentDeleteRequestDTO documentDeleteRequestDTO);

    @GetMapping("/documents")
    HttpResponse getDocumentByEntityTypeAndId(@RequestParam String entityType, @RequestParam int entityId);

    @DeleteMapping("/documents/entity/{entityType}/{entityId}")
    HttpResponse deleteDocumentsByEntityTypeAndEntityId(@PathVariable String entityType, @PathVariable Integer entityId);
}
