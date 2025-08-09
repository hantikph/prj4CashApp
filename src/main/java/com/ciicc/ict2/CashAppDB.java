package com.ciicc.ict2;

import java.sql.*;


public class CashAppDB {
    private static final String url = "jdbc:mysql://localhost:3307/prj4_cash";
    private static final String usern = "root";
    private static final String password = "";
    private static final String sqlqry = "SELECT * FROM ";
    private static Connection conn;
    private static Statement stmt;

    public static void con() {
        try {
            conn = DriverManager.getConnection(url, usern, password);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);  // type 1004, 1008
            ResultSet rs = stmt.executeQuery(sqlqry +"users");
//            System.out.println(rs);
        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void getUsers() {
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +" users");
            while (rs.next()) {
                System.out.print(rs.getInt(1) +", ");
                System.out.print(rs.getString(2) +", ");
                System.out.print(rs.getString(3) +", ");
                System.out.print(rs.getString(4));
                System.out.println();
            }
            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void getUser(int id) {
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +"users WHERE id = "+ id);
            rs.absolute(1);

            System.out.print(rs.getInt(1) +", ");
            System.out.print(rs.getString(2) +", ");
            System.out.print(rs.getString(3) +", ");
            System.out.print(rs.getString(4));

            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static UserAccount getUserByID(int id) {
        String name = "";
        String email = "";
        String mobileNum = "";
        int pIN = 0;
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +"users WHERE id = "+ id);
            rs.absolute(1);

//            System.out.print(rs.getInt(1) +", ");
//            System.out.print(rs.getString(2) +", ");
//            System.out.print(rs.getString(3) +", ");
//            System.out.print(rs.getString(4));

//            acctID = rs.getInt(1);
            name = rs.getString(2);
            email = rs.getString(3);
            mobileNum = rs.getString(4);
            pIN = rs.getInt(5);

            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return new UserAccount(id, name, email, mobileNum, pIN);
    }

