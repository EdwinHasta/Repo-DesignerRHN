package Persistencia;

import Entidades.VWActualesVigenciasViajeros;
import InterfacePersistencia.PersistenciaVWActualesVigenciasViajerosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesVigenciasViajeros implements PersistenciaVWActualesVigenciasViajerosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesVigenciasViajeros buscarTipoViajero(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesVigenciasViajeros vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesVigenciasViajeros vWActualesVigenciasViajeros = (VWActualesVigenciasViajeros) query.getSingleResult();
            return vWActualesVigenciasViajeros;
        } catch (Exception e) {
            VWActualesVigenciasViajeros vWActualesVigenciasViajeros = null;
            return vWActualesVigenciasViajeros;
        }
    }
}
