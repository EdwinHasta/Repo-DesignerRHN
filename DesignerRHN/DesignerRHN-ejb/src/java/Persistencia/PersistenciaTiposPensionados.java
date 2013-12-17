/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposPensionados;
import InterfacePersistencia.PersistenciaTiposPensionadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposPensionados'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTiposPensionados implements PersistenciaTiposPensionadosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposPensionados tiposPensionados) {
        try {
            em.persist(tiposPensionados);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposPensionados");
        }
    }

    @Override
    public void editar(TiposPensionados tiposPensionados) {
        try {
            em.merge(tiposPensionados);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposPensionados");
        }
    }

    @Override
    public void borrar(TiposPensionados tiposPensionados) {
        try {
            em.remove(em.merge(tiposPensionados));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposPensionados");
        }
    }

    @Override
    public List<TiposPensionados> buscarTiposPensionados() {
        try {
            List<TiposPensionados> tiposPensionadosLista = (List<TiposPensionados>) em.createNamedQuery("TiposPensionados.findAll").getResultList();
            return tiposPensionadosLista;
        } catch (Exception e) {
            System.out.println("Error buscarTiposPensionados PersistenciaTiposPensionados");
            return null;
        }
    }
    
    @Override
    public TiposPensionados buscarTipoPensionSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT tp FROM TiposPensionados tp WHERE tp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposPensionados tipoP = (TiposPensionados) query.getSingleResult();
            return tipoP;
        } catch (Exception e) {
            System.out.println("Error buscarTipoPensionSecuencia PersistenciaTiposPensionados");
            TiposPensionados tipoP = null;
            return tipoP;
        }
    }
}
