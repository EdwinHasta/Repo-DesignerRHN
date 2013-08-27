package Persistencia;

import Entidades.Usuarios;
import InterfacePersistencia.PersistenciaUsuariosInterface;
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
public class PersistenciaUsuarios implements PersistenciaUsuariosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public Usuarios buscarUsuario() {

        try {
            Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.alias='PRODUCCION'");
            Usuarios usuarios = (Usuarios) query.getSingleResult();
            return usuarios;

        } catch (Exception e) {
            Usuarios usuarios = null;
            return usuarios;
        }
    }
}
