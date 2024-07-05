package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.data.RouteRepository;
import bg.softuni.pathfinder.model.Picture;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.service.dto.RouteShortInfoDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RouteService {

    private RouteRepository routeRepository;
    private Random random;
    private ModelMapper modelMapper;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
        this.modelMapper = new ModelMapper();
        this.random = new Random();
    }

    @Transactional
    public List<RouteShortInfoDTO> getAll() {
        return routeRepository.findAll().stream().map(this::mapToShortInfo).toList();
    }

    @Transactional
    public RouteShortInfoDTO getRandomRoute() {

        long routeCount = routeRepository.count();
        long randomId = random.nextLong(routeCount) + 1;

        Optional<Route> routeById = routeRepository.findById(randomId);

        if (routeById.isEmpty()) {
            //throw exception;
        }

        return mapToShortInfo(routeById.get());
    }

    public RouteShortInfoDTO mapToShortInfo(Route route) {

        RouteShortInfoDTO dto = modelMapper.map(route, RouteShortInfoDTO.class);
        Optional<Picture> firstPicture = route.getPictures().stream().findFirst();
        dto.setImageUrl(firstPicture.get().getUrl());

        return dto;
    }
}
