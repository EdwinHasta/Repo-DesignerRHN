package Persistencia;

import Entidades.Empleados;
import Entidades.VigenciasCargos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaVigenciasCargosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class PersistenciaVigenciasCargos implements PersistenciaVigenciasCargosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear vigenciasCargos.
     */
    @Override
    public void crear(VigenciasCargos vigenciasCargos) {
        try {
            em.merge(vigenciasCargos);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    /*
     *Editar vigenciasCargos. 
     */
    @Override
    public void editar(VigenciasCargos vigenciasCargos) {
        try {
            em.merge(vigenciasCargos);
        } catch (Exception e) {
            System.out.println("ALERTA! Error xD");
        }
    }

    /*
     *Borrar vigenciasCargos.
     */
    @Override
    public void borrar(VigenciasCargos vigenciasCargos) {
        try {
            em.remove(em.merge(vigenciasCargos));
        } catch (Exception e) {
            System.out.println("Error Persistencia Borrar VC: " + e);
        }

    }

    /*
     *Encontrar una vigenciasCargos. 
     */
    @Override
    public VigenciasCargos buscarVigenciaCargo(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            //return em.find(VigenciasCargos.class, id);
            return em.find(VigenciasCargos.class, in);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todas las vigenciasCargos.
     */
    @Override
    public List<VigenciasCargos> buscarVigenciasCargos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasCargos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public VigenciasCargos buscarCargoEmpleado(BigDecimal secuencia) {

        Query query = em.createQuery("SELECT vc FROM VigenciasCargos vc WHERE vc.empleado.secuencia=:secuencia");
        query.setParameter("secuencia", secuencia);
        VigenciasCargos vigenciasCargos = (VigenciasCargos) query.getSingleResult();
        return vigenciasCargos;
        //return null;
    }

    @Override
    public List<VigenciasCargos> buscarVigenciaCargoEmpleado(BigInteger secEmpleado) {
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
