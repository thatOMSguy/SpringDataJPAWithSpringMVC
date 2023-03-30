package com.springrestmvcproject.spring6restmvc.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {

    private String customerName;
    private UUID id;
    private String version;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;




}