/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empleados;
import Entidades.VigenciasCargos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaVigenciasCargosInterface;
import java.math.BigInteger;
import javax.persistence.criteria.CriteriaQuery;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasCargos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasCargos implements PersistenciaVigenciasCargosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasCargos vigenciasCargos) {
        try {
            em.merge(vigenciasCargos);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    @Override
    public void editar(VigenciasCargos vigenciasCargos) {
        try {
            em.merge(vigenciasCargos);
        } catch (Exception e) {
            System.out.println("ALERTA! Error xD");
        }
    }

    @Override
    public void borrar(VigenciasCargos vigenciasCargos) {
        try {
            em.remove(em.merge(vigenciasCargos));
        } catch (Exception e) {
            System.out.println("Error Persistencia Borrar VC: " + e);
        }
    }

    @Override
    public VigenciasCargos buscarVigenciaCargo(BigInteger secuencia) {
        try {
            return em.find(VigenciasCargos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VigenciasCargos> buscarVigenciasCargos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasCargos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasCargos> buscarVigenciasCargosEmpleado(BigInteger secEmpleado) {
        try {
            Empleados empleado = (Empleados) em.createNamedQuery("Empleados.findBySecuencia").setParameter("secuencia", secEmpleado).getSingleResult();
            List<VigenciasCargos> vigenciasCargos = (List<VigenciasCargos>) em.createNamedQuery("VigenciasCargos.findByEmpleado").setParameter("empleado", empleado).getResultList();
            return vigenciasCargos;
        } catch (Exception e) {
            List<VigenciasCargos> vigenciasCargos = null;
            return vigenciasCargos;
        }
    }
}
