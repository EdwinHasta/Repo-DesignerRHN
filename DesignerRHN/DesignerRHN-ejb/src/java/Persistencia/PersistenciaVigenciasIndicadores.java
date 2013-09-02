package Persistencia;

import Entidades.VigenciasIndicadores;
import InterfacePersistencia.PersistenciaVigenciasIndicadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasIndicadores implements PersistenciaVigenciasIndicadoresInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<VigenciasIndicadores> indicadoresPersona(BigInteger secuenciaEmpl) {
        try {
            Query query = em.createQuery("SELECT COUNT(vi) FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secuenciaEmpl);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vi FROM VigenciasIndicadores vi WHERE vi.empleado.secuencia = :secuenciaEmpl and vi.fechainicial = (SELECT MAX(vin.fechainicial) FROM VigenciasIndicadores vin WHERE vin.empleado.secuencia = :secuenciaEmpl)");
                queryFinal.setParameter("secuenciaEmpl", secuenciaEmpl);
                List<VigenciasIndicadores> listaVigenciasIndicadores = queryFinal.getResultList();
                return listaVigenciasIndicadores;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasIndicadores.indicadoresPersona" + e);
            return null;
        }
    }
}
