/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaParametrosEstadosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'ParametrosEstados'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaParametrosEstados implements PersistenciaParametrosEstadosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public Integer empleadosParaLiquidar(EntityManager em, String usuarioBD) {
        try {
            String sqlQuery = "SELECT COUNT(*) FROM PARAMETROSESTADOS pe WHERE exists (select p.secuencia from parametros p, parametrosinstancias pi, usuariosinstancias ui , usuarios u where p.secuencia = pe.parametro and u.secuencia = p.usuario and pi.parametro = p.secuencia and ui.instancia = pi.instancia and u.alias = ? and proceso = (SELECT PROCESO FROM PARAMETROSESTRUCTURAS pe, usuarios u where u.secuencia = pe.usuario and u.alias=?))";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, usuarioBD);
            query.setParameter(2, usuarioBD);
            BigDecimal a = (BigDecimal) query.getSingleResult();
            Integer empeladosALiquidar = a.intValueExact();
            return empeladosALiquidar;
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosEstados.empleadosParaLiquidar " + e);
            return null;
        }
    }

    @Override
    public Integer empleadosLiquidados(EntityManager em, String usuarioBD) {
        try {
            String sqlQuery = "SELECT COUNT(*) FROM PARAMETROSESTADOS pe WHERE exists (select p.secuencia from parametros p, parametrosinstancias pi, usuariosinstancias ui , usuarios u where p.secuencia = pe.parametro and u.secuencia = p.usuario and pi.parametro = p.secuencia and ui.instancia = pi.instancia and u.alias = ? and proceso = (SELECT PROCESO FROM PARAMETROSESTRUCTURAS pe, usuarios u where u.secuencia = pe.usuario and u.alias=?)) and pe.estado = 'LIQUIDADO'";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, usuarioBD);
            query.setParameter(2, usuarioBD);
            BigDecimal a = (BigDecimal) query.getSingleResult();
            Integer empeladosALiquidar = a.intValueExact();
            return empeladosALiquidar;
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosEstados.empleadosLiquidados " + e);
            return null;
        }
    }

    @Override
    public void inicializarParametrosEstados(EntityManager em) {
        try {
            String sqlQuery = "UPDATE PARAMETROSESTADOS pe SET ESTADO= 'A LIQUIDAR'\n"
                    + "        where  ESTADO = 'LIQUIDADO'\n"
                    + "        AND exists (select --+ rule\n"
                    + "                     'x' from usuariosinstancias ui , usuarios u, parametros p, parametrosinstancias pi\n"
                    + "                    where p.secuencia = pe.parametro and u.secuencia = p.usuario\n"
                    + "                    and pi.parametro = p.secuencia and ui.instancia = pi.instancia and u.alias = user\n"
                    + "                    and proceso = (SELECT PROCESO FROM PARAMETROSESTRUCTURAS pe, usuarios u where u.secuencia = pe.usuario and u.alias=user))";
            Query query = em.createNativeQuery(sqlQuery);
            int i = query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosEstados.inicializarParametrosEstados " + e);
        }
    }
    
    @Override
    public String parametrosComprobantes(EntityManager em, BigInteger secuenciaParametro) {
        try {
            Query query = em.createQuery("SELECT pe.estado FROM ParametrosEstados pe WHERE pe.parametros.secuencia = :secuenciaParametro");
            query.setParameter("secuenciaParametro", secuenciaParametro);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            String estadoParametro = (String) query.getSingleResult();
            return estadoParametro;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaParametrosEstados.parametrosComprobantes" + e);
            return null;
        }
    }
}
