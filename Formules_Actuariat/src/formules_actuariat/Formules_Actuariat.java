/*"
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formules_actuariat;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author user
 */


public class Formules_Actuariat {

    /**
     * @param args the command line arguments
     */
	
    private static double[] tableDeMortalite; 
    
    private static void setTableDeMortalite(String csvFile){
    	tableDeMortalite = new double[115];
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int i =0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] ligneCourante = line.split(cvsSplitBy);
                tableDeMortalite[i]= Double.parseDouble(ligneCourante[1]);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    static double lx(int x) {
    	return tableDeMortalite[x]; 
    }
    
    static double probability_surviving (int x){
    double px;
    px = lx(x+1)/lx(x) ;
    return px;
    } //Probability of a life age x surviving to age x + 1
    
    static double probability_not_surviving (int x){
    double qx;
    qx = (lx(x)-lx(x+1))/lx(x) ;
    return qx;
    } //Death between ages x and x + 1
    
    static double probability_surviving_n (int x, int n){
    double px;
    px = lx(x+n)/lx(x) ;
    return px;
    } // Probability of a life age x surviving to age x + n
    
    static double probability_not_surviving_n (int x, int n){
    double qx;
    qx = (lx(x)-lx(x+n))/lx(x) ;
    return qx;
    } //Death between ages x and x + n
    
    //Properties
    
    static double probability_surviving_n_product (int x, int n){
    double px = 0;
    int i = 1;
    while(i < n-1){
        px = probability_surviving_n (x, i)*probability_surviving_n (x, i+1);
        i=i+1;
        }
    return px;
    } //Probability of a life after n years ↔ Alive from x to x+1 and from x+1 to x+2...and from x+n-1 to x+n
        
    static double probability_surviving_n_plus_m (int x, int n, int m){
    double px;
    px = probability_surviving_n (x, m)*probability_surviving_n (x+m, n);
    return px;
    } //Probability of a life after m+n years ↔ Alive from x to x+m and from x+m to x+m+n
   
    static double alive_until_m (int x, int m){
        double qx;
        qx = probability_surviving_n ( x, m)*probability_not_surviving(x+m);
        return qx;
    }
    //Alive until m then die the following year i.e. before age x+m+1
    
    static double alive_until_m_die_before_n_years(int x, int m){
        double qx;
        qx = probability_surviving_n (x, m)*probability_not_surviving_n(x+m,m);
        return qx;
    } // Alive until m, die before n years i.e. before age x+m+n
    
    static double alive_until_m_then_die(int x, int m){
        double qx;
        qx = probability_surviving_n (x, m)-probability_surviving_n(x,m+1);
        return qx;
    }// Alive until m but not in m+1
    
    double alive_until_m_then_die_m_plus_n(int x, int m, int n){
        double qx;
        qx = probability_surviving_n (x, m)-probability_surviving_n(x,m+n);
        return qx;
    }// Alive until m but not in m+n
    
    //End page 1
    
    //Page 2
    
    double D_x (int x, float r){
       double D;
       D = lx(x)*(1/(1+r));
       return D;
    }
    
    double N_x (int x, float r){
       double N = 0;
       int w = tableDeMortalite.length;
       for (int i = 0; i<w-x; i++){ N = N + D_x(x+i,r); }
       return N;
    }
    
    double S_x (int x, float r){
       double S = 0;
       int w = tableDeMortalite.length;
       for (int i = 0; i<w-x; i++){ S = S + N_x(x+i,r); }
       return S;
    }
    
    double pure_endowment (int x, int n, float r){
        double E;
        E = D_x(x+n,r)/D_x(x,r);
        return E;
    }
    
    //Life annuities due at the end of the year
    
    double life_annuity_up_to_the_death_end (int x, float r){
        double A;
        A = N_x(x+1,r)/D_x(x, r);
        return A;
    }
    
    double life_annuity_temporary_end (int x, int n, float r){
        double A;
        A = (N_x(x+1,r)-N_x(x+n+1,r))/D_x(x, r);
        return A;
    }
    
