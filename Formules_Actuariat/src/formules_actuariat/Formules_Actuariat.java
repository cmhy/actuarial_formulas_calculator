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
    
    double probability_surviving (double[] lx, int x){
    double px;
    px = lx[x+1]/lx[x] ;
    return px;
    } //Probability of a life age x surviving to age x + 1
    
    double probability_not_surviving (double[] lx, int x){
    double qx;
    qx = (lx[x]-lx[x+1])/lx[x] ;
    return qx;
    } //Death between ages x and x + 1
    
    double probability_surviving_n (double[] lx, int x, int n){
    double px;
    px = lx[x+n]/lx[x] ;
    return px;
    } // Probability of a life age x surviving to age x + n
    
    double probability_not_surviving_n (double[] lx, int x, int n){
    double qx;
    qx = (lx[x]-lx[x+n])/lx[x] ;
    return qx;
    } //Death between ages x and x + n
    
    //Properties
    
    double probability_surviving_n_product (double[] lx, int x, int n){
    double px = 0;
    int i = 1;
    while(i < n-1){
        px = probability_surviving_n (lx, x, i)*probability_surviving_n (lx, x, i+1);
        i=i+1;
        }
    return px;
    } //Probability of a life after n years ↔ Alive from x to x+1 and from x+1 to x+2...and from x+n-1 to x+n
        
    double probability_surviving_n_plus_m (double[] lx, int x, int n, int m){
    double px;
    px = probability_surviving_n (lx, x, m)*probability_surviving_n (lx, x+m, n);
    return px;
    } //Probability of a life after m+n years ↔ Alive from x to x+m and from x+m to x+m+n
   
    double alive_until_m (double[] lx, int x, int m){
        double qx;
        qx = probability_surviving_n (lx, x, m)*probability_not_surviving(lx,x+m);
        return qx;
    }
    //Alive until m then die the following year i.e. before age x+m+1
    
    double alive_until_m_die_before_n_years(double[] lx, int x, int m){
        double qx;
        qx = probability_surviving_n (lx, x, m)*probability_not_surviving_n(lx,x+m,m);
        return qx;
    } // Alive until m, die before n years i.e. before age x+m+n
    
    double alive_until_m_then_die(double[] lx, int x, int m){
        double qx;
        qx = probability_surviving_n (lx, x, m)-probability_surviving_n(lx,x,m+1);
        return qx;
    }// Alive until m but not in m+1
    
    double alive_until_m_then_die_m_plus_n(double[] lx, int x, int m, int n){
        double qx;
        qx = probability_surviving_n (lx, x, m)-probability_surviving_n(lx,x,m+n);
        return qx;
    }// Alive until m but not in m+n
    
    //End page 1
    
    //Page 2
    
    double D_x (double[] lx, int x, float r){
       double D;
       D = lx[x]*(1/Math.pow((1+r),x));
       return D;
    }
    
    double N_x (double[] lx, int x, float r){
       double N = 0;
       int w = lx.length;
       for (int i = 0; i<w-x; i++){ N = N + D_x(lx,x+i,r); }
       return N;
    }
    
    double S_x (double[] lx, int x, float r){
       double S = 0;
       int w = lx.length;
       for (int i = 0; i<w-x; i++){ S = S + N_x(lx,x+i,r); }
       return S;
    }
    
    double pure_endowment (double[] lx, int x, int n, float r){
        double E;
        E = D_x(lx,x+n,r)/D_x(lx,x,r);
        return E;
    }
    
    //Life annuities due at the end of the year
    
    double life_annuity_up_to_the_death_end (double[] lx, int x, float r){
        double A;
        A = N_x(lx, x+1,r)/D_x(lx, x, r);
        return A;
    }
    
    double life_annuity_temporary_end (double[] lx, int x, int n, float r){
        double A;
        A = (N_x(lx, x+1,r)-N_x(lx, x+n+1,r))/D_x(lx, x, r);
        return A;
    }
    
    double life_annuity_deferred_end (double[] lx, int x, int m, float r){
        double A;
        A = N_x(lx, x+m+1, r)/D_x(lx, x, r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_end(double[] lx, int x, int n, int m, float r){
        double A;
        A = (N_x(lx, m+x+1,r)-N_x(lx, x+m+n+1,r))/D_x(lx, x, r);
        return A;
    }
    
    //Properties
    
    double life_annuity_temporary_and_deferred_end_prop (double[] lx, int x, int n, int m, float r){
        double A;
        A = pure_endowment(lx,x,m,r)*life_annuity_temporary_end(lx, x+m, n ,r);
        return A;
    } 
    
    double life_annuity_whole_life_end_prop (double[] lx, int x, int n, float r){
        double A;
        double B = life_annuity_temporary_end(lx, x, n ,r) ; 
        A = life_annuity_deferred_end(lx, x, n ,r) + B;
        return A;
    }
    
     //Life annuities due at the beggining of the year
    
    double life_annuity_up_to_the_death_beg (double[] lx, int x, float r){
        double A;
        A = N_x(lx, x,r)/D_x(lx, x, r);
        return A;
    }
    
    double life_annuity_temporary_beg (double[] lx, int x, int n, float r){
        double A;
        A = (N_x(lx, x,r)-N_x(lx, x+n,r))/D_x(lx, x, r);
        return A;
    }
    
    double life_annuity_deferred_beg (double[] lx, int x, int m, float r){
        double A;
        A = N_x(lx, x+m, r)/D_x(lx, x, r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_beg (double[] lx, int x, int n, int m, float r){
        double A;
        A = (N_x(lx, m+x,r)-N_x(lx, x+m+n,r))/D_x(lx, x, r);
        return A;
    }
//Properties
    
    double life_annuity_whole_life_beg_prop (double[] lx, int x, int n, int m, float r){
        double A;
        double B = life_annuity_temporary_beg(lx, x, n ,r) ; 
        A = life_annuity_deferred_beg(lx, x, n ,r) + B;
        return A;
    }
 
    // Life annuities with several payment each year, due at the end of the period
    double life_annuity_up_to_the_death_several_end (double[] lx, int x, int k, float r){
        double A;
        A = ((life_annuity_up_to_the_death_end(lx, x,r))+((k-1)/(2*k)));
        return A;
    }
    
    double life_annuity_temporary_several_end (double[] lx, int x, int n, int k, float r){
        double A;
        A = life_annuity_temporary_end(lx,x, n,r)+((k-1)/(2*k))*(1-pure_endowment(lx,x,n,r));       
        return A;
    }
    
    double life_annuity_deferred_several_end (double[] lx, int x, int m, int k, float r){
        double A;
        A = life_annuity_deferred_end(lx, x, m, r)+((k-1)/(2*k))*pure_endowment(lx,x,m,r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_several_end (double[] lx, int x, int n, int k, int m, float r){
        double A;
        A = pure_endowment(lx,x,m,r)*life_annuity_temporary_several_end(lx, x+m, n, k, r);
        return A;
    }
    
    // Life annuities with several payment each year, due at the beggining of the period
    double life_annuity_up_to_the_death_several_beg (double[] lx, int x, int k, float r){
        double A;
        A = ((life_annuity_up_to_the_death_beg(lx, x,r))+((k-1)/(2*k)));
        return A;
    }
    
    double life_annuity_temporary_several_beg (double[] lx, int x, int n, int k, float r){
        double A;
        A = life_annuity_temporary_beg(lx,x, n,r)+((k-1)/(2*k))*(1-pure_endowment(lx,x,n,r));       
        return A;
    }
    
    double life_annuity_deferred_several_beg (double[] lx, int x, int m, int k, float r){
        double A;
        A = life_annuity_deferred_beg(lx, x, m, r)+((k-1)/(2*k))*pure_endowment(lx,x,m,r);
        return A;
    }
    
    double life_annuity_temporary_and_deferred_several_beg (double[] lx, int x, int n, int k, int m, float r){
        double A;
        A = pure_endowment(lx,x,m,r)*life_annuity_temporary_several_beg(lx, x+m, n, k, r);
        return A;
    }
    
    //Commutation symbols in case of death 
    
    double C_x(double[] lx, int x, float r){ 
        return (lx[x]-lx[x+1])*(1/Math.pow((1+r),x));
    }
    
    
    double M_x(double[] lx, int x, float r){ 
       double M = 0;
       int w = lx.length;
       for (int i = 0; i<w-x; i++){ M = M + C_x(lx,x+i,r); }
       return M;
    }
    
    double R_x(double[] lx, int x, float r){ 
       double R = 0;
       int w = lx.length;
       for (int i = 0; i<w-x; i++){ R = R + M_x(lx,x+i,r); }
       return R;
    }
    
    //Insurance in case of Death
   
    double whole_life_A_x(double[] lx, int x, float r){ 
        return M_x(lx,x,r)/D_x(lx,x,r);
    }
    
    double term_insurance_n_A_x(double[] lx, int x, int n, float r){ 
        return (M_x(lx,x,r)-M_x(lx,x+n,r))/D_x(lx,x,r);
    }
    
    double term_insurance_differed_m_A_x(double[] lx, int x, int n, int m, float r){ 
        return (M_x(lx,m+x,r)-M_x(lx,x+m+n,r))/D_x(lx,x,r);
    }
    
    double whole_life_diferred_m_A_x(double[] lx, int x, int m, float r){ 
        return M_x(lx,x+m,r)/D_x(lx,x,r);
    }
    
    double term_insurance_increasing_life_annuities(double[] lx, int x, int n, float r){ 
        return (R_x(lx,x,r)-R_x(lx,x+n,r)-n*M_x(lx,x+n,r))/D_x(lx,x,r);
    }
    
    double term_insurance_decreasing_life_annuities(double[] lx, int x, int n, float r){ 
        return (n*M_x(lx,x+n,r)-(R_x(lx,x+1,r)-R_x(lx,x+n,r)))/D_x(lx,x,r);
    }
    
}
