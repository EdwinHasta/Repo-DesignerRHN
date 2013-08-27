package Cargue;

import Entidades.Estructuras;
import InterfaceAdministrar.AdministrarEstructurasInterface;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@SessionScoped
public class PruebaComponetesUnitarios implements Serializable {

    @EJB
    AdministrarEstructurasInterface administrarEstructuras;
    private TreeNode arbolEstructuras;
    private List<Estructuras> estructurasPadre;
    private List<Estructuras> estructurasHijas1;
    private List<Estructuras> estructurasHijas2;
    private List<Estructuras> estructurasHijas3;
    private List<Estructuras> estructurasHijas4;
    private List<Estructuras> estructurasHijas5;
    private List<Estructuras> estructurasHijas6;
    private List<Estructuras> estructurasHijas7;
    private List<Estructuras> estructurasHijas8;
    private List<Estructuras> estructurasHijas9;
    private List<Estructuras> estructurasHijas10;

    public PruebaComponetesUnitarios() {
        arbolEstructuras = null;
        estructurasPadre = new ArrayList<Estructuras>();
        estructurasHijas1 = new ArrayList<Estructuras>();
        estructurasHijas2 = new ArrayList<Estructuras>();
        estructurasHijas3 = new ArrayList<Estructuras>();
        estructurasHijas4 = new ArrayList<Estructuras>();
        estructurasHijas5 = new ArrayList<Estructuras>();
        estructurasHijas6 = new ArrayList<Estructuras>();
        estructurasHijas7 = new ArrayList<Estructuras>();
        estructurasHijas8 = new ArrayList<Estructuras>();
        estructurasHijas9 = new ArrayList<Estructuras>();
        estructurasHijas10 = new ArrayList<Estructuras>();



        /*TreeNode pictures = new DefaultTreeNode("Buuu", documents);
         TreeNode movies = new DefaultTreeNode("Sexy", documents);

         TreeNode work = new DefaultTreeNode("Como estas?", documents);
         TreeNode primefaces = new DefaultTreeNode("Holaaa... estas?", documents);*/

        /* //Documents  
         TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
         TreeNode resume = new DefaultTreeNode("document", new Document("Resume.doc", "10 KB", "Word Document"), work);
         TreeNode refdoc = new DefaultTreeNode("document", new Document("RefDoc.pages", "40 KB", "Pages Document"), primefaces);

         //Pictures  
         TreeNode barca = new DefaultTreeNode("picture", new Document("barcelona.jpg", "30 KB", "JPEG Image"), pictures);
         TreeNode primelogo = new DefaultTreeNode("picture", new Document("logo.jpg", "45 KB", "JPEG Image"), pictures);
         TreeNode optimus = new DefaultTreeNode("picture", new Document("optimusprime.png", "96 KB", "PNG Image"), pictures);

         //Movies  
         TreeNode pacino = new DefaultTreeNode(new Document("Al Pacino", "-", "Folder"), movies);
         TreeNode deniro = new DefaultTreeNode(new Document("Robert De Niro", "-", "Folder"), movies);

         TreeNode scarface = new DefaultTreeNode("mp3", new Document("Scarface", "15 GB", "Movie File"), pacino);
         TreeNode carlitosWay = new DefaultTreeNode("mp3", new Document("Carlitos' Way", "24 GB", "Movie File"), pacino);

         TreeNode goodfellas = new DefaultTreeNode("mp3", new Document("Goodfellas", "23 GB", "Movie File"), deniro);
         TreeNode untouchables = new DefaultTreeNode("mp3", new Document("Untouchables", "17 GB", "Movie File"), deniro);*/
    }

    public TreeNode getArbolEstructuras() {
        if (arbolEstructuras == null) {
            arbolEstructuras = new DefaultTreeNode("arbolEstructuras", null);
            //estructurasPadre = administrarEstructuras.estructuraPadre();
            if (estructurasPadre != null) {
                for (int i = 0; i < estructurasPadre.size(); i++) {
                    TreeNode padre = new DefaultTreeNode(estructurasPadre.get(i), arbolEstructuras);
                    estructurasHijas1 = administrarEstructuras.estructurasHijas(estructurasPadre.get(i).getSecuencia());
                    if (estructurasHijas1 != null) {
                        for (int j = 0; j < estructurasHijas1.size(); j++) {
                            TreeNode hija1 = new DefaultTreeNode(estructurasHijas1.get(j), padre);
                            estructurasHijas2 = administrarEstructuras.estructurasHijas(estructurasHijas1.get(j).getSecuencia());
                            if (estructurasHijas2 != null) {
                                for (int k = 0; k < estructurasHijas2.size(); k++) {
                                    TreeNode hija2 = new DefaultTreeNode(estructurasHijas2.get(k), hija1);
                                    estructurasHijas3 = administrarEstructuras.estructurasHijas(estructurasHijas2.get(k).getSecuencia()); 
                                    if (estructurasHijas3 != null) {
                                        for (int l = 0; l < estructurasHijas3.size(); l++) {
                                            TreeNode hija3 = new DefaultTreeNode(estructurasHijas3.get(l), hija2);
                                            estructurasHijas4 = administrarEstructuras.estructurasHijas(estructurasHijas3.get(l).getSecuencia());
                                            if (estructurasHijas4 != null) {
                                                for (int m = 0; m < estructurasHijas4.size(); m++) {
                                                    TreeNode hija4 = new DefaultTreeNode(estructurasHijas4.get(m), hija3);
                                                    estructurasHijas4 = administrarEstructuras.estructurasHijas(estructurasHijas4.get(m).getSecuencia());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return arbolEstructuras;
    }

    public List<Estructuras> getEstructurasPadre() {
        return estructurasPadre;
    }

    public void setEstructurasPadre(List<Estructuras> estructurasPadre) {
        this.estructurasPadre = estructurasPadre;
    }
}
