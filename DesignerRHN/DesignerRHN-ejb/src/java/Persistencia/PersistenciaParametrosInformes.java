/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
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
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'ParametrosInformes'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaParametrosInformes implements PersistenciaParametrosInformesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
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
    public ParametrosInformes buscarParametroInforme(BigInteger secuencia) {
        try {
            return em.find(ParametrosInformes.class, secuencia);
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
}
