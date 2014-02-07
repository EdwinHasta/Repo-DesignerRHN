/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.Recordatorios;
import InterfaceAdministrar.AdministrarRecordatoriosInterface;
import InterfacePersistencia.PersistenciaRecordatoriosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarRecordatorios implements AdministrarRecordatoriosInterface {

    @EJB
    PersistenciaRecordatoriosInterface persistenciaRecordatorios;
    
    
    public List<Recordatorios> recordatorios(){
        List<Recordatorios> listaRecordatorios;
        listaRecordatorios = persistenciaRecordatorios.proverbiosRecordatorios();
        return listaRecordatorios;
    }
    
    
    public List<Recordatorios> mensajesRecordatorios(){
        List<Recordatorios> listaMensajesRecordatorios;
        listaMensajesRecordatorios = persistenciaRecordatorios.mensajesRecordatorios();
        return listaMensajesRecordatorios;
    }
    
    
    public void borrar(Recordatorios proverbios) {
        persistenciaRecordatorios.borrar(proverbios);
    }

    
    public void crear(Recordatorios proverbios) {
        persistenciaRecordatorios.crear(proverbios);
    }

    
    public void modificar(List<Recordatorios> listaProverbiosModificar) {
        for (int i = 0; i < listaProverbiosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaProverbiosModificar.get(i).getMensaje() == null) {
                listaProverbiosModificar.get(i).setMensaje(null);
            }
            persistenciaRecordatorios.editar(listaProverbiosModificar.get(i));
        }
    }
    
    @Override
    public void borrarMU(Recordatorios mensajeUsuario) {
        persistenciaRecordatorios.borrar(mensajeUsuario);
    }

    @Override
    public void crearMU(Recordatorios mensajeUsuario) {
        persistenciaRecordatorios.crear(mensajeUsuario);
    }

    @Override
    public void modificarMU(List<Recordatorios> listaMensajesUsuariosModificar) {
        for (int i = 0; i < listaMensajesUsuariosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaMensajesUsuariosModificar.get(i).getMensaje() == null) {
                listaMensajesUsuariosModificar.get(i).setMensaje(null);
            }
            if (listaMensajesUsuariosModificar.get(i).getAno()== null) {
                listaMensajesUsuariosModificar.get(i).setAno(null);
            }
            if (listaMensajesUsuariosModificar.get(i).getMes() == null) {
                listaMensajesUsuariosModificar.get(i).setMes(null);
            }
            if (listaMensajesUsuariosModificar.get(i).getDia() == null) {
                listaMensajesUsuariosModificar.get(i).setDia(null);
            }
            persistenciaRecordatorios.editar(listaMensajesUsuariosModificar.get(i));
        }
    }
}
