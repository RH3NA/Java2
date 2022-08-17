package com.javafxdemo.models;

import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

// this class is based on the Item table in the database and inventory items for search and select
public class ItemModel extends DBConnection {

    private int idItem;
    private String authorLastName;
    private String authorFirstName;
    private int numberInStock;
    private String title;
    private String isbn;
    private String publisher;
    private int totalStock;
    private int idBarcode;
    private String category;

    public static ArrayList<ItemModel> items = new ArrayList<>();

    public ItemModel(int idItem, int numberInStock, String title, String isbn, int totalStock, String publisher, String category, int idBarcode, String authorLastName, String authorFirstName, int totalStock1) {
    }
    public ItemModel(int idItem, String title, String authorLastName, String authorFirstName, String publisher, String isbn, String category, int numberInStock, int idBarcode, int totalStock) {
        this.idItem = idItem;
        this.title = title;
        this.authorLastName = authorLastName;
        this.authorFirstName = authorFirstName;
        this.publisher = publisher;
        this.isbn = isbn;
        this.category = category;
        this.numberInStock = numberInStock;
        this.idBarcode = idBarcode;
        this.totalStock = totalStock;}

    public ItemModel(int idItem, int numberInStock, String title, String isbn, int totalStock, String publisher) { //constructor
        this.idItem = idItem;
        this.numberInStock = numberInStock;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.totalStock = totalStock;}

    @Override
    public String toString() //overriding toString method so we get the values instead of the hashcodes from the arraylist prints
    {
        return "idItem = " + this.idItem +
                " numberInStock = " + this.numberInStock +
                " title = " + this.title +
                " isbn = " + this.isbn +
                " totalStock = " + this.totalStock +
                " publisher = " + this.publisher +
                " category = " + this.category +
                " idBarcode = " + this.idBarcode +
                " authorLastName = " + this.authorLastName +
                " authorFirstName = " + this.authorFirstName +
                " totalStock = " + this.totalStock;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }
    public int getTotalStock() {
        return totalStock;
    }
    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }
    public int getIdItem() {
        return idItem;
    }
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
    public int getNumberInStock() {
        return numberInStock;
    }
    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public int getIdBarcode() { return idBarcode;}
    public String getAuthorFirstName() {
        return authorFirstName;
    }
    public String getCategory() {
        return category;
    }

    public static void getItemsDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist
        items.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Item";

        ResultSet rst;
        rst = stm.executeQuery(sql);

        while (rst.next()) {
            ItemModel item = new ItemModel(rst.getInt("idItem"), rst.getInt("numberInStock"), rst.getString("title"), rst.getString("isbn"), rst.getInt("totalStock"), rst.getString("publisher"));
            items.add(item);
        }
    }

    public static ObservableList<ItemModel> searchAllModelObservableList = FXCollections.observableArrayList();

    public static void getSearchItemsDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist
        items.clear();

        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        String searchAllQuery = "SELECT DISTINCT idItem, title, lastName, firstName, publisher, isbn, category, numberInStock, idBarcode, totalStock FROM D0005N.inventory join item_has_creator on inventory.Items_idItems = item_has_creator.Item_idItem join item on item_has_creator.Item_idItem = item.idItem left join itemkeyword on item.idItem = itemkeyword.Item_idItem left join keyword ON itemkeyword.Keyword_idKeyword = keyword.idKeyword;\n";

        try {

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(searchAllQuery);

            while (resultSet.next()) {

                int queryIdItem = resultSet.getInt("idItem");
                String queryTitle = resultSet.getString("title");
                String queryAuthorLastName = resultSet.getString("lastName");
                String queryAuthorFirstName = resultSet.getString("firstName");
                String queryPublisher = resultSet.getString("publisher");
                String queryIsbn = resultSet.getString("isbn");
                String queryCategory = resultSet.getString("category");
                int queryNumberInStock = resultSet.getInt("numberInStock");
                int queryIdBarcode = resultSet.getInt("idBarcode");
                int queryTotalStock = resultSet.getInt("totalStock");

                searchAllModelObservableList.add(new ItemModel(queryIdItem, queryTitle, queryAuthorLastName, queryAuthorFirstName, queryPublisher, queryIsbn, queryCategory, queryNumberInStock, queryIdBarcode, queryTotalStock));
                System.out.println(searchAllModelObservableList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);}
        }

    public static void insertItem(int idItem, int numberInStock, String title, String isbn, int totalStock, String publisher) throws SQLException { //inserts an item
        getItemsDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

                Session.getInstance().setCurrentAdd(new ItemModel(idItem,numberInStock, title, isbn, totalStock, publisher));

                System.out.println("Current add information: " + Session.getInstance().getCurrentAdd());
                System.out.println(Session.getInstance().getCurrentAdd());

                String queryToItem = " insert into Item (idItem, numberInStock, title, isbn, totalstock, publisher)" + " values (?, ?, ? , ? , ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(queryToItem);
                preparedStmt.setInt(1, Session.getInstance().getCurrentAdd().idItem);
                preparedStmt.setInt(2, Session.getInstance().getCurrentAdd().numberInStock);
                preparedStmt.setString(3,Session.getInstance().getCurrentAdd().title);
                preparedStmt.setString(4,Session.getInstance().getCurrentAdd().isbn);
                preparedStmt.setInt(5,Session.getInstance().getCurrentAdd().totalStock);
                preparedStmt.setString(6,Session.getInstance().getCurrentAdd().publisher);

                preparedStmt.executeUpdate();
            }

    public static Boolean isbnExists(String ISBN) { //checks if the ISBN exists in the database
        for (ItemModel item : items) {
            if (!item.getIsbn().equalsIgnoreCase(ISBN)) {
                return false;
            }
        }
        return true;
    }

    public static void updateItem(int idItem, String attribute, String value) throws SQLException { //updates an attribute value in any table based on the input parameters
        if(isNumeric(value) == Boolean.FALSE) { //checks if it's a string or an int, if it's not an int it adds the quotation marks in order to work as a SQL query
            value = '"' + value + '"';
        }
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();

        //builds the query based on what the input parameters are
        String query = "UPDATE item\n" +
                "SET " + attribute + " = " + value + "\n" +
                "WHERE idItem = " + idItem;
        stm.executeUpdate(query);
        System.out.println(query); //prints the query in the console to check for any SQL syntax error
    }
    private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?"); //regex pattern

    public static boolean isNumeric(String strNum) { //method to check if the String contains numbers or not
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}

