package Persistencia;

import Entidades.ParametrosInformes;
import InterfacePersistencia.PersistenciaParametrosInformesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaParametrosInformes implements PersistenciaParametrosInformesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(ParametrosInformes parametrosInformes) {
        try {
            em.persist(parametrosInformes);
        } catch (Exception e) {
            System.out.println("El ParametroInforme no exite o esta reservada por lo cual no puede ser modificada (ParametrosInformes)");
        }
    }

    @Override
    public void editar(ParametrosInformes parametrosInformes) {
        try {
            em.merge(parametrosInformes);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el ParametroInforme : "+e.toString());
        }
    }

    @Override
    public void borrar(ParametrosInformes parametrosInformes) {
        try {
            em.remove(em.merge(parametrosInformes));
        } catch (Exception e) {
            System.out.println("El ParametroInforme no se ha podido eliminar");
        }
    }

    @Override
    public ParametrosInformes buscarParametroInforme(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(ParametrosInformes.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarParametroInforme Persistencia");
            return null;
        }
    }

    @Override
    public List<ParametrosInformes> buscarParametrosInformes() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametrosInformes.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarParametrosInformes");
            return null;
        }
    }

    @Override
    public ParametrosInformes buscarParametroInformeUsuario(String alias) {
        try {
            Query query = em.createQuery("SELECT pi FROM ParametrosInformes pi WHERE pi.usuario =:Alias");
            query.setParameter("Alias", alias);
            ParametrosInformes parametrosInformes = (ParametrosInformes) query.getSingleResult();
            return parametrosInformes;
        } catch (Exception e) {
            System.out.println("Error en buscarParametroInformeUsuario " + e);
            return null;
        }
    }
    
    public List<ParametrosInformes> buscarParametrosInformesUsuario(String alias) {
        try {
            
            Query query = em.createQuery("SELECT pi FROM ParametrosInformes pi WHERE pi.usuario =:Alias");
            query.setParameter("Alias", alias);
            List<ParametrosInformes> listPI = (List<ParametrosInformes> ) query.getResultList();
            return listPI;
        } catch (Exception e) {
            System.out.println("Error en buscarParametrosInformesUsuario " + e);
            return null;
        }
    }
}
