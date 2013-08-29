package Persistencia;

import Entidades.VigenciasFormales;
import InterfacePersistencia.PersistenciaVigenciasFormalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasFormales implements PersistenciaVigenciasFormalesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<VigenciasFormales> educacionPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(vf) FROM VigenciasFormales vf WHERE vf.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vf FROM VigenciasFormales vf WHERE vf.persona.secuencia = :secuenciaPersona and vf.fechavigencia = (SELECT MAX(vfo.fechavigencia) FROM VigenciasFormales vfo WHERE vfo.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<VigenciasFormales> listaVigenciasFormales = queryFinal.getResultList();
                return listaVigenciasFormales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasFormales.educacionPersona" + e);
            return null;
        }
    }
}
