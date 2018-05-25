import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class DataSet{
	private String pathToreview_set;
	
	/*
	 * Constructor that takes a file and initializes
	 * data set and their average rating file.
	 */
	DataSet(String file){
		pathToreview_set = file;
	}
	/*
	 * This method calculates average rating
	 * of a review depending on the product name
	 * which was passed as an argument
	 * it also normalises the avg_rating from 
	 * 5 to -1 to 1 scale.
	 */
	public double calAverageRating(String name){ 
		 double normalise = 0.0;
	       try{
	    	   BufferedReader csv =  new BufferedReader(new FileReader(pathToreview_set));
	    	   String line ="";
	    	   int i=0;
	           String dummy = "";
	           double rating = 0.0;
	          while((line = csv.readLine()) != null){
	        	  String[] data = line.split(":");    	         
	        	  if(data[0].equals("[productName]")){
	        		  	dummy = data[1];
	    	  			if(data[1].equals(name)){    
	    	  				i = i+1;
	    	  			}                       
	        	  }
	        	  if(data[0].equals("[rating]")){
	                    if(dummy.equals(name)){
	                    	rating += Double.parseDouble(data[1]);
	                    }
	        	  }
	                   
	          }
	          normalise = ((((rating/i)*2)/5)-1);
	          csv.close();
	          
	       }
	       catch(Exception e){e.printStackTrace();
	       }
		return normalise;
	}
	
	
	/*
	 * This method returns boolean value true if 
	 * the review with the given id is present in
	 * the data set.
	 */
	public boolean isPresent(int i){
		try{
             BufferedReader csv =  new BufferedReader(new FileReader(pathToreview_set));
             String line = null;
             while((line = csv.readLine()) != null){
            	 if(line.matches("#{5}"+i)){
            		 csv.close();
            		 return true;
            	 }	 
              }  
             csv.close();
         }
         catch(Exception e){e.printStackTrace();}
		 return false;	 
	}
	/*
	 * This method takes the review id as input and delete it 
	 * by creating a temporary file and copying the original data into 
	 * the file and renaming the temporary file.
	 * 
	 */
	public void deleteReview(int id){
		try{
             BufferedReader csv =  new BufferedReader(new FileReader(pathToreview_set));
             PrintWriter writer = new PrintWriter(pathToreview_set+"tm.txt", "UTF-8");
             File file1 = new File(pathToreview_set);
             File file2 = new File(pathToreview_set+"tm.txt");
             String line = null;
             while((line = csv.readLine()) != null){       	 
            	 if(line.matches("^#{5}"+id)){
            		 while((line = csv.readLine()) != null){   
            			 if(line.matches("^#{5}(.*)"))	 break;                             		 
            		 }         
            			 
            	 }
            	 if(line != null){
            		 writer.println(line);
                 	writer.flush();
            	 }
           
            }
             file1.delete();
             file2.renameTo(file1);
             
             
             csv.close();
             writer.close();
         }
                     catch(Exception e){e.printStackTrace();}  
		 
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataSet dt = new DataSet("/home/hduser1/workspace/dm_project/src/data/amazon_mp3");
		
		System.out.println(dt.calAverageRating("Apple iPod classic 80 GB 6G (Black)"));
		
			

	}

}
