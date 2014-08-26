/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Usuarios;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'usuario' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaUsuariosInterface {
    /**
     * Método encargado de buscar el Usuario con el alias dado por parámetro.
     * @param alias Alias del Usuario que se quiere encontrar.
     * @return Retorna el Usuario identificado con el alias dado por parámetro.
     */
    public Usuarios buscarUsuario(EntityManager em, String alias);
    public void crear(EntityManager em, Usuarios usuarios);
    public void editar(EntityManager em, Usuarios usuarios);
    public void borrar(EntityManager em, Usuarios usuarios);
    public List<Usuarios> buscarUsuarios(EntityManager em);
    public void crearUsuario(EntityManager em, String alias);
    public void crearUsuarioPerfil(EntityManager em, String alias, String perfil);
    public void borrarUsuario(EntityManager em, String alias);
    public void borrarUsuarioTotal(EntityManager em, String alias);
    public void clonarUsuario(EntityManager em, String alias, String aliasclonado, BigInteger secuencia);
    public void desbloquearUsuario(EntityManager em, String alias);
    public void restaurarUsuario(EntityManager em, String alias, String fecha);
}
