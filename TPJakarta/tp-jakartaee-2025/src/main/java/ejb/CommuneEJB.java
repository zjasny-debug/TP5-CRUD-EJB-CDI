package ejb;

import entity.Commune;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class CommuneEJB {

    @PersistenceContext
    private EntityManager em;

    // CREATE
    public Long createCommune(Commune commune) {
        em.persist(commune);
        return commune.getId();
    }

    // READ
    public Commune findCommuneById(Long id) {
        return em.find(Commune.class, id);
    }

    // UPDATE
    public Commune updateCommune(Commune commune) {
        return em.merge(commune);
    }

    // DELETE
    public boolean deleteCommuneById(Long id) {
        Commune c = em.find(Commune.class, id);
        if (c == null) return false;
        em.remove(c);
        return true;
    }

    // FIND ALL
    public List<Commune> findAllCommune() {
        return em.createQuery("SELECT c FROM Commune c", Commune.class)
                 .getResultList();
    }
}
