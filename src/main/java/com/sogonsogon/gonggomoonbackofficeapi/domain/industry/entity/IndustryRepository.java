package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryResponse;

import java.util.List;
import java.util.Optional;

public interface IndustryRepository {

    Industry save(Industry industry);

    Optional<Industry> findById(Long industryId);

    List<IndustryResponse> findAllWithReportCount();

    boolean existsByName(String name);

    boolean existsById(Long industryId);

    void delete(Industry industry);
}
