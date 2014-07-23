/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Jornadas;
import Entidades.JornadasLaborales;
import Entidades.JornadasSemanales;
import InterfaceAdministrar.AdministrarJornadasLaboralesInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaJornadasInterface;
import InterfacePersistencia.PersistenciaJornadasLaboralesInterface;
import InterfacePersistencia.PersistenciaJornadasSemanalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
@Stateful
public class AdministrarJornadasLaborales implements AdministrarJornadasLaboralesInterface {

    @EJB
    PersistenciaJornadasLaboralesInterface persistenciaJornadasLaborales;
    @EJB
    PersistenciaJornadasSemanalesInterface persistenciaJornadasSemanales;
    @EJB
    PersistenciaJornadasInterface persistenciaJornadas;
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    // Metodos
    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    public List<JornadasLaborales> consultarJornadasLaborales() {
        List<JornadasLaborales> listaJornadasLaborales;
        listaJornadasLaborales = persistenciaJornadasLaborales.buscarJornadasLaborales(em);
        return listaJornadasLaborales;
    }

    public List<Jornadas> consultarJornadas() {
        List<Jornadas> listaJornadas;
        listaJornadas = persistenciaJornadas.consultarJornadas(em);
        return listaJornadas;
    }

    @Override
    public void modificarJornadasLaborales(List<JornadasLaborales> listaJornadasLaborales) {
        JornadasLaborales c;
        for (int i = 0; i < listaJornadasLaborales.size(); i++) {
            System.out.println("Modificando...JornadasLaborales");
            if (listaJornadasLaborales.get(i).getCodigo().equals(null)) {
                listaJornadasLaborales.get(i).setCodigo(null);
                c = listaJornadasLaborales.get(i);
            } else {
                c = listaJornadasLaborales.get(i);
            }
            persistenciaJornadasLaborales.editar(em, c);
        }
    }

    @Override
    public void borrarJornadasLaborales(List<JornadasLaborales> listaJornadasLaborales) {
        for (int i = 0; i < listaJornadasLaborales.size(); i++) {
            System.out.println("Borrando..JornadasLaborales.");
            if (listaJornadasLaborales.get(i).getCodigo().equals(null)) {

                listaJornadasLaborales.get(i).setCodigo(null);
                persistenciaJornadasLaborales.borrar(em, listaJornadasLaborales.get(i));
            } else {
                persistenciaJornadasLaborales.borrar(em, listaJornadasLaborales.get(i));
            }
        }
    }

    @Override
    public void crearJornadasLaborales(List<JornadasLaborales> listaJornadasLaborales) {
        for (int i = 0; i < listaJornadasLaborales.size(); i++) {
            System.out.println("Creando. JornadasLaborales..");
            if (listaJornadasLaborales.get(i).getCodigo().equals(null)) {

                listaJornadasLaborales.get(i).setCodigo(null);
                persistenciaJornadasLaborales.crear(em, listaJornadasLaborales.get(i));
            } else {
                persistenciaJornadasLaborales.crear(em, listaJornadasLaborales.get(i));
            }
        }
    }

    @Override
    public void modificarJornadasSemanales(List<JornadasSemanales> listaJornadasSemanales) {
        JornadasSemanales c;
        for (int i = 0; i < listaJornadasSemanales.size(); i++) {
            System.out.println("Modificando JornadasSemanales...");
            c = listaJornadasSemanales.get(i);

            persistenciaJornadasSemanales.editar(em, c);
        }
    }

    @Override
    public void borrarJornadasSemanales(List<JornadasSemanales> listaJornadasSemanales) {
        for (int i = 0; i < listaJornadasSemanales.size(); i++) {
            System.out.println("Borrando JornadasSemanales...");

            persistenciaJornadasSemanales.borrar(em, listaJornadasSemanales.get(i));

        }
    }

    @Override
    public void crearJornadasSemanales(List<JornadasSemanales> listaJornadasSemanales) {
        for (int i = 0; i < listaJornadasSemanales.size(); i++) {
            System.out.println("Creando JornadasSemanales...");
            System.out.println("secuencia: "+ listaJornadasSemanales.get(i).getSecuencia());
            persistenciaJornadasSemanales.crear(em, listaJornadasSemanales.get(i));

        }
    }

    @Override
    public List<JornadasSemanales> consultarJornadasSemanales(BigInteger secuencia) {
        List<JornadasSemanales> listaJornadasSemanales;
        listaJornadasSemanales = persistenciaJornadasSemanales.buscarJornadasSemanalesPorJornadaLaboral(em, secuencia);
        return listaJornadasSemanales;
    }
}
