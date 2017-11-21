package kr.co.mapchat.util.FireBase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyFirebaseConnector {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String table;

    public MyFirebaseConnector(String table){
        this.table = table;
    }

    public void insertData(Object obj){
        databaseReference.child(table).push().setValue(obj);
    }
}
