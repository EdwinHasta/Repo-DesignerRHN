package Persistencia;

import Entidades.VigenciasEventos;
import InterfacePersistencia.PersistenciaVigenciasEventosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasEventos implements PersistenciaVigenciasEventosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<VigenciasEventos> eventosPersona(BigInteger secuenciaEmpl) {
        try {
            Query query = em.createQuery("SELECT COUNT(ve) FROM VigenciasEventos ve WHERE ve.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secuenciaEmpl);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT ve FROM VigenciasEventos ve WHERE ve.empleado.secuencia = :secuenciaEmpl and ve.fechainicial = (SELECT MAX(vev.fechainicial) FROM VigenciasEventos vev WHERE vev.empleado.secuencia = :secuenciaEmpl)");
                queryFinal.setParameter("secuenciaEmpl", secuenciaEmpl);
                List<VigenciasEventos> listaVigenciasEventos = queryFinal.getResultList();
                return listaVigenciasEventos;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasEventos.eventosPersona" + e);
            return null;
        }
    }
}
