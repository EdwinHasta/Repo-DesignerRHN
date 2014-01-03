/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasSueldos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaVigenciasSueldosInterface;
import java.math.BigInteger;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasSueldos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasSueldos implements PersistenciaVigenciasSueldosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(VigenciasSueldos vigenciasSueldos) {
        em.persist(vigenciasSueldos);
    }

    public void editar(VigenciasSueldos vigenciasSueldos) {
        em.merge(vigenciasSueldos);
    }

    public void borrar(VigenciasSueldos vigenciasSueldos) {
        em.remove(em.merge(vigenciasSueldos));
    }

    public List<VigenciasSueldos> buscarVigenciasSeldos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasSueldos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasSueldos> buscarVigenciasSueldosEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vs FROM VigenciasSueldos vs WHERE vs.empleado.secuencia = :secuenciaEmpl ORDER BY vs.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasSueldos> vigenciasSueldos = query.getResultList();
            return vigenciasSueldos;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasSueldosEmpleado " + e);
            return null;
        }
    }

    @Override
    public VigenciasSueldos buscarVigenciasSueldosSecuencia(BigInteger secVS) {
        try {
            Query query = em.createNamedQuery("VigenciasSueldos.findBySecuencia").setParameter("secuencia", secVS);
            VigenciasSueldos vigenciasSueldos = (VigenciasSueldos) query.getSingleResult();
            return vigenciasSueldos;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasSueldosSecuencia " + e);
            return null;
        }
    }

    @Override
    public List<VigenciasSueldos> buscarVigenciasSueldosEmpleadoRecientes(BigInteger secEmpleado) {
        try {
            String consulta = "SELECT * FROM VIGENCIASSUELDOS v "
                    + "WHERE v.fechavigencia = ("
                    + "    SELECT MAX(i.fechavigencia) "
                    + "    FROM VIGENCIASSUELDOS i, tipossueldos ts"
                    + "    WHERE v.empleado = i.empleado"
                    + "    AND ts.secuencia = v.tiposueldo"
                    + "    AND i.fechavigencia <= (SELECT fechahastacausado FROM VWACTUALESFECHAS)"
                    + "    AND i.fechavigencia >=  (SELECT decode (NVL(TS.BASICO,'N'),'S', "
                    + "                                GREATEST(MAX(vrl.fechavigencia),i.fechavigencia), "
                    + "                                LEAST(MAX(vrl.fechavigencia),i.fechavigencia)) "
                    + "                            FROM VIGENCIASREFORMASLABORALES vrl"
                    + "                            WHERE vrl.empleado = v.empleado"
                    + "                            AND vrl.fechavigencia <= (SELECT fechahastacausado FROM VWACTUALESFECHAS)"
                    + "                            ) "
                    + "                        )"
                    + "AND v.empleado = (SELECT e.secuencia FROM empleados e WHERE e.secuencia = ?)"
                    + "ORDER BY fechavigencia DESC";
            List<VigenciasSueldos> vigenciasSueldos = (List<VigenciasSueldos>) em.createNativeQuery(consulta, VigenciasSueldos.class).setParameter(1, secEmpleado).getResultList();
            return vigenciasSueldos;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasSueldosEmpleadoRecientes " + e);
            return null;
        }
    }
}
