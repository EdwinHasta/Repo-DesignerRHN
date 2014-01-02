/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasJornadas;
import InterfacePersistencia.PersistenciaVigenciasJornadasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasJornadas'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasJornadas implements PersistenciaVigenciasJornadasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasJornadas vigenciasJornadas) {
        try {
            em.persist(vigenciasJornadas);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser creada (PersistenciaVigenciasJornadas)");
        }
    }

    @Override
    public void editar(VigenciasJornadas vigenciasJornadas) {
        try {
            em.merge(vigenciasJornadas);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la PersistenciaVigenciasJornadas");
        }
    }

    @Override
    public void borrar(VigenciasJornadas vigenciasJornadas) {
        try {
            em.remove(em.merge(vigenciasJornadas));
        } catch (Exception e) {
            System.out.println("Error borrar (PersistenciaVigenciasJornadas)");
        }
    }

    @Override
    public List<VigenciasJornadas> buscarVigenciasJornadas() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasJornadas.class));
        return em.createQuery(cq).getResultList();
        }catch(Exception e){
            System.out.println("Error buscarVigenciasJornadas");
            return null;
        }
    }

    @Override
    public List<VigenciasJornadas> buscarVigenciasJornadasEmpleado(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vj FROM VigenciasJornadas vj WHERE vj.empleado.secuencia = :secuenciaEmpl ORDER BY vj.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            List<VigenciasJornadas> vigenciasJornadas = query.getResultList();
            return vigenciasJornadas;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasJornadasEmpleado " + e);
            return null;
        }
    }

    @Override
    public VigenciasJornadas buscarVigenciasJornadasSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("VigenciasJornadas.findBySecuencia").setParameter("secuencia", secuencia);
            VigenciasJornadas vigenciasJornadas = (VigenciasJornadas) query.getSingleResult();
            return vigenciasJornadas;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasJornadasSecuencia Persistencia VL");
            return null;
        }
    }
}
