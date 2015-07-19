package geektic.app.controllers;

import geektic.app.model.EnumGender;
import geektic.app.model.Profile;
import geektic.app.services.ProfileService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 *
 * REST service for profiles.
 *
 */

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @RequestMapping(value = " /id/{id}", method = GET)
    public Profile getProfile(@PathVariable Long id) {
        return profileService.getProfile(id);
    }

    @RequestMapping(value = " /count", method = GET)
    public int countProfile() {
        return profileService.countProfile();
    }

    @RequestMapping(method = GET)
    public List<Profile> listProfiles() {
        return profileService.listProfiles();
    }

    @RequestMapping(value = " /gender/{gender}/interests/{interests}", method = GET)
    public List<Profile> getProfileGenreInterets(@PathVariable String gender, @PathVariable List<Long> interests) {
        EnumGender enumGenre = gender.equalsIgnoreCase("HOMME") ? EnumGender.HOMME : EnumGender.FEMME;
        return profileService.getProfileGenreInterets(enumGenre, interests);
    }
}
