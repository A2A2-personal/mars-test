package com.mars.mars_test.controllers;

import com.mars.mars_test.requests.RegistrationRequest;
import com.mars.mars_test.requests.UpdateStatusRegistrationRequest;
import com.mars.mars_test.responses.DefaultResponse;
import com.mars.mars_test.services.RegistrationService;
import org.opensearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DefaultResponse<Object>> createRegistration(@RequestParam("name") String name,
                                                                      @RequestParam("address") String address,
                                                                      @RequestParam("phoneNumber") String phoneNumber,
                                                                      @RequestParam(value = "photo") MultipartFile photo) throws IOException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName(name);
        registrationRequest.setAddress(address);
        registrationRequest.setPhoneNumber(phoneNumber);
        registrationService.register(registrationRequest, photo);
        return ResponseEntity.ok(new DefaultResponse<>().success());
    }

    @GetMapping
    public Object getRegistrations() throws IOException {
        return ResponseEntity.ok(registrationService.getRegistrations());
    }

    @GetMapping("{id}")
    public Object getRegistration(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(registrationService.getRegistration(id));
    }

    @DeleteMapping("{id}")
    public Object deleteRegistration(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(registrationService.deleteRegistration(id));
    }

    @PutMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateResponse> updateRegistration(@PathVariable String id,
                                                             @RequestParam("name") String name,
                                                             @RequestParam("address") String address,
                                                             @RequestParam("phoneNumber") String phoneNumber,
                                                             @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setName(name);
        registrationRequest.setAddress(address);
        registrationRequest.setPhoneNumber(phoneNumber);
        return ResponseEntity.ok(registrationService.updateRegister(id, registrationRequest, photo));
    }

    @PatchMapping("{id}/status")
    public Object updateStatusRegistration(
            @RequestBody UpdateStatusRegistrationRequest updateStatusRegistrationRequest,
            @PathVariable String id) throws IOException {
        return ResponseEntity.ok(registrationService.updateStatusRegistration(id, updateStatusRegistrationRequest.getStatus()));
    }
}
