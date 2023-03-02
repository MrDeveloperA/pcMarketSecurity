package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.entity.Brand;
import com.example.pcmarketsecurity.entity.Partner;
import com.example.pcmarketsecurity.repository.BrandRepository;
import com.example.pcmarketsecurity.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.util.Optional;

@RestController
@RequestMapping("/api/partner")
public class PartnerController {
    @Autowired
    PartnerRepository partnerRepository;

    //    Create
//    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addBrand(@RequestBody Partner partner) {

        Partner partner1 = new Partner();
        partner1.setName(partner.getName());

        Partner savePartner = partnerRepository.save(partner1);
        return ResponseEntity.ok(savePartner);
    }

    //    Get
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getPartner() {
        return ResponseEntity.ok(partnerRepository.findAll());
    }

    //   get by id
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getPartnerById(@PathVariable Integer id) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        return ResponseEntity.status(optionalPartner.isPresent() ? 200 : 404).body(optionalPartner.orElse(null));
    }

    //    Update
    //    SUPER_ADMIN, MODERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editPartner(@PathVariable Integer id, @RequestBody Partner partner) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        if (!optionalPartner.isPresent())
            return ResponseEntity.notFound().build();

        Partner editingPartner = optionalPartner.get();
        editingPartner.setName(partner.getName());

        Partner savePartner = partnerRepository.save(editingPartner);
        return ResponseEntity.ok(savePartner);
    }

    //    Delete
    //    SUPER_ADMIN
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePartner(@PathVariable Integer id) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        if (!optionalPartner.isPresent())
            return ResponseEntity.notFound().build();

        partnerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
