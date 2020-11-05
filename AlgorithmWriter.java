
/*The aim of this program is to make a algorithm
 (
 A txt file with steps by examining a program provided

 "NOT to be confused with creating new independent problem solving procedures"
  )

  //MY THOUGHTS
  Functions that will be created
   1.TXT to string converter or read
   2.String to TXT converter or write
   3.Buffer
		   DESCRIPTION: To hold sentence before sending it to the inventory and
		                it also does the basic analyses and creates filename,function,class

   4.Inventory
		   DESCRIPTION: TO hold all the STEP components and computing the sentence and calls the stepcreator

   5.StepsCreator
		   DESCRIPTION: it creates steps from the string that it gets from the inventory


There are many things changed in the procedure like which function calls which one to achieve the end result and the other things
: the buffer is calling reader if the reader hasn't been called and passing it to the inventory
:the inventory at the end is calling buffer to obtain the next line so that the process keeps on running  till there are no more lines
:
SOMNATH PAUL 2018
 */

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgorithmWriter {

   private String Sourcefile; // no use

   private  String file_name;//output file name
   private  String singlesentence;
   private int stepscounter;
   private static int linecounter;
   private static String variables[];

   private Matcher m;
   private FileWriter fout;


    private FileReader fin ;
    private BufferedReader finn ;


   private AlgorithmWriter(String fileSourceName) throws IOException{
        singlesentence="";
        fin = new FileReader(fileSourceName);//Sourcefile name
        finn =new BufferedReader(fin);


    }
    public static void main(String[] args)throws IOException {
       // Scanner inn = new Scanner(System.in);
        AlgorithmWriter ob = new AlgorithmWriter("asd.txt");



        //for reading Lines


        System.out.println("Copy all the texts and paste here");
        //  ob.file_name = inn.nextLine();

        ob.buffer(false);

        ob.write("STEP "+(++ob.stepscounter)+" : END");
        ob.cleanup();


        System.out.println("\n$$$$$$ NO PROGRAM IS 100% CORRECT SO OPEN THE FILE AND CHECK THE RESULT AND CHANGE THE LINES WHERE " +
                "\nUSER ASSISTANCE IS REQUIRED IS STATED WITH THE GIVEN LINE NUMBER $$$$$$");

    }



    //closes the writer object and the reader object
     void cleanup()throws IOException{
         fout.close();

         fin.close();
         finn.close();

     }
     //53 4f 4d 4e 41 54 48 20 50 41 55 4c 20 32 30 31 38
    // txt to string
     void read() throws IOException {
     String s="";
        if((s = finn.readLine()) != null) {
                 linecounter++;

                 if (s.contains("//")) {//taking the string before comments
                  //   System.out.println(singlesentence+"#read");
                    singlesentence = s.substring(0,s.indexOf("//")).trim();

            }


            else {
                     singlesentence = s.trim();
                 }

        }
        else {
           singlesentence = "#NOMORELINES#";
        //System.out.println(singlesentence);
        }



    }

    //string to txt
    void write(String s) throws IOException {
        fout= new FileWriter(file_name, true);
        PrintWriter Fout=new PrintWriter(fout);

        Fout.println(s);

        Fout.close();



    }

    //this function checks the regex pattern with the given string
    // called from the inventory function
    boolean checkRegexs(String regex,String s){
        Pattern p=Pattern.compile(regex);
        m=p.matcher(s);

        boolean found=false;

        if(m.matches())
        {System.out.println(s +"   :||:    "+ regex);
           found=true;}
            //the reason break is used here so that to avoid the next time it iterates
            //  the string will be empty and it will throw a IllegalStateException
            //53 4f 4d 4e 41 54 48 20 50 41 55 4c 20 32 30 31 38


        return found;

    }

    //53 4f 4d 4e 41 54 48 20 50 41 55 4c 20 32 30 31 38
    // responsible for storing the string temporarily
    // and sending it in single lines or in a group of lines to the stepCreator
    void buffer(boolean alreadyCalledRead) throws IOException {

        //calling the reader

       if(!alreadyCalledRead)
       read();


        //file_name="testStringMultipleLines.txt";
       // singlesentence="clatsss { " ;

        String s ="";
        String s2="";


        singlesentence=singlesentence.trim();


        StringTokenizer str = new StringTokenizer(singlesentence);
        //53 4f 4d 4e 41 54 48 20 50 41 55 4c 20 32 30 31 38
        // the code below is written like this because of how execution takes place, s will be initialised to the string even if it does not equal to "public","static"
        if(str.hasMoreTokens() && (((s=str.nextToken()).equals("public") || s.equals("static")) && ((s=str.nextToken()).equals("static") || s.equals("public"))))
         s=str.nextToken();

        // the code below is written like this because of how execution takes place, s2 will be initialised to the string even if it does not equal to "public","static"
        if(str.hasMoreTokens() && (((s2=str.nextToken()).equals("public") || s2.equals("static")) && ((s2=str.nextToken()).equals("static") || s2.equals("public"))))
         s2=str.nextToken();




        String datatype[]={"int","boolean","char","short","void","String","long","double","float"};

         for(int i=0;i<datatype.length;i++)
             if (s.equals(datatype[i]) && s2.matches("\\w+\\s*\\((([\\w, \\[\\]]*\\))|[\\w]*)\\{?"))//regex checks if it is a function
                 s="dataType";



        //53 4f 4d 4e 41 54 48 20 50 41 55 4c 20 32 30 31 38
         switch (s) { //this is for putting an end and beginning of an algorithm and its subparts = functions,class.

                    case "class"://setting output filename
                        if(s2.contains("{"))
                         file_name = "Algorithm_For_" + s2.substring(0, s2.indexOf('{')).trim() + ".txt";
                        else
                            file_name="Algorithm_For_"+s2+".txt";


                         write("                 -:"+file_name.substring(0,file_name.indexOf('.'))+" :- ");
                         write("----------------------------------------------------------------");
                         inventory("");
                         break;
                     case "dataType":
                         if(stepscounter!=0){
                             write("STEP "+(++stepscounter)+" : END");
                             stepscounter = 0;
                         }

                         write("----------------------------------------------------------------");

                         s = "Algorithm for " + s2.substring(0, s2.indexOf('(')) + " function : ";

                         write(s);
                         write("");//newline
                         write("STEP "+(++stepscounter) +" : START");//1st step is start
                         inventory("");
                         break;





                     default:
                        // System.out.println(singlesentence+"--");
                         if(!singlesentence.equals("#NOMORELINES#"))
                         this.inventory(singlesentence);


         }



    }

    //responsible for creating steps and calling the write function
    void stepCreator(String s) throws IOException {
       
        write("STEP "+(++stepscounter)+" : "+s);


    }

    //it is like a module holder where i can
    //implement more modules which converts code into algo. lines
    //it calls the stepCreator and
    // the buffer  to continue to the next line

    void inventory(String s) throws IOException{

         s+="";
        if(s.contains(";"))
        s=s.substring(0, s.indexOf(";"));

        boolean hold;
          
       // System.out.println("inventory#    "+s);
    
        StringTokenizer ob=new StringTokenizer("sa as");


        if(s.equals("{")  || s.equals("}")|| s.equals("")){}//for blank lines

        else if(checkRegexs("[\\w]{3,7}\\s+(\\w+);", s)) {         //int a
              stepCreator("Declare the variables "+m.group(1) );
              do{//advance checking for similar lines
                 read();
                 System.out.println(singlesentence +"  # advance check");
                  if(checkRegexs("[\\w]{3,7}\\s+(\\w+);", singlesentence.trim())){
                      write("               "+m.group(1)+" ");
                      hold=true;
                  }
                  else{
                      hold=false;
                      buffer(true);
                  }
              }while(hold);
        }

        else if(checkRegexs("\\s*[\\w]{3,7}\\s+([\\w]+)\\s*=(?!\\s*new)(?!\\s*\\w+\\(\\w*\\))\\s*[\\w +\\-()/%*]*;", s) ){     //int a = a+b+(asd+ads)
            stepCreator("Declare and initialize the variable  "+m.group(1) +"  with  :USER ASSISTANCE REQURIED :     AT LINE NO.  :   "+linecounter );
        }


        else if(checkRegexs( "\\s*[\\w]+\\s*=(?!\\s*new\\s+)(?!\\s*\\w+\\(\\w*\\))\\s*[\\w/(*%\\-+)]+;", s)) {                       // a = a + b+ (c*d)/t ,  a=a ,etc
            stepCreator(" :USER ASSISTANCE REQUIRED   :  AT LINE NO.   : "+linecounter+" :   LINE       :");
            write("                  "+ s);
        }
         else if(checkRegexs("\\w+\\s*=\\s*\\w*\\.?(\\w+)\\(\\w*\\);", s)) {                                      // function call   with  '.' and without '.'   :  a=ab.acb() , a=abc(ads)
               stepCreator("Call  " +m.group(1)+"  function : USER ASSISTANCE REQUIRED : " );
               write("                 "+s);
        }
        else if(checkRegexs("for\\s*\\(.+",s )){            //for loop
            stepCreator("Repeat from STEP __ through STEP __ till i becomes ___" );


        }
        else if(s.contains("import")){}
        else if(checkRegexs("System\\s*\\.\\s*out\\s*\\.\\s*println\\(\\)", s)){}
        else if(checkRegexs("System\\s*\\.\\s*out\\s*\\.\\s*print(ln)*\\s*(\\(.*\\))",s)){//print statement

            stepCreator("PRINT  "+ m.group(2));

        }
        //53 4f 4d 4e 41 54 48 20 50 41 55 4c 20 32 30 31 38
        else
            stepCreator(s+"#inventory");
           // stepCreator("****USER ASSISTANCE REQUIRED LINE NO = "+linenumber+"****");






        this.buffer(false);//calling buffer so that the next line is obtained

    }


}
