/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Aficiones;
import Entidades.Empleados;
import Entidades.VigenciasAficiones;
import InterfaceAdministrar.AdministrarVigenciaAficionInterface;
import InterfacePersistencia.PersistenciaAficionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaVigenciasAficionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarVigenciaAficion implements AdministrarVigenciaAficionInterface{

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaVigenciasAficionesInterface persistenciaVigenciasAficiones;
    @EJB
    PersistenciaAficionesInterface persistenciaAficiones;

    
    @Override
    public List<VigenciasAficiones> listVigenciasAficionesPersona(BigInteger secuenciaP) {
        try {
            List<VigenciasAficiones> retorno = persistenciaVigenciasAficiones.aficionesTotalesSecuenciaPersona(secuenciaP);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listVigenciasAficionesPersona Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasAficiones(List<VigenciasAficiones> listVA) {
        try {
            for (int i = 0; i < listVA.size(); i++) {
                if (listVA.get(i).getAficion().getSecuencia() == null) {
                    listVA.get(i).setAficion(null);
                }
                persistenciaVigenciasAficiones.crear(listVA.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasAficiones Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciasAficiones(List<VigenciasAficiones> listVA) {
        try {
            for (int i = 0; i < listVA.size(); i++) {
                if (listVA.get(i).getAficion().getSecuencia() == null) {
                    listVA.get(i).setAficion(null);
                }
                persistenciaVigenciasAficiones.editar(listVA.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarVigenciasAficiones Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciasAficiones(List<VigenciasAficiones> listVA) {
        try {
            for (int i = 0; i < listVA.size(); i++) {
                if (listVA.get(i).getAficion().getSecuencia() == null) {
                    listVA.get(i).setAficion(null);
                }
                persistenciaVigenciasAficiones.borrar(listVA.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasAficiones Admi : " + e.toString());
        }
    }

    @Override
    public List<Aficiones> listAficiones() {
        try {
            List<Aficiones> retorno = persistenciaAficiones.buscarAficiones();
            return retorno;
        } catch (Exception e) {
            System.out.println("Error listAficiones Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados empleadoActual(BigInteger secuencia) {
        try {
            Empleados retorno = persistenciaEmpleado.buscarEmpleado(secuencia);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error empleadoActual Admi : " + e.toString());
            return null;
        }
    }
}
