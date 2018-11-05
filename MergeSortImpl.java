
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.FileNameMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * This program implements the merge sort algorithm
 *
 * The algorithm is given below :
 *
 */

public class MergeSortImpl extends Application {

    /**
     *
     * @param original This is the list that needs to be partitioned into two sublist
     * @param <T> This is a generic palceholder
     * @return a new ArrayList and generic type <T>
     */
    private <T> List<T> splitOriginalList(List<T> original){
        return new ArrayList<>(original);//returns an ArrayList
    }
    /**
     * This class sort a single dimensional Array of data
     * 'data' parameter is a single dimensional array that the program will take in
     */

    public static  class ArrayMergeSort<E> implements Comparator<E>{
        private E[] S ;
        private E[] sortedData ;

        public ArrayMergeSort(E ...data){

            S = data;

            //sorting S recursively
            sortDataPart(S);
        }//end constructor

        @Override
        public int compare(E o1, E o2){

            int result = 0 ;
            if (o1 instanceof Integer) {
                return ((Integer) o1).compareTo((Integer)o2);
            }else if (o1 instanceof String){
                return  ((String) o1).compareTo((String) o2);
            }else if (o1 instanceof Boolean){
                return ((Boolean) o1).compareTo((Boolean)o2);
            }else if ((o1 instanceof Character)){
                return ((Character)o1).compareTo((Character) o2);
            }else if(o1 instanceof Float){
                return ((Float)o1).compareTo((Float) o2);
            }else if(o1 instanceof Byte){
                return ((Byte)o1).compareTo((Byte) o2);
            }else if (o1 instanceof Double){
                return ((Double)o1).compareTo((Double) o2);

            }

            return 0 ; //equal
        }//end comparator


        private void sortDataPart(E[] S){

            int size = S.length ;
            if (size < 2){
                return ; //stop the recursion
            }else{
                //divide
                E[] S1 = (E[])new Object[S.length / 2];
                E[] S2 = (E[])new Object[S.length / 2 + (S.length % 2)] ;

                int i = 0 ;
                while ( i < S.length / 2){
                    S1[i] = S[i] ;
                    S[i] = null ;
                    i++ ;
                }

                //load the remaining into the S2 Array
                int c = 0 ;
                while ( i < S.length){
                    S2[c] = S[i];
                    S[i] = null ;
                    c++ ;
                    i++ ;
                }
                c = 0 ;
                //recursive
                /**System.out.println("------------------------------------------------------------");
                 System.out.println("S1 ---->"+Arrays.toString(S1));
                 System.out.println("S2 ---->"+Arrays.toString(S2));
                 * System.out.println("------------------------------------------------------------");
                 */
                sortDataPart(S1);
                sortDataPart(S2);
                //conquer
//                System.out.println("Recursion Finised...");

                mergeData(S , S1 ,S2, new ArrayMergeSort<>());


            }
        }


        private void mergeData(E[] S , E[] S1 , E[] S2, Comparator<E> c) {

            // System.out.println("To string "+S1[2].toString());

            int m = 0; //this is used as the index of S (the original data given)
            int iCount = 0;
            int jCount = 0;
            while (iCount < S1.length && jCount < S2.length) {
                if (c.compare(S1[iCount], S2[jCount]) <= 0) {
                    //  System.out.println("n1 val"+n1+" n2 val"+n2);

                    S[m] = S1[iCount];
                    iCount++; //increment i
                    m++;
                } else {
                    // System.out.println("n1 val"+n1+" n2 val"+n2);

                    S[m] = S2[jCount]; //copy the value of S2[j]
                    jCount++;
                    m++;
                }
            }//end first while

            //offloading the remaining S1 contents to the S
            while (iCount < S1.length) {
                S[m] = S1[iCount];
                iCount++; //increment i
                m++; //increment the index of S
            }//end the while loop

            while (jCount < S2.length) {
                S[m] = S2[jCount];
                jCount++; //increment i
                m++;//increment the index of S
            }//end the while loop

        }//end mergeData


        /**
         * prints the output of the sorting
         */
        private void outputSortedData(){
            System.out.println("Sorted Data");
            sortedData = S ; //collects the sorted data
            System.out.println(Arrays.toString(S));//this will show the sorted array
        }//end method

        public void setData(E...data){
            this.S = data ;
        }

        /**
         * This part gets the data from the user to sort
         * @return this method returns an integer array
         */
        public E[] getData(){
            return this.S ; //returns the data supplied to the program
        }

        /**
         *
         * @return the size of the data supplied into the program
         */

        public int getDataSize(){
            return this.S.length ;
        }//end
    }//end class

    public class ListMergeSort{
        private List<Integer> listData;

        public ListMergeSort(List<Integer> data){
            listData = data;
        }//end constructor

        /**
         *
         * @param data
         */
        public void setData(List<Integer> data){
            this.listData = data ;
        }

        /**
         * This part gets the data from the user to sort
         * @return this method returns an integer array
         */
        public List<Integer> getData(){
            return this.listData ; //returns the data supplied to the program
        }

        /**
         * @return the size of the data supplied into the program
         */
        public int getDataSize(){
            return this.listData.size() ;
        }//end
    }//end ListMergeSort

    //testing the program here
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        float compTime = 0L;
        Long start = 0L ;

