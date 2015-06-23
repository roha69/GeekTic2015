package geektic.app.services;

import geektic.app.dao.InterestRepository;
import geektic.app.model.Interest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterestService {

    @Autowired
    private InterestRepository interestDAO;

    @Transactional
    public List<Interest> listinterets() {
        return interestDAO.findAll();
    }

}
