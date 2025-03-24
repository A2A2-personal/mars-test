package com.mars.mars_test.services;

import com.mars.mars_test.enums.RegistrationStatusEnum;
import com.mars.mars_test.models.Photo;
import com.mars.mars_test.models.Registration;
import com.mars.mars_test.repositories.RegistrationRepository;
import com.mars.mars_test.requests.RegistrationRequest;
import org.opensearch.action.delete.DeleteResponse;
import org.opensearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    FileService fileService;

    public Registration register(RegistrationRequest registrationRequest, MultipartFile file) throws IOException {
        Registration registration = registrationRequest.getRegistration();
        registration.setStatus(RegistrationStatusEnum.PROCESSING);
        Photo photo = fileService.onUploadFile(file);
        if (photo == null) throw new HttpClientErrorException(HttpStatusCode.valueOf(422), "Upload photo error");
        registration.setPhotoUrl(photo.getPhotoUrl());
        registration.setPhotoPath(photo.getPhotoPath());
        registrationRepository.save(registration);
        return registration;
    }

    public UpdateResponse updateRegister(String id, RegistrationRequest registrationRequest, MultipartFile file) throws IOException {
        Registration registration = registrationRequest.getRegistration();
        Map<String, Object> updateMap = new java.util.HashMap<>(Map.of(
                "name", registration.getAddress(),
                "address", registration.getAddress(),
                "phoneNumber", registration.getPhoneNumber()
        ));
        if (file != null) {
            Photo photo = fileService.onUploadFile(file);
            if (photo == null) throw new HttpClientErrorException(HttpStatusCode.valueOf(422), "Upload photo error");
            registration.setPhotoUrl(photo.getPhotoUrl());
            registration.setPhotoPath(photo.getPhotoPath());
            updateMap.put("photoUrl", photo.getPhotoUrl());
            updateMap.put("photoPath", photo.getPhotoPath());
        }
        return registrationRepository.updateById(id, updateMap);
    }

    public List<Registration> getRegistrations() throws IOException {
        return registrationRepository.findAll();
    }

    public Registration getRegistration(String id) throws IOException {
        return registrationRepository.findById(id);
    }

    public DeleteResponse deleteRegistration(String id) throws IOException {
        return registrationRepository.deleteById(id);
    }

    public UpdateResponse updateStatusRegistration(String id, RegistrationStatusEnum registrationStatus) throws IOException {
        if (registrationStatus == RegistrationStatusEnum.PROCESSING)
            throw new HttpClientErrorException(HttpStatusCode.valueOf(422), "Only allow APPROVED or REJECTED");
        return registrationRepository.updateStatusById(id, registrationStatus);
    }
}
