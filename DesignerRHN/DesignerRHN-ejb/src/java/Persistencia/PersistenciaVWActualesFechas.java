package Persistencia;

import InterfacePersistencia.PersistenciaVWActualesFechasInterface;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author -Felipphe-
 */
@Stateless
public class PersistenciaVWActualesFechas implements PersistenciaVWActualesFechasInterface{

    @Override
    public Date actualFechaHasta(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT vw.fechaHastaCausado FROM VWActualesFechas vw");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Date actualFechaHasta = (Date) query.getSingleResult();
            return actualFechaHasta;
        } catch (Exception e) {
            System.out.println("Exepcion: PersistenciaVWActualesFechas.actualFechaHasta: " + e);
            return null;
        }
    }
}
