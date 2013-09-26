/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.AdiestramientosF;
import Entidades.Instituciones;
import Entidades.Personas;
import Entidades.Profesiones;
import Entidades.TiposEducaciones;
import Entidades.VigenciasFormales;
import InterfaceAdministrar.AdministrarVigenciasFormalesInterface;
import InterfacePersistencia.PersistenciaAdiestramientosFInterface;
import InterfacePersistencia.PersistenciaInstitucionesInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import InterfacePersistencia.PersistenciaProfesionesInterface;
import InterfacePersistencia.PersistenciaTiposEducacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasFormalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarVigenciasFormales implements AdministrarVigenciasFormalesInterface {

    @EJB
    PersistenciaVigenciasFormalesInterface persistenciaVigenciasFormales;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaTiposEducacionesInterface persistenciaTiposEducaciones;
    @EJB
    PersistenciaProfesionesInterface persistenciaProfesiones;
    @EJB
    PersistenciaInstitucionesInterface persistenciaInstituciones;
    @EJB
    PersistenciaAdiestramientosFInterface persistenciaAdiestramientosF;
    private VigenciasFormales vF;
    

    @Override
    public List<VigenciasFormales> vigenciasFormalesPersona(BigInteger secPersona) {
        try {
            return persistenciaVigenciasFormales.vigenciasFormalesPersona(secPersona);
        } catch (Exception e) {
            System.err.println("Error AdministrarVigenciasFormales.vigenciasFormalesPersona " + e);
            return null;
        }
    }

    @Override
    public Personas encontrarPersona(BigInteger secPersona) {
        return persistenciaPersonas.buscarPersonaSecuencia(secPersona);
    }

    //Listas de Valores Educacion, Profesion, Instituciones, Adiestramiento
    @Override
    public List<TiposEducaciones> lovTiposEducaciones() {
        return persistenciaTiposEducaciones.tiposEducaciones();
    }

    @Override
    public List<Profesiones> lovProfesiones() {
        return persistenciaProfesiones.profesiones();
    }

    @Override
    public List<Instituciones> lovInstituciones() {
        return persistenciaInstituciones.instituciones();
    }
    
    @Override
    public List<AdiestramientosF> lovAdiestramientosF() {
        return persistenciaAdiestramientosF.adiestramientosF();
    }
    
        @Override
    public void modificarVigenciaFormal(List<VigenciasFormales> listaVigenciasFormalesModificar) {
        for (int i = 0; i < listaVigenciasFormalesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaVigenciasFormalesModificar.get(i).getTipoeducacion().getSecuencia() == null) {
                listaVigenciasFormalesModificar.get(i).setTipoeducacion(null);
                vF = listaVigenciasFormalesModificar.get(i);
            } else {
                vF = listaVigenciasFormalesModificar.get(i);
            }
            if (listaVigenciasFormalesModificar.get(i).getProfesion().getSecuencia() == null) {
                listaVigenciasFormalesModificar.get(i).setProfesion(null);
                vF = listaVigenciasFormalesModificar.get(i);
            } else {
                vF = listaVigenciasFormalesModificar.get(i);
            }
            if (listaVigenciasFormalesModificar.get(i).getInstitucion().getSecuencia() == null) {
                listaVigenciasFormalesModificar.get(i).setInstitucion(null);
                vF = listaVigenciasFormalesModificar.get(i);
            } else {
                vF = listaVigenciasFormalesModificar.get(i);
            }
            if (listaVigenciasFormalesModificar.get(i).getAdiestramientof().getSecuencia() == null) {
                listaVigenciasFormalesModificar.get(i).setAdiestramientof(null);
                vF = listaVigenciasFormalesModificar.get(i);
            } else {
                vF = listaVigenciasFormalesModificar.get(i);
            }
            
            
            persistenciaVigenciasFormales.editar(vF);
        }
    }

    @Override
    public void borrarVigenciaFormal(VigenciasFormales vigenciasFormales) {
        persistenciaVigenciasFormales.borrar(vigenciasFormales);
    }


    @Override
    public void crearVigenciaFormal(VigenciasFormales vigenciasFormales) {
        persistenciaVigenciasFormales.crear(vigenciasFormales);
    }
    
}
