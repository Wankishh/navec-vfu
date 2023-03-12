package com.navec.address.place;

import com.navec.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Place findById(Integer id) {
        return this.placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
    }

    public static class PlaceNotFoundException extends ResponseException {
        public PlaceNotFoundException() {
            super(HttpStatus.NOT_FOUND, "PlaceNotFound");
        }
    }
}
