package com.example.GreenBack.controller;

import com.example.GreenBack.dto.VehicleAdminDto;
import com.example.GreenBack.dto.VehicleDTO;
import com.example.GreenBack.entity.*;
import com.example.GreenBack.service.impl.ImageStorageService;
import com.example.GreenBack.service.impl.VehicleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleServiceImpl vehicleService;
    private final ImageStorageService imageStorageService;

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("hiiiiiiiiiiiiiiiiii");
        return "Hello!";
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createVehicle(
            @RequestParam("vehicle") String vehicleJson,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VehicleDTO vehicleRequest = objectMapper.readValue(vehicleJson, VehicleDTO.class);

            String imagePath = imageStorageService.uploadImage(imageFile);
            vehicleRequest.setPictureUrl(imagePath);

            VehicleDTO savedVehicle = vehicleService.saveVehicle(vehicleRequest);
            return ResponseEntity.ok(savedVehicle);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON or file processing error.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

        @GetMapping("/image/{vehicleId}")
        public ResponseEntity<Resource> getProfileImage(@PathVariable Long vehicleId) throws IOException {
            String filename = vehicleService.getProfileImageFilename(vehicleId); // just the filename
            Resource image = imageStorageService.loadAsResource(filename);
    
            // Get absolute path of the image file
            Path imagePath = Paths.get("uploads/images").resolve(filename).normalize();
            String contentType = Files.probeContentType(imagePath);
    
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback
            }
    
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(image);
        }





    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/getall")
    public ResponseEntity<List<VehicleAdminDto>> getAllVehiclesForAdmin() {
        return ResponseEntity.ok(vehicleService.getAllVehiclesForAdmin());
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }
    @GetMapping("/count")
    public ResponseEntity<String> getVehicleCount() {
        long count = vehicleService.countVehicles();
        return ResponseEntity.ok("Total vehicles in database: " + count);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle updatedVehicle
    ) {
        return ResponseEntity.ok(vehicleService.updateVehicleById(id, updatedVehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ride/{rideId}")
    public ResponseEntity<Vehicle> getVehicleByRideId(@PathVariable Long rideId) {
        return ResponseEntity.ok(vehicleService.getVehicleByRideId(rideId));
    }
}
