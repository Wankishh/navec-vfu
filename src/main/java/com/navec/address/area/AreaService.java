package com.navec.address.area;


import com.navec.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AreaService {
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public Area findById(Integer id) throws ResponseException {
        return this.areaRepository.findById(id)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "Area not found"));
    }
}
