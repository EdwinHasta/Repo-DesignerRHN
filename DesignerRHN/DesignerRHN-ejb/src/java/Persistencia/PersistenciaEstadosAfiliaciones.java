/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.EstadosAfiliaciones;
import InterfacePersistencia.PersistenciaEstadosAfiliacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'EstadosAfiliaciones'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEstadosAfiliaciones implements PersistenciaEstadosAfiliacionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
   
    @Override
    public void crear(EstadosAfiliaciones afiliaciones) {
        try {
            em.persist(afiliaciones);
        } catch (Exception e) {
            System.out.println("Error creando bancos persistencia bancos");
        }
    }
    
    @Override
    public void editar(EstadosAfiliaciones afiliaciones) {
        try {
            em.merge(afiliaciones);
        } catch (Exception e) {
            System.out.println("Error editando bancos persistencia bancos");
        }
    }
    
    @Override
    public void borrar(EstadosAfiliaciones afiliaciones) {
        try {
            em.remove(em.merge(afiliaciones));
        } catch (Exception e) {
            System.out.println("Error borrando bancos persistencia bancos");
        }
    }
    
    @Override
    public EstadosAfiliaciones buscarEstadoAfiliacion(BigInteger secuencia) {
        try {
            return em.find(EstadosAfiliaciones.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarbanco persistencia bancos : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<EstadosAfiliaciones> buscarEstadosAfiliaciones() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadosAfiliaciones.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarBancos persistencia bancos");
            return null;
        }
    }
}
