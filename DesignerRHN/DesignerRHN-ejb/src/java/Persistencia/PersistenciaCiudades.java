package Persistencia;

import Entidades.Ciudades;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaCiudades implements PersistenciaCiudadesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<Ciudades> ciudades() {
        try {
            Query query = em.createQuery("SELECT c FROM Ciudades c ORDER BY c.nombre");
            List<Ciudades> ciudades = query.getResultList();
            return ciudades;
        } catch (Exception e) {
            return null;
        }
    }
}
