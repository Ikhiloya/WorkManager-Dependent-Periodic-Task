package com.ikhiloyaimokhai.companydemo.controller;

import com.ikhiloyaimokhai.companydemo.entity.Beneficiary;
import com.ikhiloyaimokhai.companydemo.error.ResourceNotFoundException;
import com.ikhiloyaimokhai.companydemo.service.BeneficiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class BeneficiaryController {
    private final Logger log = LoggerFactory.getLogger(BeneficiaryController.class);
    private BeneficiaryService beneficiaryService;

    public BeneficiaryController(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }

    @PostMapping("/beneficiary")
    public ResponseEntity<Beneficiary> createBeneficiary(@RequestBody Beneficiary beneficiary) throws ResourceNotFoundException {
        log.debug("REST request to save Beneficiary : {}", beneficiary);
        Beneficiary result = beneficiaryService.save(beneficiary);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/beneficiary")
    public List<Beneficiary> getAllBeneficiaries() {
        log.debug("REST request to get all Beneficiary");
        return beneficiaryService.findAll();
    }
}




