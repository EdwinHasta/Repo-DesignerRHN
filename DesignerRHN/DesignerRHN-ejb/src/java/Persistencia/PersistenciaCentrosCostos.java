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
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'CentrosCostos' de la
 * base de datos
 *
 * @author Hugo David Sin Gutiérrez
 */
@Stateless
public class PersistenciaCentrosCostos implements PersistenciaCentrosCostosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/
    @Override
    public void crear(EntityManager em,CentrosCostos centrosCostos) {
        try {
            if (centrosCostos.getManoobra() == null) {
                System.out.println("PERSISTENCIA CENTROSCOSTOS MANO DE OBRA ES NULA ");
            } else if (centrosCostos.getManoobra().isEmpty() || centrosCostos.getManoobra().equals("") || centrosCostos.getManoobra().equals(" ")) {
                centrosCostos.setManoobra(null);
            }
            em.persist(centrosCostos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaCentrosCostos E " + e);
        }
    }

    @Override
    public void editar(EntityManager em,CentrosCostos centrosCostos) {
        try {
            em.merge(centrosCostos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaCentrosCostos");
        }
    }

    @Override
    public void borrar(EntityManager em,CentrosCostos centrosCostos) {
        try {
            em.remove(em.merge(centrosCostos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaCentrosCostos");
        }
    }

    @Override
    public List<CentrosCostos> buscarCentrosCostos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT cc FROM CentrosCostos cc");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<CentrosCostos> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error buscarCentrosCostos PersistenciaCentrosCostos");
            return null;
        }
    }

    @Override
    public CentrosCostos buscarCentroCostoSecuencia(EntityManager em,BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT cc FROM CentrosCostos cc WHERE cc.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            CentrosCostos centrosCostos = (CentrosCostos) query.getSingleResult();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error buscarCentroCostoSecuencia PersistenciaCentrosCostos");
            CentrosCostos centrosCostos = null;
            return centrosCostos;
        }
    }

    @Override
    public List<CentrosCostos> buscarCentrosCostosEmpr(EntityManager em,BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT cce FROM CentrosCostos cce WHERE cce.empresa.secuencia = :secuenciaEmpr AND cce.comodin='N' ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaEmpr", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<CentrosCostos> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaCentrosCostos BuscarCentrosCostosEmpr " + e);
            return null;
        }
    }

    @Override
    public BigInteger contadorComprobantesContables(EntityManager em,BigInteger secuencia) {
        try {

            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, comprobantescontables ccs WHERE cc.secuencia = ccs.centrocostoconsolidador AND cc.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            System.err.println("PersistenciaCENTROSCOSTOS contadorComprobantesContables  " + query.getSingleResult());
            return new BigInteger(query.getSingleResult().toString());

        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorHvReferencias. " + e);
            return null;
        }
    }

    @Override
    public BigInteger contadorDetallesCCConsolidador(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, detallescc dcc WHERE cc.secuencia = dcc.ccconsolidador AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorDetallesCCConsolidador. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorDetallesCCDetalle(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, detallescc dcc WHERE cc.secuencia = dcc.ccdetalle AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorDetallesCCDetalle. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorEmpresas(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, empresas e WHERE cc.secuencia = e.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorEmpresas. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorEstructuras(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, estructuras e WHERE cc.secuencia = e.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorEstructuras. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorInterconCondor(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_condor ic WHERE cc.secuencia = ic.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconCondor. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorInterconDynamics(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_dynamics id WHERE cc.secuencia = id.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconDynamics. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorInterconGeneral(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_general ig WHERE cc.secuencia = ig.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconGeneral. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorInterconHelisa(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_helisa ih WHERE cc.secuencia = ih.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconHelisa. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorInterconSapbo(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_sapbo isp WHERE cc.secuencia = isp.centrocosto AND cc.secuencia=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconSapbo. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorInterconSiigo(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_siigo isi WHERE cc.secuencia = isi.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconSiigo. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorInterconTotal(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, intercon_total it WHERE cc.secuencia = it.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorInterconTotal. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorNovedadesC(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, novedades n WHERE cc.secuencia = n.centrocostoc AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorNovedadesC. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorNovedadesD(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, novedades n WHERE cc.secuencia = n.centrocostod AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorNovedadesD. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorProcesosProductivos(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, procesosproductivos pp WHERE cc.secuencia = pp.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorProcesosProductivos. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorProyecciones(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, proyecciones p WHERE cc.secuencia = p.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorProyecciones. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorSolucionesNodosC(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, solucionesnodos sn WHERE cc.secuencia = sn.centrocostoc AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorSolucionesNodosC. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorSolucionesNodosD(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, solucionesnodos sn WHERE cc.secuencia = sn.centrocostod AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorSolucionesNodosD. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorSoPanoramas(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, sopanoramas sp WHERE cc.secuencia = sp.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorSoPanoramas. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorTerceros(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, terceros t WHERE cc.secuencia = t.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorTerceros. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorUnidadesRegistradas(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, unidadesregistradas ur WHERE cc.secuencia = ur.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorUnidadesRegistradas. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorVigenciasCuentasC(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, vigenciascuentas vc WHERE cc.secuencia = vc.consolidadorc AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorVigenciasCuentasC. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorVigenciasCuentasD(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, vigenciascuentas vc WHERE cc.secuencia = vc.consolidadord AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorVigenciasCuentasD. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorVigenciasProrrateos(EntityManager em,BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM centroscostos cc, vigenciasprorrateos vp WHERE cc.secuencia = vp.centrocosto AND cc.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACENTROSCOSTOS  contadorVigenciasProrrateos. " + e);
            return retorno;
        }
    }

    @Override
    public List<CentrosCostos> buscarCentroCostoPorSecuenciaEmpresa(EntityManager em,BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT c FROM CentrosCostos c WHERE EXISTS (SELECT e FROM Empresas e WHERE e.secuencia=c.empresa.secuencia AND e.secuencia=:secEmpresa) AND c.obsoleto!='S' ORDER BY c.codigo ASC");
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<CentrosCostos> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error buscarCentroCostoSecuencia PersistenciaCentrosCostos : " + e.toString());
            List<CentrosCostos> centrosCostos = null;
            return centrosCostos;
        }
    }
}
