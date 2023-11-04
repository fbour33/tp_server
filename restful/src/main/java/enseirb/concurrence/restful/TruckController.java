package enseirb.concurrence.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;

@RestController
@RequestMapping("/truck")
public class TruckController {

    //@Autowired Producer producer;
    @Autowired TruckService truckService;
    @Autowired MapFromPositionService mapFromPositionService;

    @GetMapping("/{id}")
    public ResponseEntity<Position> getTruckById(@PathVariable int id) {
        try {
            Position position = truckService.getTruckPosition(id);
            return ResponseEntity.ok(position);
        } catch (IllegalAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/map")
    public ModelAndView redirectToTheLastPosition(@PathVariable int id, ModelMap model) {
        Position position;
        try {
             position = truckService.getTruckPosition(id);
        } catch (IllegalAccessException e){
            return new ModelAndView("error404", HttpStatus.NOT_FOUND);
        }
        String redirectUrl = mapFromPositionService.getMapWithPosition(position);
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        return new ModelAndView("redirect:" + redirectUrl, model);
    }
}