package Persistencia;

import Entidades.VWActualesUbicaciones;
import InterfacePersistencia.PersistenciaVWActualesUbicacionesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesUbicaciones implements PersistenciaVWActualesUbicacionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesUbicaciones buscarUbicacion(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesUbicaciones vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesUbicaciones vWActualesUbicaciones = (VWActualesUbicaciones) query.getSingleResult();
            return vWActualesUbicaciones;
        } catch (Exception e) {
            VWActualesUbicaciones vWActualesUbicaciones = null;
            return vWActualesUbicaciones;
        }
    }
}
