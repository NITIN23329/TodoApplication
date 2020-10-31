package datamodel;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private  static final List<TodoItem> todoItemList=new ArrayList<>();    //it will store list of Todo items we will display
    private static  ArrayList<String> list = new ArrayList<>(); //it contains data of txt file
    //singleton class i.e. only one instance will be created
    private DataBase(){}
    public static void readFile() throws IOException {
        //reading data from text file and adding Todo items to todoItemList
        try(BufferedReader br = new BufferedReader(new FileReader("TodoData.txt"))) {
             String input;
             while ((input=br.readLine())!=null){
                 String[] data = input.split("\t");
                 String[] date = data[2].split(",");
                 LocalDate ld = LocalDate.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
                 TodoItem i = new TodoItem(data[0],data[1],ld);
                 todoItemList.add(i);
             }
        }
    }
    public static void writeFile() throws IOException{
        //writing data to text file from list
        // this operation is not needed if we write text file explicitly
        //adder() is called for first time so that default todo items will be written in TodoData.txt
        //adder();
        try(BufferedWriter br = new BufferedWriter(new FileWriter("TodoData.txt"))){
            for(String str : list)
                br.write(str+"\n");
        }
    }
    private static void adder(){
        //this are default Todo items
        list.add("Mail Birthday Card\tBuy a 30th birthday card for Vidhi\t2020,08,23");
        list.add("Doctor's Appointment\tSee Dr. Smith at 123 Main Street, Bring reports\t2020,11,12");
        list.add("Finish Design Proposal\tI promised Mike I'd email website mockups by friday,1 February\t2020,02,01");
        list.add("Pickup Doug at train station\tDoug's arriving on March 30 on 5:00 pm\t2020,03,30");
        list.add("Pick up dry cleaning\tClothes should be ready by wednesday\t2020,06,18");
        list.add("Buy a condom\tToday i am gonna have sex with Vidhi. She likes strawberry flavour\t2020,2,14");
    }
    public static void addItem(String shortDescription,String detailedDescription ,LocalDate ld){
        // User added Todo item using DialogPane
        // newly added Todo will be at starting of list
        String[] date = ld.toString().split("-");
        list=new ArrayList<>();
        list.add(shortDescription+"\t"+detailedDescription+"\t"+date[0]+','+date[1]+","+date[2]);
        //adding remaining Todo items from TodoData.txt
        try(BufferedReader br = new BufferedReader(new FileReader("TodoData.txt"))){
            String input;
            while ((input=br.readLine())!=null) {
                list.add(input);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try {
            writeFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public static List<TodoItem> getTodoItemList() {
        return todoItemList;
    }
}
