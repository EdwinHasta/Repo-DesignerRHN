/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Declarantes;
import Entidades.RetencionesMinimas;
import Entidades.TarifaDeseo;
import InterfaceAdministrar.AdministrarDeclarantesInterface;
import InterfacePersistencia.PersistenciaDeclarantesInterface;
import InterfacePersistencia.PersistenciaRetencionesMinimasInterface;
import InterfacePersistencia.PersistenciaTarifaDeseoInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarDeclarantes implements AdministrarDeclarantesInterface {

    @EJB
    PersistenciaDeclarantesInterface persistenciaDeclarantes;
    @EJB
    PersistenciaRetencionesMinimasInterface persistenciaRetencionesMinimas;
    @EJB
    PersistenciaTarifaDeseoInterface persistenciaTarifaDeseo;

    List<Declarantes> declarantesLista;
    List<TarifaDeseo> retencionesLista;
    List<RetencionesMinimas> retencionesMinimasLista;
    Declarantes declarantes;

    @Override
    public List<Declarantes> declarantesPersona(BigInteger secPersona) {
        try {
            declarantesLista = persistenciaDeclarantes.buscarDeclarantesPersona(secPersona);
        } catch (Exception e) {
            System.out.println("Error en Administrar Declarantes (declarantesPersona)");
            declarantesLista = null;
        }
        return declarantesLista;
    }

    @Override
    public void modificarDeclarantes(List<Declarantes> listaDeclarantesModificados) {
        for (int i = 0; i < listaDeclarantesModificados.size(); i++) {
            if (listaDeclarantesModificados.get(i).getRetenciondeseada() == null) {
                listaDeclarantesModificados.get(i).setRetenciondeseada(null);
            }

            if (listaDeclarantesModificados.get(i).getRetencionminima().getSecuencia() == null) {
                listaDeclarantesModificados.get(i).setRetencionminima(null);
            }
            System.out.println("Modificando...");
            
            persistenciaDeclarantes.editar(listaDeclarantesModificados.get(i));
        }
    }

    @Override
    public void borrarDeclarantes(Declarantes declarantes) {
        persistenciaDeclarantes.borrar(declarantes);
    }

    @Override
    public void crearDeclarantes(Declarantes declarantes) {
        persistenciaDeclarantes.crear(declarantes);
    }

    public List<TarifaDeseo> retencionesMinimas(Date fechaFinal) {
        retencionesLista = persistenciaTarifaDeseo.retenciones(fechaFinal);
        return retencionesLista;
    }
    
    public List<RetencionesMinimas> retencionesMinimasLista(){
        retencionesMinimasLista = persistenciaRetencionesMinimas.retenciones();
        return retencionesMinimasLista;
    }

}
