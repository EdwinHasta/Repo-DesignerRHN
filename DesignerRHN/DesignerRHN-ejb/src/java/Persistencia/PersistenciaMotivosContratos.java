package Persistencia;

import Entidades.MotivosContratos;
import InterfacePersistencia.PersistenciaMotivosContratosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaMotivosContratos implements PersistenciaMotivosContratosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
     public List<MotivosContratos> motivosContratos() {
        try {
            Query query = em.createQuery("SELECT m FROM MotivosContratos m ORDER BY m.codigo");
            List<MotivosContratos> motivosContratos = query.getResultList();
            return motivosContratos;
        } catch (Exception e) {
            return null;
        }
    }

}
