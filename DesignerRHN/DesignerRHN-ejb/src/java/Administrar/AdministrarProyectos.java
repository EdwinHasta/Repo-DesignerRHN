/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empresas;
import Entidades.Monedas;
import Entidades.Proyectos;
import Entidades.PryClientes;
import Entidades.PryPlataformas;
import InterfaceAdministrar.AdministrarProyectosInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaMonedasInterface;
import InterfacePersistencia.PersistenciaProyectosInterface;
import InterfacePersistencia.PersistenciaPryClientesInterface;
import InterfacePersistencia.PersistenciaPryPlataformasInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Viktor
 */
@Stateful
public class AdministrarProyectos implements AdministrarProyectosInterface {

    @EJB
    PersistenciaProyectosInterface persistenciaProyectos;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaPryClientesInterface persistenciaPryCliente;
    @EJB
    PersistenciaMonedasInterface persistenciaMonedas;
    @EJB
    PersistenciaPryPlataformasInterface persistenciaPryPlataformas;

    @Override
    public List<Proyectos> Proyectos() {
        List<Proyectos> listaProyectos;
        listaProyectos = persistenciaProyectos.proyectos();
        return listaProyectos;
    }

    @Override
    public List<Proyectos> lovProyectos() {
        return persistenciaProyectos.proyectos();
    }

    @Override
    public void crearProyectos(List<Proyectos> crearList) {
        try {
            for (int i = 0; i < crearList.size(); i++) {
                if (crearList.get(i).getEmpresa().getSecuencia() == null) {
                    crearList.get(i).setEmpresa(null);
                }
                if (crearList.get(i).getPryCliente().getSecuencia() == null) {
                    crearList.get(i).setPryCliente(null);
                }
                if (crearList.get(i).getPryPlataforma().getSecuencia() == null) {
                    crearList.get(i).setPryPlataforma(null);
                }
                if (crearList.get(i).getTipomoneda().getSecuencia() == null) {
                    crearList.get(i).setTipomoneda(null);
                }
                persistenciaProyectos.crear(crearList.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearProyectos Admi : " + e.toString());
        }
    }

    @Override
    public void editarProyectos(List<Proyectos> editarList) {
        try {
            for (int i = 0; i < editarList.size(); i++) {
                if (editarList.get(i).getEmpresa().getSecuencia() == null) {
                    editarList.get(i).setEmpresa(null);
                }
                if (editarList.get(i).getPryCliente().getSecuencia() == null) {
                    editarList.get(i).setPryCliente(null);
                }
                if (editarList.get(i).getPryPlataforma().getSecuencia() == null) {
                    editarList.get(i).setPryPlataforma(null);
                }
                if (editarList.get(i).getTipomoneda().getSecuencia() == null) {
                    editarList.get(i).setTipomoneda(null);
                }
                persistenciaProyectos.editar(editarList.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarProyectos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarProyectos(List<Proyectos> borrarList) {
        try {
            for (int i = 0; i < borrarList.size(); i++) {
                if (borrarList.get(i).getEmpresa().getSecuencia() == null) {
                    borrarList.get(i).setEmpresa(null);
                }
                if (borrarList.get(i).getPryCliente().getSecuencia() == null) {
                    borrarList.get(i).setPryCliente(null);
                }
                if (borrarList.get(i).getPryPlataforma().getSecuencia() == null) {
                    borrarList.get(i).setPryPlataforma(null);
                }
                if (borrarList.get(i).getTipomoneda().getSecuencia() == null) {
                    borrarList.get(i).setTipomoneda(null);
                }
                persistenciaProyectos.borrar(borrarList.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarProyectos Admi : " + e.toString());
        }
    }

    @Override
    public List<PryClientes> listPryClientes() {
        try {
            List<PryClientes> listPC = persistenciaPryCliente.buscarPryClientes();
            return listPC;
        } catch (Exception e) {
            System.out.println("Error listPryClientes Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PryPlataformas> listPryPlataformas() {
        try {
            List<PryPlataformas> listPP = persistenciaPryPlataformas.buscarPryPlataformas();
            return listPP;
        } catch (Exception e) {
            System.out.println("Error listPryPlataformas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empresas> listEmpresas() {
        try {
            List<Empresas> listE = persistenciaEmpresas.buscarEmpresas();
            return listE;
        } catch (Exception e) {
            System.out.println("Error listEmpresas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Monedas> listMonedas() {
        try {
            List<Monedas> listM = persistenciaMonedas.consultarMonedas();
            return listM;
        } catch (Exception e) {
            System.out.println("Error listMonedas Admi : " + e.toString());
            return null;
        }
    }
}
