package geektic.app.controllers;

import geektic.app.model.Interest;
import geektic.app.services.InterestService;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * REST service for interests.
 *
 */

@RestController
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    private InterestService interestDAO;

    @RequestMapping(method = GET)
    public List<Interest> listinterets() {
        return interestDAO.listinterets();
    }

}
