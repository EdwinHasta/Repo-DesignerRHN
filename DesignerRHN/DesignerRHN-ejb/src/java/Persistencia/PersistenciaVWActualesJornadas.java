package Persistencia;

import Entidades.VWActualesJornadas;
import InterfacePersistencia.PersistenciaVWActualesJornadasInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesJornadas implements PersistenciaVWActualesJornadasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesJornadas buscarJornada(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesJornadas vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesJornadas actualesJornadas = (VWActualesJornadas) query.getSingleResult();
            return actualesJornadas;
        } catch (Exception e) {
            VWActualesJornadas actualesJornadas = null;
            return actualesJornadas;
        }

    }
}
