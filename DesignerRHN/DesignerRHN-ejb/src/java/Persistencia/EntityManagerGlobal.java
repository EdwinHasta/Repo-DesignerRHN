/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.EntityManagerGlobalInterface;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Remove;
import javax.ejb.Singleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase de tipo singleton. 
 * Esta clase tiene la responsabilidad de crear y controlar las conexiones a la 
 * base de datos que se están usando. Al ser ‘singleton’ solo permite crear
 * una instancia a la base de datos.
 * @author Felipe Triviño
 */
@Singleton
public class EntityManagerGlobal implements EntityManagerGlobalInterface, Serializable{
    
    /**
     * Atributo EntityManagerFactory.
     */
    private EntityManagerFactory emf;
    
    
    @Override
    public boolean crearFactoryInicial(String baseDatos) {
        try {
            System.out.println("Entro y la bd es: " + baseDatos);
            emf = Persistence.createEntityManagerFactory(baseDatos);
            return true;
        } catch (Exception e) {
            System.out.println("Se estallo... PUM!");
            System.out.println(e);
            return false;
        }
    }
    
    @Override
    public boolean crearFactoryUsuario(String usuario, String contraseña, String baseDatos) {
        try {
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.user", usuario);
            properties.put("javax.persistence.jdbc.password", contraseña);
            emf = Persistence.createEntityManagerFactory(baseDatos, properties);
            return true; 
        } catch (Exception e) {
            System.out.println("Error crearFactoryUsuario: " + e);
            return false;
        }
    }
   
    @Override
    public EntityManagerFactory getEmf() {
        return emf;
    }
    
    @Override
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Remove
    @Override
    public void adios(){
        System.out.println("Cerrando xD");
    }
}
