package com.ikhiloyaimokhai.companydemo.service;

import com.ikhiloyaimokhai.companydemo.entity.Beneficiary;
import com.ikhiloyaimokhai.companydemo.error.ResourceNotFoundException;
import com.ikhiloyaimokhai.companydemo.repository.BeneficiaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BeneficiaryService {
    private final Logger log = LoggerFactory.getLogger(BeneficiaryService.class);

    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryService(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    /**
     * Save a beneficiary.
     *
     * @param beneficiary the entity to save
     * @return the persisted entity
     */
    public Beneficiary save(Beneficiary beneficiary) throws ResourceNotFoundException {
        log.info("Request to save Beneficiary : {}", beneficiary);
        if (null == beneficiary.getPersonId())
            throw new ResourceNotFoundException("Person Entity not found");
        return beneficiaryRepository.save(beneficiary);
    }


    /**
     * Get all the beneficiary.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Beneficiary> findAll() {
        log.debug("Request to get all beneficiary");
        return beneficiaryRepository.findAll();
    }

}
