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
 *
 * @author Administrator
 */
@Stateless
public class PersistenciaMotivosCambiosCargos implements PersistenciaMotivosCambiosCargosInterface {

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
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(MotivosCambiosCargos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<String> buscarNombresMotivosCambiosCargos() {
        try {
            List<String> nombresMotivosCambiosCargos = (List<String>) em.createNamedQuery("MotivosCambiosCargos.findNombres").getResultList();
            return nombresMotivosCambiosCargos;
        } catch (Exception e) {
            System.out.println("PersistenciaMotivosCambiosCargos: buscarNombresMotivosCambios trae null");
            return null;
        }
    }

    @Override
    public Long verificarBorradoVigenciasCargos(BigInteger secuencia) {
        Long retorno = new Long(-1);
        try {
            Query query = em.createQuery("SELECT count(vc) FROM VigenciasCargos vc WHERE vc.motivocambiocargo.secuencia =:secTipoCentroCosto ");
            query.setParameter("secTipoCentroCosto", secuencia);
            retorno = (Long) query.getSingleResult();
            System.err.println("PersistenciaTiposCentrosCostos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaMotivosCambiosCargos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}
