package Persistencia;

import Entidades.IdiomasPersonas;
import InterfacePersistencia.PersistenciaIdiomasPersonasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaIdiomasPersonas implements PersistenciaIdiomasPersonasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<IdiomasPersonas> idiomasPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(ip) FROM IdiomasPersonas ip WHERE ip.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT ip FROM IdiomasPersonas ip WHERE ip.persona.secuencia = :secuenciaPersona");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<IdiomasPersonas> listaIdiomasPersonas = queryFinal.getResultList();
                return listaIdiomasPersonas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaIdiomasPersonas.idiomasPersona" + e);
            return null;
        }
    }
}
