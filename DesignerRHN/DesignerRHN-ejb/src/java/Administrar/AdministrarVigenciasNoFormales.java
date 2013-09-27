/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.AdiestramientosNF;
import Entidades.Cursos;
import Entidades.Instituciones;
import Entidades.Personas;
import Entidades.VigenciasNoFormales;
import InterfaceAdministrar.AdministrarVigenciasNoFormalesInterface;
import InterfacePersistencia.PersistenciaAdiestramientosNFInterface;
import InterfacePersistencia.PersistenciaCursosInterface;
import InterfacePersistencia.PersistenciaInstitucionesInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import InterfacePersistencia.PersistenciaVigenciasNoFormalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarVigenciasNoFormales implements AdministrarVigenciasNoFormalesInterface {

    @EJB
    PersistenciaVigenciasNoFormalesInterface persistenciaVigenciasNoFormales;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaCursosInterface persistenciaCursos;
    @EJB
    PersistenciaInstitucionesInterface persistenciaInstituciones;
    @EJB
    PersistenciaAdiestramientosNFInterface persistenciaAdiestramientosNF;
    private VigenciasNoFormales vNF;
    
    @Override
    public List<VigenciasNoFormales> vigenciasNoFormalesPersona(BigInteger secPersona) {
        try {
            return persistenciaVigenciasNoFormales.vigenciasNoFormalesPersona(secPersona);
        } catch (Exception e) {
            System.err.println("Error AdministrarVigenciasNoFormales.vigenciasNoFormalesPersona " + e);
            return null;
        }
    }

    @Override
    public Personas encontrarPersona(BigInteger secPersona) {
        return persistenciaPersonas.buscarPersonaSecuencia(secPersona);
    }
    
    //Listas de Valores Cursos, Instituciones, Adiestramiento
    
    @Override
    public List<Cursos> lovCursos() {
        return persistenciaCursos.cursos();
    }

    @Override
    public List<Instituciones> lovInstituciones() {
        return persistenciaInstituciones.instituciones();
    }
    
    @Override
    public List<AdiestramientosNF> lovAdiestramientosNF() {
        return persistenciaAdiestramientosNF.adiestramientosNF();
    }
    
    @Override
    public void modificarVigenciaNoFormal(List<VigenciasNoFormales> listaVigenciasNoFormalesModificar) {
        for (int i = 0; i < listaVigenciasNoFormalesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaVigenciasNoFormalesModificar.get(i).getCurso().getSecuencia() == null) {
                listaVigenciasNoFormalesModificar.get(i).setCurso(null);
                vNF = listaVigenciasNoFormalesModificar.get(i);
            } else {
                vNF = listaVigenciasNoFormalesModificar.get(i);
            }
            if (listaVigenciasNoFormalesModificar.get(i).getInstitucion().getSecuencia() == null) {
                listaVigenciasNoFormalesModificar.get(i).setInstitucion(null);
                vNF = listaVigenciasNoFormalesModificar.get(i);
            } else {
                vNF = listaVigenciasNoFormalesModificar.get(i);
            }
            if (listaVigenciasNoFormalesModificar.get(i).getAdiestramientonf().getSecuencia() == null) {
                listaVigenciasNoFormalesModificar.get(i).setAdiestramientonf(null);
                vNF = listaVigenciasNoFormalesModificar.get(i);
            } else {
                vNF = listaVigenciasNoFormalesModificar.get(i);
            }
            
            
            persistenciaVigenciasNoFormales.editar(vNF);
        }
    }

    @Override
    public void borrarVigenciaNoFormal(VigenciasNoFormales vigenciasNoFormales) {
        persistenciaVigenciasNoFormales.borrar(vigenciasNoFormales);
    }


    @Override
    public void crearVigenciaNoFormal(VigenciasNoFormales vigenciasNoFormales) {
        persistenciaVigenciasNoFormales.crear(vigenciasNoFormales);
    }
    

}




