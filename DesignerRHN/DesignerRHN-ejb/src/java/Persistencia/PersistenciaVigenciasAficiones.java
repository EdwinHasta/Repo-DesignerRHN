package Persistencia;

import Entidades.VigenciasAficiones;
import InterfacePersistencia.PersistenciaVigenciasAficionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasAficiones implements PersistenciaVigenciasAficionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<VigenciasAficiones> aficionesPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(va) FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT va FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona and va.fechainicial = (SELECT MAX(vaf.fechainicial) FROM VigenciasAficiones vaf WHERE vaf.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<VigenciasAficiones> listVigenciasAficiones = queryFinal.getResultList();
                return listVigenciasAficiones;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasAficiones.aficionesPersona" + e);
            return null;
        }
    }
}
