package Persistencia;

import Entidades.ParametrosEstructuras;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
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
}
