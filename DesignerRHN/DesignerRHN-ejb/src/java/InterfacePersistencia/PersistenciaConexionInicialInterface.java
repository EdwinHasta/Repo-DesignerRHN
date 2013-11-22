/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Perfiles;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Administrator
 */
public interface PersistenciaConexionInicialInterface {
    public boolean validarUsuario(EntityManager eManager, String usuario);
    public EntityManager validarConexionUsuario(EntityManagerFactory emf, String usuario, String contraseña, String baseDatos);
    public Perfiles perfilUsuario(EntityManager eManager, BigInteger secPerfil);
    public BigInteger usuarioLogin(EntityManager eManager, String usuarioBD);
    public void setearUsuario(EntityManager eManager, String rol, String pwd);
    public void accesoDefault(EntityManager eManager);
    public int cambiarClave(EntityManager eManager, String usuario, String nuevaClave);
}
