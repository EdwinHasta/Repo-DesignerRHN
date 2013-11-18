package Persistencia;

import InterfacePersistencia.PersistenciaParametrosEstadosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaParametrosEstados implements PersistenciaParametrosEstadosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public Integer empleadosParaLiquidar() {
        try {
            String sqlQuery = "SELECT COUNT(*) FROM PARAMETROSESTADOS pe WHERE exists (select p.secuencia from parametros p, parametrosinstancias pi, usuariosinstancias ui , usuarios u where p.secuencia = pe.parametro and u.secuencia = p.usuario and pi.parametro = p.secuencia and ui.instancia = pi.instancia and u.alias = 'PRODUCCION' and proceso = (SELECT PROCESO FROM PARAMETROSESTRUCTURAS pe, usuarios u where u.secuencia = pe.usuario and u.alias='PRODUCCION'))";
            Query query = em.createNativeQuery(sqlQuery);
            //Query query = em.createQuery("SELECT COUNT(pe) FROM ParametrosEstados pe WHERE EXISTS (SELECT p FROM Parametros p, ParametrosInstancias pi, UsuariosInstancias ui , Usuarios u WHERE p.secuencia = pe.parametros.secuencia AND u.secuencia = p.usuario.secuencia AND pi.parametro.secuencia = p.secuencia AND ui.instancia.secuencia = pi.instancia.secuencia AND ui.usuario.secuencia = u.secuencia AND u.alias = (SELECT vwu.alias from ActualUsuario vwu) and p.proceso = (SELECT pes.proceso FROM ParametrosEstructuras pes WHERE pes.usuario.secuencia = u.secuencia))");
            //Integer empeladosALiquidar = (Integer) query.getSingleResult();
            BigDecimal a = (BigDecimal) query.getSingleResult();
            Integer empeladosALiquidar = a.intValueExact();
            return empeladosALiquidar;
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosEstados.empleadosParaLiquidar " + e);
            return null;
        }
    }

    public Integer empleadosLiquidados() {
        try {
            String sqlQuery = "SELECT COUNT(*) FROM PARAMETROSESTADOS pe WHERE exists (select p.secuencia from parametros p, parametrosinstancias pi, usuariosinstancias ui , usuarios u where p.secuencia = pe.parametro and u.secuencia = p.usuario and pi.parametro = p.secuencia and ui.instancia = pi.instancia and u.alias = 'PRODUCCION' and proceso = (SELECT PROCESO FROM PARAMETROSESTRUCTURAS pe, usuarios u where u.secuencia = pe.usuario and u.alias='PRODUCCION')) and pe.estado = 'LIQUIDADO'";
            Query query = em.createNativeQuery(sqlQuery);
            //Query query = em.createQuery("SELECT COUNT(pe) FROM ParametrosEstados pe WHERE EXISTS (SELECT p FROM Parametros p, ParametrosInstancias pi, UsuariosInstancias ui , Usuarios u WHERE p.secuencia = pe.parametros.secuencia AND u.secuencia = p.usuario.secuencia AND pi.parametro.secuencia = p.secuencia AND ui.instancia.secuencia = pi.instancia.secuencia AND ui.usuario.secuencia = u.secuencia AND u.alias = (SELECT vwu.alias from ActualUsuario vwu) and p.proceso = (SELECT pes.proceso FROM ParametrosEstructuras pes WHERE pes.usuario.secuencia = u.secuencia))");
            //Integer empeladosALiquidar = (Integer) query.getSingleResult();
            BigDecimal a = (BigDecimal) query.getSingleResult();
            Integer empeladosALiquidar = a.intValueExact();
            return empeladosALiquidar;
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosEstados.empleadosLiquidados " + e);
            return null;
        }
    }

    public void inicializarParametrosEstados() {
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
    
    public String parametrosComprobantes(BigInteger secuenciaParametro) {
        try {
            Query query = em.createQuery("SELECT pe.estado FROM ParametrosEstados pe WHERE pe.parametros.secuencia = :secuenciaParametro");
            query.setParameter("secuenciaParametro", secuenciaParametro);
            String estadoParametro = (String) query.getSingleResult();
            return estadoParametro;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaParametrosEstados.parametrosComprobantes" + e);
            return null;
        }
    }
}
