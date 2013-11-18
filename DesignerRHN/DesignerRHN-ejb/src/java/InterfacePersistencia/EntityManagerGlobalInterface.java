/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Administrator
 */
public interface EntityManagerGlobalInterface {
    public boolean crearFactoryInicial(String baseDatos);
    public boolean crearFactoryUsuario(String usuario, String contraseña, String baseDatos);
    public EntityManagerFactory getEmf();
    public void setEmf(EntityManagerFactory emf);
    public void adios();
}
