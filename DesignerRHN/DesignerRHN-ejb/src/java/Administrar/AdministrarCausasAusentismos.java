/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Causasausentismos;
import Entidades.Clasesausentismos;
import InterfaceAdministrar.AdministrarCausasAusentismosInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaCausasAusentismosInterface;
import InterfacePersistencia.PersistenciaClasesAusentismosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

@Stateful
public class AdministrarCausasAusentismos implements AdministrarCausasAusentismosInterface{

    @EJB
    PersistenciaClasesAusentismosInterface persistenciaClasesAusentismos;
    
    @EJB
    PersistenciaCausasAusentismosInterface persistenciaCausasausentismos;

    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    @Override
    public List<Causasausentismos> consultarCausasAusentismos() {
        List<Causasausentismos> listaCausasAusentismo;
        listaCausasAusentismo = persistenciaCausasausentismos.buscarCausasAusentismos(em);
        return listaCausasAusentismo;
    }
    
    @Override
    public List<Clasesausentismos> consultarClasesAusentismos() {
        List<Clasesausentismos> listaClasesAusentismos;
        listaClasesAusentismos = persistenciaClasesAusentismos.buscarClasesAusentismos(em);
        return listaClasesAusentismos;
    }
     
    @Override
    public void modificarCausasAusentismos(List<Causasausentismos> listaCausasAusentismo) {
        Causasausentismos c;
        for (int i = 0; i < listaCausasAusentismo.size(); i++) {
            System.out.println("Modificando...CausasAusentismos");
            if (listaCausasAusentismo.get(i).getCodigo().equals(null)) {
                c = listaCausasAusentismo.get(i);
            } else {
                c = listaCausasAusentismo.get(i);
            }
            persistenciaCausasausentismos.editar(em, c);
        }
    }
    
    @Override
    public void borrarCausasAusentismos(List<Causasausentismos> listaCausasAusentismo) {
        for (int i = 0; i < listaCausasAusentismo.size(); i++) {
            System.out.println("Borrando..CausasAusentismos.");
            if (listaCausasAusentismo.get(i).getCodigo().equals(null)) {

                persistenciaCausasausentismos.borrar(em, listaCausasAusentismo.get(i));
            } else {
                persistenciaCausasausentismos.borrar(em, listaCausasAusentismo.get(i));
            }
        }
    }
    
    @Override
    public void crearCausasAusentismos(List<Causasausentismos> listaCausasAusentismo) {
        for (int i = 0; i < listaCausasAusentismo.size(); i++) {
            System.out.println("Creando. JornadasLaborales..");
            if (listaCausasAusentismo.get(i).getCodigo().equals(null)) {
                persistenciaCausasausentismos.crear(em, listaCausasAusentismo.get(i));
            } else {
                persistenciaCausasausentismos.crear(em, listaCausasAusentismo.get(i));
        }
    }
    }  

}
