/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.DetallesEmpresas;
import InterfacePersistencia.PersistenciaDetallesEmpresasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'DetallesEmpresas' de
 * la base de datos.
 *
 * @author betelgeuse
 * @version 1.1 AndresPineda (Crear-Editar-Borrar-BuscarDetallesEmpresas-BuscarDetalleEmpresaPorSecuencia)
 */
@Stateless
public class PersistenciaDetallesEmpresas implements PersistenciaDetallesEmpresasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,DetallesEmpresas detallesEmpresas) {
        try {
            em.persist(detallesEmpresas);
        } catch (Exception e) {
            System.out.println("Error creando detallesEmpresas PersistenciaDetallesEmpresas : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em,DetallesEmpresas detallesEmpresas) {
        try {
            em.merge(detallesEmpresas);
        } catch (Exception e) {
            System.out.println("Error editando detallesEmpresas PersistenciaDetallesEmpresas : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em,DetallesEmpresas detallesEmpresas) {
        try {
            em.remove(em.merge(detallesEmpresas));
        } catch (Exception e) {
            System.out.println("Error borrando detallesEmpresas PersistenciaDetallesEmpresas : " + e.toString());
        }
    }

    @Override
    public DetallesEmpresas buscarDetalleEmpresa(EntityManager em,Short codigoEmpresa) {
        DetallesEmpresas detallesEmpresas = null;
        try {
            Query query = em.createQuery("SELECT de FROM DetallesEmpresas de WHERE de.empresa.codigo = :codigoEmpresa");
            query.setParameter("codigoEmpresa", codigoEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            detallesEmpresas = (DetallesEmpresas) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("PersistenciaDetallesEmpresas.buscarDetallesEmpresas.");
            System.out.println("Error consultando los detallesempresas.");
        } finally {
            return detallesEmpresas;
        }
    }

    @Override
    public DetallesEmpresas buscarDetalleEmpresaPorSecuencia(EntityManager em,BigInteger secEmpresa) {
        try {
            DetallesEmpresas detallesEmpresas = null;
            Query query = em.createQuery("SELECT de FROM DetallesEmpresas de WHERE de.empresa.secuencia =:secEmpresa");
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            detallesEmpresas = (DetallesEmpresas) query.getSingleResult();
            return detallesEmpresas;
        } catch (Exception e) {
            System.out.println("Error buscarDetalleEmpresaPorSecuencia PersistenciaDetallesEmpresas : "+e.toString());
            DetallesEmpresas detallesEmpresas = null;
            return detallesEmpresas;
        }
    }

    @Override
    public List<DetallesEmpresas> buscarDetallesEmpresas(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetallesEmpresas.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarBancos PersistenciaDetallesEmpresas : " + e.toString());
            return null;
        }
    }

}
