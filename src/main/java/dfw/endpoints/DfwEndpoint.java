package dfw.endpoints;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dfw.core.model.Coordinate;
import dfw.core.model.InterpolationStrategy;
import dfw.core.model.Shape;
import dfw.core.model.Tract;
import dfw.core.service.InterpolationService;
import dfw.database.DataRepository;
import jakarta.ws.rs.Produces;

@RestController
@RequestMapping("api")
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class DfwEndpoint {

    // TODO: error handling

    @Autowired(required = true)
    private DataRepository repo;

    @GetMapping("/shapes")
    public List<Shape> getAllShapes() {
        return repo.findAllShapes();
    }

    @GetMapping("/compute")
    public Map<String, Double> getResult(
            @RequestParam(required = true) double lat,
            @RequestParam(required = true) double lng,
            @RequestParam(name = "rad", required = true) double radius,
            @RequestParam(name = "strategyId", required = true) int strategyId) {

        List<Tract> tracts = repo.findTracts(new Coordinate(lat, lng), radius,
                InterpolationStrategy.fromId(strategyId));

        return Map.of(
                "totalPopulation", InterpolationService.computeTotalPopulation(tracts),
                "medianIncome", InterpolationService.computeMedianIncome(tracts));
    }
}
