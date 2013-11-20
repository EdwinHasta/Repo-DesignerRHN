package Persistencia;

import Entidades.Cargos;
import Entidades.ParametrosEstructuras;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class PersistenciaParametrosEstructuras implements PersistenciaParametrosEstructurasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void editar(ParametrosEstructuras parametroEstructura) {
        em.merge(parametroEstructura);
    }

    public ParametrosEstructuras buscarParametros() {

        try {
            Query query = em.createQuery("SELECT p FROM ParametrosEstructuras p WHERE p.usuario.alias='PRODUCCION'");
            ParametrosEstructuras parametrosEstructuras = (ParametrosEstructuras) query.getSingleResult();
            return parametrosEstructuras;

        } catch (Exception e) {
            ParametrosEstructuras parametrosEstructuras = null;
            return parametrosEstructuras;
        }
    }

    public BigInteger buscarEmpresaParametros(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT p.estructura.organigrama.empresa.secuencia FROM ParametrosEstructuras p WHERE p.usuario.alias = :usuarioBD");
            query.setParameter("usuarioBD", usuarioBD);
            BigInteger secEmpresa = (BigInteger) query.getSingleResult();
            return secEmpresa;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ParametrosEstructuras estructurasComprobantes(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT COUNT(pe) FROM ParametrosEstructuras pe WHERE pe.usuario.alias = :usuarioBD");
            query.setParameter("usuarioBD", usuarioBD);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                query = em.createQuery("SELECT pe FROM ParametrosEstructuras pe WHERE pe.usuario.alias = :usuarioBD");
                query.setParameter("usuarioBD", usuarioBD);
                ParametrosEstructuras parametroEstructura = (ParametrosEstructuras) query.getSingleResult();
                return parametroEstructura;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosEstructuras.estructurasComprobantes" + e);
            return null;
        }
    }

    public void adicionarEmpleados(BigInteger secParametroEstructura) {
        try {
            String sqlQuery = "call PARAMETROS_PKG.InsertarParametrosProceso(?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secParametroEstructura);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("PersistenciaParametrosEstructuras.adicionarEmpleados: " + e);
        }
    }

    public Integer empleadosParametrizados(BigInteger secProceso) {
        try {
            String sqlQuery = "SELECT COUNT(*)\n"
                    + "FROM PARAMETROSESTRUCTURAS P \n"
                    + "WHERE P.PROCESO = ?\n"
                    + "AND P.USUARIO <> (SELECT SECUENCIA FROM USUARIOS WHERE ALIAS=USER)\n"
                    + "AND EXISTS (SELECT * FROM USUARIOSESTRUCTURAS UE, EMPRESAS E WHERE UE.USUARIO=P.USUARIO\n"
                    + "AND UE.EMPRESA = E.SECUENCIA)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secProceso);
            BigDecimal a = (BigDecimal) query.getSingleResult();
            Integer empeladosALiquidar = a.intValueExact();
            return empeladosALiquidar;
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosEstructuras.empleadosParametrizados " + e);
            return null;
        }
    }
}
