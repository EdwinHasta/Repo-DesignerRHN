/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposEntidades;
import InterfacePersistencia.PersistenciaTiposEntidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposEntidades'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTiposEntidades implements PersistenciaTiposEntidadesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposEntidades tiposEntidades) {
        try {
            em.persist(tiposEntidades);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposEntidades");
        }
    }

    @Override
    public void editar(TiposEntidades tiposEntidades) {
        try {
            em.merge(tiposEntidades);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposEntidades");
        }
    }

    @Override
    public void borrar(TiposEntidades tiposEntidades) {
        try {
            em.remove(em.merge(tiposEntidades));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposEntidades");
        }
    }   

    @Override
    public List<TiposEntidades> buscarTiposEntidades() {
        try {
            List<TiposEntidades> tiposEntidades = (List<TiposEntidades>) em.createNamedQuery("TiposEntidades.findAll").getResultList();
            return tiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTiposEntidades");
            return null;
        }
    }

    @Override
    public TiposEntidades buscarTiposEntidadesSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT te FROM TiposEntidades te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposEntidades tiposEntidades = (TiposEntidades) query.getSingleResult();
            return tiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTiposEntidadesSecuencia");
            TiposEntidades tiposEntidades = null;
            return tiposEntidades;
        }
    }

    @Override
    public Long verificarBorrado(BigInteger secTipoEntidad) {
        try {
            Long conteo;
            Query query = em.createQuery("SELECT COUNT(va) FROM VigenciasAfiliaciones va WHERE va.tipoentidad.secuencia = :secuencia");
            query.setParameter("secuencia", secTipoEntidad);
            conteo = (Long) query.getSingleResult();
            return conteo;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposEntidades.verificarBorrado.");
            System.err.println("Exception: " + e);
            return null;
        }
    }

    @Override
    public Long verificarBorradoFCE(BigInteger secTipoEntidad) {
        try {
            Long conteo;
            Query query = em.createQuery("SELECT COUNT(fce) FROM FormulasContratosEntidades fce WHERE fce.tipoentidad.secuencia = :secuencia");
            query.setParameter("secuencia", secTipoEntidad);
            conteo = (Long) query.getSingleResult();
            return conteo;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposEntidades.verificarBorradoFCE.");
            System.err.println("Exception: " + e);
            return null;
        }
    }

    @Override
    public List<TiposEntidades> buscarTiposEntidadesIBCS() {
        try {
            Query query = em.createQuery("SELECT te FROM TiposEntidades te WHERE EXISTS (SELECT gte FROM Grupostiposentidades gte WHERE gte.secuencia = te.grupo.secuencia AND gte.codigo IN(1,8))");
            List<TiposEntidades> tiposEntidades = (List<TiposEntidades>) query.getResultList();
            return tiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTiposEntidadesIBCS PersistenciaTiposEntidades : "+e.toString());
            List<TiposEntidades> tiposEntidades = null;
            return tiposEntidades;
        }
    }
}
