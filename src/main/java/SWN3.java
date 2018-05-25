import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
public class SWN3 {
    private String pathToSWN;
    public HashMap<String, Double> _dict;

    public void find_rating(){
    	_dict = new HashMap<String, Double>();
    	HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
        try{
        	URL stream = SWN3.class.getResource("sentiword.txt");
        	pathToSWN = stream.getFile();
            BufferedReader csv =  new BufferedReader(new FileReader(pathToSWN));
            String line = "";           
            while((line = csv.readLine()) != null){
                String[] data = line.split("\t");
                Double score = Double.parseDouble(data[2])-Double.parseDouble(data[3]);
                String[] words = data[4].split(" ");
                for(String w:words){
                    String[] w_n = w.split("#");                 
                    w_n[0] += "#"+data[0];
                    int index = Integer.parseInt(w_n[1])-1;
                    if(_temp.containsKey(w_n[0]))	{
                              // System.out.println( _temp.get(w_n[0]));
                        Vector<Double> v = _temp.get(w_n[0]);
                        if(index>v.size())
                            for(int i = v.size();i<index; i++)
                                v.add(0.0);
                        v.add(index, score);
                        _temp.put(w_n[0], v);
                    }
                    else
                    {
                        Vector<Double> v = new Vector<Double>();
                        for(int i = 0;i<index; i++)
                            v.add(0.0);
                        v.add(index, score);
                        _temp.put(w_n[0], v);
                     
                    }
                }
            }
            
         /* for (String key : _temp.keySet()) {
     System.out.println(key);
      System.out.println(_temp.get(key));
    }*/
            Set<String> temp = _temp.keySet();
            for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
                String word = (String) iterator.next();
                Vector<Double> v = _temp.get(word);
                double score = 0.0;
                double sum = 0.0;
                double b = 0.0;
               /* if(b ==0.0){
                 System.out.println("hello");                       
}*/
                for(int i = 0; i < v.size(); i++){
                         b= v.get(i);
                       //  System.out.println(v.get(i));
                      if(b!=0.0){
                      
                    score += ((double)1/(double)(i+1))*v.get(i);
                //for(int i = 1; i<=v.size(); i++)
                    sum += (double)1/(double)(i+1);
                              }}
                if(sum != 0){
                	score /= sum;
                }
                else{
                	score =0.0;
                }  
                _dict.put(word, score);
               
            }
           /* for (String key : _dict.keySet()) {
    // System.out.println(key);
      System.out.println(_dict.get(key));
    }*/


            csv.close();
        }
        catch(Exception e){e.printStackTrace();
        }  
        
  }

  /*  public Double extract(String word, String pos)
    {
        return _dict.get(word+"#"+pos);
    }
 public Double extract(String word)
    {
        
       
        return total;
    }
 

*/




    /*
     * This method calculates the rating by sentiment analysis
     * and then returns the value in -1 to 1 scale. 
     * 
     */


    public Double classifyreview(String tagged){
    	double total =0; 
        double totalScore = 0, averageScore;
        int i=0;
        // System.out.println(tagged);
         StringTokenizer  tokenizer = new StringTokenizer(tagged); // class used to divide into tokens 
         while (tokenizer.hasMoreTokens()){
        	 String word = tokenizer.nextToken();
        // String[] words = tagged.split("\\s+");
        // for(String word : words) {
         //	System.out.println(word);
         if(word.matches("(.*)/V(.*)")){
        	 String[] all_words = word.split("/");
      		if(check_for_word(all_words[0])){
      			word = all_words[0]+"#v";
      			//System.out.println(word);
      			if(_dict.get(word) != null){
      				//System.out.println(_dict.get(word+"#v"));
      				total =_dict.get(word) + total;
      				if(_dict.get(word) != 0){
                 	i=i+1;
      				}
                 
      			}
      		}
      		else{
      			all_words[0] = preprocessing(all_words[0]);
      			if(check_for_word(all_words[0])){
          			word = all_words[0]+"#v";
          			//System.out.println(word);
          			if(_dict.get(word) != null){
          				//System.out.println(_dict.get(word+"#v"));
          				total =_dict.get(word) + total;
          				if(_dict.get(word) != 0){
                     	i=i+1;
          				}
                     
          			}
          		}
      			else{
      				
      			}
      		}
         		
         	}
         	else if(word.matches("(.*)/J(.*)")){
         		String[] all_words = word.split("/");
         		if(check_for_word(all_words[0])){
         			word = all_words[0]+"#a";
         			//System.out.println(word);
         			if(_dict.get(word) != null){
         				//System.out.println(_dict.get(word+"#v"));
         				total =_dict.get(word) + total;
         				if(_dict.get(word) != 0){
                    	i=i+1;
         				}
                    
         			}
         		}
         		else{
         			all_words[0] = preprocessing(all_words[0]);
         			if(check_for_word(all_words[0])){
             			word = all_words[0]+"#a";
             			//System.out.println(word);
             			if(_dict.get(word) != null){
             				//System.out.println(_dict.get(word+"#v"));
             				total =_dict.get(word) + total;
             				if(_dict.get(word) != 0){
                        	i=i+1;
             				}
                        
             			}
             		}
         			else{
         				
         			}
         		}
         		
         	}
         	else if(word.matches("(.*)/R(.*)")){
         		String[] all_words = word.split("/");
         		if(check_for_word(all_words[0])){
         			word = all_words[0]+"#r";
         			//System.out.println(word);
         			if(_dict.get(word) != null){
         				//System.out.println(_dict.get(word+"#v"));
         				total =_dict.get(word) + total;
         				if(_dict.get(word) != 0){
                    	i=i+1;
         				}
                    
         			}
         		}
         		else{
         			all_words[0] = preprocessing(all_words[0]);
         			if(check_for_word(all_words[0])){
             			word = all_words[0]+"#r";
             			//System.out.println(word);
             			if(_dict.get(word) != null){
             				//System.out.println(_dict.get(word+"#v"));
             				total =_dict.get(word) + total;
             				if(_dict.get(word) != 0){
                        	i=i+1;
             				}
                        
             			}
             		}
         			else{
         				
         			}
         		}
         		
         	}
         	else{
         		
         		//System.out.println("not matched");
         	}
            totalScore += total;
            total=0;
            
        }
         averageScore = totalScore/i;
         return averageScore;
    }
    
    /*
     * This method checks if a word is 
     * present in the built in american-english 
     * dictionary or not.
     */
    public static boolean check_for_word(String word) {
        // System.out.println(word);
        try {
            @SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(
                    "/usr/share/dict/american-english"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.indexOf(word) != -1) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
        }

        return false;
    }
    /*
     * Text preprocessing for multi repetition of letters.
     */
    
    public static String preprocessing(String str){
		String input = str;
		return input.replaceAll("(.)\\1{1,}", "$1");
		
	}
    
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
    	System.out.println(preprocessing("HAIRRRRRRRRRRRRRRRRRRRRR"));
     }
        
        // TODO Auto-generated method stub
}






