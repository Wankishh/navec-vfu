package com.navec.section;

import com.navec.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }


    public Section findById(final Long id) {
        return this.sectionRepository.findById(id)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND));
    }

    public List<SectionDto> getSections() {
        Iterable<Section> sections = this.sectionRepository.findAll();
        List<SectionDto> sectionDtoList = new ArrayList<>();
        sections.forEach(s -> sectionDtoList.add(new SectionDto(s)));
        return sectionDtoList;
    }
}
