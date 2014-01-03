/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.CentrosCostos;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'CentrosCostos' de la base de datos
 *
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
            System.out.println("PersistenciaCentrosCostos mano de obra " + centrosCostos.getManoobra());
            if (centrosCostos.getManoobra().isEmpty() || centrosCostos.getManoobra().equals("")|| centrosCostos.getManoobra().equals(" ")) {
                centrosCostos.setManoobra(null);
            }
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
     *
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

    public BigDecimal contadorComprobantesContables(BigInteger secuencia) {
        try {

            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, comprobantescontables ccs WHERE cc.secuencia = ccs.centrocostoconsolidador AND cc.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            System.err.println("PersistenciaCENTROSCOSTOS contadorComprobantesContables  " + query.getSingleResult());
            return (BigDecimal) query.getSingleResult();

        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorHvReferencias. " + e);
            return null;
        }
    }

    public BigDecimal contadorDetallesCCConsolidador(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, detallescc dcc WHERE cc.secuencia = dcc.ccconsolidador AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorDetallesCCConsolidador. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorDetallesCCDetalle(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, detallescc dcc WHERE cc.secuencia = dcc.ccdetalle AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorDetallesCCDetalle. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorEmpresas(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, empresas e WHERE cc.secuencia = e.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorEmpresas. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorEstructuras(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, estructuras e WHERE cc.secuencia = e.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorEstructuras. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorInterconCondor(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_condor ic WHERE cc.secuencia = ic.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconCondor. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorInterconDynamics(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_dynamics id WHERE cc.secuencia = id.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconDynamics. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorInterconGeneral(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_general ig WHERE cc.secuencia = ig.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconGeneral. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorInterconHelisa(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_helisa ih WHERE cc.secuencia = ih.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconHelisa. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorInterconSapbo(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_sapbo isp WHERE cc.secuencia = isp.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconSapbo. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorInterconSiigo(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_siigo isi WHERE cc.secuencia = isi.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconSiigo. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorInterconTotal(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_total it WHERE cc.secuencia = it.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconTotal. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorNovedadesC(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, novedades n WHERE cc.secuencia = n.centrocostoc AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorNovedadesC. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorNovedadesD(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, novedades n WHERE cc.secuencia = n.centrocostod AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorNovedadesD. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorProcesosProductivos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, procesosproductivos pp WHERE cc.secuencia = pp.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorProcesosProductivos. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorProyecciones(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, proyecciones p WHERE cc.secuencia = p.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorProyecciones. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorSolucionesNodosC(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, solucionesnodos sn WHERE cc.secuencia = sn.centrocostoc AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorSolucionesNodosC. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorSolucionesNodosD(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, solucionesnodos sn WHERE cc.secuencia = sn.centrocostod AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorSolucionesNodosD. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorSoPanoramas(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, sopanoramas sp WHERE cc.secuencia = sp.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorSoPanoramas. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorTerceros(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, terceros t WHERE cc.secuencia = t.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorTerceros. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorUnidadesRegistradas(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, unidadesregistradas ur WHERE cc.secuencia = ur.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorUnidadesRegistradas. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorVigenciasCuentasC(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, vigenciascuentas vc WHERE cc.secuencia = vc.consolidadorc AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorVigenciasCuentasC. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorVigenciasCuentasD(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, vigenciascuentas vc WHERE cc.secuencia = vc.consolidadord AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorVigenciasCuentasD. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorVigenciasProrrateos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, vigenciasprorrateos vp WHERE cc.secuencia = vp.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorVigenciasProrrateos. " + e);
            return retorno;
        }
    }

}
