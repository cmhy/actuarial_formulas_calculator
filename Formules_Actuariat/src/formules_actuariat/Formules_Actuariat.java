/*"
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formules_actuariat;

/**
 *
 * @author user
 */
public class Formules_Actuariat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    static void probability_surviving (double[] lx, int n){
    int x=1;
    int y=1;
    int z=1;
    int w = lx.length;
    double[] npx = null;
    double[] nqx = null;
    double[] ndx = null;
    
    do {
        ndx[z] = lx[z+n]-lx[z] ;
        z=z+1;
    }while(z<=w); 
    
    do {
        npx[x] = lx[x+n]/lx[x] ;
        x=x+1;
    }while(x<=w); //proba of life age x surviving x+n
    
    do {
        nqx[y] = (lx[y]-lx[y+n])/lx[y] ;
        y=y+1;
    }while(y<=w); //proba of death age x surviving x+n
    }
    
    
    
}
