package Persistencia;

import Entidades.VWActualesAfiliacionesPension;
import InterfacePersistencia.PersistenciaVWActualesAfiliacionesPensionInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesAfiliacionesPension implements PersistenciaVWActualesAfiliacionesPensionInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesAfiliacionesPension buscarAfiliacionPension(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesAfiliacionesPension vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesAfiliacionesPension vwActualesAfiliacionesPension = (VWActualesAfiliacionesPension) query.getSingleResult();
            return vwActualesAfiliacionesPension;

        } catch (Exception e) {
            VWActualesAfiliacionesPension vwActualesAfiliacionesPension=null;
            return vwActualesAfiliacionesPension;
        }
    }
}
