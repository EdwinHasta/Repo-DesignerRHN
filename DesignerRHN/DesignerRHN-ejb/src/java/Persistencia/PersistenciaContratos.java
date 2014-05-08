/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Contratos;
import InterfacePersistencia.PersistenciaContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Contratos'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaContratos implements PersistenciaContratosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,Contratos contratos) {
        em.persist(contratos);
    }

    @Override
    public void editar(EntityManager em,Contratos contratos) {
        try {
            em.merge(contratos);
        } catch (Exception e) {
            System.out.println("Error PersistenciaContratos.editar: " + e);
        }
    }

    @Override
    public void borrar(EntityManager em,Contratos contratos) {
        em.remove(em.merge(contratos));
    }

    @Override
    public List<Contratos> buscarContratos(EntityManager em) {
        Query query = em.createNamedQuery("Contratos.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Contratos> contratoLista = (List<Contratos>) query.getResultList();
        return contratoLista;
    }

    @Override
    public Contratos buscarContratoSecuencia(EntityManager em,BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT e FROM Contratos e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Contratos contrato = (Contratos) query.getSingleResult();
            return contrato;
        } catch (Exception e) {
            Contratos contrato = null;
            return contrato;
        }
    }

    @Override
    public List<Contratos> lovContratos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT c FROM Contratos c ORDER BY c.codigo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Contratos> listaContratos = query.getResultList();
            return listaContratos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void reproducirContrato(EntityManager em,Short codigoOrigen, Short codigoDestino) {        
        try {
            String sqlQuery = "call FORMULASCONTRATOS_PKG.CLONARLEGISLACION(?, ?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, codigoOrigen);
            query.setParameter(2, codigoDestino);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en reproducirContrato: " + e);
        }
    }
}
