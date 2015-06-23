package geektic.app.services;

import geektic.app.dao.ProfileRepository;
import geektic.app.model.EnumGender;
import geektic.app.model.Profile;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business Service for Profile Class
 * 
 */
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileDAO;

    @Transactional
    public Profile getProfile(Long id) {
        profileDAO.increaseView(id);
        return profileDAO.findById(id);
    }

    @Transactional
    public int countProfile() {
        return profileDAO.count();
    }

    @Transactional
    public List<Profile> listProfiles() {
        return profileDAO.findAll();
    }

    @Transactional
    public List<Profile> getProfileGenreInterets(EnumGender gender, @PathVariable List<Long> interests) {
        return profileDAO.findByCriteria(gender, interests);
    }
}
