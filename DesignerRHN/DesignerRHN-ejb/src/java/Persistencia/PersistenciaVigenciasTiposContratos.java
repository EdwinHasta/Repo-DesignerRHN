/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empleados;
import Entidades.VigenciasTiposContratos;
import InterfacePersistencia.PersistenciaVigenciasTiposContratosInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'VigenciasTiposContratos' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasTiposContratos implements PersistenciaVigenciasTiposContratosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, VigenciasTiposContratos vigenciasTiposContratos) {
        try {
            em.clear();
            em.getTransaction().begin();
            em.merge(vigenciasTiposContratos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasTiposContratos)" + e);
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasTiposContratos vigenciasTiposContratos) {
        try {
            em.clear();
            em.getTransaction().begin();
            em.merge(vigenciasTiposContratos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias Tipo Contrato");
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasTiposContratos vigenciasTiposContratos) {
        try {
            em.clear();
            em.getTransaction().begin();
            em.remove(em.merge(vigenciasTiposContratos));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error Persistencia Borrar VC: " + e);
        }
    }

    @Override
    public VigenciasTiposContratos buscarVigenciaTipoContrato(EntityManager em, BigInteger secuencia
    ) {
        try {
            return em.find(VigenciasTiposContratos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VigenciasTiposContratos> buscarVigenciasTiposContratos(EntityManager em
    ) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasTiposContratos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasTiposContratos> buscarVigenciaTipoContratoEmpleado(EntityManager em, BigInteger secuencia
    ) {
        try {
            Query query = em.createQuery("SELECT vtc FROM VigenciasTiposContratos vtc WHERE vtc.empleado.secuencia = :secuenciaEmpl ORDER BY vtc.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasTiposContratos> vigenciasTiposContratos = (List<VigenciasTiposContratos>) query.getResultList();
            return vigenciasTiposContratos;
        } catch (Exception e) {
            System.out.println("Error llave: " + e);
            return null;
        }
    }

    @Override
    public Date fechaMaxContratacion(EntityManager em, Empleados secuencia
    ) {
        try {
            Date fechaContratacion;
            Query query = em.createQuery("SELECT vwac.fechaVigencia FROM VWActualesTiposContratos vwac WHERE vwac.empleado =:empleado");
            query.setParameter("empleado", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            fechaContratacion = (Date) query.getSingleResult();
            return fechaContratacion;
        } catch (Exception e) {
            System.out.println("Error fechaMaxContratacion PersistenciaTiposContratos : " + e.toString());

            return null;
        }
    }
    
    @Override 
    public Date fechaFinalContratacionVacaciones(EntityManager em, BigInteger secuencia) {
        try{
            Date fecha;
            Query query2 = em.createQuery("SELECT vtc.fechavigencia FROM VigenciasTiposContratos vtc WHERE vtc.empleado.secuencia =:secuencia AND vtc.fechavigencia = (SELECT MIN(vtci.fechavigencia) FROM VigenciasTiposContratos vtci WHERE vtci.empleado.secuencia = vtc.empleado.secuencia AND vtci.fechavigencia <= (SELECT vaf.fechaHastaCausado FROM VWActualesFechas vaf))");
            query2.setParameter("secuencia", secuencia);
            query2.setHint("javax.persistence.cache.storeMode", "REFRESH");
            fecha = (Date) query2.getSingleResult();
            System.out.println("Persistencia fecha : "+fecha);
            return fecha;
        }catch(Exception e){
            System.out.println("Error fechaFinalContratacionVacaciones PersistenciaTiposContratos : "+e.toString());

            return null;
        }
    }
}