    public static UserAccount getUserByNumber(String number) {
        String name = "";
        String email = "";
        String mobileNum = "";
        int acctID = 0;
        int pIN = 0;
        try{
            con();
            ResultSet rs = stmt.executeQuery(String.format("%susers WHERE number = '%s'", sqlqry, number));
            rs.absolute(1);

//            System.out.print(rs.getInt(1) +", ");
//            System.out.print(rs.getString(2) +", ");
//            System.out.print(rs.getString(3) +", ");
//            System.out.print(rs.getString(4));

            acctID = rs.getInt(1);
            name = rs.getString(2);
            email = rs.getString(3);
            mobileNum = (number.equals(rs.getString(4))) ? rs.getString(4) : number;
            pIN = rs.getInt(5);

            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return new UserAccount(acctID, name, email, mobileNum, pIN);
    }

    public static void addUser(String name, String email, String number, int pIdNum) {
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +" users");
            rs.moveToInsertRow();

//            rs.updateInt(1, 1020);  // last manual entry
            rs.updateString(2, name);
            rs.updateString(3, email);
            rs.updateString(4, number);
            rs.updateDouble(5, pIdNum);

            rs.insertRow();

//            System.out.println("Adding user:");
//            System.out.print(rs.getString(2) +", ");
//            System.out.print(rs.getString(3) +", ");
//            System.out.print(rs.getString(4));

            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void editPIN(int acctID, int newPIN) {
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +"users WHERE id = "+ acctID);
            rs.absolute(1);

//            System.out.print(rs.getInt(1) +", ");
//            System.out.print(rs.getString(2) +", ");
//            System.out.print(rs.getString(3) +", ");
//            System.out.print(rs.getString(4));

            rs.updateInt(5, newPIN);
            rs.updateRow();
            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void editUserInfo(String number, String name, String email) {
        try{
            con();
            ResultSet rs = stmt.executeQuery(String.format("%susers WHERE number = %s", sqlqry, number));
            rs.absolute(1);

//            System.out.print(rs.getInt(1) +", ");
//            System.out.print(rs.getString(2) +", ");
//            System.out.print(rs.getString(3) +", ");
//            System.out.print(rs.getString(4));

            rs.updateString(2, name);
            rs.updateString(3, email);
            rs.updateRow();
            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    // for CheckBalance

    public static boolean findUserBalance(int acctID) {
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +" balance WHERE user_id = "+ acctID);

            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public static void initUserBalance(int acctID) {
//        Balance bal = new Balance(acctID);
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +" balance ");

            rs.moveToInsertRow();

            rs.updateInt(2, acctID);
            rs.updateDouble(3, 0);

            rs.insertRow();

//            System.out.println("Adding user balance:");
//            System.out.print(rs.getString(2) +", ");
//            System.out.print(rs.getDouble(3));

            conn.close();

        } catch (java.sql.SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static double getUserBalance(int acctID) {
        String qry = "SELECT * FROM balance WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(url, usern, password);
                PreparedStatement pstmt = conn.prepareStatement(qry)){

            pstmt.setInt(1, acctID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {

                    System.out.print(rs.getInt(1) +", ");
                    System.out.print(rs.getInt(2) +", ");
                    System.out.print(rs.getDouble(3));

                    return rs.getDouble(3);
                }
            }

        } catch (java.sql.SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return 0;
    }

    public static void updateUserBalance(int acctID, double amount) {
        try{
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +"balance WHERE user_id = "+ acctID);
            rs.absolute(1);

            rs.updateDouble(3, amount);

//            System.out.print(rs.getInt(1) +", ");
//            System.out.print(rs.getInt(2) +", ");
//            System.out.print(rs.getDouble(3));

            rs.updateRow();
            conn.close();

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    // for Transactions
    public static void createTransaction(String txnID, double amount, String name, int acctID, int xferToID, int xferFromID) {
        try {
            con();
            ResultSet rs = stmt.executeQuery(sqlqry +"transactions ");
            System.out.println();
            rs.moveToInsertRow();

            rs.updateString("trnID", txnID);
            rs.updateDouble("amount", amount);
            rs.updateString("name", name);
            rs.updateInt("account_ID", acctID);
            rs.updateInt("transferToID", xferToID);
            rs.updateInt("transferFromID", xferFromID);

            rs.insertRow();

//            System.out.println("Transaction created: "+ String.valueOf(rs.getString(2)));
//            System.out.println("Transaction date: "+ String.valueOf(rs.getLong(5)));

            conn.close();

        } catch (java.sql.SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public static Transaction getTxnDetails(String tID) {
        try {
            con();
            ResultSet rs = stmt.executeQuery(String.format("%stransactions WHERE trnID = '%s'", sqlqry, tID));
            rs.absolute(1);
            System.out.println(rs.getString(2));

            Transaction t = new Transaction();
            t.setTxnID(rs.getString("trnID"));
            t.setAmount(rs.getDouble("amount"));
            t.setName(rs.getString("name"));
            t.setAccount_ID(rs.getInt("account_ID"));
            t.setDate(rs.getTimestamp("date"));
            t.setTransferToID(rs.getInt("transferToID"));
            t.setTransferFromID(rs.getInt("transferFromID"));

            return t;

        } catch (java.sql.SQLException e) {
            System.err.println("Err getting transaction id: "+ e.getLocalizedMessage());
            return null;
        }
    }

    public static void getAllTransactions() {
        try {
            con();  // connect method in file
            ResultSet rs = stmt.executeQuery(sqlqry + "transactions");  // premade stmt & query

            while(rs.next()) {  // .next() boolean for next record
                Main.transactions.add(new Transaction(rs.getString("trnID"), rs.getDouble("amount"),
                        rs.getString("name"), rs.getInt("account_ID"), rs.getTimestamp("date"),
                        rs.getInt("transferToID"), rs.getInt("transferFromID")));

                System.out.println(rs.getString("trnID"));
//                System.out.println(rs.getDouble("amount"));
//                System.out.println(rs.getString("name"));
//                System.out.println(rs.getInt("account_ID"));
//                System.out.println(rs.getTimestamp("date"));
//                System.out.println(rs.getInt("transferToID"));
//                System.out.println(rs.getInt("transferFromID"));
            }

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getUserTransactions(int id) {
        String qry = "SELECT * FROM transactions WHERE account_id = ? OR transferToID = ?";
        Transactions.results.clear();
        System.out.println("Called getUserTransactions");
        try (Connection conn = DriverManager.getConnection(url, usern, password);
                PreparedStatement pstmt = conn.prepareStatement(qry)){
            pstmt.setInt(1, id);
            pstmt.setInt(2, id);

            try (ResultSet rs = pstmt.executeQuery()) {  // premade stmt & query

                while (rs.next()) {  // .next() boolean for next record
                    Transaction trn = new Transaction(rs.getString("trnID"), rs.getDouble("amount"),
                            rs.getString("name"), rs.getInt("account_ID"), rs.getTimestamp("date"),
                            rs.getInt("transferToID"), rs.getInt("transferFromID"));

                    Transactions.results.add(trn);

//                    System.out.print(rs.getString("trnID")+", ");
//                    System.out.print(rs.getDouble("amount")+", ");
//                    System.out.print(rs.getString("name")+", ");
//                    System.out.print(rs.getInt("account_ID")+", ");
//                    System.out.print(rs.getTimestamp("date")+", ");
//                    System.out.print(rs.getInt("transferToID")+", ");
//                    System.out.print(rs.getInt("transferFromID"));
//                    System.out.println("EOF");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getUserTransactions: " + e.getMessage());
        }
    }


//    public static void main(String[] args) {
//        con();  // test connection
//        addUser("Pedro Parkour", "gagamba@email.kuno", "09123456789", 654321);
//        getUsers();
//        getUser(1001);
//        createTransaction(txn17544367);
//        Transaction t = getTxnDetails("gc1754447992309CI1020");
//        getUserTransactions(1020);
//    }
}
