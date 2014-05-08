/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Encargaturas;
import InterfacePersistencia.PersistenciaEncargaturasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Encargaturas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEncargaturas implements PersistenciaEncargaturasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public List<Encargaturas> reemplazoPersona(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT COUNT(e) FROM Encargaturas e WHERE e.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT e FROM Encargaturas e WHERE e.empleado.secuencia = :secuenciaEmpleado and e.fechainicial = (SELECT MAX(en.fechainicial) FROM Encargaturas en WHERE en.empleado.secuencia = :secuenciaEmpleado)");
                queryFinal.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<Encargaturas> listaEncargaturas = queryFinal.getResultList();
                return listaEncargaturas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEncargaturas.reemplazoPersona" + e);
            return null;
        }
    }
    
     @Override
    public void crear(EntityManager em,Encargaturas encargaturas) {
        try {
            em.merge(encargaturas);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaEncargaturas.crear");
        }
    }
       
    @Override
    public void editar(EntityManager em,Encargaturas encargaturas) {
        em.merge(encargaturas);
    }

    @Override
    public void borrar(EntityManager em,Encargaturas encargaturas) {
        em.remove(em.merge(encargaturas));
    }
    
    @Override
    public List<Encargaturas> buscarEncargaturas(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Encargaturas.class));
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public List<Encargaturas> encargaturasEmpleado(EntityManager em,BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT e FROM Encargaturas e WHERE e.empleado.secuencia= :secuenciaEmpleado ORDER BY e.fechainicial");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Encargaturas> listaEncargaturas = query.getResultList();
            return listaEncargaturas;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEncargaturas.encargaturasEmpleado" + e);
            return null;
        }
    }
    
}
