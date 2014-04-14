/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Conceptos;
import Entidades.ConceptosRedondeos;
import Entidades.TiposRedondeos;
import InterfaceAdministrar.AdministrarConceptosRedondeosInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaConceptosRedondeosInterface;
import InterfacePersistencia.PersistenciaTiposRedondeosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarConceptosRedondeos implements AdministrarConceptosRedondeosInterface{

    @EJB
    PersistenciaConceptosRedondeosInterface persistenciaConceptosRedondeos;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    @EJB
    PersistenciaTiposRedondeosInterface persistenciaTiposRedondeos;

//Conceptos Redondeos
    @Override
    public void borrarConceptosRedondeos(ConceptosRedondeos conceptosRedondeos) {
        persistenciaConceptosRedondeos.borrar(conceptosRedondeos);
    }

    @Override
    public void crearConceptosRedondeos(ConceptosRedondeos conceptosRedondeos) {
        persistenciaConceptosRedondeos.crear(conceptosRedondeos);
    }

    @Override
    public void modificarConceptosRedondeos(List<ConceptosRedondeos> listaConceptosRedondeosModificar) {
        for (int i = 0; i < listaConceptosRedondeosModificar.size(); i++) {
            System.out.println("Modificando...");
            persistenciaConceptosRedondeos.editar(listaConceptosRedondeosModificar.get(i));
        }
    }

    public List<ConceptosRedondeos> consultarConceptosRedondeos() {
        try {
            List<ConceptosRedondeos> actual = persistenciaConceptosRedondeos.buscarConceptosRedondeos();
            return actual;
        } catch (Exception e) {
            System.out.println("Error consultarVigenciasRetenciones: " + e.toString());
            return null;
        }
    }
    
    //LOV Conceptos
    @Override
    public List<Conceptos> lovConceptos() {
        try {
            List<Conceptos> actual = persistenciaConceptos.buscarConceptos();
            return actual;
        } catch (Exception e) {
            System.out.println("Error lovConceptos: " + e.toString());
            return null;
        }
    }
    
    //LOV Tipos Redondeos
    @Override
    public List<TiposRedondeos> lovTiposRedondeos() {
        try {
            List<TiposRedondeos> actual = persistenciaTiposRedondeos.buscarTiposRedondeos();
            return actual;
        } catch (Exception e) {
            System.out.println("Error lovTiposRedondeos: " + e.toString());
            return null;
        }
    }

}
