/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angelo-KabyLake
 */
public class Pagination {
    private int nombreLigneParPage;
    private GeneriqueDAO dao = new GeneriqueDAO();
    private List<BaseModel> pageActuel;
    private int nombrePage;

    public BaseModel getTypage() {
        return typage;
    }

    public void setTypage(BaseModel typage) {
        this.typage = typage;
    }
    private BaseModel typage;
    

    public int getNombreLigneParPage() {
        return nombreLigneParPage;
        
    }
    public void setNombreLigneParPage(int nombreLigneParPage) {
        this.nombreLigneParPage = nombreLigneParPage;
    }
    public List<BaseModel> getPageActuel() {
        return pageActuel;
    }
    public void setPageActuel(List<BaseModel> pageActuel) {
        this.pageActuel = pageActuel;
    }
    
    
    public Pagination() {}
    public Pagination(BaseModel bm, int nombreLigneParPage) throws Exception {
        List<BaseModel> temp = dao.findAll(bm);
        
        System.out.println("TAILLE = "+temp.size());
       
        int taille = temp.size()/nombreLigneParPage;
        //System.out.println("taille TOTAL = "+temp.length);
        //System.out.println("taille pagination = "+taille);
        if(taille%2==0){
            //System.out.println("taille pagination2 = "+taille);
            this.setNombreLigneParPage(nombreLigneParPage);
            this.setNombrePage(taille);
        }
        else if(taille%2!=0){
            //System.out.println("taille pagination2 = "+taille);
            this.setNombreLigneParPage(nombreLigneParPage);
            this.setNombrePage(taille+1);
        }
        
        List<BaseModel> page = null;
        if(getNombreLigneParPage()!=0){
            page = dao.pagination(bm,1 , getNombreLigneParPage());
            this.setPageActuel(page);
            System.out.println("ANATY PAGINATION ");
        }
        typage = bm;
       
    }  

    public int getNombrePage() {
        return nombrePage;
    }

    public void setNombrePage(int nombrePage) {
        this.nombrePage = nombrePage;
    }
  
    public void getPage(int indexPage){
        try{
            //System.out.println(indexPage+ " <= "+this.getNombrePage());
            if(indexPage<=this.getNombrePage()){
            List<BaseModel> temp = dao.pagination(typage, indexPage, this.getNombreLigneParPage());       
            this.setPageActuel(temp);
             System.out.println("Apres modification paginaation");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

   
}
