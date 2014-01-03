/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasTiposTrabajadores;
import InterfacePersistencia.PersistenciaVigenciasTiposTrabajadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasTiposTrabajadores'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasTiposTrabajadores implements PersistenciaVigenciasTiposTrabajadoresInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasTiposTrabajadores vigenciasTiposTrabajadores) {
        try {
            em.persist(vigenciasTiposTrabajadores);
            System.out.println("Creo la vigencia");
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser creada (VigenciasTiposTrabajadores)");
        }
    }

    @Override
    public void editar(VigenciasTiposTrabajadores vigenciasTiposTrabajadores) {
        try {
            em.merge(vigenciasTiposTrabajadores);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias TiposTrabajadores");
        }
    }

    @Override
    public void borrar(VigenciasTiposTrabajadores vigenciasTiposTrabajadores) {
        try {
            em.remove(em.merge(vigenciasTiposTrabajadores));
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasTiposTrabajadores (PersistenciaVigenciasTiposTrabajadores)");
        }
    }

    @Override
    public List<VigenciasTiposTrabajadores> buscarVigenciasTiposTrabajadores() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasTiposTrabajadores.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasTiposTrabajadores> buscarVigenciasTiposTrabajadoresEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vtt FROM VigenciasTiposTrabajadores vtt WHERE vtt.empleado.secuencia = :secuenciaEmpl ORDER BY vtt.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<VigenciasTiposTrabajadores> vigenciasTiposTrabajadores = query.getResultList();
            return vigenciasTiposTrabajadores;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias TiposTrabajadores " + e);
            return null;
        }
    }

    @Override
    public VigenciasTiposTrabajadores buscarVigenciasTiposTrabajadoresSecuencia(BigInteger secVTT) {
        try {
            Query query = em.createNamedQuery("VigenciasTiposTrabajadores.findBySecuencia").setParameter("secuencia", secVTT);
            VigenciasTiposTrabajadores vigenciasTiposTrabajadores = (VigenciasTiposTrabajadores) query.getSingleResult();
            return vigenciasTiposTrabajadores;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<VigenciasTiposTrabajadores> buscarEmpleados() {

        Query query = em.createQuery("SELECT vtt FROM VigenciasTiposTrabajadores vtt "
                + "WHERE vtt.fechavigencia = (SELECT MAX(vttt.fechavigencia) "
                + "FROM VigenciasTiposTrabajadores vttt)");
        return query.getResultList();
    }
}
