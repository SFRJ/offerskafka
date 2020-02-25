package com.codechallenge.offers.web;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientErrorResponse {

    private String error;
}
