import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.satya.hadoop.fake_review.Detector_Mapper;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private static String product_name ;
	private static double rating;
	private static String review;
	private static double cal_rating;
	private static double avg_rating;
	private static int review_id;
	private static  String pathToreview_set;
	
	/*
	 * Finds threshold value of two numbers 
	 * and return it only positive value
	 */
	public static double threshold(double x, double y){
		double value = 0.0;
		value = x-y;
		if(value<0){
			return -value;
		}
		else{
			return value;
		}
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		pathToreview_set = args[0];
		SWN3 samp = new SWN3();
		DataSet ds = new DataSet(pathToreview_set);
        samp.find_rating();
        URL stream = Main.class.getResource("/left3words-wsj-0-18.tagger");
        String file = stream.getFile();
        MaxentTagger tagger = new MaxentTagger(file);
        try{
            BufferedReader csv =  new BufferedReader(new FileReader(pathToreview_set));
            String line ="";
            while((line = csv.readLine()) != null){
                String[] data = line.split(":");
                if(data[0].matches("^#{5}(.*)")){
	    			   String[] id = line.split("^#{5}") ;
	    			   review_id = Integer.parseInt(id[1]);
	    			   System.out.println("\n\n\nFor Review: "+ review_id);
	    		}
                else if(data[0].equals("[productName]")){	
               	 	product_name = data[1];
                }
                else if(data[0].equals("[fullText]")){
               	 	review = data[1];
                    review = review.toLowerCase();
                    System.out.println(review);
                    String tagged = tagger.tagString(review);
                    cal_rating = samp.classifyreview(tagged);
                    avg_rating = ds.calAverageRating(product_name);
                   
                }
                else if(data[0].equals("[rating]")){
                	rating = Double.parseDouble(data[1]);
                	rating = (double) ((rating/(double)5)*(double)2 - (double)1); //normalizing rating
                	System.out.println("Calculated Rating: " + cal_rating);
                   // System.out.println("Rating: " + rating);
                    System.out.println("Average Rating: " + avg_rating);
                  /*  if(((int) rating ^(int) cal_rating) < 0){			 // To check for opposite signs.
                    	System.out.println("Sarcasm Detected :" + review_id");
                    	cal_rating = -cal_rating;
                    }*/
                    if(!(threshold(avg_rating,cal_rating) < 0.5)){		//Threshold value.
                    	ds.deleteReview(review_id);
                    	System.out.println("Deleted Review due to lack of genuineness");
                    	System.out.println();
                    }
                }
                
                /*
                if(rating>=0.75)
                	System.out.println("very positive");
                else if(rating > 0.25 && rating<0.5)
                	System.out.println("positive");
                else if(rating>=0.5)
                	System.out.println("positive");
                else if(rating < 0 &&rating>=-0.25)
                	System.out.println("negative");
                else if(rating < -0.25 && rating>=-0.5)
                	System.out.println("negative");
                else if(rating<=-0.75)
                	System.out.println("very negative");
                else
                	System.out.println("neutral");
                */
                
                
              }
            csv.close();
        }
                    catch(Exception e){e.printStackTrace();}  

	}

}
