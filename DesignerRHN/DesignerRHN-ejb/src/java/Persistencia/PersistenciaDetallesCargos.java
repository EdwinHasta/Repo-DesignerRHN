/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.DetallesCargos;
import InterfacePersistencia.PersistenciaDetallesCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'DetallesCargos' de la
 * base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaDetallesCargos implements PersistenciaDetallesCargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(DetallesCargos detallesCargos) {
        try {
            em.persist(detallesCargos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaDetallesCargos : " + e.toString());
        }
    }

    @Override
    public void editar(DetallesCargos detallesCargos) {
        try {
            System.out.println("detallesCargos : "+detallesCargos.getDescripcion()+" ::::: "+detallesCargos.getSecuencia());
            em.merge(detallesCargos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaDetallesCargos : " + e.toString());
        }
    }

    @Override
    public void borrar(DetallesCargos detallesCargos) {
        try {
            em.remove(em.merge(detallesCargos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaDetallesCargos : " + e.toString());
        }
    }

    @Override
    public List<DetallesCargos> buscarDetallesCargos() {
        try {
            Query query = em.createQuery("SELECT cc FROM DetallesCargos cc ORDER BY cc.peso ASC");
            List<DetallesCargos> detallesCargos = query.getResultList();
            return detallesCargos;
        } catch (Exception e) {
            System.out.println("Error buscarDetallesCargos PersistenciaDetallesCargos : " + e.toString());
            return null;
        }
    }

    @Override
    public DetallesCargos buscarDetallesCargosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT dc FROM DetallesCargos dc WHERE dc.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            DetallesCargos detallesCargos = (DetallesCargos) query.getSingleResult();
            return detallesCargos;
        } catch (Exception e) {
            System.out.println("Error buscarDetallesCargosSecuencia PersistenciaDetallesCargos : " + e.toString());
            DetallesCargos detallesCargos = null;
            return detallesCargos;
        }
    }

    @Override 
    public DetallesCargos buscarDetalleCargoParaSecuenciaTipoDetalle(BigInteger secTipoDetalle, BigInteger secCargo) { 
        try {
            Query query = em.createQuery("SELECT dc FROM DetallesCargos dc WHERE dc.tipodetalle.secuencia=:secTipoDetalle AND dc.cargo.secuencia=:secCargo");
            query.setParameter("secTipoDetalle", secTipoDetalle);
            query.setParameter("secCargo", secCargo);
            DetallesCargos detallesCargos = (DetallesCargos) query.getSingleResult();
            return detallesCargos;
        } catch (Exception e) {
            System.out.println("Error buscarDetalleCargoParaSecuenciaTipoDetalle PersistenciaDetallesCargos : " + e.toString());
            return null;
        }
    }
     
    @Override 
    public List<DetallesCargos> buscarDetallesCargosDeCargoSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT dc FROM DetallesCargos dc WHERE dc.cargo.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            List<DetallesCargos> detallesCargos =  query.getResultList();
            return detallesCargos;
        } catch (Exception e) {
            System.out.println("Error buscarDetallesCargosDeCargoSecuencia PersistenciaDetallesCargos : " + e.toString());
            List<DetallesCargos> detallesCargos = null;
            return detallesCargos;
        }
    }

}
