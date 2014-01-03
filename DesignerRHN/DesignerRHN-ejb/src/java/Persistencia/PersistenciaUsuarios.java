/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Usuarios;
import InterfacePersistencia.PersistenciaUsuariosInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Usuarios'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaUsuarios implements PersistenciaUsuariosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public Usuarios buscarUsuario(String alias) {
        try {
            Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.alias= :alias");
            query.setParameter("alias",alias);
            Usuarios usuarios = (Usuarios) query.getSingleResult();
            return usuarios;
        } catch (Exception e) {
            Usuarios usuarios = null;
            return usuarios;
        }
    }
}