    double life_annuity_deferred_end (int x, int m, float r){
        double A;
        A = N_x(x+m+1, r)/D_x(x, r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_end(int x, int n, int m, float r){
        double A;
        A = (N_x(m+x+1,r)-N_x(x+m+n+1,r))/D_x(x, r);
        return A;
    }
    
    //Properties
    
    double life_annuity_temporary_and_deferred_end_prop (int x, int n, int m, float r){
        double A;
        A = pure_endowment(x,m,r)*life_annuity_temporary_end(x+m, n ,r);
        return A;
    } 
    
    double life_annuity_whole_life_end_prop (int x, int n, float r){
        double A;
        double B = life_annuity_temporary_end(x, n ,r) ; 
        A = life_annuity_deferred_end(x, n ,r) + B;
        return A;
    }
    
     //Life annuities due at the beggining of the year
    
    double life_annuity_up_to_the_death_beg (int x, float r){
        double A;
        A = N_x(x,r)/D_x(x, r);
        return A;
    }
    
    double life_annuity_temporary_beg (int x, int n, float r){
        double A;
        A = (N_x(x,r)-N_x(x+n,r))/D_x( x, r);
        return A;
    }
    
    double life_annuity_deferred_beg (int x, int m, float r){
        double A;
        A = N_x(x+m, r)/D_x(x, r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_beg (int x, int n, int m, float r){
        double A;
        A = (N_x(m+x,r)-N_x(x+m+n,r))/D_x(x, r);
        return A;
    }
//Properties
    
    double life_annuity_whole_life_beg_prop (int x, int n, int m, float r){
        double A;
        double B = life_annuity_temporary_beg(x, n ,r) ; 
        A = life_annuity_deferred_beg(x, n ,r) + B;
        return A;
    }
 
    // Life annuities with several payment each year, due at the end of the period
    double life_annuity_up_to_the_death_several_end (int x, int k, float r){
        double A;
        A = ((life_annuity_up_to_the_death_end(x,r))+((k-1)/(2*k)));
        return A;
    }
    
    double life_annuity_temporary_several_end (int x, int n, int k, float r){
        double A;
        A = life_annuity_temporary_end(x, n,r)+((k-1)/(2*k))*(1-pure_endowment(x,n,r));       
        return A;
    }
    
    double life_annuity_deferred_several_end (int x, int m, int k, float r){
        double A;
        A = life_annuity_deferred_end(x, m, r)+((k-1)/(2*k))*pure_endowment(x,m,r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_several_end (int x, int n, int k, int m, float r){
        double A;
        A = pure_endowment(x,m,r)*life_annuity_temporary_several_end(x+m, n, k, r);
        return A;
    }
    
    // Life annuities with several payment each year, due at the beggining of the period
    double life_annuity_up_to_the_death_several_beg (int x, int k, float r){
        double A;
        A = ((life_annuity_up_to_the_death_beg(x,r))+((k-1)/(2*k)));
        return A;
    }
    
    double life_annuity_temporary_several_beg (int x, int n, int k, float r){
        double A;
        A = life_annuity_temporary_beg(x, n,r)+((k-1)/(2*k))*(1-pure_endowment(x,n,r));       
        return A;
    }
    
    double life_annuity_deferred_several_beg (int x, int m, int k, float r){
        double A;
        A = life_annuity_deferred_beg(x, m, r)+((k-1)/(2*k))*pure_endowment(x,m,r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_several_beg (int x, int n, int k, int m, float r){
        double A;
        A = pure_endowment(x,m,r)*life_annuity_temporary_several_beg(x+m, n, k, r);
        return A;
    }
    
    public static void main(String[] args) {
    	String csvFile = "G:\\COURS\\MAM5-IMAFA\\Actuariat\\TD\\TD\\Classeur1.csv";
    	setTableDeMortalite(csvFile);
    	System.out.println(lx(5));
    }
}