        while (true) {
            System.out.println("Select option :\n(1)Test program\n(2)sort data in a file");
            int opt = scan.nextInt();
            if (opt == 1) {

                Random random = new Random();
                Scanner inputHolder = new Scanner(System.in);
                System.out.println("Enter data size :'");
                int dataSize = inputHolder.nextInt();
                Integer[] dataI ;
                Double[] dataD ;


                System.out.println("Choose data type to sort\n(1)Integer\n(2)Double");
                int type = scan.nextInt();

                if (type==1){
                    dataI= new Integer[dataSize];
                    for (int i = 0; i < dataSize; i++) {
                        //data.add(i*random.nextInt(1000000));
                        dataI[i] = Math.abs (random.nextInt(1000) );
                    }//end for loop

                    System.out.println("Original Integer Data :" + Arrays.toString(dataI) + "\n");
                    start = System.currentTimeMillis();
                    new MergeSortImpl.ArrayMergeSort<>(dataI); // The program kicks off from here
                    compTime = System.currentTimeMillis() - start;
                    System.out.printf("The computational time is: %.3fs\n", compTime / 1000);
                    System.out.println("==========================================================================================================================================================================================================================-----------------------------");

                    dataI = null ; //release the memory used by dataI
                }else {
                    dataD = new Double[dataSize];
                    for (int i = 0; i < dataSize; i++) {
                        //data.add(i*random.nextInt(1000000));
                        dataD[i]=(random.nextDouble()*1000);
                    }
                    System.out.println("Original Data :" + Arrays.toString(dataD));
                    start = System.currentTimeMillis();//get the current milliseconds time from the system
                    MergeSortImpl.ArrayMergeSort<Double> program = new MergeSortImpl.ArrayMergeSort<>(dataD); // The program kicks off from here
                    compTime = System.currentTimeMillis() - start;
                    program.outputSortedData(); //prints out the sorted array data
                    System.out.printf("The computational time is: %.3fs\n", compTime / 1000); //gives the computational time in seconds
                    dataD = null ; //release the memory used
                }//tdata to be sorted by the program
            } else {
                //when an input file is preferred
                Application.launch(args);
            }

        }//end while(true)
    }//end main method

    @Override
    public void start(Stage stage){
        Button btn = new Button("Select File");
        FileChooser fileChooser = new FileChooser();
        Random random = new Random();
        Scanner inputHolder = new Scanner(System.in);

        Pane pane = new Pane();
        pane.getChildren().add(btn);
        stage.setScene(new Scene(pane));

        try {
            btn.setOnMouseClicked(e -> {
                Path filePath = Paths.get("");

                List<String> lines = new ArrayList<>();
                fileChooser.setTitle("View Pictures");
                fileChooser.setInitialDirectory(
                        new File(System.getProperty("user.home"))
                );

                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("TXT", "*.txt")
                );

                try {
                    System.out.println("Enter option:\n(1)Number\n(2)Strings");
                    int opt = inputHolder.nextInt();
                    filePath =  fileChooser.showOpenDialog(stage).toPath();
                    if (!filePath.equals(null)){
                        lines = Files.readAllLines(filePath);
                        String str = "";

                        List<String> val = new ArrayList<>();

                        Collections.addAll(lines,lines.get(0).split("\\s+|\\.+"));
                        Collections.addAll(lines,lines.get(0).split("\\s+|\\.+"));
                        lines.remove(0);

                        System.out.println(lines.toString());
                        Double[] data = new Double[lines.size()];
                        String[] strData = new String[lines.size()];

                        if (opt==1){
                            for(int i = 0 ; i < lines.size()  ; i++){
                                //  if (lines.get(i).matches("[0-9]+ | [0-9]+\\.")){
                                data[i] = Double.parseDouble(lines.get(i));
                                //  }
                            }//end for

                            System.out.println("Length of data : "+data.length);

                            System.out.println("Original Data :" + Arrays.toString(data));
                            Long start = System.currentTimeMillis();//get the current milliseconds time from the system

                            MergeSortImpl.ArrayMergeSort<Double> program = new MergeSortImpl.ArrayMergeSort<>(data); // The program kicks off from here
                            float compTime = System.currentTimeMillis() - start;
                            // program.outputSortedData(); //prints out the sorted array data
                            System.out.printf("The computational time is: %.3f\ns", compTime / 1000); //gives the computational time in seconds
                            System.out.println("==========================================================================================================================================================================================================================-----------------------------");

                        }//end if opt is 1
                        else {

                            System.out.println("Original Data :"+Arrays.toString(lines.toArray(new String[lines.size()])));
                            Long start = System.currentTimeMillis() ;//get the current milliseconds time from the system
                            MergeSortImpl.ArrayMergeSort<String> program =  new MergeSortImpl.ArrayMergeSort<>(lines.toArray(new String[lines.size()])); // The program kicks off from here
                            float compTime = System.currentTimeMillis() - start ;
                            program.outputSortedData(); //prints out the sorted array data
                            System.out.printf("The computational time is: %.3fs",compTime/1000);

                        }//end else of opt conditioning
                    }//end if file is not empty condition
                } catch (NullPointerException | IOException e1) {
                    if (filePath==null){
                        System.out.println("No file was selected");
                    }
                    e1.printStackTrace();
                }

            });
        }catch (Exception e){
            System.out.println();
        }

          /*  inputHolder = new Scanner(fileChooser.showOpenDialog(stage)); //this line will selet the file from the user system
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            */
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.show();// display the dialog window
    }//end stage method
}//end MergeSortAlgorithm



//writing to a file
/**for (int i = 0; i <  data.length; i++) {
 *
 *  Integer[] data ;

 data = new Integer[5000000];
 // data.add(i*random.nextInt(1000000));
 data[i] = random.nextInt(1000000);
 }
 */