/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actuariat;
import java.applet.*;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author user
 */
public class Actuariat  extends Applet{

    /**
     * @param g
     * @param args the command line arguments
     */
        int nbLineUseless = 4;
      

    
    
     private Sheet dataTable(String filename){
         
        
         try{
            XSSFWorkbook wb = new XSSFWorkbook(filename);
            Sheet theSheet =wb.getSheetAt(0);
        
        return theSheet;
      
         }catch(Exception e){
            System.out.println("Catch dataTable");
           return null;
          
         }
     }
         
         private double lx(int age,Sheet mySheet){
             
            try{
                
                return mySheet.getRow(nbLineUseless+age).getCell(1).getNumericCellValue();
        
                }catch(Exception e){
             
             return -1;
            }
         }
            
            
            private  String  px( int x){
                try{
            
                String mySheetFileName = "/home/user/Bureau/prototype/TD88-90.xlsx";
                //XSSFWorkbook wb = dataTable(mySheetFileName);
                Sheet theSheet =dataTable(mySheetFileName);
                int age=x;//Integer.parseInt(getParameter("x"));
                 
                //double lx=theSheet.getRow(nbLineUseless+age).getCell(1).getNumericCellValue();
                //double lxPlusUn = theSheet.getRow(nbLineUseless+age+1).getCell(1).getNumericCellValue();
               double res= ((lx(age,theSheet)- lx(age+1,theSheet))/lx(age,theSheet));
                //System.out.println(lx(0,theSheet));
                String result= "result = "+res;
               return result;
                //Font f = new Font(font, Font.BOLD, 10);
                //g.setFont(f);
            
      
             }catch(Exception e){
                
                    return "empty";
            }
               
            }
            
            
           /* public static void main(String[] args){
               Actuariat act= new Actuariat();
                Sheet theSheet = act.dataTable("/home/user/Bureau/prototype/TD88-90.xlsx");
                System.out.println(act.px());
            }*/
            
      public void paint(Graphics g) {

      int age   = 0;//Integer.parseInt(getParameter("x"));
      String myString = px(age);
      //int mySize      = Integer.parseInt(getParameter("size"));

      //Font f = new Font(myFont, Font.BOLD, mySize);
      //g.setFont(f);
      g.setColor(Color.red);
      g.drawString(myString, 20, 20);

   }
            
         
    
    
    
    
}
