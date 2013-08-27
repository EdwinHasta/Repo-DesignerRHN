package Persistencia;

import Entidades.VWActualesLocalizaciones;
import InterfacePersistencia.PersistenciaVWActualesLocalizacionesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesLocalizaciones implements PersistenciaVWActualesLocalizacionesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesLocalizaciones buscarLocalizacion(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesLocalizaciones vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesLocalizaciones vwActualesLocalizaciones = (VWActualesLocalizaciones) query.getSingleResult();
            return vwActualesLocalizaciones;
        } catch (Exception e) {
        }
        VWActualesLocalizaciones vwActualesLocalizaciones = null;
        return vwActualesLocalizaciones;

    }
}
