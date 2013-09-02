package Persistencia;

import Entidades.Familiares;
import InterfacePersistencia.PersistenciaFamiliaresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaFamiliares implements PersistenciaFamiliaresInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<Familiares> familiaresPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(f) FROM Familiares f WHERE f.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT f FROM Familiares f WHERE f.persona.secuencia = :secuenciaPersona");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<Familiares> listFamiliares = queryFinal.getResultList();
                return listFamiliares;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaFamiliares.familiaresPersona" + e);
            return null;
        }
    }
}
