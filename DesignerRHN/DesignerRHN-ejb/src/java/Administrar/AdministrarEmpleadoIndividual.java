package Administrar;

import Entidades.Direcciones;
import Entidades.Encargaturas;
import Entidades.HVHojasDeVida;
import Entidades.InformacionesAdicionales;
import Entidades.Telefonos;
import Entidades.VigenciasEstadosCiviles;
import InterfacePersistencia.AdministrarEmpleadoIndividualInterface;
import InterfacePersistencia.PersistenciaDireccionesInterface;
import InterfacePersistencia.PersistenciaEncargaturasInterface;
import InterfacePersistencia.PersistenciaHVHojasDeVidaInterface;
import InterfacePersistencia.PersistenciaInformacionesAdicionalesInterface;
import InterfacePersistencia.PersistenciaTelefonosInterface;
import InterfacePersistencia.PersistenciaVigenciasEstadosCivilesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarEmpleadoIndividual implements AdministrarEmpleadoIndividualInterface {

    @EJB
    PersistenciaHVHojasDeVidaInterface persistenciaHVHojasDeVida;
    @EJB
    PersistenciaTelefonosInterface persistenciaTelefonos;
    @EJB
    PersistenciaDireccionesInterface persistenciaDirecciones;
    @EJB
    PersistenciaVigenciasEstadosCivilesInterface persistenciaVigenciasEstadosCiviles;
    @EJB
    PersistenciaInformacionesAdicionalesInterface persistenciaInformacionesAdicionales;
    @EJB
    PersistenciaEncargaturasInterface persistenciaEncargaturas;

    @Override
    public HVHojasDeVida hvHojaDeVidaPersona(BigInteger secPersona) {
        return persistenciaHVHojasDeVida.hvHojaDeVidaPersona(secPersona);
    }

    public Telefonos primerTelefonoPersona(BigInteger secPersona) {
        List<Telefonos> listaTelefonos;
        listaTelefonos = persistenciaTelefonos.telefonoPersona(secPersona);
        if (listaTelefonos != null) {
            return listaTelefonos.get(0);
        } else {
            return null;
        }
    }

    public Direcciones primeraDireccionPersona(BigInteger secPersona) {
        List<Direcciones> listaDirecciones;
        listaDirecciones = persistenciaDirecciones.direccionPersona(secPersona);
        if (listaDirecciones != null) {
            return listaDirecciones.get(0);
        } else {
            return null;
        }
    }

    public VigenciasEstadosCiviles estadoCivilPersona(BigInteger secPersona) {
        List<VigenciasEstadosCiviles> listaVigenciasEstadosCiviles;
        listaVigenciasEstadosCiviles = persistenciaVigenciasEstadosCiviles.vigenciaEstadoCivilPersona(secPersona);
        if (listaVigenciasEstadosCiviles != null) {
            return listaVigenciasEstadosCiviles.get(0);
        } else {
            return null;
        }
    }

    public InformacionesAdicionales informacionAdicionalPersona(BigInteger secEmpleado) {
        List<InformacionesAdicionales> listaInformacionesAdicionales;
        listaInformacionesAdicionales = persistenciaInformacionesAdicionales.informacionAdicionalPersona(secEmpleado);
        if (listaInformacionesAdicionales != null) {
            return listaInformacionesAdicionales.get(0);
        } else {
            return null;
        }
    }

    public Encargaturas reemplazoPersona(BigInteger secEmpleado) {
        List<Encargaturas> listaEncargaturas;
        listaEncargaturas = persistenciaEncargaturas.reemplazoPersona(secEmpleado);
        if (listaEncargaturas != null) {
            return listaEncargaturas.get(0);
        } else {
            return null;
        }
    }
}
