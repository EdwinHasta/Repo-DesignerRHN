/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Conexiones;
import java.math.BigInteger;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrator
 */
public interface PersistenciaConexionesInterface {

    public void crear_Modificar(Conexiones conexion, EntityManager em);
    public BigInteger buscarSID(EntityManager em);
    public void verificarSID(EntityManager em, Conexiones conexion);
}
