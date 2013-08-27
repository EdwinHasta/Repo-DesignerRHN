package Persistencia;

import Entidades.VWActualesAfiliacionesSalud;
import InterfacePersistencia.PersistenciaVWActualesAfiliacionesSaludInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesAfiliacionesSalud implements PersistenciaVWActualesAfiliacionesSaludInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesAfiliacionesSalud buscarAfiliacionSalud(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesAfiliacionesSalud vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesAfiliacionesSalud vwActualesAfiliacionesSalud = (VWActualesAfiliacionesSalud) query.getSingleResult();
            return vwActualesAfiliacionesSalud;
        } catch (Exception e) {
            VWActualesAfiliacionesSalud vwActualesAfiliacionesSalud=null;
            return vwActualesAfiliacionesSalud;
        }

        
    }
}
