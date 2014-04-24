/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.MotivosCambiosCargos;
import InterfacePersistencia.PersistenciaMotivosCambiosCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosCambiosCargos'
 * de la base de datos. (Para verificar que esta asociado a una VigenciaCargo,
 * se realiza la operacion sobre la tabla VigenciasCargos)
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMotivosCambiosCargos implements PersistenciaMotivosCambiosCargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(MotivosCambiosCargos motivoCambioCargo) {
        try {
            em.persist(motivoCambioCargo);
        } catch (Exception e) {
            System.out.println("No es posible crear El MotivosCambiosCargos");
        }
    }

    @Override
    public void editar(MotivosCambiosCargos motivoCambioCargo) {
        try {
            em.merge(motivoCambioCargo);
        } catch (Exception e) {
            System.out.println("El MotivosCambiosCargos no exite o esta reservado por lo cual no puede ser modificado");
        }
    }

    @Override
    public void borrar(MotivosCambiosCargos motivoCambioCargo) {
        //revisar        
        em.remove(em.merge(motivoCambioCargo));
    }

    @Override
    public MotivosCambiosCargos buscarMotivoCambioCargo(BigInteger secuencia) {
        try {
            return em.find(MotivosCambiosCargos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MotivosCambiosCargos> buscarMotivosCambiosCargos() {
        Query query = em.createQuery("SELECT m FROM MotivosCambiosCargos m");
        List<MotivosCambiosCargos> lista = query.getResultList();
        return lista;
    }

    @Override
    public BigInteger verificarBorradoVigenciasCargos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            Query query = em.createQuery("SELECT count(vc) FROM VigenciasCargos vc WHERE vc.motivocambiocargo.secuencia =:secMotivoCambioCargo");
            query.setParameter("secMotivoCambioCargo", secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("PersistenciaMotivosCambiosCargos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaMotivosCambiosCargos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}
