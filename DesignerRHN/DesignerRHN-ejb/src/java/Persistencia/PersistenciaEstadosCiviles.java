/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.EstadosCiviles;
import InterfacePersistencia.PersistenciaEstadosCivilesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'EstadosCiviles'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEstadosCiviles implements PersistenciaEstadosCivilesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
  
    @Override
    public void crear(EstadosCiviles estadosCiviles) {
        try{
        em.persist(estadosCiviles);
        } catch(Exception e){
            System.out.println("Error creando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }
  
    @Override
    public void editar(EstadosCiviles estadosCiviles) {
        try {
        em.merge(estadosCiviles);
        } catch(Exception e){
            System.out.println("Error editando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }
 
    @Override
    public void borrar(EstadosCiviles estadosCiviles) {
        try{
        em.remove(em.merge(estadosCiviles));
        } catch(Exception e){
            System.out.println("Error borrando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }

    @Override
    public EstadosCiviles buscarEstadoCivil(BigInteger secuencia) {
        try {          
            return em.find(EstadosCiviles.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarEstadoCivil PersistenciaEstadosCiviles : "+e.toString());
            return null;
        }
    }

    @Override
    public List<EstadosCiviles> buscarEstadosCiviles() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(EstadosCiviles.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarEstadosCiviles PersistenciaEstadosCiviles");
            return null;
        }
    }

}
