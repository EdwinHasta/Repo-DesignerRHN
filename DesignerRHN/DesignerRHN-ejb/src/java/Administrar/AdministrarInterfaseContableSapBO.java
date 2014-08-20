/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.ActualUsuario;
import Entidades.Empresas;
import Entidades.InterconSapBO;
import Entidades.ParametrosContables;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Entidades.Terceros;
import InterfaceAdministrar.AdministrarInterfaseContableSapBOInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaInterconSapBOInterface;
import InterfacePersistencia.PersistenciaParametrosContablesInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
@Stateful
public class AdministrarInterfaseContableSapBO implements AdministrarInterfaseContableSapBOInterface{

    @EJB
    AdministrarSesionesInterface administrarSesiones;
    @EJB
    PersistenciaParametrosContablesInterface persistenciaParametrosContables;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaInterconSapBOInterface persistenciaInterconSap;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public List<ParametrosContables> obtenerParametrosContablesUsuarioBD(String usuarioBD) {
        try {
            List<ParametrosContables> parametro = persistenciaParametrosContables.buscarParametrosContablesUsuarioBD(em, usuarioBD);
            if (parametro != null) {
                for (int i = 0; i < parametro.size(); i++) {
                    Empresas empresa = persistenciaEmpresas.consultarEmpresaPorCodigo(em, parametro.get(i).getEmpresaCodigo());
                    if (empresa != null) {
                        parametro.get(i).setEmpresaRegistro(empresa);
                    }
                    if (parametro.get(i).getProceso() == null) {
                        parametro.get(i).setProceso(new Procesos());
                    }
                }
            }
            return parametro;
        } catch (Exception e) {
            System.out.println("Error obtenerParametrosContablesUsuarioBD Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public void modificarParametroContable(ParametrosContables parametro) {
        try {
            if (parametro.getProceso().getSecuencia() == null) {
                parametro.setProceso(null);
            }
            persistenciaParametrosContables.editar(em, parametro);
        } catch (Exception e) {
            System.out.println("Error modificarParametroContable Admi : " + e.toString());

        }
    }

    @Override
    public void borrarParametroContable(List<ParametrosContables> listPC) {
        try {
            for (int i = 0; i < listPC.size(); i++) {
                if (listPC.get(i).getProceso().getSecuencia() == null) {
                    listPC.get(i).setProceso(null);
                }
                persistenciaParametrosContables.borrar(em, listPC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarParametroContable Admi : " + e.toString());
        }
    }

    @Override
    public void crearParametroContable(ParametrosContables parametro) {
        try {
            if (parametro.getProceso().getSecuencia() == null) {
                parametro.setProceso(null);
            }
            persistenciaParametrosContables.crear(em, parametro);
        } catch (Exception e) {
            System.out.println("Error modificarParametroContable Admi : " + e.toString());

        }
    }
    
    @Override
    public List<SolucionesNodos> obtenerSolucionesNodosParametroContable(Date fechaInicial, Date fechaFinal) {
        try {
            List<SolucionesNodos> lista = persistenciaSolucionesNodos.buscarSolucionesNodosParaParametroContable_SAP(em, fechaInicial, fechaFinal);
            return lista;
        } catch (Exception e) {
            System.out.println("Error obtenerSolucionesNodosParametroContable Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<InterconSapBO> obtenerInterconSapBOParametroContable(Date fechaInicial, Date fechaFinal) {
        try {
            List<InterconSapBO> lista = persistenciaInterconSap.buscarInterconSAPBOParametroContable(em, fechaInicial, fechaFinal);
            if (lista != null) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getCodigotercero() != null && (!lista.get(i).getCodigotercero().isEmpty())) {
                        Long codigo = new Long(lista.get(i).getCodigotercero());
                        Terceros tercero = persistenciaTerceros.buscarTerceroPorCodigo(em, codigo);
                        if (tercero != null) {
                            lista.get(i).setTerceroRegistro(tercero);
                        } else {
                            lista.get(i).setTerceroRegistro(new Terceros());
                        }
                    } else {
                        lista.get(i).setTerceroRegistro(new Terceros());
                    }
                }
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Error obtenerInterconSapBOParametroContable Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Procesos> lovProcesos() {
        try {
            List<Procesos> lista = persistenciaProcesos.buscarProcesos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovProcesos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empresas> lovEmpresas() {
        try {
            List<Empresas> lista = persistenciaEmpresas.buscarEmpresas(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpresas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public ActualUsuario obtenerActualUsuario() {
        try {
            ActualUsuario user = persistenciaActualUsuario.actualUsuarioBD(em);
            return user;
        } catch (Exception e) {
            System.out.println("Error obtenerActualUsuario Admi : " + e.toString());
            return null;
        }
    }


}
