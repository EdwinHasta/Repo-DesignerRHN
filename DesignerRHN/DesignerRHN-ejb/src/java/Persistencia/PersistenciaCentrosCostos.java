/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.CentrosCostos;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless Clase encargada de realizar operaciones sobre la tabla 'CentrosCostos'
 * de la base de datos
 * @author Hugo David Sin Gutiérrez
 */
@Stateless
public class PersistenciaCentrosCostos implements PersistenciaCentrosCostosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(CentrosCostos centrosCostos) {
        try {
            em.persist(centrosCostos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaCentrosCostos");
        }
    }

    @Override
    public void editar(CentrosCostos centrosCostos) {
        try {
            em.merge(centrosCostos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaCentrosCostos");
        }
    }

    @Override
    public void borrar(CentrosCostos centrosCostos) {
        try {
            em.remove(em.merge(centrosCostos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaCentrosCostos");
        }
    }  

    @Override
    public List<CentrosCostos> buscarCentrosCostos() {
        try {
            List<CentrosCostos> centrosCostos = (List<CentrosCostos>) em.createNamedQuery("CentrosCostos.findAll").getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error buscarCentrosCostos PersistenciaCentrosCostos");
            return null;
        }
    }

    @Override
    public CentrosCostos buscarCentroCostoSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT cc FROM CentrosCostos cc WHERE cc.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            CentrosCostos centrosCostos = (CentrosCostos) query.getSingleResult();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error buscarCentroCostoSecuencia PersistenciaCentrosCostos");
            CentrosCostos centrosCostos = null;
            return centrosCostos;
        }
    }

    @Override
    public List<CentrosCostos> buscarCentrosCostosEmpr(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT cce FROM CentrosCostos cce WHERE cce.empresa.secuencia = :secuenciaEmpr AND cce.comodin='N' ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaEmpr", secEmpresa);
            List<CentrosCostos> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaCentrosCostos BuscarCentrosCostosEmpr " + e);
            return null;
        }
    }
    
    /**
     * Método realizado por ?????
     * @param secEmpresa
     * @return 
     */
    @Override
    public long contadorSecuenciaEmpresa(BigInteger secEmpresa) {
        long so, vc, vp;
        long total;
        try {
            Query query = em.createQuery("SELECT COUNT(so) FROM SolucionesNodos so WHERE so.secuencia = :secuenciaEmpr");
            query.setParameter("secuenciaEmpr", secEmpresa);
            so = query.getMaxResults();
            Query qury = em.createQuery("SELECT COUNT(vc) FROM VigenciasCuentas vc WHERE vc.secuencia = :secuenciaEmpr");
            qury.setParameter("secuenciaEmpr", secEmpresa);
            vc = qury.getMaxResults();
            Query que = em.createQuery("SELECT COUNT(vp) FROM VigenciasProrrateos vp WHERE vp.secuencia = :secuenciaEmpr");
            que.setParameter("secuenciaEmpr", secEmpresa);
            vp = que.getMaxResults();
            total = so + vc + vp;
            return total;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaCentrosCostos BuscarCentrosCostosEmpr " + e);
            total = -1;
            return total;
        }
    }
}
