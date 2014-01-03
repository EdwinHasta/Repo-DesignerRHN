/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.DetallesExtrasRecargos;
import InterfacePersistencia.PersistenciaDetallesExtrasRecargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'DetallesExtrasRecargos' 
 * de la base de datos.
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaDetallesExtrasRecargos implements PersistenciaDetallesExtrasRecargosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(DetallesExtrasRecargos detallesExtrasRecargos) {
        em.persist(detallesExtrasRecargos);
    }

    @Override
    public void editar(DetallesExtrasRecargos detallesExtrasRecargos) {
        em.merge(detallesExtrasRecargos);
    }

    @Override
    public void borrar(DetallesExtrasRecargos detallesExtrasRecargos) {
        em.remove(em.merge(detallesExtrasRecargos));
    }

    @Override
    public DetallesExtrasRecargos buscarDetalleExtraRecargo(BigInteger secuencia) {
        try {
            return em.find(DetallesExtrasRecargos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaDetallesExtrasRecargos buscarDetalleExtraRecargo : " + e.toString());
            return null;
        }
    }

    @Override
    public List<DetallesExtrasRecargos> buscaDetallesExtrasRecargos() {
        try {
            Query query = em.createQuery("SELECT der FROM DetallesExtrasRecargos der ORDER BY der.codigo DESC");
            List<DetallesExtrasRecargos> extrasRecargos = query.getResultList();
            return extrasRecargos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDetallesExtrasRecargos buscaDetallesExtrasRecargos : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<DetallesExtrasRecargos> buscaDetallesExtrasRecargosPorSecuenciaExtraRecargo(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT der FROM DetallesExtrasRecargos der WHERE der.extrarecargo.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            List<DetallesExtrasRecargos> extrasRecargos = query.getResultList();
            return extrasRecargos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDetallesExtrasRecargos buscaDetallesExtrasRecargosPorSecuenciaExtraRecargo : " + e.toString());
            return null;
        }
    }

}
