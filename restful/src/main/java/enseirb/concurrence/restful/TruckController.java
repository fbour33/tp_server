package enseirb.concurrence.restful;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

@RestController
@RequestMapping("/truck")
public class TruckController {

    @Autowired TruckService truckService;

    @Autowired MapFromPositionService mapFromPositionService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTruckById(@PathVariable int id) {
        try{
            return new ResponseEntity<>(truckService.getTruckPosition(id), HttpStatus.OK);
        }catch(IllegalAccessException e){
            return new ResponseEntity<String>("Truck id doesn't exist, error 404",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/map")
    public ModelAndView getUrlMap(@PathVariable int id, ModelMap model, HttpServletResponse response){
        Position position;
        try {
            position = truckService.getTruckPosition(id);
        }catch(IllegalAccessException e){
            return new ModelAndView("error 404", HttpStatus.NOT_FOUND);
        }
        String stringURL = mapFromPositionService.getUrlMap(position);
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        //response.sendRedirect();
        return new ModelAndView("redirect:" + stringURL, model);
    }
}