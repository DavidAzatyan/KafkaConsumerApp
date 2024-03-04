package org.spring.kafka.dao;

import org.spring.kafka.models.Json;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class JsonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public JsonDAO(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Json> read() {
        Session session = sessionFactory.getCurrentSession();
        List<Json> selectJFromJsonJ = session.createQuery("select j from Json j", Json.class).getResultList();
        return selectJFromJsonJ;
    }

    @Transactional
    public void save(Json json) {
        Session session = sessionFactory.getCurrentSession();
        session.save(json);
    }
}
