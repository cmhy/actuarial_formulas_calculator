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
    
    static double probability_surviving (double[] lx, int x){
    double px;
    px = lx[x+1]/lx[x] ;
    return px;
    }
    
    static double probability_not_surviving (double[] lx, int x){
    double qx;
    qx = (lx[x]-lx[x+1])/lx[x] ;
    return qx;
    }
    
    static double probability_surviving_n (double[] lx, int x, int n){
    double px;
    px = lx[x+n]/lx[x] ;
    return px;
    }
    
    static double probability_not_surviving_n (double[] lx, int x, int n){
    double qx;
    qx = (lx[x]-lx[x+n])/lx[x] ;
    return qx;
    }
    
    static double probability_surviving_n_product (double[] lx, int x, int n){
    double px = 0;
    int i = 1;
    while(i < n-1){
        px = probability_surviving_n (lx, x, i)*probability_surviving_n (lx, x, i+1);
        i=i+1;
        }
    return px;
    }
    
   
}
