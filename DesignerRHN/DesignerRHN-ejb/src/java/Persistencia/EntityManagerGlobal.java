package Persistencia;

import InterfacePersistencia.EntityManagerGlobalInterface;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class EntityManagerGlobal implements EntityManagerGlobalInterface, Serializable{

    private EntityManagerFactory emf;
    private EntityManager em;

    public boolean crearFactoryInicial(String baseDatos) {
        try {
            emf = Persistence.createEntityManagerFactory(baseDatos);
            return true;
        } catch (Exception e) {
            //System.out.println(e);
            return false;
        }
    }

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

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Remove
    public void adios(){
        System.out.println("Cerrando xD");
    }
}
