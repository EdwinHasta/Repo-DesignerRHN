/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.Motivosmvrs;
import Entidades.Mvrs;
import Entidades.OtrosCertificados;
import Entidades.TiposCertificados;
import InterfaceAdministrar.AdministrarEmplMvrsInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaMotivosMvrsInterface;
import InterfacePersistencia.PersistenciaMvrsInterface;
import InterfacePersistencia.PersistenciaOtrosCertificadosInterface;
import InterfacePersistencia.PersistenciaTiposCertificadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEmplMvrs implements AdministrarEmplMvrsInterface {

    @EJB
    PersistenciaOtrosCertificadosInterface persistenciaOtrosCertificados;
    @EJB
    PersistenciaMvrsInterface persistenciaMvrs;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaMotivosMvrsInterface persistenciaMotivos;
    @EJB
    PersistenciaTiposCertificadosInterface persistenciaTiposCertificados;

    @Override
    public List<OtrosCertificados> listOtrosCertificadosEmpleado(BigInteger secuencia) {
        try {
            List<OtrosCertificados> listOtrosCertificados;
            listOtrosCertificados = persistenciaOtrosCertificados.buscarOtrosCertificadosEmpleado(secuencia);
            return listOtrosCertificados;
        } catch (Exception e) {
            System.out.println("Error listOtrosCertificadosEmpleado Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Mvrs> listMvrsEmpleado(BigInteger secuenciaEmpl) {
        try {
            List<Mvrs> listMvrs;
            listMvrs = persistenciaMvrs.buscarMvrsEmpleado(secuenciaEmpl);
            return listMvrs;
        } catch (Exception e) {
            System.out.println("Error listMvrsEmpleado Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearOtrosCertificados(List<OtrosCertificados> crearOtrosCertificados) {
        try {
            for (int i = 0; i < crearOtrosCertificados.size(); i++) {
                if (crearOtrosCertificados.get(i).getTipocertificado().getSecuencia() == null) {
                    crearOtrosCertificados.get(i).setTipocertificado(null);
                }
                persistenciaOtrosCertificados.crear(crearOtrosCertificados.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en crearOtrosCertificados Admi : " + e.toString());
        }
    }

    @Override
    public void modificarOtrosCertificados(List<OtrosCertificados> modificarOtrosCertificados) {
        try {
            for (int i = 0; i < modificarOtrosCertificados.size(); i++) {
                if (modificarOtrosCertificados.get(i).getTipocertificado().getSecuencia() == null) {
                    modificarOtrosCertificados.get(i).setTipocertificado(null);
                }
                persistenciaOtrosCertificados.editar(modificarOtrosCertificados.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en modificarOtrosCertificados Admi : " + e.toString());
        }
    }

    @Override
    public void borrarOtrosCertificados(List<OtrosCertificados> borrarOtrosCertificados) {
        try {
            for (int i = 0; i < borrarOtrosCertificados.size(); i++) {
                if (borrarOtrosCertificados.get(i).getTipocertificado().getSecuencia() == null) {
                    borrarOtrosCertificados.get(i).setTipocertificado(null);
                }
                persistenciaOtrosCertificados.borrar(borrarOtrosCertificados.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en borrarOtrosCertificados Admi : " + e.toString());
        }
    }

    @Override
    public void crearMvrs(List<Mvrs> crearMvrs) {
        try {
            for (int i = 0; i < crearMvrs.size(); i++) {
                if (crearMvrs.get(i).getMotivo().getSecuencia() == null) {
                    crearMvrs.get(i).setMotivo(null);
                }
                persistenciaMvrs.crear(crearMvrs.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en crearMvrs Admi : " + e.toString());
        }
    }

    @Override
    public void modificarMvrs(List<Mvrs> modificarMvrs) {
        try {
            for (int i = 0; i < modificarMvrs.size(); i++) {
                if (modificarMvrs.get(i).getMotivo().getSecuencia() == null) {
                    modificarMvrs.get(i).setMotivo(null);
                }
                persistenciaMvrs.editar(modificarMvrs.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en modificarMvrs Admi : " + e.toString());
        }
    }

    @Override
    public void borrarMvrs(List<Mvrs> borrarMvrs) {
        try {
            for (int i = 0; i < borrarMvrs.size(); i++) {
                if (borrarMvrs.get(i).getMotivo().getSecuencia() == null) {
                    borrarMvrs.get(i).setMotivo(null);
                }
                persistenciaMvrs.borrar(borrarMvrs.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error en borrarMvrs Admi : " + e.toString());
        }
    }

    @Override
    public List<Motivosmvrs> listMotivos() {
        try {
            List<Motivosmvrs> listMotivos;
            listMotivos = persistenciaMotivos.buscarMotivosMvrs();
            return listMotivos;
        } catch (Exception e) {
            System.out.println("Error listMotivos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposCertificados> listTiposCertificados() {
        try {
            List<TiposCertificados> listTiposCertificados;
            listTiposCertificados = persistenciaTiposCertificados.buscarTiposCertificados();
            return listTiposCertificados;
        } catch (Exception e) {
            System.out.println("Error listTiposCertificados Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados empleadoActual(BigInteger secuencia) {
        try {
            Empleados empl = persistenciaEmpleados.buscarEmpleado(secuencia);
            return empl;
        } catch (Exception e) {
            System.out.println("Error empleadoActual Admi : " + e.toString());
            return null;
        }
    }
}
