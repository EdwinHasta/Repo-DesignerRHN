/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.Idiomas;
import Entidades.IdiomasPersonas;
import InterfaceAdministrar.AdministrarIdiomaPersonaInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaIdiomasInterface;
import InterfacePersistencia.PersistenciaIdiomasPersonasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarIdiomaPersona implements AdministrarIdiomaPersonaInterface {

    @EJB
    PersistenciaIdiomasPersonasInterface persistenciaIdiomasPersonas;
    @EJB
    PersistenciaIdiomasInterface persistenciaIdiomas;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    @Override
    public void crearIdiomasPersonas(List<IdiomasPersonas> listaID) {
        try {
            for (int i = 0; i < listaID.size(); i++) {
                if (listaID.get(i).getIdioma().getSecuencia() == null) {
                    listaID.get(i).setIdioma(null);
                }
                persistenciaIdiomasPersonas.crear(listaID.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearIdiomarPersonas Admi : " + e.toString());
        }
    }

    @Override
    public void borrarIdiomasPersonas(List<IdiomasPersonas> listaID) {
        try {
            for (int i = 0; i < listaID.size(); i++) {
                if (listaID.get(i).getIdioma().getSecuencia() == null) {
                    listaID.get(i).setIdioma(null);
                }
                persistenciaIdiomasPersonas.borrar(listaID.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearIdiomarPersonas Admi : " + e.toString());
        }
    }

    @Override
    public void editarIdiomasPersonas(List<IdiomasPersonas> listaID) {
        try {
            for (int i = 0; i < listaID.size(); i++) {
                if (listaID.get(i).getIdioma().getSecuencia() == null) {
                    listaID.get(i).setIdioma(null);
                }
                persistenciaIdiomasPersonas.editar(listaID.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearIdiomarPersonas Admi : " + e.toString());
        }
    }

    @Override
    public List<IdiomasPersonas> listIdiomasPersonas(BigInteger secuencia) { 
        try {
            List<IdiomasPersonas> lista = persistenciaIdiomasPersonas.idiomasPersona(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listIdiomasPersonas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Idiomas> listIdiomas() {
        try {
            List<Idiomas> lista = persistenciaIdiomas.buscarIdiomas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lisIdiomas Admi : " + e.toString());
            return null;
        }
    }
    
    public Empleados empleadoActual(BigInteger secuencia){
        try{
            Empleados empl = persistenciaEmpleado.buscarEmpleado(secuencia);
            return empl;
        }catch(Exception e){
            System.out.println("Error empleadoActual Admi : "+e.toString());
            return null;
        }
    }
}
