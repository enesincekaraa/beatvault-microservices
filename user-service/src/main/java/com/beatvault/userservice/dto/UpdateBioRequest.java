package com.beatvault.userservice.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBioRequest {

    @Size(max = 300, message = "Bio 300 karakterden uzun olamaz")
    private String bio;
}